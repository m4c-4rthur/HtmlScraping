/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.htmlscraping.app.service;

import java.util.Map;


public interface CachingService {
    
    void setHtmlVersions();
    
    Map getHtmlVersions();
    
    void setSystemProperties();
    
    Map getSystemProperties();
    
}
