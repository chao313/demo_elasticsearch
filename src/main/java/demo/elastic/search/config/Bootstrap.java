package demo.elastic.search.config;


public enum Bootstrap {

    HONE("http://39.107.236.187:9200", Version.SEVEN),

    MY_WIND("http://10.202.16.136:9201", Version.SEVEN),

    LOCAL_SIX_1("http://127.0.0.1:9201", Version.SIX),

    LOCAL_SIX_0("http://127.0.0.1:9200", Version.SIX),

    DEV_WIND_ALI("http://39.107.236.187:8000", Version.TWO),

    DEV_WIND("http://10.202.16.9:9200", Version.TWO),

    PROD_OLD_WIND("http://127.0.0.1:8000", Version.TWO),

    PROD_NEW_WIND("http://127.0.0.1:80", Version.TWO),

    Ali2("http://39.107.236.187:7014", Version.SEVEN),

    Ali("http://39.107.236.187:7013", Version.SEVEN),

    PROD_UNIQE("http://10.200.5.217:9161/elasticsearch/", Version.TWO),

    PROD_CHECK("http://10.200.5.217:9162/elasticsearch/", Version.TWO);

    private String url;
    private Version version;

    Bootstrap(String url, Version version) {
        this.url = url;
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public Version getVersion() {
        return version;
    }

    public static Bootstrap getBootstrapByUrl(String url) {
        for (Bootstrap bootstrap : Bootstrap.values()) {
            if (bootstrap.getUrl().equals(url)) {
                return bootstrap;
            }
        }
        return null;
    }

    public static final String allowableValues = "39.107.236.187:9200,10.202.16.9:9200,127.0.0.1:8000,127.0.0.1:80";

    //    public static final String IN_USE = "http://10.202.16.9:9200";
//    public static final String IN_USE = "http://127.0.0.1:9202";
//    public static final String IN_USE = "http://10.202.16.9:9200";
//    public static final String IN_USE = "http://10.202.16.136:9201";
//    public static final String IN_USE = "http://127.0.0.1:80";
//    public static final String IN_USE = "http://39.107.236.187:7013";
    public static final String IN_USE = "http://39.107.236.187:7013";

    public static final String EXAMPLE = "http://127.0.0.1:9200 http://127.0.0.1:9202 http://39.107.236.187:7013 http://10.202.16.9:9200 http://127.0.0.1:80 http://39.107.236.187:7014";
    //    public static final String DEFAULT_VALUE = "http://39.107.236.187:7013";
    public static final String DEFAULT_VALUE = IN_USE;

    /**
     * 获取正在使用的ES的版本
     *
     * @return
     */
    public static Version getInUseVersion() {
        return Bootstrap.getBootstrapByUrl(IN_USE).getVersion();
    }

    public enum Version {
        TWO("2"), SIX("6"), SEVEN("7");
        private String version;

        Version(String version) {
            this.version = version;
        }

        public String getVersion() {
            return version;
        }
    }

}