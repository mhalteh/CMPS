/*
		Matthew Halteh
		mhalteh@ucsc.edu
		CRUZ ID: mhalteh
		Due: 10/15/17
		FILE NAME: NQueens.java

		This program is a solution to the N Queens problem. It takes in a position
		of one queen and gives the positions of the rest of the queens.
*/

import java.util.*;
import java.io.*;

public class NQueens {

	boolean board[][];
	int size;
	short result = 0;

	public NQueens(int size) {
		if (size < 4)
			size = 4;

		this.size = size;
		this.board = new boolean[this.size][this.size];
		for (int cc=0; cc<this.size; cc++) {
			for (int rr=0; rr<this.size; rr++) {
				this.board[cc][rr] = false;
			}
		}
	}

	public NQueens(int size, int col, int row) {
		this(size);
		this.board[col-1][row-1] = true;
	}

	public void solve() {
		if (tryColumn(0)) {
			this.result = 1;
		}
		else {
			this.result = 2;
		}
	}

	public void printResult(PrintWriter out) {
		if (this.result == 0) {
			solve();
		}

		if (this.result == 1) {
			printBoard(out);
		}

		else {
			out.println("No solution");
		}
	}

	public void printBoard(PrintWriter out) {

		for (int cc=0; cc<this.size; cc++) {
			for (int rr=0; rr<this.size; rr++) {
				if (this.board[cc][rr]) {
					out.print((cc+1)+" "+(rr+1) + " ");
				}
			}
		}
		out.println();
	}

	private boolean tryColumn(int col) {
		if (col == this.size)
			return true;

		if (columnTaken(col)) {
			return tryColumn(col+1);
		}

		int rr;
		for (rr=0; rr<this.size; rr++) {
			if (isValidSquare(col, rr)) {
				this.board[col][rr] = true;
				if (tryColumn(col+1)) {
					return true;
				}
				this.board[col][rr] = false;
			}

		}

		return false;
	}


	private boolean columnTaken(int col) {
		for (int rr=0; rr<this.size; rr++) {
			if (this.board[col][rr]) {
				return true;
			}

		}

		return false;
	}


	private boolean isValidSquare(int col, int row) {
		for (int cc=0; cc<this.size; cc++) {
			for (int rr=0; rr<this.size; rr++) {
				if (cc == col && rr == row) {
					continue;
				}

				if (! this.board[cc][rr]) {
					continue;
				}

				if (cc == col) {
					return false;
				}

				if (rr == row) {
					return false;
				}

				if (Math.abs(cc-col) == Math.abs(rr-row)) {
					return false;
				}

			}

		}

		return true;
	}

	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(new File(args[0]));
		String line = "";
		String[] token = null;

		PrintWriter out = new PrintWriter(args[1]);

		while(in.hasNextLine()) {
			line = in.nextLine() + " ";
			token = line.split("\\s+");
			int q = Integer.parseInt(token[0]);
			int r = Integer.parseInt(token[1]);
			int s = Integer.parseInt(token[2]);

			NQueens nq = new NQueens(q, r, s);

			nq.printResult(out);

		}

		out.close();

	}

}
