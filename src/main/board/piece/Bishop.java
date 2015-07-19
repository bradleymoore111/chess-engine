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
		int i=x+1; // Quadrant 1
		int j=y+1; // (x,y) is now (i,j)
		while(i<8&&j<8){
			positions.add(new XY(i,j));
			i++;
			j++;
			if(board[i][j] != 0){
				break;
			}
		}

		i=x-1; // Quadrant 2
		j=y+1;
		while(i>=0&&j<8){
			positions.add(new XY(i,j));
			i--;
			j++;
			if(board[i][j] != 0){
				break;
			}
		}

		i=x-1; // Quadrant 3
		j=y-1;
		while(i>=0&&j>=0){
			positions.add(new XY(i,j));
			i--;
			j--;
			if(board[i][j] != 0){
				break;
			}
		}

		i=x+1; // Quadrant 4
		j=y-1;
		while(i<8&&j>=0){
			positions.add(new XY(i,j));
			i++;
			j--;
			if(board[i][j] != 0){
				break;
			}
		}

		return positions;
	}
}