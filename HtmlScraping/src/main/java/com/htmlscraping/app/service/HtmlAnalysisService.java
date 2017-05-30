/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htmlscraping.app.service;

import com.htmlscraping.app.model.AnalysisResponseModel;
import java.io.IOException;
import java.util.List;
import org.jsoup.nodes.Document;


public interface HtmlAnalysisService {

    AnalysisResponseModel analyseHtmlFromUrl(String url) throws IOException, Exception;

    String getHtmlVersion(Document doc);

    List<Integer> getHeadingGroups(Document doc);

    List<String> getAllLinks(Document doc, AnalysisResponseModel responseModel) throws InterruptedException;

    Boolean hasLoginForm(Document doc);


}
