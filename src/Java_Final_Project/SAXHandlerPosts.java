package Java_Final_Project;


import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandlerPosts extends DefaultHandler{

	int questionType,ownerId;
	HashMap<Integer,Integer> postsMapQuestions = new HashMap<Integer,Integer>();
	HashMap<Integer,Integer> postsMapAnswers = new HashMap<Integer,Integer>();
	
	@Override
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

	
	public void sortQuestionsAnswers(List<Integer> list,List<String> idName, HashMap<Integer,Integer> mapToSort){
		Comparator<Integer> comparator = new MapValueComparator(mapToSort);
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>(10, comparator);
		
		for(Integer key : mapToSort.keySet()){
			if(queue.size()<10){
				queue.add(key);
			}
			else if(comparator.compare(queue.peek(), key) < 0) {
				queue.remove();
				queue.add(key);
			  }
		}
		
        while (queue.size() != 0)
        {
        	Integer val = queue.remove();
        	if(list.contains(val))
        		System.out.println(idName.get(list.indexOf(val))+"="+mapToSort.get(val));
        }
	}


	public HashMap<Integer, Integer> getPostsMapQuestions() {
		return postsMapQuestions;
	}


	public HashMap<Integer, Integer> getPostsMapAnswers() {
		return postsMapAnswers;
	}
}
