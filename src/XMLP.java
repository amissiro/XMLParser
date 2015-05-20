
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLP extends DefaultHandler {
    List<Record> records;
    static Set<String> authors;
    static Set<String> booktitles;
    static Set<String> publishers;
    static Set<String> documents;
    static List<List<String>> stupid_authors;
    
    String xmlFile;
    String tmpValue;
    Record record;
    
    SimpleDateFormat sdf= new SimpleDateFormat("yy-MM-dd");
    
    public XMLP(String xmlFile) {
        this.xmlFile = xmlFile;
        records = new ArrayList<Record>();
        authors = new HashSet<String>();
        booktitles = new HashSet<String>();
        publishers = new HashSet<String>();
        documents = new HashSet<String>();
        stupid_authors = new ArrayList<List<String>>();
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
        	String[] temp = r.toPeople().trim().split(",");
        	for (int i = 0; i < temp.length; i++){
        		{
        			if (!temp[i].isEmpty()){
        		        authors.add(temp[i]);
        			}
        		}
        	}
        }
    	
        int j = 1; 
        for (String author: authors){
     	   
            for (int i = 0; i < stupid_authors.size(); i++){
         	   
         	  if (stupid_authors.get(i).contains(author)){
         		  
         		  record.editor_id = Integer.toString(j);
         		  System.out.println(record.editor_id);
                  j++;


             	  break;

         	  }
            }
            
            
        }
//    	for (Record r : records){
//
//    		System.out.println(r.toDocuments());
//    	}
    }
    @Override
    public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {
        // if current element is book , create new book
        // clear tmpValue on start of element

        if ((elementName.equalsIgnoreCase("incollection") && attributes.getQName(0).equalsIgnoreCase("mdate"))
            ||(elementName.equalsIgnoreCase("book") && attributes.getQName(0).equalsIgnoreCase("mdate"))
            ||(elementName.equalsIgnoreCase("inproceedings") && attributes.getQName(0).equalsIgnoreCase("mdate"))
            ||(elementName.equalsIgnoreCase("proceedings") && attributes.getQName(0).equalsIgnoreCase("mdate"))	
        	)
        {
            record = new Record();
            for (int i = 0; i < record.genres.size(); i++){
            	if (elementName.equals(record.genres.get(i))){
            		record.genre_id = Integer.toString(i+1);
            	}
          }
        }
    }
    
    @Override
    public void endElement(String s, String s1, String element) throws SAXException {
        // if end of book element add to list
        if (element.equals("incollection") 
        		|| element.equals("book")
        		|| element.equals("inproceedings")
        		|| element.equals("proceedings")) {
        	records.add(record);
        }
        if (element.equalsIgnoreCase("author") || element.equalsIgnoreCase("editor")) {

            record.authors_editors.add(tmpValue);
            
            stupid_authors.add(record.authors_editors);

            
        }
        if (element.equalsIgnoreCase("booktitle")) {
        	record.booktitle = tmpValue;
        	booktitles.add(record.booktitle);
        	
        }
        if(element.equalsIgnoreCase("publisher")){
        	record.publisher = tmpValue;
        	publishers.add(record.publisher);
        }
        if(element.equalsIgnoreCase("title")){
        	record.title = tmpValue;
        }
        if(element.equalsIgnoreCase("pages")){
        	
        	String[] pgs = tmpValue.split("-");
        	
        	if (pgs.length<2){
        	   record.start_page = pgs[0];
        	   record.end_page = "";
        	}
        	else if (pgs.length==2){
        	   record.start_page = pgs[0];
          	   record.end_page = pgs[1];
        	}
        }
        if(element.equalsIgnoreCase("year")){
        	record.year = tmpValue;
        }
        if(element.equalsIgnoreCase("volume")){
        	record.volume = tmpValue;
        }
        if(element.equalsIgnoreCase("number")){
        	record.number = tmpValue;
        }
        if(element.equalsIgnoreCase("url")){
        	record.url = tmpValue;
        }
        if(element.equalsIgnoreCase("ee")){
        	record.ee = tmpValue;
        }
        if(element.equalsIgnoreCase("cdrom")){
        	record.cdrom = tmpValue;
        }
        if(element.equalsIgnoreCase("crossref")){
        	record.crossref = tmpValue;
        }
        if(element.equalsIgnoreCase("isbn")){
        	record.isbn = tmpValue;
        }
        if(element.equalsIgnoreCase("series")){
        	record.series = tmpValue;
        }
        if(element.equalsIgnoreCase("cite")){
        	if (!tmpValue.equals("..."))
        	    record.cite.add(tmpValue);
        }
    }
    @Override
    public void characters(char[] ac, int i, int j) throws SAXException {
        tmpValue = new String(ac, i, j);     

    }
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        new XMLP("dblp-data.xml");
        

      
        
//        PrintWriter writer = new PrintWriter("publishers.sql", "UTF-8");
//        
//      for (String publisher_p:publishers)
//      {
//
//      	String insert = "INSERT INTO tbl_publisher (publisher_name) VALUES(\""+publisher_p.replaceAll("\"","") +"\");"; 
//          writer.println(insert);
//
//      	
//      }
//      writer.close();
        
//        for (String bookt:booktitles )
//        {
//
//        	String insert = "INSERT INTO tbl_booktitle (title) VALUES(\""+bookt.replaceAll("\"","") +"\");"; 
//            writer.println(insert);
//
//        	
//        }
//        writer.close();
        
//        for (String author:authors )
//        {
//
//        	String insert = "INSERT INTO tbl_people (name) VALUES(\""+author.replaceAll("\"","") +"\");"; 
//            writer.println(insert);
//
//        	
//        }
//        writer.close();

    }
}


