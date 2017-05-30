package com.htmlscraping.app.model;


public class AnalysisRequestModel {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "AnalysisRequestModel{" + "url=" + url + '}';
    }

}
