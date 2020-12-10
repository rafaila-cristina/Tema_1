package SiteMap;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SiteMapGenerator {
    private String baseUrl;
    private SiteMapTree siteMapTree;

    public SiteMapGenerator(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public SiteMapTree GenerateSiteMap() throws IOException {
        siteMapTree = new SiteMapTree(baseUrl);

        //send request to baseUrl
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

        List<String> urls;
        urls = SiteMapParser.GetAllUrls(bodyString);

        // for in urls
        // create a new TreeNode with every url
        // add every new TreeNode to siteMapTree
        // /c/1/abcd
        // /c/1/aef
        // /c/1/tyu
        // ==> (c) -> (1) -> (abcd)
        //                -> (aef)
        //                -> (tyu)

        return siteMapTree;
    }

}
