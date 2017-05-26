package kg.nais.controllers;

import javax.faces.bean.ManagedBean;
import java.util.Calendar;

/**
 * Created by b-207 on 5/1/2017.
 */
@ManagedBean
public class TestController {

    private Calendar date = Calendar.getInstance();
    private Calendar bod = Calendar.getInstance();
    private int diff,num1,num2,sum;


    private int age;

    public int getDate() {
        return date.get(Calendar.WEEK_OF_YEAR);
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public int getBod() {
        return bod.get(Calendar.WEEK_OF_YEAR);
    }

    public void setBod(Calendar bod) {
        this.bod = bod;
    }

    public Calendar getCurrentDate(){
        return Calendar.getInstance();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getDiff() {
        return diff;
    }

    public void calculate(){
        bod.add(Calendar.WEEK_OF_YEAR,-age);
        diff = 52 * (date.get(Calendar.YEAR) - bod.get(Calendar.YEAR) ) + (date.get(Calendar.WEEK_OF_YEAR) - bod.get(Calendar.WEEK_OF_YEAR));
    }

    public int getNum1() {
        return num1;
    }

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public int getNum2() {
        return num2;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public void calc(int n1, int n2){
        sum = n1+n2;
    }
}
