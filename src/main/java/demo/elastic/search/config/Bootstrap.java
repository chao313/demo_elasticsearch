package demo.elastic.search.config;

public enum Bootstrap {


    HONE("http://39.107.236.187:9200", Version.SEVEN),

    MY_WIND("http://10.202.16.136:9201", Version.SEVEN),

    DEV_WIND_ALI("http://39.107.236.187:8000", Version.TWO),

    DEV_WIND("http://10.202.16.9:9200", Version.TWO),

    PROD_OLD_WIND("http://127.0.0.1:8000", Version.TWO),

    PROD_NEW_WIND("http://127.0.0.1:80", Version.TWO);

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

    //    public static final String IN_USE = "http://39.107.236.187:8000";
//    public static final String IN_USE = "http://39.107.236.187:9200";
//    public static final String IN_USE = "http://127.0.0.1:80";
    public static final String IN_USE = "http://39.107.236.187:7013";
//    public static final String IN_USE = "http://127.0.0.1:8000";
//    public static final String IN_USE = "http://10.202.16.9:9200";
//    public static final String IN_USE = "http://10.202.16.136:9201";

    /**
     * 获取正在使用的ES的版本
     *
     * @return
     */
    public static Version getInUseVersion() {
        return Bootstrap.getBootstrapByUrl(IN_USE).getVersion();
    }

    public enum Version {
        TWO("2"), SEVEN("7");
        private String version;

        Version(String version) {
            this.version = version;
        }

        public String getVersion() {
            return version;
        }
    }

}