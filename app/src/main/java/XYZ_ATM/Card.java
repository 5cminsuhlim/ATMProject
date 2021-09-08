package XYZ_ATM;
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

}