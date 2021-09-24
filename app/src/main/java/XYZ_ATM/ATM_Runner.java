package XYZ_ATM;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.time.LocalDate;
import java.math.BigDecimal;

public class ATM_Runner{

    public static void main(String[] args){
        //NEED FILE OF USERS ASSOCIATED W/ CARDS
        Scanner atmInput = new Scanner(System.in);
        System.out.println("Initialising ATM...");

        boolean cardSuccess = false;
        ArrayList<Card> validCards = new ArrayList<>();

        while (!cardSuccess) {
            Scanner reader = new Scanner(System.in);
            System.out.println("Enter card file name: "); // reads in card file name
            String filename = reader.nextLine(); // need to fix this so it gets the absolute path

            validCards = Card.readCards(filename);
            cardSuccess = true;
        }

        boolean userSuccess = false;
        ArrayList<User> userList = new ArrayList<>();

        while (!userSuccess) {
            Scanner reader = new Scanner(System.in);
            System.out.println("Enter user file name: "); // reads in userList file name
            String filename = reader.next(); // need to fix this so it gets the absolute path

            userList = ATM.readUsers(filename, validCards);
            userSuccess = true;
        }

        LinkedHashMap<BigDecimal, Integer> balance = new LinkedHashMap<>(); // initialise balance

        String[] amounts = new String[] {"100.00", "50.00", "20.00", "10.00",
                "5.00", "2.00", "1.00", "0.50", "0.20", "0.10", "0.05"};
        for(String amount : amounts) {
            System.out.println("How many $" + amount + " will be inserted?");
            int count = atmInput.nextInt();
            balance.put(new BigDecimal(amount), count);
        }

        ATM atm = new ATM(balance, validCards, userList, LocalDate.now()); // create the ATM object
        boolean running = true;
        boolean prompting = true;
        while(running) { // loops entire thing
            while(prompting) { // break when done with the atm/when the card is ejected, so it prompts for another card
                //GOING TO NEED TO CHECK WHETHER USER OR ADMIN HERE
                System.out.println("Are you an admin or user?" +
                        "\nAdmin = 1" +
                        "\nUser = 2");
                int userType = Integer.parseInt(atmInput.next());

                //if user claims to be an admin
                if (userType == 1) {
                    System.out.println("Please enter admin access code: ");
                    String enteredPin = atmInput.next();

                    //checking if valid pin
                    if (atm.isAdmin(enteredPin)) {
                        //do admin stuff
                        boolean done = false;

                        while (!done) {
                            System.out.println("Options: \n1: Check ATM Balance \n2: Add Funds \n3: Exit \n4: Shut Down ATM\n");
                            String option = atmInput.next();

                            switch (option) {
                                case "1" -> {
                                    System.out.println(atm.checkIndivBalance());
                                    System.out.println("Total Balance: $" + String.format("%,.2f", atm.checkTotalBalance()));
                                    //System.out.println("Total Balance: " + atm.checkTotalBalance());
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
                                case "4" -> {
                                    done = true;
                                    prompting = false;
                                    running = false;
                                    System.out.println("Shutting down...");
                                }
                            }
                        }
                    } else {
                        System.out.println("Incorrect pin. Access denied.");
                        break;
                    }
                }
                //if user is user
                else if (userType == 2) {
                    //do user stuff
                    System.out.println("Please insert your card (Enter Card Number).\n");
                    String cardNumber = atmInput.next(); // cardNumber from user input
                    int cardIndex = atm.checkCardNumber(cardNumber); // gets the card object from the number
                    if (cardIndex == -1) { // card was not found
                        System.out.println("Card not linked to any account in the system. " +
                                "Please try again or use a different card.\n");
                        break; // prompt for card again
                    } else if (cardIndex == -11) {
                        System.out.println("Card number must be 5 digits. Please try again.\n");
                        break;
                    }

                    Card card = atm.getCard(cardIndex); // gets the card object of the entered card

                    if(atm.isValid(card) != 0){
                        break;
                    }

                    System.out.println("Please enter your PIN: \n");
                    int i = 0;
                    boolean valid = false; //Initialised as invalid
                    while(i<3){ //Check pin 3 times
                        String cardPin = atmInput.next(); // cardPin from user input
                        valid = atm.checkPin(card, cardPin);
                        if(!valid){
                            i++;
                            if(3-i>1){
                                System.out.println("You have " + (3-i) + " attempts left. Please try again.");
                            }
                            else if(3-i==1){
                                System.out.println("You have " + (3-i) + " attempt left. Please try again.");
                            }
                        }
                        else{
                            break;
                        }
                    }
                    if (!valid) {
                        card.setBlocked(true);
                        System.out.println("Unfortunately this card has been blocked.");
                        break;
                    }

                    int userNum = atm.getUserFromCard(card);
                    User user = atm.getUserList().get(userNum);

                    boolean logged_in = true;
                    while (logged_in) {
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
                                BigDecimal received = BigDecimal.ZERO;
                                LinkedHashMap<BigDecimal, Integer> userInput = new LinkedHashMap<>();
                                for (String amount : amounts) {
                                    if (Double.parseDouble(amount) < 5) {
                                        break;
                                    }

                                    System.out.println("How many $" + amount + " will be inserted?");
                                    int count = atmInput.nextInt();
                                    userInput.put(new BigDecimal(amount), count);
                                    received = received.add(new BigDecimal(amount).multiply(BigDecimal.valueOf(count)));
                                }

                                if (received.doubleValue() == 0) {
                                    System.out.println("No money inputted. Cancelling deposit...\n");
                                    break;
                                }

                                System.out.println("Deposited amount: " + String.format("%,.2f", received) +
                                        "\nAre you sure you want to proceed with the deposit? Enter '1' to proceed." +
                                        "\nEnter 'cancel' to cancel the transaction.");

                                String userChoice = atmInput.next();
                                switch (checkString(userChoice)) {
                                    case 1: //deposit amount is a number
                                        atm.deposit(user, userInput);
                                    case -1:
                                        break;
                                    default:
                                        System.out.println("Invalid input.\n");
                                }
                            }
                            case "3" -> System.out.println("Your balance is $" + String.format("%,.2f", user.getBalance()));
                            case "4" -> logged_in = false; // prompts for another card
                            default -> System.out.println("Invalid Input, please try again.\n");
                        }
                    }
                }
            }
        }
        // runs when admin shuts down atm
        boolean notSaved = true;
        while(notSaved){
            System.out.println("Enter user file name:");
            String filename = atmInput.next();
            File f = new File(filename);
            if(f.exists() && !f.isDirectory()){
                f.delete();
                try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream("users"), StandardCharsets.UTF_8))) {
                    ArrayList<User> users = atm.getUserList();
                    for(User u : users) {
                        writer.write(u.getUserID() + "," + u.getFullName() + "," + u.getBalance() + "\n");
                    }
                }
                catch(Exception e){
                    System.out.println("Saving failed.");
                }
                notSaved = false;
            } else{
                System.out.println("File does not exist. Please try again");
            }
        }

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