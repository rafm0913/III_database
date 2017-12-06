package tw.org.iii.androidlittlehappy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActRefuse extends AppCompatActivity {

    private View.OnClickListener btnBackToMap_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ActRefuse.this,ActMain.class);
            startActivity(intent);
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,ActMain.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actrefuse);
        btnBackToMap = (Button)findViewById(R.id.btnBackToMap);
        btnBackToMap.setOnClickListener(btnBackToMap_Click);



    }

    Button btnBackToMap;
}
