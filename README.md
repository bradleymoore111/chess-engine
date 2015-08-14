Extremely useful link:
http://home.hccnet.nl/h.g.muller/max-src2.html

Program layout:
// Parentheses are optional future todos, otherwise, this is all end goal.

Search Program
	GetMoves from Board for each piece

	Make separate branch for each move

	Same for opposite color
		(Optional Check transposition table)

	Do up to Nth ply, then ping evaluation program with board // Might be 4 or 5, as after that it's in the millions

	(Optional pruning, use top N branches)
	(Optional pruning, if move is not above a certain threshold for evalution, EG. all branches that are within 4 points of top branch, everything below would be useless)

	Go back to "Do up to Nth..." as needed 

	Stop at time constraint, or depth constraint

	// Figure out how to search branches for best outcome assuming ideal play from both

	Return best branch

Evaluation Program // Honestly might be the more fun part
	Intakes board

	Sum up pieces according to material value
		Friendly King needs to be worth a MASSIVE amount
		Enemy King needs to be worth a lot 

		Rooks are less than double bishops
		Bishops are SLIGHTLY more than knights (in general, though well placed knights could counter this)
		Queens are less than double rooks

		// Might just steal stockfish's scoring system, as it almost perfectly represents my values on the subject
		// Pawn		1.98
		// Knight	8.17
		// Bishop	8.36
		// Rook		12.70
		// Queen	25.21


	(Bonus pawns based on distance from end, "passed pawns")

	(Penalize minor pieces that have no moves)

	// Theoretical stuff
	(King Safety)
	(Control of the Center)
	(Knight Outposts)
	(Pawn Structure)
	(Rook Open File)
