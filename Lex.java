/*
Matthew Halteh
CruzID: mhalteh
PA1
*/
import java.util.*;
import java.io.*;

public class Lex {
    public static void main(String[] args) throws IOException {
        //lineCount scanner
        Scanner firstInput = new Scanner(new File(args[0]));
        //stringArray scanner
        Scanner secondInput = new Scanner(new File(args[0]));
        //Output File
        PrintWriter outputFile = new PrintWriter(new FileWriter(args[1]));

        //Declare List, lineCount, and words array
        List listSorting = new List();
        listSorting.append(0);
        int numLines = lineCount(firstInput);
        String[] words = stringArray(secondInput, numLines);

        //Declare variables for insertion sort
        String currentWord = "";
        int indexList;
        boolean checkPos;

        //Insertion sort algorithm used in List ADT
        for(int i = 1; i < words.length; i++) {
            //Find placements of indices in List ADT
            indexList = 0;
            listSorting.moveFront();
            currentWord = words[i];
            checkPos = false;

            while(checkPos == false && indexList < i) {
                //Compares current word of unsorted words array to current word at List cursor
                //If current word of unsorted words array is before List cursor, insert before cursor, then exit while loop
                if(currentWord.compareTo(words[listSorting.get()]) < 0) {
                    listSorting.insertBefore(i);
                    checkPos = true;
                }

                //Moves List cursor and updates indexList
                else {
                    listSorting.moveNext();
                    indexList++;
                }
            }

            //Insert index of current word in words array in the back of the list
            if(checkPos == false) {
                listSorting.append(i);
            }
        }

        //Places list cursor towards front of list to print sorted array according to list indices
        listSorting.moveFront();
        for(int j = 0; j < words.length; j++) {
            outputFile.println(words[listSorting.get()]);
            listSorting.moveNext();
        }

        firstInput.close();
        secondInput.close();
        outputFile.close();
    }

    //lineCount() method counts total numLines in Scanner file
    //Input: Scanner, presumably the "in.txt" file
    //Output: (int) containing total amount of numLines
    public static int lineCount(Scanner in) {
        int lineCount = 0;

        while(in.hasNextLine()) {
            in.nextLine();
            lineCount++;
        }

        return lineCount;
    }

    //stringArray() method stores every line into their appropriate index in a String array
    //Input: Scanner containing text on each line and an int of total amount of numLines
    //Output: words[] containing each line stored in each index of array
    public static String[] stringArray(Scanner in, int lineCount) {
        String[] words = new String[lineCount];
        int i = 0;

        while(in.hasNextLine()) {
            words[i] = in.nextLine();
            i++;
        }

        return words;
    }

}
