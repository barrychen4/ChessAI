package chai;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import assignment_robots.PRMPlanner.ArmRobotWithDistance;
import chesspresso.move.Move;
import chesspresso.Chess;
import chesspresso.move.IllegalMoveException;
import chesspresso.position.Position;

public class MinimaxAI extends Application implements ChessAI {
	TextField commandField;
	TextArea logArea;
	MoveMaker[] moveMaker;
	private static final String welcomeMessage = 
			"Welcome to CS 76 chess.  Moves can be made using algebraic notation;"
			+ " for example the command c2c3 would move the piece at c2 to c3.  "
			+ "Additional commands: who, challenge <playername>.\n";
	private static final int PIXELS_PER_SQUARE = 64;
	BoardView boardView;
	ChessGame game;
	//---------------
	
	private int thisPlayer;
	
	
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
		//position = position.copy();
		int maxDepth = 1;
		thisPlayer = position.getToPlay();
		
		short s = getMove(position, maxDepth);
		
		return s;
	}
	
	/*
	 * If position.getToPlay() is WHITE, then 
	 */
	public short getMove(Position position, int maxDepth) throws IllegalMoveException {
		
		short[] allMoves = position.getAllMoves();
		short bestMove = allMoves[0];
		int val = Integer.MIN_VALUE;
		
		for(int i = 0; i<allMoves.length; i++) {
			position.doMove(allMoves[i]);
			int currVal = minMove(position, maxDepth - 1);
			if(currVal > val) {
				val = currVal;
				bestMove = allMoves[i];
			}
			position.undoMove();
		}
		
		
//		System.out.println("Move is " + s);
		return bestMove;
//		if(position.getToPlay() == Chess.WHITE) {
//			return maxMove(position, maxDepth).move;
//		} else {
//			return minMove(position, maxDepth).move;
//		}
	}		
	
	public int maxMove(Position position, int maxDepth) throws IllegalMoveException {
		
		if(maxDepth == 0 || position.isTerminal()) {
//			System.out.println(maxDepth);
			return evalPosition(position);
			
		} else {
			
			short [] moves = position.getAllMoves();
//			System.out.println(moves.length);
			int val = Integer.MIN_VALUE;
			short bestMove = moves[0];
			for(int i = 0; i<moves.length; i++) {
				position.doMove(moves[i]);
				int mini = minMove(position, maxDepth - 1);
//				System.out.println("Minimum is " + mini);
//				System.out.println(position);
				if(mini>val) {
					val = mini;
					bestMove = moves[i];
				}
//				System.out.println("Start" + position + position.undoMove() + position + "End");
//				System.out.println("undo");
				position.undoMove();
//				System.out.println(position);
			}
			
			return val;
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
	public int evalPosition(Position pos) {
		Random r = new Random();
		int val;
		if(pos.isMate()) {
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
		
	public int minMove(Position position, int maxDepth) throws IllegalMoveException {
		
		
		if(maxDepth == 0 || position.isTerminal()) {
			return evalPosition(position);
		} else {
			short [] moves = position.getAllMoves();
			int val = Integer.MAX_VALUE;
			short bestMove = moves[0];
			for(int i = 0; i<moves.length; i++) {
//				Position check1 = position;
				position.doMove(moves[i]);
				int maxi = maxMove(position, maxDepth - 1);
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
			return val;
		}
	}
	
	
	
//	public ChessMove maxMove(Position pos, short sh, short[] allMoves) throws IllegalMoveException {
//		
//		pos.doMove(allMoves[0]);
//		int val = evalPosition(pos);
//		short bestMove = allMoves[0];
//		pos.undoMove();
//		
//		for(int j = 1; j<allMoves.length; j++) {
//			pos.doMove(allMoves[j]);
//			int currVal = evalPosition(pos);
//			pos.undoMove();
//			if(currVal > val) {
//				val = currVal;
//				bestMove = allMoves[j];
//			}
//		}
//		
//		return bestMove;
//		
//	}
//	
//public short minMove(Position pos, short sh, short[] allMoves) throws IllegalMoveException {
//		
//		
//	
//		pos.doMove(allMoves[0]);
//		int val = evalPosition(pos);
//		short bestMove = allMoves[0];
//		pos.undoMove();
//		
//		for(int j = 1; j<allMoves.length; j++) {
//			pos.doMove(allMoves[j]);
//			int currVal = evalPosition(pos);
//			pos.undoMove();
//			if(currVal < val) {
//				val = currVal;
//				bestMove = allMoves[j];
//			}
//		}
//		
//		return bestMove;
//		
//	}
	
	
	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IllegalMoveException {
		primaryStage.setTitle("CS 76 Chess");

	Position position = new Position(
			"k7/8/1R6/8/8/8/8/1K2Q3 w KQkq - 0 1");
	game = new ChessGame(position);
	
//	position.doMove(Move.getRegularMove(0, 1, false) );
		

		// build the board
		boardView = new BoardView(game, PIXELS_PER_SQUARE);

		// build the text area for giving log info to user
		logArea = new TextArea();
	 	//logArea.setPrefColumnCount(50);
	 	logArea.setPrefRowCount(5);
	 	logArea.setEditable(false);
	 	logArea.setWrapText(true);

		
		// build the command entry text field
		commandField = new TextField();
		
		// request focus on the command field after the ui is built,
		//  to get a blinking cursor
		Platform.runLater(new Runnable() {
		    public void run() {
		        commandField.requestFocus();
		    }
		});

		// set up the movemakers for black and white players.
		// Movemakers handle getting input from an AI, from the keyboard, or
		// from a server, depending on which type is used.
		moveMaker = new MoveMaker[2];

		VBox vb = new VBox();
		vb.getChildren().addAll(boardView, logArea, commandField);
		vb.setSpacing(10);
		vb.setPadding(new Insets(10, 10, 10, 10));

		// add everything to a root stackpane, and then to the main window
		StackPane root = new StackPane();
		root.getChildren().add(vb);
		primaryStage.setScene(new Scene(root)); //, boardView.getPreferredWidth(), 600));
		primaryStage.show();
		System.out.println("player " + position.getToPlay());
		System.out.println("terminal " + position.isTerminal());
		System.out.println("check " + position.isCheck());
		System.out.println("mate " + position.isMate());
//		position.doMove(Move.getRegularMove(51, 49, false) );
//		System.out.println("Evaluatoin is " + new MinimaxAI().evalPosition(position));
		System.out.println("player " + position.getToPlay());
		System.out.println("terminal " + position.isTerminal());
		System.out.println("check " + position.isCheck());
		System.out.println("mate " + position.isMate());
		// sets the game world's game loop (Timeline)


		// moveMaker = new AIMoveMaker(new RandomAI());

	}
//	public static void main(String[] args) {
//		
//
//		System.out.println(-Integer.MAX_VALUE);
//		System.out.println(Integer.MIN_VALUE);
//	}
//As time passes, handle the state of the game
	private class GameHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			// System.out.println("timer fired");

			// System.out.println(boardView.ready());
			// setting activeMoveSource to null will cause a new one to be
			// created:

			MoveMaker mover = moveMaker[game.position.getToPlay()];

			if (mover.getState() == Worker.State.READY) {
				mover.start(game.position);
			} else if (mover.getState() == Worker.State.SUCCEEDED
					&& boardView.ready()) {
				short move = mover.getMove();
				boardView.doMove(move);
				mover.reset();
			}

			// System.out.println("activeMoveSource state " +
			// activeMoveSource.getState());

			// short move =
			// playerMoveSources[0].getMove(game.position.toString());
			// System.out.println(move);
			// boardView.doMove(move);
		}

	}

//	private class TextFieldMoveMaker implements MoveMaker,
//			EventHandler<ActionEvent> {
//
//		private Worker.State state;
//		short move;
//
//		public TextFieldMoveMaker() {
//			this.state = Worker.State.READY;
//			commandField.setOnAction(this);
//			move = 0;
//		}
//
//		@Override
//		public void start(Position position) {
//			//String[] players = {"WHITE", "BLACK"};
//			//commandField.setPromptText("Your move," + players[position.getToPlay()] + ".");
//		}
//
//		@Override
//		public void reset() {
//			commandField.setText("");
//			this.state = Worker.State.READY;
//
//		}
//
//		@Override
//		public State getState() {
//			return state;
//		}
//
//		@Override
//		public short getMove() {
//			return move;
//		}
//
//		@Override
//		public void handle(ActionEvent e) {
//			String text = commandField.getText();
//			if (text != null & text != "") {
//				int fromSqi = Chess.strToSqi(text.charAt(0), text.charAt(1));
//				int toSqi = Chess.strToSqi(text.charAt(2), text.charAt(3));
//
//				move = game.findMove(fromSqi, toSqi);
//				this.state = Worker.State.SUCCEEDED;
//
//			}
//
//		}
//
//	}

//	private class AIMoveMaker implements MoveMaker {
//		ChessAI ai;
//		AIMoveTask moveTask;
//
//		public AIMoveMaker(ChessAI ai) {
//			super();
//			this.ai = ai;
//			this.moveTask = null;
//		}
//
//		public void start(Position position) {
//			moveTask = new AIMoveTask(ai, position);
//			new Thread(moveTask).start();
//		}
//
//		public Worker.State getState() {
//			// short circuit if moveTask hasn't been initalized
//
//			if (moveTask == null)
//				return Worker.State.READY;
//			return moveTask.getState();
//
//		}
//
//		public short getMove() {
//			return moveTask.getValue();
//
//		}
//
//		public void reset() {
//			this.moveTask = null;
//		}
//
//	
//	}

}
//
//	
//	public static void main(String[] args) throws IllegalMoveException {
//		
//		Position position = new Position(
//				"k       /8/8/8/8/8/8/K        w KQkq - 0 1");
//		System.out.println(position.getToPlay());
//		short move[] = position.getAllMoves();
//		position.doMove(move[0]);
//		System.out.println(position.getToPlay());
//		
//		System.out.println(-Integer.MAX_VALUE);
//		System.out.println(Integer.MIN_VALUE);
//	}
//
//}
