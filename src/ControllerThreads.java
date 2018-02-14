package src;

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
	public static View view = new View();
	public static Model model = new Model();
	
	// Zachary
	public static class MenuThread extends Thread
	{
		public MenuThread() {}
		
		public void run()
		{
			
			while(true)
			{
				// menu loop
				// prompt user
				view.showMessage("\n\t\tMenu\n"
						+ "1. Edit existing reminder\n"
						+ "2. Create a new reminder\n"
						+ "3. Exit");
				
				String choice = (String)view.getInput(DataType.STRING);
				switch (choice)
				{
				case "1": editReminder(); break;
				case "2": createReminder(); break;
				case "3": /*Model.saveReminders();*/ System.exit(0);
				default: view.showMessage("Invalid input");
				}
			}
		}
		
		// EditReminder allows user to change any aspect of a reminder
		// name, date, time, etc.
		// the user can also delete the reminder.
		private void editReminder()
		{
			// get list from model and display to console through the view
			String name = "";
			// user selects a reminder to edit/delete.
			// selection made by typing name (?) or number...
			
			// edit reminder
			boolean _return = false;
			while(!_return) 
			{
				view.showMessage("What would you like to change?\n"
						+ "1. Change date or time\n"
						+ "2. Delete reminder\n"
						+ "3. Return to main menu");
				String choice = (String)view.getInput(DataType.STRING);
				switch (choice)
				{
				case "1": 
					model.changeDate_Time(name,reminderDate(),reminderTime());
					view.showMessage("Date and time successfully changed.");
					break;
				case "2": 
					model.removeAlarm(model.getAlarm(name)); 
					view.showMessage("Reminder deleted.");
					break;
				case "3":
					_return = true;
					break;
				default: view.showMessage("Invalid input");
				}
			}
		}
		
		
		// Allows user to enter a date.
		// returns the date if it is valid.
		private LocalDate reminderDate()
		{
			String date = "";
			LocalDate d = LocalDate.now();
			boolean quit = false;
			do {
				if(quit)
					break;
				view.showMessage("Enter the date in format YYYY-MM-DD: ");
				date = (String)view.getInput(DataType.STRING);
				try {
					d = LocalDate.parse(date);
					quit = true;
				}catch(Exception e) {
					view.showMessage("Invalid format...");
				}
			} while(!quit);
			
			return d;
		}
		
		// Allows user to enter a time.
		// returns the time if it is valid.
		private LocalTime reminderTime()
		{
			String time = "";
			LocalTime t = LocalTime.now();
			boolean quit = false;
			do {
				if(quit)
					break;
				view.showMessage("Enter the time in format HH:MM (24hr): ");
				time = (String)view.getInput(DataType.STRING);
				try {
					t = LocalTime.parse(time);
					quit = true;
				}catch (Exception e) {
					view.showMessage("Invalid format...");
				}
				
			} while(!quit);
			
			return t;
		}
		
		// CreateReminder creates a new reminder based on the user's specifications
		// It adds the reminder to the reminder list and saves the reminder list to a file. 
		private void createReminder()
		{
			String name ="";
			LocalDate d;
			LocalTime t;
			
			// get name or description of reminder
			view.showMessage("What is the reminder for? Short description: ");
			name = (String)view.getInput(DataType.STRING);
			
			// get date and time for reminder
			d = reminderDate();
			t = reminderTime();
			
			Alarm al = new Alarm(name, d, t);
			view.showMessage("Reminder successfully created!");
			
			// add reminder to list
			//Model.addReminder(al);
			//Model.saveReminders();
			
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
