package Java_Final_Project;


import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandlerPosts extends DefaultHandler{

	int questionType,ownerId;
	HashMap<Integer,Integer> postsMapQuestions = new HashMap<Integer,Integer>();
	HashMap<Integer,Integer> postsMapAnswers = new HashMap<Integer,Integer>();
	
	public void endDocument(){
//		
//		System.out.println(postsMapQuestions.size());
//		System.out.println(postsMapAnswers.size());
	}
	
	
	public void startElement(String uri, String localName,
            String elementName, Attributes attributes) throws SAXException {
	
		if (elementName.equalsIgnoreCase("row") && attributes.getValue("OwnerUserId") != null ){
			try{
				questionType = Integer.parseInt(attributes.getValue("PostTypeId"));
				ownerId = Integer.parseInt(attributes.getValue("OwnerUserId"));
				}
			catch(NullPointerException e){
				System.out.println(attributes.getValue("Id"));
			}
			if(questionType==1){
				//this post is a question
				if(postsMapQuestions.containsKey(ownerId)){
					postsMapQuestions.put(ownerId,postsMapQuestions.get(ownerId)+1);
				}
				else{//the key doesn't exist in the map
					postsMapQuestions.put(ownerId, 1);
					}
			}
			else{
				//this post is an answer
				if(postsMapAnswers.containsKey(ownerId)){
					postsMapAnswers.put(ownerId,postsMapAnswers.get(ownerId)+1);
				}
				else{//the key doesn't exist in the map
					postsMapAnswers.put(ownerId, 1);
					}
			}
		}
	}

}
