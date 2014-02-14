package chai;

import chesspresso.move.IllegalMoveException;
import chesspresso.position.Position;

public class AlphaBetaAI implements ChessAI {

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
	
	public short getMove(Position position) throws IllegalMoveException {
		int maxDepth = 1;
		thisPlayer = position.getToPlay();
		short s = getMove(position, maxDepth);
		short[] allMoves = position.getAllMoves();
		for(short m : allMoves) {
			if(m==s) {
				System.out.println("You are fine");
			}
		}
		return 0;
	}
	
	public short getMove(Position position, int maxDepth) throws IllegalMoveException {
		short[] allMoves = position.getAllMoves();
		ChessMove v = maxValue( position, Integer.MIN_VALUE, Integer.MAX_VALUE, maxDepth);
//		boolean f = false;
		for(short m : allMoves) {
			if(m==v.move) {
//				System.out.println("You are fine");
			}
		}
		return v.move;
	}
	
	public ChessMove maxValue(Position position, int alpha, int beta, int maxDepth) throws IllegalMoveException {
		if(maxDepth == 0 || position.isTerminal()) {
			return new ChessMove((short) 0, evalPosition(position));
		}
		int v = Integer.MIN_VALUE;
		short[] allMoves = position.getAllMoves();

		short bestMove = allMoves[0];
		for(int i = 0; i<allMoves.length; i++) {
			position.doMove(allMoves[i]);
			ChessMove child = minValue(position, alpha, beta, maxDepth - 1);

			position.undoMove();
//			v = alpha;
			if(alpha < child.value) {
				alpha = child.value;
				bestMove = allMoves[i];
			}
//			v = Math.max(v,  child.value);
			if(alpha > beta) {//should be <= right??
				return child; //doesn't matter what we return, since this option won't be taken away
			}
//			if(alpha < v) {
//				alpha = v;
//				
//			}
//			alpha = Math.max(alpha, v);
		}
		return new ChessMove(bestMove, alpha);
	}
	
	public ChessMove minValue(Position position, int alpha, int beta, int maxDepth) throws IllegalMoveException {
		if(maxDepth == 0 || position.isTerminal()) {
			return new ChessMove((short) 0, evalPosition(position));
		}
		int v = Integer.MAX_VALUE;
		
		short[] allMoves = position.getAllMoves();
		short bestMove = allMoves[0];
		for(int i = 0; i<allMoves.length; i++) {
			position.doMove(allMoves[i]);
			ChessMove child = maxValue(position, alpha, beta, maxDepth - 1);

			position.undoMove();
//			v = alpha;
			if(beta > child.value) {
				beta = child.value;
				bestMove = allMoves[i];
			}
//			v = Math.max(v,  child.value);
			if(beta < alpha) {//should be <= right??
				return child; //doesn't matter what we return, since this option won't be taken away
			}
//			if(alpha < v) {
//				alpha = v;
//				
//			}
//			alpha = Math.max(alpha, v);
		}
		return new ChessMove(bestMove, beta);
	}
	
	public int evalPosition(Position pos) {
		int util;
		
		if(pos.isMate()) {
//		System.out.println("Impossible!");
			util = Integer.MIN_VALUE;
		}
		else if(pos.isStaleMate()) {
			util = 0;
		} else {
			util = pos.getMaterial();
		}
//	System.out.println(Integer.MAX_VALUE == val);
		
		if(pos.getToPlay() != whoAmI()) {
			util = -util;
		}
		return util;
	}
	
	public int whoAmI() {
		return thisPlayer;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
