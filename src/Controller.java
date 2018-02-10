

// controller class

public class Controller
{
	
	static View view;

	public static void main()
	{
		
		Thread menu = new Thread();
		Thread alarmCheck = new Thread();
		
		menu.start();
		alarmCheck.start();
		
		
		
	}



	Thread menu = new Thread()
	{
		public void run()
		{
			view.showMessage("hello");
		}
	};
	Thread alarmCheck = new Thread()
	{
		public void run()
		{
			view.showMessage("world");
		}
	};

}