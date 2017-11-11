package tw.org.iii.androidlittlehappy;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
// code reference with [2]
public class FragmentIcons extends DialogFragment {

    GridView gridView;
    //設定data base
    int[] images = {
            R.drawable.fish01,
            R.drawable.fish02,
            R.drawable.fish03,
            R.drawable.fish04
    };

    public FragmentIcons() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.actgridviewprofilepic, null);
        gridView = rootView.findViewById(R.id.mygridview);


        //Adapter
        ImageAdapter adapter = new ImageAdapter(getActivity(),images);
        gridView.setAdapter(adapter);

        //getDialog().setTitle("Title");
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Events
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                //確認點到圖片的狀態
                //Toast.makeText(getActivity(),strnum,Toast.LENGTH_SHORT).show();
                //找到activity的Image View，然後換上點選的圖片
                ImageView imgLuckyPic= (ImageView) getActivity().findViewById(R.id.imgLuckyPic);
                imgLuckyPic.setImageResource(images[pos]);
                //找到activity的TextView，然後換上點選的圖片的編號，以便後續儲存到資料庫
                TextView txtLuckyPic = (TextView) getActivity().findViewById(R.id.txtLuckyPic);
                txtLuckyPic.setText(String.valueOf(pos));
                //關閉DialogFragment
                dismiss();

            }
        });


        return rootView;
    }
}

//       這段純粹紀錄用[1]
//    public ActGridViewProfilePic(Context context) {
//
//        super(context);
//        requestWindowFeature(Window.FEATURE_NO_TITLE); // 灭掉对话框标题，要放在setContentView前面否则会报错
//
//        setContentView(R.layout.actgridviewprofilepic);
//
//        setCanceledOnTouchOutside(true);// 点击对话框外部取消对话框显示
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        getWindow().setAttributes(lp);
//        getWindow().addFlags(lp.FLAG_DIM_BEHIND);
//
//        // 设置透明度，对话框透明(包括对话框中的内容)alpha在0.0f到1.0f之间。1.0完全不透明，0.0f完全透明
//        // lp.alpha = 0.5f;
//        lp.dimAmount = 0.1f;// 设置对话框显示时的黑暗度，0.0f和1.0f之间，在我这里设置成0.0f会出现黑屏状态，求解。
//
//        initGrid();
//    }
//


/*
Reference:
[1] http://blog.csdn.net/appleflashstudio/article/details/6177871  (2011)對岸碼農，照打無法顯示window，但節錄這段是滿特別的方式修改視窗標題、透明度...等
[2] https://www.youtube.com/watch?v=V0Vg07dcNnI 2015年，照打即可實踐gridview in dialog，模擬器上看不到title，但實際運作時會有...但沒設定window屬性，可實際做出來已具備預計的效果: 按視窗邊緣可取消視窗、背景灰階、



*/
