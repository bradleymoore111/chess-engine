package main.board.piece;

import java.util.ArrayList;
public class Bishop{
	public static ArrayList<XY> getMoves(XY pos,int[][] board){
		ArrayList<XY> positions = new ArrayList<XY>();
		int x = pos.x;
		int y = pos.y;

		// 2 | 1
		// -----
		// 3 | 4
		int i=x; // Quadrant 1
		int j=y; // (x,y) is now (i,j)
		while(i<7&&j<7){
			i++;
			j++;
			positions.add(new XY(i,j));
			if(board[i][j] != 0){
				break;
			}
		}

		i=x; // Quadrant 2
		j=y;
		while(i>=1&&j<7){
			i--;
			j++;
			positions.add(new XY(i,j));
			if(board[i][j] != 0){
				break;
			}
		}

		i=x; // Quadrant 3
		j=y;
		while(i>=1&&j>=1){
			i--;
			j--;
			positions.add(new XY(i,j));
			if(board[i][j] != 0){
				break;
			}
		}

		i=x; // Quadrant 4
		j=y;
		while(i<7&&j>=1){
			i++;
			j--;
			positions.add(new XY(i,j));
			if(board[i][j] != 0){
				break;
			}
		}

		return positions;
	}
}