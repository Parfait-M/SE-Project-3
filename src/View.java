import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class View {
	
	private void sop(Object s) {System.out.print(s);}
	private void sopl(Object s) {System.out.println(s);}
	
	public Scanner kb = new Scanner(System.in);
	
	public View() {}
	
	// Function takes in a string and prints it out to the standard console output
	public void showMessage(String msg) {
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
	
	
	// Make an alarm ring
	public boolean ringNow(Alarm remind) {
		AlarmVisual alarm = new AlarmVisual(remind);
		alarm.setVisible(true);
		return alarm.getStatus();
		
		
	}

}
