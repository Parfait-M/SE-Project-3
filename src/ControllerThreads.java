

// ControllerThreads class:
// Contains 2 threads, one for looping through the menu,
// and one for checking if any reminders will go off.
public class ControllerThreads extends Thread
{
	

	public final static int TIME_BETWEEN_CHECKING_REMINDERS = 5000; // 30 seconds
	
	// Zac
	public static class MenuThread extends Thread
	{
		
		public View view;
		public MenuThread()
		{
			this.view = new View();
		}
		
		public void run()
		{
			while(true)
			{
				// menu loop
				int input = view.menu();
				if (input == 3)
				{
					System.exit(0);
				}
			}
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
					System.out.println("test");
				//
				
				
				try
				{
					// wait...
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
