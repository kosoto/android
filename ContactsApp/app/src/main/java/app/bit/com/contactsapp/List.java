package app.bit.com.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class List extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        final Context ctx = List.this;
        findViewById(R.id.listToAdd).setOnClickListener(
                (View v)->{
                    startActivity(new Intent(ctx,Add.class));
                }
        );
        findViewById(R.id.listToDetatil).setOnClickListener(
                (View v)->{
                    startActivity(new Intent(ctx,Detatil.class));
                }
        );
    }
}
