package tw.org.iii.androidlittlehappy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ActCheckUserInforPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actcheckuserinforpage);
        Initialcomponent();
        int[] image= CPublicParameters.images;


        fUserName.setText(CPublicParameters.user.getfUserName());
        fUserNickName.setText(CPublicParameters.user.getfNickName());
        fMascot.setImageResource(image[Integer.parseInt(CPublicParameters.user.getfMascot())]);


    }

    private void Initialcomponent() {
        fUserName = (TextView)findViewById(R.id.fUserName);
        fUserNickName = (TextView)findViewById(R.id.fUserNickName);
        fMascot = (ImageView)findViewById(R.id.fMascot);

    }

    TextView fUserName;
    TextView fUserNickName;
    ImageView fMascot;

}
