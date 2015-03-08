public class Pair{
	//hello julia
    private int hops;
    private String urlLink;

    public Pair(int num, String url){
        hops = num;
        urlLink = url;
    }

    public int getNumHops(){
        return hops;
    }

    public String getLink(){
        return urlLink;
    }
}
