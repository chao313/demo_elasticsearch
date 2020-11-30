package demo.elastic.search.out.db.mysql.service;

import demo.elastic.search.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Component
public class DBService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 复制表结构
     * CREATE TABLE targetTable LIKE sourceTable;
     *
     * @param sourceTable
     * @param targetTable
     * @return
     */
    public Boolean cloneTableStruct(String sourceTable, String targetTable) {
        String sql = "CREATE TABLE " + targetTable + " LIKE " + sourceTable;
        return jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps)
                    throws SQLException, DataAccessException {
                return ps.execute();
            }
        });

    }

    /**
     * 插入6917
     *
     * @return
     */
    public int batchInsert(String tableName, List<List<String>> datas, List<String> fieldNames) {
        int successCount = 0;
        if (datas == null || datas.size() == 0) {
            return successCount;
        }
        String insertSQL = "INSERT INTO " + tableName + "";
        insertSQL += " ( ";
        for (String field : fieldNames) {
            insertSQL += field + ",";
        }
        insertSQL = insertSQL.substring(0, insertSQL.length() - 1);
        insertSQL += " ) VALUES( ";
        for (String field : fieldNames) {
            insertSQL += "?,";
        }
        insertSQL = insertSQL.substring(0, insertSQL.length() - 1);
        insertSQL += " )";
        int size = datas.size();
        int[] countArray = jdbcTemplate.batchUpdate(insertSQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                List<String> model = datas.get(i);
                log.info("插入进度:{}/{}->{}", i + 1, size, ExcelUtil.percent(i + 1, size));
                for (int j = 0; j < fieldNames.size(); j++) {
                    ps.setNString(j + 1, model.get(j));
                }
            }

            @Override
            public int getBatchSize() {
                return datas.size();
            }
        });
        for (int count : countArray) {
            if (count == -2) {
                successCount++;
            }
        }
        return successCount;
    }


}
