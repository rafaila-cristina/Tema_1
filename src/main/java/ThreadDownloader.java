import java.io.IOException;
import java.util.ArrayList;

public class ThreadDownloader extends Thread {
    private ArrayList<String> _mInnerDependency=new ArrayList<String>();
    private void _mAddDependency(ArrayList<String> _inDependent){
        for (int i=0;i<_inDependent.size();i++){
            this._mInnerDependency.add(_inDependent.get(i));
        }
        return;
    }
    public void _mRun(String _inRunningDownload) throws IOException {
        DataDownloader tempObj = new DataDownloader();
        tempObj._mDownload(_inRunningDownload);
        this._mAddDependency(tempObj._mGetDeppendencies());
        return;
    }

    public ArrayList<String> _mGetDependent(){return this._mInnerDependency;}
}
