package demo.elastic.search.out.hbase;


import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class HbaseService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory
            .getLogger(HbaseService.class);

    /**
     *
     */
    @Autowired
    private Connection hbaseConn = null;


    /**
     * @param tableNameValue
     * @param rowKey
     * @return
     */
    public byte[] getAttach(String tableNameValue, String rowKey) {

        byte[] temp = null;
        try {
            Table table = hbaseConn.getTable(TableName.valueOf(tableNameValue.toUpperCase()));
            Get get = new Get(Bytes.toBytes(rowKey));
            get.setMaxVersions();
            //get.addColumn("CONTENT_DATA".getBytes(),"ARTICLE".getBytes());
            Result result = table.get(get);
            if (result == null || result.list() == null) {
                return null;
            }
            byte[] resultValue = null;
            for (KeyValue kv : result.list()) {
                if ("ATTACH".equals(Bytes.toString(kv.getFamily())) && "ATTACH".equals(Bytes.toString(kv.getQualifier()))) {
                    resultValue = kv.getValue();
                }
            }
            table.close();
            return resultValue;
            //  connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * @param tableNameValue
     * @param rowKey
     * @return
     */
    public Map hbsearch(String tableNameValue, String rowKey) {

        Map resultMap = new HashMap();
        try {

            Table table = hbaseConn.getTable(TableName.valueOf(tableNameValue.toUpperCase()));
            Get get = new Get(Bytes.toBytes(rowKey));
            get.setMaxVersions();
            //get.addColumn("CONTENT_DATA".getBytes(),"ARTICLE".getBytes());
            System.out.println(">>>>>>>>>>>>>>>>>" + table.getName());
            Result result = table.get(get);
            if (result == null || result.list() == null) {
//                return;
                return null;
            }
            for (KeyValue kv : result.list()) {
                System.out.println("family:" + Bytes.toString(kv.getFamily()));
                System.out.println("qualifier:" + Bytes.toString(kv.getQualifier()));
                System.out.println("value:" + Bytes.toString(kv.getValue()));
                System.out.println("Timestamp:" + kv.getTimestamp());


                if ("ContentData".equals(Bytes.toString(kv.getQualifier()))) {
                    resultMap.put(Bytes.toString(kv.getQualifier()), JSONObject.parseObject(Bytes.toString(kv.getValue())));
                } else {
                    resultMap.put(Bytes.toString(kv.getQualifier()), Bytes.toString(kv.getValue()));
                }
            }
            table.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }
}
