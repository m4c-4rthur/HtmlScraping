package com.htmlscraping.app;

import com.htmlscraping.app.configuration.SystemConfiguration;
import com.htmlscraping.app.model.AnalysisResponseModel;
import com.htmlscraping.app.service.HtmlAnalysisService;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SystemConfiguration.class)
@WebAppConfiguration
public class AnalysisTest {

    @Autowired
    HtmlAnalysisService analysisService;

    static Document doc;

    private static final String mockHtml = "<!DOCTYPE html>\n"
            + "<HTML>\n"
            + "<HEAD>\n"
            + "<TITLE>Basic HTML Sample Page</TITLE>\n"
            + "</HEAD>\n"
            + "<BODY BGCOLOR=\"WHITE\">\n"
            + "<CENTER>\n"
            + "<H1>A Simple Sample Web Page</H1>\n"
            + "\n"
            + " \n"
            + "\n"
            + "  <IMG SRC=\"//www.sheldonbrown.com/images/scb_eagle_contact.jpeg\">\n"
            + "\n"
            + " \n"
            + "\n"
            + " \n"
            + "\n"
            + "  <H4>By Sheldon Brown</H4>\n"
            + "\n"
            + "<H2>Demonstrating a few HTML features</H2>\n"
            + "\n"
            + "</CENTER>\n"
            + "\n"
            + "HTML is really a very simple language. It consists of ordinary text, with commands that are enclosed by \"<\" and \">\" characters, or bewteen an \"&\" and a \";\". <P>\n"
            + " \n"
            + "\n"
            + "You don't really need to know much HTML to create a page, because you can copy bits of HTML from other pages that do what you want, then change the text!<P>\n"
            + " \n"
            + "\n"
            + "This page shows on the left as it appears in your browser, and the corresponding HTML code appears on the right. The HTML commands are linked to explanations of what they do.\n"
            + " \n"
            + "\n"
            + " \n"
            + "\n"
            + "<H3>Line Breaks</H3>\n"
            + "\n"
            + "HTML doesn't normally use line breaks for ordinary text. A white space of any size is treated as a single space. This is because the author of the page has no way of knowing the size of the reader's screen, or what size type they will have their browser set for.<P>\n"
            + "\n"
            + " \n"
            + "\n"
            + "If you want to put a line break at a particular place, you can use the \"<BR>\" command, or, for a paragraph break, the \"<P>\" command, which will insert a blank line. The heading command (\"<4></4>\") puts a blank line above and below the heading text.\n"
            + "\n"
            + " \n"
            + "\n"
            + "<H4>Starting and Stopping Commands</H4>\n"
            + "\n"
            + "Most HTML commands come in pairs: for example, \"<H4>\" marks the beginning of a size 4 heading, and \"</H4>\" marks the end of it. The closing command is always the same as the opening command, except for the addition of the \"/\".<P>\n"
            + "\n"
            + " \n"
            + "\n"
            + "Modifiers are sometimes included along with the basic command, inside the opening command's < >. The modifier does not need to be repeated in the closing command.\n"
            + "\n"
            + " \n"
            + "\n"
            + " \n"
            + "\n"
            + "<H1>This is a size \"1\" heading</H1>\n"
            + "\n"
            + "<H2>This is a size \"2\" heading</H2>\n"
            + "\n"
            + "<H3>This is a size \"3\" heading</H3>\n"
            + "\n"
            + "<H4>This is a size \"4\" heading</H4>\n"
            + "\n"
            + "<H5>This is a size \"5\" heading</H5>\n"
            + "\n"
            + "<H6>This is a size \"6\" heading</H6>\n"
            + "\n"
            + "<center>\n"
            + "\n"
            + "<H4>Copyright © 1997, by\n"
            + "<A HREF=\"/index.html\">Sheldon Brown</A>\n"
            + "<A HREF=\"https://www.google.com.eg\">Sheldon Brown</A>\n"
            + "</H4>\n"
            + "\n"
            + "If you would like to make a link or bookmark to this page, the URL is:<BR> //www.sheldonbrown.com/web_sample1.html</body>\n"
            + "</HTML>";


    @BeforeClass
    public static void setup() throws IOException {
        doc = Jsoup.parse(mockHtml);

    }

    @Test
    public void testGetHtmlVersion() throws IOException {

        String version = analysisService.getHtmlVersion(doc);

        Assert.assertEquals("HTML.5", version);

    }

    @Test
    public void testGetHeadingGroups() throws IOException {
        List<Integer> actual = Arrays.asList(2, 2, 2, 5, 1, 1);
        List<Integer> list = analysisService.getHeadingGroups(doc);
        assertThat(actual, is(list));

    }

    @Test
    public void testGetAllLinks() throws IOException, InterruptedException {
        
        AnalysisResponseModel responseModel = new AnalysisResponseModel();
        
        List<String> list =  analysisService.getAllLinks(doc, responseModel);
        assertThat(1, is(responseModel.getInternalLinks()));
        assertThat(1, is(responseModel.getExternalLinks()));

    }

    @Test
    public void testHasLoginForm() throws IOException {
        Boolean result = analysisService.hasLoginForm(doc);
        assertEquals(result, Boolean.FALSE);
    }

 
}
