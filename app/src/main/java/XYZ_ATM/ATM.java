package XYZ_ATM;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.time.LocalDate;
import java.math.BigDecimal;

public class ATM{

    private HashMap<BigDecimal, Integer> balance;
    private ArrayList<Card> validCards;
    private LocalDate date;
    private int transactionNo;

    public ATM(HashMap<BigDecimal, Integer> balance, ArrayList<Card> validCards, LocalDate date, int transactionNo) {
        this.balance = balance;
        this.validCards = validCards;
        this.date = date;
        this.transactionNo = transactionNo;
    }

    public int isValid(Card c,String userInPin){

        if(!checkPin(c,userInPin)){
            return -2;
        }
        else if(!checkExpDate(c)){
            return -3;
        }
        else if(!checkIssDate(c)){
            return -4;
        }
        else if(!c.isStolen()){
            apologize(c);
            return -5;
        }
        else if(!c.isBlocked()){
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
        int cardExpiry = Integer.parseInt(c.getExpiry_date().substring(0,2) + c.getExpiry_date().substring(3,7));
        // card expiry is in the form MM/YYYY, changing to YYYYMM
        return currentDate <= cardExpiry;
        //since the years are just numbers, later dates are just bigger numbers so making sure the expiry date is bigger
    }

    public void setBalance(HashMap<BigDecimal, Integer> balance){
        this.balance = balance;
    }

    public boolean checkIssDate(Card c){
        String dateString;
        dateString = this.date.toString();
        int currentDate = Integer.parseInt(dateString.substring(0,4) + dateString.substring(5,7));
        int cardIssue = Integer.parseInt(c.getStart_date().substring(0,2) + c.getStart_date().substring(3,7));
        return currentDate >= cardIssue; // making sure the card is active already
    }

    public void apologize(Card c){
        System.out.println("The inserted card has been recognized as lost or stolen. " +
                "Further action will be restricted. " +
                "We apologize for the inconvenience.");
    }

    public void error(){
        System.out.println("ERROR: Insufficient funds remaining in the ATM.");
    }

    public void insuffUserFunds(User u){
        System.out.println("Insufficient funds in account " + u.getFullName(). +
                " Current balance: " + u.getBalance());
    }

    //incomplete
    public void withdraw(User u, double userInput){ // should probably instead return a bool, so that ATM_Runner
        // can call atm.error and will know if the transaction failed.
        BigDecimal toWithdraw = BigDecimal.valueOf(userInput);
        BigDecimal count;

        if(userInput > u.getBalance()){
            insuffUserFunds(u);
        } else if(userInput > checkTotalBalance()){
            error();
        } else{

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

        //subtract withdrawn amount from userBalance
        u.setBalance(u.getBalance() - userInput);

        System.out.println("Print receipt")
    }

    //incomplete
    public void deposit(User u, HashMap<BigDecimal, Integer> userInput){
        BigDecimal received = BigDecimal.ZERO;

        for(Map.Entry<BigDecimal, Integer> entry : userInput.entrySet()){
            received = received.add(entry.getKey().multiply(BigDecimal.valueOf(entry.getValue())));
            // equivalent to received += entry.getKey() * entry.getValue()
        }

        //combine the count for each type of currency from userInput to the existing ATM balance
        userInput.forEach((currency, count) -> balance.merge(currency, count, Integer::sum));

        //add received amount to userBalance
        u.setBalance(received.add(BigDecimal.valueOf(u.getBalance())).doubleValue());
        // equivalent to received + u.getBalance()
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

    public boolean promptUser(int userInput){ //checking to proceed / cancel
        return userInput == 1;
    }
}