package learning.thisone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;

/**
 * Created by tushar on 8/25/2015.
 */
public class F1 extends android.support.v4.app.Fragment {

    static String TAG = "F1";
    static String linkurl="";
    private static int countr=0;
    public static Bitmap img=null;
    public static boolean notfound=false;
    public static String title="";
    public static String tag="";

    public static F1 newInstance()
    {
        F1 f=new F1();
        countr++;
        Log.e(TAG,"first frag time-"+countr);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        notfound=false;
        ViewGroup rootView =(ViewGroup) inflater.inflate(R.layout.f1, container, false);

        Tfetch t = new Tfetch();
        img=null;
        t.start();
        //Toast.makeText(getApplicationContext(), "running", Toast.LENGTH_SHORT).show();
       // TextView tv=(TextView)rootView.findViewById(R.id.tv);
        ImageView imageView=(ImageView)rootView.findViewById(R.id.img);
        TextView f1_title=(TextView)rootView.findViewById(R.id.f1_title);
        TextView f1_tag=(TextView)rootView.findViewById(R.id.f1_tag);
        while(img==null&&notfound==false)
        {
       //     Log.e(TAG,""+notfound);
        }

        if(notfound)
        {
            Log.e(TAG, "image was not available");
            imageView.setImageResource(R.drawable.download);
        }
        else {
            Log.e(TAG, "image set");
            imageView.setImageBitmap(img);
            f1_tag.setText(tag);
            f1_title.setText(title);
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ImgView.class);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    img.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image", byteArray);
                    startActivity(intent);
                }
            });
        }
        return rootView;
    }

    public class Tfetch extends Thread {

        public void run() {
            //need to give the linkurl here !
            Vars object=new Vars();
            linkurl=object.link+"{\"action\":\"getItem\",\"imei\":"+object.uid+",\"itemType\":\"image\"}";
            Log.e(TAG,linkurl);
            HttpURLConnection con = Conthread.startCon(linkurl);
            String data = Conthread.readData(con);
            F1 obj=new F1();
            if(data==null)
            {
                obj.notfound=true;
                return;
            }else {
                Log.e(TAG, data);
            }
            try {
                JSONObject jsonResponse = new JSONObject(data);
                JSONObject jsonObject=jsonResponse.getJSONObject("data");
                data=jsonObject.getString("url");
                title=jsonObject.getString("title");
                tag=jsonObject.getString("tag");
            } catch (JSONException j) {
                j.printStackTrace();
            }


//            Log.e(TAG,data);
            //this is response, now read JSON !
            //String urlfromresponse="https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQCC2q-B6Q8K9eyh0uWBb9lFxgR7dWdyRxv7E0YugFjcY9Nxm5J7w";
            // This is gif -> String urlfromresponse="http://i.imgur.com/DfQqM.gif";
            Bitmap image=null;
                    image=Conthread.readImg(data);
Log.e(TAG,"Returned");
            if(image!=null) {
                obj.img = image;
            }else
            {
                obj.notfound=true;
            }
Log.e(TAG,""+obj.notfound);
        }
    }

    }