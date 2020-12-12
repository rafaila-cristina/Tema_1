import java.util.ArrayList;

public class Extensions {
    private ArrayList<String> _mImageExtensions=new ArrayList<String>();
    private ArrayList<String> _mXMLExtensions=new ArrayList<String>();
    private ArrayList<String> _mPDFExtensions=new ArrayList<String>();
    private ArrayList<String> _mAudioExtensions=new ArrayList<String>();
    public Extensions(){
        this._mImageExtensions.add("jpeg");
        this._mImageExtensions.add("bmp");
        this._mImageExtensions.add("gif");
        this._mImageExtensions.add("png");
        this._mImageExtensions.add("jpg");
        this._mImageExtensions.add("JPEG");
        this._mImageExtensions.add("BMP");
        this._mImageExtensions.add("GIF");
        this._mImageExtensions.add("PNG");
        this._mImageExtensions.add("JPG");

        this._mXMLExtensions.add("xml");
        this._mXMLExtensions.add("XML");

        this._mPDFExtensions.add("pdf");
        this._mPDFExtensions.add("PDF");

        this._mAudioExtensions.add("wav");
        this._mAudioExtensions.add("WAV");
        this._mAudioExtensions.add("mp3");
        this._mAudioExtensions.add("MP3");
        this._mAudioExtensions.add("mp4");
        this._mAudioExtensions.add("MP4");
    }

    public int _mImageCheck(String _inExtension){
        if (_mImageExtensions.contains(_inExtension)){
            return 1;
        }
        return 0;
    }

    public int _mXMLCheck(String _inExtension){
        if (_mXMLExtensions.contains(_inExtension)){
            return 1;
        }
        return 0;
    }

    public int _mPDFCheck(String _inExtension){
        if (_mPDFExtensions.contains(_inExtension)){
            return 1;
        }
        return 0;
    }

    public int _mAudioCheck(String _inExtension){
        if (_mAudioExtensions.contains(_inExtension)){
            return 1;
        }
        return 0;
    }
}
