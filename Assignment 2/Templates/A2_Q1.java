import java.util.*;

public class A2_Q1 {

	public static void main(String[] args) {
		String[][] board = {{"#","#","#","o","o",".","#","#","#"},
							{"o",".",".",".",".",".",".",".","."},
							{".",".",".",".","o",".",".",".","o"},
							{".","o","o","o",".",".",".",".","."},
							{"#","#","#",".",".",".","#","#","#"}};
		int[] result = game(board);
		System.out.println(Arrays.toString(result));
	}

	private static class BallPosition{
		public int x;
		public int y;

		public BallPosition(int X, int Y){
			x = X;
			y = Y;
		}
	}

	private static class Result{
		public int ballsLeft;
		public int moves;

		public Result(int ball, int move) {
			ballsLeft = ball;
			moves = move;
		}

	}
	
	public static int[] game(String[][] board){
		ArrayList<BallPosition> ballPositions = findAllBalls(board);
		ArrayList<Result> results = new ArrayList<>();
		completeSearch(ballPositions, board, 0, results);
		int[] finalResult = getResult(results);
		return finalResult;
	}


	private static void completeSearch(ArrayList<BallPosition> ballPositions, String[][] board, int prevMoves, ArrayList<Result> results){
		int moves = prevMoves;

		for (BallPosition ballPosition : ballPositions){
			if(canJumpLeft(ballPosition, board)){
				String[][] boardCopy = Arrays.stream(board).map(arr -> Arrays.copyOf(arr, arr.length)).toArray(String[][]::new);
				ArrayList<BallPosition> newBallPositions = jumpLeft(boardCopy, ballPosition.x, ballPosition.y, ballPositions);
				moves++;
				completeSearch(newBallPositions, boardCopy, prevMoves+1, results);
			}
			if(canJumpUp(ballPosition, board)){
				String[][] boardCopy = Arrays.stream(board).map(arr -> Arrays.copyOf(arr, arr.length)).toArray(String[][]::new);
				ArrayList<BallPosition> newBallPositions = jumpUp(boardCopy, ballPosition.x, ballPosition.y, ballPositions);
				moves++;
				completeSearch(newBallPositions, boardCopy, prevMoves+1, results);
			}
			if(canJumpRight(ballPosition, board)){
				String[][] boardCopy = Arrays.stream(board).map(arr -> Arrays.copyOf(arr, arr.length)).toArray(String[][]::new);
				ArrayList<BallPosition> newBallPositions = jumpRight(boardCopy, ballPosition.x, ballPosition.y, ballPositions);
				moves++;
				completeSearch(newBallPositions, boardCopy, prevMoves+1, results);
			}
			if(canJumpDown(ballPosition, board)){
				String[][] boardCopy = Arrays.stream(board).map(arr -> Arrays.copyOf(arr, arr.length)).toArray(String[][]::new);
				ArrayList<BallPosition> newBallPositions = jumpDown(boardCopy, ballPosition.x, ballPosition.y, ballPositions);
				moves++;
				completeSearch(newBallPositions, boardCopy, prevMoves+1, results);
			}
		}

		//end condition
		if(moves == prevMoves){
			Result result = new Result(ballPositions.size(), moves);
			results.add(result);
			return;
		}

	}

	private static boolean canJumpLeft(BallPosition ballPosition, String[][] board){
		int x = ballPosition.x;
		int y = ballPosition.y;
		if(x>1 && board[y][x-1].equals("o") && board[y][x-2].equals(".")){
			return true;
		}
		return false;
	}

	private static boolean canJumpRight(BallPosition ballPosition, String[][] board){
		int x = ballPosition.x;
		int y = ballPosition.y;
		if(x<7 && board[y][x+1].equals("o") && board[y][x+2].equals(".")){
			return true;
		}
		return false;
	}

	private static boolean canJumpDown(BallPosition ballPosition, String[][] board){
		int x = ballPosition.x;
		int y = ballPosition.y;
		if(y<3 && board[y+1][x].equals("o") && board[y+2][x].equals(".")){
			return true;
		}
		return false;
	}

	private static boolean canJumpUp(BallPosition ballPosition, String[][] board){
		int x = ballPosition.x;
		int y = ballPosition.y;
		if(y>1 && board[y-1][x].equals("o") && board[y-2][x].equals(".")){
			return true;
		}
		return false;
	}

	private static int[] getResult(ArrayList<Result> results){
		int lowestBalls = 1000;
		int minMoves = 1000;
		ArrayList<Result> newResults = new ArrayList<>();

		//get min number of balls
		for(Result result : results){
			System.out.println("{" + result.ballsLeft + ", " + result.moves + "}");
			if(result.ballsLeft < lowestBalls){
				lowestBalls = result.ballsLeft;
				newResults.clear();
				newResults.add(result);
			}else if(result.ballsLeft == lowestBalls){
				newResults.add(result);
			}
		}

		//get min number of moves
		for(Result result : newResults){
			if(result.moves < minMoves){
				minMoves = result.moves;
			}
		}

		int[] finalResult = {lowestBalls, minMoves};
		return finalResult;

	}


	private static ArrayList<BallPosition> jumpRight(String[][] board, int x, int y, ArrayList<BallPosition> ballPositions){
		ArrayList<BallPosition> newBallPositions = new ArrayList<>();
		//copy all positions to new array
		for(BallPosition position : ballPositions){
			newBallPositions.add(position);
		}

		//make the jump
		board[y][x] = ".";
		board[y][x+2] = "o";
		board[y][x+1] = ".";

		//update positions in new array
		BallPosition newPosition = new BallPosition(x+2, y);
		newBallPositions.removeIf(ballPosition -> (ballPosition.x == x && ballPosition.y == y) || (ballPosition.x == x + 1 && ballPosition.y == y));
		newBallPositions.add(newPosition);

		return newBallPositions;
	}

	private static ArrayList<BallPosition> jumpLeft(String[][] board, int x, int y, ArrayList<BallPosition> ballPositions){
		ArrayList<BallPosition> newBallPositions = new ArrayList<>();
		//copy all positions to new array
		for(BallPosition position : ballPositions){
			newBallPositions.add(position);
		}

		//make the jump
		board[y][x] = ".";
		board[y][x-2] = "o";
		board[y][x-1] = ".";

		//update positions in new array
		BallPosition newPosition = new BallPosition(x-2, y);
		newBallPositions.removeIf(ballPosition -> (ballPosition.x == x && ballPosition.y == y) || (ballPosition.x == x - 1 && ballPosition.y == y));
		newBallPositions.add(newPosition);

		return newBallPositions;
	}

	private static ArrayList<BallPosition> jumpDown(String[][] board, int x, int y, ArrayList<BallPosition> ballPositions){
		ArrayList<BallPosition> newBallPositions = new ArrayList<>();
		//copy all positions to new array
		for(BallPosition position : ballPositions){
			newBallPositions.add(position);
		}

		//make the jump
		board[y][x] = ".";
		board[y+2][x] = "o";
		board[y+1][x] = ".";

		//update positions in new array
		BallPosition newPosition = new BallPosition(x,y+2);
		newBallPositions.removeIf(ballPosition -> (ballPosition.x == x && ballPosition.y == y) || (ballPosition.x == x && ballPosition.y == y + 1));
		newBallPositions.add(newPosition);

		return newBallPositions;
	}

	private static ArrayList<BallPosition> jumpUp(String[][] board, int x, int y, ArrayList<BallPosition> ballPositions){
		ArrayList<BallPosition> newBallPositions = new ArrayList<>();
		//copy all positions to new array
		for(BallPosition position : ballPositions){
			newBallPositions.add(position);
		}

		//make the jump
		board[y][x] = ".";
		board[y-2][x] = "o";
		board[y-1][x] = ".";

		//update positions in new array
		BallPosition newPosition = new BallPosition(x, y-2);
		newBallPositions.removeIf(ballPosition -> (ballPosition.x == x && ballPosition.y == y) || (ballPosition.x == x && ballPosition.y == y - 1));
		newBallPositions.add(newPosition);

		return newBallPositions;
	}




	private static ArrayList<BallPosition> findAllBalls(String[][] board){
		ArrayList<BallPosition> ballPositions = new ArrayList<>();

		for(int y = 0; y < board.length; y++){
			for(int x = 0; x < board[y].length; x++){
				if(board[y][x].equals("o")){
					ballPositions.add(new BallPosition(x, y));
				}
			}
		}

		return ballPositions;
	}



}
