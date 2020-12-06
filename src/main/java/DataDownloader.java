import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class DataDownloader {
    private int _mBeginning[]=new int[1];
    private int _mIndex;
    private ArrayList<String> _mDependencies = new ArrayList<String>();

    private void _mDependenciesPrint(){
        int i;
        for (i=0;i<this._mDependencies.size();i++){
            System.out.println(this._mDependencies.get(i));
        }
        return;
    }

    private void _mTrackDependent(String _mContent){
        int i;
        for (i=0;i<this._mIndex;i++) {
            String buff="";
            int start=this._mBeginning[i];
            while (_mContent.charAt(start)!='"') {
                buff+=_mContent.charAt(start);
                start++;
            }
            _mDependencies.add(buff);
        }
    }

    private static int[] addPosition(int _inSize, int[] _inArr, int _inAdder){
        int i;
        int newArray[]=new int[_inSize+1];
        for (i=0;i<_inSize;i++){
            newArray[i]=_inArr[i];
        }
        newArray[_inSize]=_inAdder;
        return newArray;
    }

    private void _mHTTPTrack(String _inContent){
        int index=_inContent.indexOf("http://");
        this._mIndex=1;
        this._mBeginning[0]=index;
        while(index >= 0) {
            index = _inContent.indexOf("http://", index+1);
            if (index!=-1){
                this._mBeginning=this.addPosition(this._mIndex,this._mBeginning,index);
                this._mIndex++;
            }
        }
        return;
    }

    private void _mHTTPSTrack(String _inContent){
        int index=_inContent.indexOf("https://");
        this._mBeginning=this.addPosition(this._mIndex,this._mBeginning,index);
        this._mIndex++;
        while(index >= 0) {
            index = _inContent.indexOf("https://", index+1);
            if (index!=-1){
                this._mBeginning=this.addPosition(this._mIndex,this._mBeginning,index);
                this._mIndex++;
            }
        }
        return;
    }

    public void _mDownload(String urlString) throws IOException {
        URL url = new URL(urlString);
        String content="";
        int i;
        try(
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                BufferedWriter writer = new BufferedWriter(new FileWriter("page.txt"));
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                content+=line;
            }
            System.out.println("Page downloaded.");

            this._mHTTPTrack(content);
            this._mHTTPSTrack(content);
            this._mTrackDependent(content);
            this._mDependenciesPrint();

        }
    }
}
