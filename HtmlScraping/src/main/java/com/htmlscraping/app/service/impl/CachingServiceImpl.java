/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htmlscraping.app.service.impl;

import com.htmlscraping.app.service.CachingService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Service;


@Service
public class CachingServiceImpl implements CachingService {

    private  Map<String, String> htmlVersion = new HashMap<>();
    private  Map<String, String> systemProperties = new HashMap<>();

    @Autowired
    private ConfigurableEnvironment env;

    @Override
    public Map getHtmlVersions() {
       return htmlVersion;
    }

    @Override
    public Map getSystemProperties() {
       return systemProperties;
    }

    @Override
    public void setHtmlVersions() {

        htmlVersion = (Map) env.getPropertySources().get("htmlProperties").getSource();

    }

    @Override
    public void setSystemProperties() {
        
        systemProperties = (Map) env.getPropertySources().get("systemProperties").getSource();
        
    }

}
