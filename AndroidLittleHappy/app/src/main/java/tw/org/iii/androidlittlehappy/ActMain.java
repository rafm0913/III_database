package tw.org.iii.androidlittlehappy;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.List;

public class ActMain extends FragmentActivity implements Mapfragment2.OnMapfragment2SelectedListener{
    double gpsX=0, gpsY=0;
    public static List<CActivitys> iv_activitylist = new ArrayList<CActivitys>();
    public void onGpsSelected(double x, double y) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
        gpsX = x;
        gpsY = y;
    }

    private View.OnClickListener btnSearchActivity_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //非同步程式運行
            SearchAct searchTask = new SearchAct();
            searchTask.execute(new String[] { SearchAct.URL });
            //Log.d("test", String.valueOf(iv_activitylist.size()));
        }
    };


    private View.OnClickListener btnNewActivity_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ActMain.this,NewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putDouble("gpsX", gpsX);
            bundle.putDouble("gpsY", gpsY);
            intent.putExtras(bundle);
            startActivity(intent);
            //Toast.makeText(ActMain.this, "x:" + x  + "  y:" + y, Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener btnProfile_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ActMain.this,ActProfile.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener btnActivityInfo_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ActMain.this,ActivityInfo.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener btnLogOut_click= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SharedPreferences setting=getSharedPreferences("loginInfo",MODE_PRIVATE);

            setting.edit()
                    .clear()
                    .commit();
            finish();
        }
    };

    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Mapfragment2 mapfragment = new Mapfragment2();
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.content, mapfragment).commit();

                    return true;
                case R.id.navigation_chat:

                    return true;
                case R.id.navigation_history:

                    return true;
                case R.id.navigation_profile:

                    profileFragment = new ProfileFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content, profileFragment).commit();
                    return true;

            }
            return false;
        }

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actmain);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
        Initialcomponent();
    }


    @Override
    protected void onResume() {
        super.onResume();

        Mapfragment2 mapfragment = new Mapfragment2();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content, mapfragment).commit();

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    private void Initialcomponent()
    {
        btnLogOut = (Button)findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(btnLogOut_click);
        btnNewActivity=(Button) findViewById(R.id.btnNewActivity);
        btnNewActivity.setOnClickListener(btnNewActivity_Click);
        btnProfile=(Button) findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(btnProfile_Click);
        btnActivityInfo=(Button) findViewById(R.id.btnActivityInfo);
        btnActivityInfo.setOnClickListener(btnActivityInfo_Click);
        btnSearchActivity = (Button)findViewById(R.id.btnSearchActivity) ;
        btnSearchActivity.setOnClickListener(btnSearchActivity_Click);


        SearchAct searchTask = new SearchAct();
        searchTask.execute(new String[] { SearchAct.URL });


        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Mapfragment2 mapfragment = new Mapfragment2();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content, mapfragment).commit();







    }
    Button btnLogOut;
    Button btnNewActivity;
    Button btnProfile;
    Button btnActivityInfo;
    Button btnSearchActivity;
    GoogleMap mMap;
    BottomNavigationView navigation;
    android.support.v4.app.FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction fragmentTransaction;
    Fragment mapFragment;
    ProfileFragment profileFragment;


}
