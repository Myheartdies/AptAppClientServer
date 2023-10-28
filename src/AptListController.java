import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AptListController implements ListSelectionListener, ActionListener {
    private AptListScreen screen;
    Apartment apartment = null;

    DataAccess myDAO;

    public AptListController(AptListScreen screen, DataAccess dao) {
        this.screen = screen;
        this.myDAO = dao;
        screen.getTblApts().getSelectionModel().addListSelectionListener(this);
        apartment = new Apartment();
        screen.btnSearchByPrice.addActionListener(this);
        screen.btnSearchByType.addActionListener(this);
        screen.btnReset.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == screen.btnSearchByPrice) {      // button Load is clicked
            searchByPrice();
        }
        else if (e.getSource() == screen.btnSearchByType) {      // button Save is clicked
            searchByType();
        } else if (e.getSource() == screen.btnReset) {
            screen.setAptList(myDAO.loadAptList());
        }
    }

    private void searchByType() {
        List<Apartment> apartments = new ArrayList<>();
        try {
            String aptType = (String) screen.typeSelect.getSelectedItem();
            apartments = myDAO.loadAptByType(aptType);
            screen.setAptList(apartments);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null,
                    "Invalid format for numbers!");
            ex.printStackTrace();
        }
    }

    private void searchByPrice() {
        List<Apartment> apartments = new ArrayList<>();
        try {
            double low = Double.parseDouble(screen.txtSearchByPriceLow.getText());
            double high = Double.parseDouble(screen.txtSearchByPriceHigh.getText());
            apartments = myDAO.loadAptByPrice(low, high);
            screen.setAptList(apartments);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null,
                    "Invalid format for numbers!");
            ex.printStackTrace();
        }
    }

    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = screen.getTblApts().getSelectedRow();
            if (selectedRow >= 0) {
                String idS = screen.getTblApts().getValueAt(selectedRow, 0).toString();
                int id = Integer.parseInt(idS);
                // set the id to detail screen for saving the apt to wishlist
                AptAppManager.getInstance().getAptDetailController().setCurrAptID(id);
                apartment = AptAppManager.getInstance().getDataAccess().loadAptByID(id);
                User poster = AptAppManager.getInstance().getDataAccess().loadUserByID(apartment.getPosterID());

                AptDetailScreen aptDetailScreen = AptAppManager.getInstance().getAptDetailScreen();
                aptDetailScreen.setPropertyName(apartment.getAptName());
                aptDetailScreen.setAddress(apartment.getAddress());
                aptDetailScreen.setArea(Double.toString(apartment.getArea()));
                aptDetailScreen.setType(apartment.getType());
                aptDetailScreen.setPrice(Double.toString(apartment.getPrice()));
                aptDetailScreen.setAvailableDate(apartment.getAvailableDate());
                aptDetailScreen.setDescri(apartment.getDescr());
                aptDetailScreen.setPosterName(poster.getFullName());
                aptDetailScreen.setPosterEmail(poster.getEmail());
                aptDetailScreen.setVisible(true);
            }
        }
    }
}