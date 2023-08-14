package ownerPartController;

import model.entity.Toy;
import model.iGetModel;
import toysMachineApi.ToysMachineApi;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;

public class MainFrame extends JFrame {
    private final Font textAreaFont = new Font("Segoe print", Font.PLAIN, 14);
    private final Font labelFont = new Font("Segoe print", Font.PLAIN, 20);
    private final Font buttonFont = new Font("Segoe print", Font.PLAIN, 20);
    private TextArea allToys;
    private JLabel paramsLabel;
    private JButton addButton;
    private JButton settingsButton;
    private JButton updateProbabilityButton;
    private JButton deleteButton;
    private static HashMap<Integer, Toy> toysMap;
    private JScrollBar scrollBar;
    private final ToysMachineApi toysMachineApi;

    public MainFrame(iGetModel<Toy> model) {
        this.toysMachineApi = new ToysMachineApi(model);
    }
    public void initialize() {
        setTitle("ToysMachineOwnerApp");
        setSize(900, 800);
        setMaximumSize(new Dimension(300, 400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);


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
            stringBuilder.append(key + ". " + toysMap.get(key).printToOwner() + "\n");
        }
        return stringBuilder.toString();
    }
    public static void main(String[] args) {

    }
}

