package SiteMap;

public class SiteMapTree {
    private MapTreeNode root;

    public SiteMapTree(MapTreeNode root) {
        this.root = root;
    }

    public SiteMapTree(String rootUrl) {
        this.root = new MapTreeNode(rootUrl);
    }


}
