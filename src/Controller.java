
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

// ControllerThreads class:
// Contains 2 threads, one for looping through the menu,
// and one for checking if any reminders/alarms will go off.
public class Controller
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
				view.showMessageNL("\n\tMenu\n"
						+ "1. Create a new reminder\n"
						+ "2. Edit upcoming reminder\n"
						+ "3. View missed reminders\n"
						+ "4. Exit");
				
				String choice = (String)view.getInput(DataType.STRING);
				switch (choice)
				{
				case "1": createReminder(); break;
				case "2": editReminder(); break;
				case "3": displayReminderList(model.getPassedAlarms(LocalDateTime.now()), "missed"); break;
				case "4": 
					view.showMessageNL("Goodbye!");
					//Model.saveReminders(); 
					System.exit(0);
				default: view.showMessageNL("Invalid input");
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
			
			// get name or description of reminder
			view.showMessageNL("\nWhat is the reminder for? Short description: ");
			name = (String)view.getInput(DataType.STRING);
			
			// get date and time for reminder
			d = reminderDate();
			t = reminderTime();
			
			// add reminder to list
			model.addAlarm(name, d.atTime(t));
			view.showMessageNL("Reminder successfully created!");
			
			//model.saveReminders();
			
		}
		
				
		// EditReminder allows user to change the date and time of a reminder
		// the user can also delete the reminder.
		private void editReminder()
		{
			// TODO change to getCurrentAlarms...
			ArrayList<Alarm> alarms = model.getPassedAlarms(LocalDateTime.now());
			
			// print list of upcoming reminders
			displayReminderList(alarms, "upcoming");
			if (alarms.size() < 1)
				return;
			
			String choice = "";
			Integer input = 0;
			
			boolean done = false;
			// get user input
			while(!done)
			{
				view.showMessageNL("Which reminder would you like to edit? (\'quit\' to return to menu)");
				choice = (String)view.getInput(DataType.STRING);
				if (choice.equals("quit"))
					return;
				try
				{
					input = Integer.parseInt(choice);
				} catch (Exception e)
				{
					view.showMessageNL("Invalid input");
					continue;
				}
				if (input > alarms.size() || input < 1)
				{
					view.showMessageNL("Invalid input");
				}
				else done = true;
			}
			
			
			String name = alarms.get(input-1).getName();
			while(true) 
			{
				printAlarm(alarms.get(input-1));
				view.showMessageNL("\nWhat would you like to change?\n"
						+ "1. Change date or time\n"
						+ "2. Delete reminder\n"
						+ "3. Return to list\n"
						+ "4. Return to menu");
				choice = (String)view.getInput(DataType.STRING);
				switch (choice)
				{
				case "1": 
					model.changeDate_Time(name,reminderDate(),reminderTime());
					view.showMessageNL("Date and time successfully changed.\n");
					break;
				case "2":
					view.showMessageNL("\nAre you sure you want to delete this reminder?");
					String s = (String)view.getInput(DataType.STRING);
					if (s.equals("y") || s.equals("Y"))
					{
						model.removeAlarm(model.getAlarm(name)); 
						view.showMessageNL("Reminder deleted.\n");
						editReminder();
						return;
					}
					else break;
				case "3":
					editReminder();
					return;
				case "4":
					return;
				default: view.showMessageNL("Invalid input");
				}
			}
		}
		
		
		// prints a list of reminders
		private void displayReminderList(ArrayList<Alarm> alarms, String type)
		{
			if (alarms.size() < 1)
			{
				view.showMessageNL("\nYou don't have any " + type + " reminders\n");
				return;
			}
				
			view.showMessageNL("\n\n\t" + type + " reminders\n");
			view.showMessageNL("\tdate\t\t\ttime\tdescription");
			for (int i = 0; i < alarms.size(); ++i)
			{
				Alarm alarm = alarms.get(i);
				view.showMessage(i+1 + ".\t");
				printAlarm(alarm);
			}
			view.showMessageNL("\n\ttotal: " + alarms.size() + "\n");
		}
		
		
		// print alarm in nice format
		private void printAlarm(Alarm alarm)
		{
			// correct name of month's case
			String month = alarm.getMonthName().substring(0,1) 
					+ alarm.getMonthName().substring(1).toLowerCase();
			
			view.showMessageNL(month + " " + alarm.getDay() + " " + alarm.getYear() 
					+ "\t" + alarm.getTime() + "\t" + alarm.getName());
		}
		
		
		// Allows user to enter a date.
		// returns the date if it is valid.
		private LocalDate reminderDate()
		{
			String date = "";
			LocalDate d = LocalDate.now();
			boolean done = false;
			while (!done)
			{
				view.showMessageNL("Enter the date in format YYYY-MM-DD: ");
				date = (String)view.getInput(DataType.STRING);
				try 
				{
					d = LocalDate.parse(date);
					done = true;
				} catch(Exception e) 
				{
					view.showMessageNL("Invalid format...");
				}
			}
			
			return d;
		}
		
		// Allows user to enter a time.
		// returns the time if it is valid.
		private LocalTime reminderTime()
		{
			String time = "";
			LocalTime t = LocalTime.now();
			boolean done = false;
			while (!done)
			{
				view.showMessageNL("Enter the time in format HH:MM (24hr): ");
				time = (String)view.getInput(DataType.STRING);
				try {
					t = LocalTime.parse(time);
					done = true;
				} catch (Exception e) 
				{
					view.showMessageNL("Invalid format...");
				}
				
			}
			
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
