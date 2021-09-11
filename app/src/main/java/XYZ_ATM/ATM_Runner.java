package XYZ_ATM;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.math.BigDecimal;

public class ATM_Runner{


    public static void main(String[] args){
        System.out.println("Initialising ATM...\n");
        ArrayList<Card> validCards = readCards(); // get validcards into a list by calling the readCards method
        HashMap<BigDecimal, Integer> balance = new HashMap<>(); // initialise balance
        System.out.println("Enter the number of note/coins in the form: 100,50,20,10,5,2,1,0.50,0.20,0.10,0.05");
        Scanner balanceReader = new Scanner(System.in);
        String[] balanceIn = balanceReader.next().split(","); // reads input and splits into an array
        balanceReader.close();
        balance.put(new BigDecimal(100), Integer.parseInt(balanceIn[0]));
        balance.put(new BigDecimal(50), Integer.parseInt(balanceIn[1]));
        balance.put(new BigDecimal(20), Integer.parseInt(balanceIn[2]));
        balance.put(new BigDecimal(10), Integer.parseInt(balanceIn[3]));
        balance.put(new BigDecimal(5), Integer.parseInt(balanceIn[4]));
        balance.put(new BigDecimal(2), Integer.parseInt(balanceIn[5]));
        balance.put(new BigDecimal(1), Integer.parseInt(balanceIn[6]));
        balance.put(new BigDecimal("0.50"), Integer.parseInt(balanceIn[7]));
        balance.put(new BigDecimal("0.20"), Integer.parseInt(balanceIn[8]));
        balance.put(new BigDecimal("0.10"), Integer.parseInt(balanceIn[9]));
        balance.put(new BigDecimal("0.05"), Integer.parseInt(balanceIn[10])); // add values to balance

        ATM atm = new ATM(balance, validCards, LocalDate.now()); // create the ATM object
        boolean running = true;
        while(running) { // loops entire thing
            while(true) { // break when done with the atm/when the card is ejected so it prompts for another card
                Scanner atmInput = new Scanner(System.in); // create scanner to get user input
                System.out.println("Welcome to XYZ ATM!\n\nPlease insert your card (Enter Card Number).\n");
                String cardNumber = atmInput.next(); // cardnumber from user input
                int cardIndex = numberToCard(cardNumber, atm); // gets the card object from the number
                if(cardIndex == -1){ // card was not found
                    System.out.println("Card not linked to any account in the system. " +
                            "Please try again or use a different card.");
                    atmInput.close();
                    break; // prompt for card again
                }

                Card card = atm.getCard(cardIndex); // gets the card object of the entered card number

                System.out.println("Options: \n1: Withdraw\n2: Deposit\n3: Check Balance\n4: Exit");
                String option = atmInput.next();

                switch (option) {
                    case "1":

                    case "2":

                    case "3":

                    case "4":
                        atmInput.close();
                        break;
                    default:
                        System.out.println("Invalid Input, please try again.");
                        break;
                }
            }
        }

    }

    private static ArrayList<Card> readCards(){
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter card file name: "); // reads in card file name
        String filename = reader.next(); // need to fix this so it gets the absolute path
        reader.close();
        ArrayList<Card> validCards = new ArrayList<>();
        try{
            File file = new File(filename);
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) { //reads all lines of the file
                String[] line = input.nextLine().split(",");
                // splits the line using regex to get rid of comma and space, each item is a variable for Card
                boolean stolen = "1".equals(line[3]);
                validCards.add(new Card(line[0], line[4], line[1], line[2], "placeholder", stolen));
                // adds a card object into the validCards list
                // card has format (card_number, pin, start_date, expiry_date, UID, stolen
                // card list has format cardNo,dd/mm/yyyy/,mm/yyyy,lostOrStolenStatus,pin
            }
            input.close();
        } catch(Exception e){
            System.out.println("error"); // placeholder
        }
        return validCards; // returns the arraylist of cards to pass to an ATM object
    }

    private static int numberToCard(String number, ATM atm) {
        int i = 0;
        for(Card card : atm.getCardList()){ // iterate through cards within the atm card list
            if(card.getCard_number().equals(number)){
                return i; // returns the card index
            } else{
                i++;
            }
        } return -1; // error meaning that a card with this number was not found.
    }
}
