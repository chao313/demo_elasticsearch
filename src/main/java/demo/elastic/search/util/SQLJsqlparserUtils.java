package demo.elastic.search.util;


import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.apache.calcite.sql.parser.SqlParseException;

import java.util.List;

/**
 * 解析语句的工具类
 */
@Slf4j
public class SQLJsqlparserUtils {

    /**
     * 获取检索的表
     */
    public static List<String> getFrom(String sql) throws SqlParseException, JSQLParserException {
        Statement stmt = CCJSqlParserUtil.parse(sql);
        Select selectStatement = (Select) stmt;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
        return tableList;
    }
}


