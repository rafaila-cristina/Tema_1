package SiteMap;

import javax.print.DocFlavor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.*;

public class SiteMapGenerator {
    private SiteMapTree siteMapTree;

    public String DownloadPage(String baseUrl) throws IOException{        //send request to baseUrl
        String bodyString = "";
        URL url = new URL(baseUrl);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);

        BufferedReader bin = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer stringBuffer = new StringBuffer();
        String inputLine;

        while ((inputLine = bin.readLine())!= null){
            stringBuffer.append(inputLine);
        }

        bodyString = stringBuffer.toString();
        return bodyString;
    }

    public void GenerateAllUrls(Map<String, String> urls, String currentUrl, String baseUrl, int startIndex) throws IOException {
        if(startIndex < urls.size())
            return;

        SiteMapParser.GetAllUrls(urls, DownloadPage(baseUrl + currentUrl));

        System.out.println("Urls list size = " + urls.size());
        for (int i=startIndex; i < urls.size(); i++)
            GenerateAllUrls(urls, urls.get(i), baseUrl, i + 1);
    }

    /**
     *
     * @param baseUrl
     * @return O lista cu toate url-urile
     * @throws IOException
     */

    public List<String> GenerateSiteMap(String baseUrl) throws IOException {
        siteMapTree = new SiteMapTree(baseUrl);

        Map<String, String> mapUrls = new HashMap<String, String>();
        GenerateAllUrls(mapUrls, "", baseUrl, 0);

        List<String> urls = new ArrayList<String>();

        Iterator it = mapUrls.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            urls.add(baseUrl + pair.getValue());
        }


        return new ArrayList(urls);
    }

}
