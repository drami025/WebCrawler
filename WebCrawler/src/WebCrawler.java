import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler {
	
	private ConcurrentHashMap<String, Integer> mAllLinks;
	private ArrayBlockingQueue<String> mLinkQueue;
	private static int mMax_Pages;
	private static int mMax_Hops;
	private String seed;
	
	public WebCrawler(int pages, int hops, String seed){
		mMax_Pages = pages;
		mMax_Hops = hops;
		mLinkQueue = new ArrayBlockingQueue<String>(1000);
		mLinkQueue.add(seed);
		mAllLinks.put(seed, hops);
	}
	
	public void crawl(int hops) throws InterruptedException, MalformedURLException{
		
			if(hops == 0){
				return;
			}
		
			String nextUrl = mLinkQueue.take();
			
			getLinks(nextUrl);
			
			crawl(mAllLinks.get(nextUrl));
	}
	
	
	public String getHtml(String url) throws IOException, MalformedURLException{
		
		URL urlObj = new URL(url);
		
		BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(urlObj.openConnection().getInputStream()));
		
		String fileName = "/home/daniel/workspace/url_files/" + url;
		
		new File(fileName);
		
		BufferedWriter bufferedWriter = new BufferedWriter (new FileWriter(fileName));
		
		while(bufferedReader.ready()){
			
			String line = bufferedReader.readLine();
			bufferedWriter.write(line + "\n");
		}
		
		return bufferedReader.toString();
	}
	
	public void getLinks(String nextUrl){
		
		if(!RobotExclusionUtil.robotsShouldFollow(nextUrl)){
			return;
		}
		
		try{
			getHtml(nextUrl);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		Document doc;
		
		int num = 0;
		
		try{
			doc = Jsoup.connect(nextUrl).get();
			
			Elements links = doc.select("a[href]");
			
			for(Element link : links){
				String linkString = link.attr("href");
				
				mAllLinks.put(linkString, mAllLinks.get(nextUrl) - 1);
				mLinkQueue.put(linkString);
			}
		}catch(IOException e){
			e.printStackTrace();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
}
