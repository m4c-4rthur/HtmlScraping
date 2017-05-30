/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.htmlscraping.app.model;

import java.util.List;


public class AnalysisResponseModel {
    
    private String htmlVersion;
    private String pageTitle;
    private List<Integer> headingGroup;
    private Integer internalLinks;
    private Integer externalLinks;
    private Boolean loginForm;
    private Integer validLinks;
    private Integer invalidLinks;
    private List<String> invalidLinksReasons;
    
    

    public List<Integer> getHeadingGroup() {
        return headingGroup;
    }

    public void setHeadingGroup(List<Integer> headingGroup) {
        this.headingGroup = headingGroup;
    }

    public Integer getExternalLinks() {
        return externalLinks;
    }

    public void setExternalLinks(Integer externalLinks) {
        this.externalLinks = externalLinks;
    }
    
    

    public String getHtmlVersion() {
        return htmlVersion;
    }

    public void setHtmlVersion(String htmlVersion) {
        this.htmlVersion = htmlVersion;
    }

    public Integer getInvalidLinks() {
        return invalidLinks;
    }

    public void setInvalidLinks(Integer invalidLinks) {
        this.invalidLinks = invalidLinks;
    }

    public List<String> getInvalidLinksReasons() {
        return invalidLinksReasons;
    }

    public void setInvalidLinksReasons(List<String> invalidLinksReasons) {
        this.invalidLinksReasons = invalidLinksReasons;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public Integer getInternalLinks() {
        return internalLinks;
    }

    public void setInternalLinks(Integer internalLinks) {
        this.internalLinks = internalLinks;
    }

    public void setLoginForm(Boolean loginForm) {
        this.loginForm = loginForm;
    }

    public Integer getValidLinks() {
        return validLinks;
    }

    public void setValidLinks(Integer validLinks) {
        this.validLinks = validLinks;
    }

    public boolean getLoginForm() {
        return loginForm;
    }
    
    
    
    
    
}
