package toysMachineApi;

import model.Probability;
import model.entity.Toy;
import model.iGetModel;

import java.io.*;
import java.util.*;

public class ToysMachineApi {
    private static final PriorityQueue<Toy> prizesQueue = new PriorityQueue<>();
    private static final Wallet wallet = new Wallet();
    private final static String PATH_TO_PROB_FILE = "src/main/resources/prob_file.txt";
    private final static String PATH_SETTINGS = "src/main/resources/settings.txt";
    private final static String PATH_TOYS_CLIENT = "src/main/resources/client_toys.txt";
    private final static Settings settings = new Settings();
    private static Money choiceCost = Settings.getChoiceSettings();
    private static int minAmountOfToys = Settings.getMinAmountSettings();
    private static final Probability maxProbability = new Probability(100.0);
    private static final Probability currentProbability = getProbFromFile();
    private final iGetModel<Toy> model;
    public ToysMachineApi(iGetModel<Toy> model) {
        this.model = model;
    }

    public void addMoneyToWallet(Money money) {
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
        if (amount * probability.getValue() +
                currentProbability.getValue() > maxProbability.getValue()) {
            throw new RuntimeException("Result probability can't be more than 100");
        }
        for (int i = 0; i < amount; i++) {
            Toy newToy = new Toy(nameOfToy, probability);
            model.addData(newToy);
        }
        currentProbability.setProbability(currentProbability.getValue() + (probability.getValue() * amount));
        saveProbIntoFile();
    }

    public void addToyToStorage(String nameOfToy, Probability probability) {
        addToysToStorage(nameOfToy, probability, 1);
    }

    public void deleteToyFromStorage(Toy toy) {
        model.deleteData(toy);
        currentProbability.setProbability(currentProbability.getValue() - toy.getProbability().getValue());
        saveProbIntoFile();
    }

    public void updateToyProbability(Probability probability, Toy toy) {
        Probability beforeProb = toy.getProbability();
        if (currentProbability.getValue() - beforeProb.getValue() + probability.getValue() > maxProbability.getValue()) {
            throw new RuntimeException("Result probability can't be more than 100");
        }
        toy.setProbability(probability);
        model.updateData(toy);
        currentProbability.setProbability(currentProbability.getValue() - beforeProb.getValue() + probability.getValue());
        saveProbIntoFile();
    }

    public String usePlayingChoiceAndGetInfo() {
        List<Toy> toysList = model.getAllData();
        if (toysList.size() < minAmountOfToys) {
            return "There are not enough toys in machine. Please come back later.";
        }
        if (wallet.getMoney().getValue() < choiceCost.getValue()) {
            return "You don't have enough money on your wallet. Please add or quit.";
        };

        wallet.spendMoney(choiceCost);

        int[] probabilityArray = new int[1000];
        int probCurrentPosition = 0;
        HashMap<Integer, Toy> toysMap = new HashMap<>();
        for (int i = 1; i < toysList.size(); i++) {
            double probabilityDouble = toysList.get(i - 1).getProbability().getValue();
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
            currentProbability.setProbability(currentProbability.getValue() - prize.getProbability().getValue());
            saveProbIntoFile();
            prizesQueue.add(prize);
            return String.format("Congratulates you! You won a prize: %s", prize.getName());
        }
    }

    public Toy getPrizeFromQueue() throws IOException {
        Toy prize = prizesQueue.peek();
        if (prize != null) {
            saveToyToFile(prize);
            model.deleteData(prize);
            prizesQueue.remove(prize);
        }
        return prize;
    }

    public PriorityQueue<Toy> getPrizesQueue() {
        return prizesQueue;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public List<Toy> getAllToys() {
        return model.getAllData();
    }

    public Probability getCurrentProbability() {
        return currentProbability;
    }

    public Money getChoiceCost() {
        return choiceCost;
    }

    public int getMinAmountOfToys() {
        return minAmountOfToys;
    }

    public void shutDownMachine() {
        model.shutdownModel();
    }

    public boolean setSettings(Money choiceCosts, int minAmountToys) {
        if (choiceCosts.getValue() > 1000.0) throw new RuntimeException("Cost of one choice can't be less than 1000.");
        if (minAmountToys <= 0 || minAmountToys > 30) throw new RuntimeException("Minimum amount of toys can't be less 0 or equals that and more than 30.");
        boolean result = Settings.saveSettings(choiceCosts, minAmountToys);
        choiceCost = choiceCosts;
        minAmountOfToys = minAmountToys;
        return result;
    }

    public boolean setSettings(Money choiceCosts) {
        if (choiceCosts.getValue() > 1000.0) throw new RuntimeException("Cost of one choice can't be less than 1000.");
        boolean result = Settings.saveSettings(choiceCosts);
        choiceCost = choiceCosts;
        return result;
    }

    public boolean setSettings(int minAmountToys) {
        if (minAmountToys <= 0 || minAmountToys > 30) throw new RuntimeException("Minimum amount of toys can't be less 0 or equals that and more than 30.");
        boolean result = Settings.saveSettings(minAmountToys);
        minAmountOfToys = minAmountToys;
        return result;
    }

    private void saveToyToFile(Toy toy) throws IOException {
//        File file = new File(PATH_TOYS_CLIENT);
//        if (!file.exists()){
//            file.createNewFile();
//        }
        int current_number = getLastNumber();
        try (FileWriter fw = new FileWriter(PATH_TOYS_CLIENT, true)) {
            fw.write(getLastNumber() + ". " + toy.printToClient());
        } catch (Throwable ex) {
            System.err.println("Exception in saving client's toy to src/main/resources/client_toys.txt. " + ex);
        }
    }

    private int getLastNumber() {
        try {
            FileReader fr = new FileReader(PATH_TOYS_CLIENT);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            if (line == null) {
                return 0;
            }
            List<String> strings = new LinkedList<>();
            while (line != null) {
                strings.add(line);
                line = reader.readLine();
            }
            return Integer.parseInt(strings.get(strings.size() - 1).split(" ")[0].substring(0, strings.get(strings.size() - 1).split(" ")[0].length() - 2));
        } catch (Throwable ex) {
            System.err.println("Exception in finding last number of Toy from client list. Check src/main/resources/client_toys.txt. " + ex);
        }
        return 0;
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
            fw.write(String.valueOf(currentProbability.getValue()));
        } catch (Throwable ex) {
            System.err.println("Exception in saving current_prob to src/main/resources/prob_file.txt. " + ex);
        }
    }

    private static class Settings {
        private final static Money DEFAULT_CHOICE_COST = new Money(100.0);
        private final static int DEFAULT_MIN_AMOUNT = 10;

        public Settings() {
            initDefaultSettings();
        }

        private static Money getChoiceSettings() {
            try {
                FileReader fr = new FileReader(PATH_SETTINGS);
                BufferedReader reader = new BufferedReader(fr);
                return new Money(Double.parseDouble(reader.readLine()));
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
            File file = new File(PATH_SETTINGS);
            if (file.exists()) {
                return;
            }
            try (FileWriter fw = new FileWriter(PATH_SETTINGS, false)) {
                fw.write(DEFAULT_CHOICE_COST.getValue() + "\n" + DEFAULT_MIN_AMOUNT);
            } catch (Throwable ex) {
                System.err.println("Exception in initializing src/main/resources/settings.txt. " + ex);
            }
        }

        private static boolean saveSettings(Money choiceCost, int minAmountOfToys) {
            try (FileWriter fw = new FileWriter(PATH_SETTINGS, false)) {
                fw.write(choiceCost.getValue() + "\n" + minAmountOfToys);
                return true;
            } catch (Throwable ex) {
                System.err.println("Exception in saving current_prob to src/main/resources/prob_file.txt. " + ex);
            }
            return false;
        }

        private static boolean saveSettings(Money choiceCost) {
            return saveSettings(choiceCost, getMinAmountSettings());
        }

        private static boolean saveSettings(int minAmountOfChoice) {
            return saveSettings(getChoiceSettings(), minAmountOfChoice);
        }
    }
}






