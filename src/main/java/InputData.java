import SiteMap.SiteMapGenerator;
import SiteMap.SiteMapTree;
import java.io.IOException;
import java.util.List;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;

public class InputData {
    String[] _mArgs;
    String _mURL;
    int _mNrThreads;
    int _mLimit;

    public InputData(String[] var1) {
        this._mArgs = var1;
    }

    public void Print() {
        String[] var1 = this._mArgs;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String var4 = var1[var3];
            System.out.println(var4);
        }

    }

    private void PrintMessage(String var1) {
        System.out.println("Wrong format");
        System.out.println(var1);
        System.out.println("");
        System.out.println("Where -t is optional for multithreading");
    }

    private void ReadFromFile(String _filepath)
    {
        try {
            File myObj = new File(_filepath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] words=data.split("=");
                if(words[0].equals("n_threads"))
                    this._mNrThreads=Integer.parseInt(words[1]);
                else if(words[0].equals("URL"))
                    this._mURL=words[1];
                else if(words[0].equals("limit"))
                    this._mLimit=Integer.parseInt(words[1]);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void Filter(String var1) {
        if (this._mArgs.length > 2) {
            int var2;
            FilterData var9;
            if (var1.equals("limit")) {
                int var3;
                if (this._mArgs.length == 6) {
                    if (this._mArgs[1].equals("-t")) {
                        try {
                            var2 = Integer.parseInt(this._mArgs[2]);
                            var3 = Integer.parseInt(this._mArgs[4]);
                            FilterData var4 = new FilterData(this._mArgs[3], var2);
                            var4.FilterByDimension((long)var3, this._mArgs[5]);
                        } catch (Exception var8) {
                            this.PrintMessage("Form: limit [-t number_of_threads] \"path\" integer type(b/kb/MB/gb)");
                        }
                    } else {
                        this.PrintMessage("Form: limit [-t number_of_threads] \"path\" integer type(b/kb/MB/gb)");
                    }
                } else if (this._mArgs.length == 4) {
                    if (this._mArgs[1] != null && !this._mArgs[1].isEmpty() && this._mArgs[2] != null && !this._mArgs[2].isEmpty()) {
                        try {
                            var9 = new FilterData(this._mArgs[1], 1);
                            var3 = Integer.parseInt(this._mArgs[2]);
                            var9.FilterByDimension((long)var3, this._mArgs[3]);
                        } catch (Exception var7) {
                            this.PrintMessage("Form: limit [-t number_of_threads] \"path\" integer type(b/kb/MB/gb)");
                        }
                    }
                } else {
                    this.PrintMessage("Form: limit [-t number_of_threads] \"path\" integer type(b/kb/MB/gb)");
                }
            } else if (this._mArgs.length == 5) {
                if (this._mArgs[1].equals("-t")) {
                    try {
                        var2 = Integer.parseInt(this._mArgs[2]);
                        FilterData var10 = new FilterData(this._mArgs[3], var2);
                        if (var1.equals("extension")) {
                            var10.FilterByExtension(this._mArgs[4]);
                        } else if (var1.equals("search")) {
                            var10.FilterByWords(this._mArgs[4]);
                        }
                    } catch (Exception var6) {
                        this.PrintMessage("Form: " + var1 + " [-t number_of_threads] \"path\" keyword");
                    }
                } else {
                    this.PrintMessage("Form: " + var1 + " [-t number_of_threads] \"path\" keyword");
                }
            } else if (this._mArgs.length == 3) {
                if (this._mArgs[1] != null && !this._mArgs[1].isEmpty() && this._mArgs[2] != null && !this._mArgs[2].isEmpty()) {
                    try {
                        var9 = new FilterData(this._mArgs[1], 1);
                        if (var1.equals("extension")) {
                            var9.FilterByExtension(this._mArgs[2]);
                        } else if (var1.equals("search")) {
                            var9.FilterByWords(this._mArgs[2]);
                        }
                    } catch (Exception var5) {
                        this.PrintMessage("Form: " + var1 + " [-t number_of_threads] \"path\" keyword");
                    }
                }
            } else {
                this.PrintMessage("Form: " + var1 + " [-t number_of_threads] \"path\" keyword");
            }
        } else if (var1.equals("limit")) {
            this.PrintMessage("Form: limit [-t number_of_threads] \"path\" integer type(b/kb/MB/gb)");
        } else {
            this.PrintMessage("Form: " + var1 + " [-t number_of_threads] \"path\" keyword");
        }

    }

    public void Process() throws IOException {
        if (this._mArgs[0].equals("crowler")) {
            if (this._mArgs.length == 2) {
                try {
                    ReadFromFile(_mArgs[1]);
                    if (_mURL!=null && _mLimit!=0 && _mNrThreads!=0)
                    {
                        SiteDownloader tester=new SiteDownloader();
                        tester._mFullStateDownload(_mURL,_mNrThreads,_mLimit);
                    }
                    else
                    {
                        System.out.println("Fisier de configurare gresit sau inexistent");
                    }

                } catch (Exception var4) {
                    System.out.println("Something went wrong");
                    System.out.println("Add configure file");
                    System.out.println("Exemple: crowler file.conf");
                }
            } else {
                System.out.println("Add configure file");
                System.out.println("Exemple: crowler file.conf");
            }
        } else if (this._mArgs[0].equals("extension")) {
            this.Filter(this._mArgs[0]);
        } else if (this._mArgs[0].equals("search")) {
            this.Filter(this._mArgs[0]);
        } else if (this._mArgs[0].equals("limit")) {
            this.Filter(this._mArgs[0]);
        } else if (this._mArgs[0].equals("sitemap")) {
            if (this._mArgs.length >= 2) {
                try {
                    SiteMapGenerator siteMapGenerator = new SiteMapGenerator();
                    List<String> siteUrls = siteMapGenerator.GenerateSiteMap( _mArgs[1]);
                } catch (Exception var3) {
                    System.out.println("Something went wrong");
                    System.out.println("Wrong format");
                    System.out.println("Form: sitemap \"URL\"");
                }
            }
            else
            {
                System.out.println("Wrong format");
                System.out.println("Form: sitemap \"URL\"");
            }
        } else {
            System.out.println("Bad command");
        }

    }
}
