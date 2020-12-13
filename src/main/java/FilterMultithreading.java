import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
/**
 * Clasa ce implementeaza filtrarea datelor pe threaduri.
 * Este folosita de clasa {@link FilterData}
 *
 *
 * @author Bogdan Popescu
 * @see FilterData
 */
public class FilterMultithreading {
    Thread[] _mThread;
    Vector<File> _mFiles = new Vector<>();

    /**
     * Metoda ce adauga fisierele pentru a fi filtrate.
     * @param _file fisierul care va fi adaugat in vector.
     */
    public void addFile(File _file) {
        _mFiles.add(_file);
    }

    /**
     * Aceasta metoda este apelata din {@link FilterData#FilterByExtension(String)}
     * si face afisarea rezultatului filtrarii.
     * 
     * @param _Extension Extensia dupa care sa se faca filtrarea.
     * @param _ThreadsNumber Numarul de threaduri care sa  faca filtrarea.
     */
    public void StartExtension(String _Extension, int _ThreadsNumber) {
        _mThread = new Thread[_ThreadsNumber];
        for (int i = 0; i < _ThreadsNumber; i++) {
            _mThread[i] = new Thread(new Runnable() {
                public void run() { // Anonymous method
                    int start = _mFiles.size() * Integer.parseInt(Thread.currentThread().getName()) / _ThreadsNumber;
                    int end = _mFiles.size() * (Integer.parseInt(Thread.currentThread().getName()) + 1) / _ThreadsNumber;
                    for (int j = start; j < end; j++) {
                        //FilterByExtensionRecursive(_mFiles.get(j),_Extension);
                        if (_mFiles.get(j).getName().toLowerCase().endsWith(_Extension.toLowerCase())) {
                            try {
                                System.out.println("File with extension : " + _Extension + ": " + _mFiles.get(j).getCanonicalPath());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            });
            _mThread[i].setName(String.valueOf(i));
            _mThread[i].start();
        }
        for (int i = 0; i < _ThreadsNumber; i++) {
            try {
                _mThread[i].join();
                _mThread[i] = null;
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Aceasta metoda este apelata din {@link FilterData#FilterByWords(String)}.
     * si face afisarea rezultatului filtrarii.
     * 
     * @param _Words Un cuvant sau mai multe cuvinte dupa care sa se faca filtrarea.
     * @param _ThreadsNumber Numarul de threaduri care sa  faca filtrarea.
     */
    public void StartWords(String _Words, int _ThreadsNumber) {
        _mThread = new Thread[_ThreadsNumber];
        for (int i = 0; i < _ThreadsNumber; i++) {
            _mThread[i] = new Thread(new Runnable() {
                public void run() { // Anonymous method
                    int start = _mFiles.size() * Integer.parseInt(Thread.currentThread().getName()) / _ThreadsNumber;
                    int end = _mFiles.size() * (Integer.parseInt(Thread.currentThread().getName()) + 1) / _ThreadsNumber;
                    for (int j = start; j < end; j++) {
                        //FilterByExtensionRecursive(_mFiles.get(j),_Extension);
                        try {
                            if (_mFiles.get(j).getName().toLowerCase().indexOf(_Words.toLowerCase()) != -1) {
                                System.out.println("Cuvinte gasite in titlul fisierului : " + _mFiles.get(j).getCanonicalPath());
                            }
                            Scanner sc1 = new Scanner(_mFiles.get(j));
                            long count = 0;
                            while (sc1.hasNextLine()) {
                                String line = sc1.nextLine();
                                if (line.toLowerCase().indexOf(_Words.toLowerCase()) != -1) {
                                    count = count + 1;
                                    System.out.println("Cuvinte gasite in fisierul : " + _mFiles.get(j).getCanonicalPath() + " La linia:" + count);
                                    System.out.println("Continut linie:" + line);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            _mThread[i].setName(String.valueOf(i));
            _mThread[i].start();
        }
        for (int i = 0; i < _ThreadsNumber; i++) {
            try {
                _mThread[i].join();
                _mThread[i] = null;
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Aceasta metoda este apelata din {@link FilterData#FilterByDimension(long, String)}.
     * si face afisarea rezultatului filtrarii.
     * 
     * @param _Limit limita maxima pentru filtrarea fisierelor.
     * @param _LimitType tipul de limita data. Poate fi b, kb, mb, gb.
     * @param _ThreadsNumber Numarul de threaduri care sa  faca filtrarea.
     */
    public void StartDimension(long _Limit, String _LimitType, int _ThreadsNumber) {
        _mThread = new Thread[_ThreadsNumber];
        for (int i = 0; i < _ThreadsNumber; i++) {
            _mThread[i] = new Thread(new Runnable() {
                public void run() { // Anonymous method
                    int start = _mFiles.size() * Integer.parseInt(Thread.currentThread().getName()) / _ThreadsNumber;
                    int end = _mFiles.size() * (Integer.parseInt(Thread.currentThread().getName()) + 1) / _ThreadsNumber;
                    for (int j = start; j < end; j++) {
                        long bytes = _mFiles.get(j).length();
                        long kilobytes = (bytes / 1024);
                        long megabytes = (kilobytes / 1024);
                        long gigabytes = (megabytes / 1024);
                        try {
                            switch (_LimitType.toLowerCase()) {
                                case "b":
                                    if (bytes <= _Limit)
                                        System.out.println("File with dimension <=" + _Limit + " bytes: " + _mFiles.get(j).getCanonicalPath());
                                    break;

                                case "kb":
                                    if (kilobytes <= _Limit)
                                        System.out.println("File with dimension <=" + _Limit + " kilobytes: " + _mFiles.get(j).getCanonicalPath());
                                    break;
                                case "mb":
                                    if (megabytes <= _Limit)
                                        System.out.println("File with dimension <=" + _Limit + " megabytes: " + _mFiles.get(j).getCanonicalPath());
                                    break;
                                case "gb":
                                    if (gigabytes <= _Limit)
                                        System.out.println("File with dimension <=" + _Limit + " gigabytes: " + _mFiles.get(j).getCanonicalPath());
                                    break;
                                default:
                                    break;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            _mThread[i].setName(String.valueOf(i));
            _mThread[i].start();
        }
        for (int i = 0; i < _ThreadsNumber; i++) {
            try {
                _mThread[i].join();
                _mThread[i] = null;
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
