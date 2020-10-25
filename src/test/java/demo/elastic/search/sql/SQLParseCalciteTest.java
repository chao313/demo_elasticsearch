package demo.elastic.search.sql;

import lombok.extern.slf4j.Slf4j;
import org.apache.calcite.config.Lex;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.type.RelDataTypeSystem;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
public class SQLParseCalciteTest {

    /**
     * {@link org.apache.calcite.sql.parser.impl.SqlParserImplConstants#tokenImage}
     * <p>
     * 这里测试select的解析
     * <p>
     * select id,name,age FROM stu where age<20 AND age > 10 OR name like '%..%' AND regexp_like(name,  '\d*') OR name is null
     */
    @Test
    public void select() {
        SqlParser.Config config = SqlParser.configBuilder()
                .setLex(Lex.ORACLE) //使用mysql 语法
                .build();
        //SqlParser 语法解析器
        SqlParser sqlParser = SqlParser
                .create("select id,name,age FROM stu where age<20 AND age > 10 OR name like '%..%' AND regexp_like(name,  '\\d*') OR name is null", config);
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


    @Test
    public void selectAnd() {
        SqlParser.Config config = SqlParser.configBuilder()
                .setLex(Lex.ORACLE) //使用mysql 语法
                .build();
        //SqlParser 语法解析器
        SqlParser sqlParser = SqlParser
                .create("select id,name,age FROM stu where age<20 AND age > 10 AND name like '%..%' AND regexp_like(name,  '\\d*') AND name is null", config);
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

    @Test
    public void xx2() {
        SqlParser.Config config = SqlParser.configBuilder()
                .setLex(Lex.ORACLE) //使用mysql 语法
                .build();
        //SqlParser 语法解析器
        SqlParser sqlParser = SqlParser
                .create("select id,name,age FROM stu where  name LIKE_REGEX '*'", config);
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
