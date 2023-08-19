package ownerPartController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddFrame extends JFrame {
    private final Font textFieldFont = new Font("Times New Roman", Font.BOLD, 16);
    private final Font labelFont = new Font("Times New Roman", Font.BOLD, 15);
    private final Font buttonFont = new Font("Times New Roman", Font.BOLD, 15);

    private MainFrame father;

//    public AddFrame(MainFrame father) {
//        this.father = father;
//        initialize();
//    }

    public AddFrame() {
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
        infoLabel.setText("<html><h2><center>Instruction</center></h2><p> &nbsp  &nbsp  &nbsp Please fill params which describe toy's name, probability of only one toy and count of this instance" +
                " in the fields. (Result probability equals multiplying of probability of one toy and count).</p></html>");

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(3, 1, 25, 25));
        fieldsPanel.setBackground(new Color(229, 216, 216));


        JPanel fieldsElementToy = new JPanel();
        fieldsElementToy.setLayout(new BoxLayout(fieldsElementToy, BoxLayout.X_AXIS));
        fieldsElementToy.setBackground(new Color(229, 216, 216));
        JLabel toyName = new JLabel();
        toyName.setFont(labelFont);
        toyName.setText("Toy's name: ");
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
        toyProb.setText("Toy's prob:   ");
        toyProb.setBackground(new Color(229, 216, 216));
        JTextField probField = new JTextField();
        probField.setFont(textFieldFont);
        probField.setBackground(new Color(240, 243, 252));
        probField.setColumns(20);
        fieldProbability.add(toyProb);
        fieldProbability.add(probField);

        JPanel fieldCount = new JPanel();
        fieldCount.setLayout(new BoxLayout(fieldCount, BoxLayout.X_AXIS));
        fieldCount.setBackground(new Color(229, 216, 216));
        JLabel count = new JLabel();
        count.setFont(labelFont);
        count.setText("Count:          ");
        count.setBackground(new Color(229, 216, 216));
        JTextField countField = new JTextField();
        countField.setFont(textFieldFont);
        countField.setBackground(new Color(240, 243, 252));
        countField.setColumns(20);
        fieldCount.add(count);
        fieldCount.add(countField);

        fieldsPanel.add(fieldsElementToy);
        fieldsPanel.add(fieldProbability);
        fieldsPanel.add(fieldCount);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(229, 216, 216));
        Box box = new Box(BoxLayout.X_AXIS);
        box.setBackground(new Color(229, 216, 216));
        JButton buttonAdd = new JButton("Add");
        buttonAdd.setFont(buttonFont);
        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.setFont(buttonFont);
        box.add(buttonAdd);
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
        var frame = new AddFrame();
    }
}
