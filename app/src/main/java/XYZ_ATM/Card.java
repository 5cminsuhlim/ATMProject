package XYZ_ATM;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Card{

    private String card_number;
    private String pin;
    private String start_date;
    private String expiry_date;
    private String UID;
    private boolean stolen;
    private boolean blocked;


    public Card(String card_number, String pin, String start_date, String expiry_date, String UID, boolean stolen){
        this.card_number = card_number;
        this.pin = pin;
        this.start_date = start_date;
        this.expiry_date = expiry_date;
        this.UID = UID;
        this.stolen = stolen;
        this.blocked = false;

    }

    public String getCard_number() {
        return card_number;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked){
        this.blocked = blocked;
    }

    public boolean isStolen() {
        return stolen;
    }

    public void setStolen(boolean stolen){
        this.stolen = stolen;
    }

    protected static ArrayList<Card> readCards(String filename){
        ArrayList<Card> validCards = new ArrayList<>();

        try{
            File file = new File(filename);
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) { //reads all lines of the file
                String[] line = input.nextLine().split(",");
                // splits the line using regex to get rid of comma and space, each item is a variable for Card
                boolean stolen = "1".equals(line[3]);
                validCards.add(new Card(line[0], line[4], line[1], line[2], "placeholder", stolen));
            }
        }
        catch (Exception e) {
            System.out.println("Error reading card file. Please try again.\n"); // placeholder
        }

        return validCards;
    }
}