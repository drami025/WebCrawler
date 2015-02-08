import java.util.concurrent.ConcurrentHashMap;
import java.net.URL;
import java.net.MalformedURLException;

public class LinkHandler{

    
    public boolean check(String url, ConcurrentHashMap hm){
     // call the robots function to see if it can be crawled.
       
        String cleanUrl = cleanURL(url);

        if(hm.get(cleanUrl) == null)
         return true;

        return false;
    }
        
    public String cleanURL(String url){
      
        URL URLClean = null;
        try{
            URLClean = new URL(url);
        }

        catch(MalformedURLException e){
            e.printStackTrace();
        }

        String finalURL = null;
        
        if(url.startsWith("#")){
            return null;
        }
        
        if(url.equals("/")){
            return null;
        }

        if(url.startsWith("/")){
            finalURL = URLClean.getProtocol() + "://" + URLClean.getHost() + url;
        }

        if(url.startsWith("//")){
            //add the www. to link
            finalURL = URLClean.getProtocol() + "://" + url;
        }

        if(url.contains("?")) {
            finalURL = URLClean.getProtocol() + "://" + URLClean.getHost() + "/" + URLClean.getPath() + "/" + URLClean.getQuery();    
        }
        
        if(!URLClean.getProtocol().equals("http")) {
        //change the protocol to http:
            return null; 
        }

         if(url.contains("#")){
            //remove the # and everything after it
            int index = url.indexOf('#');
            finalURL = url.substring(0,index);
        }

        if(URLClean.getHost() == url.substring(0) && url.charAt(0) == '/'){
            finalURL = URLClean.getProtocol() + ":/" + url;
        }

        return finalURL;
        
    }

}
