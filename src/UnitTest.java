
import java.time.*;
import java.util.*;


import java.io.*;

public class UnitTest {

  public static LocalDateTime withM(LocalDateTime t, int m) {
    return t.withMinute(m % 60).withHour(t.getHour() + m / 60);
  }

  public static void test_model() {
    Model m = new Model();
    LocalDateTime current_time = LocalDateTime.now();
    int min = current_time.getMinute();

    m.addAlarm("Poopy", withM(current_time, min+10));
    m.addAlarm("Plopy", withM(current_time, min-2));
    m.addAlarm("Plumy", withM(current_time, min-3));
    m.addAlarm("Pluky", withM(current_time, min+3));


    System.out.printf("Ctime %d\n",current_time.getMinute());
    System.out.printf("Poopy %d\n",m.getAlarmTime("Poopy").getMinute());
    System.out.printf("Plopy %d\n",m.getAlarmTime("Plopy").getMinute());
    System.out.printf("Plumy %d\n",m.getAlarmTime("Plumy").getMinute());

    for (Alarm a : m.getPassedAlarms(current_time)) {
      System.out.printf("Passed: %s %d\n", a.getName(), a.getMinute());
    }

    m.changeTime("Poopy", current_time.withMinute(min - 10));

    for (Alarm a : m.getPassedAlarms(current_time)) {
      System.out.printf("New Passed: %s %d\n", a.getName(), a.getMinute());
    }
  }

  public static void main(String[] args) {
    test_model();

    // try {
    //   File file = new File("text.txt");
    //
    //   FileOutputStream
    // }
    // catch (IOException e) {
    //   System.err.printf("ERROR: %s\n", e);
    // }
  }
}
