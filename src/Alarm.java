

// Alarm class that holds an alarm's info such as:
// Name, date, time, and sound
// Date and time are stored in a LocalDateTime object
// this makes it easier to validate and do other stuff.

import java.time.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

public class Alarm {
	private String name ="";
	private LocalDateTime date_time = LocalDateTime.now();
	private MediaPlayer track;
	private String [] songs = {"old_bell.mp3"};
	private int counter = 0;

	public Alarm() {
		//Media m = new Media(songs[counter]);
		//track = new MediaPlayer(m);
	}

	public Alarm(String s) {
		this();
		setName(s);
	}

	public Alarm(String s, LocalDateTime ldt) {
		this();
		name = s;
		setDate_time(ldt);
	}

	public Alarm(String s, LocalDate date, LocalTime time) {
		this();
		name = s;
		setDate_time(date,time);

	}

	public Alarm(String s, int year, int month, int day, int hour, int min) {
		this();
		name = s;
		date_time = LocalDateTime.of(year, month, day, hour, min);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getDate_time() {
		return date_time;
	}

	public void setDate_time(LocalDateTime date_time) {
		this.date_time = date_time;
	}

	public void setDate_time(LocalDate date, LocalTime time) {
		this.date_time = LocalDateTime.of(date, time);
	}

	public boolean setDayOfMonth(int day) {
		try {
			date_time = date_time.withDayOfMonth(day);
		}catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean setMonth(int month) {
		try {
			date_time = date_time.withMonth(month);
		}catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean setYear(int year) {
		try {
			date_time = date_time.withYear(year);
		}catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean setHour(int hour) {
		try {
			date_time = date_time.withHour(hour);
		}catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean setMin(int min) {
		try {
			date_time = date_time.withMinute(min);
		}catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean setTime(int hour, int min) {
		try {
			date_time = date_time.withHour(hour);
			date_time = date_time.withMinute(min);
		}catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean setDate_Time(int year, int month, int day, int hour, int min) {
		try{
			date_time = LocalDateTime.of(year, month, day, hour, min);
		}catch (Exception e) {
			return false;
		}
		return true;
	}

	public int getYear() {
		return date_time.getYear();
	}

	public int getMonth() {
		return date_time.getMonthValue();
	}

	public String getMonthName() {
		return "" + date_time.getMonth();
	}

	public int getHour() {
		return date_time.getHour();
	}

	public int getMinute() {
		return date_time.getMinute();
	}

	public String getDate() {
		return (date_time.toLocalDate()).toString();
	}

	public String getTime() {
		return (date_time.toLocalTime()).toString();
	}
}
