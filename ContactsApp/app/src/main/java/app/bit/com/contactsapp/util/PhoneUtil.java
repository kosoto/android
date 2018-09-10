package app.bit.com.contactsapp.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

public class PhoneUtil { //PhoneUtil이 ctx,act를 사용
    private Context ctx;
    private Activity act;
    private String phoneNum;

    public PhoneUtil(Context ctx, Activity act) {
        this.ctx = ctx;
        this.act = act;
    }
    public void dial(){
        ctx.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneNum)));
    }
    public void call(){
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(act,
                    new String[]{Manifest.permission.CALL_PHONE},
                    2);
        }else{
            ctx.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNum)));
        }
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
