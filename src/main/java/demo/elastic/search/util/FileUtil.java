package demo.elastic.search.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.function.Consumer;

public class FileUtil {

    /**
     * 按行读取大文件
     *
     * @param inputStream
     * @param consumer
     * @throws IOException
     */
    public static void readBigFileByLine(InputStream inputStream, Consumer<String> consumer) throws IOException {
        LineNumberReader lineNumberReader =
                new LineNumberReader(new InputStreamReader(inputStream));
        String line = "";
        int i = 0;
        while ((line = lineNumberReader.readLine()) != null) {
            consumer.accept(line);
        }
    }
}
