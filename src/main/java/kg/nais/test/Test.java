package kg.nais.test;

import kg.nais.controllers.OrdersController;
import kg.nais.models.Chick;
import kg.nais.tools.BasicOperations;

import java.util.Calendar;

/**
 * Created by aziret on 7/19/17.
 */
public class Test {
    public static void main(String[] args) {
        Calendar c1 = Calendar.getInstance();
        try {
            Thread.sleep(500);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        Calendar c2 = Calendar.getInstance();

        System.out.println(BasicOperations.isSameDate(c1,c2));
//        c1.add(Calendar.DAY_OF_YEAR,-10);
//
//        System.out.printf("c1: %d\nc2: %d",c1.get(Calendar.DAY_OF_YEAR),c2.get(Calendar.DAY_OF_YEAR));


    }
}
