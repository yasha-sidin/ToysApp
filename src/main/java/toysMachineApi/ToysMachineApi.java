package toysMachineApi;

import model.Probability;
import model.entity.Toy;
import model.iGetModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class ToysMachineApi {
    private static final PriorityQueue<Toy> prizesQueue = new PriorityQueue<>();
    private static final Wallet wallet = new Wallet();
    private final static String PATH_TO_PROB_FILE = "src/main/resources/prob_file.txt";
    private final static String PATH_SETTINGS = "src/main/resources/settings.txt";
    private final static String PATH_TOYS_CLIENT = "src/main/resources/client_toys.txt";
    private final static Settings settings = new Settings();
    private static double choiceCost = Settings.getChoiceSettings();
    private static int minAmountOfToys = Settings.getMinAmountSettings();
    private static final Probability max_probability = new Probability(100.0);
    private static final Probability current_probability = getProbFromFile();
    private final iGetModel<Toy> model;
    public ToysMachineApi(iGetModel<Toy> model) {
        this.model = model;
    }

    public void addMoneyToWallet(Double money) {
        wallet.addMoney(money);
    }

    /**
     *
     * @param nameOfToy   name of toy which you want to add
     * @param probability probability for ONLY one toy, NOT for all amount of toys(summary probability always must be less than
     * @param amount      amount of toys named @nameOfToys which you want to add
     */
    public void addToysToStorage(String nameOfToy, Probability probability, int amount) {
        if (amount < 1) throw new RuntimeException("Amount must be more than 0");
        if (amount * probability.getProbability() +
                current_probability.getProbability() > max_probability.getProbability()) {
            throw new RuntimeException("Result probability can't be more than 100");
        }
        for (int i = 0; i < amount; i++) {
            Toy newToy = new Toy(nameOfToy, probability);
            model.addData(newToy);
        }
        current_probability.setProbability(current_probability.getProbability() + (probability.getProbability() * amount));
        saveProbIntoFile();
    }

    public void addToyToStorage(String nameOfToy, Probability probability) {
        addToysToStorage(nameOfToy, probability, 1);
    }

    public void deleteToyFromStorage(Toy toy) {
        model.deleteData(toy);
        current_probability.setProbability(current_probability.getProbability() - toy.getProbability());
        saveProbIntoFile();
    }

    public String usePlayingChoiceAndGetInfo() {
        List<Toy> toysList = model.getAllData();
        if (toysList.size() < minAmountOfToys) {
            return "There are not enough toys in machine. Please come back later.";
        }
        if (wallet.getMoney() < choiceCost) {
            return "You don't have enough money on your wallet. Please add or quit.";
        };

        wallet.spendMoney(choiceCost);

        int[] probabilityArray = new int[1000];
        int probCurrentPosition = 0;
        HashMap<Integer, Toy> toysMap = new HashMap<>();
        for (int i = 1; i < toysList.size(); i++) {
            double probabilityDouble = toysList.get(i - 1).getProbability();
            toysMap.put(i, toysList.get(i - 1));
            int valueToFillProbArray = Integer.parseInt(String.valueOf(probabilityDouble * 10));
            for (int j = 0; j < valueToFillProbArray; j++) {
                probabilityArray[probCurrentPosition] = i;
                probCurrentPosition++;
            }
        }
        List<Integer> probabilityList = new LinkedList<>();
        Arrays.stream(probabilityArray).parallel().forEach(probabilityList::add);
        Collections.shuffle(probabilityList);
        Random random = new Random();
        int randomSelect = random.nextInt(0, 1001);
        int selectedKey = probabilityList.get(randomSelect);
        if (selectedKey == 0) {
            return "Unfortunately, you didn't win anything. Don't worry, maybe next time you will be more lucky.";
        } else {
            Toy prize = toysMap.get(selectedKey);
            current_probability.setProbability(current_probability.getProbability() - prize.getProbability());
            saveProbIntoFile();
            prizesQueue.add(prize);
            return String.format("Congratulates you! You won a prize: %s", prize.getName());
        }
    }

    public Toy getPrizeFromQueue() {
        Toy prize = prizesQueue.peek();
        if (prize != null) {
            model.deleteData(prize);
            prizesQueue.remove(prize);
        }
        return prize;
    }

    public static PriorityQueue<Toy> getPrizesQueue() {
        return prizesQueue;
    }

    public static Wallet getWallet() {
        return wallet;
    }

    public void shutDownMachine() {
        model.shutdownModel();
    }

    public void setSettings(double choiceCosts, int minAmountToys) {
        Settings.saveSettings(choiceCosts, minAmountToys);
        choiceCost = choiceCosts;
        minAmountOfToys = minAmountToys;
    }

    public void setSettings(double choiceCosts) {
        Settings.saveSettings(choiceCosts);
        choiceCost = choiceCosts;
    }

    public void setSettings(int minAmountToys) {
        Settings.saveSettings(minAmountToys);
        minAmountOfToys = minAmountToys;
    }

    private static Probability getProbFromFile() {
        try {
            FileReader fr = new FileReader(PATH_TO_PROB_FILE);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            if (line == null || line.isEmpty()) {
                return new Probability(0.0);
            } else {
                return new Probability(Double.parseDouble(line));
            }
        } catch (Throwable ex) {
            System.err.println("Exception in initializing current_prob. Check src/main/resources/prob_file.txt. " + ex);
        }
        return new Probability(0.0);
    }

    private static void saveProbIntoFile() {
        try (FileWriter fw = new FileWriter(PATH_TO_PROB_FILE, false)) {
            fw.write(String.valueOf(current_probability.getProbability()));
        } catch (Throwable ex) {
            System.err.println("Exception in saving current_prob to src/main/resources/prob_file.txt. " + ex);
        }
    }

    private static class Settings {
        private final static double DEFAULT_CHOICE_COST = 100.0;
        private final static int DEFAULT_MIN_AMOUNT = 10;

        public Settings() {
            initDefaultSettings();
        }

        private static double getChoiceSettings() {
            try {
                FileReader fr = new FileReader(PATH_SETTINGS);
                BufferedReader reader = new BufferedReader(fr);
                return Double.parseDouble(reader.readLine());
            } catch (Throwable ex) {
                System.err.println("Exception in reading settings(costChoice). Check src/main/resources/settings.txt(first string). " + ex);
            }
            return DEFAULT_CHOICE_COST;
        }

        private static int getMinAmountSettings() {
            try {
                FileReader fr = new FileReader(PATH_SETTINGS);
                BufferedReader reader = new BufferedReader(fr);
                reader.readLine();
                return Integer.parseInt(reader.readLine());
            } catch (Throwable ex) {
                System.err.println("Exception in reading settings(minAmount). Check src/main/resources/settings.txt.(Second string) " + ex);
            }
            return DEFAULT_MIN_AMOUNT;
        }

        private static void checkSettings() {
            File settingsFile = new File(PATH_SETTINGS);
            try {
                if (!settingsFile.exists()) {
                    initDefaultSettings();
                }
            } catch (Throwable e) {
                initDefaultSettings();
            }
        }

        private static void initDefaultSettings() {
            try (FileWriter fw = new FileWriter(PATH_SETTINGS, false)) {
                fw.write(String.valueOf(DEFAULT_CHOICE_COST) + "\n" + DEFAULT_MIN_AMOUNT);
            } catch (Throwable ex) {
                System.err.println("Exception in initializing src/main/resources/settings.txt. " + ex);
            }
        }

        private static void saveSettings(double choiceCost, int minAmountOfToys) {
            try (FileWriter fw = new FileWriter(PATH_SETTINGS, false)) {
                fw.write(String.valueOf(choiceCost) + "\n" + minAmountOfToys);
            } catch (Throwable ex) {
                System.err.println("Exception in saving current_prob to src/main/resources/prob_file.txt. " + ex);
            }
        }

        private static void saveSettings(double choiceCost) {
            saveSettings(choiceCost, getMinAmountSettings());
        }

        private static void saveSettings(int minAmountOfChoice) {
            saveSettings(getChoiceSettings(), minAmountOfChoice);
        }
    }
}






