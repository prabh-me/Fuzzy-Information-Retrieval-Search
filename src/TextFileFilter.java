import java.io.File;
import java.io.FileFilter;

public class TextFileFilter implements FileFilter {

   @Override
   public boolean accept(File file) {
      return file.getName().toLowerCase().endsWith(".txt");
   }
}