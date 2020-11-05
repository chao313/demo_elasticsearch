package demo.elastic.search.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.util.NlsString;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 解析语句的工具类
 */
@Slf4j
public class SQLMysqlCalciteParseUtilsTest {
    private static final SqlParser.Config config = SqlParser.configBuilder().setLex(Lex.ORACLE).setCaseSensitive(true).build();//使用mysql 语法

    @Test
    public void order() throws SqlParseException {
        //SqlParser 语法解析器
        String sql = "SELECT * FROM tb_object_0088 WHERE F1_0088 IS NOT NULL ORDER BY F2_0088";
        SqlParser sqlParser = SqlParser.create(sql, config);
        SqlNode sqlNode = sqlParser.parseStmt();
        SqlSelect sqlSelect = null;
        if (sqlNode instanceof SqlOrderBy) {
            SqlOrderBy sqlOrderBy = (SqlOrderBy) sqlNode;
            SqlNode query = sqlOrderBy.query;
            if (query instanceof SqlSelect) {
                sqlSelect = (SqlSelect) query;
            } else {
                throw new RuntimeException("解析sql异常:" + query);
            }
        } else if (sqlNode instanceof SqlSelect) {
            sqlSelect = (SqlSelect) sqlNode;
        }
        SqlNodeList sqlNodeList = sqlSelect.getSelectList();
        List<String> result = new ArrayList<>();
    }

    @Test
    public void query() throws SqlParseException {
        //SqlParser 语法解析器
        String sql = "SELECT * FROM tb_object_0088 WHERE F1_0088 IS NOT NULL";
        SqlParser sqlParser = SqlParser.create(sql, config);
        SqlNode sqlNode = sqlParser.parseStmt();
        SqlSelect sqlSelect = ((SqlSelect) sqlNode);
        SqlNodeList sqlNodeList = sqlSelect.getSelectList();
        List<String> result = new ArrayList<>();
    }

}


