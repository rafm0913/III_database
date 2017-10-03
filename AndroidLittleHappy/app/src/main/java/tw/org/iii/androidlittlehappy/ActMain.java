package tw.org.iii.androidlittlehappy;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActMain extends FragmentActivity implements OnMapReadyCallback {


    private View.OnClickListener btnNewActivity_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ActMain.this,NewActivity.class);
            startActivity(intent);
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
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavugation_click=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.home:
                    Toast.makeText(ActMain.this,"this is home",Toast.LENGTH_LONG).show();
                    break;
                case R.id.Profile:
                    Intent intent = new Intent(ActMain.this,ActProfile.class);
                    startActivity(intent);
                    break;
                case R.id.setting:
                    Toast.makeText(ActMain.this,"this is setting",Toast.LENGTH_LONG).show();
                    break;
                case R.id.logs:
                    Toast.makeText(ActMain.this,"this is logs",Toast.LENGTH_LONG).show();
                    break;
            }
            return true;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actmain);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Initialcomponent();
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        LatLng III = new LatLng(22.628216, 120.293043);
        LatLng user1 = new LatLng(22.627230, 120.292534);
        mMap.addMarker(new MarkerOptions().position(III).title("南區資策會")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.penguin));
        mMap.addMarker(new MarkerOptions().position(user1).title("咖啡買一送一")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.coffee));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(III, 18));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user1, 18));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

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
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavugation_click);


    }
    Button btnLogOut;
    Button btnNewActivity;
    Button btnProfile;
    Button btnActivityInfo;
    GoogleMap mMap;
    BottomNavigationView bottomNavigationView ;

}
