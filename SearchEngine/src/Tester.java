import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


public class Tester {
	
	//private final static String INDEX_DIR = "indexTest";
	//private final static String CRAWLED_HTML = "/home/daniel/Documents/Programming/cs172/cs172_wb/newcrawled";
	
	private final static String INDEX_DIR = "indexOther";
	private final static String CRAWLED_HTML = "/home/daniel/Documents/Programming/cs172/cs172_wb/newcrawled";
	
	
	public static void main(String[] args) throws IOException, ParseException{
		System.out.println("Enter a query");
	  	Scanner scan = new Scanner(System.in);
	  	String querystr = scan.next();
	  	
	    Analyzer analyzer = new StandardAnalyzer();
	    // 1. create the index
	    //Directory index = new RAMDirectory();
	    File index = new File(INDEX_DIR);

	    IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_48, analyzer);
	    IndexWriter w = new IndexWriter(FSDirectory.open(index), config);
		Query q = new QueryParser(Version.LUCENE_48, "title", analyzer).parse(querystr);

	    // 3. search
	    int hitsPerPage = 10;
	    //IndexReader reader = DirectoryReader.open(index);
	    IndexReader reader = IndexReader.open(FSDirectory.open(new File(INDEX_DIR)));
	    
	    IndexSearcher searcher = new IndexSearcher(reader);
	    TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
	    searcher.search(q, collector);
	    ScoreDoc[] hits = collector.topDocs().scoreDocs;
	    
	    // 4. display results
	    System.out.println("Found " + hits.length + " hits.");
	    for(int i=0;i<hits.length;++i) {
	      int docId = hits[i].doc;
	      Document d = searcher.doc(docId);
	      System.out.print((i + 1) + ". ");
	      System.out.printf("%-30.30s %-30.30s\n", d.get("title"), d.get("url"));
	    }
		
		File directory = new File(CRAWLED_HTML);
		
	}
}
