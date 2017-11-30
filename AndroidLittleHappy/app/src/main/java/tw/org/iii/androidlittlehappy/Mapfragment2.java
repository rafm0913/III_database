package tw.org.iii.androidlittlehappy;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class Mapfragment2 extends Fragment implements OnMapReadyCallback {

    OnMapfragment2SelectedListener mCallback;
    //public static String activityTitle = "";




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

        FloatingActionButton ftbNewActivity = (FloatingActionButton)v.findViewById(R.id.ftbNewActivity);
        ftbNewActivity.setOnClickListener(ftbNewActivity_Click);
        FloatingActionButton ftbSearchActivity = (FloatingActionButton)v.findViewById(R.id.ftbSearchActivity);
        ftbSearchActivity.setOnClickListener(ftbSearchActivity_Click);

        return v;
    }

    private View.OnClickListener ftbNewActivity_Click = new View.OnClickListener() {
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
    private View.OnClickListener ftbSearchActivity_Click = new View.OnClickListener() {
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

//            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1234);
            //ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1234);


            return;
        }else {

//            if ("".equals(activityTitle)){
//                setupMyLocation();
//            }else{
//                setupMyLocation(activityTitle);
//                activityTitle = "";
//            }

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

            @Override
            public View getInfoContents(Marker marker) {
                // Setting up the infoWindow with current's marker info
                int activityId = Integer.valueOf(marker.getTitle());

                for (int i = 0; i < ActMain.iv_activitylist_I_can_see.size(); i++) {
                    if (ActMain.iv_activitylist_I_can_see.get(i).getId()==activityId){
                        infoActTitle.setText(ActMain.iv_activitylist_I_can_see.get(i).getTitle().toString());
                        infoActContent.setText(ActMain.iv_activitylist_I_can_see.get(i).getContent().toString());
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

            for (int i = 0; i < ActMain.iv_activitylist_I_can_see.size(); i++) {
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


//    private void setupMyLocation(String activityTitle) {
//        //noinspection MissingPermission
//        mMap.setMyLocationEnabled(true);
//
//        GpsTracker gps= new GpsTracker(getActivity());
//        LatLng user3;
//        if(gps.getLocation()!=null) {
//            user3 = new LatLng(gps.getLocation().getLatitude(), gps.getLocation().getLongitude());
//            mMap.addMarker(new MarkerOptions().position(user3).title(activityTitle));
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user3, 16));
//
//
//        }
//    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode){
            case 1234:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

//                    if ("".equals(activityTitle)){
//                        setupMyLocation();
//                    }else{
//                        setupMyLocation(activityTitle);
//                        activityTitle = "";
//                    }

                    setupMyLocation();
                    setupMarkerInfoWindow();

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



    private ViewGroup infoWindow;
    private TextView infoActTitle;
    private TextView infoActContent;
    private TextView infoActInitiator;
    private Button infobtnInterest;
    private ImageView infoimgInitiator;
    private OnInfoWindowElemTouchListener infoButtonListener;



}
