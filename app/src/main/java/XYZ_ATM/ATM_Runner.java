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
        ArrayList<Card> validCards = readCards(); // get validCards into a list by calling the readCards method
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
            while(true) { // break when done with the atm/when the card is ejected, so it prompts for another card
                Scanner atmInput = new Scanner(System.in); // create scanner to get user input
                System.out.println("Please insert your card (Enter Card Number).\n");
                String cardNumber = atmInput.next(); // cardNumber from user input
                int cardIndex = atm.checkCardNumber(cardNumber); // gets the card object from the number
                if(cardIndex == -1){ // card was not found
                    System.out.println("Card not linked to any account in the system. " +
                            "Please try again or use a different card.\n");
                    atmInput.close();
                    break; // prompt for card again
                } else if(cardIndex == -11){
                    System.out.println("Card number must be 5 digits. Please try again.\n");
                }

                Card card = atm.getCard(cardIndex); // gets the card object of the entered card number
                boolean logged_in = true;
                while(logged_in){
                    System.out.println("Options: \n1: Withdraw\n2: Deposit\n3: Check Balance\n4: Exit\n");
                    String option = atmInput.next();

                    switch (option) {
                        case "1":
                            System.out.println("Please enter the amount you would like to withdraw. Enter 'cancel'" +
                                    "to cancel the transaction\n");
                            String withdrawAmount = atmInput.next();
                            switch(checkString(withdrawAmount)) {
                                case 1: // deposit amount is a number
                                    //atm.deposit();
                                case -1: // cancel option
                                    break;
                                default: // invalid input
                                    System.out.println("Invalid input.\n");
                            }
                        case "2":
                            System.out.println("Please enter the amount you would like to deposit. Enter 'cancel'" +
                                    "to cancel the transaction\n");
                            String depositAmount = atmInput.next();
                            switch(checkString(depositAmount)){
                                case 1: // deposit amount is a number
                                    //atm.deposit();
                                case -1: // cancel option
                                    break;
                                default: // invalid input
                                    System.out.println("Invalid input.\n");
                            }
                        case "3":
                            System.out.println("Your balance is \n");
                        case "4":
                            atmInput.close();
                            logged_in = false; // prompts for another card
                        default:
                            System.out.println("Invalid Input, please try again.\n");
                    }
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
                // card has the format (card_number, pin, start_date, expiry_date, UID, stolen
                // card list has the format cardNo,dd/mm/yyyy/,mm/yyyy,lostOrStolenStatus,pin
            }
            input.close();
        } catch(Exception e){
            System.out.println("error"); // placeholder
        }
        return validCards; // returns the arraylist of cards to pass to an ATM object
    }

    private static int checkString(String str){
        if(str.equals("cancel")){
            return -1;
        }
        try {
            Double.parseDouble(str);
            return 1;
        } catch (NumberFormatException e) {
            return -2;
        }
    }
}
