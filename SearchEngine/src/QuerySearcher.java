import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Terms;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.GradientFormatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


public class QuerySearcher {
	
	//private final static String INDEX_DIR = "indexTest";
	//private final static String CRAWLED_HTML = "/home/daniel/Documents/Programming/cs172/cs172_wb/newcrawled";
	
	private final static String INDEX_DIR = "indexOther";
	private final static String CRAWLED_HTML = "/home/daniel/Documents/Programming/cs172/cs172_wb/newcrawled";
	
	
	public static void main(String[] args) throws IOException, ParseException{
		System.out.println("Enter a query");
	  	
	  	BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
	  	String querystr = bf.readLine();
	  	
	    Analyzer analyzer = new StandardAnalyzer();
	    // 1. create the index
	    //Directory index = new RAMDirectory();
	    File index = new File(INDEX_DIR);

	    IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_48, analyzer);
	    IndexWriter w = new IndexWriter(FSDirectory.open(index), config);
		//Query q = new QueryParser(Version.LUCENE_48, "body", analyzer).parse(querystr);
		Query q = new MultiFieldQueryParser(Version.LUCENE_4_10_3, new String[]{"body", "title"}, analyzer).parse(querystr);

	    // 3. search
	    int hitsPerPage = 10;
	    //IndexReader reader = DirectoryReader.open(index);
	    IndexReader reader = IndexReader.open(FSDirectory.open(new File(INDEX_DIR)));
	    
	    IndexSearcher searcher = new IndexSearcher(reader);
	    TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
	    searcher.search(q, collector);
	    ScoreDoc[] hits = collector.topDocs().scoreDocs;
	    SimpleHTMLFormatter formatter = new SimpleHTMLFormatter();
	    QueryScorer qs = new QueryScorer(q);
	    qs.setExpandMultiTermQuery(true);
	    Highlighter highlighter = new Highlighter(formatter, qs);
	    highlighter.setTextFragmenter(new SimpleFragmenter(50));
	    
	    String separator = "...";
	    
	    // 4. display results
	    System.out.println("Found " + hits.length + " hits.");
	    for(int i=0;i<hits.length;++i) {
	      int docId = hits[i].doc;
	      Document d = searcher.doc(docId);
	      System.out.print((i + 1) + ". ");
	      System.out.printf("%-30.30s %-30.30s\n", d.get("title"), d.get("url"));
	      
	      String body = d.get("body");
	      Terms tv = reader.getTermVector(docId, "body");
	      TokenStream tokenStream = TokenSources.getTokenStream(tv);
	      String[] result = null;
	      try{
	    	  result = highlighter.getBestFragments(tokenStream, body, 3);
	      }catch(Exception e){
	    	  e.printStackTrace();
	      }
	      
	      System.out.println("\nResult: " + result.length);
	      
	      for(int j = 1; j < result.length; j++){
	    	  System.out.print(result[j] + " ... ");
	      }
	      System.out.println("\n");
	    }
	}
}
