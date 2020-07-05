package scripts.Nodes;

import org.tribot.api.General;
import org.tribot.api2007.Login;

public class LoginUser extends Node {

    @Override
    public boolean validate() {
        return Login.getLoginState() == Login.STATE.LOGINSCREEN;
    }

    @Override
    public void execute() {
        boolean isLoggedIn = Login.login();
        if(!isLoggedIn) {
            General.println("Error logging in");
        }
    }
}
