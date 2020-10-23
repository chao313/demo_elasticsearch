package demo.elastic.search.sql;

import lombok.extern.slf4j.Slf4j;
import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.junit.jupiter.api.Test;


@Slf4j
public class SQLParseTest {

    @Test
    public void xx() {
        SqlParser.Config config = SqlParser.configBuilder()
                .setLex(Lex.MYSQL) //使用mysql 语法
                .build();
        //SqlParser 语法解析器
        SqlParser sqlParser = SqlParser
                .create("select id,name,age FROM stu where age<20 AND age > 10 OR name like '%..%' AND name REGEXP 'ok' OR name is null", config);
        SqlNode sqlNode = null;
        try {
            sqlNode = sqlParser.parseStmt();
            SqlSelect sqlSelect = ((SqlSelect) sqlNode);
            SqlKind kind = sqlSelect.getKind();
            SqlNodeList selectList = sqlSelect.getSelectList();
            SqlNode from = sqlSelect.getFrom();
            SqlNode where = sqlSelect.getWhere();
            log.info(":{}");
        } catch (SqlParseException e) {
            throw new RuntimeException("", e);

        }

    }
}
