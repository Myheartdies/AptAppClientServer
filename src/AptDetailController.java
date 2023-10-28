import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AptDetailController implements ActionListener {
    AptDetailScreen aptDetailScreen;
    DataAccess myDAO;

    private int currAptID;

    public void setCurrAptID(int currAptID) {
        this.currAptID = currAptID;
    }

    public AptDetailController(AptDetailScreen aptDetailScreen, DataAccess dao) {
        this.aptDetailScreen = aptDetailScreen;
        myDAO = dao;
        aptDetailScreen.btnAddWishList.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == aptDetailScreen.btnAddWishList) {
            System.out.println("Current Apt id is " + currAptID);
            WishApt wishApt = new WishApt();
            wishApt.aptID = currAptID;
            wishApt.userID = AptAppManager.getInstance().getCurrentUser().getUserID();
            myDAO.saveApt2WishList(wishApt);

            Apartment apartment = myDAO.loadAptByID(currAptID);
            Object[] row = new Object[5];
            row[0] = apartment.getID();
            row[1] = apartment.getAptName();
            row[2] = apartment.getType();
            row[3] = apartment.getPrice();
            row[4] = apartment.getAddress();
            AptAppManager.getInstance().getWishListScreen().addRow(row);

            JOptionPane.showMessageDialog(
                    null, "Apartment has been added in WishList!");
        }
    }
}
