package app.bit.com.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Update extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);
        final Context ctx = Update.this;
        findViewById(R.id.updateToDetatil).setOnClickListener(
                (View v)->{
                    startActivity(new Intent(ctx,Detatil.class));
                }
        );

    }
}
