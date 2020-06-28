package demo.elastic.search.config;

public enum Bootstrap {
    HONE("39.107.236.187:9200"),
    DEV_WIND("10.202.16.9:9200"),
    PROD_OLD_WIND("127.0.0.1:8000"),
    PROD_NEW_WIND("127.0.0.1:80");

    private String ip;

    Bootstrap(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public static final String allowableValues = "39.107.236.187:9200,10.202.16.9:9200,127.0.0.1:8000,127.0.0.1:80";
}