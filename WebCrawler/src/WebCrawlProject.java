
public class WebCrawlProject {
	
	public static void main(String[] args){
		if(args.length != 4){
			System.err.println("Please enter the name of the seed file, "
					+ "the number of pages to be crawled, "
					+ "the maximum number of hops away from the seed URLs, "
					+ "and the directory to store saved files");
			System.exit(-1);
		}
		
		String file = args[0];
		int pages = Integer.parseInt(args[1]);
		int hops = Integer.parseInt(args[2]);
		String directory = args[3];
		
		
	}
}
