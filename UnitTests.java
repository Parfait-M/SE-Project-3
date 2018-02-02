
import proj.Model;
import proj.View;
import proj.Controller;

public class UnitTests {

  public static void main(String[] argv)
  {
	  System.out.println("Unit Tests");
	  RunAllTests();
  }

  static void RunAllTests()
  {
    TestZero();
    TestOne();
  }

  static void TestZero()
  {
    System.out.print("\n~Unit Test 0: ~\n");

  }
  
//tests generateNonogramPrompt method in the view
 static void TestOne()
 {
	 View view = new View();
	 view.setBoardSize(5);
	  System.out.print("\n~Unit Test 1: ~\n");
	  
	  // test boards and prompts
	  char[][] testBoard1 = {
			  {'_', '_', '_', '_', '_'},
			  {'_', '_', '_', '_', '_'},
			  {'_', '_', '_', '_', '_'},
			  {'_', '_', '_', '_', '_'},
			  {'_', '_', '_', '_', '_'}
	  };
	  int[][] expectedPrompt1 = {
			    {-1, -1, -1, -1, -1},
			    {-1, -1, -1, -1, -1},
			    {-1, -1, -1, -1, -1},
			    {-1, -1, -1, -1, -1},
			    {-1, -1, -1, -1, -1},
			    {-1, -1, -1, -1, -1},
			    {-1, -1, -1, -1, -1},
			    {-1, -1, -1, -1, -1},
			    {-1, -1, -1, -1, -1},
			    {-1, -1, -1, -1, -1},
	  };
	  
	  char[][] testBoard2 = {
		      {'#', '#', '#', '#', '#'},
		      {'_', '_', '_', '#', '_'},
		      {'#', '_', '#', '_', '#'},
		      {'_', '#', '_', '_', '_'},
		      {'#', '#', '#', '#', '#'}
	  };
	  int[][] expectedPrompt2 = {
			    {1, 1, 1, -1, -1},
			    {1, 2, -1, -1, -1},
			    {1, 1, 1, -1, -1},
			    {2, 1, -1, -1, -1},
			    {1, 1, 1, -1, -1},
			    {5, -1, -1, -1, -1},
			    {1, -1, -1, -1, -1},
			    {1, 1, 1, -1, -1},
			    {1, -1, -1, -1, -1},
			    {5, -1, -1, -1, -1},
	  };
	  
	  char[][] testBoard3 = {
		      {'#', '#', '#', '#', '#'},
		      {'#', '#', '#', '#', '#'},
		      {'#', '#', '#', '#', '#'},
		      {'#', '#', '#', '#', '#'},
		      {'#', '#', '#', '#', '#'}
	  };
	  int[][] expectedPrompt3 = {
			    {5, -1, -1, -1, -1},
			    {5, -1, -1, -1, -1},
			    {5, -1, -1, -1, -1},
			    {5, -1, -1, -1, -1},
			    {5, -1, -1, -1, -1},
			    {5, -1, -1, -1, -1},
			    {5, -1, -1, -1, -1},
			    {5, -1, -1, -1, -1},
			    {5, -1, -1, -1, -1},
			    {5, -1, -1, -1, -1},
	  };
	  
	  int[][] testPrompt1 = View.generateNongramPrompt(testBoard1);
	  int[][] testPrompt2 = View.generateNongramPrompt(testBoard2);
	  int[][] testPrompt3 = View.generateNongramPrompt(testBoard3);
	  boolean result1 = TestOneResults(testPrompt1, expectedPrompt1);
	  boolean result2 = TestOneResults(testPrompt2, expectedPrompt2);
	  boolean result3 = TestOneResults(testPrompt3, expectedPrompt3);
	  System.out.print((result1 && result2 && result3)?"pass":"fail");
	  
 }

 static private boolean TestOneResults(int [][] testPrompt, int [][] expectedPrompt)
 {
	 if (testPrompt.length != expectedPrompt.length)
		 return false;
	 
	  boolean pass = true;
	  for (int i = 0; i < 10; ++i)
	  {
		  for (int j = 0; j < 5; ++j)
		  {
			  if (testPrompt[i][j] != expectedPrompt[i][j])
				  return false;
		  }
	  }
	  return true;
 }
}
