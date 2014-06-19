package com.twu.biblioteca;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by morrista on 6/13/2014.
 */
public class ScreenInfo {

    protected String category;
    protected NodeList list;

    ScreenInfo(String category, NodeList list) {
        this.category = category;
        this.list = list;
    }


}
