package learning.thisone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class Web_view extends Activity implements SimpleGestureFilter.SimpleGestureListener{

    String TAG="Webview";
    private SimpleGestureFilter detector;
    private WebView mWebview ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_web_view);


        Intent intent = getIntent();
        String myUrl = intent.getExtras().getString("link");
        mWebview  = new WebView(this);
        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript

        final Activity activity = this;

        mWebview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
        });

        mWebview .loadUrl(myUrl);
        setContentView(mWebview);

        detector = new SimpleGestureFilter(this, this);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }
    @Override
    public void onSwipe(int direction) {


        switch (direction) {

            case SimpleGestureFilter.SWIPE_RIGHT : //str = "Swipe Right";
                break;
            case SimpleGestureFilter.SWIPE_LEFT :  //str = "Swipe Left";
                break;
            case SimpleGestureFilter.SWIPE_DOWN :  //str = "Swipe Down";
                this.finish();
                Log.e(TAG, "ending");
                break;
            case SimpleGestureFilter.SWIPE_UP :    //str = "Swipe Up";
                break;

        }


    }

    @Override
    public void onDoubleTap() {

    }
}

