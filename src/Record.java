import java.util.ArrayList;
import java.util.List;


public class Record {
 
	   public List<String> authors_editors;
	   public String booktitle;
	   public String publisher;
	   
	   public String start_page;
	   public String end_page;
	   public String type;
	   
	   
	   public Record(){
		   
		   authors_editors = new ArrayList<String>();
	   }
//	   
//	   @Override
//	    public String toString() {
//	        return "record:: booktitle = "+this.booktitle+" publisher = " + this.publisher +
//	        		"authors = " + this.authors_editors + " -> "+this.type;
//	    }
//	   
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
	    
//	    public String toBookTitle() {
//	    	
//	    	String listString = "";
//            listString += booktitle;
//            
//	        return  listString;
//	    }
}
