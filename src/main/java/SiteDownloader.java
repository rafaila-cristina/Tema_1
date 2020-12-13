import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;

public class SiteDownloader extends Thread {
    private ArrayList<String> _mDependent=new ArrayList<String>();
    private ArrayList<String> _mVisited=new ArrayList<String>();

    private void _mRemoveDuplicates() {
        int i;
        ArrayList<String> newList = new ArrayList<String>();
        for (i=0;i<this._mDependent.size();i++){
            if (!newList.contains(this._mDependent.get(i))){
                newList.add(this._mDependent.get(i));
            }
        }
        this._mDependent=new ArrayList<String>();
        for (i=0;i<newList.size();i++){
            this._mDependent.add(newList.get(i));
        }
        return;
    }

    private void _mAddDependencyList(ArrayList<String> _inList){
        int i;
        for (i=0;i<_inList.size();i++){
            this._mDependent.add(_inList.get(i));
        }
        return;
    }

    private void _mAddVisited(String _inString){
        this._mVisited.add(_inString);
        return;
    }

    public void _mFullStateDownload(String _inPage, int _inThreads, int _inLimit) throws IOException {
        DataDownloader initialScripter=new DataDownloader();
        initialScripter._mDownload(_inPage);
        this._mAddDependencyList(initialScripter._mGetDeppendencies());
        this._mAddVisited(_inPage);
        initialScripter=null;
        System.out.println(this._mDependent.size());

        int i,j;
        for (i=0;i<this._mDependent.size();i+=_inThreads){
            if (i>_inLimit){
                break;
            }
            for (j=0;j<_inThreads;j++) {
                if (!this._mVisited.contains(this._mDependent.get(i+j))) {
                    ThreadDownloader _innerThread = new ThreadDownloader();
                    _innerThread._mRun(this._mDependent.get(i));

                    this._mAddDependencyList(_innerThread._mGetDependent());
                    this._mRemoveDuplicates();
                    this._mAddVisited(this._mDependent.get(i));
                    System.out.println(this._mDependent.size());
                    _innerThread = null;
                }
            }
        }
        return;
    }
}