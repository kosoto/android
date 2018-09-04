package app.bit.com.myapplicationscheduler;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final Context ctx = Main.this;
        findViewById(R.id.btnEnd).setOnClickListener(
                (View v)->{
                    Toast.makeText(ctx, "예약버튼 누름", Toast.LENGTH_SHORT).show();
                }
        );
    }
}
