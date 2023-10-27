import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class AptListController implements ListSelectionListener {
    private AptListScreen screen;
    Apartment apartment = null;

    public AptListController(AptListScreen screen) {
        this.screen = screen;
        screen.getTblApts().getSelectionModel().addListSelectionListener(this);
        apartment = new Apartment();
    }

    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = screen.getTblApts().getSelectedRow();
            if (selectedRow >= 0) {
                String idS = screen.getTblApts().getValueAt(selectedRow, 0).toString();
                int id = Integer.parseInt(idS);
                apartment = Application.getInstance().getDataAdapter().loadApartment(id);
                User poster = Application.getInstance().getDataAdapter().loadUserByID(apartment.getPosterID());

                AptDetailScreen aptDetailScreen = Application.getInstance().getAptDetailScreen();
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