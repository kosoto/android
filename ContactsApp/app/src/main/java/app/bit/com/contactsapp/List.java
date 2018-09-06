package app.bit.com.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static app.bit.com.contactsapp.Main.*;

public class List extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        final Context ctx = List.this;
        ItemList query = new ItemList(ctx);
        ListView memberList = findViewById(R.id.memberList);
        memberList.setAdapter(new MemberAdapter(ctx,new ListService(){
            @Override
            public java.util.List<Main.Member> perform() {
                return  query.execute();
            }
        }.perform()));

        memberList.setOnItemClickListener(
                (AdapterView<?> p, View v, int i, long l)->{
                    Intent intent = new Intent(ctx,Detatil.class);
                    Member m = (Member) memberList.getItemAtPosition(i);
                    intent.putExtra("seq",m.seq);
                    startActivity(intent);
                }
        );

    }
    private class ListQuery extends Main.QueryFactory {
        Main.SQLiteHelper helper;
        public ListQuery(Context ctx) {
            super(ctx);
            helper = new Main.SQLiteHelper(ctx);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }
    private class ItemList extends ListQuery{
        public ItemList(Context ctx) {
            super(ctx);
        }
        public java.util.List<Main.Member> execute(){
            java.util.List<Main.Member> list = new ArrayList<>();
            Main.Member member = null;
            Cursor c = getDatabase().rawQuery("SELECT * FROM MEMBER ",null);
            if(c != null){
                while (c.moveToNext()){
                    member = new Main.Member();
                    member.seq = c.getInt(c.getColumnIndex(MEMSEQ));
                    member.name = c.getString(c.getColumnIndex(MEMNAME));
                    member.email = c.getString(c.getColumnIndex(MEMEMAIL));
                    member.phone = c.getString(c.getColumnIndex(MEMPHONE));
                    member.addr = c.getString(c.getColumnIndex(MEMADDR));
                    member.photo = c.getString(c.getColumnIndex(MEMPHOTO));
                    list.add(member);
                }
                Log.d("등록된 회원수가",list.size()+"");
            }else{
                Log.d("등록된 회원이","없습니다");
            }
            return list;
        }

    }
    private class MemberAdapter extends BaseAdapter{
        java.util.List<Member> list;
        LayoutInflater inflater;

        public MemberAdapter(Context ctx, java.util.List<Member> list) {
            this.list = list;
            this.inflater = LayoutInflater.from(ctx);
        }
        private int[] photos = {
                R.drawable.profile_1,
                R.drawable.profile_2,
                R.drawable.profile_3,
                R.drawable.profile_4,
                R.drawable.profile_5
        };

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View v, ViewGroup g) {
            ViewHolder holder;
            if(v==null){
                v = inflater.inflate(R.layout.member_item,null);
                holder = new ViewHolder();
                holder.profile = v.findViewById(R.id.profile);
                holder.name = v.findViewById(R.id.name);
                holder.phone = v.findViewById(R.id.phone);
                v.setTag(holder);
            }else{
                holder = (ViewHolder) v.getTag();
            }
            holder.profile.setImageResource(photos[i]);
            holder.name.setText(list.get(i).name);
            holder.phone.setText(list.get(i).phone);
            return v;
        }
    }
    static class ViewHolder{
        ImageView profile;
        TextView name, phone;
    }
}

