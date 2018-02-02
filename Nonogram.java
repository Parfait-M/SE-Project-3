/*
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Nonogram {
	
  // intputs:
    // run:  run_game()
	// z_board: use default z puzzle
	// a_board: use default a puzzle
    // default: run_game()
  public static void main(String[] argv) {
    if (argv.length == 0) {
      run_game();
    }
    else switch (argv[0]) {
    case "print":
      UnitTest();
      break;
    case "z_board":
    	fname = "Z_puzzle.txt";
    	run_game();
    	break;
    case "a_board":
    	fname = "A_puzzle.txt";
    	run_game();
    	break;
    case "run":
    default:
    	fname = argv[0];
      run_game();
    }
  }

  // Didn't want to make changes to John's function, so made this variable
    // can delete this if we add a parameter to run_game();
  static String fname = null;

  //array of different boards we have that user can use to play
  static String [] boards = {"Z_puzzle.txt","A_puzzle.txt"};
  static int board_counter = 0;
  
  public static char[][] static_board = {
      {'#', '#', '#', '#', '#'},
      {'_', '_', '_', '#', '_'},
      {'_', '_', '#', '_', '_'},
      {'_', '#', '_', '_', '_'},
      {'#', '#', '#', '#', '#'}
  };
  static int static_board_prompt[][] = {
    // Col prompt
    {1, 1, -1, -1, -1},
    {2, -1, -1, -1, -1},
    {3, -1, -1, -1, -1},
    {2, -1, -1, -1, -1},
    {1, 3, -1, -1, -1},

    // Row prompt
    {4, -1, -1, -1, -1},
    {1, -1, -1, -1, -1},
    {1, -1, -1, -1, -1},
    {1, -1, -1, -1, -1},
    {4, -1, -1, -1, -1},
  };

  // I/O -----------------------------------------------------------------------

  // calls System.out.println
  static void log(Object s) {
    System.out.println(s);
  }

  // calls System.out.printf
  static void logf(String s, Object...o) {
    System.out.printf(s, o);
  }

  // calls System.err.println
  static void err(Object s) {
    System.err.println(s);
  }

  // console input
  static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
  static String cin() {
    try {
      return in.readLine();
    } catch (Exception e) {
      err(e);
      return "Error";
    }
  }

  // Parfait -------------------------------------------------------------------
  // try to open a file that might have been passed through argv[0]. If file
  // is opened, create a 5x5 board from that file and store it in static_board.
  // if fname variable contains null (no file given) or file cannot be opened,
  // utilize pre-made game board.
  // returns a 5x5 board
  static char[][] get_board() {
	  
	  char [][] board = new char[5][5];
	  //if input file was provided, use it. Else use a default board
	  String b = fname == null ? boards[board_counter%boards.length] : fname;
	  //user provided file only gets to be used once
	  fname = null;
	  board_counter++;
	  try{
		  BufferedReader br = new BufferedReader(new FileReader(b));
		  String line;
		  int i = 0;
		  while((line = br.readLine()) != null) {
			  for(int j = 0; j < board[i].length; j++) {
				  board[i][j] = line.charAt(j);
			  }
			  i++;
		  }
		  br.close();
		  return board;
	  }
	  catch (IOException ex){
			  //don't actually need to do anything here...
			  System.out.println("Error opening file, using default board");
		  }

    return static_board; // just in case we were not able to load the file, use a default one
  }

  // Zac -----------------------------------------------------------------------
  // receives a 5x5 board
  // returns a 10x5 array (2 5x5 arrays)
  static int[][] get_prompt(char[][] board) {

	  int boardSize = 5;
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
				  prompt[promptI+5][promptJ2] = rowCounter;
				  promptJ2++;
				  rowCounter = 0;
			  }

		  }

		  promptI++;
		  promptJ1 = promptJ2 = 0;
	  }

	  return prompt;
  }

  // John ----------------------------------------------------------------------

  // compare two boards
  static boolean board_equal(char[][] b1, char[][] b2) {
    for (int j = 0; j < 5; ++j) {
      for (int i = 0; i < 5; ++i) {
        if ((b1[j][i] == '#') != (b2[j][i] == '#')) {
          return false;
        }
      }
    }
    return true;
  }

  // run game loop until player quits
  static void run_game() {
    log("Running game...\n\n");

    log("WELCOME TO");
    log("ADAM, JOHN, PARFAIT, AND ZAC'S");
    log("NONAGRAM GAME!!!\n\n");


    // check board (null state by default)
    char[][] real_board = null;
    // board that player uses
    char[][] current_board = null;
    // prompt for player
    int[][] prompt = null;

    // game loop
    while (true) {
      // check for null state
      if (real_board == null) {
        real_board = get_board();
        current_board = new char[5][5];

        // populate current_board with '_'
        for (char[] bi : current_board) {
          for (int i = 0; i < 5; ++i) {
            bi[i] = '_';
          }
        }

        // get prompt
        prompt = get_prompt(real_board);
      }

      // print board with prompt
      print_board(current_board, prompt);
      log("\n\n");

      // give player instructions
      log("Type 'quit' or 'exit' or 'clear' to quit");
      log("Type 'rules' for rules");
      logf("Player Input: ");

      // get next move from player
      String move = cin();

      // check for quit
      if (move.length() == 0) {
        continue;
      }
      else if (move.equals("exit") || move.equals("quit") || move.equals("clear")) {
        log("exiting game...");
        return;
      }
      else if (move.equals("rules")) {
        log("Player can input unlimited moves at once");
        log("Moves are delimited by spaces");

        log("There are three kinds of moves:");
        log("   <cord>: places '#' at the coordinate");
        log("   -<cord>: places '_' at the coordinate");
        log("   x<cord>: places 'x' at the coordinate");

        log("<cord> is either in form <L><N> or <N><L>");
        log("   <L> is a-e or A-E");
        log("   <N> is 1-5");

        log("example Input: a1 3b D4 2C -e3 -1a xa1 x4E");

        log("\npress ENTER to continue...");
        cin();
      }
      // user can input as many moves at once as he wants
      // moves are delimited by spaces
      else for (String split : move.split(" ")) {

        // z = char[0]
        // get length
        char z = split.charAt(0);
        int length = split.length();

        // valid examples:
          // a1 1a -a1 xa1
        // invalid examples:
          // TODO
        if (length != 2 && ((z != '-' && z != 'x') || length != 3)) {
          err("invalid command");
          continue;
        }

        // if split[0] == '-'
          // letter = '_'
        // if split[0] == 'x'
          // letter = 'x'
        // else
          // letter = '#'
        char letter = z == '-' ? '_' : z == 'x' ? 'x' : '#';

        // if split[0] == '_' or split[0] == 'x'
          // row = split[1]
          // col = split[2]
        // else
          // row = split[0]
          // col = split[1]
        int row = (int) split.charAt(z == '-' || z == 'x' ? 1 : 0);
        int col = (int) split.charAt(z == '-' || z == 'x' ? 2 : 1);

        // if the row cord is a digit, swap row and col
          // otherwise, check is col is a digit
          // change col to a valid index
        if ('1' <= row && row <= '5') {
          int row_temp = row;
          row = col;
          col = row_temp;
        }
        else if ('1' > col || col > '5') {
          err("no digit found in cord");
          continue;
        }

        col -= '1';

        // check if row is a lowercase letter
          // change row to a valid index
        // check if row is a uppercase letter
          // change row to a valid index
        // else row is not a letter, throw error
        if ('a' <= row && row <= 'e') {
          row -= 'a';
        }
        else if ('A' <= row && row <= 'e') {
          row -= 'A';
        }
        else {
          err("no letter found in cord");
          continue;
        }

        // set letter at (row, col) to the user given letter
        current_board[row][col] = letter;

        // if board
        if (board_equal(current_board, real_board)) {

          // YAY!
          print_board(current_board, prompt);
          log("You successfully completed the game!");
          log("press ENTER to continue");
          cin();

          // set board to null state
          real_board = null;
        }
      }
    }
  }

  // Adam ----------------------------------------------------------------------
  static void print_board(char[][] board, int[][] numbers) {

    // Leave board at 5x5
    int rLen = board[0].length;

    // To print colummn coordinates
    char rowCords[] = new char[] {'A', 'B', 'C', 'D', 'E'};

    int r = 2; // Default maximum number of rows


    // Finds the maximum number of rows to avoid prining extra blank spaces
    for(int i = 2; i > -1; i--)
    {
      if(numbers[0][i] == -1 && numbers[1][i] == -1 && numbers[2][i] == -1 && numbers[3][i] == -1 && numbers[4][i] == -1) {
        r--;
      }
    }

    // For looks, I swap the non -1 numbers so that there are only blanks on top of numbers
    for(int i = 0; i < 5; i++)
    {
      int j = 2;
      boolean swapped = false;
      while(!swapped && j > 0)
      {
        if(numbers[i][j] != -1)
        {
          int temp = numbers[i][0];
          numbers[i][0] = numbers[i][j];
          numbers[i][j] = temp;
          swapped = true;
        }
        j--;
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
        if(numbers[i + 5][a] == -1) {
          // Stop loop to print row
          a = rLen;
        }

        else {
          // Generates a string literal for the row numbers for print formatting purposes
          row += (Integer.toString(numbers[i + 5][a]) + " ");
        }
      }

      // Print row numbers for row i
      System.out.printf("%6s ", row);

      // Print board row i
      for(int j = 0; j < board[i].length; j++) {
        System.out.print(board[i][j] + " ");
      }

      // Print board col coordinate for row i
      System.out.print(rowCords[i] + "\n");
    }

    // Print Col Cords
    System.out.print("       ");
    for(int i = 1; i < 6; i++) {
      System.out.print(i + " ");
    }
    System.out.print("\n");
  }

  static void UnitTest() {
    // Print Board Unit Test
    System.out.println("\n~Unit Test 0: Print Board~");
    print_board(static_board, static_board_prompt);

    // Get Board Unit Test
    System.out.println("\n~Unit Test 1: Get Board~");
    get_board();
    
    // Add your units tests below:
  }

}
*/