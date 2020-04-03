package com.example.demo.model;

public class Picture {
    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "href='" + href + '\'' +
                '}';
    }
}
