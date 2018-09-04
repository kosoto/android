package app.bit.com.myapplicationbmi;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BMI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi);
        final Context ctx = BMI.this;
        EditText h = findViewById(R.id.height);
        EditText w = findViewById(R.id.weight);
        class Calc{
            Double h,w;
            String result;
            public String exe(){
                Double bmi = w/(Math.pow(h/100,2));
                if (bmi < 18.5) {
                    result = "저체중";
                } else if (bmi < 23) {
                    result = "정상";
                } else if (bmi < 25) {
                    result = "비만 전단계";
                } else if (bmi < 30) {
                    result = "1단계 비만";
                } else if (bmi < 35) {
                    result = "2단계 비만";
                } else {
                    result = "3단계 비만";
                }
                return result;
            }
        }
        findViewById(R.id.btn).setOnClickListener(
                (View v)->{
                    Double weight = Double.parseDouble(w.getText().toString());
                    Double height = Double.parseDouble(h.getText().toString());
                    Calc c = new Calc();
                    c.h = height;
                    c.w = weight;
                    TextView res = findViewById(R.id.res);
                    res.setText(c.exe());



                }
        );
    }
}
/*
new View.OnClickListener() {
@Override
public void onClick(View v) {
        EditText h = findViewById(R.id.height);
        EditText w = findViewById(R.id.weight);
        Double bmi = (Double.parseDouble(w.getText().toString())) / Math.pow((Double.parseDouble(h.getText().toString()))/100,2);
        String result;
        if (bmi < 18.5) {
        result = "저체중";
        } else if (bmi < 23) {
        result = "정상";
        } else if (bmi < 25) {
        result = "비만 전단계";
        } else if (bmi < 30) {
        result = "1단계 비만";
        } else if (bmi < 35) {
        result = "2단계 비만";
        } else {
        result = "3단계 비만";
        }
        TextView res = findViewById(R.id.res);
        res.setText(result);

        }
        }*/
