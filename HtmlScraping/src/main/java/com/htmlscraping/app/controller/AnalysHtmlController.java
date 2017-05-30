/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htmlscraping.app.controller;

import com.htmlscraping.app.model.AnalysisRequestModel;
import com.htmlscraping.app.service.HtmlAnalysisService;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AnalysHtmlController {

    private final Logger LOGGER = LoggerFactory.getLogger(AnalysHtmlController.class);
    @Autowired
    HtmlAnalysisService analysisService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        LOGGER.debug("index()");
        return "redirect:/html";
    }

    @RequestMapping(value = "/html", method = RequestMethod.POST)
    public String saveOrUpdateUser(@ModelAttribute("analysisForm") AnalysisRequestModel arm,
            BindingResult result, Model model, final RedirectAttributes redirectAttributes) throws Exception {

        String[] schemes = {"http", "https"}; // DEFAULT schemes = "http", "https", "ftp"
        UrlValidator urlValidator = new UrlValidator(schemes);
        if (!urlValidator.isValid(arm.getUrl())) {
            
            model.addAttribute("error", "Invalid Url Recieved, Please Enter Valid URL");
            return "html/userPage";
        }
        LOGGER.debug("Start Analysing Html : {}", arm.toString());

        model.addAttribute("result", analysisService.analyseHtmlFromUrl(arm.getUrl()));

        return "html/userPage";

    }

    @RequestMapping(value = "/html", method = RequestMethod.GET)
    public String redierct(Model model) {

        LOGGER.debug("Home Page");

        AnalysisRequestModel arm = new AnalysisRequestModel();

        // set default value
        model.addAttribute("analysisForm", arm);

        return "html/userPage";

    }

}
