package SiteMap;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.*;

public class SiteMapParser {
    public static void GetAllUrls(Map<String, String> urls, String htmlString){

        Dictionary dictionary = new Hashtable();        //dictionary
        dictionary.put(1, "h");
        dictionary.put(2, "/");
        dictionary.put(3, "#");
        dictionary.put(4, "");
        int ok;

        for (int i=0; i<htmlString.length(); i++){
            for (int j=i; j<htmlString.length()-6; j++){
                String subString = htmlString.substring(j, j + 6);
                if(subString.equals("href=\"")){
                    int k=1;
                    while (j + k < htmlString.length() - j - k - 6){
                        char ch = htmlString.charAt(j+k + 6);
                        if (ch == '\"') {
                            break;
                        }
                        k++;
                    }

                    String newUrl = htmlString.substring(j+7,j+6+k);
                    i = j + 6 + k;
                    int endIndex = newUrl.indexOf('?');
                    if(endIndex > 0)
                        newUrl = newUrl.substring(0, endIndex);
                    ok=0;

                    for (Enumeration counter = dictionary.elements(); counter.hasMoreElements();)
                        if(newUrl.equals(counter.nextElement())) {       //verify dictionary
                            ok=1;
                            break;
                        }

                    if (ok==0) {     //if nothing found in dictionary then add url to result


                        if (urls.size() > 400)     // Debugging pruposes
                            return;

                        urls.put(newUrl, newUrl);
                    }
                }
            }
        }
    }



}
