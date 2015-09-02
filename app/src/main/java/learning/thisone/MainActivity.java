package learning.thisone;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.util.Random;

public class MainActivity extends FragmentActivity {

    Vars obj=new Vars();
    SharedPreferences prefs;
    private static int pages=3;
    private ViewPager mPager;
    String TAG="Main";
    private FragmentPagerAdapter mpageadapter;

    public static Boolean registered=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        obj.uid = telephonyManager.getDeviceId();

        Reg();
        setContentView(R.layout.activity_main);


        while(registered==false){}

        mPager = (ViewPager) findViewById(R.id.pager);
        mpageadapter = new Fspa(getSupportFragmentManager());


        // wrap pager to provide a minimum of 4 pages
        MinFragmentPagerAdapter wrappedMinAdapter = new MinFragmentPagerAdapter(getSupportFragmentManager());
        wrappedMinAdapter.setAdapter(mpageadapter);


        // wrap pager to provide infinite paging with wrap-around
        PagerAdapter wrappedAdapter = new InfinitePagerAdapter(wrappedMinAdapter);


        // actually an InfiniteViewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(wrappedAdapter);

}//onCreate ends

    protected void Reg() {


        ConnectionDetector cd=new ConnectionDetector(getApplicationContext());
        Boolean isconnected=cd.isConnectingToInternet();

        if(!isconnected)
        {
            Toast.makeText(this,"Please connect to internet",Toast.LENGTH_LONG).show();
            this.finish();
        }




        Log.e(TAG, "app started");
        prefs = getSharedPreferences("com.mycompany.myAppName", MODE_PRIVATE);
        if (prefs.getBoolean("firstrun", true)) {
//            Toast.makeText(this,"first time running",Toast.LENGTH_SHORT).show();
            // Do first run stuff here then set 'firstrun' as false

            //TODO REGISTER THE USER
            Th t= new Th();
            t.start();
            Log.e(TAG,"starting thread");


            // using the following line to edit/commit prefs
            prefs.edit().putBoolean("firstrun", false).commit();
        }
        else
        {
            registered=true;
        }
        Log.e(TAG,"registered ="+registered);
    }



    private class Fspa extends FragmentPagerAdapter {
        public Fspa(FragmentManager fm) {
            super(fm);
        }

        public android.support.v4.app.Fragment getItem(int position) {


      //      Random rand = new Random();
      //      position = rand.nextInt((2 - 0) + 1) + 0;
     //     if(position>2) {
    //          position = 0;
      //    }
            //Log.e(TAG,"position is "+position);
            switch (position) {
                case 0:
                    return F1.newInstance();

                case 1:
                    return F2.newInstance();

                case 2:
                    return F3.newInstance();

              default:
                  return F1.newInstance();
                //return null;
            }

        }
        @Override
        public int getCount() {
            return pages;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.e(TAG,"instantiating item at position-"+position);
            return super.instantiateItem(container, position);
        }
    }



class Th extends Thread
{
public void run()
{
    String reglink=obj.link+"{\"action\":\"addNewUser\",\"imei\":"+obj.uid+"}";
    HttpURLConnection con= Conthread.startCon(reglink);
    String data= Conthread.readData(con);
    registered=true;
    Log.e(TAG,data);

}
}

}
