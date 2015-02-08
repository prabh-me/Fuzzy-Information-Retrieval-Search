import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


public class Searcher {
	
   IndexSearcher indexSearcher;
   Query query;

   @SuppressWarnings("deprecation")
public Searcher(String indexDirectoryPath) throws IOException{
      Directory indexDirectory = 
         FSDirectory.open(new File(indexDirectoryPath));
      indexSearcher = new IndexSearcher(indexDirectory);
   }
   
   public TopDocs search(Query query) throws IOException, ParseException{
      return indexSearcher.search(query, LuceneConstants.MAX_SEARCH);
   }

   public Document getDocument(ScoreDoc scoreDoc) 
      throws CorruptIndexException, IOException{
     return indexSearcher.doc(scoreDoc.doc);	
   }

   public void close() throws IOException{
      indexSearcher.close();
   }
}