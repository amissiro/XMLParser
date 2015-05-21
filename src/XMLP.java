
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
    static List<Record> records;
    static Set<String> authors;
    static Set<String> booktitles;
    static Set<String> publishers;
    static Set<String> documents;
    static List<List<String>> stupid_authors;
    List<String> titles;
    List<String> largebt;
    List<Integer> bt;


    
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
        titles = new ArrayList<String>();
        largebt = new ArrayList<String>();
         bt = new ArrayList<Integer>();

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
        
//        
//        for (Record r : records){      	
//        	for (String author: authors){
//        		if (r.authors_editors.contains(author)){			
//        			r.editor_id = Integer.toString(j);
//        		}
//        	}
//          j++;
//        }
//        

   j = 1;
   for (String bt : booktitles){
     for (Record r : records){
	   if (r.booktitle!=null){
	   if (r.booktitle.equals(bt)){
		   r.booktitle_id = Integer.toString(j);
	    }
	   }
     }
     j++;
   }
   
   j = 1;
   
   for (String pub : publishers){
	 for (Record r : records){
      if (r.publisher!=null){
		   if (r.publisher.equals(pub)){
			   r.publisher_id = Integer.toString(j);
		   }
	  }
      else{    	  
		   r.publisher_id = null;
      }
	 }
       j++;
   }
   
   int i = 1, l = 0;
   for (Record r : records){
	   
	   for (String author : authors){
	   if (r.authors_editors.contains(author)){
		   
			 
//	     System.out.println(i + "->" + author + "-->" + (l+1));
	     System.out.println("INSERT INTO tbl_author_document_mapping (doc_id, author_id) VALUES("+i+","+(l+1)+");");
			   
	   }
     	   l++;
	   }
	  l=0;
	i++;   
   }
   System.out.println(records.size());

   
 }
    @Override
    public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {

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
        	largebt.add(record.booktitle);
        	booktitles.add(record.booktitle);
        	
        }
        if(element.equalsIgnoreCase("publisher")){
        	record.publisher = tmpValue;
        	publishers.add(record.publisher);
        }
        if(element.equalsIgnoreCase("title")){
        	record.title = tmpValue;
        	titles.add(record.title);
        }
        if(element.equalsIgnoreCase("pages")){
        	
        	String[] pgs = tmpValue.split("-");
        	
        	if (pgs.length<2){
        	   record.start_page = pgs[0];
        	   record.end_page = pgs[0];
        	}
        	else if (pgs.length==2){
        	   record.start_page = pgs[0];
          	   record.end_page = pgs[1];
        	}
        	else{
        		record.start_page = "0";
        		record.end_page = "0";
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
        

//        for (Record r : records){
//     	   
//     	  //System.out.println(r.toDocuments());	   
//
//     	  String[] temp = r.toDocuments().split("@split@");
//     	  //String[] temp1 = temp.;
//     	  
//     	  String insert = "INSERT INTO tbl_dblp_document (title,start_page,end_page,year,volume,number,url,ee,cdrom,cite,crossref,isbn,series,editor_id,booktitle_id,genre_id,publisher_id)VALUES(\""
//     			  		  + temp[0].replaceAll("\"","")+"\","
//     			  		  + "\""+ temp[1] + "\","
//     			  		  + "\""+ temp[2] + "\","
//     			  		  + temp[3] + ","
//     			  		  + "\""+temp[4] + "\","
//     			  		  + temp[5] + ","
//     			  		  + "\""+temp[6].replaceAll("\"","")+"\","
//     			  		  + "\""+temp[7].replaceAll("\"","")+"\","
//     			  		  + "\""+temp[8].replaceAll("\"","")+"\","
//     			  		  + "\""+temp[9].replaceAll("\"","")+"\","
//     			  		  + "\""+temp[10].replaceAll("\"","")+"\","
//     			  		  + "\""+temp[11].replaceAll("\"","")+"\","
//     			  		  + "\""+temp[12].replaceAll("\"","")+"\","
//     			  		  + temp[13] + ","
//     			  		  + temp[14] + ","
//     			  		  + temp[15] + ","
//     			  		  + temp[16] +");";
//     	  
//     	  System.out.println(insert);
//        }
//        
        
        
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


