package proj;

import java.util.ArrayList;

public class Model {

	// updateBoard outputs
	public static final int INVALID_INPUT = 0;
	public static final int VALID_INPUT = 1;
	public static final int SOLVED_BOARD = 2;

	// model constants
	private static int NIB; // for Sodoku, the square root of size
	private static int SIZE; // the hight and width of either Nonogram or Sodoku boards
	private static GameType gameType; // the model state type
	private static char[][] solved_board; // the solution to the current state
	private static char[][] current_board; // the current model state

	public static void main(String[] a) {
		makeBoard(GameType.NONOGRAM);
		View.setGameType(GameType.NONOGRAM);
		View.displayBoard(getBoard(),getSolution());
		// makeBoard(GameType.SUDOKU);

		// int[] prompt = new int[5];
		// for (int i = 0; i < a.length; ++i)
		// 	prompt[i] = Integer.parseInt(a[i]);
    //
		// boolean[][] ops = make_combos(prompt);
    //
		// for (boolean[] op : ops) {
		// 	for (boolean o : op) System.out.printf("%s ", o?'#':'_');
		// 	System.out.println();
		// }

	}

	/* Make Board
	changes model state specified by gameType
	*/
	static void makeBoard(GameType gt) {
		gameType = gt;

		if (gt == GameType.NONOGRAM) makeNonogram();
		else if (gt == GameType.SUDOKU) makeSudoku();
		else gameType = GameType.QUIT;
	}
	
	static int getSize() {
		return SIZE;
	}

	/* Get Board
	returns current model state
	*/
	static char[][] getBoard() {
		if (gameType == GameType.NONOGRAM) return getNonogram();
		else if (gameType == GameType.SUDOKU) return getSudoku();
		else return null;
	}

	/* Get Solution
	returns the solution to the current state
	*/
	static char[][] getSolution() {
		if (gameType == GameType.NONOGRAM) return solved_board;
		else if (gameType == GameType.SUDOKU) return solved_board;
		else return null;
	}

	/* Update Board
	updates the board based on input
		places c at coordinates[i][j] if input is valid
		returns INVALID_INPUT if input is invalid
		returns VALID_INPUT if input is valid
		returns SOLVED_BOARD if input solved board
	valid chars for Nonogram are '#', 'x', '_'
	valid chars for Sodoku are numbers '1'-'9' and '_'
	valid i and j are [0 -> SIZE)
	*/
	static int updateBoard(char c, int i, int j) {
		if (gameType == GameType.NONOGRAM) return updateNonogram(c,i,j);
		else if (gameType == GameType.SUDOKU) return updateSudoku(c,i,j);
		return INVALID_INPUT;
	}

	// Nonogram
	// ---------------------------------------------------------------------------

	/* Options
 	returns every possible combination of space size
 	defined recursively

 	total: the number of spaces to distribute among several columns
 	count: the number of columns that spaces are distributed among

 	examples
		options(n 0) -> [[]]
		options(n 1) -> [[n]]
 		options(1 2) -> [[0 1] [1 0]]
		options(2 2) -> [[0 2] [1 1] [2 0]]
	*/
	private static int[][] options(int total, int count) {
		if (count <= 0) {
			int[][] ans = {};
			return ans;
		}
		else if (count == 1) {
			int[][] ans = {{total}};
			return ans;
		}
		else {
			ArrayList<int[]> ans = new ArrayList<int[]>();
			for (int i = 0; i <= total; ++i) {
				int[][] op = options(total - i, count - 1);

				for (int[] j : op) {
					int[] temp = new int[j.length + 1];
					temp[0] = i;
					for (int k = 0; k < j.length; ++k) {
						temp[k+1] = j[k];
					}
					ans.add(temp);
				}
			}
			return ans.toArray(new int[ans.size()][]);
		}
	}
	/* Make Combos
	returns every possible line for a given prompt in boolean form
	prompt: a array of length SIZE that lists consecutive elements

	examples ('#' for true, '_' for false):
		prompt([2 1 0 0 0]) -> [[##_#_] [##__#] [_##_#]]
		prompt([1 1 0 0 0]) -> [[#_#__] [#__#_] [#___#] [_#_#_] [_#__#] [__#_#]]
		prompt([1 1 1 0 0]) -> [[#_#_#]]
	*/
	private static boolean[][] make_combos(int[] prompt) {
		int l = SIZE;
		int sum = 0;
		for (int i : prompt) {
			sum += i;
			l -= i == 0 ? 1 : 0;
		}

		int total = SIZE - (sum + l - 1);
		total = total > SIZE ? SIZE : total;

		int[][] ops = options(total, l + 1);
		ArrayList<boolean[]> ans = new ArrayList<boolean[]>();

		for (int[] op : ops) {
			boolean[] s = new boolean[SIZE];
			int k = 0;

			for (int i = 0; i < l; ++i) {
				for (int j = 0; j < op[i] + (i>0?1:0); ++j)
					s[k++] = false;
				for (int j = 0; j < prompt[i]; ++j)
					s[k++] = true;
			}
			for (int j = 0; j < op[l]; ++j)
				s[k++] = false;

			ans.add(s);
		}
		return ans.toArray(new boolean[ans.size()][]);
	}
	/* Get Mask
	given a set of combos, return two masks the size of a board
	 	that respectivly represent what elements are guarenteed to be and not to be.
	elements in the first mask that are:
		true represent numbers that are guarenteed to be empty
		false represent numbers that are not guarenteed to be empty
	elements in the second mask that are:
		true represent numbers that are guarenteed to be full
		false represent numbers that are not guarenteed to be full
	*/
	private static boolean[][][] get_mask(boolean[][][] combos) {
		boolean[][][] mask = new boolean[2][SIZE][SIZE];

		for (int a = 0; a < SIZE; ++a) {
			boolean[] sub_mask_0 = mask[0][a];
			boolean[] sub_mask_1 = mask[1][a];

			for (int b = 0; b < SIZE; ++b)
				sub_mask_0[b] = sub_mask_1[b] = true;

			for (boolean[] combo : combos[a]) {
				if (combo == null) continue;

				for (int b = 0; b < SIZE; ++b)
					(combo[b] ? sub_mask_0 : sub_mask_1)[b] = false;
			}
		}
		return mask;
	}
	/* Cut Mask
	geven two orthogonal sets of combos
		generate a mask based on the first set of combos
		remove any combos in the second set of combos that contradict that mask
		return true if any combos were removed from that second set of combos

		a removed combo is set to null
	*/
	private static boolean cut_mask(boolean[][][] a_combos, boolean[][][] b_combos) {
		boolean change = false;
		boolean[][][] mask = get_mask(a_combos);

		for (int b = 0; b < SIZE; ++b) {
			boolean[][] sub_b_combos = b_combos[b];

			for (int sb = 0; sb < sub_b_combos.length; ++sb) {
				boolean[] b_combo = sub_b_combos[sb];
				if (b_combo == null) continue;

				boolean fail = false;
				for (int a = 0; a < SIZE && !fail; ++a) {
					fail = mask[b_combo[a] ? 0 : 1][a][b];
				}
				if (fail) {
					sub_b_combos[sb] = null;
					change = true;
				}
			}
		}

		return change;
	}

	/* Make Nonogram
	generates a valid board of size 5
		current_board stores the working board
		solved_board stores the solution to compare to
		SIZE stores the size
	*/
	private static void makeNonogram() {
		// set the size to 5
		SIZE = 5;

		// probably reduntant, but not sure what's going on upstairs
		View.setGameType(GameType.NONOGRAM);
		View.setBoardSize(SIZE);

		solved_board = new char[SIZE][SIZE];
		current_board = new char[SIZE][SIZE];
		int[][] col_prompt = new int[SIZE][SIZE];
		int[][] row_prompt = new int[SIZE][SIZE];
		boolean[][][] col_combos = new boolean[SIZE][][];
		boolean[][][] row_combos = new boolean[SIZE][][];

		// loop continuously until a unique board is produced
		boolean unique_solution;
		do {
			for (int i = 0; i < SIZE; ++i)
				for (int j = 0; j < SIZE; ++j) {
					solved_board[i][j] = Math.random() < 0.6 ? '#' : '_';
					current_board[i][j] = '_';
				}

			// generate prompts
			for (int i = 0; i < SIZE; ++i) {
				int col_row_count = 0;
				int row_col_count = 0;
				int col_row_index = 0;
				int row_col_index = 0;

				for (int j = 0; j < SIZE; ++j) {
					if (solved_board[i][j] == '#') {
						++col_row_count;
					}
					else if (col_row_count > 0){
						col_prompt[i][col_row_index++] = col_row_count;
						col_row_count = 0;
					}

					if (solved_board[j][i] == '#') {
						++row_col_count;
					}
					else if (row_col_count > 0) {
						row_prompt[i][row_col_index++] = row_col_count;
						row_col_count = 0;
					}
				}

				if (col_row_count > 0)
					col_prompt[i][col_row_index++] = col_row_count;
				if (row_col_count > 0)
					row_prompt[i][row_col_index++] = row_col_count;
			}

			// generate combos
			for (int i = 0; i < SIZE; ++i) {
				col_combos[i] = make_combos(col_prompt[i]);
				row_combos[i] = make_combos(row_prompt[i]);
			}

			// go back and forth cutting masks until they stop changing each other
			do {
				cut_mask(row_combos, col_combos);
			} while (cut_mask(col_combos, row_combos));

			// generate the place-guarenteeing-masks
			boolean[][][] mask = get_mask(col_combos);

			// a solution is only unique if the two masks that are genrated from
			// 	the col_combos have no ambiguous elements
			unique_solution = true;
			for (int i = 0; i < SIZE && unique_solution; ++i)
				for (int j = 0; j < SIZE && unique_solution; ++j)
					unique_solution = mask[0][i][j] || mask[1][i][j];
		} while (!unique_solution);
	}

	/* Get Nonogram
	returns the current board state
	*/
	private static char[][] getNonogram() {
		// return solved_board;
		return current_board;
	}

	/* Is Nonogram Solved
	returns true if board is sovlved and false otherwise
	*/
	private static boolean isNonogramSolved() {
		for (int i = 0; i < SIZE; ++i)
			for (int j = 0; j < SIZE; ++j)
				if ((current_board[i][j] == '#') != (solved_board[i][j] == '#'))
					return false;
		return true;
	}

	/* Update Nonogram
	updates the board based on input
		places c at coordinates[i][j] if input is valid
		returns INVALID_INPUT if input is invalid
		returns VALID_INPUT if input is valid
		returns SOLVED_BOARD if input solved board
	valid chars are '#', 'x', '_'
	valid i and j are [0 -> SIZE)
	*/
	private static int updateNonogram(char in, int r, int c) {
		if ((in == '#' || in == '_' || in == 'x') &&
		(0 <= r && r < SIZE && 0 <= c && c < SIZE)) {
			current_board[r][c] = in;
			return isNonogramSolved() ? SOLVED_BOARD : VALID_INPUT;
		}
		else return INVALID_INPUT;
	}

	// Sudoku
	// ---------------------------------------------------------------------------

	/* Sudoku Blocks, Make Sudoku Blocks
	generates the set of sodoku blocks
		(sodoku blocks are arrays of indecies for each region)
	*/
	private static int[][] sodoku_blocks;
	private static void make_sudoku_blocks() {
		sodoku_blocks = new int[SIZE * NIB][SIZE];
		for (int i = 0; i < SIZE; ++i)
			for (int j = 0; j < SIZE; ++j)
				sodoku_blocks[i][j] = sodoku_blocks[SIZE+j][i] = SIZE * i + j;
		for (int I = 0, K = SIZE + SIZE; I < SIZE * SIZE; I += NIB * SIZE)
			for (int J = 0; J < SIZE; J += NIB, K++)
				for (int i = 0, k = 0; i < NIB * SIZE; i += SIZE)
					for (int j = 0; j < NIB; ++j)
						sodoku_blocks[K][k++] = I + J + i + j;
	}

	/* Get Value (at k)
	returns the character at the indecies of the 2D board from a 1D coordinate
	returns -1 if character is '_'
	returns 0-8 if character is '1'-'9'
	*/
	private static int get_value(int k) {
		char c = current_board[k / SIZE][k % SIZE];
		if (c == '_') return -1;
		else return (int)(c - '1');
	}

	/* Prompt Index, Sudoku Prompts, Get Sudoku Prompts
	an array of possible board prompts that can be cycled through
		*** I'd like to implement a generator, but probably will not have time ***
	*/
	private static int prompt_index = 0;
	private static String[] sudoku_prompts = {
		"_____9__81_4___5__26__1_____9__3_4__81_7_5_36__3_9__8_____5__43__1___8_55__3_____",
		"____6___23__9__6___48_____5_79__2___46_____23___4__96_2_____35___1__6__85___4____",
		"_______74_8_4_3_9_____571__3________9_45_83_6________1__932_____2_8_9_6_13_______",
		"_9_6_2_8_______9__23_____64_1_49___745_____937___23_4_36_____79__1_______2_1_6_3_",
		"_5___4____97_8___51_____9___6___8__2__24_68__4__2___7___1_____97___4_15____9___3_"
	};
	private static char[] get_sudoku_prompt() {
		return sudoku_prompts[prompt_index++ % sudoku_prompts.length].toCharArray();
	}

	/* Make Sudoku
	gets a valid soduku prompt and clears the board
	sets the SIZE to 3*3
	*/
	private static void makeSudoku() {
		NIB = 3;
		SIZE = NIB * NIB;
		View.setBoardSize(SIZE);
		solved_board = new char[SIZE][SIZE];
		current_board = new char[SIZE][SIZE];
		make_sudoku_blocks();

		char[] prompt = get_sudoku_prompt();
		for (int i = 0; i < SIZE; ++i)
			for (int j = 0; j < SIZE; ++j)
				current_board[i][j] = solved_board[i][j] = prompt[i*SIZE + j];
	}

	/* Get Sudoku
	returns current model state
	*/
	private static char[][] getSudoku() {
		return current_board;
	}

	/* Is Sudoku Solved
	returns true if game is solved, false otherwise
	*/
	private static boolean isSudokuSolved() {
		for (int[] b : sodoku_blocks) {
			int[] num_count = new int[SIZE];
			for (int i : b) {
				int n = get_value(i);
				if (n < 0) return false;
				else ++num_count[n];
			}
			for (int c : num_count)
				if (c != 1) return false;
		}
		return true;
	}

	/* Update Sudoku
	updates the board based on input
		places c at coordinates[i][j] if input is valid
		returns INVALID_INPUT if input is invalid
		returns VALID_INPUT if input is valid
		returns SOLVED_BOARD if input solved board
	valid chars for Sodoku are numbers '1'-'9' and '_'
	valid i and j are [0 -> SIZE)
	if i and j land on an index that is specified in the prompt,
		the input will be deamed invalid
	*/
	private static int updateSudoku(char in, int r, int c) {
		if ((in == '_' || ('1' <= in && in <= '9')) &&
		(0 <= r && r < SIZE && 0 <= c && c < SIZE) &&
		(solved_board[r][c] == '_')) {
			current_board[r][c] = in;
			return isSudokuSolved() ? SOLVED_BOARD : VALID_INPUT;
		}
		else return INVALID_INPUT;
	}
}
