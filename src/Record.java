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
	   
	   @Override
	    public String toString() {
	        return "record:: booktitle = "+this.booktitle+" publisher = " + this.publisher +
	        		"authors = " + this.authors_editors + " -> "+this.type;
	    }
}
