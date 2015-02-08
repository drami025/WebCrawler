import java.io.IOException;
import java.net.MalformedURLException;


public class Test {
	public static void main(String[] args){
		
		WebCrawler web = new WebCrawler(100, 100);
		String webHtml;
		
		try{
			webHtml = web.downloadFile("http://www.google.com/");
		}
		catch(MalformedURLException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		//this is a test
		
		web.printLinks();
	}
}
