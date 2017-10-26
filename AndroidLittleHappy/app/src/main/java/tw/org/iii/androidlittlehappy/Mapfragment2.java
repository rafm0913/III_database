package tw.org.iii.androidlittlehappy;



import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.Manifest;



import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Mapfragment2 extends Fragment implements OnMapReadyCallback {

    GoogleMap mMap;
    public Mapfragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.mapfragment2, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //ActivityCompat.requestPermissions(this.getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION},12);



        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        LatLng III = new LatLng(22.628216, 120.293043);
        LatLng user1 = new LatLng(22.627230, 120.292534);
       // LatLng user2 = new LatLng(gt.getLocation().getLatitude(),gt.getLocation().getLongitude());

       // mMap.addMarker(new MarkerOptions().position(user2).title("user2"));
        /*
        mMap.addMarker(new MarkerOptions().position(III).title("南區資策會")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.penguin));
        mMap.addMarker(new MarkerOptions().position(user1).title("咖啡買一送一")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.coffee));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(III, 18));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user1, 18));*/

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1234);
            //ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1234);


            return;
        }else {

            setupMyLocation();



        }
    }

    private void setupMyLocation() {
        //noinspection MissingPermission
        mMap.setMyLocationEnabled(true);

        //實作地圖定位按鈕功能
        mMap.setOnMyLocationButtonClickListener(
                new GoogleMap.OnMyLocationButtonClickListener(){
                    @Override
                    public  boolean onMyLocationButtonClick(){
                        GpsTracker gps= new GpsTracker(getActivity());
                        LatLng user3;
                        if(gps.getLocation()!=null) {
                            user3 = new LatLng(gps.getLocation().getLatitude(), gps.getLocation().getLongitude());
                            mMap.addMarker(new MarkerOptions().position(user3).title("ggg"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user3, 18));
                        }
                        return false;
                    }
                }
        );



        GpsTracker gps= new GpsTracker(getContext());
        LatLng user3;
        if(gps.getLocation()!=null) {
            user3 = new LatLng(gps.getLocation().getLatitude(), gps.getLocation().getLongitude());
            mMap.addMarker(new MarkerOptions().position(user3).title("123"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user3, 18));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode){
            case 1234:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    setupMyLocation();

                }else {


                }
                break;

        }



        /*
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null) {
                    fragment.onRequestPermissionsResult(requestCode,permissions,grantResults);
                }
            }
        }
        */


    }


}
