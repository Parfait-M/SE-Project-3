// The View class is in charge of getting input from the user,
// as well as displaying output. This includes displaying the 
// alarm/reminder to the user.

import java.util.Scanner;

public class View {
	
	private void sop(Object s) {System.out.print(s);}
	private void sopl(Object s) {System.out.println(s);}
	
	public Scanner kb = new Scanner(System.in);
	
	public View() {}
	
	// Function takes in a string and prints it out to the standard console output
	public void showMessage(String msg) {
		sop(msg);
	}
	// Function displays a string to the standard output, as well as a new line character
	public void showMessageNL(String msg) {
		sopl(msg);
	}
	
	// Function takes in a DataType enum obj, and returns an object of one of the
	// supported types
	public Object getInput(DataType type) {
		switch(type) {
		case INT:
			return kb.nextInt();
		case STRING:
			return kb.nextLine();	
		case FLOAT:
			return kb.nextFloat();
		default:
			return kb.next();
		}
		
	}
	
	
	// Function takes in an alarm object, and displays it to the user. 
	// it returns a boolean variable indicating whether the snooze button
	// was pressed by the user or not.
	public boolean ringNow(Alarm remind) {
		AlarmVisual alarm = new AlarmVisual(remind);
		alarm.setVisible(true);
		while(alarm.isVisible()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return alarm.getStatus();		
	}

}
