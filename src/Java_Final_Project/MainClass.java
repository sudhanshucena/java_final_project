package Java_Final_Project;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;

public class MainClass {

	public static void main(String[] args) {
//		DOMParser obj = new DOMParser();
//		obj.generatePostsMap();
//		obj.generateUserMap();
//		obj.sortQuestionsMap();
//		obj.sortAnswersMap();

		try {
			
			XMLReader xr = XMLReaderFactory.createXMLReader();
			SAXHandlerUserXML handler = new SAXHandlerUserXML();
			xr.setContentHandler(handler);
			xr.parse("users.xml");
			SAXHandlerPosts postsHandler = new SAXHandlerPosts();
			xr.setContentHandler(postsHandler);
			xr.parse("posts.xml");
			System.out.println("\n---------Top 10 Users by Questions Posted--------");
			postsHandler.sortQuestionsAnswers(handler.getIdList(), handler.getIdName(),postsHandler.getPostsMapQuestions());
			System.out.println("\n--------Top 10 Users by Answers Posted--------");
			postsHandler.sortQuestionsAnswers(handler.getIdList(), handler.getIdName(),postsHandler.getPostsMapAnswers());

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
