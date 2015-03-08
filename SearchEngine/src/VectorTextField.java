import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.analysis.TokenStream;

public class VectorTextField extends Field{

	public static final FieldType NOT_STORED = new FieldType();
	public static final FieldType STORED = new FieldType();
	
	public VectorTextField(String name, Store store) {
	    super(name, store == Store.YES ? STORED : NOT_STORED);
	}
	
	public VectorTextField(String name, String value, Store store) {
	    super(name, value, store == Store.YES ? STORED : NOT_STORED);
	}
	
	public VectorTextField(String name, TokenStream stream) {
	    super(name, stream, NOT_STORED);
	}

	static {
	    NOT_STORED.setIndexed(true);
	    NOT_STORED.setTokenized(true);
	    NOT_STORED.setStoreTermVectorOffsets(true);
	    NOT_STORED.setStoreTermVectors(true);
	    NOT_STORED.setStoreTermVectorPositions(true);
	    NOT_STORED.freeze();

	    STORED.setIndexed(true);
	    STORED.setTokenized(true);
	    STORED.setStored(true);
	    STORED.setStoreTermVectors(true);
	    STORED.setStoreTermVectorPositions(true);
	    STORED.setStoreTermVectorOffsets(true);
	    STORED.freeze();
	}
}
