import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.Instant;

public class MainScreen extends JFrame {

    private JButton btnListing = new JButton("View listings");
    private JButton btnPost = new JButton("Post a listing");
    // The label of the new username panel, at class scope so it can be access by
    // the event listener
    private JLabel userLabel = new JLabel("User: ");
    private JLabel nameLabel = new JLabel("Name: ");

    public MainScreen() {
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(650, 350, 400, 300);

        btnListing.setPreferredSize(new Dimension(140, 50));
        btnPost.setPreferredSize(new Dimension(140, 50));

        JLabel title = new JLabel("Rental sharing System");
        title.setAlignmentY(CENTER_ALIGNMENT);
        title.setFont(new Font("Sans Serif", Font.BOLD, 24));
        JPanel panelTitle = new JPanel();
        panelTitle.add(title);
        // panelTitle.setBackground(Color.red);
        this.getContentPane().add(panelTitle);

        // A new panel is created and added for username
        userLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        nameLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        // The labels will be centered on x axis
        userLabel.setAlignmentX(CENTER_ALIGNMENT);
        nameLabel.setAlignmentX(CENTER_ALIGNMENT);
        JPanel panelUser = new JPanel();
        panelUser.add(userLabel);
        panelUser.add(nameLabel);
        panelUser.setLayout(new BoxLayout(panelUser, BoxLayout.Y_AXIS));
        this.getContentPane().add(panelUser);

        JPanel panelButton = new JPanel();
        panelButton.add(btnListing);
        panelButton.add(btnPost);

        this.getContentPane().add(panelButton);

        btnListing.addActionListener(new ActionListener() { // when controller is simple, we can declare it on the fly
            public void actionPerformed(ActionEvent e) {
                AptListScreen aptListScreen = AptAppManager.getInstance().getAptList();
                SQLDataAdapter dataAdapter = AptAppManager.getInstance().getDataAdapter();
                aptListScreen.setAptList(dataAdapter.loadAptList());
                aptListScreen.setVisible(true);
            }
        });

        btnPost.addActionListener(new ActionListener() { // when controller is simple, we can declare it on the fly
            public void actionPerformed(ActionEvent e) {
                AptAppManager.getInstance().getNewPostScreen().setVisible(true);
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                onWindowOpen();
            }
        });
    }

    // This function change the text of the userPanel to make it match the current
    // user name
    // on entering this window
    void onWindowOpen() {
        User currUser;
        currUser = AptAppManager.getInstance().getCurrentUser();
        if (currUser != null) {
            userLabel.setText("User: " + currUser.getUsername());
            nameLabel.setText("Name:" + currUser.getFullName());
        }
    }

}
