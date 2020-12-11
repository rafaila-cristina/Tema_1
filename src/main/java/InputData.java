import SiteMap.SiteMapGenerator;
import SiteMap.SiteMapTree;
import java.io.IOException;

public class InputData {
    String[] _mArgs;

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
                    DataDownloader var1 = new DataDownloader();
                    var1._mDownload(this._mArgs[1]);
                } catch (Exception var4) {
                    System.out.println("Add one URL");
                    System.out.println("Exemple: crowler \"http://www.google.com\"");
                }
            } else {
                System.out.println("Add one URL");
                System.out.println("Exemple: crowler \"http://www.google.com\"");
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
                    SiteMapGenerator var5 = new SiteMapGenerator(this._mArgs[1]);
                    SiteMapTree var2 = var5.GenerateSiteMap();
                } catch (Exception var3) {
                    System.out.println("Wrong format");
                    System.out.println("Form: sitemap \"URL\"");
                }
            }
        } else {
            System.out.println("Bad command");
        }

    }
}
