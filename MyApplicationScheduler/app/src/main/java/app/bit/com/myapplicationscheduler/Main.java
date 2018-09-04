package app.bit.com.myapplicationscheduler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends AppCompatActivity {
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final Context ctx = Main.this;
        TextView today = findViewById(R.id.today);
        today.setText(new SimpleDateFormat("yyyy MM dd kk:mm:ss").format(new Date()));
        CalendarView calender = findViewById(R.id.calender);
        TimePicker time = findViewById(R.id.time);
        TextView year = findViewById(R.id.year);
        TextView month = findViewById(R.id.month);
        TextView day = findViewById(R.id.day);
        TextView hour = findViewById(R.id.hour);
        TextView minute = findViewById(R.id.minute);
        TextView second = findViewById(R.id.second);
        calender.setVisibility(View.VISIBLE);
        time.setVisibility(View.INVISIBLE);
        findViewById(R.id.rdoCalendar).setOnClickListener(
                (View v)->{
                    calender.setVisibility(View.VISIBLE);
                    time.setVisibility(View.INVISIBLE);

                }
        );

        findViewById(R.id.rdoTime).setOnClickListener(
                (View v)->{
                    calender.setVisibility(View.INVISIBLE);
                    time.setVisibility(View.VISIBLE);
                }
        );

        findViewById(R.id.btnEnd).setOnClickListener(
                (View v)->{
                    date = String.format("%s %s %s %s:%s",
                            year.getText().toString(),
                            month.getText().toString(),
                            day.getText().toString(),
                            hour.getText().toString(),
                            minute.getText().toString());
                }
        );
        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                TextView y = findViewById(R.id.year);
                TextView m = findViewById(R.id.month);
                TextView day = findViewById(R.id.day);
                y.setText(year+"");
                m.setText(month+"");
                day.setText(dayOfMonth+"");

            }
        });
        time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(@NonNull TimePicker view, int hourOfDay, int minute) {
                TextView h = findViewById(R.id.hour);
                TextView m = findViewById(R.id.minute);
                h.setText(hourOfDay+"");
                m.setText(minute+"");
            }
        });




    }
}
