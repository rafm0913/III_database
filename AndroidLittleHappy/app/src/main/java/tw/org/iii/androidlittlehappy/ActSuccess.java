package tw.org.iii.androidlittlehappy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActSuccess extends AppCompatActivity {
    private View.OnClickListener btnBackToMap_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ActSuccess.this,ActMain.class);
            startActivity(intent);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actsuccess);
        btnBackToMap1 = (Button)findViewById(R.id.btnBackToMap1);
        btnBackToMap1.setOnClickListener(btnBackToMap_Click);
    }
    Button btnBackToMap1;
}
