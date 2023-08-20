package clientPartController;

import model.DbModel;
import model.entity.Toy;
import model.iGetModel;
import toysMachineApi.ToysMachineApi;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class ClientFrame extends JFrame {
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

    public ClientFrame(iGetModel<Toy> model) {
        this.toysMachineApi = new ToysMachineApi(model);
        initialize();
    }
    private void initialize() {
        if (!toysMachineApi.checkMinAmount()) {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "<html>There are not enough toys in machine. " +
                    "Please come back later.</html>", "Info", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
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
        addMoneyButton.addActionListener(e -> new MoneyFrame(this));
        playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    JOptionPane.showMessageDialog(externalPanel, toysMachineApi.usePlayingChoiceAndGetInfo(), "Info", JOptionPane.INFORMATION_MESSAGE);
                    refreshData();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(externalPanel, "<html>" + ex.getMessage() + "</html>", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        playButton.setFont(buttonFont);
        getPrizeButton = new JButton("Get prize");
        getPrizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Toy toy = toysMachineApi.getPrizeFromQueue();
                    if (toy == null) {
                        JOptionPane.showMessageDialog(externalPanel, "There aren't any prize in queue.", "Info", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    JOptionPane.showMessageDialog(externalPanel, String.format("<html>%s was saved into your file. " +
                            "Push on button 'see toys' to see all your prizes for all time.</html>", toy.getName()), "Info", JOptionPane.INFORMATION_MESSAGE);
                    refreshData();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(externalPanel, "<html>" + ex.getMessage() + "</html>", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        getPrizeButton.setFont(buttonFont);
        seePrizesButton = new JButton("See prizes");
        seePrizesButton.addActionListener(e -> {
            try {
                new ClientToysFrame(this);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(externalPanel, "<html>" + ex.getMessage() + "</html>", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
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

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                String info;
                if (toysMachineApi.getPrizesQueue().isEmpty()) {
                    info = "Your change: " + toysMachineApi.getWallet().getMoney().getValue() + ".";
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Your change: ").append(toysMachineApi.getWallet().getMoney().getValue()).append(". Toy(s): ");
                    while (!toysMachineApi.getPrizesQueue().isEmpty()) {
                        try {
                            Toy toy = toysMachineApi.getPrizeFromQueue();
                            stringBuilder.append(toy.getName()).append(", ");
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(externalPanel, "<html>" + ex.getMessage() + "</html>", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length() -1 ).append(" was(were) saved into file");
                    info = stringBuilder.toString();
                }
                JOptionPane.showMessageDialog(externalPanel, "<html>" + info + "</html>", "Info", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        });
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
        for (Object toy : toysQueue.toArray()) {
            queueMap.put(i, (Toy)toy);
            i++;
        }
    }

    private String getQueueMap() {
        fillQueueMap();
        if (queueMap.isEmpty()) {
            System.out.println("Empty");
            return "Prizes queue: \n";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Prizes queue: \n");
        System.out.println(queueMap);
        for (int key : queueMap.keySet()) {
            stringBuilder.append(key).append(". ").append(queueMap.get(key).printToClient()).append("\n");
        }
        System.out.println(queueMap);
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
        prizesQueue.setText(getQueueMap());
    }

    private String getSensitiveData() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Balance: ")
                     .append(toysMachineApi.getWallet().getMoney().getValue())
                     .append(toysMachineApi.getWallet().getMoney().getCurrencyChar())
                     .append(" Choice cost: ")
                     .append(toysMachineApi.getChoiceCost().getValue())
                     .append(toysMachineApi.getChoiceCost().getCurrencyChar());
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
}


