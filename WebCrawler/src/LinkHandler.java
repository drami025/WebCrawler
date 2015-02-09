import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

public class LinkHandler{

    
    public static boolean check(String url, ConcurrentHashMap hm){
     // call the robots function to see if it can be crawled.
       
        String cleanUrl = cleanURL(url, "none");

        if(hm.get(cleanUrl) == null)
         return true;

        return false;
    }
        
    public static String cleanURL(String url, String host){
    	
    	if(url.startsWith("javascript:")){
    		return null;
    	}

        String finalURL = url;
        
        String protocol = "http";
        
        if(url == null || url.equals("") || url.equals("./")){
        	return null;
        }
        
        if(url.charAt(0) == '#'){
            return null;
        }
        
        if(url.equals("/")){
            return null;
        }

        if(url.startsWith("/")){
            finalURL = protocol + "://" + host + url;
        }
        else if(!url.contains(host) && !url.contains(protocol)){
        	finalURL = protocol + "://" + host + "/" + url;
        }

        if(url.startsWith("//")){
            //add the www. to link
            finalURL = protocol + ":" + url;
        }
        
        if(url.startsWith("..")){
        	finalURL = protocol + "://" + host + "/" + url;
        }
        
        URL URLClean = null;
        
        try{
        	URLClean = new URL(finalURL);
        }catch(MalformedURLException e){
        	e.printStackTrace();
        }
        
        if(!URLClean.getProtocol().equals("http")) {
        //change the protocol to http:
            return null; 
        }
        
        if(url.contains("?")) {
            finalURL = protocol + "://" + host + "/" + URLClean.getPath() + "/" + URLClean.getQuery();    
        }

        if(url.contains("#")){
            //remove the # and everything after it
            int index = url.indexOf('#');
            finalURL = url.substring(0,index);
            if(finalURL.equals("")){
            	return null;
            }
        }

        if(URLClean.getHost() == url.substring(0) && url.charAt(0) == '/'){
            finalURL = URLClean.getProtocol() + ":/" + url;
        }
        
        try{
        	System.out.println(finalURL);
        	if(isErrorLink(finalURL)){
        		return null;
        	}
        }catch(IOException e){
        	return null;
        }
        
        return finalURL;
    }
    
    public static boolean isErrorLink(String url) throws MalformedURLException, IOException{
    	URL u = new URL(url);
    	HttpURLConnection.setFollowRedirects(false);
    	HttpURLConnection httpCon = (HttpURLConnection) u.openConnection();
    	httpCon.setRequestMethod("GET");
    	httpCon.connect();
    	boolean isError = (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK);
    	httpCon.disconnect();
    	return isError;
    }

}
