package tw.org.iii.androidlittlehappy.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tw.org.iii.androidlittlehappy.R;

/**
 * Created by iii on 2017/11/24.
 */

public class TabHistoryJoin extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.acthistory_i_joined,container,false);

        return view;
    }
}
