package tw.org.iii.androidlittlehappy;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.github.ikidou.fragmentBackHandler.BackHandlerHelper;
import com.github.ikidou.fragmentBackHandler.FragmentBackHandler;

import tw.org.iii.androidlittlehappy.tab.TabHistoryInitiate;
import tw.org.iii.androidlittlehappy.tab.TabHistoryJoin;
import tw.org.iii.androidlittlehappy.tab.TabHistorySeen;

public class ActHistory extends Fragment implements FragmentBackHandler {


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


    @Override
    public boolean onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            //外理返回键
            Mapfragment2 mapfragment = new Mapfragment2();
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.content, mapfragment).commit();

            Log.v("onback","onback(ActHistory)");
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

    android.support.v4.app.FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction fragmentTransaction;


}
