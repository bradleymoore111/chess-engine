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
			System.out.println("Pick a piece to get its possible moves");
			System.out.print("X Y: ");
			int x=a.nextInt();
			int y=a.nextInt();
			message = board.getMoves(new XY(x,y)).toString();
		}
	}
}