import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class DisplayFoodsForm extends JFrame {

    public DisplayFoodsForm() {
        setTitle("Display Foods");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        fetchFoodData(table);

        setVisible(true);
    }

    private void fetchFoodData(JTable table) {
        String url = "jdbc:mysql://localhost:3306/nutrition";
        String username = "root";
        String password = "";

        try {
            Connection conn = DriverManager.getConnection(url, username, password);

            String query = "SELECT name, calories, proteins, carbs, fats, weight FROM foods";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Vector<String> columnNames = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            Vector<Vector<Object>> rowData = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    if (columnName.equals("calories") || columnName.equals("proteins") || 
                        columnName.equals("carbs") || columnName.equals("fats") || 
                        columnName.equals("weight")) {
                        row.add(rs.getDouble(i) * 100); 
                    } else {
                        row.add(rs.getObject(i));
                    }
                }
                rowData.add(row);
            }

            DefaultTableModel model = new DefaultTableModel(rowData, columnNames);
            table.setModel(model);

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching food data.");
        }
    }

}
