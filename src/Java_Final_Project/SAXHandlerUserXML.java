package Java_Final_Project;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandlerUserXML extends DefaultHandler{

	//local variables for some reason inserting into the map throws NUllPointerException
	//	so have used two arrays and mapped them using index values
	List<Integer> idList = new ArrayList<Integer>();
	List<String> idName = new ArrayList<String>();
	
	/*
     * Every time the parser encounters the beginning of a new element,
     * it calls this method, which resets the string buffer
     */ 
	@Override
    public void startElement(String uri, String localName,
                  String elementName, Attributes attributes) throws SAXException {
        if (elementName.equalsIgnoreCase("row")) {
        	  try{
        		  Integer id = Integer.parseInt(attributes.getValue("Id"));
        		  idList.add(id);
        		  idName.add(attributes.getValue("DisplayName"));
        	  }
        	  catch(NullPointerException e){
        		  System.out.println(attributes.getValue("Id"));
        	  }
        }
    }

	
	//Getter for the Lists
	public List<Integer> getIdList() {
		return idList;
	}

	public List<String> getIdName() {
		return idName;
	}
	
	//Explicit function to iterate over the elements  USED FOR MANUAL DEBUGGING
	public void display(){
		for(int i = 0; i < idList.size();i++){
			System.out.println(idList.get(i)+"="+idName.get(i));
		}
	}
}//SAXParser class ends here



	
