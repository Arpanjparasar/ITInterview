package itinterview.arpan.com.itinterview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class AboutUs extends AppCompatActivity {

    ImageView iv_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        iv_back=findViewById(R.id.IV_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AboutUs.this,HomeScreen.class);

                finish();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(AboutUs.this,HomeScreen.class);

        startActivity(intent);
        finish();
        //overridePendingTransition(R.anim.topttobottom,R.anim.bottomtotop);
    }
}
