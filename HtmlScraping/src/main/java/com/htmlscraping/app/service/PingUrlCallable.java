/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htmlscraping.app.service;

import com.htmlscraping.app.model.ValidateUrlModel;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;


public class PingUrlCallable implements Callable<ValidateUrlModel> {

    private BlockingQueue<String> queue;
    private Integer timeOut;

    public PingUrlCallable(BlockingQueue<String> queue, Integer timeOut) {
        this.queue = queue;
        this.timeOut = timeOut;
    }

    @Override
    public ValidateUrlModel call() throws Exception {
        ValidateUrlModel model = new ValidateUrlModel();
        Integer validLinksCount = 0;
        Integer invalidLinksCount = 0;
        List<String> invalidLinksReason = new ArrayList<>();
        
        /**
         * Keep thread alive As long as there are Links in the shared Queue
         */
        while (!queue.isEmpty()) {
            String url = queue.poll();
            if (url != null) {
                String response = pingURL(url, timeOut);

                if (response.equalsIgnoreCase("valid")) {
                    validLinksCount++;
                } else {
                    invalidLinksCount++;
                    invalidLinksReason.add(response);
                }
            }
        }
        model.setValidLinksCount(validLinksCount);
        model.setInvalidLinksCount(invalidLinksCount);
        model.setInvalidLinksReason(invalidLinksReason);
        return model;
    }

    public String pingURL(String url, Integer timeout) {
        // Otherwise an exception may be thrown on invalid SSL certificates.
        url = url.replaceFirst("^https", "http"); 

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod("HEAD");
            Integer responseCode = connection.getResponseCode();
            /**
             * Handle Success and redirect Responses as Valid
             */
            if (200 <= responseCode && responseCode <= 399) {
                return "valid";
            } else {
                return "invalid HTTP Response code " + responseCode;
            }
        } catch (IOException exception) {
            return exception.getMessage();
        } catch (Exception ex) {
            return "invalid HTTP response";
        }
    }

}
