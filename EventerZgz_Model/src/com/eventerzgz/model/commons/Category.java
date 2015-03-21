package com.eventerzgz.model.commons;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import com.eventerzgz.model.Base;

import com.eventerzgz.model.exception.EventZgzException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Created by joseluis on 21/3/15.
 */
public class Category extends Base {

    private String sImage;

    public static List<Category> doParseList(String sRawObj) throws EventZgzException{

        List<Category> categoryList = new ArrayList<>();

        XPathFactory  factory= XPathFactory.newInstance();
        XPath xPath=factory.newXPath();

        try
        {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder =  builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(new InputSource(new StringReader( sRawObj )));

            XPathExpression tag_id = xPath.compile("/sparql/results/result");
            NodeList nodeList = (NodeList) tag_id.evaluate(xmlDocument, XPathConstants.NODESET);
            Category objCategory;
            int j;
            for (int i = 0; i < nodeList.getLength(); i++)
            {
                objCategory = new Category();
                System.out.println(i);
                j = i+1;
                String id = xPath.compile("/*[name()='sparql']/*[name()='results']/*[name()='result']["+j+"]/*[name()='binding'][@name='id']/*[name()='literal']/text()").evaluate(xmlDocument);
                String title = xPath.compile("/*[name()='sparql']/*[name()='results']/*[name()='result']["+j+"]/*[name()='binding'][@name='tema']/*[name()='literal']/text()").evaluate(xmlDocument);
                System.out.println(i);
                objCategory.setId(id);
                objCategory.setsTitle(title);
                categoryList.add(objCategory);
            }
        } catch (Exception e) {
            throw new EventZgzException(e);
        }

        return categoryList;
    }

    //GETTERS & SETTERS
    public String getsImage() {
        return sImage;
    }

    public void setsImage(String sImage) {
        this.sImage = sImage;
    }

    public String getFieldWithUri(String sFieldValue){
        return "http://"+sFieldValue;
    }

}
