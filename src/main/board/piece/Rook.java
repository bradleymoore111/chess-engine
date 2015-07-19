package main.board.piece;

import java.util.ArrayList;
public class Rook{
	public static ArrayList<XY> getMoves(XY pos,int[][] board){
		ArrayList<XY> positions = new ArrayList<XY>();
		int x = pos.x;
		int y = pos.y;
		
		// Left
		int i=x-1;
		while(i>=0){
			positions.add(new XY(i,y));
			i--;
			if(board[i][y] != 0){
				break;
			}	
		}

		// Right
		i=x+1;
		while(i<8){
			positions.add(new XY(i,y));
			i++;
			if(board[i][y] != 0){
				break;
			}
		}

		// Down
		i=y-1;
		while(i>=0){
			positions.add(new XY(x,i));
			i--;
			if(board[x][i] != 0){
				break;
			}
		}

		// Up
		i=y+1;
		while(i<8){
			positions.add(new XY(x,i));
			i++;
			if(board[x][i] != 0){
				break;
			}
		}

		return positions;
	}
}