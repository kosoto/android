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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static app.bit.com.contactsapp.Main.MEMADDR;
import static app.bit.com.contactsapp.Main.MEMEMAIL;
import static app.bit.com.contactsapp.Main.MEMNAME;
import static app.bit.com.contactsapp.Main.MEMPHONE;
import static app.bit.com.contactsapp.Main.MEMPHOTO;
import static app.bit.com.contactsapp.Main.MEMSEQ;

public class Update extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);
        final Context ctx = Update.this;
        ItemRetrieve query = new ItemRetrieve(ctx);
        query.id = getIntent().getIntExtra("seq", 0);
        Main.Member m = (Main.Member) new Main.RetrieveService() {
            @Override
            public Object perform() {
                return query.execute();
            }
        }.perform();
        ImageView profile = findViewById(R.id.profile);
        profile.setImageDrawable(getResources().getDrawable(getResources().getIdentifier(getPackageName() + ":drawable/" + m.photo, null, null), getTheme()));
        EditText name = findViewById(R.id.textName);
        name.setText(m.name);
        EditText email = findViewById(R.id.changeEmail);
        email.setText(m.email);
        EditText phone = findViewById(R.id.changePhone);
        phone.setText(m.phone);
        EditText addr = findViewById(R.id.changeAddress);
        addr.setText(m.addr);

        findViewById(R.id.confirmBtn).setOnClickListener(
                (View v) -> {
                    Log.d("====", "확인 버튼 누름");
                    ItemUpdate updateQuery = new ItemUpdate(ctx);
                    updateQuery.id = m.seq;
                    updateQuery.name = (name.getText().toString().equals("")) ? m.name : name.getText().toString();
                    updateQuery.email = (email.getText().toString().equals("")) ? m.email : email.getText().toString();
                    updateQuery.phone = (phone.getText().toString().equals("")) ? m.phone : phone.getText().toString();
                    updateQuery.addr = (addr.getText().toString().equals("")) ? m.addr : addr.getText().toString();
                    new Main.StatusService() {
                        @Override
                        public void perform() {
                            updateQuery.execute();
                        }
                    }.perform();
                    Intent moveDetail = new Intent(ctx, Detatil.class);
                    moveDetail.putExtra("seq", m.seq);
                    startActivity(moveDetail);
                }
        );
        findViewById(R.id.cancelBtn).setOnClickListener(
                (View v) -> {
                    Intent moveDetail = new Intent(ctx, Detatil.class);
                    moveDetail.putExtra("seq", m.seq);
                    startActivity(moveDetail);
                }
        );
    }

    private class RetrieveQuery extends Main.QueryFactory {
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

    private class ItemRetrieve extends RetrieveQuery {
        int id;

        public ItemRetrieve(Context ctx) {
            super(ctx);
        }

        public Main.Member execute() {
            Main.Member m = null;
            Cursor c = getDatabase().rawQuery(
                    String.format(
                            "SELECT * FROM MEMBER WHERE %s LIKE '%s'", MEMSEQ, id),
                    null
            );
            if (c != null) {
                while (c.moveToNext()) {
                    m = new Main.Member();
                    m.seq = c.getInt(c.getColumnIndex(MEMSEQ));
                    m.name = c.getString(c.getColumnIndex(MEMNAME));
                    m.email = c.getString(c.getColumnIndex(MEMEMAIL));
                    m.phone = c.getString(c.getColumnIndex(MEMPHONE));
                    m.addr = c.getString(c.getColumnIndex(MEMADDR));
                    m.photo = c.getString(c.getColumnIndex(MEMPHOTO));
                }
            } else Log.d("일치하는 아이디가", "없습니다");
            return m;
        }
    }

    private class UpdateQuery extends Main.QueryFactory {
        Main.SQLiteHelper helper;

        public UpdateQuery(Context ctx) {
            super(ctx);
            helper = new Main.SQLiteHelper(ctx);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getWritableDatabase();
        }
    }

    private class ItemUpdate extends UpdateQuery {
        int id;
        String name, email, phone, addr;

        public ItemUpdate(Context ctx) {
            super(ctx);
        }

        public void execute() {
            String sql = String.format(
                    " UPDATE MEMBER " +
                            "SET %s = '%s', " +
                            "%s = '%s' ," +
                            "%s = '%s' ," +
                            "%s = '%s' " +
                            "WHERE %s LIKE '%s' ",
                    MEMNAME, name,
                    MEMEMAIL, email,
                    MEMPHONE, phone,
                    MEMADDR, addr,
                    MEMSEQ, id);
            Log.d("실행할 쿼리", sql);
            getDatabase().execSQL(sql);
        }
    }
}
