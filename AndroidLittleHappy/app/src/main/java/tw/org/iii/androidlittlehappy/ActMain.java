package tw.org.iii.androidlittlehappy;

import android.*;
import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.iid.FirebaseInstanceId;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

import tw.org.iii.androidlittlehappy.fcm.MyInstanceIDService;
import tw.org.iii.androidlittlehappy.fcm.SendRegistrationToken;

public class ActMain extends FragmentActivity implements Mapfragment2.OnMapfragment2SelectedListener{
    double gpsX=0, gpsY=0;
    public static List<CActivitys> iv_activitylist = new ArrayList<CActivitys>();

    public static List<CActivitys> iv_activitylist_I_join = new ArrayList<CActivitys>();
    public static List<CActivitys> iv_activitylist_I_initiate= new ArrayList<CActivitys>();
    public static List<CActivitys> iv_activitylist_I_can_see = new ArrayList<CActivitys>();
    public static List<CActivitys> iv_activitylist_I_have_seen = new ArrayList<CActivitys>();

    public static Hashtable<String, CCustomers> Hashtable_UserNameToCust = new Hashtable<String, CCustomers>();


    public static String KEY = "tw.org.iii.androidlittlehappy";
    public static String[] typelistString;
    public static int[] typelistImg;
    public static int[] typelistIndex;



    public void onGpsSelected(double x, double y) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
        gpsX = x;
        gpsY = y;
    }

//    private View.OnClickListener btnSearchActivity_Click = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            //非同步程式運行
//            SearchAct searchTask = new SearchAct();
//            searchTask.execute(new String[] { SearchAct.URL });
//            //Log.d("test", String.valueOf(iv_activitylist.size()));
//        }
//    };
//
//
//    private View.OnClickListener btnNewActivity_Click = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Intent intent = new Intent(ActMain.this,NewActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putDouble("gpsX", gpsX);
//            bundle.putDouble("gpsY", gpsY);
//            intent.putExtras(bundle);
//            startActivity(intent);
//            //Toast.makeText(ActMain.this, "x:" + x  + "  y:" + y, Toast.LENGTH_SHORT).show();
//        }
//    };

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
            Intent intent = new Intent(ActMain.this,ActCheckUserInforPage.class);
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
            CPublicParameters.user = null;
            finish();
            Intent intent= new Intent(ActMain.this,ActAppLogo.class);
            //finish在ActMain前所有的activity，並重建ActAppLogo [1]
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            ActMain.this.finish();
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
                    FragmentChat fragmentChat = new FragmentChat();
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content, fragmentChat).commit();

                    return true;
                case R.id.navigation_history:
                    actHistory = new ActHistory();
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content, actHistory).commit();

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


        //請求GPS權限，請求完後才會執行SearchAct，之後才是Initialcomponent
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},CDictionary.BK_GPS_PERMISSION_REQUEST_CODE);
        }
        else {

            GpsTracker track = new GpsTracker(this);

            SearchAct searchTask = new SearchAct(track.getLocation().getLatitude(), track.getLocation().getLongitude(), new SearchAct.AsynResponse() {
                @Override
                public void processFinish(Boolean output) {
                    Initialcomponent();
                }
            });
            searchTask.execute(new String[] { SearchAct.URL });

        }

        //SharedPreferences setting=getSharedPreferences(ActMain.KEY,MODE_PRIVATE);
        String token = getSharedPreferences(ActMain.KEY,MODE_PRIVATE)
                .getString("Token", "null");
        if("null".equals(token)){
            MyInstanceIDService.token = FirebaseInstanceId.getInstance().getToken();
            Log.d("FCM", "shareprefer token: " + MyInstanceIDService.token);
        }else{
            MyInstanceIDService.token = token;
            Log.d("FCM", "token:已有 " + MyInstanceIDService.token);
        }
        SendRegistrationToken insertToken = new SendRegistrationToken(MyInstanceIDService.token);
        insertToken.execute(new String[] { SendRegistrationToken.URL });
        //Log.d("FCM", "userName" + CPublicParameters.user.getfUserName() + "token:已有 " + MyInstanceIDService.token);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode) {
            case CDictionary.BK_GPS_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    GpsTracker track = new GpsTracker(this);

                    SearchAct searchTask = new SearchAct(track.getLocation().getLatitude(), track.getLocation().getLongitude(), new SearchAct.AsynResponse() {
                        @Override
                        public void processFinish(Boolean output) {
                            Initialcomponent();
                        }
                    });
                    searchTask.execute(new String[] { SearchAct.URL });

                } else {

                    Toast.makeText(this,"請允許GPS權限",Toast.LENGTH_LONG);
                    return;
                }
        }
    }








    @Override
    protected void onResume() {
        super.onResume();


        Mapfragment2 mapfragment = new Mapfragment2();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content, mapfragment).commit();

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                Log.d("FCM", "Key: " + key + " Value: " + value);
            }
        }else{
            Log.d("FCM", "沒有包裹");
        }
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
//        btnNewActivity=(Button) findViewById(R.id.btnNewActivity);
//        btnNewActivity.setOnClickListener(btnNewActivity_Click);
        btnProfile=(Button) findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(btnProfile_Click);
        btnActivityInfo=(Button) findViewById(R.id.btnActivityInfo);
        btnActivityInfo.setOnClickListener(btnActivityInfo_Click);
//        btnSearchActivity = (Button)findViewById(R.id.btnSearchActivity) ;
//        btnSearchActivity.setOnClickListener(btnSearchActivity_Click);


        //SearchAct searchTask = new SearchAct();
        //searchTask.execute(new String[] { SearchAct.URL });


        //GpsTracker track = new GpsTracker(getApplication());
        //SearchAct searchTask = new SearchAct(track.getLocation().getLatitude(),track.getLocation().getLongitude());
        //searchTask.execute(new String[] { SearchAct.URL });



        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Mapfragment2 mapfragment = new Mapfragment2();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content, mapfragment).commit();


        //設定活動種類對應index和圖片id
        LinkedHashMap typeIndex = new LinkedHashMap();
        typeIndex.put("共乘","1");
        typeIndex.put("分享","2");
        typeIndex.put("吃","3");
        typeIndex.put("咖啡","4");
        typeIndex.put("折扣","5");
        typeIndex.put("服飾","6");
        typeIndex.put("買一送一","7");
        typeIndex.put("電影","8");

        LinkedHashMap typeImgId = new LinkedHashMap();
        typeImgId.put("共乘",String.valueOf(R.drawable.type_sharetaxi));
        typeImgId.put("分享",String.valueOf(R.drawable.type_share));
        typeImgId.put("吃",String.valueOf(R.drawable.type_eat));
        typeImgId.put("咖啡",String.valueOf(R.drawable.type_coffee));
        typeImgId.put("折扣",String.valueOf(R.drawable.type_discount));
        typeImgId.put("服飾",String.valueOf(R.drawable.type_dress));
        typeImgId.put("買一送一",String.valueOf(R.drawable.type_50percentoff));
        typeImgId.put("電影",String.valueOf(R.drawable.type_movie));

        typelistIndex = new int[typeIndex.size()];
        typelistImg = new int[typeIndex.size()];
        typelistString = new String[typeIndex.size()];

        int i = 0;

        for (Object key : typeIndex.keySet()){

            typelistIndex[i] = Integer.valueOf(typeIndex.get(key).toString());
            typelistImg[i] = Integer.valueOf(typeImgId.get(key).toString());
            typelistString[i] = key.toString();
            i++;
            Log.d("test", key + " : " + typeIndex.get(key));

        }
        lblUserName=(TextView)findViewById(R.id.lblUserName);
        lblUserName.setText(CPublicParameters.user.getfUserName().toString());



    }

    public static class BottomNavigationViewHelper {
        public static void disableShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    //noinspection RestrictedApi
                    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    //noinspection RestrictedApi
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("BNVHelper", "Unable to get shift mode field", e);
            } catch (IllegalAccessException e) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e);
            }
        }
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

    ActHistory actHistory;

    TextView lblUserName;


}

/*
Reference:
[1] http://blog.51cto.com/glblong/1209829  (2013)對岸碼農，關於Activity啟動模式介紹



*/