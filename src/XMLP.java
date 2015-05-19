
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLP extends DefaultHandler {
    List<Record> records;
    String xmlFile;
    String tmpValue;
    Record record;
    
    SimpleDateFormat sdf= new SimpleDateFormat("yy-MM-dd");
    
    public XMLP(String xmlFile) {
        this.xmlFile = xmlFile;
        records = new ArrayList<Record>();
        parseDocument();
        printDatas();
    }
    private void parseDocument() {

    	SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(xmlFile, this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
    }
    private void printDatas() {
        for (Record r : records) {
            System.out.println(r.toString());
        }
    }
    @Override
    public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {
        // if current element is book , create new book
        // clear tmpValue on start of element

        if (elementName.equalsIgnoreCase("book") && attributes.getQName(0).equalsIgnoreCase("mdate")) {
            record = new Record();
        }
    }
    
    @Override
    public void endElement(String s, String s1, String element) throws SAXException {
        // if end of book element add to list
        if (element.equals("book")) {
        	records.add(record);
        }
        if (element.equalsIgnoreCase("author") || element.equalsIgnoreCase("editor")) {
            record.authors_editors.add(tmpValue);
        }
        if (element.equalsIgnoreCase("booktitle")) {
        	record.booktitle = tmpValue;
        }
        if(element.equalsIgnoreCase("publisher")){
        	record.publisher = tmpValue;
        }
    }
    @Override
    public void characters(char[] ac, int i, int j) throws SAXException {
        tmpValue = new String(ac, i, j);
    }
    public static void main(String[] args) {
        new XMLP("book.xml");
    }
}


