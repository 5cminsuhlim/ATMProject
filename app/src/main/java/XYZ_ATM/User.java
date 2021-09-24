package XYZ_ATM;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class User{

    private ArrayList<Card> cards;
    private String userID;
    private String fullName;
    private double balance;

    public User(String userID, String fullName, double balance) {
        this.cards = new ArrayList<>();
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

    protected static ArrayList<User> readUsers(String filename, ArrayList<Card> cards){
        ArrayList<User> users = new ArrayList<>();
        try{
            File file = new File(filename);
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) { //reads all lines of the file
                String[] line = input.nextLine().split(",");
                // splits the line using regex to get rid of comma , each item is a variable for User
                User newUser = new User(line[0], line[1], Double.parseDouble(line[2]));
                for(Card c : cards) {
                    if(c.getUID().equals(newUser.getUserID())){
                        newUser.addCard(c);
                    }
                }
                users.add(newUser);

                // adds a user object into the userList
                // user has the format (userID, fullName, balance)
                // userList file has same format
            }
        }
        catch (Exception e) {
            System.out.println("Error reading card file. Please try again.\n");
            return null;
        }

        return users;
    }
}