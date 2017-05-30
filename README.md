# HtmlScraping
HTML Scraping Web Application Using Jsoup and SpringMVC 

## Pre-requisites
	-Maven 3.0.5
	-Java IDE
	-JDK 8
	-Any Web container (Preferred Tomcat +7)

## Running Steps
	- Run maven command "mvn clean install"
	- Deploy the "HtmlAnalysis-1.0-SNAPSHOT.war" file in the target folder in your Web container 
	- Voila!!

## Design
    -The Jsoup Library helped me much in the HTML Parsing, 
    the main design bottle neck was the process for validation of the urls resources
    -I have chosen Future API backed with fixed thread pool size executor 
    to do this task asynchronous from the normal flow,also I have used HEAD request 
    To ping url status considering redirect responses.
    -I was planing to use RxJava with Observable pattern 
    and use callbacks to update the page, But I didn't have time.
