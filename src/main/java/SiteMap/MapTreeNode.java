package SiteMap;

import javax.naming.BinaryRefAddr;
import java.util.ArrayList;
import java.util.List;

public class MapTreeNode {
    public String url;
    public List<MapTreeNode> childNodes;

    public MapTreeNode(String url) {
        this.url = url;
        childNodes = new ArrayList<MapTreeNode>();
    }

    // lib/exe/opensearch.php
    public void addNode(String childUrl){
        int endIndex = childUrl.indexOf('/');
        if (endIndex < 1 && childUrl.length() < 2)
            return;

        String firstPart = childUrl;
        if (endIndex > 0)
            firstPart = childUrl.substring(0, endIndex);

        int childIndex = -1;

        for (int i=0; i<childNodes.size(); i++){
            if (childNodes.get(i).url.equals(firstPart)){
                childIndex = i;
                break;
            }
        }

        if (childIndex != -1){
            String newChildString = childUrl.substring(endIndex + 1, childUrl.length());
            if(!childNodes.get(childIndex).url.equals(newChildString))
                childNodes.get(childIndex).addNode(newChildString);
        } else{
            childNodes.add(new MapTreeNode(firstPart));
            if (endIndex > 0)
                childNodes.get(childNodes.size() - 1).addNode(childUrl.substring(endIndex + 1, childUrl.length()));
        }
    }

    public List<MapTreeNode> GetChildren(){
        return childNodes;
    }
}
