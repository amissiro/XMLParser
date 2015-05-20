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
	        return  this.title + "@^token^@" + this.start_page + "@^token^@" + this.end_page + "@^token^@" + this.year + "@^token^@" +this.volume + "@^token^@"+
	        		this.number + "@^token^@" + this.url + "@^token^@" + this.ee + "@^token^@" + this.cdrom + "@^token^@" + listString + "@^token^@" + this.crossref+"@^token^@"+        		
	                this.isbn + "@^token^@" + this.series + "@^token^@" + this.genre_id;
	    	    
	    }
	    

	    

	    

}
