// Parfait's Domain..
// There may be functions here that
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class View {
	
	private void sop(Object s) {System.out.print(s);}
	private void sopl(Object s) {System.out.println(s);}
	
	public Scanner kb = new Scanner(System.in);
	
	public View() {}
	
	// Just in case you want to print a message to the user, this will do it
	public void showMessage(String msg) {
		sopl(msg);
	}
	
	// Sample menu, still might add some other values to it
	public int menu() {
		int x;
		sopl("\n\t\tMenu\n");
		sopl("1. View reminders");
		sopl("2. Create a new reminder");
		sopl("3. Exit\n");
		sopl("Make a selection");
		
		try {
			x = kb.nextInt();
		}catch (Exception e) {
			return -1;
		}
		return x;
	}
	
	// Allow the user to make a reminder for a certain date and time.
	// Returns the Alarm object containing the reminder name, date, and time
	// Starting to think that this might be in Model...but idk
	public Alarm makeReminder() {
		String name ="", date = "", time = "";
		LocalDate d = LocalDate.now();
		LocalTime t = LocalTime.now();
		boolean quit = false;
		
		sopl("Enter the name for the alarm: ");
		name = kb.nextLine();
		
		do {
			if(quit)
				break;
			sopl("Enter the date in format YYYY-MM-DD : ");
			date = kb.nextLine();
			try {
				d = LocalDate.parse(date);
				quit = true;
			}catch(Exception e) {
				sopl("invalid input entered. Please try again");
			}
		}while(!quit);
		
		quit = false;
		do {
			if(quit)
				break;
			sopl("Enter the time in format HH:MM (24hr form): ");
			time = kb.nextLine();
			try {
				t = LocalTime.parse(time);
				quit = true;
			}catch (Exception e) {
				sopl("invalid input entered. Please try again");
			}
			
		}while(!quit);
		
		return new Alarm(name,d,t);
	}
	
	// Make an alarm ring
	public void ringNow(Alarm remind) {
		AlarmVisual alarm = new AlarmVisual(remind);
		alarm.setVisible(true);
	}

}
