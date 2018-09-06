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
        final Context ctx = Detatil.this;
        ItemRetrieve query = new ItemRetrieve(ctx);
        query.id = getIntent().getIntExtra("seq",0);
        Main.Member m = (Main.Member) new Main.RetrieveService(){
            @Override
            public Object perform() {
                return query.excute();
            }
        }.perform();
        Log.d("검색한 이름 ::",m.name);

        TextView name = findViewById(R.id.name);
        TextView email = findViewById(R.id.email);
        TextView phone = findViewById(R.id.phone);
        TextView addr = findViewById(R.id.addr);
        ImageView profile = findViewById(R.id.profile);
        name.setText(m.name);
        email.setText(m.email);
        phone.setText(m.phone);
        addr.setText(m.addr);
        String img = m.photo;
        profile.setImageResource(R.drawable.profile_1);

        findViewById(R.id.detailToList).setOnClickListener(
                (View v)->{
                    startActivity(new Intent(ctx,List.class));
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
