package XYZ_ATM;

import java.math.BigDecimal;
import java.util.HashMap;

public class Admin {

    private String adminID;
    private String adminPin;

    public Admin(String adminID, String aPin) {
        this.adminID = adminID;
        this.adminPin = aPin;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getAdminPin() {
        return adminPin;
    }

    public void setAdminPin(String adminPin) {
        this.adminPin = adminPin;
    }

    public void adminSetBal(ATM currAtm, HashMap<BigDecimal, Integer> balance){
        currAtm.setBalance(balance);
    }


}