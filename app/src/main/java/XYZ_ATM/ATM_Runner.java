package XYZ_ATM;

import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;

public class ATM_Runner{

    public static void main(String[] args){
        System.out.println("starting "); // placeholder
    }

    private ArrayList<Card> readCards(){
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter file name: "); // reads in card file name
        String filename = reader.next(); // need to fix this so it gets the absolute path
        reader.close();
        ArrayList<Card> validCards = new ArrayList<>();
        try{
            File file = new File(filename);
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) { //reads all lines of the file
                String lines = input.nextLine();
                String[] line = lines.split(",[ ]*");
                // splits the line using regex to get rid of comma and space, each item is a variable for Card
                validCards.add(new Card(line[0], line[1], line[2], line[3], line[4], Boolean.parseBoolean(line[5])));
                // adds a card object into the validCards list
            }
            input.close();
        } catch(Exception e){
            System.out.println("error"); // placeholder
        }
        return validCards; // returns the arraylist of cards to pass to an ATM object
    }
}
