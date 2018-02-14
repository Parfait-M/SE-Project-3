package src;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

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
			// menu loop
			while(true)
			{
				
				// prompt user
				view.showMessage("\n\tMenu\n"
						+ "1. Create a new reminder\n"
						+ "2. Edit upcoming reminder\n"
						+ "3. View old reminders\n"
						+ "4. Exit");
				
				String choice = (String)view.getInput(DataType.STRING);
				switch (choice)
				{
				case "1": createReminder(); break;
				case "2": editReminder(); break;
				case "3": viewOldReminders(); break;
				case "4": 
					view.showMessage("Goodbye!");
					//Model.saveReminders(); 
					System.exit(0);
				default: view.showMessage("Invalid input");
				}
			}
		}
		
		
		// CreateReminder creates a new reminder based on the user's specifications
		// It adds the reminder to the reminder list and saves the reminder list to a file. 
		private void createReminder()
		{
			String name = "";
			LocalDate d;
			LocalTime t;
			boolean done = false;
			
			// get name or description of reminder
			view.showMessage("\nWhat is the reminder for? Short description: ");
			
			//while()
			name = (String)view.getInput(DataType.STRING);
			
			// get date and time for reminder
			d = reminderDate();
			t = reminderTime();
			
			// add reminder to list
			model.addAlarm(name, d.atTime(t));
			view.showMessage("Reminder successfully created!");
			
			//model.saveReminders();
			
		}
		
				
		// EditReminder allows user to change the date and time of a reminder
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
					view.showMessage("Date and time successfully changed.\n");
					break;
				case "2": 
					model.removeAlarm(model.getAlarm(name)); 
					view.showMessage("Reminder deleted.\n");
					break;
				case "3":
					_return = true;
					break;
				default: view.showMessage("Invalid input");
				}
			}
		}
		
		// Allow user to see which reminders he missed
		// prints a list of old reminders
		private void viewOldReminders()
		{
			ArrayList<Alarm> alarms = model.getPassedAlarms(LocalDateTime.now());
			view.showMessage("\n\n\tOld reminders");
			view.showMessage("date\t\ttime\tdescription");
			for (int i = 0; i < alarms.size(); ++i)
			{
				Alarm alarm = alarms.get(i);
				// change name of month's case
				String month = alarm.getMonthName().substring(0,1) 
						+ alarm.getMonthName().substring(1).toUpperCase();
				
				view.showMessage(month + " " /*+ /*alarm.getDay()*/ + " " + alarm.getYear() 
						+ "\t" + alarm.getTime() + "\t" + alarm.getName());
			}
			view.showMessage("total: " + alarms.size() + "\n");
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
