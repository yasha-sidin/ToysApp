package ownerPartController;

import model.DbModel;
import model.entity.Toy;
import model.iGetModel;
import toysMachineApi.ToysMachineApi;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;

public class MainFrame extends JFrame {
    private final Font textAreaFont = new Font("Times New Roman", Font.PLAIN, 14);
    private final Font labelFont = new Font("Times New Roman", Font.PLAIN, 18);
    private final Font buttonFont = new Font("Times New Roman", Font.PLAIN, 18);
    private JLabel paramsLabel;
    private JTextArea allToys;
    private JButton addButton;
    private JButton settingsButton;
    private JButton updateProbabilityButton;
    private JButton deleteButton;
    private static HashMap<Integer, Toy> toysMap;
    private JScrollPane scrollPane;
    private final ToysMachineApi toysMachineApi;

    public MainFrame(iGetModel<Toy> model) {
        this.toysMachineApi = new ToysMachineApi(model);
        initialize();
    }
    private void initialize() {
        setTitle("ToysMachineOwnerApp");
        setResizable(false);
        setMinimumSize(new Dimension(1000, 900));
        setMaximumSize(new Dimension(1000, 900));
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);
        setLayout(new BorderLayout(50, 50));

        JPanel externalPanel = new JPanel(new BorderLayout());
        externalPanel.setBorder(new EmptyBorder(new Insets(15, 15, 15 , 15)));

        JPanel internalPanel = new JPanel();
        internalPanel.setBackground(new Color(229, 216, 216));
        internalPanel.setPreferredSize(new Dimension(250, 150));

        // SETTINGS PANEL
        JPanel settingsPanel = new JPanel();
        settingsPanel.setBackground(new Color(229, 216, 216));
        paramsLabel = new JLabel();
        paramsLabel.setFont(labelFont);
        paramsLabel.setText(getSensitiveData());
        settingsPanel.add(paramsLabel);

        // ALL TOYS
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(229, 216, 216));
        allToys = new JTextArea();
        allToys.setFont(textAreaFont);
        allToys.setEditable(true);
        allToys.setText(getToysList());
        allToys.setRows(40);
        allToys.setColumns(65);
        allToys.setBackground(new Color(240, 243, 252));
        scrollPane = new JScrollPane(allToys, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        infoPanel.add(scrollPane);

        // BUTTON PANEL
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(229, 216, 216));
        Box box = new Box(BoxLayout.X_AXIS);
        addButton = new JButton("Add");
        addButton.setFont(buttonFont);
        settingsButton = new JButton("Settings");
        settingsButton.setFont(buttonFont);
        updateProbabilityButton = new JButton("Update Toy");
        updateProbabilityButton.setFont(buttonFont);
        deleteButton = new JButton("Delete");
        deleteButton.setFont(buttonFont);
        box.add(addButton);
        box.add(Box.createRigidArea(new Dimension(60, 0)));
        box.add(settingsButton);
        box.add(Box.createRigidArea(new Dimension(60, 0)));
        box.add(updateProbabilityButton);
        box.add(Box.createRigidArea(new Dimension(60, 0)));
        box.add(deleteButton);
        buttonsPanel.add(box);





        // ASSEMBLING COMPONENTS
        internalPanel.add(settingsPanel, BorderLayout.NORTH);
        internalPanel.add(infoPanel, BorderLayout.CENTER);
        internalPanel.add(buttonsPanel, BorderLayout.SOUTH);

        externalPanel.add(internalPanel);
        add(externalPanel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void fillToysMap() {
        if (toysMap != null) {
            toysMap.clear();
        }
        List<Toy> toysList = toysMachineApi.getAllToys();
        for (int i = 1; i < toysList.size(); i++) {
            toysMap.put(i, toysList.get(i - 1));
        }
    }

    private String getToysList() {
        fillToysMap();
        if (toysMap == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int key : toysMap.keySet()) {
            stringBuilder.append(key).append(". ").append(toysMap.get(key).printToOwner()).append("\n");
        }
        return stringBuilder.toString();
    }

    private String getSensitiveData() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Current probability: ")
                     .append(toysMachineApi.getCurrentProbability().getValue())
                     .append(";  ")
                     .append("Settings values: { minAmountOfToys: ")
                     .append(toysMachineApi.getMinAmountOfToys())
                     .append(", choice's cost: ")
                     .append(toysMachineApi.getChoiceCost().getValue())
                     .append(" }");
        return stringBuilder.toString();
    }
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame(new DbModel());
    }
}

