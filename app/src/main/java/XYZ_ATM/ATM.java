package XYZ_ATM;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.Scanner;


public class ATM{

    private HashMap<BigDecimal, Integer> balance;
    private ArrayList<Card> validCards;
    private LocalDate date;
    private int transactionNo = 0;
    private String adminPin = "9746346416"; //made w/ RNG
    private ArrayList<User> userList;

    public ATM(HashMap<BigDecimal, Integer> balance, ArrayList<Card> validCards, ArrayList<User> userList, LocalDate date, int transactionNo, String adminPin) {
        this.balance = balance;
        this.validCards = validCards;
        this.userList = userList;
        this.date = date;
        this.transactionNo = transactionNo;
        this.adminPin = adminPin;
    }

    public boolean isAdmin(String userInput){
        return userInput.equals(adminPin);
    }

    public int isValid(Card c,String userInPin){

        if(!checkPin(c,userInPin)){
            System.out.println("Incorrect Pin.\n");
            return -2;
        }
        else if(!checkExpDate(c)){
            System.out.println("This card is expired.\n");
            return -3;
        }
        else if(!checkIssDate(c)){
            System.out.println("This card has not been activated yet.\n");
            return -4;
        }
        else if(c.isStolen()){
            apologize(c);
            return -5;
        }
        else if(c.isBlocked()){
            System.out.println("This card is blocked.\n");
            return -6;
        }
        else{
            return 0;
        }
    }

    public int checkCardNumber(String UserIn){

        if (UserIn.length() != 5) {
            // Returns -11 if the user entered a wrong size card
            return -11;
        }
        for (int i = 0; i < validCards.size(); i++){
            if(validCards.get(i).getCard_number().equals(UserIn)){
                // return index if card is found in validCards
                return i;
            }
        }
        //Return -1 if Card does not exist.
        return -1;
    }

    public ArrayList<Card> getCardList(){
        return this.validCards;
    }

    public ArrayList<User> getUserList(){
        return this.userList;
    }

    public int getUserFromCard(Card card){
        for(int i = 0; i < this.userList.size(); i++){
            if(this.userList.get(i).getCards().contains(card)){
                return i;
            }
        } return -1; // user not found
    }

    public Card getCard(int cardIndex){
        return validCards.get(cardIndex);
    }

    public boolean checkPin(Card c, String userPin){
        return userPin.equals(c.getPin());
    }

    public boolean checkExpDate(Card c){
        String dateString;
        dateString = this.date.toString(); // converts to string in the format YYYY-MM
        int currentDate = Integer.parseInt(dateString.substring(0,4) + dateString.substring(5,7));
        //gets the year and date, without the "-" so it looks like 202109
        int cardExpiry = Integer.parseInt(c.getExpiry_date().substring(3,7) + c.getExpiry_date().substring(0,2));
        // card expiry is in the form MM/YYYY, changing to YYYYMM
        return currentDate <= cardExpiry;
        //since the years are just numbers, later dates are just bigger numbers so making sure the expiry date is bigger
    }

    public void setBalance(HashMap<BigDecimal, Integer> userInput){
        for(Map.Entry<BigDecimal, Integer> entry : userInput.entrySet()) {
            balance.put(entry.getKey(), (BigDecimal.valueOf(entry.getValue())).intValue());
        }
    }

    public void addFunds(HashMap<BigDecimal, Integer> userInput){
        userInput.forEach((currency, count) -> balance.merge(currency, count, Integer::sum));
    }

    public void removeFunds(double userInput){
        BigDecimal toWithdraw = BigDecimal.valueOf(userInput);
        BigDecimal count;

        for(Map.Entry<BigDecimal, Integer> entry : balance.entrySet()) {
            count = toWithdraw.remainder(entry.getKey());

                /*if withdrawing $257,
                      257 - [(257 % 100) * 100] = 57
                      57 - [(57 % 50) * 50] = 7
                      7 - [(7 % 20) * 20] = -133 (would be skipped since toWithdraw < toSubtract)
                      7 - [(7%10) * 10] = -63 (would be skipped since toWithdraw < toSubtract)

                */

            if (toWithdraw.compareTo(count) >= 0) {
                toWithdraw = toWithdraw.subtract(count).multiply(entry.getKey());
                // equivalent to toWithdraw -= count * entry.getKey()

                //updates the count for the respective currency
                balance.put(entry.getKey(), (BigDecimal.valueOf(entry.getValue()).subtract(count)).intValue());
            }
        }
    }

    public boolean checkIssDate(Card c){
        String dateString;
        dateString = this.date.toString();
        int currentDate = Integer.parseInt(dateString.substring(0,4) + dateString.substring(5,7));
        int cardIssue = Integer.parseInt(c.getStart_date().substring(6,10) + c.getStart_date().substring(3,5));
        return currentDate >= cardIssue; // making sure the card is active already
    }

    public void apologize(Card c){
        System.out.println("The inserted card has been recognized as lost or stolen." +
                "\nFurther action will be restricted." +
                "\nWe apologize for the inconvenience.");
    }

    public void insuffATMFunds(){
        System.out.println("ERROR: Insufficient funds remaining in the ATM.");
    }

    public void insuffUserFunds(User u){
        System.out.println("Insufficient funds in account " + u.getFullName() +
                "\nCurrent balance: " + u.getBalance());
    }

    //incomplete
    public void withdraw(User u, double userInput){ // should probably instead return a bool, so that ATM_Runner
        // can call atm.error and will know if the transaction failed.
        transactionNo++;

        if(userInput > u.getBalance()){
            insuffUserFunds(u);
        }
        else if(userInput > checkTotalBalance()){
            insuffATMFunds();
        }
        else{
            removeFunds(userInput);
        }
        //subtract withdrawn amount from userBalance
        u.setBalance(BigDecimal.valueOf(u.getBalance()).subtract(BigDecimal.valueOf(userInput)).doubleValue());
        //equivalent to u.setBalance(u.getBalance() - userInput)

        //receipt
        System.out.println("Receipt Details:" +
                "\nTransaction No.:" + transactionNo +
                "\nTransaction Type: Withdrew $" + userInput +
                "\nAccount Balance: " + u.getBalance());
    }

    //incomplete
    public void deposit(User u, HashMap<BigDecimal, Integer> userInput){
        transactionNo++;
        BigDecimal received = BigDecimal.ZERO;

        for(Map.Entry<BigDecimal, Integer> entry : userInput.entrySet()){
            received = received.add(entry.getKey().multiply(BigDecimal.valueOf(entry.getValue())));
            // equivalent to received += entry.getKey() * entry.getValue()
        }

        //combine the count for each type of currency from userInput to the existing ATM balance
        addFunds(userInput);

        //add received amount to userBalance
        u.setBalance(received.add(BigDecimal.valueOf(u.getBalance())).doubleValue());
        // equivalent to received + u.getBalance()

        //receipt
        System.out.println("Receipt Details:" +
                "\nTransaction No.:" + transactionNo +
                "\nTransaction Type: Deposited $" + received +
                "\nAccount Balance: " + u.getBalance());
    }

    //returns individual breakdown of each coin/note
    public void checkIndivBalance(){
        for(Map.Entry<BigDecimal, Integer> entry : balance.entrySet()){
            System.out.println("Currency: " + entry.getKey() +
                    ", Quantity: " + entry.getValue());
        }
    }

    //returns sum total of all coins/notes
    public double checkTotalBalance(){
        BigDecimal totalBal = BigDecimal.ZERO;
        for(Map.Entry<BigDecimal, Integer> entry : balance.entrySet()){
            totalBal =  totalBal.add(entry.getKey().multiply(BigDecimal.valueOf(entry.getValue())));
            // equivalent to totalBal += entry.getKey() * entry.getValue()
        }
        return totalBal.doubleValue();
    }
}