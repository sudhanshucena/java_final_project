package Java_Final_Project;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;

public class DOMParser implements Parser{
	private File xmlFile;
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	Document document;
	NodeList nList;
	
	DOMParser(String filename){
			this.xmlFile =  new File (filename);
			this.factory = DocumentBuilderFactory.newInstance();
			try {
				this.builder = factory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@Override
	public
	void parse() {
		
		try {
			this.document = builder.parse(xmlFile);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.getDocumentElement().normalize();
		System.out.println("Root element :" + document.getDocumentElement().getNodeName());
		nList = document.getElementsByTagName("row");
		Node nNode = nList.item(8);
		System.out.println(nNode.getNodeName());
		Element element = (Element) nNode;
		System.out.println(element.getAttribute("Id"));
		
		
	}
	
	public static void main (String args[]){
		DOMParser obj = new DOMParser("users.xml");
		obj.parse();
	}

}	


