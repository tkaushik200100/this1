package learning.thisone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public class F2 extends android.support.v4.app.Fragment {

        public static String TAG = "F2";
        public static String linkurl=null;
        public static String title="";
        public static String tag="";
        private static int countr=0;
        public static String imglink=null;

        WebView myWebView;

        public static F2 newInstance()
        {
            F2 f=new F2();
            countr++;
            Log.e(TAG,"f2 time-"+countr);


            return f;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            ViewGroup rootView =(ViewGroup) inflater.inflate(R.layout.f2full, container, false);

             //ProgressBar progress;
             //progress = (ProgressBar) rootView.findViewById(R.id.progbar);
//            rootView.addView(progress);
           // progress.setVisibility(View.VISIBLE);
            Tfetch t = new Tfetch();
            t.start();

            myWebView = (WebView)rootView.findViewById(R.id.mywebview);

            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.setWebViewClient(new MyWebViewClient());

             while(imglink==null){}

           // progress.setVisibility(View.GONE);

            Log.e(TAG,"imglink="+imglink);

            myWebView.loadUrl(imglink);
            myWebView.clearFocus();
       /*     myWebView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                }
            });

*/
            myWebView.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // TODO Auto-generated method stub
                 //   Intent intent = new Intent(getActivity(), Web_view.class);
                  //  intent.putExtra("link", myUrl);
                 //   startActivity(intent);
                    return false;
                }
            });




            return rootView;
        }

        public class Tfetch extends Thread {

            public void run() {
                // /need to give the lingurl here !

                Vars object = new Vars();
                linkurl = object.link + "{\"action\":\"getItem\",\"imei\":" + object.uid + ",\"itemType\":\"article\"}";
                Log.e(TAG, linkurl);


                HttpURLConnection con = Conthread.startCon(linkurl);
                String data = Conthread.readData(con);
                F2 obj = new F2();
                if (data != null) {

                    try {
                        JSONObject jsonResponse = new JSONObject(data);
                        JSONObject jsonObject = jsonResponse.getJSONObject("data");
                        obj.imglink = jsonObject.getString("url");
                        title = jsonObject.getString("title");
                        tag = jsonObject.getString("tag");
                    } catch (JSONException j) {
                        j.printStackTrace();
                    }
                    Log.e(TAG, data);
                }
                else
                {
                    obj.imglink="www.buzzfeed.com/javiermoreno/what-country-matches-your-personality";
                }
            }
        }

        private class MyWebViewClient extends WebViewClient {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e(TAG,"loading this -"+url);
                view.loadUrl(url);
                return true;
            }
        }




    }
