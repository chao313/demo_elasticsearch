package demo.elastic.search.feign.plus;

import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.response.ESResponse;
import demo.elastic.search.po.response.InnerHits;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.functions.Func2;

import javax.annotation.Resource;
import java.util.ArrayList;
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


    public ESResponse _search(String index, String scroll, String body) {
        String result = searchService._search(index, scroll, body);
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
    public List<List<String>> _search(String index, String body, int initialCapacity, Func2<Integer, Integer, Void> progress) {
        List<String> names = mappingServicePlus.getFieldNamesList(index);//获取
        List<List<String>> resultList = new ArrayList<>(initialCapacity);
        resultList.add(names);//添加head
        String result = searchService._search(index, body);
        ESResponse esResponse = ESResponse.parse(result);
        esResponse.getHits().getHits().forEach(innerHits -> {
            if (null != progress) {
                progress.call(resultList.size(), esResponse.getHits().getTotal());
            }
            List<String> tmp = new ArrayList<>();
            names.forEach(name -> {
                String value = innerHits.get_source().get(name) == null ? "" : innerHits.get_source().get(name).toString();
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
    public List<List<String>> _search(String index, String scroll, String body, int initialCapacity, Func2<Integer, Integer, Void> progress) {
        List<String> names = mappingServicePlus.getFieldNamesList(index);//获取
        List<List<String>> resultList = new ArrayList<>(initialCapacity);
        resultList.add(names);//添加head
        ESResponse esResponse = this._search(index, scroll, body);
        esResponse.getHits().getHits().forEach(innerHits -> {
            if (null != progress) {
                progress.call(resultList.size(), esResponse.getHits().getTotal());
            }
            List<String> tmp = new ArrayList<>();
            names.forEach(name -> {
                String value = innerHits.get_source().get(name) == null ? "" : innerHits.get_source().get(name).toString();
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
                    String value = innerHits.get_source().get(name) == null ? "" : innerHits.get_source().get(name).toString();
                    tmp.add(value);
                });
                resultList.add(tmp);//添加row
            }
        });
        return resultList;
    }

}
