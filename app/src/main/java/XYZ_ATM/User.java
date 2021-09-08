package XYZ_ATM;

import java.util.ArrayList;

public class User{

    private ArrayList<Card> cards;
    private String userID;
    private String fullName;
    private double balance;

    public User(ArrayList<Card> cards, String userID, String fullName, double balance) {
        this.cards = cards;
        this.userID = userID;
        this.fullName = fullName;
        this.balance = balance;
    }

    public ArrayList<Card> getCards() { return cards; }

    public void setCards(ArrayList<Card> cards) { this.cards = cards; }

    public void addCard(Card newCard) {
        cards.add(newCard);
    }

    public String getUserID() { return userID; }

    public void setUserID(String userID) { this.userID = userID; }

    public String getFullName() { return fullName; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public double getBalance() { return balance; }

    public void setBalance(double balance) { this.balance = balance; }
}