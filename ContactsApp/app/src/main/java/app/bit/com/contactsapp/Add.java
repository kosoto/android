package app.bit.com.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Add extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        final Context ctx = Add.this;
        findViewById(R.id.addToList).setOnClickListener(
                (View v)->{
                    startActivity(new Intent(ctx,List.class));
                }
        );
    }
}
