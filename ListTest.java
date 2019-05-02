// Matthew Halteh
// 5/14/18
// PA3

public class ListTest {
	public static void main(String[] args) {
		List ll01 = new List();
		List ll02 = new List();

		ll01.append(5);
		ll01.append(9);
		ll01.prepend(1);
		ll01.append(3);
		ll01.append(6);
		ll01.append(12);
		ll01.moveFront();
		ll01.print("ll01", "	");

		ll01.delete();
		ll01.print("ll01", "	");

		ll01.moveFront();;
		ll01.moveNext();
		ll01.moveNext();
		ll01.delete();
		ll01.print("ll01", "	");

		ll02.append(5);
		ll02.append(9);
		ll02.append(6);
		ll02.append(12);
		ll01.print("ll02", "	");
		System.out.println("ll01 == ll02: " + ll01.equals(ll02));

		ll02.deleteBack();
		ll02.print("ll02", "	");
		System.out.println("ll01 == ll02: " + ll01.equals(ll02));
	}
}
