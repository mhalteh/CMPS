// Matthew Halteh
// 5/14/18
// PA3

import java.util.*;
import java.io.*;

public class Sparse {
    public static void main(String[] args) throws IOException
    {
        Scanner input = new Scanner(new File(args[0]));
        PrintWriter output = new PrintWriter(new FileWriter(args[1]));

        int sz = 0;
        int a = 0;
        int b = 0;




        if (!(input.hasNextInt())){
            System.exit(0);
        }

        else {
            Matrix A = null;
            Matrix B = null;
            while (input.hasNextInt()) {
                sz = input.nextInt();
                a = input.nextInt();
                b = input.nextInt();

                A = new Matrix(sz);
                B = new Matrix(sz);

                for (int i = 0; i < a; i++) {
                    int rowA = input.nextInt();
                    int colA = input.nextInt();
                    double valA = input.nextDouble();
                    A.changeEntry(rowA, colA, valA);
                }

                for (int j = 0; j < b; j++) {
                    int rowB = input.nextInt();
                    int colB = input.nextInt();
                    double valB = input.nextDouble();
                    B.changeEntry(rowB, colB, valB);
                }
            }

            output.println("A has " + A.getNNZ() + " non-zero entries:");
            output.println(A);

            output.println("B has " + B.getNNZ() + " non-zero entries:");
            output.println(B);

            Matrix scalarMtx = A.scalarMult(1.5);
            output.println("(1.5)*A =\n"+scalarMtx);

            Matrix addMtx = A.add(B);
            output.println("A+B =\n"+addMtx);

            Matrix addMtx2 = A.add(A);
            output.println("A+A =\n"+addMtx2);

            Matrix subMtx = B.sub(A);
            output.println("B-A =\n"+subMtx);

            Matrix subMtx2 = A.sub(A);
            output.println("A-A =\n"+subMtx2);

            Matrix transMtx = A.transpose();
            output.println("Transpose(A) =\n"+transMtx);

            Matrix multMtx = A.mult(B);
            output.println("A*B =\n"+multMtx);

            Matrix multMtx2 = B.mult(B);
            output.println("B*B =\n"+multMtx2);

            input.close();
            output.close();
        }
    }
}
