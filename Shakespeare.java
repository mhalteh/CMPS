// Matthew Halteh
// mhalteh
// 12/05/17
// This program allows the user to check the frequency of words, as well the word length
//      of Shakespeare's texts, with the use of Hashtables

import java.util.*;
import java.io.*;

public class Shakespeare {
    public static Hashtable<String, Integer> shakeText = new Hashtable<String, Integer>();

    public static BufferedWriter outputFile;

    public static void main(String[] args) throws IOException {
        Scanner shakeFile = new Scanner(new File("shakespeare.txt"));
        Scanner inputFile = new Scanner(new File("test-input.txt"));

        outputFile = new BufferedWriter(new FileWriter("analysis.txt"));
        createHash(shakeFile);
        readInputFile(inputFile);

        outputFile.close();
    }

    public static void createHash(Scanner shakeFile) {
        while(shakeFile.hasNextLine()) {
            while(shakeFile.hasNext()) {
                String nextTextWord = shakeFile.next();

                if(nextTextWord == nextTextWord.toUpperCase()) {
                    continue;
                }

                String lowerCaseWord1 = nextTextWord.toLowerCase();
                String lowerCaseWord2 = lowerCaseWord1.replaceAll("\\.", "");
                String lowerCaseWord3 = lowerCaseWord2.replaceAll("\\,", "");
                String lowerCaseWord4 = lowerCaseWord3.replaceAll("\\?", "");
                String lowerCaseWord5 = lowerCaseWord4.replaceAll("\\!", "");
                String lowerCaseWord6 = lowerCaseWord5.replaceAll(";", "");
                String lowerCaseWord7 = lowerCaseWord6.replaceAll(":", "");
                String lowerCaseWord8 = lowerCaseWord7.replaceAll("\\[", "");
                String lowerCaseWord9 = lowerCaseWord8.replaceAll("\\]", "");
                String lowerCaseWord10 = lowerCaseWord9.replaceAll("\\(", "");
                String lastWordInText = lowerCaseWord10.replaceAll("\\)", "");

                if(shakeText.containsKey(lastWordInText)) {
                    int count = shakeText.get(lastWordInText);
                    shakeText.put(lastWordInText, count + 1);
                }

                else {
                    shakeText.put(lastWordInText, 1);
                }
            }

            if(shakeFile.nextLine() == null) {
                shakeFile.close();
            }
        }
        shakeFile.close();
    }

    public static void readInputFile(Scanner inputFile) {
        while(inputFile.hasNextLine()) {
            String entry = inputFile.nextLine();

            if(entry.contains(" ")) {
                String trimEntry = entry.trim();
                String[] splitEntry = trimEntry.split("\\s+");
                int length = Integer.parseInt(splitEntry[0]);
                int frequency = Integer.parseInt(splitEntry[1]);

                findQuery(length, frequency);
            }

            else {
                keySearch(entry);
            }
        }
    }

    public static void keySearch(String key) {
        if(shakeText.get(key) == null) {
            writeStringTo(0);
        }

        else {
            writeStringTo(shakeText.get(key));
        }
    }

    public static void findQuery(int length, int frequency) {
        @SuppressWarnings("unchecked")
        Hashtable<String, Integer> clone = (Hashtable<String, Integer>)shakeText.clone();

        int counter = 0;
        while(counter < frequency) {
            Set<String> key  = clone.keySet();
            Iterator<String> setKey = key.iterator();

            int maxVal = 0;
            String maxKey = "";

            while(setKey.hasNext()) {
                String nextItem = setKey.next();

                if(nextItem.length() == length && clone.get(nextItem) > maxVal) {
                    maxVal = clone.get(nextItem);
                    maxKey = nextItem;
                }
            }

            if(maxKey.equals("")) {
                writeStringTo("Not Found ");
                counter = counter + 1;
            }

            else {
                writeStringTo(maxKey + " ");

                clone.remove(maxKey);
                counter = counter + 1;
            }
        }
        writeToLine();
    }

    public static void writeStringTo(Integer strWrite) {
        try {
            outputFile.write(strWrite + "\n");
        }
        catch (IOException e) {
            System.out.println("Error printing");
        }
    }

    public static void writeStringTo(String strWrite) {
        try {
            outputFile.write(strWrite);
        }
        catch (IOException e) {
            System.out.println("Error printing");
        }
    }

    public static void writeToLine() {
        try {
            outputFile.write("\n");
        }
        catch (IOException e) {
            System.out.println("Error printing");
        }
    }

    public static void traverseHT() {
        Hashtable<String, Integer> clone = shakeText;

        Set<String> key  = clone.keySet();
        Iterator<String> setKey = key.iterator();

        while(setKey.hasNext()) {
            String itemInKey = setKey.next();
            System.out.println("||" + itemInKey + " " + clone.get(itemInKey) + "|| ");
        }
    }
}
