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
    public JTextField txtSearchByPriceLow = new JTextField(3);
    public JTextField txtSearchByPriceHigh = new JTextField(3);
    private String[] types = { "1b1b", "2b2b" };
    public JComboBox<String> typeSelect = new JComboBox<>(types);

    JLabel labelLow = new JLabel("Low");
    JLabel labelHigh = new JLabel("High");
    JButton btnSearchByPrice = new JButton("Search By Price");


    public AptListScreen() {
        this.setTitle("Apartment listings");
        this.setBounds(750, 260, 550, 500);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        JPanel main = new JPanel(new SpringLayout());
        main.add(labelLow);
        main.add(txtSearchByPriceLow);
        main.add(labelHigh);
        main.add(txtSearchByPriceHigh);
        main.add(btnSearchByPrice);
        SpringUtilities.makeCompactGrid(main,
                1, 5, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad
        this.getContentPane().add(main);

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
