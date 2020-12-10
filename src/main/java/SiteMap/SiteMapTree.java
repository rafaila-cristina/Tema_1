package SiteMap;

import java.util.List;
import java.util.StringTokenizer;

public class SiteMapTree {
    private MapTreeNode root;

    public SiteMapTree(String rootUrl) {
        this.root = new MapTreeNode(rootUrl);
    }
            /*
    public List<String> getAllUrls(){
    }       */

    // lib/exe/opensearch.php
    public void addNode(String childUrl){
        root.addNode(childUrl);
    }
}
