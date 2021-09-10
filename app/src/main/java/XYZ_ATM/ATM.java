package XYZ_ATM;

import java.util.HashMap;
import java.util.ArrayList;
import java.time.LocalDate;

public class ATM{

    private HashMap<Double, Integer> balance;
    private ArrayList<Card> validCards;
    private LocalDate date;

    public ATM(HashMap<Double, Integer> balance, ArrayList<Card> validCards, LocalDate date) {
        this.balance = balance;
        this.validCards = validCards;
        this.date = LocalDate.now();
    }

    public int isValid(Card c,String userInPin){

        if(!checkPin(c,userPin)){
            return -2;
        }
        else if(!checkExpDate(c)){
            return -3;
        }
        else if(!checkIssDate(c)){
            return -4;
        }
        else if(!c.isStolen()){
            return -5;
        }
        else if(!c.isBlocked()){
            return -6;
        }
        else{
            return 0;
        }
    }

    public boolean checkCardNumber(String UserIn){

        if (UserIn.length() != 5) {
            // Returns -11 if the user entered a wrong size card
            return -11;
        }
        for (int i = 0, i < validCards.size(),i++){
            if(validCards.get(i).getCardNumber().equals(UserIn)){
                // return index if card is found in validCards
                return i;
            }
        }
        //Return -1 if Card does not exist.
        return -1
    }

    public Card getCard(int cardIndex){
        return validcCards.get(cardIndex);
    }

    public boolean checkPin(Card c, String userPin){
        return userPin.equals(c.getPin());
    }

    public boolean checkExpDate(Card c){
        String dateString;
        dateString = date.toString(); // converts to string in the format YYYY-MM-DD
        int currentDate = Integer.parseInt(dateString.substring(0,4) + dateString.substring(5,7));
        //gets the year and date, without the "-" so it looks like 202109
        int cardExpiry = Integer.parseInt(c.getExpiry_date().substring(0,4) + c.getExpiry_date().substring(5,7));
        return currentDate < cardExpiry;
        //since the years are just numbers, later dates are just bigger numbers so making sure the expiry date is bigger
    }

    public void setBalance(HashMap<Double, Integer> balance){
        this.balance = balance;
    }

    public boolean checkIssDate(Card c){
        String dateString;
        dateString = date.toString();
        int currentDate = Integer.parseInt(dateString.substring(0,4) + dateString.substring(5,7));
        int cardIssue = Integer.parseInt(c.getStart_date().substring(0,4) + c.getStart_date().substring(5,7));
        return currentDate >= cardIssue; // making sure the card is active already
    }

    public void apologize(Card c){
        if(c.isStolen()){
            System.out.println("The inserted card has been recognized as lost or stolen. " +
                    "Further action will be restricted. " +
                    "We apologize for the inconvenience.");
        }
    }

    public void error(){
        System.out.println("ERROR: Insufficient funds remaining in the ATM.");
    }

    //incomplete
    public void withdraw(User u, double userInput){
        double toWithdraw = userInput;
        double count;

        if(userInput > u.getBalance() && userInput > checkTotalBalance()){
            error();
            return u.getBalance();
        }
        else if(userInput > u.getBalance()){
            return u.getBalance();
        }
        else if(userInput > checkTotalBalance()){
            error();
        }

        for(Map.Entry<Double, Integer> entry : balance.entrySet()){
            count = toWithdraw % entry.getKey();


            /*if withdrawing $257,
                  257 - [(257 % 100) * 100] = 57
                  57 - [(57 % 50) * 50] = 7
                  7 - [(7 % 20) * 20] = -133 (would be skipped since toWithdraw < toSubtract)
                  7 - [(7%10) * 10] = -63 (would be skipped since toWithdraw < toSubtract)

            */
            if(toWithdraw >= count){
                toWithdraw -= count * entry.getKey();

                //updates the count for the respective currency
                balance.put(entry.getKey(), entry.getValue() - count);
            }
        }

        //subtract withdrawn amount from userBalance
        u.setBalance(u.getBalance() - userInput);
    }

    //incomplete
    public void deposit(User u, HashMap<Double, Integer> userInput){
        int received = 0;

        for(Map.Entry<Double, Integer> entry : userInput.entrySet()){
            received += entry.getKey() * entry.getValue();
        }

        //combine the count for each type of currency from userInput to the existing ATM balance
        userInput.forEach((currency, count) -> balance.merge(currency, count, Integer::sum));

        //add received amount to userBalance
        u.setBalance(u.getBalance() + received);
    }

    //returns individual breakdown of each coin/note
    public void checkIndivBalance(){
        for(Map.Entry<Double, Integer> entry : balance.entrySet()){
            System.out.println("Currency: " + entry.getKey() +
                    ", Quantity: " + entry.getValue());
        }
    }

    //returns sum total of all coins/notes
    public double checkTotalBalance(){
        double totalBal = 0;
        for(Map.Entry<Double, Integer> entry : balance.entrySet()){
            totalBal += entry.getKey() * entry.getValue();
        }

        return totalBal;
    }

    public boolean promptUser(int userInput){ //checking to proceed / cancel
        if(userInput == 1){
            return true;
        }
        return false;
    }
}