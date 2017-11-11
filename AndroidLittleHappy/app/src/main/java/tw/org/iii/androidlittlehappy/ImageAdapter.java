package tw.org.iii.androidlittlehappy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


/**
 * Created by kirisolin on 2017/11/8.
 */

class ImageAdapter extends BaseAdapter{

    private Context context;
    int[] images;

    public ImageAdapter(Context ctx, int[] images)
    {
        this.context = ctx;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int i) {
        return images[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.actgridviewprofilepic_item, null);
        }
        //get view
        ImageView img = (ImageView) convertView.findViewById(R.id.item_image);

        //assign DATA
        img.setImageResource(images[i]);

        return convertView;
    }
}
//ActRegister 用