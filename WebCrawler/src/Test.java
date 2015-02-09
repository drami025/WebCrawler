import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Test {
	
	private static WebCrawler web;
	public static void main(String[] args){
		
//		WebCrawler web = new WebCrawler(100, 100, "http://www.ucr.edu/");
//		String webHtml;
//		
//		try{
//			webHtml = web.downloadFile("http://www.google.com/");
//		}
//		catch(MalformedURLException e){
//			e.printStackTrace();
//		}
//		catch(IOException e){
//			e.printStackTrace();
//		}
//		
//		//this is a test
//		
//		web.printLinks();
		String seed = "http://www.ucr.edu/";
		
		String file = "/home/daniel/workspace/WebCrawler/src/seed.txt";
		int pages = Integer.parseInt("100");
		int hops = Integer.parseInt("5");
		String directory = "/home/daniel/workspace/url_files";
		
		web = new WebCrawler(pages, hops, file, directory);
		web.crawl();
		
//		Test t = new Test();
//		
//		
//		while(!web.isFinished()){
//			CrawlingThread thread = t.new CrawlingThread();
//			thread.start();
//		}
//		try{
//			web.crawl(1);
//		}catch(InterruptedException e){
//			e.printStackTrace();
//		}catch(MalformedURLException e){
//			e.printStackTrace();
//		}
		
//		try{
//			web.test();
//		}catch(MalformedURLException e){
//			e.printStackTrace();
//		}
//		
//		String urlTest = "http://www.indeed.com?indpubnum=3957652204875274&utm_source=publisher&utm_medium=referral&utm_campaign=cnn-partner&utm_term=3+millions+jobs&chnl=3millionjobs";
//		String output = "";
//		
//		try{
//			URL url = new URL(urlTest);
//			output = url.getProtocol() + "://" + url.getHost() + "/" + url.getPath() + "?" + url.getQuery(); 
//		}
//		catch(MalformedURLException e){
//			e.printStackTrace();
//		}
//		
//		System.out.println("\nOutput is: " + output);
	}
	
	private class CrawlingThread extends Thread{
		public void run(){
			web.crawl();
		}
	}
}
