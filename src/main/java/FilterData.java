import java.io.File;
import java.io.FileFilter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
/**
 * Clasa ce implementeaza filtrarea datelor.
 * Se foloseste de clasa {@link FilterMultithreading}.
 *<p>Exemplu de utilizare pentru a afisa toate fisierele cu extensia ".txt" din folderul D:\\anul4 cu 4 threaduri:</p>
 * <p>FilterData filtrate=new FilterData("D:\\anul4",4);</p>
 * <p>filtrate.FilterByExtension(".txt");</p>
 *
 * @author Bogdan Popescu
 * @see FilterMultithreading
 */
public class FilterData {
    private String _mPath;
    private FilterMultithreading _mMulti;
    private int _mThreadsNumber;

    /**
     * @param _mPath Calea catre fisierul unde a fost descarcat un site.
     * @param _NumberOfThreads Numarul de threaduri cu care sa se faca filtrarea.
     */
    public FilterData(String _mPath,int _NumberOfThreads) {
        this._mPath = _mPath;
        _mMulti=new FilterMultithreading();
        this._mThreadsNumber=_NumberOfThreads;
    }

    private void FilterByExtensionRecursive(File dir, String _Extension) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                FilterByExtensionRecursive(file, _Extension);
            } else {
                //if (file.getName().endsWith(_Extension)) {
                //  System.out.println("File with extension :" + _Extension + ":" + file.getCanonicalPath());
                //}
                _mMulti.addFile(file);
            }
        }
    }

    private void FilterByDimensionsRecursive(File dir, long _Limit, String _LimitType) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                FilterByDimensionsRecursive(file, _Limit, _LimitType);
            } else {
                _mMulti.addFile(file);
            }
        }
    }

    /**
     * Metoda ce face filtrarea dupa dimensiune, adaugand initial toate fisierele in clasa {@link FilterMultithreading} cu metoda {@link FilterMultithreading#addFile(File)}
     * apoi apeland metoda {@link FilterMultithreading#StartDimension(long, String, int)}
     * @param _Limit limita maxima pentru filtrarea fisierelor.
     * @param _LimitType tipul de limita data. Poate fi b, kb, mb, gb.
     */
    public void FilterByDimension(long _Limit, String _LimitType) {
        File dir = new File(_mPath);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                FilterByDimensionsRecursive(file, _Limit, _LimitType);
            } else {
                _mMulti.addFile(file);
            }
        }
        _mMulti.StartDimension(_Limit,_LimitType,_mThreadsNumber);
    }

    private void FilterByWordsRecursive(String _Words, File dir) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                FilterByWordsRecursive(_Words, file);
            } else {
                _mMulti.addFile(file);
            }
        }
    }

    /**
     * Metoda ce face filtrarea dupa cuvinte, adaugand initial toate fisierele in clasa {@link FilterMultithreading} cu metoda {@link FilterMultithreading#addFile(File)}
     * apoi apeland metoda {@link FilterMultithreading#StartWords(String, int)}
     * @param _Words Un cuvant sau mai multe cuvinte dupa care sa se faca filtrarea.
     */
    public void FilterByWords(String _Words) {
        File dir = new File(_mPath);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                FilterByWordsRecursive(_Words, file);
            } else {
                _mMulti.addFile(file);
            }
        }
        _mMulti.StartWords(_Words,_mThreadsNumber);
    }

    /**
     * Metoda ce face filtrarea dupa extensie, adaugand initial toate fisierele in clasa {@link FilterMultithreading} cu metoda {@link FilterMultithreading#addFile(File)}
     * apoi apeland metoda {@link FilterMultithreading#StartExtension(String, int)}
     * @param _Extension Extensia dupa care sa se faca filtrarea.
     *
     */
    public void FilterByExtension(String _Extension) {
        File dir = new File(_mPath);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                FilterByExtensionRecursive(file, _Extension);
            } else {
                _mMulti.addFile(file);
            }
        }
        _mMulti.StartExtension(_Extension,_mThreadsNumber);
    }
}
