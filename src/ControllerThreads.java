import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

// ControllerThreads class:
// Contains 2 threads, one for looping through the menu,
// and one for checking if any reminders/alarms will go off.
public class ControllerThreads
{
	
	// ADAM, feel free to change this as needed ...
	public final static int TIME_BETWEEN_CHECKING_REMINDERS = 30000; // 30 seconds
	
	// Zac
	public static class MenuThread extends Thread
	{
		
		public static View view = new View();
		
		public MenuThread() {}
		
		public void run()
		{
			// temporary
			Scanner kb = new Scanner(System.in);
			
			while(true)
			{
				// menu loop
				menu();
				String choice = kb.nextLine();//view.getInput();
				switch (choice)
				{
				case "1": ViewReminders(); break;
				case "2": CreateReminder(); break;
				case "3": /*Model.saveReminders();*/ System.exit(0);
				default: view.showMessage("Invalid input");
				}
			}
		}
		
		// displays list of existing reminders
		public void ViewReminders()
		{
			// get list from model and display to console through the view
			
			// user selects a reminder to edit/delete.
			// selection made by typing name (?) or number...
			
			// edit reminder
		}
		
		// deletes a reminder
		private void DeleteReminder(Alarm al)
		{
			//Model.deleteReminder(al);
			view.showMessage("Reminder deleted.");
		}
		
		// EditReminder allows user to change any aspect of a reminder
		// name, date, time, etc.
		// the user can also delete the reminder.
		private void EditReminder(Alarm al)
		{
			
		}
		
		// CreateReminder creates a new reminder based on the user's specifications
		// It adds the reminder to the reminder list and saves the reminder list to a file. 
		private void CreateReminder()
		{
			String name ="", date = "", time = "";
			LocalDate d = LocalDate.now();
			LocalTime t = LocalTime.now();
			boolean quit = false;
			
			// temporary
			Scanner kb = new Scanner(System.in);
			
			view.showMessage("What is the reminder for? Short description: ");
			name = kb.nextLine();
			
			
			// get date and time for reminder
			do {
				if(quit)
					break;
				view.showMessage("Enter the date in format YYYY-MM-DD: ");
				date = kb.nextLine();
				try {
					d = LocalDate.parse(date);
					quit = true;
				}catch(Exception e) {
					view.showMessage("Invalid format...");
				}
			} while(!quit);
			
			quit = false;
			do {
				if(quit)
					break;
				view.showMessage("Enter the time in format HH:MM (24hr): ");
				time = kb.nextLine();
				try {
					t = LocalTime.parse(time);
					quit = true;
				}catch (Exception e) {
					view.showMessage("Invalid format...");
				}
				
			} while(!quit);
			
			Alarm al = new Alarm(name, d, t);
			view.showMessage("Reminder successfully created!");
			
			// add reminder to list
			//Model.addReminder(al);
			//Model.saveReminders();
			
		}
		
		
		// print menu format
		private void menu() 
		{
			view.showMessage("\n\t\tMenu\n"
					+ "1. View existing reminders\n"
					+ "2. Create a new reminder\n"
					+ "3. Exit\nMake a selection:");
		}
	}
	
	
	
	
	
	// Adam
	public static class CheckRemindersThread extends Thread
	{
		
		public void run()
		{
			while(true)
			{
				

				// check reminders from model
				
				//test
				//System.out.println("test");
				//
				
				
				try
				{
					// wait 30 seconds
					Thread.sleep(TIME_BETWEEN_CHECKING_REMINDERS);
					
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	

	
	public static void main(String[] args)
	{
		new MenuThread().start();
		new CheckRemindersThread().start();
		
	} 



	

}
