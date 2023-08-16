package ownerPartController;

import model.DbModel;
import model.entity.Toy;
import model.iGetModel;
import toysMachineApi.ToysMachineApi;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;

public class MainFrame extends JFrame {
    private final Font textAreaFont = new Font("Times New Roman", Font.PLAIN, 14);
    private final Font labelFont = new Font("Times New Roman", Font.PLAIN, 20);
    private final Font buttonFont = new Font("Times New Roman", Font.PLAIN, 20);
    private TextArea allToys;
    private JButton addButton;
    private JButton settingsButton;
    private JButton updateProbabilityButton;
    private JButton deleteButton;
    private static HashMap<Integer, Toy> toysMap;
    private JScrollBar scrollBar;
    private final ToysMachineApi toysMachineApi;

    public MainFrame(iGetModel<Toy> model) {
        this.toysMachineApi = new ToysMachineApi(model);
        initialize();
    }
    private void initialize() {
        setTitle("ToysMachineOwnerApp");
        setSize(900, 800);
        setMaximumSize(new Dimension(300, 400));
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);

        JPanel externalPanel = new JPanel(new BorderLayout());
        externalPanel.setBorder(new EmptyBorder(new Insets(15, 15, 15 , 15)));

        JPanel internalPanel = new JPanel();
        internalPanel.setBackground(Color.gray);
        internalPanel.setPreferredSize(new Dimension(250, 150));

        /*

          CREATING INFO PART OF THE FRAME

         */
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(229, 216, 216));
        JLabel paramsLabel = new JLabel();
        paramsLabel.setText(getSensitiveData());
        infoPanel.add(paramsLabel);


        internalPanel.add(infoPanel);
        externalPanel.add(internalPanel);
        add(externalPanel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void fillToysMap() {
        toysMap.clear();
        List<Toy> toysList = toysMachineApi.getAllToys();
        for (int i = 1; i < toysList.size(); i++) {
            toysMap.put(i, toysList.get(i - 1));
        }
    }

    private String getToysList() {
        fillToysMap();
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
                     .append(" ")
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

