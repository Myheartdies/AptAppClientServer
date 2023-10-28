import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AptListScreen extends JFrame {
    private DefaultTableModel apts = new DefaultTableModel();
    private JTable tblApts = new JTable(apts);
    private List<Apartment> aptList = new ArrayList<>();

    public JTable getTblApts() {
        return tblApts;
    }

    public void addRow(Object[] row) {
        apts.addRow(row);
    }

    public void setAptList(List<Apartment> aptList) {
        apts.setRowCount(0);
        this.aptList.clear();
        this.aptList.addAll(aptList);
        for (Apartment apartment : aptList) {
            Object[] row = {
                    apartment.getID(),
                    apartment.getAptName(),
                    apartment.getType(),
                    apartment.getPrice(),
                    apartment.getAddress()
            };
            apts.addRow(row);
        }
    }

    public AptListScreen() {
        this.setTitle("Apartment listings");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setBounds(750, 200, 550, 650);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        apts.addColumn("ApartmentID");
        apts.addColumn("Apartment Name");
        apts.addColumn("Type");
        apts.addColumn("Price $/month");
        apts.addColumn("Address");

        Font font = new Font(tblApts.getFont().getName(), Font.PLAIN, 18);
        tblApts.getTableHeader().setFont(font);
        tblApts.setFont(font);
        tblApts.setShowGrid(true);
        tblApts.setGridColor(Color.BLACK);

        int rowHeight = font.getSize() + 4; // Adjust the value as needed
        tblApts.setRowHeight(rowHeight);

        JScrollPane scrollable = new JScrollPane(tblApts, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollable.setBounds(20, 120, 800, 300);
        this.getContentPane().add(scrollable);
    }
}
