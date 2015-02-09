import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler {
	
	private ConcurrentHashMap<String, Integer> mAllLinks;
	private ArrayBlockingQueue<String> mLinkQueue;
	//private Queue<String> mLinkQueue;
	private static int mMax_Pages;
	private static int mMax_Hops;
	private String seed;
	private int crawled_pages = 0;
	private int crawled_links = 0;
	private String mDirectory;
	
	public WebCrawler(int pages, int hops, String seedFile, String directory){
		mAllLinks = new ConcurrentHashMap<String, Integer>(pages);
		mMax_Pages = pages - 1;
		mMax_Hops = hops;
		mLinkQueue = new ArrayBlockingQueue<String>(pages);
		mDirectory = directory;
		
		BufferedReader bufferedReader; 
		try{
			bufferedReader = new BufferedReader(new FileReader(seedFile));
			String line = null;
			while((line = bufferedReader.readLine()) != null){
				mLinkQueue.add(line);
				mAllLinks.put(line, hops);
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
//		mLinkQueue.add(seed);
//		mAllLinks.put(seed, hops);
		
		String fileName = mDirectory + "/crawled_url_links.txt";
		
		File file = new File(fileName);
		
		try{
			if(!file.exists()){
				file.createNewFile();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void crawl(){
		try{
			crawl(mAllLinks.get(mLinkQueue.peek()));
		}catch(InterruptedException e){
			e.printStackTrace();
		}catch(MalformedURLException e){
			e.printStackTrace();
		}
	}
	
	public void crawl(int hops) throws InterruptedException, MalformedURLException{
		
			String nextUrl = mLinkQueue.peek();
			
			try{
				getHtml(nextUrl);
			}catch(IOException e){
				e.printStackTrace();
			}
			
			String fileName = mDirectory + "/crawled_url_links.txt";
			
			File file = new File(fileName);
			
			FileWriter fileWriter = null; 
			
			try{
				if(!file.exists()){
					file.createNewFile();
				}
				
				fileWriter = new FileWriter(fileName, true);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(nextUrl + "\n");
				bufferedWriter.close();
				
			}catch(IOException e){
				e.printStackTrace();
			}
		
			System.out.println("Link: " + nextUrl + ", Away from seed: " + (5 - mAllLinks.get(nextUrl)));
		
			if(hops < 0 || crawled_pages == mMax_Pages){
				return;
			}
		
			crawled_pages++;
			//String nextUrl = mLinkQueue.take();
			
			mLinkQueue.remove();
			
			getLinks(nextUrl);
			
			crawl(mAllLinks.get(nextUrl));
	}
	
	
	public String getHtml(String url) throws IOException, MalformedURLException{
		
		URL urlObj = new URL(url);
		
		BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(urlObj.openStream()));
		
		String end = urlObj.getHost().replace('.', '_') + urlObj.getPath().replace('/', '_');
		
		String fileName = mDirectory + "/" + end + ".html";
		
		new File(fileName);
		
		BufferedWriter bufferedWriter = new BufferedWriter (new FileWriter(fileName));
		
		String line;
		while((line = bufferedReader.readLine()) != null){	
			bufferedWriter.write(line + "\n");
		}
		
		bufferedReader.close();
		bufferedWriter.close();
		
		return bufferedReader.toString();
	}
	
	public void getLinks(String nextUrl){
		
		if(!RobotExclusionUtil.robotsShouldFollow(nextUrl)){
			return;
		}
		
		Document doc;
		
		URL url;
		
		int num = 0;
		
		try{
			doc = Jsoup.connect(nextUrl).followRedirects(false).get();
			
			url = new URL(nextUrl);
			String host = url.getHost();
			
			Elements links = doc.select("a[href]");
			
			for(Element link : links){
				String linkString = link.attr("href");
				
				String normalized_link = LinkHandler.cleanURL(linkString, host);
				
				if(normalized_link != null && mAllLinks.get(normalized_link) == null && crawled_links < mMax_Pages){
					mAllLinks.put(normalized_link, mAllLinks.get(nextUrl) - 1);
					//mLinkQueue.put(linkString);
					mLinkQueue.add(normalized_link);
					crawled_links++;
				}
			}
		}catch(IOException e){
			return;
		}/*catch(InterruptedException e){
			e.printStackTrace();
		}*/
	}
	
	public boolean isFinished(){
		return crawled_pages == mMax_Pages;
	}
	
	public void test() throws MalformedURLException{
		
		String urlString = "http://www.indeed.com?indpubnum=3957652204875274&utm_source=publisher&utm_medium=referral&utm_campaign=cnn-partner&utm_term=3+millions+jobs&chnl=3millionjobs";
		
		URL url = new URL(urlString);
		
		Document doc;
		int i = 0;
		
		try{
			doc = Jsoup.connect(urlString).get();
			
			Elements links = doc.select("a[href]");
			
			for(Element link : links){
				String linkString = link.attr("href");
				
				System.out.println("\nLink: " + linkString);
				i++;
			}
			
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		URL test2 = new URL("http://www.google.com");
		
		URI uri = null;
		
		try{
			uri = new URI(test2.getProtocol(), test2.getHost(), null, null, null);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("URI: " + uri.toString());
	
		System.out.println("\nNumber of links: " + i);
	}
	
	
}
