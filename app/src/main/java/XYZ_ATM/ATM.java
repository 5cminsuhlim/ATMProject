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

    private boolean isValid(Card c){
        return checkCardNumber(c) && checkExpDate(c) && checkIssDate(c); //checkPin needs to be verified too
    }

    private boolean checkCardNumber(Card c){
        return c.getCard_number().length() == 5 && validCards.contains(c);
    }

    private boolean checkPin(Card c, String userPin){
        return userPin.equals(c.getPin());
    }

    private boolean checkExpDate(Card c){
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

    private boolean checkIssDate(Card c){
        String dateString;
        dateString = date.toString();
        int currentDate = Integer.parseInt(dateString.substring(0,4) + dateString.substring(5,7));
        int cardIssue = Integer.parseInt(c.getStart_date().substring(0,4) + c.getStart_date().substring(5,7));
        return currentDate >= cardIssue; // making sure the card is active already
    }

    private void apologize(Card c){
        if(c.isStolen()){
            System.out.println("The inserted card has been recognized as lost or stolen. " +
                    "Further action will be restricted. " +
                    "We apologize for the inconvenience.");
        }
    }

    private String error(){

    }

    private void withdraw(){

    }

    public void deposit(){

    }

    public double checkBalance(){
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