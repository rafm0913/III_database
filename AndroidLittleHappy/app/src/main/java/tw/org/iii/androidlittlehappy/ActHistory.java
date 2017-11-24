package tw.org.iii.androidlittlehappy;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class ActHistory extends Fragment {


    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.acthistory, container, false);

        mSectionsPageAdapter = new SectionsPageAdapter(getChildFragmentManager());

        mViewPager = (ViewPager) view.findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        return view;


    }




    private void setupViewPager (ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getChildFragmentManager());
        adapter.addFragment(new TabHistoryInitiate(), "我發起的活動");
        adapter.addFragment(new TabHistoryJoin(), "參加的活動");
        adapter.addFragment(new TabHistorySeen(), "看過的活動");
        viewPager.setAdapter(adapter);
    }


}
