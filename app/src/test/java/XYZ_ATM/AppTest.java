/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package XYZ_ATM;

import java.util.*;
import java.lang.*;
import java.math.*;
import java.time.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test
    void CheckCardClass(){
        String cardnum = "54321";
        String cardpin = "4321";
        String startdate = "09/2018";
        String expdate = "01/2022";
        String UID = "6969";
        boolean stolen = false;

        Card testCard = new Card(cardnum,cardpin,startdate,expdate,UID,stolen);
        assertEquals("54321", testCard.getCard_number(), "Card number returned is incorrect");
        assertEquals("4321",testCard.getPin(),"Card Pin returned is incorrect");
        assertEquals("09/2018",testCard.getStart_date(),"Start date returned is incorrect");
        assertEquals("01/2022",testCard.getExpiry_date(), "Expiry date returned is incorrect");
        assertEquals("6969",testCard.getUID(),"UID returned is incorrect");
        assertFalse(testCard.isStolen(), "Stolen should not be true");
        assertFalse(testCard.isBlocked(), "Blocked should not be true");
    }

    @Test
    void checkCardSetMethods(){
        String cardnum = "11111";
        String pin = "1111";
        String startdate = "09/2018";
        String expdate = "01/2022";
        String UID = "1111";
        boolean stolen = false;

        Card testCard = new Card(cardnum, pin, startdate, expdate, UID, stolen);
        assertEquals("1111",testCard.getPin(),"Card Pin returned is incorrect");
        testCard.setPin("2222"); //Testing set pin method
        assertEquals("2222",testCard.getPin(),"New pin was not set correctly");

        assertEquals("09/2018",testCard.getStart_date(),"Start date returned is incorrect");
        testCard.setStart_date("07/2018"); //Testing set start date method
        assertEquals("07/2018",testCard.getStart_date(),"New start date was not set correctly");

        assertEquals("01/2022",testCard.getExpiry_date(),"Expiry date returned is incorrect");
        testCard.setExpiry_date("03/2022"); //Testing set expiry date method
        assertEquals("03/2022",testCard.getExpiry_date(),"New expiry date was not set correctly");

        assertEquals("1111",testCard.getUID(),"UID returned is incorrect");
        testCard.setUID("2222"); //Testing set UID method
        assertEquals("2222",testCard.getUID(),"New UID was not set correctly");

        assertEquals(false, testCard.isBlocked(), "Non-blocked card is returning true");
        testCard.setBlocked(true); //Testing set blocked method
        assertEquals(true, testCard.isBlocked(), "Blocked card is returning false");

        assertEquals(false, testCard.isStolen(), "Non-stolen card is returning true");
        testCard.setStolen(true); //Testing set stolen method
        assertEquals(true, testCard.isStolen(), "Stolen card is returning false");
    }

    @Test
    void checkUserClass(){
        String cardnum = "54321";
        String cardpin = "4321";
        String startdate = "09/2018";
        String expdate = "01/2022";
        String UID = "6969";
        boolean stolen = false;

        Card testCard = new Card(cardnum,cardpin,startdate,expdate,UID,stolen);

        String userID = "6969";
        String full_name = "first_name last_name";
        double balance = 2000.00;

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(testCard);

        User testUser = new User(userID, full_name, balance);
        testUser.setCards(cards);
        assertEquals(cards, testUser.getCards(), "Card ArrayList is incorrect");
        assertEquals(userID, testUser.getUserID(), "UserID returned is incorrect");
        assertEquals(full_name, testUser.getFullName(), "Full name returned is incorrect");
        assertEquals(balance, testUser.getBalance(), "Balance returned is incorrect");

        String cardnum2 = "11111";
        String cardpin2 = "1111";
        String startdate2 = "08/2018";
        String expdate2 = "02/2022";
        String UID2 = "1111";
        boolean stolen2 = false;

        Card testCard2 = new Card(cardnum2,cardpin2,startdate2,expdate2,UID2,stolen2);
        testUser.addCard(testCard2); //Adding a new card to testUser
        assertEquals(cards, testUser.getCards(), "Card ArrayList is incorrect");
    }

    @Test
    void checkUserSetMethods(){
        String cardnum = "11111";
        String cardpin = "1111";
        String startdate = "09/2018";
        String expdate = "01/2022";
        String UID = "1111";
        boolean stolen = false;

        Card testCard = new Card(cardnum,cardpin,startdate,expdate,UID,stolen);

        String userID = "1111";
        String full_name = "first_name last_name";
        double balance = 2000.00;

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(testCard);

        User testUser = new User(userID, full_name, balance);

        String cardnum1 = "22222";
        String cardpin1 = "2222";
        String startdate1 = "09/2018";
        String expdate1 = "01/2022";
        String UID1 = "1111";
        boolean stolen1 = false;

        Card testCard1 = new Card(cardnum1,cardpin1,startdate1,expdate1,UID1,stolen1);
        ArrayList<Card> cards1 = new ArrayList<>();
        cards1.add(testCard1);

        testUser.setCards(cards);
        assertEquals(cards, testUser.getCards(), "Card ArrayList returned is incorrect");
        testUser.setCards(cards1);
        assertEquals(cards1, testUser.getCards(), "After setting cards, method returned incorrect arraylist");

        assertEquals("1111", testUser.getUserID(), "User ID returned is incorrect");
        testUser.setUserID("2222");
        assertEquals("2222", testUser.getUserID(), "After setting UserID, method returned incorrect value");

        assertEquals("first_name last_name", testUser.getFullName(), "Fullname returned is incorrect");
        testUser.setFullName("abc 123");
        assertEquals("abc 123", testUser.getFullName(), "After setting fullname, method returned incorrect value");

        assertEquals(2000.00, testUser.getBalance(), "Balance returned is incorrect");
        testUser.setBalance(2500.00);
        assertEquals(2500, testUser.getBalance(), "After setting balance, method returned incorrect value");
    }

    @Test
    void checkCardNumberTest(){
        LinkedHashMap<BigDecimal, Integer> balance = new LinkedHashMap<BigDecimal, Integer>();
        BigDecimal bd100 = new BigDecimal("100.00");
        BigDecimal bd50 = new BigDecimal("50.00");
        BigDecimal bd20 = new BigDecimal("20.00");
        BigDecimal bd10 = new BigDecimal("10.00");
        BigDecimal bd5 = new BigDecimal("5.00");
        BigDecimal bd2 = new BigDecimal("2.00");
        BigDecimal bd1 = new BigDecimal("1.00");
        BigDecimal bd050 = new BigDecimal("0.50");
        BigDecimal bd020 = new BigDecimal("0.20");
        BigDecimal bd010 = new BigDecimal("0.10");
        BigDecimal bd005 = new BigDecimal("0.05");
        balance.put(bd100, 10);
        balance.put(bd50, 10);
        balance.put(bd20, 10);
        balance.put(bd10, 10);
        balance.put(bd5, 10);
        balance.put(bd2, 10);
        balance.put(bd1, 10);
        balance.put(bd050, 10);
        balance.put(bd020, 10);
        balance.put(bd010, 10);
        balance.put(bd005, 10);


        String cardnum1 = "11111";
        String cardpin1 = "1111";
        String startdate1 = "09/2018";
        String expdate1 = "01/2022";
        String UID1 = "1111";
        boolean stolen1 = false;

        Card testCard1 = new Card(cardnum1,cardpin1,startdate1,expdate1,UID1,stolen1);

        String cardnum2 = "22222";
        String cardpin2 = "2222";
        String startdate2 = "04/2019";
        String expdate2 = "02/2023";
        String UID2 = "2222";
        boolean stolen2 = false;

        Card testCard2 = new Card(cardnum2,cardpin2,startdate2,expdate2,UID2,stolen2);

        String cardnum3 = "33333";
        String cardpin3 = "3333";
        String startdate3 = "05/2018";
        String expdate3 = "07/2022";
        String UID3 = "3333";
        boolean stolen3 = false;

        Card testCard3 = new Card(cardnum3,cardpin3,startdate3,expdate3,UID3,stolen3);

        ArrayList<Card> testCards = new ArrayList<Card>();
        testCards.add(testCard1);
        testCards.add(testCard2);
        testCards.add(testCard3);

        LocalDate testDate = LocalDate.now();

        String userID = "1111";
        String full_name = "first_name last_name";
        double userBalance = 2000.00;
        User testUser = new User(userID, full_name, userBalance);
        ArrayList<User> testUserList = new ArrayList<User>();
        testUserList.add(testUser);

        ATM testATM = new ATM(balance, testCards, testUserList,testDate);

        //Checking correct input
        assertEquals(0, testATM.checkCardNumber("11111"), "Index for first valid card returned is incorrect");
        assertEquals(testCard1, testATM.getCard(0), "Card returned is incorrect");
        assertEquals(1, testATM.checkCardNumber("22222"), "Index for second valid card returned is incorrect");
        assertEquals(testCard2, testATM.getCard(1), "Card returned is incorrect");

        //Checking incorrect input
        assertEquals(-1, testATM.checkCardNumber("12345"), "System does not identify that the card DOES NOT exist");

        //Checking short/long input length
        assertEquals(-11, testATM.checkCardNumber("123"), "System does not identify that the card number is of incorrect size");
        assertEquals(-11, testATM.checkCardNumber("1234567"), "System does not identify that the card number is of incorrect size");
    }

    @Test
    void checkIssDateTest(){
        LinkedHashMap<BigDecimal, Integer> balance = new LinkedHashMap<BigDecimal, Integer>();
        BigDecimal bd100 = new BigDecimal("100.00");
        BigDecimal bd50 = new BigDecimal("50.00");
        BigDecimal bd20 = new BigDecimal("20.00");
        BigDecimal bd10 = new BigDecimal("10.00");
        BigDecimal bd5 = new BigDecimal("5.00");
        BigDecimal bd2 = new BigDecimal("2.00");
        BigDecimal bd1 = new BigDecimal("1.00");
        BigDecimal bd050 = new BigDecimal("0.50");
        BigDecimal bd020 = new BigDecimal("0.20");
        BigDecimal bd010 = new BigDecimal("0.10");
        BigDecimal bd005 = new BigDecimal("0.05");
        balance.put(bd100, 10);
        balance.put(bd50, 10);
        balance.put(bd20, 10);
        balance.put(bd10, 10);
        balance.put(bd5, 10);
        balance.put(bd2, 10);
        balance.put(bd1, 10);
        balance.put(bd050, 10);
        balance.put(bd020, 10);
        balance.put(bd010, 10);
        balance.put(bd005, 10);

        String cardnum1 = "11111";
        String cardpin1 = "1111";
        String startdate1 = "09/2018"; //Start date before today (valid)
        String expdate1 = "01/2022";
        String UID1 = "1111";
        boolean stolen1 = false;

        Card testCard1 = new Card(cardnum1,cardpin1,startdate1,expdate1,UID1,stolen1);

        String cardnum2 = "22222";
        String cardpin2 = "2222";
        String startdate2 = "09/2022"; //Start day after today (invalid)
        String expdate2 = "02/2025";
        String UID2 = "2222";
        boolean stolen2 = false;

        Card testCard2 = new Card(cardnum2,cardpin2,startdate2,expdate2,UID2,stolen2);

        ArrayList<Card> testCards = new ArrayList<Card>();
        testCards.add(testCard1);
        testCards.add(testCard2);

        LocalDate testDate = LocalDate.now();

        String userID = "1111";
        String full_name = "first_name last_name";
        double userBalance = 2000.00;
        User testUser = new User(userID, full_name, userBalance);
        ArrayList<User> testUserList = new ArrayList<User>();
        testUserList.add(testUser);

        ATM testATM = new ATM(balance, testCards, testUserList,testDate);

        assertEquals(true, testATM.checkIssDate(testCard1), "Card with valid issue date is returning an incorrect value");
        assertEquals(false, testATM.checkIssDate(testCard2), "Card with invalid issue date is returning an incorrect value");
    }

    @Test
    void checkExpDateTest(){
        LinkedHashMap<BigDecimal, Integer> balance = new LinkedHashMap<BigDecimal, Integer>();
        BigDecimal bd100 = new BigDecimal("100.00");
        BigDecimal bd50 = new BigDecimal("50.00");
        BigDecimal bd20 = new BigDecimal("20.00");
        BigDecimal bd10 = new BigDecimal("10.00");
        BigDecimal bd5 = new BigDecimal("5.00");
        BigDecimal bd2 = new BigDecimal("2.00");
        BigDecimal bd1 = new BigDecimal("1.00");
        BigDecimal bd050 = new BigDecimal("0.50");
        BigDecimal bd020 = new BigDecimal("0.20");
        BigDecimal bd010 = new BigDecimal("0.10");
        BigDecimal bd005 = new BigDecimal("0.05");
        balance.put(bd100, 10);
        balance.put(bd50, 10);
        balance.put(bd20, 10);
        balance.put(bd10, 10);
        balance.put(bd5, 10);
        balance.put(bd2, 10);
        balance.put(bd1, 10);
        balance.put(bd050, 10);
        balance.put(bd020, 10);
        balance.put(bd010, 10);
        balance.put(bd005, 10);

        String cardnum1 = "11111";
        String cardpin1 = "1111";
        String startdate1 = "09/2018";
        String expdate1 = "01/2022"; //Expiry date after today (valid)
        String UID1 = "1111";
        boolean stolen1 = false;

        Card testCard1 = new Card(cardnum1,cardpin1,startdate1,expdate1,UID1,stolen1);

        String cardnum2 = "22222";
        String cardpin2 = "2222";
        String startdate2 = "09/2017";
        String expdate2 = "02/2020"; //Expiry date before today (invalid)
        String UID2 = "2222";
        boolean stolen2 = false;

        Card testCard2 = new Card(cardnum2,cardpin2,startdate2,expdate2,UID2,stolen2);

        ArrayList<Card> testCards = new ArrayList<Card>();
        testCards.add(testCard1);
        testCards.add(testCard2);

        LocalDate testDate = LocalDate.now();

        String userID = "1111";
        String full_name = "first_name last_name";
        double userBalance = 2000.00;
        User testUser = new User(userID, full_name, userBalance);
        ArrayList<User> testUserList = new ArrayList<User>();
        testUserList.add(testUser);

        ATM testATM = new ATM(balance, testCards, testUserList,testDate);

        assertEquals(true, testATM.checkExpDate(testCard1), "Card with valid expiry date is returning an incorrect value");
        assertEquals(false, testATM.checkExpDate(testCard2), "Card with invalid expiry date is returning an incorrect value");
    }

    @Test
    void TestATM(){
        LinkedHashMap<BigDecimal, Integer> balance = new LinkedHashMap<BigDecimal, Integer>();
        BigDecimal bd100 = new BigDecimal("100.00");
        BigDecimal bd50 = new BigDecimal("50.00");
        BigDecimal bd20 = new BigDecimal("20.00");
        BigDecimal bd10 = new BigDecimal("10.00");
        BigDecimal bd5 = new BigDecimal("5.00");
        BigDecimal bd2 = new BigDecimal("2.00");
        BigDecimal bd1 = new BigDecimal("1.00");
        BigDecimal bd050 = new BigDecimal("0.50");
        BigDecimal bd020 = new BigDecimal("0.20");
        BigDecimal bd010 = new BigDecimal("0.10");
        BigDecimal bd005 = new BigDecimal("0.05");
        balance.put(bd100, 10);
        balance.put(bd50, 10);
        balance.put(bd20, 10);
        balance.put(bd10, 10);
        balance.put(bd5, 10);
        balance.put(bd2, 10);
        balance.put(bd1, 10);
        balance.put(bd050, 10);
        balance.put(bd020, 10);
        balance.put(bd010, 10);
        balance.put(bd005, 10);

        String cardnum1 = "11111";
        String cardpin1 = "1111";
        String startdate1 = "09/2018";
        String expdate1 = "01/2022";
        String UID1 = "1111";
        boolean stolen1 = false;

        Card testCard1 = new Card(cardnum1,cardpin1,startdate1,expdate1,UID1,stolen1);

        ArrayList<Card> testCards = new ArrayList<Card>();
        testCards.add(testCard1);

        LocalDate testDate = LocalDate.now();

        String userID = "1111";
        String full_name = "first_name last_name";
        double userBalance = 2000.00;
        User testUser = new User(userID, full_name, userBalance);
        ArrayList<User> testUserList = new ArrayList<User>();
        testUserList.add(testUser);

        ATM testATM = new ATM(balance, testCards, testUserList,testDate);

        assertEquals(true, testATM.isAdmin("9746346416"), "ATM does not recognise correct adminPin");
        assertEquals(false,testATM.isAdmin("12345"), "ATM does not recognise incorrect adminPin");

        assertEquals(testCards, testATM.getCardList(),"ATM returns incorrect list of cards");
        assertEquals(testUserList, testATM.getUserList(),"ATM returns incorrect list of users");
    }

    @Test
    void tchecktotalbal(){
        LinkedHashMap<BigDecimal, Integer> balance = new LinkedHashMap<BigDecimal, Integer>();
        BigDecimal bd100 = new BigDecimal("100.00");
        BigDecimal bd50 = new BigDecimal("50.00");
        BigDecimal bd20 = new BigDecimal("20.00");
        BigDecimal bd10 = new BigDecimal("10.00");
        BigDecimal bd5 = new BigDecimal("5.00");
        BigDecimal bd2 = new BigDecimal("2.00");
        BigDecimal bd1 = new BigDecimal("1.00");
        BigDecimal bd050 = new BigDecimal("0.50");
        BigDecimal bd020 = new BigDecimal("0.20");
        BigDecimal bd010 = new BigDecimal("0.10");
        BigDecimal bd005 = new BigDecimal("0.05");
        balance.put(bd100, 10);
        balance.put(bd50, 5);
        balance.put(bd20, 1);
        balance.put(bd10, 0);
        balance.put(bd5, 0);
        balance.put(bd2, 0);
        balance.put(bd1, 0);
        balance.put(bd050, 0);
        balance.put(bd020, 0);
        balance.put(bd010, 0);
        balance.put(bd005, 0);

        String cardnum1 = "11111";
        String cardpin1 = "1111";
        String startdate1 = "09/2018";
        String expdate1 = "01/2022";
        String UID1 = "1111";
        boolean stolen1 = false;

        Card testCard1 = new Card(cardnum1,cardpin1,startdate1,expdate1,UID1,stolen1);

        ArrayList<Card> testCards = new ArrayList<Card>();
        testCards.add(testCard1);

        LocalDate testDate = LocalDate.now();

        String userID = "1111";
        String full_name = "first_name last_name";
        double userBalance = 2000.00;
        User testUser = new User(userID, full_name, userBalance);
        ArrayList<User> testUserList = new ArrayList<User>();
        testUserList.add(testUser);
      
        ATM testATM = new ATM(balance, testCards, testUserList,testDate);

        assertEquals(1270,testATM.checkTotalBalance(),"ATM balance Incorrect");
    }

    @Test
    void withdrawlTest(){
        LinkedHashMap<BigDecimal, Integer> balance = new LinkedHashMap<BigDecimal, Integer>();
        BigDecimal bd100 = new BigDecimal("100.00");
        BigDecimal bd50 = new BigDecimal("50.00");
        BigDecimal bd20 = new BigDecimal("20.00");
        BigDecimal bd10 = new BigDecimal("10.00");
        BigDecimal bd5 = new BigDecimal("5.00");
        BigDecimal bd2 = new BigDecimal("2.00");
        BigDecimal bd1 = new BigDecimal("1.00");
        BigDecimal bd050 = new BigDecimal("0.50");
        BigDecimal bd020 = new BigDecimal("0.20");
        BigDecimal bd010 = new BigDecimal("0.10");
        BigDecimal bd005 = new BigDecimal("0.05");
        balance.put(bd100, 10);
        balance.put(bd50, 10);
        balance.put(bd20, 10);
        balance.put(bd10, 10);
        balance.put(bd5, 10);
        balance.put(bd2, 10);
        balance.put(bd1, 10);
        balance.put(bd050, 10);
        balance.put(bd020, 10);
        balance.put(bd010, 10);
        balance.put(bd005, 10);

        LinkedHashMap<BigDecimal, Integer> balance2 = new LinkedHashMap<BigDecimal, Integer>();
        BigDecimal bd1002 = new BigDecimal("100.00");
        balance2.put(bd1002, 1);

        String cardnum1 = "11111";
        String cardpin1 = "1111";
        String startdate1 = "09/2018";
        String expdate1 = "01/2022";
        String UID1 = "1111";
        boolean stolen1 = false;

        Card testCard1 = new Card(cardnum1,cardpin1,startdate1,expdate1,UID1,stolen1);

        ArrayList<Card> testCards = new ArrayList<Card>();
        testCards.add(testCard1);

        LocalDate testDate = LocalDate.now();

        String userID = "1111";
        String full_name = "first_name last_name";
        double userBalance = 1000.00;
        User testUser = new User(userID, full_name, userBalance);
        ArrayList<User> testUserList = new ArrayList<User>();
        testUserList.add(testUser);

        userID = "1111";
        full_name = "first_name last_name";
        userBalance = 100000.00;
        User testUser2 = new User(userID, full_name, userBalance);
        testUserList.add(testUser2);

        double userOverBalance = 1500.00;
        double userNonValidAmmount = 750.02;
        double atmOverBalance = 25000.00;
        double smallAmmount = 50.00;
        double validAmmount = 750.00;

        ATM testATM = new ATM(balance, testCards, testUserList,testDate);
        ATM testATM2 = new ATM(balance2, testCards, testUserList,testDate);
        assertEquals(-1,testATM.withdraw(testUser, userOverBalance),"Insufficent User Funds");
        assertEquals(-2,testATM.withdraw(testUser2, atmOverBalance),"Insufficent ATM Funds");
        assertEquals(-4,testATM2.withdraw(testUser2, smallAmmount),"Insufficent ATM Funds (Bad Denominations");
        assertEquals(-3,testATM.withdraw(testUser, userNonValidAmmount),"Invalid Ammount Entered");
        assertEquals(0,testATM.withdraw(testUser, validAmmount),"Withdrawl Error");
    }

}
