import java.io.File;
import java.io.FileFilter;
import java.io.File;
import java.io.IOException;

public class FilterData {
    private String _mPath;

    public FilterData(String _mPath) {
        this._mPath = _mPath;
    }

    private void FilterByExtensionRecursive(File dir, String _Extension) {
        try {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    // System.out.println("directory:" + file.getCanonicalPath());
                    FilterByExtensionRecursive(file, _Extension);
                } else {
                    if (file.getName().endsWith(_Extension)) {
                        System.out.println("File with extension :" + _Extension + ":" + file.getCanonicalPath());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void FilterByDimensionsRecursive(File dir,long _Limit, String _LimitType) {
        try {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    //System.out.println("directory:" + file.getCanonicalPath());
                    FilterByDimensionsRecursive(file,_Limit,_LimitType);
                } else {
                    long bytes = file.length();
                    long kilobytes = (bytes / 1024);
                    long megabytes = (kilobytes / 1024);
                    long gigabytes = (megabytes / 1024);
                    switch (_LimitType) {
                        case "b":
                            if (bytes <= _Limit)
                                System.out.println("File with dimension <=" + bytes + " bytes:" + file.getCanonicalPath());
                            break;

                        case "kb":
                            if (kilobytes <= _Limit)
                                System.out.println("File with dimension <=" + kilobytes + " kilobytes:" + file.getCanonicalPath());
                            break;
                        case "mb":
                            if (megabytes <= _Limit)
                                System.out.println("File with dimension <=" + megabytes + " megabytes:" + file.getCanonicalPath());
                            break;
                        case "gb":
                            if (gigabytes <= _Limit)
                                System.out.println("File with dimension <=" + gigabytes + " gigabytes:" + file.getCanonicalPath());
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void FilterByDimension(long _Limit, String _LimitType) {
        File dir = new File(_mPath);
        try {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    //System.out.println("directory:" + file.getCanonicalPath());
                    FilterByDimensionsRecursive(file,_Limit, _LimitType);
                } else {
                    long bytes = file.length();
                    long kilobytes = (bytes / 1024);
                    long megabytes = (kilobytes / 1024);
                    long gigabytes = (megabytes / 1024);
                    switch (_LimitType) {
                        case "b":
                            if (bytes <= _Limit)
                                System.out.println("File with dimension <=" + bytes + " bytes:" + file.getCanonicalPath());
                            break;

                        case "kb":
                            if (kilobytes <= _Limit)
                                System.out.println("File with dimension <=" + kilobytes + " kilobytes:" + file.getCanonicalPath());
                            break;
                        case "mb":
                            if (megabytes <= _Limit)
                                System.out.println("File with dimension <=" + megabytes + " megabytes:" + file.getCanonicalPath());
                            break;
                        case "gb":
                            if (gigabytes <= _Limit)
                                System.out.println("File with dimension <=" + gigabytes + " gigabytes:" + file.getCanonicalPath());
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int FilterByExtension(String _Extension) {
        File dir = new File(_mPath);
        try {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    //System.out.println("directory:" + file.getCanonicalPath());
                    FilterByExtensionRecursive(file, _Extension);
                } else {
                    if (file.getName().endsWith(_Extension)) {
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
