package tw.org.iii.androidlittlehappy;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by iii on 2017/10/27.
 */

public class PassValueFactory {
    PassValueFactory(){

    }

    void fragmentToActivity(double x, double y, Activity myActivity){
        //send data from fragment to activity
        //getActivity() in a Fragment returns the Activity the Fragment is currently associated with.
        Intent intent = new Intent(myActivity, ActMain.class);
        intent.putExtra("gpsX", x);
        intent.putExtra("gpsX", y);
        //myActivity.startActivity(intent);
    }


}
