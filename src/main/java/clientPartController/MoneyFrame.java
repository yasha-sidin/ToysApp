package clientPartController;

import toysMachineApi.Money;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MoneyFrame extends JFrame {
    private final Font textFieldFont = new Font("Times New Roman", Font.BOLD, 16);
    private final Font labelFont = new Font("Times New Roman", Font.BOLD, 15);
    private final Font buttonFont = new Font("Times New Roman", Font.BOLD, 15);

    private ClientFrame father;

    public MoneyFrame(ClientFrame father) {
        this.father = father;
        initialize();
    }

    private void initialize() {
        father.setEnabled(false);
        setTitle("Add money");
        setResizable(false);
        setMinimumSize(new Dimension(500, 400));
        setMaximumSize(new Dimension(500, 400));
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);
        setLayout(new BorderLayout(50, 50));

        JPanel externalPanel = new JPanel(new BorderLayout());
        externalPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10 , 10)));

        JPanel internalPanel = new JPanel();
        internalPanel.setLayout(new BorderLayout(50, 50));
        internalPanel.setBackground(new Color(229, 216, 216));
        internalPanel.setPreferredSize(new Dimension(250, 150));

        JLabel infoLabel = new JLabel();
        infoLabel.setFont(labelFont);
        infoLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        infoLabel.setBackground(new Color(229, 216, 216));
        infoLabel.setText("<html><h2><center>Instruction</center></h2><p> &nbsp  &nbsp  &nbsp Please fill value of " +
                "money which you want to add into Toys Machine.</p></html>");

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setBackground(new Color(229, 216, 216));

        JPanel moneyPanel = new JPanel();
        moneyPanel.setLayout(new BoxLayout(moneyPanel, BoxLayout.X_AXIS));
        moneyPanel.setBackground(new Color(229, 216, 216));
        JLabel moneyLabel = new JLabel();
        moneyLabel.setFont(labelFont);
        moneyLabel.setText("Add money: ");
        moneyLabel.setBackground(new Color(229, 216, 216));
        JTextField moneyField = new JTextField();
        moneyField.setFont(textFieldFont);
        moneyField.setBackground(new Color(240, 243, 252));
        moneyField.setColumns(20);
        moneyPanel.add(moneyLabel);
        moneyPanel.add(moneyField);

        fieldsPanel.add(moneyPanel);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(229, 216, 216));
        Box box = new Box(BoxLayout.X_AXIS);
        box.setBackground(new Color(229, 216, 216));
        JButton addMoneyButton = new JButton("Add money");
        addMoneyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (moneyField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(externalPanel, "Please fill field.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                try {
                    father.getToysMachineApi().addMoneyToWallet(new Money(Double.parseDouble(moneyField.getText())));
                    father.setEnabled(true);
                    father.refreshData();
                    setVisible(false);
                } catch (Throwable ex) {
                    JOptionPane.showMessageDialog(externalPanel, "<html>" + ex.getMessage() + "</html>", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        addMoneyButton.setFont(buttonFont);
        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.setFont(buttonFont);
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                father.setEnabled(true);
            }
        });
        box.add(addMoneyButton);
        box.add(Box.createRigidArea(new Dimension(60, 0)));
        box.add(buttonCancel);
        buttonsPanel.add(box);

        internalPanel.add(infoLabel, BorderLayout.NORTH);
        internalPanel.add(fieldsPanel, BorderLayout.CENTER);
        internalPanel.add(buttonsPanel, BorderLayout.SOUTH);

        externalPanel.add(internalPanel);

        add(externalPanel);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                setVisible(false);
                father.setEnabled(true);
            }
        });
    }
}
