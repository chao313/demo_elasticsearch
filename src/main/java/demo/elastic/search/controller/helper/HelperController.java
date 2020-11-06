package demo.elastic.search.controller.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import demo.elastic.search.feign.plus.MappingServicePlus;
import demo.elastic.search.feign.plus.SearchServicePlus;
import demo.elastic.search.framework.Response;
import demo.elastic.search.out.db.mysql.service.DBService;
import demo.elastic.search.out.resource.service.ResourceService;
import demo.elastic.search.po.helper.DSLHelper;
import demo.elastic.search.po.helper.DSLHelperPlus;
import demo.elastic.search.po.request.QueryBuilders;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.aggs.VoidAggs;
import demo.elastic.search.po.request.dsl.compound.BoolQuery;
import demo.elastic.search.service.RedisService;
import demo.elastic.search.thread.ThreadPoolExecutorService;
import demo.elastic.search.util.DateUtil;
import demo.elastic.search.util.ExcelUtil;
import demo.elastic.search.util.SQLMysqlCalciteParseUtils;
import demo.elastic.search.util.SQLOracleCalciteParseUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import org.apache.calcite.sql.SqlBasicCall;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static demo.elastic.search.util.ExcelUtil.percent;
import static demo.elastic.search.util.SQLOracleCalciteParseUtils.getWhereSimpleSqlBasicCall;

/**
 * helper使用
 */
@Slf4j
@RequestMapping(value = "/HelperController")
@RestController
public class HelperController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private SearchServicePlus searchServicePlus;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private DBService dbService;

    @Autowired
    private MappingServicePlus mappingServicePlus;

    @Autowired
    private ThreadPoolExecutorService threadPoolExecutorService;


    private static final Integer LIMIT_EXCEL = 500000;
    private static final Integer LIMIT_DB = 50000;

    @ApiOperation(value = "请求结构体转换成ES结构体")
    @PostMapping(value = "/DSLHelper")
    public Response DSLHelper(@RequestBody DSLHelper dslHelper) throws JsonProcessingException {

        BoolQuery boolQuery = QueryBuilders.boolQuery();


        SearchSourceBuilder<BoolQuery, VoidAggs> request = new SearchSourceBuilder<>();

        dslHelper.getFilter().getExists().forEach(exists -> {
            boolQuery.filter(QueryBuilders.existsQuery(exists.getField()));
        });
        dslHelper.getFilter().getTerm().forEach(term -> {
            boolQuery.filter(QueryBuilders.termQuery(term.getField(), term.getValue()));
        });
        dslHelper.getFilter().getTerms().forEach(terms -> {
            boolQuery.filter(QueryBuilders.termsQuery(terms.getField(), terms.getValue()));
        });
        dslHelper.getFilter().getRange().forEach(range -> {
            boolQuery.filter(QueryBuilders.rangeQuery(range.getField(), range.getGte(), range.getGt(), range.getLte(), range.getLt()));
        });
        dslHelper.getFilter().getRegexp().forEach(regexp -> {
            boolQuery.filter(QueryBuilders.regexpQuery(regexp.getField(), regexp.getValue()));
        });
        dslHelper.getFilter().getPrefix().forEach(prefix -> {
            boolQuery.filter(QueryBuilders.prefixQuery(prefix.getField(), prefix.getValue()));
        });
        dslHelper.getFilter().getWildcard().forEach(wildcard -> {
            boolQuery.filter(QueryBuilders.wildcardQuery(wildcard.getField(), wildcard.getValue()));
        });

        dslHelper.getFilter().getFuzzy().forEach(fuzzy -> {
            boolQuery.filter(QueryBuilders.fuzzyQuery(fuzzy.getField(), fuzzy.getValue()));
        });
        if (null != dslHelper.getFilter().getIds() && null != dslHelper.getFilter().getIds().getValue() && dslHelper.getFilter().getIds().getValue().size() > 0) {
            boolQuery.filter(QueryBuilders.idsQuery(dslHelper.getFilter().getIds().getValue()));
        }
        //must
        dslHelper.getMust().getExists().forEach(exists -> {
            boolQuery.must(QueryBuilders.existsQuery(exists.getField()));
        });
        dslHelper.getMust().getTerm().forEach(term -> {
            boolQuery.must(QueryBuilders.termQuery(term.getField(), term.getValue()));
        });
        dslHelper.getMust().getTerms().forEach(terms -> {
            boolQuery.must(QueryBuilders.termsQuery(terms.getField(), terms.getValue()));
        });
        dslHelper.getMust().getRange().forEach(range -> {
            boolQuery.must(QueryBuilders.rangeQuery(range.getField(), range.getGte(), range.getGt(), range.getLte(), range.getLt()));
        });
        dslHelper.getMust().getRegexp().forEach(regexp -> {
            boolQuery.must(QueryBuilders.regexpQuery(regexp.getField(), regexp.getValue()));
        });
        dslHelper.getMust().getPrefix().forEach(prefix -> {
            boolQuery.must(QueryBuilders.prefixQuery(prefix.getField(), prefix.getValue()));
        });
        dslHelper.getMust().getWildcard().forEach(wildcard -> {
            boolQuery.must(QueryBuilders.wildcardQuery(wildcard.getField(), wildcard.getValue()));
        });

        dslHelper.getMust().getFuzzy().forEach(fuzzy -> {
            boolQuery.must(QueryBuilders.fuzzyQuery(fuzzy.getField(), fuzzy.getValue()));
        });
        if (null != dslHelper.getMust().getIds() && null != dslHelper.getMust().getIds().getValue() && dslHelper.getMust().getIds().getValue().size() > 0) {
            boolQuery.must(QueryBuilders.idsQuery(dslHelper.getFilter().getIds().getValue()));
        }
        //must_not
        dslHelper.getMust_not().getExists().forEach(exists -> {
            boolQuery.must_not(QueryBuilders.existsQuery(exists.getField()));
        });
        dslHelper.getMust_not().getTerm().forEach(term -> {
            boolQuery.must_not(QueryBuilders.termQuery(term.getField(), term.getValue()));
        });
        dslHelper.getMust_not().getTerms().forEach(terms -> {
            boolQuery.must_not(QueryBuilders.termsQuery(terms.getField(), terms.getValue()));
        });
        dslHelper.getMust_not().getRange().forEach(range -> {
            boolQuery.must_not(QueryBuilders.rangeQuery(range.getField(), range.getGte(), range.getGt(), range.getLte(), range.getLt()));
        });
        dslHelper.getMust_not().getRegexp().forEach(regexp -> {
            boolQuery.must_not(QueryBuilders.regexpQuery(regexp.getField(), regexp.getValue()));
        });
        dslHelper.getMust_not().getPrefix().forEach(prefix -> {
            boolQuery.must_not(QueryBuilders.prefixQuery(prefix.getField(), prefix.getValue()));
        });
        dslHelper.getMust_not().getWildcard().forEach(wildcard -> {
            boolQuery.must_not(QueryBuilders.wildcardQuery(wildcard.getField(), wildcard.getValue()));
        });

        dslHelper.getMust_not().getFuzzy().forEach(fuzzy -> {
            boolQuery.must_not(QueryBuilders.fuzzyQuery(fuzzy.getField(), fuzzy.getValue()));
        });
        if (null != dslHelper.getMust_not().getIds() && null != dslHelper.getMust_not().getIds().getValue() && dslHelper.getMust_not().getIds().getValue().size() > 0) {
            boolQuery.must_not(QueryBuilders.idsQuery(dslHelper.getFilter().getIds().getValue()));
        }
        //should
        dslHelper.getShould().getExists().forEach(exists -> {
            boolQuery.should(QueryBuilders.existsQuery(exists.getField()));
        });
        dslHelper.getShould().getTerm().forEach(term -> {
            boolQuery.should(QueryBuilders.termQuery(term.getField(), term.getValue()));
        });
        dslHelper.getShould().getTerms().forEach(terms -> {
            boolQuery.should(QueryBuilders.termsQuery(terms.getField(), terms.getValue()));
        });
        dslHelper.getShould().getRange().forEach(range -> {
            boolQuery.should(QueryBuilders.rangeQuery(range.getField(), range.getGte(), range.getGt(), range.getLte(), range.getLt()));
        });
        dslHelper.getShould().getRegexp().forEach(regexp -> {
            boolQuery.should(QueryBuilders.regexpQuery(regexp.getField(), regexp.getValue()));
        });
        dslHelper.getShould().getPrefix().forEach(prefix -> {
            boolQuery.should(QueryBuilders.prefixQuery(prefix.getField(), prefix.getValue()));
        });
        dslHelper.getShould().getWildcard().forEach(wildcard -> {
            boolQuery.should(QueryBuilders.wildcardQuery(wildcard.getField(), wildcard.getValue()));
        });

        dslHelper.getShould().getFuzzy().forEach(fuzzy -> {
            boolQuery.should(QueryBuilders.fuzzyQuery(fuzzy.getField(), fuzzy.getValue()));
        });
        if (null != dslHelper.getShould().getIds() && null != dslHelper.getShould().getIds().getValue() && dslHelper.getShould().getIds().getValue().size() > 0) {
            boolQuery.should(QueryBuilders.idsQuery(dslHelper.getFilter().getIds().getValue()));
        }
        JsonMapper jsonMapper = new JsonMapper();
        String s = StringEscapeUtils.unescapeJavaScript(boolQuery.getRequestBody());//去除双\\
        return Response.Ok(jsonMapper.readTree(s));
    }

    @ApiOperation(value = "导出全部的查询结果(收编入ES体系)")
    @PostMapping(value = "/_search/outputToExcel/{index}")
    public Response _searchOutputToExcel(
            @ApiParam(defaultValue = "tb_object_0088") @PathVariable(value = "index") String index,
            @ApiParam(value = "scroll的有效时间,允许为空(e.g. 1m 1d)") @RequestParam(value = "scroll") String scroll,
            @ApiParam(value = "导出的size(-1代表全部)") @RequestParam(value = "outPutSize", required = false) Integer outPutSize,
            @RequestBody String body,
            @ApiParam(hidden = true) @RequestHeader(value = "host") String host,
            HttpServletRequest httpServletRequest
    ) throws IOException, IllegalAccessException {
        List<String> filesNames = new ArrayList<>();//文件名称
        List<List<String>> lists = new ArrayList<>();
        AtomicReference<Integer> sum = new AtomicReference<>(0);
        lists = searchServicePlus._searchScrollToList(index, scroll, body, true, (size, total) -> {
            log.info("读取进度:{}/{}->{}", size, total, percent(size, total));
        }, new Function<List<List<String>>, Boolean>() {
            @SneakyThrows
            @Override
            public Boolean apply(List<List<String>> lists) {
                if (lists.size() >= LIMIT_EXCEL) {
                    String fileName = index + DateUtil.getNow() + ".xlsx";
                    filesNames.add(fileName);
                    File file = resourceService.addNewFile(fileName);
                    ExcelUtil.writeListSXSS(lists, new FileOutputStream(file), (line, size) -> log.info("写入进度:{}/{}->{}", line, size, percent(line, size)));
                    sum.set(sum.get() + lists.size());
                    lists.clear();
                }
                //限制导出大小
                if (outPutSize != -1) {
                    if (sum.get() >= outPutSize) {
                        //如果大于导出数据 -> 停止
                        return false;
                    }
                }
                sum.set(sum.get() + 1);//每次接收一条记录 只需要+1
                return true;
            }
        });

        if (lists.size() > 0) {
            String fileName = index + DateUtil.getNow() + ".xlsx";
            filesNames.add(fileName);
            File file = resourceService.addNewFile(fileName);
            ExcelUtil.writeListSXSS(lists, new FileOutputStream(file), (line, size) -> log.info("写入进度:{}/{}->{}", line, size, percent(line, size)));
            lists.clear();
        }

        List<String> urls = new ArrayList<>();
        filesNames.forEach(path -> {
            String url = "http://" + host + resourceService.getContextPath() + "/ResourceController/downloadByFileName?fileName=" + path;
            urls.add(url);
        });
        return Response.Ok(urls);
    }

    @ApiOperation(value = "导出全部的查询到DB(收编入ES体系)")
    @PostMapping(value = "/_search/outputToDB/{index}")
    public Response _searchOutputToDB(
            @ApiParam(defaultValue = "tb_object_0088")
            @PathVariable(value = "index") String index,
            @ApiParam(value = "scroll的有效时间,允许为空(e.g. 1m 1d)")
            @RequestParam(value = "scroll", required = true) String scroll,
            @ApiParam(value = "导出的size(-1代表全部)") @RequestParam(value = "outPutSize", required = false) Integer outPutSize,
            @RequestBody String body
    ) throws Exception {
        String tableName = null;
        tableName = index;
        if (index.matches("comstore_(.*)")) {
            tableName = index.replaceAll("comstore_(.*)", "$1");
        }
        if (index.matches("comstore_(.*)_v\\d+")) {
            tableName = index.replaceAll("comstore_(.*)_v\\d+", "$1");
        }
        if (index.matches("(.*)_v\\d+")) {
            tableName = index.replaceAll("(.*)_v\\d+", "$1");
        }
        if (index.matches("(.*)?_ext")) {
            tableName = index.replaceAll("(.*)?_ext", "$1");
        }
        if (index.matches("comstore_(.*)?_ext")) {
            tableName = index.replaceAll("comstore_(.*)?_ext", "$1");
        }

        String targetTable = tableName + "_" + DateUtil.getNow();
        dbService.cloneTableStruct(tableName, targetTable);//创建新的表
        List<String> fieldNames = mappingServicePlus.getFieldNamesList(index);//获取name
        fieldNames.remove("row_feature");
        fieldNames.remove("ES_MOD_TIME");
        List<List<String>> lists = new ArrayList<>();
        AtomicReference<Integer> i = new AtomicReference<>(0);
        AtomicReference<Integer> sum = new AtomicReference<>(0);

        lists = searchServicePlus._searchScrollToList(index, scroll, body, false, (size, total) -> {
            log.info("读取进度:{}/{}->{}", size + 1, total, percent(size + 1, total));
        }, new Function<List<List<String>>, Boolean>() {
            @SneakyThrows
            @Override
            public Boolean apply(List<List<String>> lists) {
                if (lists.size() >= LIMIT_DB) {
                    List<List<String>> tmp = new ArrayList<>(lists);
                    threadPoolExecutorService.addWork(new Runnable() {
                        @Override
                        public void run() {
                            dbService.batchInsert(targetTable, tmp, fieldNames);
                        }
                    });
                    lists.clear();
                }
                //限制导出大小
                if (outPutSize != -1) {
                    if (sum.get() >= outPutSize) {
                        //如果大于导出数据 -> 停止
                        return false;
                    }
                }
                sum.set(sum.get() + 1);//每次接收一条记录 只需要+1
                return true;
            }
        });

        if (lists.size() > 0) {
            List<List<String>> tmp = new ArrayList<>(lists);
            threadPoolExecutorService.addWork(new Runnable() {
                @Override
                public void run() {
                    dbService.batchInsert(targetTable, tmp, fieldNames);
                }
            });
            lists.clear();
        }

        threadPoolExecutorService.waitComplete();
        log.info("提取完成,return");
        return Response.Ok(Arrays.asList(targetTable));
    }

    /**
     * SELECT * FROM tb_object_0088 WHERE
     * F1_0088 IS NOT NULL AND F2_0088 IS NULL
     * AND F1_0088 = '1' AND F1_0088 <> '1'
     * AND F1_0088 IN ('1','2') AND F1_0088 NOT IN ('1','2')
     * AND F3_0088 BETWEEN 30 AND 40
     * AND F3_0088 >= 30 AND F3_0088 <= 30
     * AND REGEXP_LIKE(F4_0088,'\d*') AND  NOT REGEXP_LIKE(F4_0088,'\d*')
     * AND F4_0088 LIKE 'St%'  AND F4_0088 NOT LIKE 'St%'
     * <p>
     * <p>
     * SELECT * FROM tb_object_0088 WHERE
     * F1_0088 IS NOT NULL AND F2_0088 IS NULL
     * AND F1_0088 = '1' AND F1_0088 <> '1'
     * AND F1_0088 IN ('1') AND F1_0088 NOT IN ('1','2')
     * AND F3_0088 BETWEEN 30 AND 40
     * AND F3_0088 >= '30' AND F3_0088 <= '40'
     * AND REGEXP_LIKE(F4_0088,'\d*') AND  NOT REGEXP_LIKE(F4_0088,'\d*')
     * AND F4_0088 LIKE 'St%'  AND F4_0088 NOT LIKE 'St%'
     *
     * <pre>
     *  测试用例
     *  :
     * SELECT * FROM "tb_object_0088" WHERE
     * "F1_0088" IS NOT NULL AND "F2_0088" IS NULL
     * AND "F1_0088" = '1' AND "F1_0088" <> '1'
     * AND "F1_0088" IN ('1','2') AND "F1_0088" NOT IN ('1','2')
     * AND "F3_0088" BETWEEN 30 AND 40
     * AND "F3_0088" >= 30 AND F3_0088 <= 30
     * AND REGEXP_LIKE("F2_0088",'[\u4e00-\u9fa5]+') AND  NOT REGEXP_LIKE("F1_0088",'[0-9]*')
     * AND "F4_0088" LIKE 'St%'  AND "F4_0088" NOT LIKE 'St%'
     *
     *
     * SELECT * FROM tb_object_0088 WHERE F1_0088 = '1'
     *
     * SELECT * FROM tb_object_0088 WHERE F1_0088 <> '1'
     *
     * SELECT * FROM tb_object_0088 WHERE F1_0088 LIKE '1*'
     *
     * SELECT * FROM tb_object_0088 WHERE F1_0088 NOT LIKE '1*'
     *
     * SELECT * FROM tb_object_0088 WHERE F1_0088 IS NULL
     *
     * SELECT * FROM tb_object_0088 WHERE F1_0088 IS NOT NULL
     *
     * SELECT * FROM tb_object_0088 WHERE F1_0088 IN ('1','2')
     *
     * SELECT * FROM tb_object_0088 WHERE F1_0088 NOT IN ('1','2')
     *
     * SELECT * FROM tb_object_0088 WHERE F10_0088 <= 1
     *
     * SELECT * FROM tb_object_0088 WHERE F10_0088 >= 50
     *
     * SELECT * FROM tb_object_0088 WHERE F10_0088 BETWEEN 1 AND 49
     *
     * SELECT * FROM tb_object_0088 WHERE  REGEXP_LIKE(F1_0088,'1.*')
     *
     * SELECT * FROM tb_object_0088 WHERE NOT REGEXP_LIKE(F1_0088,'1.*')
     *
     * </pre>
     *
     * @param sql
     * @return
     * @throws JsonProcessingException
     * @throws SqlParseException
     */
    @ApiOperation(value = "SQL转换成ES结构体")
    @PostMapping(value = "/SQLToEsHelper")
    public Response SQLToEsHelper(@RequestBody String sql) throws JsonProcessingException, SqlParseException, JSQLParserException {

        DSLHelper dslHelper = new DSLHelper();
        SqlKind kind = SQLOracleCalciteParseUtils.getKind(sql);
        if (!kind.equals(SqlKind.SELECT) && !kind.equals(SqlKind.ORDER_BY)) {
            throw new RuntimeException("只支持 select/order by 类型,当前类型是:" + kind);
        }
        List<String> simpleSelectList = SQLOracleCalciteParseUtils.getSimpleSelectList(sql);
        List<SqlBasicCall> sqlBasicCalls = getWhereSimpleSqlBasicCall(sql);
        for (SqlBasicCall sqlBasicCall : sqlBasicCalls) {
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.IS_NOT_NULL)) {
                //处理null
                DSLHelper.Exists exists = new DSLHelper.Exists();
                exists.setField(sqlBasicCall.getOperands()[0].toString());
                dslHelper.must(exists);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.EQUALS)) {
                //处理 =
                DSLHelper.Term term = new DSLHelper.Term();
                term.setField(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                term.setValue(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                dslHelper.must(term);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.IN)) {
                //处理 in
                DSLHelper.Terms terms = new DSLHelper.Terms();
                terms.setField(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                if (sqlBasicCall.getOperands()[1] instanceof SqlNodeList) {
                    SqlNodeList sqlNodeList = (SqlNodeList) sqlBasicCall.getOperands()[1];
                    List<String> values = new ArrayList<>();
                    sqlNodeList.getList().forEach(sqlNode -> {
                        values.add(SQLOracleCalciteParseUtils.getValue(sqlNode));
                    });
                    terms.setValue(values);
                } else {
                    throw new RuntimeException("In 后不是数组");
                }
                dslHelper.must(terms);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.BETWEEN)) {
                //处理 between
                DSLHelper.Range range = new DSLHelper.Range();
                range.setField(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                range.setGte(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                range.setLte(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[2]));
                dslHelper.must(range);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.GREATER_THAN_OR_EQUAL)) {
                //处理 >=
                DSLHelper.Range range = new DSLHelper.Range();
                range.setField(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                range.setGte(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                dslHelper.must(range);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.GREATER_THAN)) {
                //处理 >=
                DSLHelper.Range range = new DSLHelper.Range();
                range.setField(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                range.setGt(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                dslHelper.must(range);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.LESS_THAN_OR_EQUAL)) {
                //处理 <=
                DSLHelper.Range range = new DSLHelper.Range();
                range.setField(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                range.setLte(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                dslHelper.must(range);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.LESS_THAN)) {
                //处理 <=
                DSLHelper.Range range = new DSLHelper.Range();
                range.setField(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                range.setLt(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                dslHelper.must(range);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.LIKE)) {
                //处理 like -> 注意类型都是 LIKE
                if (sqlBasicCall.getOperator().getName().equalsIgnoreCase("LIKE")) {
                    DSLHelper.Wildcard wildcard = new DSLHelper.Wildcard();
                    wildcard.setField(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                    wildcard.setValue(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                    dslHelper.must(wildcard);
                }

            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.OTHER_FUNCTION)) {
                //处理 regex_like 这里当做函数来处理
                if (sqlBasicCall.getOperator().getName().equalsIgnoreCase("REGEXP_LIKE")) {
                    DSLHelper.Regexp regexp = new DSLHelper.Regexp();
                    regexp.setField(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                    regexp.setValue(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                    dslHelper.must(regexp);
                } else {
                    throw new RuntimeException("出现不支持函数:" + sqlBasicCall.getOperator().getName());
                }
            }
            //处理 not

            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.IS_NULL)) {
                //处理 not null
                //处理null
                DSLHelper.Exists exists = new DSLHelper.Exists();
                exists.setField(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                dslHelper.must_not(exists);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.NOT_EQUALS)) {
                //处理 <>
                DSLHelper.Term term = new DSLHelper.Term();
                term.setField(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                term.setValue(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                dslHelper.must_not(term);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.NOT_IN)) {
                //处理 not in
                DSLHelper.Terms terms = new DSLHelper.Terms();
                terms.setField(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                if (sqlBasicCall.getOperands()[1] instanceof SqlNodeList) {
                    SqlNodeList sqlNodeList = (SqlNodeList) sqlBasicCall.getOperands()[1];
                    List<String> values = new ArrayList<>();
                    sqlNodeList.getList().forEach(sqlNode -> {
                        values.add(SQLOracleCalciteParseUtils.getValue(sqlNode));
                    });
                    terms.setValue(values);
                } else {
                    throw new RuntimeException("In 后不是数组");
                }
                dslHelper.must_not(terms);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.LIKE)) {
                //处理 not like -> 注意类型都是 LIKE
                if (sqlBasicCall.getOperator().getName().equalsIgnoreCase("NOT LIKE")) {
                    DSLHelper.Wildcard wildcard = new DSLHelper.Wildcard();
                    wildcard.setField(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                    wildcard.setValue(SQLOracleCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                    dslHelper.must_not(wildcard);
                }
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.NOT)) {
                //处理 NOT 先判断是NOT类型。
                //处理 regex_like 这里当做函数来处理
                Arrays.stream(sqlBasicCall.getOperands()).forEach(sqlNode -> {
                    if (sqlNode instanceof SqlBasicCall) {
                        SqlBasicCall sqlBasicCallTmp = (SqlBasicCall) sqlNode;
                        if (sqlBasicCallTmp.getOperator().getName().equalsIgnoreCase("REGEXP_LIKE")) {
                            DSLHelper.Regexp regexp = new DSLHelper.Regexp();
                            regexp.setField(SQLOracleCalciteParseUtils.getValue(sqlBasicCallTmp.getOperands()[0]));
                            regexp.setValue(SQLOracleCalciteParseUtils.getValue(sqlBasicCallTmp.getOperands()[1]));
                            dslHelper.must_not(regexp);
                        } else {
                            throw new RuntimeException("出现不支持函数:" + sqlBasicCall.getOperator().getName());
                        }
                    }
                });
            }

        }

        SqlNode from = SQLOracleCalciteParseUtils.getFrom(sql);
        DSLHelperPlus dslHelperPlus = new DSLHelperPlus();
        dslHelperPlus.setDslHelper(dslHelper);
        dslHelperPlus.setIndex(from.toString());
        dslHelperPlus.setFields(simpleSelectList);

        Map<String, String> sqlOrderMap = SQLOracleCalciteParseUtils.getSqlOrderMap(sql);
        dslHelperPlus.getSort().add(sqlOrderMap);
        return Response.Ok(dslHelperPlus);

    }

    /**
     * SELECT F2_0088,F1_0088 FROM  tb_object_0088  WHERE  F1_0088  IS NOT NULL AND F21_0088 = '江苏' ORDER BY  F3_0088 ASC ,F1_0088 DESC
     *
     * @param sql
     * @return
     * @throws JsonProcessingException
     * @throws SqlParseException
     * @throws JSQLParserException
     */
    @ApiOperation(value = "SQL转换成ES结构体Beta版本")
    @PostMapping(value = "/SQLToEsHelper_Beta")
    public Response SQLToEsHelper_Beta(@RequestBody String sql) throws JsonProcessingException, SqlParseException, JSQLParserException {

        DSLHelper dslHelper = new DSLHelper();
        SqlKind kind = SQLMysqlCalciteParseUtils.getKind(sql);
        if (!kind.equals(SqlKind.SELECT) && !kind.equals(SqlKind.ORDER_BY)) {
            throw new RuntimeException("只支持 select/order by 类型,当前类型是:" + kind);
        }
        List<String> simpleSelectList = SQLMysqlCalciteParseUtils.getSimpleSelectList(sql);
        List<SqlBasicCall> sqlBasicCalls = SQLMysqlCalciteParseUtils.getWhereSimpleSqlBasicCall(sql);
        for (SqlBasicCall sqlBasicCall : sqlBasicCalls) {
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.IS_NOT_NULL)) {
                //处理null
                DSLHelper.Exists exists = new DSLHelper.Exists();
                exists.setField(sqlBasicCall.getOperands()[0].toString());
                dslHelper.must(exists);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.EQUALS)) {
                //处理 =
                DSLHelper.Term term = new DSLHelper.Term();
                term.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                term.setValue(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                dslHelper.must(term);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.IN)) {
                //处理 in
                DSLHelper.Terms terms = new DSLHelper.Terms();
                terms.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                if (sqlBasicCall.getOperands()[1] instanceof SqlNodeList) {
                    SqlNodeList sqlNodeList = (SqlNodeList) sqlBasicCall.getOperands()[1];
                    List<String> values = new ArrayList<>();
                    sqlNodeList.getList().forEach(sqlNode -> {
                        values.add(SQLMysqlCalciteParseUtils.getValue(sqlNode));
                    });
                    terms.setValue(values);
                } else {
                    throw new RuntimeException("In 后不是数组");
                }
                dslHelper.must(terms);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.BETWEEN)) {
                //处理 between
                DSLHelper.Range range = new DSLHelper.Range();
                range.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                range.setGte(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                range.setLte(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[2]));
                dslHelper.must(range);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.GREATER_THAN_OR_EQUAL)) {
                //处理 >=
                DSLHelper.Range range = new DSLHelper.Range();
                range.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                range.setGte(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                dslHelper.must(range);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.GREATER_THAN)) {
                //处理 >=
                DSLHelper.Range range = new DSLHelper.Range();
                range.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                range.setGt(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                dslHelper.must(range);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.LESS_THAN_OR_EQUAL)) {
                //处理 <=
                DSLHelper.Range range = new DSLHelper.Range();
                range.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                range.setLte(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                dslHelper.must(range);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.LESS_THAN)) {
                //处理 <=
                DSLHelper.Range range = new DSLHelper.Range();
                range.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                range.setLt(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                dslHelper.must(range);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.LIKE)) {
                //处理 like -> 注意类型都是 LIKE
                if (sqlBasicCall.getOperator().getName().equalsIgnoreCase("LIKE")) {
                    DSLHelper.Wildcard wildcard = new DSLHelper.Wildcard();
                    wildcard.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                    wildcard.setValue(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                    dslHelper.must(wildcard);
                }

            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.OTHER_FUNCTION)) {
                //处理 regex_like 这里当做函数来处理
                if (sqlBasicCall.getOperator().getName().equalsIgnoreCase("REGEXP_LIKE")) {
                    DSLHelper.Regexp regexp = new DSLHelper.Regexp();
                    regexp.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                    regexp.setValue(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                    dslHelper.must(regexp);
                } else {
                    throw new RuntimeException("出现不支持函数:" + sqlBasicCall.getOperator().getName());
                }
            }
            //处理 not

            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.IS_NULL)) {
                //处理 not null
                //处理null
                DSLHelper.Exists exists = new DSLHelper.Exists();
                exists.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                dslHelper.must_not(exists);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.NOT_EQUALS)) {
                //处理 <>
                DSLHelper.Term term = new DSLHelper.Term();
                term.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                term.setValue(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                dslHelper.must_not(term);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.NOT_IN)) {
                //处理 not in
                DSLHelper.Terms terms = new DSLHelper.Terms();
                terms.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                if (sqlBasicCall.getOperands()[1] instanceof SqlNodeList) {
                    SqlNodeList sqlNodeList = (SqlNodeList) sqlBasicCall.getOperands()[1];
                    List<String> values = new ArrayList<>();
                    sqlNodeList.getList().forEach(sqlNode -> {
                        values.add(SQLMysqlCalciteParseUtils.getValue(sqlNode));
                    });
                    terms.setValue(values);
                } else {
                    throw new RuntimeException("In 后不是数组");
                }
                dslHelper.must_not(terms);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.LIKE)) {
                //处理 not like -> 注意类型都是 LIKE
                if (sqlBasicCall.getOperator().getName().equalsIgnoreCase("NOT LIKE")) {
                    DSLHelper.Wildcard wildcard = new DSLHelper.Wildcard();
                    wildcard.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                    wildcard.setValue(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                    dslHelper.must_not(wildcard);
                }
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.NOT)) {
                //处理 NOT 先判断是NOT类型。
                //处理 regex_like 这里当做函数来处理
                Arrays.stream(sqlBasicCall.getOperands()).forEach(sqlNode -> {
                    if (sqlNode instanceof SqlBasicCall) {
                        SqlBasicCall sqlBasicCallTmp = (SqlBasicCall) sqlNode;
                        if (sqlBasicCallTmp.getOperator().getName().equalsIgnoreCase("REGEXP_LIKE")) {
                            DSLHelper.Regexp regexp = new DSLHelper.Regexp();
                            regexp.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCallTmp.getOperands()[0]));
                            regexp.setValue(SQLMysqlCalciteParseUtils.getValue(sqlBasicCallTmp.getOperands()[1]));
                            dslHelper.must_not(regexp);
                        } else {
                            throw new RuntimeException("出现不支持函数:" + sqlBasicCall.getOperator().getName());
                        }
                    }
                });
            }

        }

        SqlNode from = SQLMysqlCalciteParseUtils.getFrom(sql);
        DSLHelperPlus dslHelperPlus = new DSLHelperPlus();
        dslHelperPlus.setDslHelper(dslHelper);
        dslHelperPlus.setIndex(from.toString());
        dslHelperPlus.setFields(simpleSelectList);

        Map<String, String> sqlOrderMap = SQLMysqlCalciteParseUtils.getSqlOrderMap(sql);
        dslHelperPlus.getSort().add(sqlOrderMap);
        return Response.Ok(dslHelperPlus);

    }

}
