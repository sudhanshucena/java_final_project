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

	//Parameterized Constructor
	DOMParser(){
			this.factory = DocumentBuilderFactory.newInstance();
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
	public void parse(String fileName) {
		this.xmlFile =  new File (fileName);
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
		this.parse("users.xml");
		for (int index = 0 ; index < nList.getLength();index++){
			user = new User();
			node = nList.item(index);
			element = (Element)node;
//			System.out.println("user id : " + element.getAttribute("Id") + " Reputation: "+ element.getAttribute("Reputation") + " User Name: " + element.getAttribute("DisplayName"));
			user.setReputation(Integer.parseInt(element.getAttribute("Reputation")));
			user.setUserName(element.getAttribute("DisplayName"));
			userMap.put(Integer.parseInt(element.getAttribute("Id")),user);
		}
		System.out.println(userMap.size());
		}
	
	@Override
	public void generatePostsMap(){	
		this.parse("posts.xml");
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
//		System.out.println(answerData.size());
//		System.out.println(questionData.size());
	}//closing for generate postMap()

	public void sortQuestionsMap(){
		Comparator<Integer> comparator = new MapValueComparator(questionData);
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>(questionData.size(), comparator);
		Integer k;
		for (Integer key : questionData.keySet()){
			queue.add(key);
		}
		while (queue.size() != 0){
			k=queue.remove();
			if(userMap.get(k)!=null){
				System.out.println(userMap.get(k).getUserName()+"="+questionData.get(k));
				}			
        	}		
		}

	public void sortAnswersMap(){
		Comparator<Integer> comparator = new MapValueComparator(answerData);
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>(answerData.size(), comparator);
		Integer k;
		for (Integer key : answerData.keySet()){
			queue.add(key);
		}
		while (queue.size() != 0){
			k=queue.remove();
			if(userMap.get(k)!=null){
				System.out.println(userMap.get(k).getUserName()+"="+answerData.get(k));
				}			
        	}	
	}

}	


