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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

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
	private Map<Integer,Integer> questionData;
	private Map<Integer,Integer> answerData;
	private int postType,ownerId;
	//Parameterised Constructor
	DOMParser(String filename){
			this.xmlFile =  new File (filename);
			this.factory = DocumentBuilderFactory.newInstance();
			user = new User();
			posts = new Posts();
			userMap = new HashMap<Integer,User>();
			postsMap = new HashMap<Integer, Posts>();
			questionData = new HashMap<Integer,Integer>();
			answerData = new HashMap<Integer, Integer>();
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
		if( this.xmlFile.toString() != "users.xml")
			throw new IllegalArgumentException("Incorrect Filename passed as parameter");
		else{
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
	}	
	
	@Override
	public void generatePostsMap(){	
		if( this.xmlFile.toString() != "posts.xml")
			throw new IllegalArgumentException("Incorrect Filename passed as parameter");
		else{
		System.out.println(nList.getLength());
		for (int index = 0 ; index < nList.getLength();index++){
			node = nList.item(index);
			if(node.getNodeType()==Node.ELEMENT_NODE){
			element = (Element)node;
			postType = Integer.parseInt(element.getAttribute("PostTypeId"));
			
			//first map type
			posts.setId(Integer.parseInt(element.getAttribute("Id")));
			posts.setPostTypeId(Integer.parseInt(element.getAttribute("PostTypeId")));
//			posts.setAcceptedAnswerId(Integer.parseInt(element.getAttribute("AcceptedAnswerId")));
			if(element.getAttribute("OwnerUserId")=="")
			{
				//if no user owns the question set the ownerUserId = -2
//				System.out.println(element.getAttribute("Id"));
				posts.setOwnerUserId(-2);
			}
			else
				posts.setOwnerUserId(Integer.parseInt(element.getAttribute("OwnerUserId")));
			postsMap.put(Integer.parseInt(element.getAttribute("Id")), posts);
			
			//second map type
			List<Integer> temp = new ArrayList<Integer>();
//			temp.add(Integer.parseInt(element.getAttribute("Id")));
			temp.add(Integer.parseInt(element.getAttribute("PostTypeId")));
			if(element.getAttribute("OwnerUserId")=="")
			{
				//if no user owns the question set the ownerUserId = -2
				temp.add(-2);
			}
			else
				temp.add(Integer.parseInt(element.getAttribute("OwnerUserId")));
//			System.out.println(temp.get(0)+" "+temp.get(1));
			
			
			// 3rd map type
			//push -2 for no questionId type of post
			questionData.put(-2, 0);
			answerData.put(-2, 0);
			if (postType == 1){
				//this post is a question
				if(element.getAttribute("OwnerUserId")==""){
					//the owner id does not exist
					questionData.put(-2, questionData.get(-2)+1);
				}
				else{
					//		if the data key exists in the map
					if(questionData.get(Integer.parseInt(element.getAttribute("OwnerUserId")))!= null){
						questionData.put(Integer.parseInt(element.getAttribute("OwnerUserId")),questionData.get(Integer.parseInt(element.getAttribute("OwnerUserId")))+1);
					}
					else{//the key doesn't exist in the map
						questionData.put(Integer.parseInt(element.getAttribute("OwnerUserId")),0);
					}
				}
			}
				}//for if(node.getNodeType()==Node.ELEMENT_NODE) 
			}//outer for loop
		System.out.println(postsMap.size());
		Iterator<?> it = questionData.entrySet().iterator();
		while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		}
	}
	
	
	public Map<Integer, User> getUserMap() {
		return userMap;
	}

	public Map<Integer, Posts> getPostsMap() {
		return postsMap;
	}

	public static void main (String args[]){
		DOMParser obj = new DOMParser("posts.xml");
		DOMParser obj2 = new DOMParser("users.xml");
		obj.parse();
		obj.generatePostsMap();
//		obj.generateUserMap();
		obj2.parse();
		obj2.generateUserMap();
//		obj2.generatePostsMap();
	}

}	


