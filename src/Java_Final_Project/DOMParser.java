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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class DOMParser extends Parser{
	private File xmlFile;
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private Document document;
	private User user;
	private Map<Integer,User> userMap;
	private NodeList nList;
	private Node node;
	private Element element;
	private Map<Integer,Integer> questionData;
	private Map<Integer,Integer> answerData;
	private int postType,ownerId;
	private Comparator<Integer> comparator;
	PriorityQueue<Integer> queue;
	
	//Parameterized Constructor
	DOMParser(String filename){
			this.xmlFile =  new File (filename);
			this.factory = DocumentBuilderFactory.newInstance();
			user = new User();
			userMap = new HashMap<Integer,User>();
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
		if( this.xmlFile.toString() != "users.xml")
			throw new IllegalArgumentException("Incorrect Filename passed as parameter");
		else{
		for (int index = 0 ; index < nList.getLength();index++){
			node = nList.item(index);
			element = (Element)node;
//			System.out.println("user id : " + element.getAttribute("Id") + " Reputation: "+ element.getAttribute("Reputation") + " User Name: " + element.getAttribute("DisplayName"));
			user.setReputation(Integer.parseInt(element.getAttribute("Reputation")));
			user.setUserName(element.getAttribute("DisplayName"));
			userMap.put(Integer.parseInt(element.getAttribute("Id")),user);
		}
		System.out.println(userMap.size());
		}
	}	
	
	@Override
	public void generatePostsMap(){	
		if( this.xmlFile.toString() != "posts.xml")
			throw new IllegalArgumentException("Incorrect Filename passed as parameter");
		else{
		System.out.println(nList.getLength());
		
		//push -2 for no questionId type of post
		questionData.put(-2, 0);
		answerData.put(-2, 0);
		for (int index = 0 ; index < nList.getLength();index++){
			node = nList.item(index);
			element = (Element)node;
			postType = Integer.parseInt(element.getAttribute("PostTypeId"));
			if(element.getAttribute("OwnerUserId")==""){
				ownerId = -2;
//				System.out.println(element.getAttribute("Id"));
			}
			else 
				ownerId = Integer.parseInt(element.getAttribute("OwnerUserId"));

			// 3rd map type
			if (postType == 1){ // this post is a question
				if(questionData.containsKey(ownerId)){
					questionData.put(ownerId,questionData.get(ownerId)+1);
				}
				else{//the key doesn't exist in the map
					questionData.put(ownerId, 1);
					}
			}
			else{//this post is an answer
				if(answerData.containsKey(ownerId)){
					answerData.put(ownerId,answerData.get(ownerId)+1);
				}
				else{//the key doesn't exist in the map
					answerData.put(ownerId,1);
					}
				}
			}//main for loop every row in posts.xml
		answerData.remove(-2);
		questionData.remove(-2);
		}//closing for else error handling
	}//closing for generate postMap()
	
	
	public void sortQuestionsMap(){
		comparator = new MapValueComparator(questionData);
		queue = new PriorityQueue<Integer>(27802, comparator);
		Integer k;
		System.out.println("In sort Question");
		for (Integer key: questionData.keySet()){
			queue.add(key);
		}
		System.out.println("queuesize = "+ queue.size());
		while (queue.size() != 0)
        {
			k=queue.remove();
            System.out.println(k+"="+questionData.get(k));
        }

		
		}
		
	
	
	
	
	
	public Map<Integer, User> getUserMap() {
		return userMap;
	}

	public void setUserMap(Map<Integer, User> userMap) {
		this.userMap = userMap;
	}

	// Main 
	public static void main (String args[]){
		DOMParser obj = new DOMParser("posts.xml");
		DOMParser obj2 = new DOMParser("users.xml");
		obj.parse();
		obj.generatePostsMap();
		obj.sortQuestionsMap();
//		obj.generateUserMap();
		obj2.parse();
		obj2.generateUserMap();
//		obj2.generatePostsMap();
	}

}	


