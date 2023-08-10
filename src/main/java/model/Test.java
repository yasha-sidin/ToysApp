package model;

import model.entity.Toy;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {

//        DbModel dbModel = new DbModel();
//        Toy toy = dbModel.getDataById(25);
//        toy.setName("Rolex");
//        toy.setProbability(new Probability(25.2));
//        dbModel.updateData(toy);
//        System.out.println(String.valueOf(toy.getProbability()));
//        dbModel.shutdownSessionFabric();
        int[] probabilityArray = new int[15];
        List<Integer> probabilityList = new ArrayList<>();
        Arrays.stream(probabilityArray).forEach(probabilityList::add);
        probabilityList.forEach((i) -> System.out.print(probabilityList.get(i) + " "));
    }
}