package src;

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
	public void showMessageNL(String msg) {
		sopl(msg);
	}

	// Just in case you want to print a message to the user, this will do it
	public void showMessage(String msg) {
		sop(msg);
	}

	public Object getInput(DataType type) {
		switch(type) {
		case INT:
			return kb.nextInt();
		case STRING:
			return kb.next();
		case FLOAT:
			return kb.nextFloat();
		case LINE:
			return kb.nextLine();
		default:
			return kb.next();
		}

	}


	// Make an alarm ring
	public void ringNow(Alarm remind) {
		AlarmVisual alarm = new AlarmVisual(remind);
		alarm.setVisible(true);
	}

}
