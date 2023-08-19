package ownerPartController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UpdateFrame extends JFrame {
    private final Font textFieldFont = new Font("Times New Roman", Font.BOLD, 16);
    private final Font labelFont = new Font("Times New Roman", Font.BOLD, 15);
    private final Font buttonFont = new Font("Times New Roman", Font.BOLD, 15);

    private MainFrame father;

//    public AddFrame(MainFrame father) {
//        this.father = father;
//        initialize();
//    }

    public UpdateFrame() {
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
        infoLabel.setText("<html><h2><center>Instruction</center></h2><p> &nbsp  &nbsp  &nbsp First of all, please fill number of toy from list which you " +
                "want to update. After that, fill params which describe toy's name and probability of this toy" +
                " in the fields.</p></html>");

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(3, 1, 25, 25));
        fieldsPanel.setBackground(new Color(229, 216, 216));

        JPanel toyNumberPanel = new JPanel();
        toyNumberPanel.setLayout(new BoxLayout(toyNumberPanel, BoxLayout.X_AXIS));
        toyNumberPanel.setBackground(new Color(229, 216, 216));
        JLabel numberToy = new JLabel();
        numberToy.setFont(labelFont);
        numberToy.setText("Toy's number: ");
        numberToy.setBackground(new Color(229, 216, 216));
        JTextField countField = new JTextField();
        countField.setFont(textFieldFont);
        countField.setBackground(new Color(240, 243, 252));
        countField.setColumns(20);
        toyNumberPanel.add(numberToy);
        toyNumberPanel.add(countField);

        JPanel fieldsElementToy = new JPanel();
        fieldsElementToy.setLayout(new BoxLayout(fieldsElementToy, BoxLayout.X_AXIS));
        fieldsElementToy.setBackground(new Color(229, 216, 216));
        JLabel toyName = new JLabel();
        toyName.setFont(labelFont);
        toyName.setText("Toy's name:    ");
        toyName.setBackground(new Color(229, 216, 216));
        JTextField toyField = new JTextField();
        toyField.setFont(textFieldFont);
        toyField.setBackground(new Color(240, 243, 252));
        toyField.setColumns(20);
        fieldsElementToy.add(toyName);
        fieldsElementToy.add(toyField);

        JPanel fieldProbability = new JPanel();
        fieldProbability.setLayout(new BoxLayout(fieldProbability, BoxLayout.LINE_AXIS));
        fieldProbability.setBackground(new Color(229, 216, 216));
        JLabel toyProb = new JLabel();
        toyProb.setFont(labelFont);
        toyProb.setText("Toy's prob:      ");
        toyProb.setBackground(new Color(229, 216, 216));
        JTextField probField = new JTextField();
        probField.setFont(textFieldFont);
        probField.setBackground(new Color(240, 243, 252));
        probField.setColumns(20);
        fieldProbability.add(toyProb);
        fieldProbability.add(probField);

        fieldsPanel.add(toyNumberPanel);
        fieldsPanel.add(fieldsElementToy);
        fieldsPanel.add(fieldProbability);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(229, 216, 216));
        Box box = new Box(BoxLayout.X_AXIS);
        box.setBackground(new Color(229, 216, 216));
        JButton buttonUpdate = new JButton("Update");
        buttonUpdate.setFont(buttonFont);
        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.setFont(buttonFont);
        box.add(buttonUpdate);
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
        var frame = new UpdateFrame();
    }
}
