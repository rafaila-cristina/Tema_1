

import java.net.*;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;

public class Robots {
    final private String _nameBot = "Bot Bob";
    private URL _robotsUrl;
    private Vector<String> _vDisallow=new Vector<String>();
    private int _delay=-1;

    void createRobotsUrl(URL _url) throws MalformedURLException {
        this._robotsUrl = new URL(_url.getProtocol(), _url.getHost(), _url.getPort(), _url.getPath() + "/robots.txt");
    }

    void readRobotsTxt() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(this._robotsUrl.openStream()));
        String line;

        while ((line = in.readLine()) != null)
            if (line.equals("User-agent: *"))
                break;

        while ((line = in.readLine()) != null) {
            if (line.contains("Disallow:")) {
                StringTokenizer defaultTokenizer = new StringTokenizer(line);
                defaultTokenizer.nextToken();
                this._vDisallow.add(defaultTokenizer.nextToken());
            } else if (line.contains("Crawl-delay:")) {
                StringTokenizer defaultTokenizer = new StringTokenizer(line);
                defaultTokenizer.nextToken();
                this._delay = Integer.parseInt(defaultTokenizer.nextToken());
            }
        }
        if (_vDisallow.isEmpty())
            _vDisallow.set(0, "0");
    }



    Vector<String> getString() { return this._vDisallow; }



}
