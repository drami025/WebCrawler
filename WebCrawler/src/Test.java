import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Test {
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
		
		WebCrawler web = new WebCrawler(20, 1, seed);
		try{
			web.crawl(3);
		}catch(InterruptedException e){
			e.printStackTrace();
		}catch(MalformedURLException e){
			e.printStackTrace();
		}
		
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
}
