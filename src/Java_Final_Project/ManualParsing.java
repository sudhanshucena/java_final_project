/**
 * 
 */
package Java_Final_Project;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManualParsing{
	// Local Variables initialised when the object is created 
	BufferedReader br;
	String idPattern,betweenQuotes,displayName,ownerUserId,postTypeId;
	Pattern id,quotes,name,number,postType,ownerId;
	Map<Integer,String> userMap;
	Map<Integer,Integer> postsMapQuestions,postsMapAnswers;
	
	public ManualParsing() {
		// Create a Pattern object
		//For users.xml
		idPattern = "(Id=\"[0-9]+\")";
		displayName = "(DisplayName=\".*?\")";
		
		//for posts.xml
		ownerUserId = "(OwnerUserId=\"[0-9]+\")";
		postTypeId = "(PostTypeId=\"[0-9]+\")";
		betweenQuotes = "\"(.*?)\"";
		
		ownerId = Pattern.compile(ownerUserId);
		postType = Pattern.compile(postTypeId);
	    id = Pattern.compile(idPattern);
	    quotes = Pattern.compile(betweenQuotes);
	    name = Pattern.compile(displayName);
	    number = Pattern.compile("([0-9])+");
	    userMap =new  HashMap<Integer,String>();
	    postsMapQuestions = new HashMap<Integer,Integer>();
	    postsMapAnswers = new HashMap<Integer,Integer>();
	    
	}
	
	// read line from file and match against
	//	the regular expression patterns
	void parse(String fileName1,String fileName2) {
		String sCurrentLine;
		br = null;
		
		//first read the fileName1
		try {
			br = new BufferedReader(new FileReader(fileName1));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File provided as the first argument is not found");
		}
		try {
			//For Users.xml
			//read current line and then first extract the attributes and then the
			//	data between the double quotes
			while ((sCurrentLine = br.readLine()) != null) {
				Matcher m = id.matcher(sCurrentLine);
				Matcher dispName = name.matcher(sCurrentLine);
				Matcher dbQuotes,num;
				if (m.find() && dispName.find()){
					num = number.matcher(m.group());
					dbQuotes = quotes.matcher(dispName.group());
					num.find();
					dbQuotes.find();
					userMap.put(Integer.parseInt(num.group()), dbQuotes.group());
				}
			}
			} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Some Error in Reading the referred file");
		}
		catch (IllegalStateException e){
			System.out.println("Illegal call to the function m.find() or pattern not found");
		}
		
		
		try {
			//read the second file
			br = new BufferedReader(new FileReader(fileName2));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File provided as the first argument is not found");
		}
		
		try {
			//For posts.xml
			//read current line and then first extract the attributes and then the
			//	data between the double quotes
			while ((sCurrentLine = br.readLine()) != null) {
				Matcher m = postType.matcher(sCurrentLine);
				Matcher owner = ownerId.matcher(sCurrentLine);
				Matcher ownId,postNum;
				if (m.find() && owner.find()){
					ownId = number.matcher(owner.group());
					postNum = number.matcher(m.group());
					ownId.find();
					postNum.find();
					int key = Integer.parseInt(postNum.group());
					int own = Integer.parseInt(ownId.group());
					if (key == 1){
						//if the post is a question
						if(postsMapQuestions.containsKey(own)){
							postsMapQuestions.put(own,postsMapQuestions.get(own)+1);
						}
						else{
							postsMapQuestions.put(own, 1);
						}
					}
					else{
						//this post is an answer
						if(postsMapAnswers.containsKey(own)){
							postsMapAnswers.put(own,postsMapAnswers.get(own)+1);
						}
						else{
							postsMapAnswers.put(own, 1);
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Some Error in Reading the referred file");
		}
		catch (IllegalStateException e){
			System.out.println("Illegal call to the function m.find() or pattern not found");
		}
	}
	
	void sort(Map<Integer, Integer> map){
		Comparator<Integer> comparator = new MapValueComparator(map);
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>(10, comparator);
		for(Integer key : map.keySet()){
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
        	if(userMap.containsKey(val))
        		System.out.println(userMap.get(val)+"="+map.get(val));
        }
	}

	public Map<Integer, Integer> getPostsMapQuestions() {
		return postsMapQuestions;
	}

	public Map<Integer, Integer> getPostsMapAnswers() {
		return postsMapAnswers;
	}

	
	public void execute() {
		this.parse("users.xml","posts.xml");
		System.out.println("\n---------Top 10 Users by Questions Posted--------");
		this.sort(this.getPostsMapQuestions());
		System.out.println("\n--------Top 10 Users by Answers Posted--------");
		this.sort(this.getPostsMapAnswers());
	}
	
}


