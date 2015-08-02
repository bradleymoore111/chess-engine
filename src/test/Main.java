package test;

import java.util.Scanner;
import main.board.Board;
import main.board.piece.*;
public class Main{
	public static void main(String[] args) {
		Board board = new Board();

		Scanner a = new Scanner(System.in);

		String message="";

		while(true){
			System.out.println(board.toString());
			System.out.println(message);
			System.out.println("To move a piece, 'move' <x> <y> <x> <y>.\nTo get a pieces moves, 'list' <x> <y>");
			String choice = a.next();
			if(choice.equalsIgnoreCase("move")){
				int x=a.nextInt();
				int y=a.nextInt();
				XY o = new XY(x,y);
				x=a.nextInt();
				y=a.nextInt();
				board.move(o,new XY(x,y));
			}else if(choice.equalsIgnoreCase("list")){
				int x=a.nextInt();
				int y=a.nextInt();
				message = board.getMoves(new XY(x,y)).toString();
			}			
		}
	}
}