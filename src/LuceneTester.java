import java.io.IOException;
import java.util.Scanner;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class LuceneTester {
	
   String indexDir = "F:\\Lucene\\Index";
   String dataDir = "F:\\Lucene\\Data";
   Indexer indexer;
   Searcher searcher;
   
   public static void main(String[] args) {
      LuceneTester tester;
      Scanner s=new Scanner(System.in);
      System.out.println("Enter your query:");
      String str=s.nextLine();
      s.close();
      try {
         tester = new LuceneTester();
         tester.createIndex();
         tester.searchUsingFuzzyQuery(str);
      } catch (IOException e) {
         e.printStackTrace();
      } catch (ParseException e) {
         e.printStackTrace();
      }
   }
   
   private void createIndex() throws IOException{
	      indexer = new Indexer(indexDir);
	      int numIndexed;
	      long startTime = System.currentTimeMillis();	
	      numIndexed = indexer.createIndex(dataDir, new TextFileFilter());
	      long endTime = System.currentTimeMillis();
	      indexer.close();
	      System.out.println(numIndexed+" File(s) indexed. Time taken: "
	         +(endTime-startTime)+" ms");		
	   }
  
   private void searchUsingFuzzyQuery(String searchQuery)
		      throws IOException, ParseException{
		      searcher = new Searcher(indexDir);
		      long startTime = System.currentTimeMillis();
		      //create a term to search file name
		      Term term = new Term(LuceneConstants.CONTENTS, searchQuery);
		      //create the term query object
		      Query query = new FuzzyQuery(term,0,0);
		      //do the search
		      TopDocs hits = searcher.search(query);
		      long endTime = System.currentTimeMillis();

		      System.out.println(hits.totalHits +
		         " document(s) found. Time taken: " + (endTime - startTime) + "ms");
		      for(ScoreDoc scoreDoc : hits.scoreDocs) {
		         Document doc = searcher.getDocument(scoreDoc);
		         System.out.print("Score: "+ scoreDoc.score + ".\t");
		         System.out.println("File: "+ doc.get(LuceneConstants.FILE_PATH));
		      }
		      searcher.close();
		   }
		}