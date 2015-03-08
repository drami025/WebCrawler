import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.util.packed.PackedInts.Reader;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;


public class Indexer {
	
		//private final static String INDEX_DIR = "indexTest";
		//private final static String CRAWLED_HTML = "/home/daniel/Documents/Programming/cs172/cs172_wb/crawled";
		
		private final static String INDEX_DIR = "indexOther";
		private final static String CRAWLED_HTML = "/home/daniel/Documents/Programming/cs172/cs172_wb/newcrawled";
		private static Analyzer mAnalyzer = new StandardAnalyzer();
		private static IndexWriterConfig mConfig = new IndexWriterConfig(Version.LUCENE_48, mAnalyzer);
		private static File mIndex = new File(INDEX_DIR);
		
		
	  public static void main(String[] args) throws IOException, ParseException {
		  	getDocs();
		  	System.out.println("Done");
		  }

	  
	  	private static void addDoc(IndexWriter w, String title, String body, String url) throws IOException {
		    Document doc = new Document();
		    doc.add(new VectorTextField("title", title, Field.Store.YES));
		    doc.add(new StringField("url", url, Field.Store.YES));
		    doc.add(new VectorTextField("body", body, Field.Store.YES));
		    w.addDocument(doc);
		  }
		  
		  public static void getDocs() throws IOException{
				File directory = new File(CRAWLED_HTML);
				IndexWriter writer = new IndexWriter(FSDirectory.open(mIndex), mConfig);
				
				for(File file : directory.listFiles()){
					HtmlText html = new HtmlText(file);
					String body = html.getText();
					String url = html.getUrl();
					String title = html.getTitle();
					
					addDoc(writer, title, body, url);
				}
				
				writer.close();
		  }
}
