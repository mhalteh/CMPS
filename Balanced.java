// Matthew Halteh
//mhalteh
//11/05/17



//This program checks to see if lines of text are valid with brackets


import java.util.*;
import java.io.*;


public class Balanced {


    static LinkedList topHead = null;

    public static void main(String[] args) throws IOException {

        Scanner inputFile = new Scanner(new File(args[0]));
        PrintWriter outputFile = new PrintWriter(new FileWriter(args[1]));

        while (inputFile.hasNextLine()) {
            LinkedList topInList = null;
            String inputLine = inputFile.nextLine();
            char[] inputLineChar = inputLine.toCharArray();

            int itemsInList = 0;

            for (int i = 0; i < inputLineChar.length; i++) {
                if (inputLineChar[i] == '(' || inputLineChar[i] == '[' || inputLineChar[i] == '{' || inputLineChar[i] == '<') {
                    push(inputLineChar[i]);
                    itemsInList = itemsInList + 1;
                }
                else if (inputLineChar[i] == ')' || inputLineChar[i] == ']' || inputLineChar[i] == '}' || inputLineChar[i] == '>') {
                    topInList = peek();
                    if (topInList == null) {
                        itemsInList = itemsInList - 1;
                    }
                    else if ((topHead.symbols == '(' && inputLineChar[i] == ')')) {
                        pop();
                    }
                    else if ((topHead.symbols == '[' && inputLineChar[i] == ']')) {
                        pop();
                    }
                    else if ((topHead.symbols == '{' && inputLineChar[i] == '}')) {
                        pop();
                    }
                    else if ((topHead.symbols == '<' && inputLineChar[i] == '>')) {
                        pop();
                    }
                    itemsInList = itemsInList - 1;
                }
            }


            if (itemsInList == 0 && checkListEmpty()) {
                outputFile.println("Y");
            }
            else {
                outputFile.println("N");
            }
            listEmpty();

        }
        outputFile.close();
    }

    public static boolean checkListEmpty() {
        if (topHead == null) {
            return true;
        }
        else {
            return false;
        }
    }

    public static LinkedList peek() {
        if (topHead == null) {
            return null;
        }
        return topHead;
    }


    public static void pop() {
        if (!checkListEmpty()) {
            LinkedList checker = null;
            checker = topHead.next;
            topHead = checker;
        }
    }

    public static void push(char bracket) {
        LinkedList nextBracket = new LinkedList(bracket);
        nextBracket.next = topHead;
        topHead = nextBracket;
    }

    public static void listEmpty() {
        topHead = null;
    }

}