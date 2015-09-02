// link => https://www.youtube.com/watch?v=_TWbD3MKfMI
package learning.thisone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by tushar on 8/25/2015.
 */
public class F3 extends android.support.v4.app.Fragment implements YouTubeThumbnailView.OnInitializedListener {

    public static String TAG = "F3";

    public static String title;
    public static String tag;
    private static int countr=0;

    public static final String DEVELOPER_KEY = "AIzaSyAVgJpNEIIazIYMI7Ji5WpJeja4URYrBxY";

    private YouTubeThumbnailLoader youTubeThumbnailLoader;
    private YouTubeThumbnailView thumbnailView;
    Vars object=new Vars();


    public static F3 newInstance()
    {
        F3 f=new F3();
        countr++;
      //  Log.e(TAG, "third frag time =" + countr);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

object.VIDEO_ID=null;
       // ProgressDialog progress=new ProgressDialog(getActivity());
        ViewGroup rootView =(ViewGroup) inflater.inflate(R.layout.f3, container, false);
        TextView f1_title=(TextView)rootView.findViewById(R.id.f3_title);
        TextView f1_tag=(TextView)rootView.findViewById(R.id.f3_tag);
        //progress = ProgressDialog.show(getActivity(), "dialog title", "dialog message", true);

        Tfetch t = new Tfetch();
//        img=null;
        t.start();

while (object.VIDEO_ID==null){}
//progress.dismiss();
        tag=tag.replaceAll("\\s+","");
        f1_tag.setText("#"+tag+"\n#YouTube");
        f1_title.setText(title);

        Log.e(TAG, "video id after while loop -" + object.VIDEO_ID);
        thumbnailView = (YouTubeThumbnailView)rootView.findViewById(R.id.thumbnail);
        thumbnailView.initialize(DEVELOPER_KEY, this);

        thumbnailView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Ytube.class);
                intent.putExtra("id", object.VIDEO_ID);
                Log.e(TAG, "done putting intent " + object.VIDEO_ID);
                startActivity(intent);
            }
        });

        return rootView;
    }

    public class Tfetch extends Thread {

        public void run() {
            //need to give the lingurl here !

            Vars object=new Vars();
            String req;
            req=object.link+"{\"action\":\"getItem\",\"imei\":"+object.uid+",\"itemType\":\"video\"}";

            HttpURLConnection con = Conthread.startCon(req);
            String data = Conthread.readData(con);

if(data==null)
{
    object.VIDEO_ID="oA-fc3D09ZY";
return;
}
            try {
                JSONObject jsonResponse = new JSONObject(data);
                JSONObject jsonObject=jsonResponse.getJSONObject("data");
                object.video_url=jsonObject.getString("url");
                title=jsonObject.getString("title");
                tag=jsonObject.getString("tag");
            } catch (JSONException j) {
                j.printStackTrace();
            }


           object.video_url=object.video_url.replaceAll("\\s+","");
            object.VIDEO_ID=fetch_id(object.video_url);
            //object.VIDEO_ID=fetch_id("https://www.youtube.com/watch?v=oA-fc3D09ZY");
            Log.e(TAG,object.video_url);
            //this is response, now read JSON !

        }
    }


    public void onInitializationFailure(YouTubeThumbnailView thumbnailView,
                                        YouTubeInitializationResult errorReason) {

        String errorMessage =
                String.format("onInitializationFailure (%1$s)",
                        errorReason.toString());
        Log.e(TAG,errorMessage);
    }


    public void onInitializationSuccess(YouTubeThumbnailView thumbnailView,
                                        YouTubeThumbnailLoader thumbnailLoader) {


        youTubeThumbnailLoader = thumbnailLoader;
        thumbnailLoader.setOnThumbnailLoadedListener(new ThumbnailListener());

        Log.e(TAG, "thumbnail id-" + object.VIDEO_ID);
        String vid=object.VIDEO_ID;

        youTubeThumbnailLoader.setVideo(vid);
    }



    private final class ThumbnailListener implements
            YouTubeThumbnailLoader.OnThumbnailLoadedListener {

        @Override
        public void onThumbnailLoaded(YouTubeThumbnailView thumbnail, String videoId) {

        }

        @Override
        public void onThumbnailError(YouTubeThumbnailView thumbnail,
                                     YouTubeThumbnailLoader.ErrorReason reason) {

            Log.e(TAG,"onthumbnailerror "+reason);
        }
    }

public String fetch_id(String url)
{
    String vid=null;
    String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";

    Pattern compiledPattern = Pattern.compile(pattern);
    Matcher matcher = compiledPattern.matcher(url);

    if(matcher.find()) {
        vid= matcher.group();
    }
    if(vid==null)
    {
        vid="oA-fc3D09ZY";
    }
return vid;
}

}