import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {
    private JTextField txtUserName = new JTextField(10);
    private JTextField txtPassword = new JTextField(10);
    private JButton btnReg = new JButton("New Register");
    private JButton btnLogin = new JButton("Login");

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public JTextField getTxtPassword() {
        return txtPassword;
    }

    public JTextField getTxtUserName() {
        return txtUserName;
    }

    public LoginScreen() {

        this.setBounds(650, 350, 350, 180);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Rental Sharing System"));
        this.getContentPane().add(titlePanel);

        JPanel main = new JPanel(new SpringLayout());
        main.add(new JLabel("Username:"));
        main.add(txtUserName);
        main.add(new JLabel("Password:"));
        main.add(txtPassword);

        SpringUtilities.makeCompactGrid(main,
                2, 2, // rows, cols
                6, 6, // initX, initY
                6, 6); // xPad, yPad

        this.getContentPane().add(main);

        JPanel buttons = new JPanel();
        buttons.add(btnReg);
        buttons.add(btnLogin);
        this.getContentPane().add(buttons);

        btnReg.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Application.getInstance().getRegScreen().setVisible(true);
                    }
                });
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.getInstance().getLoginScreenCtrl().onLogin(e);
            }
        }

        );
    }
}
