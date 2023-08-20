package clientPartController;

import toysMachineApi.Money;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class ClientToysFrame extends JFrame {
    private final Font textFieldFont = new Font("Times New Roman", Font.BOLD, 16);
    private final Font labelFont = new Font("Times New Roman", Font.BOLD, 15);
    private final Font textAreaFont = new Font("Times New Roman", Font.BOLD, 17);
    private ClientFrame father;

    public ClientToysFrame(ClientFrame father) throws IOException {
        this.father = father;
        initialize();
    }

    private void initialize() throws IOException {
        father.setEnabled(false);
        setTitle("Your prizes");
        setResizable(false);
        setMinimumSize(new Dimension(800, 700));
        setMaximumSize(new Dimension(800, 700));
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

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(229, 216, 216));
        JLabel infoLabel = new JLabel();
        infoLabel.setFont(labelFont);
        infoLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        infoLabel.setBackground(new Color(229, 216, 216));
        infoLabel.setText("<html><h2><center>Your prizes</center></h2></html>");
        infoPanel.add(infoLabel);

        JPanel areaPanel = new JPanel();
        areaPanel.setBackground(new Color(229, 216, 216));
        JTextArea allToys = new JTextArea();
        allToys.setFont(textAreaFont);
        allToys.setEditable(false);
        if (father.getToysMachineApi().getClientPrizes().isEmpty()) {
            allToys.setText("There aren't any prizes");
        } else {
            allToys.setText(father.getToysMachineApi().getClientPrizes());
        }
        allToys.setRows(31);
        allToys.setColumns(38);
        allToys.setBackground(new Color(240, 243, 252));
        var scrollPane = new JScrollPane(allToys, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        areaPanel.add(scrollPane);

        internalPanel.add(infoPanel, BorderLayout.NORTH);
        internalPanel.add(areaPanel, BorderLayout.SOUTH);
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
