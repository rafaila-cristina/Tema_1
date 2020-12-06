import java.io.IOException;

public class main {
    public static void main(String args[]) throws IOException {

        DataDownloader tester=new DataDownloader();
        tester._mDownload("http://www.google.com");
    }
}