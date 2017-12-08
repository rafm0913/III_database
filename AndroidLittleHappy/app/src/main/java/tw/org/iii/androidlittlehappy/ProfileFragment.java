package tw.org.iii.androidlittlehappy;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.ikidou.fragmentBackHandler.BackHandlerHelper;
import com.github.ikidou.fragmentBackHandler.FragmentBackHandler;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends android.support.v4.app.Fragment implements FragmentBackHandler {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.profilefragment, container, false);
        profile_pic = (ImageView)view.findViewById(R.id.profile_pic);
        Log.v("profile","brfore_getMascot");
        profile_pic.setImageResource(images[Integer.parseInt(CPublicParameters.user.getfMascot())]);
        Log.v("profile","after_getMascot");
        nickname = (TextView)view.findViewById(R.id.nickname);
        Log.v("profile","brfore_getfNicName");
        nickname.setText(CPublicParameters.user.getfNickName());
        Log.v("profile","after_getfNicName");
        ratingBar =(RatingBar)view.findViewById(R.id.ratingBar);
        ratingBar.setNumStars(5);
        ratingBar.setStepSize((float) 0.5);
        Log.v("profile","brfore_setRating");
        ratingBar.setRating(Float.parseFloat(CPublicParameters.user.getfStar()));
        ratingBar.setIsIndicator(true);
        Log.v("profile","after_setRating");
        searchRatingBar = (RatingBar)view.findViewById(R.id.SeachratingBar);
        searchRatingBar.setRating((float) 1.0);
        searchRatingBar.setIsIndicator(false);


        defultSearchTime=(TextView)view.findViewById(R.id.text_defultsearchtime);
        seekBar=(SeekBar)view.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(seekBarChange);
        seekBar.setProgress(Integer.valueOf(CPublicParameters.user.getfDefaultTime()));


        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("個人資訊");
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbatText));

        btnLogOut = (Button)view.findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(btnLogOut_click);

        return view;
    }

    private SeekBar.OnSeekBarChangeListener seekBarChange=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
           defultSearchTime.setText(i+"小時");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };


    @Override
    public boolean onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            //外理返回键
            ActMain.navigation.setSelectedItemId(R.id.navigation_home);

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

    private View.OnClickListener btnLogOut_click= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SharedPreferences setting=ProfileFragment.this.getActivity().getSharedPreferences("loginInfo",ProfileFragment.this.getContext().MODE_PRIVATE);
            setting.edit()
                    .clear()
                    .commit();
            CPublicParameters.user = null;
            ProfileFragment.this.getActivity().finish();
            Intent intent= new Intent(ProfileFragment.this.getActivity(),ActAppLogo.class);
            //finish在ActMain前所有的activity，並重建ActAppLogo [1]
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            ProfileFragment.this.getActivity().finish();
        }
    };




    android.support.v4.app.FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    ImageView profile_pic;
    RatingBar ratingBar;
    RatingBar searchRatingBar;
    SeekBar seekBar;
    TextView nickname;
    Toolbar toolbar;
    TextView defultSearchTime;
    Button btnLogOut;

    int[] images = CPublicParameters.images;


}
