import java.util.ArrayList;
import java.util.List;


public class Record {
 
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
	   public String cite;
	   public String crossref;
	   public String isbn;
	   public String series;
	   public String editor_id;
	   
	   public String type;
	   
	   
	   public Record(){
		   
		   authors_editors = new ArrayList<String>();
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
	    

}
