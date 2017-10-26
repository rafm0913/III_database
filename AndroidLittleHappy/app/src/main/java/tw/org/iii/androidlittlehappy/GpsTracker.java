package tw.org.iii.androidlittlehappy;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by iii on 2017/10/18.
 */

public class GpsTracker implements LocationListener {
    Context context;

    public GpsTracker(Context c) {
       context = c;
    }


    public Location getLocation() {
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        Toast.makeText(context,"Permission not granted",Toast.LENGTH_SHORT).show();
        Log.v("testtest","權限不允許");
        return null;

    }

    try {
        Log.v("testtest","try");
        LocationManager lm = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isGPSEnabled) {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            Location loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            return loc;
        }
        else {
            Toast.makeText(context,"Please enale GPS",Toast.LENGTH_LONG).show();
        }

        }
    catch (Exception e)
        {
            Log.v("testtest","例外發生了");
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
