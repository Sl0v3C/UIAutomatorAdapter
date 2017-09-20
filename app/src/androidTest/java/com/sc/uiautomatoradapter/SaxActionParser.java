package com.sc.uiautomatoradapter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by pyy on 2017/9/19.
 */

public class SaxActionParser implements ActionParser {

    @Override
    public List<App> parse(InputStream is) throws Exception {
        // get SAXParserFactory instance
        SAXParserFactory factory = SAXParserFactory.newInstance();
        // get SAXParser instance from SAXParserFactory instance
        SAXParser parser = factory.newSAXParser();
        // new appHandler
        appHandler handler = new appHandler();
        // parse is with handler
        parser.parse(is, handler);
        return handler.getApps();
    }

    // need to implement the handler extends DefaultHandler
    private class appHandler extends DefaultHandler {
        private List<App> apps;
        private App app;
        private Action action;
        private StringBuilder builder;

        // return the List of App instances
        public List<App> getApps() {
            return apps;
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            apps = new ArrayList<App>();
            builder = new StringBuilder();
        }

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if (localName.equals("app")) {
                app = new App();
                app.actList = new ArrayList<Action>();
            } else if (localName.equals("action")) {
                action = new Action();
            }
            // set length to 0 to re-read the characters of the element
            builder.setLength(0);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            // append the length of the ch to builder
            builder.append(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if (localName.equals("name")) {
                app.setName(builder.toString());
            } else if (localName.equals("type")) {
                action.setType(builder.toString());
            }  else if (localName.equals("value")) {
                action.setValue(builder.toString());
            } else if (localName.equals("action") && app != null) {
                app.actList.add(action);
            } else if (localName.equals("app")) {
                apps.add(app);
            }
        }
    }
}