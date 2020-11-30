package demo.elastic.search.config;


public enum Bootstrap {

    HONE("http://39.107.236.187:9200", Version.SEVEN, KbnVersion.DEFAULT),

    LOCAL_SIX_1("http://127.0.0.1:9201", Version.SIX, KbnVersion.DEFAULT),

    LOCAL_SIX_0("http://127.0.0.1:9200", Version.SIX, KbnVersion.DEFAULT),

    Ali2("http://39.107.236.187:7014", Version.SEVEN, KbnVersion.DEFAULT),

    Ali("http://39.107.236.187:7013", Version.SEVEN, KbnVersion.DEFAULT);


    private String url;
    private Version version;
    private KbnVersion kbn_version;

    Bootstrap(String url, Version version) {
        this.url = url;
        this.version = version;
    }

    Bootstrap(String url, Version version, KbnVersion kbn_version) {
        this.url = url;
        this.version = version;
        this.kbn_version = kbn_version;
    }

    public String getUrl() {
        return url;
    }

    public Version getVersion() {
        return version;
    }

    public KbnVersion getKbn_version() {
        return kbn_version;
    }

    public static Bootstrap getBootstrapByUrl(String url) {
        for (Bootstrap bootstrap : Bootstrap.values()) {
            if (bootstrap.getUrl().equals(url)) {
                return bootstrap;
            }
        }
        return null;
    }

    public static final String allowableValues = "http://127.0.0.1:9202,http://39.107.236.187:7013";

    public static final String IN_USE = "http://127.0.0.1:9202";
//    public static final String IN_USE = "http://127.0.0.1:80";
//    public static final String IN_USE = "http://39.107.236.187:7013";
//    public static final String IN_USE = "http://39.107.236.187:7014";

    public static final String EXAMPLE = "http://127.0.0.1:9200 http://127.0.0.1:9202 http://39.107.236.187:7013 http://39.107.236.187:7014";
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

    public enum KbnVersion {
        DEFAULT("default"), SIX("6.4.1"), FOUR("4.5.4");
        private String version;

        KbnVersion(String version) {
            this.version = version;
        }

        public String getVersion() {
            return version;
        }
    }

}