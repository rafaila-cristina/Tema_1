import SiteMap.SiteMapGenerator;
import SiteMap.SiteMapTree;

import java.io.IOException;
import java.util.logging.Filter;

public class main {
    public static void main(String args[]) throws IOException {
/*
        DataDownloader tester=new DataDownloader();
        tester._mDownload("http://www.google.com");
        FilterData filtrate=new FilterData("D:\\anul4",1);
        filtrate.FilterByExtension(".txt");     */

        SiteMapGenerator siteMapGenerator  = new SiteMapGenerator("https://wiki.mta.ro/");
        SiteMapTree siteMap;
        siteMap = siteMapGenerator.GenerateSiteMap();

    }
}