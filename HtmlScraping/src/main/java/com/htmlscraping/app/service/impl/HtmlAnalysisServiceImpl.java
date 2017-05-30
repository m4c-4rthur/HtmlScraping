/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htmlscraping.app.service.impl;

import com.htmlscraping.app.model.AnalysisResponseModel;
import com.htmlscraping.app.model.ValidateUrlModel;
import com.htmlscraping.app.service.CachingService;
import com.htmlscraping.app.service.HtmlAnalysisService;
import com.htmlscraping.app.service.PingUrlCallable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HtmlAnalysisServiceImpl implements HtmlAnalysisService {

    @Autowired
    CachingService cachingService;
    
    private final String REQUEST_TIME_OUT = "REQUEST_TIME_OUT";
    private final String NUMBER_OF_THREADS = "NUMBER_OF_THREADS";

    private final Logger LOGGER = LoggerFactory.getLogger(HtmlAnalysisServiceImpl.class);

    private BlockingQueue<String> queue = new ArrayBlockingQueue<>(1024);
    /**
     * 
     * @param url
     * @return
     * @throws IOException
     * @throws Exception
     * Main method which handle the HTML Page Analysis
     */
    @Override
    public AnalysisResponseModel analyseHtmlFromUrl(String url) throws IOException,Exception{
        Document doc;
        Integer validLinksCount = 0;
        Integer invalidLinksCount = 0;
        List<String> invalidLinksReason = new ArrayList<>();
        Integer requestTimeOut = Integer.parseInt(cachingService.getSystemProperties().get(REQUEST_TIME_OUT).toString());
        Integer numberOfThreads = Integer.parseInt(cachingService.getSystemProperties().get(NUMBER_OF_THREADS).toString());
        AnalysisResponseModel responseModel = new AnalysisResponseModel();
        try {
            doc = Jsoup.connect(url).get();
            //this blocking queue to be used later with URL validation
            queue.addAll(getAllLinks(doc, responseModel));
            /**
             * Starting asynchronous Task to check URLs validity through Pinging 
             * Number for Threads/Taks are configurable through Configuration file
             */
            ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
            List<Future<ValidateUrlModel>> list = new ArrayList<>();
            for (int i = 0; i < numberOfThreads; i++) {
                /**
                 * Request Time Out is configurable through Configuration file
                 */
                Callable<ValidateUrlModel> worker = new PingUrlCallable(queue, requestTimeOut);
                Future<ValidateUrlModel> submit = executor.submit(worker);
                list.add(submit);
            }
            /**
             * 
             * Continue Scraping
             */
            responseModel.setHtmlVersion(getHtmlVersion(doc));
            responseModel.setPageTitle(doc.title() != null ? doc.title(): "N/A" );
            responseModel.setHeadingGroup(getHeadingGroups(doc));
            responseModel.setLoginForm(hasLoginForm(doc));
            
            /**
             * Get the Result from the Asynch taks
             */
            for (Future<ValidateUrlModel> future : list) {
                try {
                    ValidateUrlModel urlModel = future.get();
                    invalidLinksCount += urlModel.getInvalidLinksCount();
                    invalidLinksReason.addAll(urlModel.getInvalidLinksReason());
                    validLinksCount += urlModel.getValidLinksCount();
                } catch (InterruptedException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            /**
             * ShutDown the executor Service Pool
             */
            executor.shutdown();
            responseModel.setInvalidLinks(invalidLinksCount);
            responseModel.setValidLinks(validLinksCount);
            responseModel.setInvalidLinksReasons(invalidLinksReason);

        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw ex;
        }
        return responseModel;
    }

    /**
     * 
     * @param doc
     * @return 
     */
    @Override
    public List<Integer> getHeadingGroups(Document doc) {
        List<Integer> headingGroups = new ArrayList<>();
        Elements hTags = doc.select("h1, h2, h3, h4, h5, h6");
        Elements h1Tags = hTags.select("h1");
        headingGroups.add(h1Tags.size());
        Elements h2Tags = hTags.select("h2");
        headingGroups.add(h2Tags.size());
        Elements h3Tags = hTags.select("h3");
        headingGroups.add(h3Tags.size());
        Elements h4Tags = hTags.select("h4");
        headingGroups.add(h4Tags.size());
        Elements h5Tags = hTags.select("h5");
        headingGroups.add(h5Tags.size());
        Elements h6Tags = hTags.select("h6");
        headingGroups.add(h6Tags.size());
        return headingGroups;
    }

    @Override
    public String getHtmlVersion(Document doc) {
        List<Node> nods = doc.childNodes();
        DocumentType documentType = null;
        for (Node node : nods) {
            if (node instanceof DocumentType) {
                documentType = (DocumentType) node;
                break;
            }
        }
        /**
         * get cached map from properties file which contains known HTML versions doc types 
         * so if we need to add new type we don't change the code
         */
        Map<String, String> htmlMap = cachingService.getHtmlVersions();
        for (Map.Entry<String, String> entry : htmlMap.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(documentType.toString())) {
                return entry.getKey();
            }
        }
        return "Unknown Version";
    }

    @Override
    public List<String> getAllLinks(Document doc, AnalysisResponseModel responseModel) throws InterruptedException {
        Integer internalLinks = 0;
        Integer externalLinks = 0;
        List<String> linksList = new ArrayList<>();
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            //getting relative path to distingish exterrnal from interal
            String relativelink = link.attr("href");
            if (relativelink.contains("http")) {
                externalLinks++;
            } else {
                internalLinks++;
            }
            //to be used later in the URLs validation
            linksList.add(link.attr("abs:href"));
        }
        responseModel.setInternalLinks(internalLinks);
        responseModel.setExternalLinks(externalLinks);
        return linksList;
    }

    @Override
    public Boolean hasLoginForm(Document doc) {
        /**
         * match on Html Form with method = post since login form should be using post
         */
        Elements postForms = doc.select("form[method=post]");
        Integer loginFormProbabiltyCounter = 0;
        for (Element form : postForms) {
            Integer tempCounter = 0;
            for (Node el : form.childNodes()) {
                if (el.toString().contains("passwor")) {
                    tempCounter++;
                }
                if (el.toString().contains("login")) {
                    tempCounter++;
                }
                if (el.toString().contains("username")) {
                    tempCounter++;
                }
                //input of type hidden for csrf authentication token
                if (el.toString().contains("type=\"hidden\"")) {
                    tempCounter++;
                }
            }
            if (tempCounter > loginFormProbabiltyCounter) {
                loginFormProbabiltyCounter = tempCounter;
            }
        }
        /**
         * I have choosed this threshold based on multiple websites
         */
        if (loginFormProbabiltyCounter >= 2) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    

}
