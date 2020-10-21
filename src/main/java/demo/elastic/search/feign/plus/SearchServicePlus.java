package demo.elastic.search.feign.plus;

import demo.elastic.search.engine.script.ExecuteScript;
import demo.elastic.search.engine.script.impl.JavaScriptExecuteScript;
import demo.elastic.search.feign.AnalyzeService;
import demo.elastic.search.feign.SearchService;
import demo.elastic.search.out.remove.compound.Body;
import demo.elastic.search.out.remove.compound.Query;
import demo.elastic.search.out.remove.compound.base.Bool;
import demo.elastic.search.out.remove.compound.level.TermLevel;
import demo.elastic.search.out.remove.compound.level.base.Terms;
import demo.elastic.search.po.response.ESResponse;
import demo.elastic.search.po.response.InnerHits;
import demo.elastic.search.thread.ThreadLocalFeign;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.functions.Action2;

import javax.annotation.Resource;
import javax.script.ScriptException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

@Service
@Slf4j
public class SearchServicePlus {

    @Autowired
    private MappingServicePlus mappingServicePlus;

    @Autowired
    private ScrollServicePlus scrollServicePlus;


    /**
     * 普通查询
     * {@link #_searchWithoutScrollParam(String, String, Consumer, Consumer)}
     */
    public ESResponse _searchWithoutScrollParam(String index,
                                                String body) {

        return this._searchWithoutScrollParam(index, body, null, null);
    }

    /**
     * 普通查询
     * {@link #_searchWithoutScrollParam(String, String, Consumer, Consumer)}
     */
    public ESResponse _searchWithoutScrollParam(String index,
                                                String body,
                                                Consumer<InnerHits> consumer) {

        return this._searchWithoutScrollParam(index, body, null, consumer);
    }

    /**
     * 普通查询
     *
     * @param index
     * @param body
     */
    public ESResponse _searchWithoutScrollParam(String index,
                                                String body,
                                                Consumer<Integer> totalConsumer,
                                                Consumer<InnerHits> consumer
    ) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService._search(index, body);
        ESResponse esResponse = ESResponse.parse(result);
        if (null != totalConsumer) {
            totalConsumer.accept(esResponse.getHits().getTotal());
        }
        if (null != consumer) {
            esResponse.getHits().getHits().forEach(innerHits -> {
                consumer.accept(innerHits);
            });
        }
        return esResponse;
    }

    /**
     * 普通查询
     * {@link #_searchWithoutScrollParam(String, String, Consumer, Consumer)}
     */
    public ESResponse _searchWithScrollParam(String index,
                                             String scroll,
                                             String body) {

        return this._searchWithScrollParam(index, scroll, body, null, null);
    }

    /**
     * 普通查询
     * {@link #_searchWithoutScrollParam(String, String, Consumer, Consumer)}
     */
    public ESResponse _searchWithScrollParam(String index,
                                             String scroll,
                                             String body,
                                             Consumer<InnerHits> consumer) {

        return this._searchWithScrollParam(index, body, scroll, null, consumer);
    }

    /**
     * 使用scroll参数查询,本身不是滚动
     *
     * @param index
     * @param scroll
     * @param body
     * @param consumer
     * @param totalConsumer
     * @return
     */
    public ESResponse _searchWithScrollParam(String index,
                                             String scroll,
                                             String body,
                                             Consumer<Integer> totalConsumer,
                                             Consumer<InnerHits> consumer
    ) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService._search(index, scroll, body);
        ESResponse esResponse = ESResponse.parse(result);
        if (null != totalConsumer) {
            totalConsumer.accept(esResponse.getHits().getTotal());
        }
        if (null != consumer) {
            esResponse.getHits().getHits().forEach(innerHits -> {
                consumer.accept(innerHits);
            });
        }
        return esResponse;
    }

    /**
     * 查询数据放入 list 中
     * {@link #_searchToList(String, String, Boolean, Action2, ExecuteScript, String)}
     */
    public List<List<String>> _searchToList(String index,
                                            String body,
                                            Boolean addHeader
    ) {
        return this._searchToList(index, body, addHeader, null, null, null);
    }

    /**
     * 查询数据放入 list 中
     * {@link #_searchToList(String, String, Boolean, Action2, ExecuteScript, String)}
     */
    public List<List<String>> _searchToList(String index,
                                            String body,
                                            Boolean addHeader,
                                            Action2<Integer, Integer> progress
    ) {
        return this._searchToList(index, body, addHeader, progress, null, null);
    }


    /**
     * 搜索的数据存放到 List<List<String>>
     * <p>
     * 注意 -> 这里的全部一次性查询入内容，后期可能出现问题
     *
     * @param index
     * @param body
     * @param addHeader     是否添加头
     * @param progress      进度消费者(参数1:处理进度，参数2：匹配到的总值)
     * @param executeScript 脚本执行器
     * @param script        脚本本身
     * @return
     */
    public List<List<String>> _searchToList(String index,
                                            String body,
                                            Boolean addHeader,
                                            Action2<Integer, Integer> progress,
                                            ExecuteScript executeScript,
                                            String script
    ) {
        List<String> names = mappingServicePlus.getFieldNamesList(index);//获取name
        AtomicReference<List<List<String>>> result = new AtomicReference<>();
        AtomicReference<Integer> i = new AtomicReference<>(0);
        AtomicReference<Integer> totalNum = new AtomicReference<>(0);
        this._searchWithoutScrollParam(index, body, total -> {
            result.set(new ArrayList<>(total));
            log.info("ES匹配到数量:{}", total);
            totalNum.set(total);
            if (true == addHeader) {
                result.get().add(names);//添加head
            }
        }, new Consumer<InnerHits>() {
            @SneakyThrows
            @Override
            public void accept(InnerHits innerHits) {
                if (null != progress) {
                    progress.call(i.getAndSet(i.get() + 1), totalNum.get());
                }
                Boolean scriptDeal = null;
                if (null != executeScript) {
                    //执行脚本
                    scriptDeal = executeScript.evalAndFilter(script, innerHits.getSource());
                }
                if (null == scriptDeal || true == scriptDeal) {
                    //脚本没有结果 | 脚本返回的是true
                    List<String> tmp = new ArrayList<>(names.size() * 2);
                    names.forEach(name -> {
                        String value = innerHits.getSource().get(name) == null ? "" : innerHits.getSource().get(name).toString();
                        tmp.add(value);
                    });
                    result.get().add(tmp);//添加row
                }
            }
        });
        return result.get();
    }

    /**
     * {@link SearchServicePlus#_searchToConsumer(java.lang.String, java.lang.String, java.util.function.Consumer, java.util.function.Consumer)}
     */
    public void _searchToConsumer(String index, String body, Consumer<InnerHits> consumer) {
        this._searchToConsumer(index, body, consumer, null);
    }

    /**
     * 搜索的数据存放到 （不滚动）
     *
     * @param index
     * @param body
     * @param consumer      消费 函数式处理
     * @param totalConsumer 消费，获取total
     */
    public void _searchToConsumer(String index, String body, Consumer<InnerHits> consumer, Consumer<Integer> totalConsumer) {
        ESResponse esResponse = this._searchWithoutScrollParam(index, body);
        log.info("ES匹配到数量:{}", esResponse.getHits().getTotal());
        if (null != totalConsumer) {
            totalConsumer.accept(esResponse.getHits().getTotal());
        }
        esResponse.getHits().getHits().forEach(innerHits -> {
            consumer.accept(innerHits);
        });
    }


    /**
     * 搜索的数据存放到 List<List<String>> (这个是scroll)
     * <p>
     * {@link #_searchScrollToList(String, String, String, Boolean, Action2, Consumer, ExecuteScript, String)}
     * <p>
     * 注意:持续性查询会给ES造成压力,因为在ES缓存中有缓存
     *
     * @param index
     * @param scroll
     * @param body
     * @param addHeader 是否添加头
     */
    public List<List<String>> _searchScrollToList(String index,
                                                  String scroll,
                                                  String body,
                                                  Boolean addHeader) {

        return this._searchScrollToList(index, scroll, body, addHeader, null, null, null, null);

    }


    /**
     * 搜索的数据存放到 List<List<String>> (这个是scroll)
     * <p>
     * {@link #_searchScrollToList(String, String, String, Boolean, Action2, Consumer, ExecuteScript, String)}
     *
     * @param index
     * @param scroll
     * @param body
     * @param addHeader 是否添加头
     * @param progress  进度消费者(参数1:处理进度，参数2：匹配到的总值)
     */
    public List<List<String>> _searchScrollToList(String index,
                                                  String scroll,
                                                  String body,
                                                  Boolean addHeader,
                                                  Action2<Integer, Integer> progress) {

        return this._searchScrollToList(index, scroll, body, addHeader, progress, null, null, null);

    }

    /**
     * 搜索的数据存放到 List<List<String>> (这个是scroll)
     * <p>
     * {@link #_searchScrollToList(String, String, String, Boolean, Action2, Consumer, ExecuteScript, String)}
     *
     * @param index
     * @param scroll
     * @param body
     * @param addHeader 是否添加头
     * @param progress  进度消费者(参数1:处理进度，参数2：匹配到的总值)
     */
    public List<List<String>> _searchScrollToList(String index,
                                                  String scroll,
                                                  String body,
                                                  Boolean addHeader,
                                                  Action2<Integer, Integer> progress,
                                                  Consumer<List<List<String>>> resultConsumer) {

        return this._searchScrollToList(index, scroll, body, addHeader, progress, resultConsumer, null, null);

    }


    /**
     * 搜索的数据存放到 List<List<String>> (这个是scroll)
     *
     * @param index
     * @param scroll
     * @param body
     * @param addHeader     是否添加头
     * @param progress      进度消费者(参数1:处理进度，参数2：匹配到的总值)
     * @param executeScript 脚本执行器
     * @param script        脚本本身
     */
    public List<List<String>> _searchScrollToList(String index,
                                                  String scroll,
                                                  String body,
                                                  Boolean addHeader,
                                                  Action2<Integer, Integer> progress,
                                                  Consumer<List<List<String>>> resultConsumer,
                                                  ExecuteScript executeScript,
                                                  String script) {
        List<String> names = mappingServicePlus.getFieldNamesList(index);//获取
//        final List<List<String>>[] resultList = new List[]{new ArrayList<>()};
        AtomicReference<List<List<String>>> resultList = new AtomicReference<>();
        AtomicReference<Integer> i = new AtomicReference<>(0);
        AtomicReference<Integer> totalNum = new AtomicReference<>(0);
        /**
         * 消费者提取出来
         */
        Consumer<InnerHits> consumer = new Consumer<InnerHits>() {
            @SneakyThrows
            @Override
            public void accept(InnerHits innerHits) {
                if (null != progress) {
                    progress.call(i.getAndSet(i.get() + 1), totalNum.get());
                }
                Boolean scriptDeal = null;
                if (null != executeScript && StringUtils.isNotBlank(script)) {
                    //执行脚本
//                    scriptDeal = executeScript.evalAndFilter(script, innerHits.getSource());
                    scriptDeal = JavaScriptExecuteScript.evalAndFilter2(script, innerHits.getSource());
                }
                if (null == scriptDeal || true == scriptDeal) {
                    //脚本没有结果 | 脚本返回的是true
                    List<String> tmp = new ArrayList<>(names.size() * 2);
//                    names.forEach(name -> {
//                        String value = innerHits.getSource().get(name) == null ? "" : innerHits.getSource().get(name).toString();
//                        tmp.add(value);
//                    });
                    names.forEach(name -> {
                        tmp.add(innerHits.getSource().get(name) == null ? "" : innerHits.getSource().get(name).toString());
                    });
//                    log.info("获取的数据:{}", tmp);
                    resultList.get().add(tmp);//添加row
                }
                if (null != resultConsumer) {
                    //处理结果集
                    resultConsumer.accept(resultList.get());
                }
            }
        };
        ESResponse esResponse = this._searchWithScrollParam(index, scroll, body, total -> {
            resultList.set(new ArrayList<>(total * 2));
            log.info("ES匹配到数量:{}", total);
            totalNum.set(total);
            if (null != addHeader && true == addHeader) {
                resultList.get().add(names);//添加head
            }
        }, consumer);
        /**
         * 进行滚动查询
         */
        String scrollId = esResponse.getScrollId();
        scrollServicePlus._search(scroll, scrollId, consumer);
        return resultList.get();
    }


    /**
     * 搜索的数据存放到 函数式处理（滚动）
     * <p>
     * {@link SearchServicePlus#_searchScrollToConsumer(String, String, String, Consumer, Consumer)}
     *
     * @param index
     * @param scroll
     * @param body
     * @param consumer 消费
     */
    public void _searchScrollToConsumer(String index, String scroll, String body, Consumer<InnerHits> consumer) {
        this._searchScrollToConsumer(index, scroll, body, null, consumer);
    }


    /**
     * 搜索的数据存放到 函数式处理（滚动）
     *
     * @param index
     * @param scroll
     * @param body
     * @param consumer 消费
     */
    public void _searchScrollToConsumer(String index,
                                        String scroll,
                                        String body,
                                        Consumer<Integer> totalConsumer,
                                        Consumer<InnerHits> consumer) {
        ESResponse esResponse = this._searchWithScrollParam(index, scroll, body);
        log.info("ES匹配到数量:{}", esResponse.getHits().getTotal());
        if (null != totalConsumer) {
            totalConsumer.accept(esResponse.getHits().getTotal());
        }
        esResponse.getHits().getHits().forEach(consumer::accept);
        String scrollId = esResponse.getScrollId();
        scrollServicePlus._search(scroll, scrollId, innerHits -> consumer.accept(innerHits));
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
    public List<List<String>> _searchToListTerms(String index,
                                                 String scroll,
                                                 String field,
                                                 List<String> values,
                                                 ExecuteScript executeScript,
                                                 String script) throws IOException {
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

        List<List<String>> lists = this._searchScrollToList(index, scroll, bodyRequest, null, null, null, executeScript, script);
        return lists;

    }

    /**
     * terms的查询 -> 函数式处理
     *
     * @param index
     * @param field
     * @param values
     * @return
     * @throws IOException
     */
    public void _searchScrollToListTerms(String index, String scroll, String field, List<String> values, Consumer<InnerHits> consumer) throws IOException {
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

        this._searchScrollToConsumer(index, scroll, bodyRequest, consumer);
    }

}
