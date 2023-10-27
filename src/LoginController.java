
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// This is the controller for both login screen and register
public class LoginController {
    public void onLogin(ActionEvent e) {
        // if (e.getSource() == this.getBtnLogin()) {
        String username = AptAppManager.getInstance().getLoginScreen().getTxtUserName().getText().trim();
        String password = AptAppManager.getInstance().getLoginScreen().getTxtPassword().getText().trim();

        System.out.println("Login with username = " + username + " and password = " + password);
        User user = AptAppManager.getInstance().getDataAdapter().loadUser(username, password);

        if (user == null) {
            JOptionPane.showMessageDialog(null, "This user does not exist!");
        } else {
            AptAppManager.getInstance().setCurrentUser(user);
            AptAppManager.getInstance().getLoginScreen().setVisible(false);
            AptAppManager.getInstance().getMainScreen().setVisible(true);
        }
    }

    public void onReg(ActionEvent e) {
        String username = AptAppManager.getInstance().getRegScreen().getTxtUserName().getText().trim();
        String password = AptAppManager.getInstance().getRegScreen().getTxtPassword().getText().trim();
        String name = AptAppManager.getInstance().getRegScreen().getTxtFullName().getText().trim();
        String email = AptAppManager.getInstance().getRegScreen().getTxtEmail().getText().trim();

        System.out.println(
                "Register with username = " + username + " and password = " + password + " " + name + " " + email);
        User user = new User();
        user.setUsername(username);
        user.setFullName(name);
        user.setEmail(email);
        user.setPassword(password);
        // return;
        if (AptAppManager.getInstance().getDataAdapter().saveUser(user)) {
            // If register is successful, log the user in
            AptAppManager.getInstance().getRegScreen().setVisible(false);
            AptAppManager.getInstance().getLoginScreen().setVisible(false);
            AptAppManager.getInstance().setCurrentUser(user);
            AptAppManager.getInstance().getMainScreen().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "username already exists!");
            return;
        }

        if (user == null) {
            JOptionPane.showMessageDialog(null, "This user does not exist!");
        } else {
            AptAppManager.getInstance().setCurrentUser(user);
            AptAppManager.getInstance().getLoginScreen().setVisible(false);
            AptAppManager.getInstance().getMainScreen().setVisible(true);
        }
    }

}
