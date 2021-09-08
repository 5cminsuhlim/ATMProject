package XYZ_ATM;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.LocalDate;

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

    }

    private boolean checkCardNumber(Card c){
        return c.length() == 5 && validCards.contains(c);
    }

    private boolean checkPin(Card c, String userPin){
        return userPin == c.getPin();
    }

    private boolean checkExpDate(Card c){

    }

    public void setBalance(HashMap<Double, Integer> balance){
        this.balance = balance;
    }

    private boolean checkIssDate(Card c){

    }

    private String apologize(){

    }

    private String error(){

    }

    private void withdraw(){

    }

    public void deposit(){

    }

    public double checkBalance(){

    }

    public boolean promptUser(){ //checking to proceed / cancel

    }
}