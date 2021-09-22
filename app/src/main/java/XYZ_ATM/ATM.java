package XYZ_ATM;

import java.math.RoundingMode;
import java.util.*;
import java.time.LocalDate;
import java.math.BigDecimal;


public class ATM{

    private LinkedHashMap<BigDecimal, Integer> balance;
    private ArrayList<Card> validCards;
    private LocalDate date;
    private int transactionNo;
    private String adminPin; //made w/ RNG
    private ArrayList<User> userList;

    public ATM(LinkedHashMap<BigDecimal, Integer> balance, ArrayList<Card> validCards, ArrayList<User> userList, LocalDate date) {
        this.balance = balance;
        this.validCards = validCards;
        this.userList = userList;
        this.date = date;
        this.transactionNo = 0;
        this.adminPin = "9746346416";
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

    public void setBalance(LinkedHashMap<BigDecimal, Integer> userInput){
        for(Map.Entry<BigDecimal, Integer> entry : userInput.entrySet()) {
            balance.put(entry.getKey(), (BigDecimal.valueOf(entry.getValue())).intValue());
        }
    }

    public void addFunds(LinkedHashMap<BigDecimal, Integer> userInput){
        userInput.forEach((currency, count) -> balance.merge(currency, count, Integer::sum));
    }

    public int removeFunds(double userInput){
        BigDecimal toWithdraw = BigDecimal.valueOf(userInput);

        ArrayList<BigDecimal> keys = new ArrayList<>(); // array lists to hold values, don't want to update the
        //  amount at an early point if there is an error later down the track
        ArrayList<Integer> values = new ArrayList<>();

        for(BigDecimal key : balance.keySet()){ // traverse the balance arraylist by the keys

            BigDecimal times = toWithdraw.divide(key).setScale(0, RoundingMode.DOWN); // how many times the
            // current note can be divided into the toWithdraw value

            if(times.compareTo(BigDecimal.ZERO) > 0){ // if the note can be divided more than 0 times
                toWithdraw = toWithdraw.subtract(times.multiply(key));
                if((BigDecimal.valueOf(balance.get(key)).subtract(times)).intValue() < 0){ // if it would
                    // make number of notes go negative, an error
                    return -1;
                } else {
                    keys.add(key);
                    values.add(BigDecimal.valueOf(balance.get(key)).subtract(times).intValue());
                }
            }
        }
        for(int i = 0; i < keys.size(); i++){
            balance.put(keys.get(i), values.get(i)); // if everything goes well, subtract values from balance
            // and return a 1
        } return 1;
    }

    public boolean checkIssDate(Card c){
        String dateString;
        dateString = this.date.toString();
        int currentDate = Integer.parseInt(dateString.substring(0,4) + dateString.substring(5,7));
        int cardIssue = Integer.parseInt(c.getStart_date().substring(3,7) + c.getStart_date().substring(0,2));
        return currentDate >= cardIssue; // making sure the card is active already
    }

    public void apologize(Card c){
        System.out.println("The inserted card with card number " + c.getCard_number() + " has been recognized as lost or stolen." +
                "\nFurther action will be restricted." +
                "\nWe apologize for the inconvenience.");
    }

    public void insuffATMFunds(){
        System.out.println("ERROR: Insufficient funds remaining in the ATM.");
    }

    public void insuffATMBills(){
        System.out.println("ERROR: Insufficient bills remaining in the ATM.");
    }

    public void insuffUserFunds(User u){
        System.out.println("Insufficient funds in account " + u.getFullName() +
                "\nCurrent balance: " + u.getBalance());
    }

    public void invalidInput(){
        System.out.println("Invalid amount.");
    }

    public int withdraw(User u, double userInput){

        if(userInput > u.getBalance()){
            insuffUserFunds(u);
            return -1;
        }
        else if(userInput > checkTotalBalance()){
            insuffATMFunds();
            return -2;
        }
        else if(BigDecimal.ZERO.compareTo(BigDecimal.valueOf(userInput).remainder(new BigDecimal("0.05"))) != 0){
            System.out.println(userInput);

            invalidInput();
            return -3;
        }
        else{
            if(removeFunds(userInput) == 1){
                transactionNo++;
                u.setBalance(BigDecimal.valueOf(u.getBalance()).subtract(BigDecimal.valueOf(userInput)).doubleValue());
                //subtract withdrawn amount from userBalance
                //equivalent to u.setBalance(u.getBalance() - userInput)
                //receipt
                System.out.println("Receipt Details:" +
                        "\nTransaction No.:" + transactionNo +
                        "\nTransaction Type: Withdrew $" + userInput +
                        "\nAccount Balance: " + u.getBalance());
                return 0;
            }
            else{
                insuffATMBills();
                return -4;
            }
        }
    }

    //incomplete
    public void deposit(User u, LinkedHashMap<BigDecimal, Integer> userInput){
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
    public String checkIndivBalance(){
        String returnstr = "";
        for(Map.Entry<BigDecimal, Integer> entry : balance.entrySet()){
            returnstr = returnstr + "Currency: " + entry.getKey().toString() + ", Quantity: " + entry.getValue().toString() + "\n";
        }
        return returnstr;
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