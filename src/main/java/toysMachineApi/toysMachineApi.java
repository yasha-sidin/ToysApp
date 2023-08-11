package toysMachineApi;

import model.DbModel;
import model.Probability;
import model.entity.Toy;
import model.iGetModel;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class toysMachineApi {
    private static final PriorityQueue<Toy> prizesQueue = new PriorityQueue<>();
    private static final Wallet wallet = new Wallet();
    private final static String PATH_TO_PROB_FILE = "src/main/resources/prob_file.txt";
    private static final Probability max_probability = new Probability(100.0);
    private static final Probability current_probability = getProbFromFile();
    private final iGetModel<Toy> model;

    public toysMachineApi(iGetModel<Toy> model) {
        this.model = model;
    }

    public static void addMoneyToWallet(Double money) {
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

    public void addToysToStorage(String nameOfToy, Probability probability) {
        addToysToStorage(nameOfToy, probability, 1);
    }

    public String usePlayingChoiceAndGetInfo(int minAmountOfToys, Double money) {
        List<Toy> toysList = model.getAllData();
        if (toysList.size() < minAmountOfToys) {
            return "There are not enough toys in machine. Please come back later.";
        }
        if (wallet.getMoney() < money) {
            return "You don't have enough money on your wallet. Please add or quit.";
        };

        wallet.spendMoney(money);

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
        List<Integer> probabilityList = new ArrayList<>();
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
        if (prize == null) {
            return prize;
        } else {
            model.deleteData(prize);
            prizesQueue.remove(prize);
            return prize;
        }
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
}
