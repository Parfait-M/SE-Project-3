package proj;

public class Controller {

	//objects from Model, View, and GameType classes
	static Model model;
	static View view;
	static GameType game;
	static java.util.Scanner kb = new java.util.Scanner (System.in);
	//functions needed to make life easier in java
	static void sop(Object s) {System.out.print(s);}
	static void sopl(Object s) {System.out.println(s);}

	static void exit() {
		sopl("THANK YOU FOR PLAYING!!\nCOME VISIT US AGAIN!!");
		System.exit(0);
	}
	// Method updates the GameType option to the user's choice
	static void getGame() {
		String inp;
		int i = 0;

		sopl("WELCOME TO");
		sopl("ADAM, JOHN, PARFAIT, AND ZAC'S");
		sopl("NONAGRAM GAME!!!\n\n");

		do
		{
			try {
			sopl("Please select game that you want to play: ");
			sopl("1. Nonogram\n2. Sudoku\n3. QUIT");
			
				inp = kb.next();
				i = Integer.parseInt(inp);
			}catch (Exception e) {
				sopl("Invalid input, please try again!");
				i = -1;
			}
		}while(i < 1 || i > 3);
		switch(i)
		{
		case 1:
			game = GameType.NONOGRAM;
			break;
		case 2:
			game = GameType.SUDOKU;
			break;
		case 3:
			exit();
		}
	}

	
	public static int updateBoard(String input) {
		int i = 0, r = 0, c = 0;
		char ch, playChar = ' ';
		switch(game) {
		case NONOGRAM:
			ch = input.charAt(i);
			if(ch == 'x') {
				playChar = 'x';
				++i;
			}
			else if(ch == '-') {
				playChar = '_';
				i++;
			}else
				playChar = '#';
			break;

		case SUDOKU:
			ch = input.charAt(i);
			if(ch == '-') {
				playChar = '_';
				i++;
			}else {
				playChar = input.charAt(input.length() - 1);
			}
			break;
		}
		
		try {
			c = Integer.parseInt(""+input.charAt(i)) - 1;
			r = Integer.parseInt(""+ (input.charAt(i+1) - 'a'));
		}catch (Exception e){
			r = c = -1;
		}
		
		return model.updateBoard(playChar, r, c);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean quit = false;

		while(!quit) {
			//enum GameType has a quit value
			//getGame just asks the user for the game they want to play
				getGame();
				// my function. Probably call view to print quitting message or not.

				//void function to take an enum GameType and make a board out of it. Guarentee it is not quit
				model.makeBoard(game);
				view.setGameType(game);
				view.setBoardSize(model.getSize());

				//model function that returns size of the board
				//int boardSize = model.getBoardSize();
				//Print the game board from model, passed in game to make it easier to know what to print
				view.displayBoard(model.getBoard(),model.getSolution());

				//if we take input as strings, then:
				//getMove() just asks user for input. Returns string
				String input = view.getMove();
				while(!input.equalsIgnoreCase("quit") && !input.equalsIgnoreCase("q") &&
						!input.equalsIgnoreCase("exit")) {
					//QUIT can be whatever strings we decide to make game stop. I'll write that

					//user enters input in


					//updateBoard takes in a string value, and updates the game board based on that value.
					switch(updateBoard(input)){
					case 0:
						sopl("Incorrect input. Please try again");
						break;
					case 1:
						/// continue... ??
						break;
					case 2:
						sopl("CONGRATULATIONS!!! YOU WON THE GAME");
						input = "quit";
						break;
					}
					view.displayBoard(model.getBoard(),model.getSolution()); // print board again
					input = view.getMove();

				}


	}

	}
}
