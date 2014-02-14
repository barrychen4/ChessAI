package chai;

import java.util.Random;
import chesspresso.position.Position;

public class MinimaxWithEvaluationAI extends MinimaxAIFinal {

	/*
	 * Evaluation is based on White player's perspective.
	 * When this method is called, is pos.isMate() is true,
	 * that means pos.getToPlay() has lost. So, if pos.getToPlay()
	 * is WHITE, then this should have a very negative value.
	 * 
	 */
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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
