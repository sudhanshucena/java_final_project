package Java_Final_Project;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.Scanner;

public class MainClass {

	public static void main(String[] args) {
		  
		  String input;
		  long startTime,endTime;
	      Scanner scanIn = new Scanner(System.in);
	      System.out.println("Enter the choice of parser to use\n1.DOM\n2.SAX\n3.Manual");
	      input = scanIn.nextLine();
	      scanIn.close();   		
		switch(input){
		case "1":DOMParser obj = new DOMParser();
				startTime = System.nanoTime();
				obj.generatePostsMap();
				obj.generateUserMap();
				obj.sortQuestionsMap();
				endTime = System.nanoTime();
				obj.sortAnswersMap();
				System.out.println("\n-----Total Time taken = "+(endTime-startTime)/1000000 +"ms");
				break;
		case "2":
			try {
				
				XMLReader xr = XMLReaderFactory.createXMLReader();
				SAXHandlerUserXML handler = new SAXHandlerUserXML();
				xr.setContentHandler(handler);
				startTime = System.nanoTime();
				xr.parse("users.xml");
				SAXHandlerPosts postsHandler = new SAXHandlerPosts();
				xr.setContentHandler(postsHandler);
				xr.parse("posts.xml");
				System.out.println("\n---------Top 10 Users by Questions Posted--------");
				postsHandler.sortQuestionsAnswers(handler.getIdList(), handler.getIdName(),postsHandler.getPostsMapQuestions());
				System.out.println("\n--------Top 10 Users by Answers Posted--------");
				postsHandler.sortQuestionsAnswers(handler.getIdList(), handler.getIdName(),postsHandler.getPostsMapAnswers());
				endTime = System.nanoTime();
				System.out.println("\n-----Total Time taken = "+(endTime-startTime)/1000000 +"ms");


				} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
				break;
		case "3":
				startTime = System.nanoTime();
				ManualParsing manualParseHandler = new ManualParsing();
				manualParseHandler.execute();
				endTime = System.nanoTime();
				System.out.println("\n-----Total Time taken = "+(endTime-startTime)/1000000 +"ms");
				break;
		default:
			System.out.println("Wrong Input");
				
		}
		

		
		

	}

}
