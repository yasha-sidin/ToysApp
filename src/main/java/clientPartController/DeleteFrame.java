package clientPartController;

import ownerPartController.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DeleteFrame extends JFrame {
    private final Font textFieldFont = new Font("Times New Roman", Font.BOLD, 16);
    private final Font labelFont = new Font("Times New Roman", Font.BOLD, 15);
    private final Font buttonFont = new Font("Times New Roman", Font.BOLD, 15);

    private MainFrame father;

//    public AddFrame(MainFrame father) {
//        this.father = father;
//        initialize();
//    }

    public DeleteFrame() {
        initialize();
    }

    private void initialize() {
//        father.setEnabled(false);
        setTitle("Add toys");
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
        infoLabel.setText("<html><h2><center>Instruction</center></h2><p> &nbsp  &nbsp  &nbsp Please fill number of toy from list which instance " +
                "of you need to delete.</p></html>");

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setBackground(new Color(229, 216, 216));

        JPanel fieldToyNumber = new JPanel();
        fieldToyNumber.setLayout(new BoxLayout(fieldToyNumber, BoxLayout.X_AXIS));
        fieldToyNumber.setBackground(new Color(229, 216, 216));
        JLabel toyNumber = new JLabel();
        toyNumber.setFont(labelFont);
        toyNumber.setText("Toy's number: ");
        toyNumber.setBackground(new Color(229, 216, 216));
        JTextField toyField = new JTextField();
        toyField.setFont(textFieldFont);
        toyField.setBackground(new Color(240, 243, 252));
        toyField.setColumns(20);
        fieldToyNumber.add(toyNumber);
        fieldToyNumber.add(toyField);

        fieldsPanel.add(fieldToyNumber);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(229, 216, 216));
        Box box = new Box(BoxLayout.X_AXIS);
        box.setBackground(new Color(229, 216, 216));
        JButton buttonDelete = new JButton("Delete");
        buttonDelete.setFont(buttonFont);
        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.setFont(buttonFont);
        box.add(buttonDelete);
        box.add(Box.createRigidArea(new Dimension(60, 0)));
        box.add(buttonCancel);
        buttonsPanel.add(box);

        internalPanel.add(infoLabel, BorderLayout.NORTH);
        internalPanel.add(fieldsPanel, BorderLayout.CENTER);
        internalPanel.add(buttonsPanel, BorderLayout.SOUTH);

        externalPanel.add(internalPanel);

        add(externalPanel);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        var frame = new DeleteFrame();
    }
}
