package learning.thisone;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by tushar on 8/25/2015.
 */
public class Conthread {

static String TAG="Conthread";
    public static HttpURLConnection startCon(String linkurl) {
//        Log.e(TAG,linkurl);
        HttpURLConnection conn = null;
        try {
            URL url = new URL(linkurl);
            conn = (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static String readData(HttpURLConnection conn) {
        String webPage = null;
        try {
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            webPage = "";
            String data = "";

            while ((data = reader.readLine()) != null) {
                webPage += data + "\n";
            }
            is.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return webPage;
    }

    public static Bitmap readImg(String url){
        Bitmap bmp=null;
        try {
            Log.e(TAG,url);
            InputStream is = new URL(url).openStream();
            bmp = BitmapFactory.decodeStream(is);
            if(bmp==null){
                Log.e(TAG, "image is not here");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }
}
