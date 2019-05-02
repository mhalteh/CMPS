// Matthew Halteh
// 5/14/18
// PA3

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class Matrix {
	private static final NumberFormat formatter	= new DecimalFormat("#.0");
	private static final String	SPACER = "      ";
	private static final String	NUMBER_FORMAT = "%6s";

	private class Entry {
		private int colNum;
		private double value;

		private Entry(int colNum, double value) {
			this.colNum = colNum;
			this.value = value;
		}

		public boolean equals(Object compareTo) {
			if (! (compareTo instanceof Entry)) {
				return false;
			}

			return (this.colNum == ((Entry)compareTo).colNum && this.value == ((Entry)compareTo).value);
		}

		public String toString() {
			return "(" + this.colNum + ", " + this.value + ")";
		}
	}

	private List[] rows;

	Matrix(int size) {
		this.rows = new List[size];
		makeZero();
	}

	public int getSize() {
		return rows.length;
	}

	public int getNNZ() {
		int nnz = 0;
		for (int rr=0; rr<this.rows.length; rr++) {
			nnz += this.rows[rr].length();
		}

		return nnz;
	}

	private List getRow(int rowNum) {
		return this.rows[rowNum];
	}

	public void makeZero() {
		for (int rr=0; rr<this.rows.length; rr++) {
			this.rows[rr] = new List();
		}
	}

	public Matrix copy() {
		Matrix newMatrix = new Matrix(this.rows.length);
		for (int rr=0; rr<rows.length; rr++) {
			newMatrix.rows[rr] = this.rows[rr].copy();
		}

		return newMatrix;
	}

	public void changeEntry(int rowNum, int colNum, double value) {
		if (rowNum > rows.length || colNum > rows.length) {
			return;
		}

		Entry newEntry = new Entry(colNum, value);

		List row = this.rows[rowNum-1];
		row.moveFront();

		Entry entry;
		do {
			entry = (Entry)row.get();
			if (entry == null) {
				if (value != 0.0)
					row.append(newEntry);
			}
			else
			if (entry.colNum == newEntry.colNum) {
				if (value == 0.0) {
					row.delete();
				}
				else {
					row.replaceCurrent(newEntry);
				}
				entry = null;
			}
			else
			if (entry.colNum > newEntry.colNum) {
				if (value != 0.0) {
					row.insertBefore(newEntry);
				}
				entry = null;
			}
			else {
				row.moveNext();
			}
		}
		while (entry != null);
	}

	public Matrix scalarMult(double scalar) {
		Matrix newMatrix = new Matrix(this.rows.length);
		for (int rr=0; rr<this.rows.length; rr++) {
			List thisRow = this.rows[rr];
			thisRow.moveFront();
			Entry entry = (Entry)thisRow.get();

			List newRow = newMatrix.rows[rr];
			while (entry != null) {
				newRow.append(new Entry(entry.colNum, scalar*entry.value));
				thisRow.moveNext();
				entry = (Entry)thisRow.get();
			}
		}

		return newMatrix;
	}

	public Matrix add(Matrix MM) {
		if (MM == this)
			return this.scalarMult(2.0);

		return addOrSubstract(MM, 1.0);
	}

	public Matrix sub(Matrix MM) {
		if (MM == this) {
			return new Matrix(this.rows.length);
		}

		return addOrSubstract(MM, -1.0);
	}

	private Matrix addOrSubstract(Matrix MM, double factor) {
		if (MM == this) {
			MM = this.copy();
		}

		Matrix newMatrix = new Matrix(this.rows.length);
		for (int rr=0; rr<this.rows.length; rr++) {
			List row1 = this.rows[rr];
			row1.moveFront();
			Entry entry1 = (Entry)row1.get();

			List row2 = MM.rows[rr];
			row2.moveFront();
			Entry entry2 = (Entry)row2.get();

			List newRow = newMatrix.rows[rr];

			boolean flag = true;
			double newVal;
			while (flag) {
				if (entry1 == null) {
					while (entry2 != null) {
						newRow.append(new Entry(entry2.colNum, factor*entry2.value));
						row2.moveNext();
						entry2 = (Entry)row2.get();
					}
					flag = false;
				}
				else
				if (entry2 == null) {
					while (entry1 != null) {
						newRow.append(entry1);
						row1.moveNext();
						entry1 = (Entry)row1.get();
					}
					flag = false;
				}
				else {
					if (entry1.colNum == entry2.colNum) {
						newVal = entry1.value + factor*entry2.value;
						if (newVal != 0.0) {
							newRow.append(new Entry(entry1.colNum, newVal));
						}
						row1.moveNext();
						entry1 = (Entry)row1.get();
						row2.moveNext();
						entry2 = (Entry)row2.get();
					}
					else
					if (entry1.colNum < entry2.colNum) {
						newRow.append(new Entry(entry1.colNum, entry1.value));
						row1.moveNext();
						entry1 = (Entry)row1.get();
					}
					else {
						newRow.append(new Entry(entry2.colNum, factor*entry2.value));
						row2.moveNext();
						entry2 = (Entry)row2.get();
					}
				}
			}
		}

		return newMatrix;
	}

	public Matrix transpose() {
		Matrix newMatrix = new Matrix(this.rows.length);
		for (int rr=0; rr<this.rows.length; rr++) {
			List row = this.rows[rr];
			row.moveFront();
			Entry entry = (Entry)row.get();
			while (entry != null) {
				List newRow = newMatrix.rows[entry.colNum-1];
				newRow.append(new Entry(rr+1, entry.value));
				row.moveNext();
				entry = (Entry)row.get();
			}
		}

		return newMatrix;
	}

	public Matrix mult(Matrix MM) {
		if (MM == this) {
			MM = this.copy();
		}

		Matrix newMatrix = new Matrix(this.rows.length);
		Matrix MMtransposed = MM.transpose();
		for (int rr=0; rr<this.rows.length; rr++) {
			List newRow = newMatrix.getRow(rr);
			for (int cc=0; cc<this.rows.length; cc++) {
				List row1 = this.rows[rr];
				if (row1.length() == 0) continue;
				row1.moveFront();
				Entry entry1 = (Entry) row1.get();

				List row2 = MMtransposed.getRow(cc);
				if (row2.length() == 0) continue;
				row2.moveFront();
				Entry entry2 = (Entry) row2.get();

				double sum = 0.0;
				boolean flag = true;
				while (flag) {
					if (entry1.colNum == entry2.colNum) {
						sum += entry1.value * entry2.value;
						row1.moveNext();
						entry1 = (Entry)row1.get();
						row2.moveNext();
						entry2 = (Entry)row2.get();
						if (entry1 == null || entry2 == null) {
							flag = false;
						}
					}
					else
					if (entry1.colNum < entry2.colNum) {
						row1.moveNext();
						entry1 = (Entry)row1.get();
						if (entry1 == null) {
							flag = false;
						}
					}
					else {
						row2.moveNext();
						entry2 = (Entry)row2.get();
						if (entry2 == null) {
							flag = false;
						}
					}
				}
				if (sum != 0.0) {
					newRow.append(new Entry(cc+1, sum));
				}
			}
		}

		return newMatrix;
	}

	public void printSparse(String listName) {
		System.out.println(listName);
		System.out.println("--------------------------------------");

		for (int rr=0; rr<this.rows.length; rr++) {
			List row = this.rows[rr];
			if (row.length() == 0)
				continue;
			row.moveFront();
			Entry entry = (Entry)row.get();
			System.out.print((rr+1) + ":");
			while (entry != null) {
				System.out.print(" " + entry.toString());
				row.moveNext();
				entry = (Entry)row.get();
			}
			System.out.println();
		}

		System.out.println("==============================================================================");
	}

	public void printFull(String listName) {
		System.out.println(listName + " -- " + getNNZ());
		System.out.println("--------------------------------------");

		for (int rr=0; rr<rows.length; rr++) {
			List row = this.rows[rr];
			if (row.length() == 0) {
				continue;
			}
			System.out.print((rr+1) + ":");
			row.moveFront();
			Entry entry = (Entry)row.get();

			int cc = 1;
			if (entry.colNum == cc) {
				System.out.print(" " + String.format(NUMBER_FORMAT, formatter.format(entry.value)));
				row.moveNext();
				entry = (Entry)row.get();
			}
			else {
				System.out.print(" " + SPACER);
			}
			for (; entry!=null; cc++) {
				if (entry.colNum == cc+1) {
					System.out.print("  " + String.format(NUMBER_FORMAT, formatter.format(entry.value)));
					row.moveNext();
					entry = (Entry)row.get();
				}
				else {
					System.out.print("  " + SPACER);
				}
			}
			System.out.println();
		}

		System.out.println("==============================================================================");
	}

	public boolean equals(Object compareTo) {
		if (! (compareTo instanceof Matrix)) {
			return false;
		}

		if (this.rows.length != ((Matrix)compareTo).getSize()) {
			return false;
		}

		for (int rr=0; rr<this.rows.length; rr++) {
			if (! this.rows[rr].equals(((Matrix)compareTo).getRow(rr))) {
				return false;
			}
		}

		return true;
	}

	public String toString() {
		if (this.rows == null) {
			return "";
		}

		String str = "";
		for (int rr=0; rr<this.rows.length; rr++) {
			if (rows[rr].length() != 0) {
				str += (rr+1) + ": " + this.rows[rr].toString() + "\r\n";
			}
		}

		return str;
	}
}
