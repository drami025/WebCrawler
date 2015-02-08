import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.HashMap;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler {
	
	static int mMax_Pages;
	static int mMax_Hops;
	
	public WebCrawler(int pages, int hops){
		mMax_Pages = pages;
		mMax_Hops = hops;
	}
	
	public String downloadFile(String url) throws IOException, MalformedURLException{
		
		URL urlObj = new URL(url);
		
		BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(urlObj.openConnection().getInputStream()));
		
		String fileName = "/home/daniel/workspace/url_files/url_content.txt";
		
		new File(fileName);
		
		BufferedWriter bufferedWriter = new BufferedWriter (new FileWriter(fileName));
		
		while(bufferedReader.ready()){
			
			String line = bufferedReader.readLine();
			bufferedWriter.write(line + "\n");
		}
		
		return bufferedReader.toString();
	}
	
	public void printLinks(){
		
		HashMap<String, Boolean> allLinks = new HashMap();
		
		Document doc;
		String url = "http://www.cnn.com";
		
		allLinks.put(url + "/", true);
		
		int num = 0;
		
		try{
			doc = Jsoup.connect(url).get();
			
			Elements links = doc.select("a[href]");
			
			for(Element link : links){
				String linkString = link.attr("href");
				
				System.out.println("\n" + linkString);
//					
//				if(linkString.charAt(0) == '#')
//					continue;
//				
//				if(linkString.length() > 1 && linkString.substring(0, 2) == "//"){
//					linkString = "http:" + linkString;
//				}
//				
//				if(linkString.charAt(0) == '/' && !linkString.contains("www")){
//					linkString = url + linkString;
//				}
//				
//				if(allLinks.get(linkString) == null){
//					num++;
//					allLinks.put(linkString, true);
//					
//					System.out.println("\nLink: " + linkString);
//				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}	
		
		System.out.println("\nNumber of links: " + num);
	}
	
}
