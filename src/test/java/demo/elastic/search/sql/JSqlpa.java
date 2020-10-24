package demo.elastic.search.sql;

import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.util.TablesNamesFinder;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
public class JSqlpa {

    @Test
    public void xx() throws JSQLParserException {
        Statement stmt = CCJSqlParserUtil.parse("SELECT * FROM tab1");
        Select selectStatement = (Select) stmt;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
        log.info("xx:{}", stmt);
    }

    @Test
    public void xx2() throws JSQLParserException {
        Statement stmt = CCJSqlParserUtil.parse("SELECT * FROM tab1 where name = 'xx' and age like 'xx' and name regexp 'xxx' AND (age = '1' OR age ='3')");
        Select select = (Select) stmt;
        SelectBody selectBody = select.getSelectBody();
        PlainSelect plainSelect = (PlainSelect)selectBody;

        Expression where = plainSelect.getWhere();
        ExpressionDeParser expressionDeParser = new ExpressionDeParser();
        plainSelect.getWhere().accept(expressionDeParser);

        log.info("xx:{}", stmt);
    }
}
