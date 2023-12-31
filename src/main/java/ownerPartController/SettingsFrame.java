package ownerPartController;

import toysMachineApi.Money;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SettingsFrame extends JFrame {
    private final Font textFieldFont = new Font("Times New Roman", Font.BOLD, 16);
    private final Font labelFont = new Font("Times New Roman", Font.BOLD, 15);
    private final Font buttonFont = new Font("Times New Roman", Font.BOLD, 15);
    private OwnerFrame father;

    public SettingsFrame(OwnerFrame father) {
        this.father = father;
        initialize();
    }

    private void initialize() {
        father.setEnabled(false);
        setTitle("Settings");
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
        infoLabel.setText("<html><h2><center>Instruction</center></h2><p> &nbsp  &nbsp  &nbsp Please fill params of settings in the fields." +
                " (Cost of one choice in toys machine, minimum amount of toys in toys machine which need to be in that to continue playing).</p></html>");

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(3, 1, 25, 25));
        fieldsPanel.setBackground(new Color(229, 216, 216));


        JPanel fillChoiceCost = new JPanel();
        fillChoiceCost.setLayout(new BoxLayout(fillChoiceCost, BoxLayout.X_AXIS));
        fillChoiceCost.setBackground(new Color(229, 216, 216));
        JLabel cost = new JLabel();
        cost.setFont(labelFont);
        cost.setText("Choice cost:  ");
        cost.setBackground(new Color(229, 216, 216));
        JTextField costField = new JTextField();
        costField.setFont(textFieldFont);
        costField.setBackground(new Color(240, 243, 252));
        costField.setColumns(20);
        fillChoiceCost.add(cost);
        fillChoiceCost.add(costField);

        JPanel fillMinToys = new JPanel();
        fillMinToys.setLayout(new BoxLayout(fillMinToys, BoxLayout.LINE_AXIS));
        fillMinToys.setBackground(new Color(229, 216, 216));
        JLabel minToys = new JLabel();
        minToys.setFont(labelFont);
        minToys.setText("Min amount : ");
        minToys.setBackground(new Color(229, 216, 216));
        JTextField minField = new JTextField();
        minField.setFont(textFieldFont);
        minField.setBackground(new Color(240, 243, 252));
        minField.setColumns(20);
        fillMinToys.add(minToys);
        fillMinToys.add(minField);

        fieldsPanel.add(fillChoiceCost);
        fieldsPanel.add(fillMinToys);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(229, 216, 216));
        Box box = new Box(BoxLayout.X_AXIS);
        box.setBackground(new Color(229, 216, 216));
        JButton buttonSave = new JButton("Save");
        buttonSave.setFont(buttonFont);
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (minField.getText().isEmpty() || costField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(externalPanel, "Please fill all fields.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                try {
                    Money money = new Money(Double.parseDouble(costField.getText()));
                    int minAmount = Integer.parseInt(minField.getText());
                    father.getToysMachineApi().setSettings(money, minAmount);
                    father.setEnabled(true);
                    father.refreshData();
                    setVisible(false);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(externalPanel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.setFont(buttonFont);
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                father.setEnabled(true);
            }
        });
        box.add(buttonSave);
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
