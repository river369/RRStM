package com.yi.utils;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by jianguog on 17/2/18.
 */
public class XmlReader {
    public Document readXmlFileToDocument(String fileString){
        String content = null;
        try {
            content = FileUtils.readFileToString(new File(fileString), "UTF-16");
            //content = FileUtils.readFileToString(new File(fileString), "UTF-8");
            //System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringReader sr = new StringReader(content);
        InputSource is = new InputSource(sr);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder= null;
        Document doc = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        try {
            doc = builder.parse(is);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doc;
    }

}
