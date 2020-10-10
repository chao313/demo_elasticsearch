package demo.elastic.search.feign.enums;


public enum FormatEnum {

    JSON("JSON"), YAML("YAML");

    private String type;

    FormatEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static FormatEnum getOutTypeByType(String type) {
        for (FormatEnum outType : FormatEnum.values()) {
            if (outType.getType().equals(type)) {
                return outType;
            }
        }
        return null;
    }
}
