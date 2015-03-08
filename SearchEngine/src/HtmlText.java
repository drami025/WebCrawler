import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HtmlText {
	
	private Document mDoc;
	private String mUrl;
	
	public HtmlText(File file){
		
		mUrl = file.getName().replace("^", "/");
		
		try{
			mDoc = Jsoup.parse(file, "UTF-8", mUrl);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
	public String getText(){	
		String body = mDoc.text().replaceAll("[^A-Za-z0-9 ]", "");
		return body;
	}
	
	public String getTitle(){
		Element title = mDoc.select("title").first();
		
		if(title == null){
			return "";
		}
		
		return title.text();
	}
	
	public String getUrl(){
		return mUrl;
	}
}
