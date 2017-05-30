/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htmlscraping.app.model;

import java.util.List;


public class ValidateUrlModel {

    private Integer validLinksCount;
    private Integer invalidLinksCount;
    private List<String> invalidLinksReason;

    public Integer getValidLinksCount() {
        return validLinksCount;
    }

    public void setValidLinksCount(Integer validLinksCount) {
        this.validLinksCount = validLinksCount;
    }

    public Integer getInvalidLinksCount() {
        return invalidLinksCount;
    }

    public void setInvalidLinksCount(Integer invalidLinksCount) {
        this.invalidLinksCount = invalidLinksCount;
    }

    public List<String> getInvalidLinksReason() {
        return invalidLinksReason;
    }

    public void setInvalidLinksReason(List<String> invalidLinksReason) {
        this.invalidLinksReason = invalidLinksReason;
    }
    
    

}
