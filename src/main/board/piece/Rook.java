package main.board.piece;

import java.util.ArrayList;
public class Rook{
	public static ArrayList<XY> getMoves(XY pos,int[][] board){
		ArrayList<XY> positions = new ArrayList<XY>();
		int x = pos.x;
		int y = pos.y;
		
		// Left
		int i=x;
		while(i>=0){
			i--;
			positions.add(new XY(i,y));
			if(board[i][y] != 0){
				break;
			}	
		}

		// Right
		i=x;
		while(i<8){
			i++;
			positions.add(new XY(i,y));
			if(board[i][y] != 0){
				break;
			}
		}

		// Down
		i=y;
		while(i>=0){
			i--;
			positions.add(new XY(x,i));
			if(board[x][i] != 0){
				break;
			}
		}

		// Up
		i=y;
		while(i<8){
			i++;
			positions.add(new XY(x,i));
			if(board[x][i] != 0){
				break;
			}
		}

		return positions;
	}
}