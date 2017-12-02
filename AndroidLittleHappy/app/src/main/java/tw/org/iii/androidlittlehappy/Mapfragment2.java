package tw.org.iii.androidlittlehappy;



import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.github.ikidou.fragmentBackHandler.BackHandlerHelper;
import com.github.ikidou.fragmentBackHandler.FragmentBackHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class Mapfragment2 extends Fragment implements OnMapReadyCallback, FragmentBackHandler {

    OnMapfragment2SelectedListener mCallback;

    public boolean onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            //外理返回键
            Intent intentHome = new Intent(Intent.ACTION_MAIN);
            intentHome.addCategory(Intent.CATEGORY_HOME);
            intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentHome);

            return true;
        } else {
            // 如果不包含子Fragment
            // 或子Fragment没有外理back需求
            // 可如直接 return false;
            // 注：如果Fragment/Activity 中可以使用ViewPager 代替 this
            //
            return BackHandlerHelper.handleBackPress(this);
        }
    }


    // Container Activity must implement this interface
    public interface OnMapfragment2SelectedListener {
        public void onGpsSelected(double x, double y);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnMapfragment2SelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMapfragment2SelectedListener");
        }
    }

    GoogleMap mMap;
    public Mapfragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.mapfragment2, container, false);

        FloatingActionButton ftbNewActivity = (FloatingActionButton)v.findViewById(R.id.fabtnNewActivity);
        ftbNewActivity.setOnClickListener(fabtnNewActivity_Click);
        FloatingActionButton ftbSearchActivity = (FloatingActionButton)v.findViewById(R.id.fabtnSearchActivity);
        ftbSearchActivity.setOnClickListener(fabtnSearchActivity_Click);

        return v;
    }

    private View.OnClickListener fabtnNewActivity_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            GpsTracker gps= new GpsTracker(getActivity());

            Intent intent = new Intent(getActivity(),NewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putDouble("gpsX", gps.getLocation().getLatitude());
            bundle.putDouble("gpsY", gps.getLocation().getLongitude());
            intent.putExtras(bundle);
            startActivity(intent);

        }
    };
    private View.OnClickListener fabtnSearchActivity_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //有改過
            GpsTracker track = new GpsTracker(getActivity());
            SearchAct searchTask = new SearchAct(track.getLocation().getLatitude(),track.getLocation().getLongitude());
            searchTask.execute(new String[] { SearchAct.URL });

        }
    };




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
        /////自訂地圖格式
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.wellton));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }///////自訂地圖格式


        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

//            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1234);



            return;
        }else {

            setupMyLocation();
            setupMarkerInfoWindow();

        }


    }

    private void setupMarkerInfoWindow() {
        //參與活動介面跳出
        final MapWrapperLayout mapWrapperLayout = (MapWrapperLayout)getActivity().findViewById(R.id.map_linear_layout);

        // MapWrapperLayout initialization
        // 39 - default marker height
        // 20 - offset between the default InfoWindow bottom edge and it's content bottom edge
        mapWrapperLayout.init(mMap, getPixelsFromDp(getActivity(), 39 + 20));

        // We want to reuse the info window for all the markers,
        // so let's create only one class member instance
        this.infoWindow = (ViewGroup)getActivity().getLayoutInflater().inflate(R.layout.info_window, null);
        this.infoActTitle = (TextView)infoWindow.findViewById(R.id.lbltitle);
        this.infoActContent = (TextView)infoWindow.findViewById(R.id.lblcontent);
        this.infoActInitiator = (TextView)infoWindow.findViewById(R.id.lblmember);
        this.infoTimer = (TextView)infoWindow.findViewById(R.id.info_timer);
        this.infoimgInitiator =(ImageView)infoWindow.findViewById(R.id.imgMember);
        this.infobtnInterest = (Button)infoWindow.findViewById(R.id.btnInterest);

        // Setting custom OnTouchListener which deals with the pressed state
        // so it shows up
        this.infoButtonListener = new OnInfoWindowElemTouchListener(infobtnInterest,
                getResources().getDrawable(R.drawable.round_but_blue_sel), //btn_default_normal_holo_light
                getResources().getDrawable(R.drawable.round_but_gray_sel)) //btn_default_pressed_holo_light
        {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button

                //mark.title = actid
                Toast.makeText(getActivity(), marker.getTitle() + "'s button clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),ActivityInfo.class);

                //包裹丟進去
                Bundle bund = new Bundle();

                //actId
                int actId = Integer.parseInt(marker.getTitle());
                bund.putInt("actId", actId);
                intent.putExtras(bund);

                startActivity(intent);

            }
        };
        this.infobtnInterest.setOnTouchListener(infoButtonListener);


        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public View getInfoContents(Marker marker) {
                // Setting up the infoWindow with current's marker info
                int activityId = Integer.valueOf(marker.getTitle());

                for (int i = 0; i < ActMain.iv_activitylist_I_can_see.size(); i++) {
                    if (ActMain.iv_activitylist_I_can_see.get(i).getId()==activityId){
                        infoActTitle.setText(ActMain.iv_activitylist_I_can_see.get(i).getTitle().toString());
                        infoActContent.setText(ActMain.iv_activitylist_I_can_see.get(i).getContent().toString());
                        //計算剩下時間
                        //定義時間格式
                        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        try
                        {
                            Date dStart = sdf.parse(ActMain.iv_activitylist_I_can_see.get(i).getCreateTime());
                            long limitTime = Integer.parseInt(ActMain.iv_activitylist_I_can_see.get(i).getLimitTime())*60*60*1000;
                            long getSystemTime = System.currentTimeMillis();
                            long diff = (dStart.getTime()- TimeZone.getDefault().getRawOffset()+limitTime) -getSystemTime;

                            long minute = ((diff/1000)/60)%60;
                            long hour =  ((diff/1000)/60)/60;
                            Log.v("lefttime",String.valueOf(hour)+""+String.valueOf(minute)+"分");
                            Log.v("lefttime","發起時間"+String.valueOf(dStart)+" 有效時間"+String.valueOf(limitTime)+"現在時間"+String.valueOf(System.currentTimeMillis()));
                            if(diff>0){
                                infoTimer.setText("活動剩餘"+String.valueOf(hour)+"時"+String.valueOf(minute)+"分");
                            }else {
                                infoTimer.setText("活動結束");
                            }
                        }
                        catch (Exception e)
                        {
                        }



                        Log.d("test", String.valueOf(ActMain.iv_activitylist_I_can_see.size()));
                        //infoActInitiator.setText(ActMain.iv_activitylist_I_can_see.get(i).getCreator().toString());
                        if (ActMain.Hashtable_UserNameToCust.containsKey(ActMain.iv_activitylist_I_can_see.get(i).getCreator().toString()))
                        //if(false)
                        {

                            String initiatorID = ActMain.iv_activitylist_I_can_see.get(i).getCreator().toString();
                            //設定球球發起者暱稱
                            infoActInitiator.setText
                                    (
                                            ActMain.Hashtable_UserNameToCust.get(initiatorID).getfNickName()
                                    );

                            //設定球球發起者大頭貼
                            int mascotID = Integer.valueOf(ActMain.Hashtable_UserNameToCust.get(initiatorID).getfMascot());
                            infoimgInitiator.setImageResource(CPublicParameters.images[mascotID]);


                        }
                        else
                        {
                            infoActInitiator.setText(ActMain.iv_activitylist_I_can_see.get(i).getCreator().toString());
                        }
                        infoButtonListener.setMarker(marker);
                    }
                }

                // We must call this to set the current marker and infoWindow references
                // to the MapWrapperLayout
                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                return infoWindow;
            }
        });

    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }





    @TargetApi(Build.VERSION_CODES.N)
    private void setupMyLocation() {
        //noinspection MissingPermission
        mMap.setMyLocationEnabled(true);

        //實作地圖定位按鈕功能
//        mMap.setOnMyLocationButtonClickListener(
//                new GoogleMap.OnMyLocationButtonClickListener(){
//                    @Override
//                    public  boolean onMyLocationButtonClick(){
//                        GpsTracker gps= new GpsTracker(getActivity());
//                        LatLng user3;
//                        if(gps.getLocation()!=null) {
//                            user3 = new LatLng(gps.getLocation().getLatitude(), gps.getLocation().getLongitude());
//                            mMap.addMarker(new MarkerOptions().position(user3).title("ggg"));
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user3, 16));
//
//                            /*
//                            for (int i = 0; i < ActMain.iv_activitylist.size(); i++) {
//                                mMap.addMarker(new MarkerOptions().position(new LatLng(ActMain.iv_activitylist.get(i).getGpsX(),ActMain.iv_activitylist.get(i).getGpsY())).title(ActMain.iv_activitylist.get(i).getTitle()));
//                                Log.d("test", String.valueOf(ActMain.iv_activitylist.get(i).getGpsX()));
//                            }
//                            Log.d("test", String.valueOf(ActMain.iv_activitylist.size()));
//                            */
//
//                        }
//                        return false;
//                    }
//                }
//        );


        GpsTracker gps= new GpsTracker(getActivity());
        LatLng user3;
        if(gps.getLocation()!=null) {
            user3 = new LatLng(gps.getLocation().getLatitude(), gps.getLocation().getLongitude());
              //mMap.addMarker(new MarkerOptions().position(user3).title("123"));
              mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user3, 16));

            //勝文
            mCallback.onGpsSelected(gps.getLocation().getLatitude(), gps.getLocation().getLongitude());

            //放marker到地圖上
            for (int i = 0; i < ActMain.iv_activitylist_I_can_see.size(); i++) {

                //確認活動是否在有效時間內
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date dStart = null;
                try {
                    dStart = sdf.parse(ActMain.iv_activitylist_I_can_see.get(i).getCreateTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long limitTime = Integer.parseInt(ActMain.iv_activitylist_I_can_see.get(i).getLimitTime())*60*60*1000;
                long getSystemTime = System.currentTimeMillis();
                long diff = (dStart.getTime()- TimeZone.getDefault().getRawOffset()+limitTime) -getSystemTime;

                //diff>0表示剩下有效時間為正
                if(diff>0){
                    //獲取圖片來源
                    int picTypeIndex = Integer.parseInt(ActMain.iv_activitylist_I_can_see.get(i).getType());
                    int picTypeImgID = ActMain.typelistImg[picTypeIndex-1];
                    Bitmap bm = BitmapFactory.decodeStream(getResources().openRawResource(picTypeImgID));
                    //取得圖片寬高
                    int width = bm.getWidth();
                    int height = bm.getHeight();
                    //設定想要的size
                    int newWidth = 70;
                    int newHeight = 70;
                    //計算縮放比例
                    float scaleWidth = ((float) newWidth) / width;
                    float scaleHeight = ((float) newHeight) / height;
                    //設定縮放matrix參數
                    Matrix matrix = new Matrix();
                    matrix.postScale(scaleWidth, scaleHeight);
                    //建立縮放後的圖片
                    Bitmap newbm = Bitmap.createBitmap(bm, 0, 0,width, height, matrix,true);

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(ActMain.iv_activitylist_I_can_see.get(i).getGpsX()),Double.parseDouble(ActMain.iv_activitylist_I_can_see.get(i).getGpsY())))
                            .title(String.valueOf(ActMain.iv_activitylist_I_can_see.get(i).getId())))
                            .setIcon(BitmapDescriptorFactory.fromBitmap(newbm));
                }

            }

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode){
            case 1234:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    setupMyLocation();
                    setupMarkerInfoWindow();

                }else {

                }
                break;

        }


    }



    private ViewGroup infoWindow;
    private TextView infoActTitle;
    private TextView infoActContent;
    private TextView infoActInitiator;
    private TextView infoTimer;
    private Button infobtnInterest;
    private ImageView infoimgInitiator;
    private OnInfoWindowElemTouchListener infoButtonListener;


}
