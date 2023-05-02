package com.tickets.models.routes;

public class Place {
    private String name;
    private String source;
    private String code;

    public Place() {
    }

    public Place(String name, String source, String code) {
        this.name = name;
        this.source = source;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
