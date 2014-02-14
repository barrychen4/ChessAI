package chai;
import java.util.Random;
import chesspresso.move.IllegalMoveException;
import chesspresso.move.Move;
import chesspresso.position.Position;


public class MinimaxAIFinal implements ChessAI {

	private int thisPlayer;
	
	
	public class ChessMove {
		public int value;
		public short move;
		
		public ChessMove(int v) {
			value = v;
		}
		
		public ChessMove(short m, int v) {
			value = v;
			move = m;
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * returns the best move, calling a helper method to do so.
	 * @see chai.ChessAI#getMove(chesspresso.position.Position)
	 */
	public short getMove(Position position) throws IllegalMoveException {
//		System.out.println("I got called " + position.getToPlay());
//		thisPlayer = position.getToPlay();
//		System.out.println("Start position is " + position);
//		
//		System.out.println("position is " + position);
		int maxDepth = 3;
		thisPlayer = position.getToPlay();
		System.out.println(evalPosition(position));
		short s = getMove(position, maxDepth);
		
		return s;
	}
	
	/*
	 * If position.getToPlay() is WHITE, then 
	 */
	public short getMove(Position position, int maxDepth) throws IllegalMoveException {
		
		short s = maxMove(position, maxDepth).move;
		System.out.println("Chosen Best move is " + s);
//		System.out.println("Move is " + s);
		return s;
//		if(position.getToPlay() == Chess.WHITE) {
//			return maxMove(position, maxDepth).move;
//		} else {
//			return minMove(position, maxDepth).move;
//		}
	}		
	
	public ChessMove maxMove(Position position, int maxDepth) throws IllegalMoveException {
		
		if(maxDepth == 0 || position.isTerminal()) {
//			System.out.println(maxDepth);
			return new ChessMove(evalPosition(position));
			
		} else {
			
			short [] moves = position.getAllMoves();
//			System.out.println(moves.length);
			int val = Integer.MIN_VALUE;
			short bestMove = moves[0];
			for(int i = 0; i<moves.length; i++) {
				position.doMove(moves[i]);
				int mini = minMove(position, maxDepth - 1).value;
				if(position.isMate()) {
//					System.out.println("LOOK "+mini + " " + Integer.MAX_VALUE);
				}
//				System.out.println("Minimum is " + mini);
//				System.out.println(position);
				if(mini>val) {
//					if(maxDepth == 3)
//						System.out.println("Value of mini is " +mini + ", and value of val is " + val);
					val = mini;
					bestMove = moves[i];
//					if(maxDepth == 3) {
//						System.out.println("Best move is " + bestMove);
//						position.undoMove();
//						position.doMove(bestMove);
//						System.out.println(position.isMate());
//						position.undoMove();
//					}
				}
//				System.out.println("Start" + position + position.undoMove() + position + "End");
//				System.out.println("undo");
				position.undoMove();
//				System.out.println(position);
			}
//			System.out.println("val is " + val);
			return new ChessMove(bestMove, val);
		}
	}
	
	public int whoAmI() {
		return thisPlayer;
	}
	
	/*
	 * Evaluation is based on White player's perspective.
	 * When this method is called, is pos.isMate() is true,
	 * that means pos.getToPlay() has lost. So, if pos.getToPlay()
	 * is WHITE, then this should have a very negative value.
	 * 
	 */
	/*
	public int evalPosition(Position pos) {
		if(pos.getToPlay() != whoAmI()) {
			return -pos.getMaterial();
		}
		return pos.getMaterial();
	}
	*/
	
	public int evalPosition(Position pos) {
		Random r = new Random();
		int val;
		if(pos.isMate()) {
			System.out.println("PLAYER TO MOVE: " + pos.getToPlay());
			System.out.println("WHO AM I: " + thisPlayer);
			
//			System.out.println("Impossible!");
			val = Integer.MAX_VALUE;
		}
		else if(pos.isStaleMate()) {
			val = 0;
		} else {
			val = r.nextInt();
		}
//		System.out.println(Integer.MAX_VALUE == val);
		if(pos.getToPlay() != thisPlayer) {
			return val;
		}
		return -val;
	}
	
	public ChessMove minMove(Position position, int maxDepth) throws IllegalMoveException {
		
		
		if(maxDepth == 0 || position.isTerminal()) {
			return new ChessMove(evalPosition(position));
		} else {
			short [] moves = position.getAllMoves();
			int val = Integer.MAX_VALUE;
			short bestMove = moves[0];
			for(int i = 0; i<moves.length; i++) {
//				Position check1 = position;
				position.doMove(moves[i]);
				int maxi = maxMove(position, maxDepth - 1).value;
				if(maxi<val) {
					val = maxi;
					bestMove = moves[i];
				}
				position.undoMove();
//				System.out.println("Start" + position + position.undoMove() + position + "End");
//				System.out.println(position);
//				System.out.println(position.undoMove());
//				System.out.println(position);
			}
			return new ChessMove(bestMove, val);
		}
	}
	
	/**
	 * @param args
	 * @throws IllegalMoveException 
	 */
	public static void main(String[] args) throws IllegalMoveException {
		// TODO Auto-generated method stub
		Position position = new Position(
				"k7/8/1R6/8/8/8/8/1K2Q3 w KQkq - 0 1");
		System.out.println("player " + position.getToPlay());
		System.out.println("terminal " + position.isTerminal());
		System.out.println("check " + position.isCheck());
		System.out.println("mate " + position.isMate());
		MinimaxAIFinal m = new MinimaxAIFinal();
		short move = m.getMove(position);
		position.doMove(move);
//	System.out.println("Evaluatoin is " + new MinimaxAI().evalPosition(position));
		System.out.println("player " + position.getToPlay());
		System.out.println("terminal " + position.isTerminal());
		System.out.println("check " + position.isCheck());
		System.out.println("mate " + position.isMate());
	}

}
