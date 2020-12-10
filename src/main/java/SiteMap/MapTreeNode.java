package SiteMap;

import javax.naming.BinaryRefAddr;
import java.util.ArrayList;
import java.util.List;

public class MapTreeNode {
    public String url;
    private List<MapTreeNode> childNodes;

    public MapTreeNode(String url) {
        this.url = url;
        childNodes = new ArrayList<MapTreeNode>();
    }

    public void addNode(String childUrl){
        childNodes.add(new MapTreeNode(childUrl));
    }

    public void removeNode(String childUrl){
        for (int i=0; i<childNodes.size(); i++){
            if(childNodes.get(i).url.equals(childUrl)) {
                childNodes.remove(i);
                break;
            }
        }
    }

    public List<MapTreeNode> GetChildren(){
        return childNodes;
    }


}
