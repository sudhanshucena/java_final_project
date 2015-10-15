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
import java.util.HashMap;
import java.util.Map;

public class DOMParser extends Parser{
	private File xmlFile;
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private Document document;
	private User user;
	private Posts posts;
	private Map<Integer,User> userMap;
	private Map<Integer, Posts> postsMap ;
	private NodeList nList;
	private Node node;
	private Element element;
	
	
	//Parameterised Constructor
	DOMParser(String filename){
			this.xmlFile =  new File (filename);
			this.factory = DocumentBuilderFactory.newInstance();
			user = new User();
			posts = new Posts();
			userMap = new HashMap<Integer,User>();
			postsMap = new HashMap<Integer, Posts>();
			try {
				this.builder = factory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@Override
	public void parse() {
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
		nList = document.getElementsByTagName("row");
		System.out.println(nList.getLength());
	}
	
	@Override
	public void generateUserMap(){
		
		//		Random testing code to see the xml traversal
		//		Node nNode = nList.item(8);
		//		System.out.println(nNode.getNodeName());
		//		Element element = (Element) nNode;
		//		System.out.println(element.getAttribute("Id"));
		//		node = nList.item(2);
		//		System.out.println(node.getNodeType());
		
		for (int index = 0 ; index < nList.getLength();index++){
			node = nList.item(index);
			if(node.getNodeType()==Node.ELEMENT_NODE){
				element = (Element)node;
//				System.out.println("user id : " + element.getAttribute("Id") + " Reputation: "+ element.getAttribute("Reputation") + " User Name: " + element.getAttribute("DisplayName"));
				user.setReputation(Integer.parseInt(element.getAttribute("Reputation")));
//				System.out.println(user.getReputation());
				user.setUserName(element.getAttribute("DisplayName"));
//				user.setId(element.getAttribute("Id"));
				userMap.put(Integer.parseInt(element.getAttribute("Id")),user);
			}
		}
		System.out.println(userMap.size());
//		System.out.println(userMap.keySet());
	}	
	
	@Override
	public void generatePostsMap(){
		
		System.out.println(nList.getLength());
		for (int index = 0 ; index < nList.getLength();index++){
			node = nList.item(index);
			if(node.getNodeType()==Node.ELEMENT_NODE){
			element = (Element)node;
			posts.setId(Integer.parseInt(element.getAttribute("Id")));
//			posts.setAcceptedAnswerId(Integer.parseInt(element.getAttribute("AcceptedAnswerId")));
			if(element.getAttribute("OwnerUserId")=="")
			{
				//if no user owns the question set the ownerUserId = -2
				posts.setOwnerUserId(-2);
			}
			else
				posts.setOwnerUserId(Integer.parseInt(element.getAttribute("OwnerUserId")));
			postsMap.put(Integer.parseInt(element.getAttribute("Id")), posts);
		}
	}
		System.out.println(postsMap.size());
}
	
	
	public Map<Integer, User> getUserMap() {
		return userMap;
	}

	public Map<Integer, Posts> getPostsMap() {
		return postsMap;
	}

	public static void main (String args[]){
		DOMParser obj = new DOMParser("posts.xml");
		obj.parse();
		obj.generatePostsMap();
	}

}	


