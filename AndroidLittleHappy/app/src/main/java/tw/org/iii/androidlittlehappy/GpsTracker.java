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
        return null;

    }

    try {
        LocationManager lm = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        /*
        boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNETWORKEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isGPSEnabled||isNETWORKEnabled) {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            Location loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            return loc;
        }
        else {
            Toast.makeText(context,"請開啟定位服務",Toast.LENGTH_LONG).show();
        }
        */

        /*
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //如果GPS或網路定位開啟，更新位置
            Criteria criteria = new Criteria();  //資訊提供者選取標準
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            String bestProvider = lm.getBestProvider(criteria, true);    //選擇精準度最高的提供者
            lm.requestLocationUpdates(bestProvider,1000, 1, this);
            Location loc = lm.getLastKnownLocation(bestProvider);
            return loc;
        } else {
            Toast.makeText(context, "請開啟定位服務", Toast.LENGTH_LONG).show();

        }
        */


        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000, 1, this);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000, 1, this);
        Location gpsLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location networkLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        Location currentLocation = gpsLocation;
        if (isBetterLocation(networkLocation, currentLocation)){
            currentLocation = networkLocation;
        }

        return  currentLocation;



        }
    catch (Exception e)
        {
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

    //確認GPS還是network定位比較好的方法
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }
        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;
        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }
        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;
        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());
        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }
    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }
}
