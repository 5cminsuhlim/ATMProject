package XYZ_ATM;

import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.math.BigDecimal;

public class ATM_Runner{

    public static void main(String[] args){
        //NEED FILE OF USERS ASSOCIATED W/ CARDS
        Scanner atmInput = new Scanner (System.in);
        System.out.println("Initialising ATM...\n");
        ArrayList<Card> validCards = readCards(); // get validCards into a list by calling the readCards method
        ArrayList<User> userList = readUsers(validCards);
        LinkedHashMap<BigDecimal, Integer> balance = new LinkedHashMap<>(); // initialise balance

        String[] amounts = new String[] {"100.00", "50.00", "20.00", "10.00",
                "5.00", "2.00", "1.00", "0.50", "0.20", "0.10", "0.05"};
        for(int i = 0; i < amounts.length; i++) {
            System.out.println("How many $" + amounts[i] + " will be inserted?");
            int count = atmInput.nextInt();
            balance.put(new BigDecimal(amounts[i]), count);
        }

        ATM atm = new ATM(balance, validCards, userList, LocalDate.now(),0,"9746346416"); // create the ATM object
        boolean running = true;
        while(running) { // loops entire thing
            while(true) { // break when done with the atm/when the card is ejected, so it prompts for another card
                //GOING TO NEED TO CHECK WHETHER USER OR ADMIN HERE
                System.out.println("Are you an admin or user?" +
                        "Admin = 1"
                        "User = 2");
                int userType = Integer.parseInt(atmInput.next());

                //if user claims to be an admin
                if(userType == 1){
                    System.out.println("Please enter admin access code: ");
                    String enteredPin = atmInput.next();

                    //checking if valid pin
                    if(atm.isAdmin(enteredPin)){
                        //do admin stuff
                        boolean done = false;

                        while(!done){
                            System.out.println("Options: \n1: Check ATM Balance \n2: Add Funds \n3: Exit\n");
                            String option = atmInput.next();

                            switch (option) {
                                case "1" -> {
                                    atm.checkIndivBalance();
                                    System.out.println("Total Balance: " + atm.checkTotalBalance());
                                }
                                case "2" -> {
                                    LinkedHashMap<BigDecimal, Integer> adminInput = new LinkedHashMap<>();
                                    for (String amount : amounts) {
                                        System.out.println("How many $" + amount + " will be inserted?");
                                        int count = atmInput.nextInt();
                                        adminInput.put(new BigDecimal(amount), count);
                                    }
                                    atm.addFunds(adminInput);
                                }
                                case "3" -> {
                                    done = true;
                                    System.out.println("Exiting admin mode...");
                                }
                            }
                        }
                    }
                    else{
                        System.out.println("Incorrect pin. Access denied.");
                        break;
                    }
                }
                //if user is user
                else if(userType == 2){
                    //do user stuff
                    System.out.println("Please insert your card (Enter Card Number).\n");
                    String cardNumber = atmInput.next(); // cardNumber from user input
                    int cardIndex = atm.checkCardNumber(cardNumber); // gets the card object from the number
                    if(cardIndex == -1){ // card was not found
                        System.out.println("Card not linked to any account in the system. " +
                                "Please try again or use a different card.\n");
                        break; // prompt for card again
                    } else if(cardIndex == -11){
                        System.out.println("Card number must be 5 digits. Please try again.\n");
                        break;
                    }

                    Card card = atm.getCard(cardIndex); // gets the card object of the entered card
                    System.out.println("Please enter your PIN: \n");
                    String cardPin = atmInput.next(); // cardNumber from user input
                    int valid = atm.isValid(card, cardPin);
                    if(valid != 0){
                        break;
                    }

                    int userNum = atm.getUserFromCard(card);
                    User user = atm.getUserList().get(userNum);

                    boolean logged_in = true;
                    while(logged_in){
                        System.out.println("Options: \n1: Withdraw\n2: Deposit\n3: Check Balance\n4: Exit\n");
                        String option = atmInput.next();

                        switch (option) {
                            case "1" -> {
                                System.out.println("Please enter the amount you would like to withdraw. Enter 'cancel' " +
                                        "to cancel the transaction\n");
                                String withdrawAmount = atmInput.next();
                                switch (checkString(withdrawAmount)) {
                                    case 1: // withdraw amount is a number
                                        atm.withdraw(user, Double.valueOf(withdrawAmount));
                                        break;
                                    case -1: // cancel option
                                        break;
                                    default: // invalid input
                                        System.out.println("Invalid input.\n");
                                }
                            }
                            case "2" -> {
                                System.out.println("Are you sure you would like to deposit? Enter 'cancel' to exit the transaction: ");
                                String depositAmount = atmInput.next();
                                if (checkString(depositAmount) != -1) {
                                    BigDecimal received = BigDecimal.ZERO;
                                    LinkedHashMap<BigDecimal, Integer> userInput = new LinkedHashMap<>();
                                    for (String amount : amounts) {
                                        System.out.println("How many $" + amount + " will be inserted?");
                                        int count = atmInput.nextInt();
                                        userInput.put(new BigDecimal(amount), count);
                                        received = received.add(new BigDecimal(amount).multiply(BigDecimal.valueOf(count)));
                                    }

                                    if (received.doubleValue() == 0) {
                                        System.out.println("Please enter notes/coins if you wish to make a deposit.\n");
                                        break;
                                    }

                                    atm.deposit(user, userInput);
                                }
                            }
                            case "3" -> System.out.println("Your balance is $" + user.getBalance());
                            case "4" -> logged_in = false; // prompts for another card
                            default -> System.out.println("Invalid Input, please try again.\n");
                        }
                    }
                }
            }
        }

    }

    private static ArrayList<Card> readCards(){
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter card file name: "); // reads in card file name
        //GRADLE HAS RUINED THE BELOW THINGO V
        String filename = reader.nextLine(); // need to fix this so it gets the absolute path
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
        } catch(Exception e){
            System.out.println("Error reading card file. Please try again.\n"); // placeholder

        }
        return validCards; // returns the arraylist of cards to pass to an ATM object
    }

    private static ArrayList<User> readUsers(ArrayList<Card> cards){
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter user file name: "); // reads in userList file name
        String filename = reader.next(); // need to fix this so it gets the absolute path
        ArrayList<User> userList = new ArrayList<>();
        try{
            File file = new File(filename);
            Scanner input = new Scanner(file);
            int i = 1;
            while (input.hasNextLine()) { //reads all lines of the file
                String[] line = input.nextLine().split(",");
                // splits the line using regex to get rid of comma , each item is a variable for User
                User newUser = new User(line[0], line[1], Double.parseDouble(line[2]));
                if(i != 5){
                    newUser.addCard(cards.get(i-1));
                    newUser.addCard(cards.get(i));
                    userList.add(newUser);
                    i += 2;
                }
                newUser.addCard(cards.get(i-1));
                userList.add(newUser);

                // adds a user object into the userList
                // user has the format (userID, fullName, balance)
                // userList file has same format
            }
        } catch(Exception e){
            System.out.println("Error reading users file. Please try again.\n"); // placeholder
        }
        return userList; // returns the arraylist of cards to pass to an ATM object
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