import SiteMap.SiteMapGenerator;

import java.io.IOException;
import java.util.List;

public class main {
    public static void main(String args[]) throws IOException {
        SiteMapGenerator siteMapGenerator = new SiteMapGenerator();
        List<String> siteUrls = siteMapGenerator.GenerateSiteMap( "https://wiki.mta.ro/");



    //    DataDownloader tester=new DataDownloader();
    //    tester._mDownload("http://www.google.com");
    //    FilterData filtrare=new FilterData("D:\\anul4\\ingprog\\tema git\\Tema-laborator");
    //    filtrare.FilterByExtension(".java");

    }
}