import java.io.*;
import java.net.URL;

import java.util.ArrayList;
import java.util.StringTokenizer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataDownloader {
    private int _mBeginning[]=new int[1];
    private ArrayList<String> _mDependencies = new ArrayList<String>();
    private ArrayList<String> _mFiles = new ArrayList<String>();
    private ArrayList<String> _mImages=new ArrayList<String>();
    private ArrayList<String> _mXML=new ArrayList<String>();
    private ArrayList<String> _mPDF=new ArrayList<String>();
    private ArrayList<String> _mAudio=new ArrayList<String>();

    private static int stringCompare(String str1, String str2) {
        int l1 = str1.length();
        int l2 = str2.length();
        int lmin = Math.min(l1, l2);
        for (int i = 0; i < lmin; i++) {
            int str1_ch = (int)str1.charAt(i);
            int str2_ch = (int)str2.charAt(i);
            if (str1_ch != str2_ch) {
                return str1_ch - str2_ch;
            }
        }
        if (l1 != l2) {
            return l1 - l2;
        } else {
            return 0;
        }
    }

    private int _mORGChecker(String _inString){
        int counter=0;
        StringTokenizer defaultTokenizer = new StringTokenizer(_inString,".");
        while (defaultTokenizer.hasMoreTokens()) {
            if(this.stringCompare(defaultTokenizer.nextToken(),"org")==0){
                counter=1;
            }
        }
        return counter;
    }

    private void _mORGRemover(){
        int i;
        for (i=0;i<this._mDependencies.size();i++){
            if (this._mORGChecker(this._mDependencies.get(i))==1){
                this._mDependencies.remove(i);
            }
        }
        return;
    }

    private int _mImpurityChecker(){
        int i;
        int flag=0;
        for (i=0;i<this._mBeginning.length;i++){
            if (this._mBeginning[i]==-1){
                flag=1;
            }
        }
        return flag;
    }

    private void _mRemoveDuplicates() {
        int i;
        ArrayList<String> newList = new ArrayList<String>();
        for (i=0;i<this._mDependencies.size();i++){
            if (!newList.contains(this._mDependencies.get(i))){
                newList.add(this._mDependencies.get(i));
            }
        }
        this._mDependencies=new ArrayList<String>();
        for (i=0;i<newList.size();i++){
            this._mDependencies.add(newList.get(i));
        }
        return;
    }

    private String titleCreator(String _inRawTitle){
        int i,counter=0;
        for (i=0;i<_inRawTitle.length();i++){
            if(_inRawTitle.charAt(i)=='/'){
                counter=i;
            }
        }
        counter++;
        String title="";
        for (i=counter;i<_inRawTitle.length();i++){
            title+=_inRawTitle.charAt(i);
        }
        title+=".txt";
        return title;
    }

    private void _mDependenciesPrint(){
        int i;
        for (i=0;i<this._mDependencies.size();i++){
            System.out.println(this._mDependencies.get(i));
        }
        return;
    }

    private void _mTrackDependent(String _mContent){
        int i;
        for (i=0;i<this._mBeginning.length;i++) {
            String buff="";
            int start=this._mBeginning[i];
            int counter=0;
            while (counter<=2) {
                if(_mContent.charAt(start)=='/' || _mContent.charAt(start)=='"' || _mContent.charAt(start)==' ' || _mContent.charAt(start)=='\''){
                    counter++;
                }
                buff+=_mContent.charAt(start);
                start++;
            }
            String result=buff.substring(0,buff.length()-1);
            this._mDependencies.add(result);
        }
    }

    private void _mTrackFiles(String _mContent){
        int i;
        for (i=0;i<this._mBeginning.length;i++) {
            String buff="";
            int start=this._mBeginning[i];
            while (_mContent.charAt(start)!='\'' && _mContent.charAt(start)!='"' && _mContent.charAt(start)!=' '){
                buff+=_mContent.charAt(start);
                start++;
            }
            buff+=_mContent.charAt(start+1);
            String result=buff.substring(0,buff.length()-1);
            this._mFiles.add(result);
        }
    }

    private void _mFilesPrint(){
        int i;
        for (i=0;i<this._mFiles.size();i++){
            System.out.println(this._mFiles.get(i));
        }
        return;
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
        this._mBeginning[0]=index;
        while(index >= 0) {
            index = _inContent.indexOf("http://", index+1);
            if (index!=-1){
                this._mBeginning=this.addPosition(this._mBeginning.length,this._mBeginning,index);
            }
        }
        return;
    }

    private void _mHTTPSTrack(String _inContent){
        int index=_inContent.indexOf("https://");
        this._mBeginning=this.addPosition(this._mBeginning.length,this._mBeginning,index);
        while(index >= 0) {
            index = _inContent.indexOf("https://", index+1);
            if (index!=-1){
                this._mBeginning=this.addPosition(this._mBeginning.length,this._mBeginning,index);
            }
        }
        return;
    }

    private void _mImpurityRemove(){
        int[] copy = new int[this._mBeginning.length - 1];
        int counter=0;
        for (int i = 0; i < this._mBeginning.length; i++) {
            if (this._mBeginning[i] != -1) {
                copy[counter] = this._mBeginning[i];
                counter++;
            }
        }
        this._mBeginning=null;
        this._mBeginning=new int[0];
        for (int i=0;i<copy.length;i++){
            this._mBeginning=this.addPosition(this._mBeginning.length,this._mBeginning,copy[i]);
        }
        return;
    }

    private void _mAddImage(){
        int i;
        Extensions extent=new Extensions();
        for (i=0;i<this._mFiles.size();i++){
            String container=this._mFiles.get(i);
            StringTokenizer defaultTokenizer = new StringTokenizer(container,".");
            while (defaultTokenizer.hasMoreTokens()) {
                if(extent._mImageCheck(defaultTokenizer.nextToken())==1){
                    this._mImages.add(container);
                }
            }
        }
        extent=null;
        return;
    }

    private void _mAddXML(){
        int i;
        Extensions extent=new Extensions();
        for (i=0;i<this._mFiles.size();i++){
            String container=this._mFiles.get(i);
            StringTokenizer defaultTokenizer = new StringTokenizer(container,".");
            while (defaultTokenizer.hasMoreTokens()) {
                if(extent._mXMLCheck(defaultTokenizer.nextToken())==1){
                    this._mXML.add(container);
                }
            }
        }
        extent=null;
        return;
    }

    private void _mAddPDF(){
        int i;
        Extensions extent=new Extensions();
        for (i=0;i<this._mFiles.size();i++){
            String container=this._mFiles.get(i);
            StringTokenizer defaultTokenizer = new StringTokenizer(container,".");
            while (defaultTokenizer.hasMoreTokens()) {
                if(extent._mPDFCheck(defaultTokenizer.nextToken())==1){
                    this._mPDF.add(container);
                }
            }
        }
        extent=null;
        return;
    }

    private void _mAddAudio(){
        int i;
        Extensions extent=new Extensions();
        for (i=0;i<this._mFiles.size();i++){
            String container=this._mFiles.get(i);
            StringTokenizer defaultTokenizer = new StringTokenizer(container,".");
            while (defaultTokenizer.hasMoreTokens()) {
                if(extent._mPDFCheck(defaultTokenizer.nextToken())==1){
                    this._mAudio.add(container);
                }
            }
        }
        extent=null;
        return;
    }

    private void _mImagesPrint(){
        if (this._mImages.size()==0){
            System.out.println("There was no image found");
            System.out.println("------------------------------------------");
            return;
        }
        System.out.println("Images:");
        for (int i=0;i<this._mImages.size();i++){
            System.out.println(this._mImages.get(i));
        }
        System.out.println("------------------------------------------");
        return;
    }

    private void _mXMLPrint(){
        if (this._mXML.size()==0){
            System.out.println("There was no XML found");
            System.out.println("------------------------------------------");
            return;
        }
        System.out.println("XML:");
        for (int i=0;i<this._mXML.size();i++){
            System.out.println(this._mXML.get(i));
        }
        System.out.println("------------------------------------------");
        return;
    }

    private void _mPDFPrint(){
        if (this._mPDF.size()==0){
            System.out.println("There was no PDF found");
            System.out.println("------------------------------------------");
            return;
        }
        System.out.println("PDF:");
        for (int i=0;i<this._mPDF.size();i++){
            System.out.println(this._mPDF.get(i));
        }
        System.out.println("------------------------------------------");
        return;
    }

    private void _mAudioPrint(){
        if (this._mAudio.size()==0){
            System.out.println("There was no PDF found");
            System.out.println("------------------------------------------");
            return;
        }
        System.out.println("PDF:");
        for (int i=0;i<this._mAudio.size();i++){
            System.out.println(this._mAudio.get(i));
        }
        System.out.println("------------------------------------------");
        return;
    }

    private String _mFileNameReader(String _inPath){
        String fileName = _inPath.substring(_inPath.lastIndexOf("/") + 1);
        return fileName;
    }

    private void _mImagesDownload(){
        int i;
        for (i=0;i<this._mImages.size();i++){
            try {
                URL url = new URL(this._mImages.get(i));
                InputStream in= url.openStream();
                String title=this._mFileNameReader(this._mImages.get(i));
                FileOutputStream fos=new FileOutputStream(new File(title));

                int length = -1;
                byte[] buffer = new byte[1024];// buffer for portion of data from connection
                while ((length = in.read(buffer)) > -1) {
                    fos.write(buffer, 0, length);
                }
                fos.close();
                in.close();
                System.out.println(url+" downloaded...");
            } catch (Exception e)
            {
                System.out.println("Could not download "+this._mImages.get(i));
            }
        }
        return;
    }

    private void _mPDFDownload(){
        int i;
        for (i=0;i<this._mPDF.size();i++){
            try {
                URL url = new URL(this._mPDF.get(i));
                InputStream in= url.openStream();
                String title=this._mFileNameReader(this._mPDF.get(i));
                FileOutputStream fos=new FileOutputStream(new File(title));
                int length = -1;
                byte[] buffer = new byte[1024];// buffer for portion of data from connection
                while ((length = in.read(buffer)) > -1) {
                    fos.write(buffer, 0, length);
                }
                fos.close();
                in.close();
                System.out.println(url+" downloaded...");
            } catch (Exception e)
            {
                System.out.println("Could not download "+this._mPDF.get(i));
            }
        }
        return;
    }

    private void _mXMLDownload(){
        int i;
        for (i=0;i<this._mXML.size();i++){
            try {
                URL url = new URL(this._mXML.get(i));
                InputStream in= url.openStream();
                String title=this._mFileNameReader(this._mXML.get(i));
                FileOutputStream fos=new FileOutputStream(new File(title));

                int length = -1;
                byte[] buffer = new byte[1024];// buffer for portion of data from connection
                while ((length = in.read(buffer)) > -1) {
                    fos.write(buffer, 0, length);
                }
                fos.close();
                in.close();
                System.out.println(url+" downloaded...");
            } catch (Exception e)
            {
                System.out.println("Could not download "+this._mXML.get(i));
            }
        }
        return;
    }

    private void _mAudioDownload(){
        int i;
        for (i=0;i<this._mAudio.size();i++){
            try {
                URL url = new URL(this._mAudio.get(i));
                InputStream in= url.openStream();
                String title=this._mFileNameReader(this._mAudio.get(i));
                FileOutputStream fos=new FileOutputStream(new File(title));

                int length = -1;
                byte[] buffer = new byte[1024];// buffer for portion of data from connection
                while ((length = in.read(buffer)) > -1) {
                    fos.write(buffer, 0, length);
                }
                fos.close();
                in.close();
                System.out.println(url+" downloaded...");
            } catch (Exception e)
            {
                System.out.println("Could not download "+this._mAudio.get(i));
            }
        }
        return;
    }

    public void _mDownload(String urlString) throws IOException {
        try {
            System.out.println("Downloading " + urlString + " ...");
            URL url = new URL(urlString);
            String content = "";
            String title = this.titleCreator(urlString);
            int i;
            try (
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(title));
            ) {
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    content += line;
                }
                System.out.println("Page downloaded.");

                this._mHTTPTrack(content);
                this._mHTTPSTrack(content);

                if (this._mImpurityChecker() == 1) {
                    this._mImpurityRemove();
                }

                this._mTrackDependent(content);
                this._mTrackFiles(content);
                this._mRemoveDuplicates();
                this._mORGRemover();

                this._mAddImage();
                this._mAddXML();
                this._mAddPDF();
                this._mAddAudio();

                this._mImagesPrint();
                this._mXMLPrint();
                this._mPDFPrint();
                this._mAudioPrint();

                if (this._mImages.size() != 0) {
                    this._mImagesDownload();
                }
                if (this._mXML.size() != 0) {
                    this._mXMLDownload();
                }
                if (this._mPDF.size() != 0) {
                    this._mPDFDownload();
                }
                if (this._mAudio.size()!=0){
                    this._mAudioDownload();
                }
            }
            System.out.println("\n======================================");
        }
        catch (Exception e){
            System.out.println(urlString+ " can not be downloaded");
            System.out.println("\n======================================");
        }
        return;
    }

    public ArrayList<String> _mGetDeppendencies(){
        return this._mDependencies;
    }
}
