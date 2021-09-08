package XYZ_ATM;

import java.util.Arrays;

public class User{

    private Card[] cards;
    private String userID;
    private String fullName;
    private double balance;

    public User(Card[] cards, String userID, String fullName, double balance) {
        this.cards = cards;
        this.userID = userID;
        this.fullName = fullName;
        this.balance = balance;
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    public void addCard(Card newCard){
        cards = Arrays.copyOf(cards, cards.length + 1);
        cards[card.length - 1] = newCard;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}