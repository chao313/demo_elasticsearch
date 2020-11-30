package demo.elastic.search.feign.plus;

import com.alibaba.fastjson.JSON;
import demo.elastic.search.engine.script.ExecuteScript;
import demo.elastic.search.engine.script.impl.JavaScriptExecuteScript;
import demo.elastic.search.feign.AnalyzeService;
import demo.elastic.search.feign.ScrollService;
import demo.elastic.search.feign.SearchService;
import demo.elastic.search.out.remove.compound.Body;
import demo.elastic.search.out.remove.compound.Query;
import demo.elastic.search.out.remove.compound.base.Bool;
import demo.elastic.search.out.remove.compound.level.TermLevel;
import demo.elastic.search.out.remove.compound.level.base.Terms;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.response.ESResponse;
import demo.elastic.search.po.response.InnerHits;
import demo.elastic.search.thread.ThreadLocalFeign;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import rx.functions.Action2;

import javax.annotation.Resource;
import javax.script.ScriptException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@Slf4j
public class SearchServicePlus {

    @Autowired
    private MappingServicePlus mappingServicePlus;

    @Autowired
    private ScrollServicePlus scrollServicePlus;

    @Autowired
    private SearchService searchService;

    @Autowired
    private ScrollService scrollService;


    /**
     * 普通查询
     * {@link #_searchWithoutScrollParam(String, String, Consumer, Function)}
     */
    public ESResponse _searchWithoutScrollParam(String index,
                                                String body) {

        return this._searchWithoutScrollParam(index, body, null, null);
    }

    /**
     * 普通查询
     * {@link #_searchWithoutScrollParam(String, String, Consumer, Function)}
     */
    public ESResponse _searchWithoutScrollParam(String index,
                                                String body,
                                                Function<InnerHits, Boolean> function) {

        return this._searchWithoutScrollParam(index, body, null, function);
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
                                                Function<InnerHits, Boolean> function
    ) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService._search(index, body);
        ESResponse esResponse = ESResponse.parse(result);
        if (null != totalConsumer) {
            totalConsumer.accept(esResponse.getHits().getTotal());
        }
        if (null != function) {
            esResponse.getHits().getHits().forEach(innerHits -> {
                function.apply(innerHits);
            });
        }
        return esResponse;
    }

    /**
     * 普通查询
     * {@link #_searchWithoutScrollParam(String, String, Consumer, Function)}
     */
    public ESResponse _searchWithScrollParam(String index,
                                             String scroll,
                                             String body) {

        return this._searchWithScrollParam(index, scroll, body, null, null);
    }

    /**
     * 普通查询
     * {@link #_searchWithoutScrollParam(String, String, Consumer, Function)}
     */
    public ESResponse _searchWithScrollParam(String index,
                                             String scroll,
                                             String body,
                                             Function<InnerHits, Boolean> function) {

        return this._searchWithScrollParam(index, body, scroll, null, function);
    }

    /**
     * 使用scroll参数查询,本身不是滚动
     *
     * @param index
     * @param scroll
     * @param body
     * @param function
     * @param totalConsumer
     * @return
     */
    public ESResponse _searchWithScrollParam(String index,
                                             String scroll,
                                             String body,
                                             Consumer<Integer> totalConsumer,
                                             Function<InnerHits, Boolean> function
    ) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService._search(index, scroll, body);
        ESResponse esResponse = ESResponse.parse(result);
        if (null != totalConsumer) {
            totalConsumer.accept(esResponse.getHits().getTotal());
        }
        Boolean isGoon = true;//是否继续检索
        if (null != function) {
            for (InnerHits innerHits : esResponse.getHits().getHits()) {
                isGoon = function.apply(innerHits);
                if (isGoon == false) {
                    //如果不继续 跳出当前循环
                    break;
                }
            }
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
        }, new Function<InnerHits, Boolean>() {
            @SneakyThrows
            @Override
            public Boolean apply(InnerHits innerHits) {
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
                return true;
            }
        });
        return result.get();
    }

    /**
     * {@link SearchServicePlus#_searchToConsumer(java.lang.String, java.lang.String, java.util.function.Function, java.util.function.Consumer)}
     */
    public void _searchToConsumer(String index, String body, Function<InnerHits, Boolean> function) {
        this._searchToConsumer(index, body, function, null);
    }

    /**
     * 搜索的数据存放到 （不滚动）
     *
     * @param index
     * @param body
     * @param function      消费 函数式处理
     * @param totalConsumer 消费，获取total
     */
    public void _searchToConsumer(String index, String body, Function<InnerHits, Boolean> function, Consumer<Integer> totalConsumer) {
        ESResponse esResponse = this._searchWithoutScrollParam(index, body);
        log.info("ES匹配到数量:{}", esResponse.getHits().getTotal());
        if (null != totalConsumer) {
            totalConsumer.accept(esResponse.getHits().getTotal());
        }
        esResponse.getHits().getHits().forEach(innerHits -> {
            function.apply(innerHits);
        });
    }


    /**
     * 搜索的数据存放到 List<List<String>> (这个是scroll)
     * <p>
     * {@link #_searchScrollToList(String, String, String, Boolean, Action2, Function, ExecuteScript, String)}
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
     * {@link #_searchScrollToList(String, String, String, Boolean, Action2, Function, ExecuteScript, String)}
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
     * {@link #_searchScrollToList(String, String, String, Boolean, Action2, Function, ExecuteScript, String)}
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
                                                  Function<List<List<String>>, Boolean> resultConsumer) {

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
                                                  Function<List<List<String>>, Boolean> resultFunction,
                                                  ExecuteScript executeScript,
                                                  String script) {
        List<String> names = new ArrayList<>();
        SearchSourceBuilder tmp = JSON.parseObject(JSON.toJSON(body).toString(), SearchSourceBuilder.class);
        if (tmp.get_source().contains("*")) {
            names.addAll(mappingServicePlus.getFieldNamesList(index));//获取
        } else {
            names.addAll(tmp.get_source());
        }

        AtomicReference<List<List<String>>> resultList = new AtomicReference<>();
        AtomicReference<Integer> i = new AtomicReference<>(0);
        AtomicReference<Integer> totalNum = new AtomicReference<>(0);
        /**
         * 消费者提取出来
         */
        Function<InnerHits, Boolean> function = new Function<InnerHits, Boolean>() {
            @SneakyThrows
            @Override
            public Boolean apply(InnerHits innerHits) {
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
                if (null != resultFunction) {
                    //处理结果集 如果不为空，就继续处理
                    return resultFunction.apply(resultList.get());
                }
                return true;
            }
        };
        ESResponse esResponse = this._searchWithScrollParam(index, scroll, body, total -> {
            resultList.set(new ArrayList<>(total));
            log.info("ES匹配到数量:{}", total);
            totalNum.set(total);
            if (null != addHeader && true == addHeader) {
                resultList.get().add(names);//添加head
            }
        }, function);
        /**
         * 进行滚动查询
         */
        String scrollId = esResponse.getScrollId();
        if (esResponse.getHits().getTotal() > 0) {
            //添加数量判断
            scrollServicePlus._search(scroll, scrollId, function);
        }
        return resultList.get();
    }

    /**
     * 搜索的数据存放到 函数式处理（滚动）
     * <p>
     * {@link SearchServicePlus#_searchScrollToConsumer(String, String, String, Consumer, Function)}
     *
     * @param index
     * @param scroll
     * @param body
     * @param function 消费
     */
    public void _searchScrollToConsumer(String index, String scroll, String body, Function<InnerHits, Boolean> function) {
        this._searchScrollToConsumer(index, scroll, body, null, function);
    }


    /**
     * 搜索的数据存放到 函数式处理（滚动）
     *
     * @param index
     * @param scroll
     * @param body
     * @param function 消费
     */
    public void _searchScrollToConsumer(String index,
                                        String scroll,
                                        String body,
                                        Consumer<Integer> totalConsumer,
                                        Function<InnerHits, Boolean> function) {
        ESResponse esResponse = this._searchWithScrollParam(index, scroll, body);
        log.info("ES匹配到数量:{}", esResponse.getHits().getTotal());
        if (null != totalConsumer) {
            totalConsumer.accept(esResponse.getHits().getTotal());
        }
        esResponse.getHits().getHits().forEach(function::apply);
        String scrollId = esResponse.getScrollId();
        scrollServicePlus._search(scroll, scrollId, innerHits -> {
            return function.apply(innerHits);
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
    public void _searchScrollToListTerms(String index, String scroll, String field, List<String> values, Function<InnerHits, Boolean> function) throws IOException {
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

        this._searchScrollToConsumer(index, scroll, bodyRequest, function);
    }

    /**
     * 检索+滚动
     */
    public void _searchAndScroll(String index,
                                 String scroll,
                                 SearchSourceBuilder searchSourceBuilder,
                                 Consumer<InnerHits> consumer) {
        String result = searchService._search(index, scroll, searchSourceBuilder);
        ESResponse parse = ESResponse.parse(result);
        parse.getHits().getHits().forEach(innerHits -> {
            consumer.accept(innerHits);
        });
        do {
            String scrollId = parse.getScrollId();
            result = scrollService._search("10m", scrollId);
            parse = ESResponse.parse(result);
            parse.getHits().getHits().forEach(innerHits -> {
                consumer.accept(innerHits);
            });
        } while (parse.getHits().getHits().size() > 0);

    }

    /**
     * 检索+滚动
     */
    public void _search(String index,
                        SearchSourceBuilder searchSourceBuilder,
                        Consumer<InnerHits> consumer) {
        String result = searchService._search(index, searchSourceBuilder);
        ESResponse parse = ESResponse.parse(result);
        parse.getHits().getHits().forEach(innerHits -> {
            consumer.accept(innerHits);
        });
    }

}
