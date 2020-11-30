package demo.elastic.search.po.helper;

import demo.elastic.search.util.SQLOracleCalciteParseUtils;
import lombok.Data;
import org.apache.calcite.sql.SqlBasicCall;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.parser.SqlParseException;

import java.util.*;

import static demo.elastic.search.util.SQLOracleCalciteParseUtils.getWhereSimpleSqlBasicCall;

@Data
public class DSLHelperPlus {
    private String index;
    private List<String> fields;
    private DSLHelper dslHelper;
    private List<Map<String, String>> sort = new ArrayList<>();//存放排序

    /**
     * 抽象出sql转换成DSLHelperPlus
     *
     * @param sql
     * @return
     * @throws SqlParseException
     */
    public static DSLHelperPlus sqlToDSLHelperPlus(String sql) throws SqlParseException {
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
        return dslHelperPlus;
    }
}
