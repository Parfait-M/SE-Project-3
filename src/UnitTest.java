
import java.time.*;
import java.util.*;
import java.io.*;

public class UnitTest {

  public static LocalDateTime withM(LocalDateTime t, int m) {
    return t.withMinute((120 + m) % 60).withHour(t.getHour() + m / 60);
  }

  public static void test_model() throws Exception {
    Model m = null;
    try {
      m = new Model();
    }
    catch (FileNotFoundException e) {
      System.err.printf("File Not Found\n");
    }
    catch (IOException e) {
      System.err.printf("IO Exception\n");
    }
    catch (ClassNotFoundException e) {
      System.err.printf("Class Not Found\n");
    }

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

    m.changeTime("Poopy", withM(current_time,min-10));

    for (Alarm a : m.getPassedAlarms(current_time)) {
      System.out.printf("New Passed: %s %d\n", a.getName(), a.getMinute());
    }
  }
  
  public static void test_Visual() {
	  View v = new View();
	  v.showMessageNL("Testing View and AlarmVisual\n");
		Alarm remind = new Alarm();
		String inp;
		try {
			v.showMessageNL("Entering ABCDABCD as name for reminder");
			inp = "ABCDABCD";
			remind.setName(inp);
			v.showMessageNL("Entering 2018-11-02 as date for reminder ");
			inp = "2018-11-02";
			LocalDate date = LocalDate.parse(inp);
			v.showMessageNL("Entering 10:45 as time ");
			inp = "10:45";
			LocalTime lt = LocalTime.parse(inp);
			remind.setDate_time(date, lt);
		}catch(Exception e) {
			
		}
		System.out.println(remind.getName());
		String yes = "Snooze was pressed", no = "Snooze was NOT pressed";
		boolean b = v.ringNow(remind);
		System.out.println(b ? yes : no);
		v.showMessageNL("Adding 5 minutes to time ");
		remind.addMinutes(5);
		b = v.ringNow(remind);
		System.out.println(b ? yes : no);
  }

  public static void main(String[] args) throws Exception {
    test_model();
    test_Visual();
  }
}
