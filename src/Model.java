package src;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Model {

  private final ArrayList<Alarm> alarms = new ArrayList<Alarm>();


  /** Add Alarm
    name: alarm name
    ldt: the time that the alarm with go off
  */
  public void addAlarm(String name, LocalDateTime ldt) {
    alarms.add(new Alarm(name, ldt));
  }

  /** Get Passed Alarms
    ct: current_tiem
    returns: a list of alarms that have gone off
  */
  public ArrayList<Alarm> getPassedAlarms(LocalDateTime ct) {
    ArrayList<Alarm> ans = new ArrayList<Alarm>();

    for (Alarm a : alarms) {
      LocalDateTime at = a.getDate_time();
      if (ct.compareTo(at) >= 0) {
        ans.add(a);
      }
    }

    return ans;
  }


  public static void main(String[] args) {
    Integer a = 1;
    Integer b = 3;
    System.out.println("hello world");
    System.out.println("a > b: " + (a > b));
    System.out.println("a < b: " + (a < b));
    System.out.println("a.compareTo(b): " + (a.compareTo(b)));
    System.out.println("b.compareTo(a): " + (b.compareTo(a)));
    System.out.println("a,b: " + a + "," + b);
  }
}
