


public class ControllerThreads extends Thread
{
	

	public final static int TIME_BETWEEN_CHECKING_REMINDERS = 5000; // 30 seconds
	public static boolean quit = false;
	
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
			while(!quit)
			{
				// menu loop
				int input = view.menu();
				if (input == 3)
				{
					quit = true;
				}
			}
		}
	}
	
	
	
	// Adam
	public static class CheckRemindersThread extends Thread
	{
		
		public void run()
		{
			while(!quit)
			{
				try
				{
					Thread.sleep(TIME_BETWEEN_CHECKING_REMINDERS);
					
					// check reminders from model
					
					//test
						System.out.println("test");
					//
					
					
					
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
