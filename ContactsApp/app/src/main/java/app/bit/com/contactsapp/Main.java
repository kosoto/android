package app.bit.com.contactsapp;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.List;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final Context ctx = Main.this;

        findViewById(R.id.moveLogin).setOnClickListener(
                (View v)->{
                    SQLiteHelper helper = new SQLiteHelper(ctx);
                    startActivity(new Intent(ctx,Login.class));
                }
        );
    }

    static class Member{int seq;String name, pw, email, phone, addr, photo;}
    static interface StatusService{public void perform();}
    static interface ListService{public List<?> perform();}
    static interface RetrieveService{public Object perform();}
    static String DBNAME = "kst.db";
    static String MEMTAB = "MEMBER";
    static String MEMSEQ = "SEQ";
    static String MEMNAME = "NAME";
    static String MEMPW = "PW";
    static String MEMEMAIL = "EMAIL";
    static String MEMPHONE = "PHONE";
    static String MEMADDR = "ADDR";
    static String MEMPHOTO = "PHOTH";
    static abstract class QueryFactory{
        Context ctx;
        public QueryFactory(Context ctx) {
            this.ctx = ctx;
        }
        public abstract SQLiteDatabase getDatabase(); //안드 내장 DB
    }
    static class SQLiteHelper extends SQLiteOpenHelper{
        public SQLiteHelper(Context context) {
            super(context, DBNAME, null, 1); //null은 내장된 팩토리를 쓰겠다는 의미
            this.getWritableDatabase();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = String.format(
                    "CREATE TABLE IF NOT EXISTS %s "
                    +"(%s INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +"%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT) ",
                    MEMTAB,
                    MEMSEQ,
                    MEMNAME,MEMPW,MEMEMAIL,MEMPHONE,MEMADDR,MEMPHOTO
            );
            Log.d("실행할 쿼리 :: ",sql);
            db.execSQL(sql);
            Log.d("===========================","create 쿼리실행 완료");
            String[] names = {"kimyoouna","junghyungdon","suae","curry","durant"};
            String[] emails = {"kimyoouna@gmail.com","jung@gmail.com","suae@gmail.com","curry@gmail.com","durant@gmail.com"};
            String[] phones = {"010-2354-3402","010-6545-4562","010-4562-9086","010-1324-3452","010-2323-4562"};
            String[] addrs = {"seoul","paju","ilsan","ohio","texas"};
            for(int i=0;i<5;i++){
                db.execSQL(String.format(
                        "INSERT INTO %s " +
                                "(%s, %s, %s, %s, %s, %s) " +
                                "VALUES " +
                                "('%s', '%s', '%s', '%s', '%s', '%s') ",
                        MEMTAB,
                        MEMNAME,MEMPW,MEMEMAIL,MEMPHONE,MEMADDR,MEMPHOTO,
                        names[i],"1",emails[i],phones[i],addrs[i],"profile_"+(i+1)
                ));
            }
            Log.d("===========================","insert 쿼리실행 완료");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+MEMTAB);
        }
    }

}
