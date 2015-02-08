import java.utils.concrrent.ConcurrentHashMap;
import java.net.URL;

public class LinkHandler{

    
    public static boolean check(String url, ConcurrentHashMap hm){
     // call the robots function to see if it can be crawled.
       
        String cleanUrl = cleanURL(url);

        if(hm.get(url) == NULL)
        return  true;        
    }
        
    public static void cleanURL(String url){
       
        try{
            URL URLClean new URL(url);
        }

        catch(MalformedURLException e){
            e.printStackTrace();
        }

        String finalURL;
        
        if(url.startsWith("#")){
            return null;
        }
        
        if(url.equal("/")){
            return null;
        }
        
        if(url.startsWith("/")){
            finalURL = URL.getProtocol() + "://" + URLClean.getHost() + url;
        }

        if(url.startsWith("//")){
            //add the www. to link
            finaURL = URLClrean.getProtocol() + "://" + url;
        }

        if(url.contains("?")) {
            finalURL = URL.getProtocol() + "://" + URL.getHost() + "/" + URL.getPath() + "/" + URL.getQuery();    
        }
        
        if(!URLClean.getProtocol.equals("http")) {
        //change the protocol to http:
            return null; 
        }

         if(url.contains("#")){
            //remove the # and everything after it
            int index = url.indexOf('#');
            finalURL = url.subString(0,index);
        }

        return finalURL;

    }

}
