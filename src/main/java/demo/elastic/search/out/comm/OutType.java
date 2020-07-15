package demo.elastic.search.out.comm;

public enum OutType {
    EXCEL("excel"), URL("url");

    private String type;

    OutType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static OutType getOutTypeByType(String type) {
        for (OutType outType : OutType.values()) {
            if (outType.getType().equals(type)) {
                return outType;
            }
        }
        return null;
    }
}
