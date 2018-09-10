package app.bit.com.contactsapp;

import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    public String solution(int n, int t, int m, int p) {
        String answer = "";
        String temp = "";
        for(int i=0;true;i++){
            int j=i;
            while (j>=i&&j!=0){
                int quotion = j/n;
                int remainder = j%n;
                j = quotion;
                temp = String.valueOf(remainder) + temp;
                System.out.println(quotion);
                System.out.println(remainder);

            }
            temp += String.valueOf(j);
            System.out.println(temp);


            //t개의 숫자를 구하면 종료
            break;
        }

        return answer;
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        int n = 2;  //진법
        int t = 1;  //미리 구할 숫자의 갯수
        int m = 5;  //게임에 참가하는 인원
        int p = 0;  //내 순서
        solution(n,t,m,p);
    }
}