package app.bit.com.googlemap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.btn).setOnClickListener(
                (View v)->{
                    startActivity(
                            new Intent(Main.this,MapsActivity.class)
                    );
                }
        );
    }
}
