//in youtube manifest
//android:screenOrientation="landscape"
//android:configChanges="orientation|keyboardHidden"
package learning.thisone;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tushar on 8/10/2015.
 */
public class Ytube extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,SimpleGestureFilter.SimpleGestureListener {

    private YouTubePlayerView youTubeView;
    private SimpleGestureFilter detector;
    String TAG = "ytube";
    public static String video_id;
    String developerKey="AIzaSyAVgJpNEIIazIYMI7Ji5WpJeja4URYrBxY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.youtube_view);

        Intent intent=getIntent();
        video_id=intent.getExtras().getString("id");

//        video_id="oA-fc3D09ZY";

        Log.e(TAG, "got this id -> " + video_id);
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(developerKey, this);

        detector = new SimpleGestureFilter(this, this);
    }//oncreate ends


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer player, boolean wasRestored) {
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);
        if (!wasRestored) {
            Log.e(TAG,"loading video id->"+video_id);
            player.loadVideo(video_id);
        }
    }
    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {

        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
        }

        @Override
        public void onPlaying() {
        }

        @Override
        public void onSeekTo(int arg0) {
        }

        @Override
        public void onStopped() {
        }

    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        super.finish();

    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        super.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        super.finish();
    }

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {

        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
            finish();
        }

        @Override
        public void onVideoStarted() {
        }
    };



    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, 1).show();
        } else {
            String errorMessage = "error initializing player";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            // Retry initial
            // ization if user performed a recovery action
            getYouTubePlayerProvider().initialize(developerKey, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }


    public void onSwipe(int direction) {
        String str = "";

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

}//main activity ends