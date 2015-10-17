package Java_Final_Project;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandlerUserXML extends DefaultHandler{

	PrintWriter writer;
	List<Integer> idList = new ArrayList<Integer>();
	List<String> idName = new ArrayList<String>();
	
	public void startDocument(){
		System.out.println("begin parsing document");
		try {
			writer = new PrintWriter("usermap.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void endDocument(){
		System.out.println("End parsing document");
		writer.close();
	}
	
	/*
     * Every time the parser encounters the beginning of a new element,
     * it calls this method, which resets the string buffer
     */ 
	
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

	public void display(){
		for(int i = 0; i < idList.size();i++){
			System.out.println(idList.get(i)+"="+idName.get(i));
		}
	}

	public List<String> getIdName() {
		return idName;
	}

}//SAXParser class ends here



	
