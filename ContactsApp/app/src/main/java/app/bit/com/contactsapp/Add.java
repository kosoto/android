package app.bit.com.contactsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import static app.bit.com.contactsapp.Main.MEMADDR;
import static app.bit.com.contactsapp.Main.MEMEMAIL;
import static app.bit.com.contactsapp.Main.MEMNAME;
import static app.bit.com.contactsapp.Main.MEMPHONE;
import static app.bit.com.contactsapp.Main.MEMPHOTO;

public class Add extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        final Context ctx = Add.this;

        findViewById(R.id.cancelBtn).setOnClickListener(
                (View v)->{
                    startActivity(new Intent(ctx,List.class));
                }
        );
        ItemCreate query = new ItemCreate(ctx);
        findViewById(R.id.profileBtn).setOnClickListener(
                (View view)->{
                    Toast.makeText(ctx, "이미지 추가 버튼 누름", Toast.LENGTH_SHORT).show();
                    EditText img = new EditText(ctx);
                    new AlertDialog.Builder(ctx)
                        .setTitle("이미지 추가")
                        .setView(img)
                        .setPositiveButton(
                                android.R.string.yes,
                                new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        query.m.photo = img.getText().toString();
                                        ((Button) view).setText(img.getText().toString());
                                    }
                                }
                        ).show();
                    Toast.makeText(ctx, img.getText().toString(), Toast.LENGTH_SHORT).show();
                }
        );
        findViewById(R.id.createBtn).setOnClickListener(
                (View v)->{

                    EditText name = findViewById(R.id.textName);
                    EditText addr = findViewById(R.id.changeAddress);
                    EditText phone = findViewById(R.id.changePhone);
                    EditText email = findViewById(R.id.changeEmail);
                    query.m.name = name.getText().toString();
                    query.m.addr = addr.getText().toString();
                    query.m.phone = phone.getText().toString();
                    query.m.email = email.getText().toString();
                    new Main.StatusService(){
                        @Override
                        public void perform() {
                            query.execute();
                        }
                    }.perform();
                    startActivity(new Intent(ctx,List.class));
                }
        );


    }

    private class CreateQuery extends Main.QueryFactory {
        Main.SQLiteHelper helper;
        public CreateQuery(Context ctx) {
            super(ctx);
            helper = new Main.SQLiteHelper(ctx);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getWritableDatabase();
        }
    }
    private class ItemCreate extends CreateQuery{
        Main.Member m;
        public ItemCreate(Context ctx) {
            super(ctx);
            m = new Main.Member();
        }
        public void execute(){
            String sql =
                    String.format(
                            "INSERT INTO MEMBER " +
                                    "(%s, %s, %s, %s, %s) " +
                                    "VALUES " +
                                    "('%s', '%s', '%s', '%s', '%s') ",
                            MEMNAME,MEMEMAIL,MEMPHONE,MEMADDR,MEMPHOTO,
                            m.name,m.email,m.phone,m.addr,m.photo
                    );
            Log.d("실행할 쿼리",sql);
            getDatabase().execSQL(sql);
        }
    }
}
