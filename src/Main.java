import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Nutrition Tracker");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JButton addButton = new JButton("Add New Food");
        addButton.setBounds(450, 10, 120, 30);
        addButton.setFocusable(false);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddFoodForm();
            }
        });

        JButton displayButton = new JButton("Display Foods");
        displayButton.setBounds(450, 50, 120, 30);
        displayButton.setFocusable(false);

        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DisplayFoodsForm();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(addButton);
        panel.add(displayButton);
        frame.add(panel, BorderLayout.NORTH);
        frame.setVisible(true);
    }
}
