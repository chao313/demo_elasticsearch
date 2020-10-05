package demo.elastic.search.out.remove.compound;

/**
 * 继承这个接口的需要执行解析
 */
public interface Parse {
    /**
     * 由于某些情况下的json难以质检转换为vo
     * -> 解析vo为指定格式的json
     */
    String parse();
}
