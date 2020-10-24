package demo.elastic.search.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 解析语句的工具类
 */
@Slf4j
public class SQLOracleCalciteParseUtils {

    private static final SqlParser.Config config = SqlParser.configBuilder().setLex(Lex.ORACLE).build();//使用mysql 语法

    private static final List<SqlKind> sqlKinds = Arrays.asList(SqlKind.AND, SqlKind.OR);

    /**
     * 获取语句类型 select/delete/update
     */
    public static SqlKind getKind(String sql) throws SqlParseException {
        //SqlParser 语法解析器
        SqlParser sqlParser = SqlParser.create(sql, config);
        SqlNode sqlNode = sqlParser.parseStmt();
        SqlKind sqlKind = sqlNode.getKind();
        return sqlKind;
    }

    /**
     * 获取检索的字段
     */
    public static SqlNodeList getSelectList(String sql) throws SqlParseException {
        //SqlParser 语法解析器
        SqlParser sqlParser = SqlParser.create(sql, config);
        SqlNode sqlNode = sqlParser.parseStmt();
        SqlSelect sqlSelect = ((SqlSelect) sqlNode);
        SqlNodeList sqlNodeList = sqlSelect.getSelectList();
        return sqlNodeList;
    }

    /**
     * 获取检索的表
     */
    public static SqlNode getFrom(String sql) throws SqlParseException {
        //SqlParser 语法解析器
        SqlParser sqlParser = SqlParser.create(sql, config);
        SqlNode sqlNode = sqlParser.parseStmt();
        SqlSelect sqlSelect = ((SqlSelect) sqlNode);
        SqlNode from = sqlSelect.getFrom();
        return from;
    }

    /**
     * 获取检索的where
     */
    public static SqlBasicCall getWhere(String sql) throws SqlParseException {
        //SqlParser 语法解析器
        SqlParser sqlParser = SqlParser.create(sql, config);
        SqlNode sqlNode = sqlParser.parseStmt();
        SqlSelect sqlSelect = ((SqlSelect) sqlNode);
        SqlBasicCall sqlBasicCall = (SqlBasicCall) sqlSelect.getWhere();
        return sqlBasicCall;
    }

    /**
     * 获取检索的where 这里是简单的只有and的解析
     */
    public static List<SqlBasicCall> getWhereSimpleSqlBasicCall(String sql) throws SqlParseException {
        SqlBasicCall sqlBasicCall = SQLOracleCalciteParseUtils.getWhere(sql);
        List<SqlBasicCall> sqlBasicCalls = new ArrayList<>();
        getSimpleSqlBasicCalls(sqlBasicCall, sqlBasicCalls);
        return sqlBasicCalls;
    }

    /**
     * 获取全部的
     *
     * @param sqlBasicCall
     * @param sqlBasicCalls
     */
    private static void getSimpleSqlBasicCalls(SqlBasicCall sqlBasicCall, List<SqlBasicCall> sqlBasicCalls) {
        Arrays.stream(sqlBasicCall.getOperands()).forEach(sqlNode -> {
            if (sqlNode instanceof SqlBasicCall) {
                SqlBasicCall tmp = ((SqlBasicCall) sqlNode);
                if (sqlKinds.contains(tmp.getOperator().getKind())) {
                    getSimpleSqlBasicCalls(((SqlBasicCall) sqlNode), sqlBasicCalls);
                } else {
                    sqlBasicCalls.add(tmp);
                }
            } else {
                log.error("错误:{}");
            }
        });
    }


    /**
     * 解析简单的
     *
     * @param sql
     */
    public static void getKine(String sql) {

        //SqlParser 语法解析器
        SqlParser sqlParser = SqlParser.create(sql, config);
        SqlNode sqlNode = null;
        try {
            sqlNode = sqlParser.parseStmt();
            SqlSelect sqlSelect = ((SqlSelect) sqlNode);
            SqlKind sqlKind = sqlSelect.getKind();//获取语句类型 select/delete/update
            log.info("解析:kind:{}", sqlKind);
            SqlNodeList sqlNodeList = sqlSelect.getSelectList();
            log.info("解析:sqlNodeList:{}", sqlNodeList);
            SqlNode from = sqlSelect.getFrom();
            log.info("解析:from:{}", from);
            SqlNode where = sqlSelect.getWhere();
            log.info("解析:where:{}", where);
            if (where instanceof SqlBasicCall) {
                SqlBasicCall sqlBasicCall = (SqlBasicCall) where;
                log.info("sqlBasicCall:{}", sqlBasicCall);
                SqlKind kind = sqlBasicCall.getKind();
                log.info("顶级:{}", kind);
                SqlNode[] operands = sqlBasicCall.getOperands();
                for (int i = 0; i < operands.length; i++) {
                    SqlNode operand = operands[i];
                    log.info("operand:{}", operand);
                }

            }
        } catch (SqlParseException e) {
            throw new RuntimeException("", e);

        }

    }
}


