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

        if(args.length>0)
        {
            InputData input=new InputData(args);
            input.Process();
        }
        else
        {
            System.out.println("Choose one option:");
            System.out.println("  crowler");
            System.out.println("  extension");
            System.out.println("  search");
            System.out.println("  limit");
            System.out.println("  sitemap");
        }
    }
}