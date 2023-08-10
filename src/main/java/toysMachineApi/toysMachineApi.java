package toysMachineApi;

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
    private static final String probFileName = "lastProbability.txt";
    private static final Probability max_probability = new Probability(100.0);
    private static final Probability current_probability = getProbFromFile();
    private static iGetModel<Toy> model;

    public static void addMoneyToWallet(Double money) {
        wallet.addMoney(money);
    }

    public static void usePlayingChoice(int minAmountOfToys) {
        List<Toy> toysList = model.getAllData();
        int[] probabilityArray = new int[1000];
        int probCurrentPosition = 0;
        HashMap<Integer, Toy> toysMap = new HashMap<>();
        if (toysList.size() < minAmountOfToys) {
            throw new RuntimeException("There are not enough toys in machine");
        }
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
        Arrays.stream(probabilityArray).forEach(probabilityList::add);
        Collections.shuffle(probabilityList);
        Random random = new Random();
        int randomSelect = random.nextInt(0, 1001);
        int selectedKey = probabilityList.get(randomSelect);
        if (selectedKey == 0) {
            return;
        } else {
            Toy prize = toysMap.get(selectedKey);
            prizesQueue.add(prize);
        }

    }

    public static PriorityQueue<Toy> getPrizesQueue() {
        return prizesQueue;
    }

    public static Wallet getWallet() {
        return wallet;
    }

    private static Probability getProbFromFile() {
        try {
            FileReader fr = new FileReader(probFileName);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            if (line == null) {
                return new Probability(0.0);
            } else {
                return new Probability(Double.parseDouble(line));
            }
        } catch (Throwable ex) {
            System.err.println("Exception in initializing current_prob. " + ex);
        }
        return new Probability(0.0);
    }

    private static void saveProbIntoFile() {
        try (FileWriter fw = new FileWriter(probFileName, false)) {
            fw.write(String.valueOf(current_probability.getProbability()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
