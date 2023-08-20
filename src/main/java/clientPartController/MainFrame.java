package clientPartController;

import model.DbModel;
import model.entity.Toy;
import model.iGetModel;
import toysMachineApi.ToysMachineApi;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class MainFrame extends JFrame {
    private final Font textAreaFont = new Font("Times New Roman", Font.BOLD, 14);
    private final Font labelFont = new Font("Times New Roman", Font.BOLD, 18);
    private final Font buttonFont = new Font("Times New Roman", Font.BOLD, 18);
    private JLabel paramsLabel;
    private JTextArea allToys;
    private JButton addMoneyButton;
    private JButton playButton;
    private JButton getPrizeButton;
    private JButton seePrizesButton;
    private JTextArea prizesQueue;
    private static HashMap<Integer, Toy> queueMap = new HashMap<>();
    private static HashMap<Integer, Toy> toysMap = new HashMap<>();
    private final ToysMachineApi toysMachineApi;

    public MainFrame(iGetModel<Toy> model) {
        this.toysMachineApi = new ToysMachineApi(model);
        initialize();
    }
    private void initialize() {
        setTitle("Toys Machine");
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
        allToys.setEditable(false);
        allToys.setText(getToysList());
        allToys.setRows(44);
        allToys.setColumns(35);
        allToys.setBackground(new Color(240, 243, 252));
        var scrollPane = new JScrollPane(allToys, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        infoPanel.add(scrollPane);

        prizesQueue = new JTextArea();
        prizesQueue.setText(getQueueMap());
        prizesQueue.setFont(textAreaFont);
        prizesQueue.setRows(44);
        prizesQueue.setColumns(28);
        prizesQueue.setEditable(false);
        prizesQueue.setBackground(new Color(240, 243, 252));
        var scrollPane2 = new JScrollPane(prizesQueue, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        infoPanel.add(scrollPane2);



        // BUTTON PANEL
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(new Color(229, 216, 216));
        Box box = new Box(BoxLayout.X_AXIS);
        addMoneyButton = new JButton("Add Money");
        addMoneyButton.setFont(buttonFont);
        playButton = new JButton("Play");
        playButton.setFont(buttonFont);
        getPrizeButton = new JButton("Get prize");
        getPrizeButton.setFont(buttonFont);
        seePrizesButton = new JButton("See prizes");
        seePrizesButton.setFont(buttonFont);
        box.add(addMoneyButton);
        box.add(Box.createRigidArea(new Dimension(60, 0)));
        box.add(playButton);
        box.add(Box.createRigidArea(new Dimension(60, 0)));
        box.add(getPrizeButton);
        box.add(Box.createRigidArea(new Dimension(60, 0)));
        box.add(seePrizesButton);
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
        for (int i = 1; i <= toysList.size(); i++) {
            toysMap.put(i, toysList.get(i - 1));
        }
    }

    private void fillQueueMap() {
        if (queueMap != null) {
            queueMap.clear();
        }
        PriorityQueue<Toy> toysQueue = toysMachineApi.getPrizesQueue();
        int i = 1;
        for (Toy toy : toysQueue) {
            toysMap.put(i, toy);
            i++;
        }
    }

    private String getQueueMap() {
        fillQueueMap();
        if (queueMap.isEmpty()) {
            return "Prizes queue: \n";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Prizes queue: \n");
        for (int key : queueMap.keySet()) {
            stringBuilder.append(key).append(". ").append(queueMap.get(key).printToOwner()).append("\n");
        }
        return stringBuilder.toString();
    }

    private String getToysList() {
        fillToysMap();
        if (toysMap.isEmpty()) {
            return "All toys: \n";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("All toys: \n");
        for (int key : toysMap.keySet()) {
            stringBuilder.append(key).append(". ").append(toysMap.get(key).printToClient()).append("\n");
        }
        return stringBuilder.toString();
    }

    public void refreshData() {
        paramsLabel.setText(getSensitiveData());
        allToys.setText(getToysList());
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
                .append(toysMachineApi.getChoiceCost().getCurrencyChar())
                .append(" }");
        return stringBuilder.toString();
    }

    public Toy getToyByNumber(int number) {
        return toysMap.get(number);
    }

    public HashMap<Integer, Toy> getToysMap() {
        return toysMap;
    }

    public ToysMachineApi getToysMachineApi() {
        return toysMachineApi;
    }
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame(new DbModel());
    }
}


