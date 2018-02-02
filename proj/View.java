package proj;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class View
{

	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static int boardSize;
	private static GameType gameType;
	public View(){}

	public static char[][] sudoBoard= {
		{'1', '2', '3', '4', '5', '6', '7', '8', '9'},
		{'9', '8', '7', '6', '5', '4', '3', '2', '1'},
		{'1', '2', '3', '4', '5', '6', '7', '8', '9'},
		{'9', '8', '7', '6', '5', '4', '3', '2', '1'},
		{'1', '2', '3', '4', '5', '6', '7', '8', '9'},
		{'9', '8', '7', '6', '5', '4', '3', '2', '1'},
		{'1', '2', '3', '4', '5', '6', '7', '8', '9'},
		{'9', '8', '7', '6', '5', '4', '3', '2', '1'},
		{'1', '2', '3', '4', '5', '6', '7', '8', '9'}
	};

	// Zac: boardSize setter
	public static void setBoardSize(int bSz)
	{
		boardSize = bSz;
	}

	public static int getBoardSize()
	{
		return boardSize;
	}

	// Zac: gameType setter
	public static void setGameType(GameType gt)
	{
		gameType = gt;
	}

	// Zac: prompts user and gets user input
	static String getMove()
	{
		// prompt for input
		switch (gameType)
		{
		case NONOGRAM: System.out.println("\n\nfill space :"
				+ " <row><col>\nclear space : -<row><col>\nmark space :"
				+ " x<row><col>\nExamples: 1a, -4b, x2c\ntype 'quit' to return to menu"); break;
		case SUDOKU: System.out.println("sudoku input instructions:"
				+ "\nmark space: <row><col><number>\nclear space : -<row><col>" 
				+ "\nExamples: 1a9, 4b4, -7c\ntype 'quit' to return to menu"); break;
		default: break;
		}
		// get input
	    try
	    {
	      return in.readLine();
	    } catch (Exception e)
	    {
	      System.out.print("Input error");
	      return "Error";
	    }
	}

	// Zac: Calls appropriate display function for the chosen game
	static void displayBoard(char[][] board, char[][] solution)
	{
		switch (gameType)
		{
		case NONOGRAM: displayNonogramBoard(board,solution); break;
		case SUDOKU: displaySudokuBoard(board); break;
		default:break;
		}
	}

	// Adam
	// Works for any sized Sudoku board
	static void displaySudokuBoard(char[][] board)
	{
		int asciiA = 65;
	  System.out.print("\n* * * * * * * * * * * * *");

	  for(int i = 0; i < board.length; i++)
	  {
	    System.out.print("\n* ");

	    for(int j = 0; j < board[i].length; j++)
	    {
	      System.out.print(board[i][j] + " ");

	      if((j + 1) % 3 == 0 && (j + 1) != board[i].length)
	      {
	        System.out.print("| ");
	      }
	    }
	    System.out.print("* " + (char)(asciiA + i));

	    if((i + 1) % 3 == 0 && (i + 1) != board.length)
	    {
	      System.out.print("\n* ------|-------|------ *");
	    }
	  }
	  System.out.print("\n* * * * * * * * * * * * *\n  ");

	  for(int i = 1; i <= board.length; i++)
	  {
	    System.out.print(i + " ");

	    if(i % 3 == 0 && i != board.length)
	    {
	      System.out.print("  ");
	    }
	  }
	  System.out.print("\n");

	}


	// Adam
	// Works for any size
	static void displayNonogramBoard(char[][] board, char[][] solution)
	{

		int rLen = board.length;
		int asciiA = 65;

		int[][] numbers = generateNongramPrompt(solution);

		// To print colummn coordinates
		char rowCords[] = new char[] {'A', 'B', 'C', 'D', 'E'};

		int r = rLen/2; // Default maximum number of rows

		boolean foundNonnegative = false;
		// Finds the maximum number of rows to avoid prining extra blank spaces
		for(int i = rLen/2; i > -1; i--)
		{
			for(int j = 0; j < rLen; j++)
			{
				if(numbers[j][i] != -1)
				{
					foundNonnegative = true;
				}
			}
			if(!foundNonnegative)
			{
				r--;
			}
			foundNonnegative = false;
		}

		// For looks, I swap the non -1 numbers so that there are only blanks on top of numbers
		for(int i = 0; i < rLen; i++)
		{
			int j = rLen/2;
			int k = 0;
			boolean swapped = false;
			while(!swapped && j > 0)
			{
				if(numbers[i][j] != -1)
				{
					int temp = numbers[i][k];
					numbers[i][k] = numbers[i][j];
					numbers[i][j] = temp;
					swapped = true;
					k++;
				}
				j--;

				if(k == ((rLen/4) + 1))
				{
					break;
				}
			}
		}

		// Prints Column numbers in column major order
		// Start at max row to avoid creating an extra line of all blanks
		for(int i = r; i > -1; i--) {
			System.out.print("\n       ");
			int j = 0;
			while(j < rLen) {

				// If there is a -1, print a space to ensure alignment
				if(numbers[j][i] == -1) {
					System.out.print("  ");

				}

				else {
					// Print in column major order
					System.out.print(numbers[j][i] + " ");
				}
				// Next Column
				j++;
			}
		}
		System.out.print("\n");

		// Print Row nums and board
		for(int i = 0; i < board[0].length; i++) {

			String row = ""; // To hold row numbers as a delimited string
			for(int a = 0; a < rLen; a++) {
				// Use i for rows, but we need to add 5 to access the row numbers
				if(numbers[i + board.length][a] == -1) {
					// Stop loop to print row
					a = rLen;
				}

				else {
					// Generates a string literal for the row numbers for print formatting purposes
					row += (Integer.toString(numbers[i + board.length][a]) + " ");
				}
			}

			// Print row numbers for row i
			System.out.printf("%6s ", row);

			// Print board row i
			for(int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j] + " ");
			}

			// Print board col coordinate for row i
			System.out.print((char)(asciiA + i) + "\n");
		}

		// Print Col Cords
		System.out.print("       ");
		for(int i = 1; i <= board.length; i++) {
			System.out.print(i + " ");
		}
		System.out.print("\n");
	}

	// Zac
	// generates prompt for Nonogram game
	// receives any size board
	// returns a returns a size*2 x size array with the generated prompt
	public static int[][] generateNongramPrompt(char[][] board)
	{
		  int colCounter = 0;	// counters for consecutive #'s
		  int rowCounter = 0;
		  int[][] prompt;	// array to store prompts
		  int promptI = 0;	// row index for prompt
		  int promptJ1 = 0;	// col indexes for prompt
		  int promptJ2 = 0;

		  // initialize prompt
		  prompt = new int[boardSize*2][];
		  for (int i = 0; i < boardSize*2; ++i) {
			  prompt[i] = new int[boardSize];
			  // initialize all elements to -1
			  for (int j = 0; j < boardSize; ++j) {
				  prompt[i][j] = -1;
			  }
		  }

		  // generate prompt
		  for (int i = 0; i < boardSize; ++i) {
			  for (int j = 0; j < boardSize; ++j) {

				  // check columns
				  if (board[j][i] == '#') {
					  colCounter++;
				  }
				  if ((board[j][i] == '_' || j == boardSize-1) && colCounter != 0) {
					  prompt[promptI][promptJ1] = colCounter;
					  promptJ1++;
					  colCounter = 0;
				  }

				  // check rows
				  if (board[i][j] == '#') {
					  rowCounter++;
				  }
				  if ((board[i][j] == '_' || j == boardSize-1) && rowCounter != 0) {
					  prompt[promptI+boardSize][promptJ2] = rowCounter;
					  promptJ2++;
					  rowCounter = 0;
				  }
			  }
			  promptI++;
			  promptJ1 = promptJ2 = 0;
		  }

		  return prompt;
	}

}
