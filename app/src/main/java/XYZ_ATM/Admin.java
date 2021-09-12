package XYZ_ATM;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;


public class Admin {
    ATM currATM;

    public void adminCheckATMBal(){
        currAtm.checkIndivBalance();
        System.out.println("Total Balance: " + currATM.checkTotalBalance());
    }

    public void adminAddATMFunds(){
        Scanner prompt = new Scanner(System.in);
        boolean complete = false;
        BigDecimal currency;
        int count;

        while(!complete) {
            System.out.println("What coin/note is being inserted?");
            currency = prompt.next();

            System.out.println("How many?");
            count = prompt.next();

            //update count for matching currency that's being inputted by admin
            for(Map.Entry<BigDecimal, Integer> entry : currATM.balance.entrySet()) {
                if(currency.equals(entry.getKey())){
                    currATM.balance.put(entry.getKey(), (BigDecimal.valueOf(entry.getValue()).add(count)).intValue());
                }
            }

            System.out.println("Continue with maintenance?" +
                    "\nYes = 1" +
                    "\nNo = 2");
            int adminInput = prompt.next();

            if (adminInput == 1) {
                complete = True;
                prompt.close()
            }
        }
    }


}