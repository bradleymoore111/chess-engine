package test;

import java.util.Scanner;
import main.board.Board;
import main.board.piece.*;
import main.evaluation.Evaluation;
import main.search.Search;
public class Test{
	public static void main(String[] args) {
		Board board = new Board(true);

		Scanner a = new Scanner(System.in);
		String message="";

		while(true){
			System.out.println(board.toString());
			System.out.println("Previous command return message:\n"+message+"\n\n");
			System.out.println("To move a piece, 'move' <x> <y> <x> <y>.\nTo get a pieces moves, 'list' <x> <y>\nTo see if king is 'check'ed, enter a color (true or false)\nTo 'undo' a move... yea.\nTo find the best move ever, 'search'\nTo toggle ability to move when not turn, 'ToggleLock'\nTo reset the board to starting position, 'reset'");
			String choice = a.next();
			if(choice.equalsIgnoreCase("move")){
				int x=a.nextInt();
				int y=a.nextInt();
				XY o = new XY(x,y);
				x=a.nextInt();
				y=a.nextInt();
				board.move(o,new XY(x,y));
				message = board.getMessage();
			}else if(choice.equalsIgnoreCase("list")){
				int x=a.nextInt();
				int y=a.nextInt();
				message = board.getMoves(new XY(x,y)).toString();
			}else if(choice.equalsIgnoreCase("check")){
				long begin = System.nanoTime();
				boolean color=a.nextBoolean();
				message = ""+board.isChecked(color)+" in "+(System.nanoTime()-begin)/1000+" microseconds";
				message += "\n"+board.getMessage();
			}else if(choice.equalsIgnoreCase("undo")){
				board.undoMove();
				message = "Undid move";
			}else if(choice.equalsIgnoreCase("search")){
				int n = a.nextInt();
				message = "Best move EVER is\n"+Search.search(board,n)+"\nat "+n+" ply";
			}else if(choice.equalsIgnoreCase("togglelock")){
				board.moveLock = (board.moveLock)?false:true;
				message = "Toggled board lock";
			}else if(choice.equalsIgnoreCase("reset")){
				board.reset();
			}else if(choice.equalsIgnoreCase("clone")){
				Board temp = board.clone();
			}
		}
	}
}