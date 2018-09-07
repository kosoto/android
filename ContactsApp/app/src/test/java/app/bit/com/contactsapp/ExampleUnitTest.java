package app.bit.com.contactsapp;

import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    public String solution(int n, int t, int m, String[] timetable) {
        String answer = "";
        int busHour = 9,busMin = 0;
        int crew = timetable.length;
        int leftCrew = 0;
        if(n>1){
            for(int i=0;i<=n-2;i++){ //처음버스부터 마지막 2번째 전 버스까지
                for(int j=leftCrew;j-leftCrew<m&&j<crew;){
                    if(Integer.parseInt(timetable[j].split("\\:")[0])<=busHour
                       &&Integer.parseInt(timetable[j].split("\\:")[1])<=busMin){
                        j++;
                        leftCrew++;
                    }
                }
                // 다음 버스 시간 구하기
                busMin += t;
                if(busMin>=60){
                    busMin -= 60;
                    busHour += 1;
                }
            }
        }
        //마지막 버스
        for(int j=leftCrew;j-leftCrew<m-1&&j<crew;){
            if(Integer.parseInt(timetable[j].split("\\:")[0])<=busHour
                    &&Integer.parseInt(timetable[j].split("\\:")[1])<=busMin){
                j++;
                leftCrew++;
            }
        }
        String a = timetable[leftCrew];
        String b = timetable[leftCrew+1];
        int[] time =
                {Integer.parseInt(b.split(":")[0]),
                Integer.parseInt(b.split(":")[1])};
        answer = (time[1]==0)?String.format("%02d:59",time[0]-1):String.format("%02d:%02d",time[0],time[1]-1);
        return answer;
    }

    public String solution2(int n, int t, int m, String[] timetable) {
        String answer = "";

        ArrayList<String> list = new ArrayList<>();
        for(int i=0;i<timetable.length;i++){
            list.add(timetable[i]);
        }
        ArrayList<int[]> list2 = new ArrayList<>();
        for(int i=0;i<timetable.length;i++){
            String[] temp1 = timetable[i].split(":");
            int[] temp2 = {Integer.parseInt(temp1[0]),Integer.parseInt(temp1[1])};
            list2.add(temp2);
        }

        int busHour = 9,busMin = 0;



        for(int i=0;i<list.size();i++){
            System.out.print(list.get(i));
        }
        return answer;
    }
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        int n = 1; //셔틀 운행 횟수
        int t = 1; //셔틀 운행 간격
        int m = 5; //한 셔틀에 탈 수 있는 최대 크루 수
        String[] timetable = {"08:00", "08:01", "08:02", "08:03"};
        solution2(n,t,m,timetable);
    }
}