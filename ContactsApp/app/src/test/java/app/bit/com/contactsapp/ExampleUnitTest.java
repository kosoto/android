package app.bit.com.contactsapp;

import org.junit.Test;

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




        return answer;
    }
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}