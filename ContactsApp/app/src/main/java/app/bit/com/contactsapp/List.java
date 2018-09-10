package app.bit.com.contactsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

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
        memberList.setOnItemLongClickListener(
                (AdapterView<?> p, View v, int i, long l)->{
                    Member m = (Member) memberList.getItemAtPosition(i);
                    new AlertDialog.Builder(ctx)
                        .setTitle("DELETE")
                        .setMessage("정말로 삭제할까요?")
                        .setPositiveButton(
                                android.R.string.yes,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(ctx, "삭제 동의 누름", Toast.LENGTH_SHORT).show();
                                        Log.d("====","삭제 동의 누름");
                                        ItemDelete query = new ItemDelete(ctx);
                                        query.id = m.seq;
                                        new StatusService(){
                                            @Override
                                            public void perform() {
                                                query.execute();
                                            }
                                        }.perform();
                                        startActivity(new Intent(ctx,List.class));
                                        Toast.makeText(ctx, "삭제 완료", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        )
                        .setNegativeButton(
                                android.R.string.no,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(ctx, "삭제 취소", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        ).show();
                    return true;
                }
        );
        findViewById(R.id.addBtn).setOnClickListener(
                (View v)->{
                    Toast.makeText(ctx, "add 버튼 누름", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ctx,Add.class));

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
        Context ctx;

        public MemberAdapter(Context ctx, java.util.List<Member> list) {
            this.list = list;
            this.inflater = LayoutInflater.from(ctx);
            this.ctx = ctx;
        }

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
            ItemProfile query = new ItemProfile(ctx);
            query.seq = list.get(i).seq+"";
            holder.profile.setImageDrawable(
                    getResources().getDrawable(
                            getResources().getIdentifier(
                                    ctx.getPackageName()+":drawable/"
                                            + (new RetrieveService() {
                                        @Override
                                        public Object perform() {
                                            return query.execute();
                                        }
                                    }.perform())
                                    , null, null
                            ), ctx.getTheme()
                    )
            );
            holder.name.setText(list.get(i).name);
            holder.phone.setText(list.get(i).phone);
            return v;
        }
    }
    static class ViewHolder{
        ImageView profile;
        TextView name, phone;
    }
    private class profileQuery extends QueryFactory{
        SQLiteHelper helper;
        public profileQuery(Context ctx) {
            super(ctx);
            helper = new SQLiteHelper(ctx);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }
    private class ItemProfile extends profileQuery {
        String seq;
        public ItemProfile(Context ctx) {
            super(ctx);
        }
        public String execute(){
            String res = "";
            Cursor c = getDatabase()
                    .rawQuery(String.format(
                            " SELECT %s FROM %s WHERE %s LIKE '%s' "
                            , MEMPHOTO, MEMTAB, MEMSEQ, seq),null);
            if(c != null){
                if(c.moveToNext()){
                    res = c.getString(c.getColumnIndex(MEMPHOTO));
                }
            }
            return  res;
        }
    }

    private class DeleteQuery extends QueryFactory{
        SQLiteHelper helper;
        public DeleteQuery(Context ctx) {
            super(ctx);
            helper = new SQLiteHelper(ctx);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getWritableDatabase();
        }
    }

    private class ItemDelete extends DeleteQuery{
        int id;
        public ItemDelete(Context ctx) {
            super(ctx);
        }
        public void execute(){
            getDatabase().execSQL(String.format(
                    "DELETE FROM MEMBER WHERE %s LIKE '%s'",MEMSEQ,id
            ));
        }
    }


}

