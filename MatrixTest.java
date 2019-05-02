// Matthew Halteh
// 5/14/18
// PA3

public class MatrixTest {
	public static void main(String[] args) {
		Matrix mm01 = new Matrix(3);

		mm01.changeEntry(3, 3, 9.0);
		mm01.changeEntry(3, 2, 8.0);
		//mm01.changeEntry(3, 1, 7.0);

		mm01.changeEntry(2, 2, 5.0);
		//mm01.changeEntry(2, 3, 6.0);
		mm01.changeEntry(2, 1, 4.0);

		//mm01.changeEntry(1, 3, 3.0);
		mm01.changeEntry(1, 2, 2.0);
		//mm01.changeEntry(1, 1, 1.0);

		//mm01.printSparse("mm01-Sparse");
		mm01.printFull("mm01");

		Matrix mm02 = mm01.copy();
		mm02.printFull("mm02");
		System.out.println("mm1 == m02: " + mm01.equals(mm02));

		Matrix mm03 = mm01.scalarMult(3.0);
		mm03.printFull("mm03");
		System.out.println("mm1 == m03: " + mm01.equals(mm03));

		Matrix mm04 = new Matrix(3);
		mm04.changeEntry(3, 1, 13.0);

		mm04.changeEntry(2, 1, 14.0);
		mm04.changeEntry(2, 2, 15.0);
		mm04.changeEntry(2, 3, 16.0);

		mm04.changeEntry(1, 2, 6.0);

		mm04.printFull("mm04");

		Matrix mm05 = mm01.add(mm04);
		mm05.printFull("mm05");

		Matrix mm06 = mm01.sub(mm04);
		mm06.printFull("mm06");

		Matrix mm07 = mm01.transpose();
		mm07.printFull("mm07");

		Matrix mm08 = mm05.transpose();
		mm08.printFull("mm08");

		Matrix mm09 = mm06.transpose();
		mm09.printFull("mm09");

		Matrix mm10 = mm01.mult(mm04);
		mm10.printFull("mm10");
	}
}
