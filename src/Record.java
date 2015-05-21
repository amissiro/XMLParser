import java.util.ArrayList;
import java.util.List;


public class Record {
	   public List<String> genres;

	   public List<String> authors_editors;
	   public String booktitle;
	   public String publisher;
	   
	   public String title;
	   public String start_page;
	   public String end_page;
	   public String year;
	   public String volume;
	   public String number;
	   public String url;
	   public String ee;
	   public String cdrom;
	   public List<String> cite;
	   public String crossref;
	   public String isbn;
	   public String series;
	   public String editor_id;
	   public String genre_id;
	   public String booktitle_id;
	   public String publisher_id;
	   public String type;
	   
	   
	   public Record(){
		   
		   authors_editors = new ArrayList<String>();
		   cite = new ArrayList<String>();
		   genres = new ArrayList<String>();
	    	genres.add("article");
	    	genres.add("inproceedings");
	    	genres.add("proceedings");
	    	genres.add("book");
	    	genres.add("incollection");
	    	genres.add("phdthesis");
	    	genres.add("mastersthesis");
	    	genres.add("www");
	   }
   
	    public String toPeople() {
	    	String listString = "";

	    	for (int i = 0 ; i < authors_editors.size(); i++)
	    	{

	    		if (i==authors_editors.size()-1){
	    			
	    			listString += authors_editors.get(i);
	    		}
	    		else{
		    		listString += authors_editors.get(i) + ",";
	    		}
	       	}
	    	
	        return  listString;
	    }
	    
	    public String toDocuments() {
	    	
	    	String listString = null;

	    	for (int i = 0; i < this.cite.size(); i ++){
	    		
	    		if (i==this.cite.size()-1){	
	    			listString += this.cite.get(i);
	    		}
	    		else{
		    		listString += this.cite.get(i) + ",";
	    		}
	    	}
	    	
	        return  this.title + "@split@" + this.start_page + "@split@" + this.end_page + "@split@" + this.year + "@split@" +this.volume + "@split@"+
	        		this.number + "@split@" + this.url + "@split@" + this.ee + "@split@" + this.cdrom + "@split@" + listString + "@split@" + this.crossref+"@split@"+        		
	                this.isbn + "@split@" + this.series + "@split@" + this.editor_id + "@split@"+ this.booktitle_id + "@split@" + this.genre_id + "@split@" + this.publisher_id;
	    	    
	    }
}
