package tw.org.iii.androidlittlehappy;

/**
 * Created by iii on 2017/11/7.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    Context context;
    int typlistImg[];
    String[] typelistString;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, int[] typlistImg, String[] typelistString) {
        this.context = applicationContext;
        this.typlistImg = typlistImg;
        this.typelistString = typelistString;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return typlistImg.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);
        icon.setImageResource(typlistImg[i]);
        names.setText(typelistString[i]);
        return view;
    }



}