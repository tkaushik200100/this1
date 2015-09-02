package learning.thisone;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

public class ImgView extends Activity implements SimpleGestureFilter.SimpleGestureListener {

    private SimpleGestureFilter detector;
    String TAG="Gesture";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imgview);
        ImageView imageView=(ImageView)findViewById(R.id.touchable);
        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("image");

        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageView.setImageBitmap(bmp);
        detector = new SimpleGestureFilter(this, this);
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
}
