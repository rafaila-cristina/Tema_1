import java.io.File;
import java.io.FileFilter;
import java.io.File;
import java.io.IOException;
public class FilterData {
    private String _mPath;

    public FilterData(String _mPath) {
        this._mPath = _mPath;
    }
    private void FilterByExtensionRecursive(File dir,String _Extension)
    {
        try {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                   // System.out.println("directory:" + file.getCanonicalPath());
                    FilterByExtensionRecursive(file,_Extension);
                } else {
                    if(file.getName().endsWith(_Extension)) {
                        System.out.println("     file:" + file.getCanonicalPath());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int FilterByExtension(String _Extension)
    {
        File dir = new File(_mPath);
        try {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    //System.out.println("directory:" + file.getCanonicalPath());
                    FilterByExtensionRecursive(file,_Extension);
                } else {
                    if(file.getName().endsWith(_Extension)) {
                        System.out.println("File with extension :" + _Extension + ":" + file.getCanonicalPath());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
