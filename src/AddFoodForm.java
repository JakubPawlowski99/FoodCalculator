import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class AddFoodForm extends JFrame {

    private JTextField nameField, caloriesField, proteinsField, carbsField, fatsField, weightField;

    public AddFoodForm() {
        setTitle("Add New Food");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2));

        JLabel nameLabel = new JLabel("Food Name:");
        nameField = new JTextField(20);
        JLabel caloriesLabel = new JLabel("Calories:");
        caloriesField = new JTextField(20);
        JLabel proteinsLabel = new JLabel("Proteins:");
        proteinsField = new JTextField(20);
        JLabel carbsLabel = new JLabel("Carbs:");
        carbsField = new JTextField(20);
        JLabel fatsLabel = new JLabel("Fats:");
        fatsField = new JTextField(20);
        JLabel weightLabel = new JLabel("Weight (grams):");
        weightField = new JTextField(20);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFoodToDatabase();
            }
        });

        add(nameLabel);
        add(nameField);
        add(caloriesLabel);
        add(caloriesField);
        add(proteinsLabel);
        add(proteinsField);
        add(carbsLabel);
        add(carbsField);
        add(fatsLabel);
        add(fatsField);
        add(weightLabel);
        add(weightField);
        add(submitButton);

        setVisible(true);
    }

    private void saveFoodToDatabase() {
        String name = nameField.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Food name is required.");
            return;
        }

        try {
            double calories = Double.parseDouble(caloriesField.getText());
            double proteins = Double.parseDouble(proteinsField.getText());
            double carbs = Double.parseDouble(carbsField.getText());
            double fats = Double.parseDouble(fatsField.getText());
            double weight = Double.parseDouble(weightField.getText());

            if (weight <= 0) {
                JOptionPane.showMessageDialog(this, "Weight must be greater than 0.");
                return;
            }

            double caloriesPerGram = calories / weight;
            double proteinsPerGram = proteins / weight;
            double carbsPerGram = carbs / weight;
            double fatsPerGram = fats / weight;

            String url = "jdbc:mysql://localhost:3306/nutrition";
            String username = "root";
            String password = "";

            try {
                Connection conn = DriverManager.getConnection(url, username, password);

                String query = "INSERT INTO foods (name, calories, proteins, carbs, fats, weight) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, name);
                stmt.setDouble(2, caloriesPerGram);
                stmt.setDouble(3, proteinsPerGram);
                stmt.setDouble(4, carbsPerGram);
                stmt.setDouble(5, fatsPerGram);
                stmt.setDouble(6, weight / weight);
                stmt.executeUpdate();

                stmt.close();
                conn.close();

                JOptionPane.showMessageDialog(this, "Food added successfully!");
                dispose();

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding food to the database.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for all fields.");
        }
    }
}
