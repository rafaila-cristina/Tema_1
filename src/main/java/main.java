import java.io.IOException;
import java.util.logging.Filter;

public class main {
    public static void main(String args[]) throws IOException {

        DataDownloader tester=new DataDownloader();
        tester._mDownload("http://www.google.com");
        FilterData filtrare=new FilterData("D:\\anul4\\ingprog\\tema git\\Tema-laborator");
        filtrare.FilterByExtension(".java");
    }
}