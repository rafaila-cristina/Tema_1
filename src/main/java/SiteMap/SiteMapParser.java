package SiteMap;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.*;

public class SiteMapParser {
    public static List<String> GetAllUrls(String htmlString){
        List<String> result = new ArrayList<String>();

        Dictionary dictionary = new Hashtable();        //dictionary
        dictionary.put(1, "h");
        dictionary.put(2, "/");
        dictionary.put(3, "#");
        //dictionary.put(4, "https://");
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

                    String newUrl = htmlString.substring(j+6,j+6+k);
                    ok=0;

                    for (Enumeration counter = dictionary.elements(); counter.hasMoreElements();)
                        if(newUrl.equals(counter.nextElement())) {  //verify dictionary
                            ok=1;
                            break;
                        }

                    //TODO: verify list for duplicates when creating it
                    //TODO: remove "https://" from result
                    if (ok==0){     //if nothing found in dictionary then add url to result
                        if (result.size() > 100)     // Debugging pruposes
                            return result;


                        result.add(newUrl);
                    }
                }

            }
        }

        return result;
    }



}
