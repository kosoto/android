package app.bit.com.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Detatil extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detatil);
        final Context ctx = Detatil.this;
        findViewById(R.id.detailToList).setOnClickListener(
                (View v)->{
                    startActivity(new Intent(ctx,List.class));
                }
        );

        findViewById(R.id.detailToUpdate).setOnClickListener(
                (View v)->{
                    startActivity(new Intent(ctx,Update.class));
                }
        );
    }
}
