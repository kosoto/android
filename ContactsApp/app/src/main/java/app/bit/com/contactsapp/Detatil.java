package app.bit.com.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import app.bit.com.contactsapp.util.PhoneUtil;

import static app.bit.com.contactsapp.Main.MEMADDR;
import static app.bit.com.contactsapp.Main.MEMEMAIL;
import static app.bit.com.contactsapp.Main.MEMNAME;
import static app.bit.com.contactsapp.Main.MEMPHONE;
import static app.bit.com.contactsapp.Main.MEMPHOTO;
import static app.bit.com.contactsapp.Main.MEMSEQ;

public class Detatil extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detatil);
        final Context ctx = Detatil.this; //객체에서 속성값(설정값) 만을 담음.
        ItemRetrieve query = new ItemRetrieve(ctx);
        Intent intent = getIntent();
        query.id = intent.getIntExtra("seq",0);
        Main.Member m = (Main.Member) new Main.RetrieveService(){
            @Override
            public Object perform() {
                return query.excute();
            }
        }.perform();
        ImageView profile = findViewById(R.id.profile);
        profile.setImageDrawable(
                getResources()
                        .getDrawable(getResources()
                                .getIdentifier(
                                        getPackageName()
                                                +":drawable/"+m.photo,null,null),ctx.getTheme()));
        TextView name = findViewById(R.id.name);
        name.setText(m.name);
        TextView email = findViewById(R.id.email);
        email.setText(m.email);
        TextView phone = findViewById(R.id.phone);
        phone.setText(m.phone);
        TextView addr = findViewById(R.id.addr);
        addr.setText(m.addr);

        findViewById(R.id.listBtn).setOnClickListener(
                (View v)->{
                    startActivity(new Intent(ctx,List.class));
                }
        );

        findViewById(R.id.updateBtn).setOnClickListener(
                (View v)->{
                    Intent moveUpdate = new Intent(ctx,Update.class);
                    moveUpdate.putExtra("seq",m.seq);
                    startActivity(moveUpdate);
                }
        );
        findViewById(R.id.callBtn).setOnClickListener(
                (View v)->{
                    PhoneUtil util = new PhoneUtil(ctx,this);
                    util.setPhoneNum(phone.getText().toString());
                    util.call();
                }
        );
        findViewById(R.id.dialBtn).setOnClickListener(
                (View v)->{
                    PhoneUtil util = new PhoneUtil(ctx,this); //ctx 위치에 this의 기능을 가진 객체 생성
                    Toast.makeText(ctx, "전화번호 : "+phone.getText().toString(), Toast.LENGTH_LONG).show();
                    util.setPhoneNum(phone.getText().toString());
                    util.dial();
                }
        );
        findViewById(R.id.smsBtn).setOnClickListener(
                (View v)->{

                }
        );
        findViewById(R.id.emailBtn).setOnClickListener(
                (View v)->{

                }
        );
        findViewById(R.id.albumBtn).setOnClickListener(
                (View v)->{

                }
        );
        findViewById(R.id.movieBtn).setOnClickListener(
                (View v)->{

                }
        );
        findViewById(R.id.mapBtn).setOnClickListener(
                (View v)->{

                }
        );
        findViewById(R.id.musicBtn).setOnClickListener(
                (View v)->{

                }
        );

    }

    private class RetrieveQuery extends Main.QueryFactory{
        Main.SQLiteHelper helper;
        public RetrieveQuery(Context ctx) {
            super(ctx);
            helper = new Main.SQLiteHelper(ctx);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }

    private class ItemRetrieve extends RetrieveQuery{
        int id;
        public ItemRetrieve(Context ctx) {
            super(ctx);
        }
        public Main.Member excute(){
            Main.Member m = null;
            Cursor c = getDatabase().rawQuery(String.format("SELECT * FROM MEMBER WHERE %s LIKE '%d'",MEMSEQ, id),null);
            if(c != null){
                while (c.moveToNext()){
                    m = new Main.Member();
                    m.seq = c.getInt(c.getColumnIndex(MEMSEQ));
                    m.name = c.getString(c.getColumnIndex(MEMNAME));
                    m.email = c.getString(c.getColumnIndex(MEMEMAIL));
                    m.phone = c.getString(c.getColumnIndex(MEMPHONE));
                    m.addr = c.getString(c.getColumnIndex(MEMADDR));
                    m.photo = c.getString(c.getColumnIndex(MEMPHOTO));
                }
            }else Log.d("일치하는 아이디가","없습니다");
            return m;
        }
    }
}
