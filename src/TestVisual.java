import java.time.LocalDate;
import java.time.LocalTime;

public class TestVisual {
	public static void main(String [] arg) {
		View v = new View();
		Alarm remind = new Alarm();
		String inp;
		try {
			v.showMessage("Enter name: ");
			inp = (String)v.getInput(DataType.STRING);
			remind.setName(inp);
			v.showMessage("Enter date: ");
			inp = (String)v.getInput(DataType.STRING);
			LocalDate date = LocalDate.parse(inp);
			v.showMessage("Enter time: ");
			inp = (String)v.getInput(DataType.STRING);
			LocalTime lt = LocalTime.parse(inp);
			remind.setDate_time(date, lt);
		}catch(Exception e) {
			
		}
		String yes = "Snooze was pressed", no = "Snooze was NOT pressed";
		boolean b = v.ringNow(remind);
		System.out.println(b ? yes : no);
		remind.addMinutes(5);
		b = v.ringNow(remind);
		System.out.println(b ? yes : no);
	}
	
}
