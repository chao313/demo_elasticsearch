package demo.elastic.search.feign.plus;

import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.Body;
import demo.elastic.search.po.Query;
import demo.elastic.search.po.compound.base.Bool;
import demo.elastic.search.po.response.ESResponse;
import demo.elastic.search.po.response.InnerHits;
import demo.elastic.search.po.term.level.TermLevel;
import demo.elastic.search.po.term.level.base.Terms;
import demo.elastic.search.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.functions.Action2;
import rx.functions.Func2;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@Service
@Slf4j
public class SearchServicePlus {

    @Resource
    private SearchService searchService;

    @Autowired
    private MappingServicePlus mappingServicePlus;

    @Autowired
    private ScrollServicePlus scrollServicePlus;


    /**
     * 使用scroll查询(不循环)
     *
     * @param index
     * @param scroll scroll时间
     * @param body
     */
    public ESResponse _search(String index, String scroll, String body) {
        String result = searchService._search(index, scroll, body);
        ESResponse esResponse = ESResponse.parse(result);
        return esResponse;
    }

    /**
     * 普通查询
     *
     * @param index
     * @param body
     */
    public ESResponse _search(String index, String body) {
        String result = searchService._search(index, body);
        ESResponse esResponse = ESResponse.parse(result);
        return esResponse;
    }


    /**
     * 搜索的数据存放到 List<List<String>>
     *
     * @param index
     * @param body
     * @param initialCapacity 可能list存放很多数据,可以指定initialCapacity避免扩容
     * @return
     */
    public List<List<String>> _search(String index, String body, int initialCapacity, Boolean addHeader, Action2<Integer, Integer> progress) {
        List<String> names = mappingServicePlus.getFieldNamesList(index);//获取
        List<List<String>> resultList = new ArrayList<>(initialCapacity);
        if (true == addHeader) {
            resultList.add(names);//添加head
        }
        String result = searchService._search(index, body);
        ESResponse esResponse = ESResponse.parse(result);
        log.info("ES匹配到数量:{}", esResponse.getHits().getTotal());
        esResponse.getHits().getHits().forEach(innerHits -> {
            if (null != progress) {
                progress.call(resultList.size(), esResponse.getHits().getTotal());
            }
            List<String> tmp = new ArrayList<>();
            names.forEach(name -> {
                String value = innerHits.getSource().get(name) == null ? "" : innerHits.getSource().get(name).toString();
                tmp.add(value);
            });
            resultList.add(tmp);//添加row
        });
        return resultList;
    }

    /**
     * 搜索的数据存放到 List<List<String>> (这个是scroll)
     *
     * @param index
     * @param scroll
     * @param body
     * @param initialCapacity 可能list存放很多数据,可以指定initialCapacity避免扩容
     * @return
     */
    public List<List<String>> _search(String index, String scroll, String body, int initialCapacity, Boolean addHeader, Action2<Integer, Integer> progress) {
        List<String> names = mappingServicePlus.getFieldNamesList(index);//获取
        List<List<String>> resultList = new ArrayList<>(initialCapacity);
        if (true == addHeader) {
            resultList.add(names);//添加head
        }
        ESResponse esResponse = this._search(index, scroll, body);
        log.info("ES匹配到数量:{}", esResponse.getHits().getTotal());
        esResponse.getHits().getHits().forEach(innerHits -> {
            if (null != progress) {
                progress.call(resultList.size(), esResponse.getHits().getTotal());
            }
            List<String> tmp = new ArrayList<>();
            names.forEach(name -> {
                String value = innerHits.getSource().get(name) == null ? "" : innerHits.getSource().get(name).toString();
                tmp.add(value);
            });
            resultList.add(tmp);//添加row
        });
        String scrollId = esResponse.getScrollId();
        scrollServicePlus._search(scroll, scrollId, new Consumer<InnerHits>() {
            @Override
            public void accept(InnerHits innerHits) {
                if (null != progress) {
                    progress.call(resultList.size(), esResponse.getHits().getTotal());
                }
                List<String> tmp = new ArrayList<>();
                names.forEach(name -> {
                    String value = innerHits.getSource().get(name) == null ? "" : innerHits.getSource().get(name).toString();
                    tmp.add(value);
                });
                resultList.add(tmp);//添加row
            }
        });
        return resultList;
    }

    /**
     * 搜索的数据存放到 函数式处理（滚动）
     *
     * @param index
     * @param scroll
     * @param body
     * @param consumer 消费
     */
    public void _search(String index, String scroll, String body, Consumer<InnerHits> consumer) {
        ESResponse esResponse = this._search(index, scroll, body);
        log.info("ES匹配到数量:{}", esResponse.getHits().getTotal());
        esResponse.getHits().getHits().forEach(innerHits -> {
            consumer.accept(innerHits);
        });
        String scrollId = esResponse.getScrollId();
        scrollServicePlus._search(scroll, scrollId, new Consumer<InnerHits>() {
            @Override
            public void accept(InnerHits innerHits) {
                consumer.accept(innerHits);
            }
        });
    }

    /**
     * 搜索的数据存放到 函数式处理（不滚动）
     *
     * @param index
     * @param body
     * @param consumer 消费
     */
    public void _search(String index, String body, Consumer<InnerHits> consumer) {
        ESResponse esResponse = this._search(index, body);
        log.info("ES匹配到数量:{}", esResponse.getHits().getTotal());
        esResponse.getHits().getHits().forEach(innerHits -> {
            consumer.accept(innerHits);
        });
    }

    /**
     * terms的查询
     *
     * @param index
     * @param field
     * @param values
     * @return
     * @throws IOException
     */
    public List<List<String>> _search(String index, String scroll, String field, List<String> values) throws IOException {
        Body body = new Body();
        Terms terms = new Terms();
        terms.setField(field);
        terms.setValue(Collections.singletonList(values));
        Query query = new Query();
        Bool bool = new Bool();
        TermLevel termLevel = new TermLevel();
        termLevel.setTerms(Arrays.asList(terms));
        bool.setMust(termLevel);
        query.setBool(bool);
        body.setQuery(query);
        body.setSize(10000);
        String bodyRequest = body.parse();

        List<List<String>> lists = this._search(index, scroll, bodyRequest, 1000, false, null);
        return lists;

    }

}
