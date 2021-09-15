package XYZ_ATM;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;


public class Admin {
    public void adminCheckATMBal(ATM currATM){
        currAtm.checkIndivBalance();
        System.out.println("Total Balance: " + currATM.checkTotalBalance());
    }

    public void adminAddATMFunds(ATM currATM, HashMap<BigDecimal, Integer> userInput){
        currATM.setBalance(userInput);
    }


}