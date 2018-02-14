
import java.time.*;
import java.util.*;

public class Model {

  private final HashMap<String,Alarm> alarms = new HashMap<String,Alarm>();

  /** Add Alarm
    name: alarm name
    ldt: the time that the alarm with go off
  */
  public void addAlarm(String name, LocalDateTime ldt) {
    alarms.put(name,new Alarm(name, ldt));
  }

  /** Add Alarm
    alarm: alarm to add
  */
  public void addAlarm(Alarm alarm) {
    alarms.put(alarm.getName(),alarm);
  }

  /** Remove Alarm
    name: name of the alarm to remove
  */
  public Alarm removeAlarm(String n) {
    return alarms.remove(n);
  }
  /** Remove Alarm
    a: alarm to remove
  */
  public boolean removeAlarm(Alarm a) {
    return alarms.remove(a.getName(),a);
  }

  /** Get Passed Alarms
    ct: current_time
    returns: a list of alarms that have gone off
  */
  public ArrayList<Alarm> getPassedAlarms(LocalDateTime ct) {
    ArrayList<Alarm> ans = new ArrayList<Alarm>();

    alarms.forEach((n,a)->{
      if (ct.compareTo(a.getDate_time()) >= 0)
        ans.add(a);
    });

    return ans;
  }

  /** Get Alarm
    n: alarm name
    returns:
      Alarm of that name
      null if that alarm does not exist
  */
  public Alarm getAlarm(String n) {
    return alarms.get(n);
  }

  /** Get Alarm Time
    n: the name of the alarm to get the time from
    returns:
      the time of the requested alarm
      null if that name does not exist
  */
  public LocalDateTime getAlarmTime(String n) {
    Alarm a = alarms.get(n);
    return a == null ? null : a.getDate_time();
  }

  /** Change Time
    n: the name of the alarm to modify the time
    t: the time to change the alarm to
  */
  public void changeTime(String n, LocalDateTime t) {
    Alarm a = alarms.get(n);
    if (a != null) a.setDate_time(t);
  }

  /** Change Date Time
    n: the name of the alarm to modify the time
    t: the date to change the alarm to
    d: the time to change the alarm to
  */
  public void changeDate_Time(String n, LocalDate d, LocalTime t) {
    Alarm a = alarms.get(n);
    if (a != null) a.setDate_time(d,t);
  }



}
