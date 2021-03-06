

import java.time.*;
import java.util.*;
import java.io.*;

public class Model {

  private static final String FILE_NAME = "alarms.bin";
  private HashMap<String,Alarm> alarms;

  Model()
  throws IOException, FileNotFoundException, ClassNotFoundException {
    readAlarms();
  }

  public void readAlarms()
  throws IOException, FileNotFoundException, ClassNotFoundException {
    alarms = new HashMap<String,Alarm>();

    try {
      File file = new File(FILE_NAME);

      FileInputStream fis = new FileInputStream(file);
      ObjectInputStream ois = new ObjectInputStream(fis);

      while (ois.readBoolean()) {
        String name = (String)ois.readObject();
        LocalDateTime ldt = (LocalDateTime)ois.readObject();
        alarms.put(name,new Alarm(name,ldt));
      }

      ois.close();
      fis.close();
    }
    catch (FileNotFoundException e) {
      return;
    }
  }

  public void saveAlarms()
  throws IOException, FileNotFoundException {
    File file = new File(FILE_NAME);

    FileOutputStream fos = new FileOutputStream(file);
    ObjectOutputStream oos = new ObjectOutputStream(fos);

    alarms.forEach((n,a)->{
      try {
        oos.writeBoolean(true);
        oos.writeObject(n);
        oos.writeObject(a.getDate_time());
      }
      catch(Exception e){}
    });
    oos.writeBoolean(false);

    oos.close();
    fos.close();
  }

  /** Add Alarm
    name: alarm name
    ldt: the time that the alarm with go off
  */
  public void addAlarm(String name, LocalDateTime ldt)
  throws IOException, FileNotFoundException {
    alarms.put(name,new Alarm(name, ldt));
    saveAlarms();
  }

  /** Add Alarm
    alarm: alarm to add
  */
  public void addAlarm(Alarm alarm)
  throws IOException, FileNotFoundException {
    alarms.put(alarm.getName(),alarm);
    saveAlarms();
  }

  /** Remove Alarm
    name: name of the alarm to remove
  */
  public void removeAlarm(String n)
  throws IOException, FileNotFoundException {
    if (alarms.remove(n) != null)
      saveAlarms();

  }
  /** Remove Alarm
    a: alarm to remove
  */
  public void removeAlarm(Alarm a)
  throws IOException, FileNotFoundException {
    if (alarms.remove(a.getName(),a))
      saveAlarms();
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

  /** Get Upcoming Alarms
    ct: current_time
    returns: a list of alarms that have not gone off yet
  */
  public ArrayList<Alarm> getUpcomingAlarms(LocalDateTime ct) {
    ArrayList<Alarm> ans = new ArrayList<Alarm>();

    alarms.forEach((n,a)->{
      if (ct.compareTo(a.getDate_time()) < 0)
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
  public LocalDateTime getAlarmTime(String n)
  throws IOException, FileNotFoundException {
    Alarm a = alarms.get(n);
    return a == null ? null : a.getDate_time();
  }

  /** Change Time
    n: the name of the alarm to modify the time
    t: the time to change the alarm to
  */
  public void changeTime(String n, LocalDateTime t)
  throws IOException, FileNotFoundException {
    Alarm a = alarms.get(n);
    if (a != null) {
      a.setDate_time(t);
      saveAlarms();
    }
  }

  /** Change Date Time
    n: the name of the alarm to modify the time
    t: the date to change the alarm to
    d: the time to change the alarm to
  */
  public void changeDate_Time(String n, LocalDate d, LocalTime t)
  throws IOException, FileNotFoundException {
    Alarm a = alarms.get(n);
    if (a != null) {
      a.setDate_time(d,t);
      saveAlarms();
    }
  }

  public void changeName(String oldName, String newName)
  throws IOException, FileNotFoundException {
    Alarm a = alarms.get(oldName);
    if (a != null && !oldName.equals(newName)) {
      alarms.remove(oldName);
      a.setName(newName);
      alarms.put(newName,a);
      saveAlarms();
    }
  }

}
