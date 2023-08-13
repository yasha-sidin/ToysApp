package model;

import model.entity.Toy;
import org.hibernate.Session;
import toysMachineApi.ToysMachineApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {

//        DbModel dbModel = new DbModel();
//        List<Toy> toys = dbModel.getAllData();
//        System.out.println(toys.get(2).getId());
//        Toy toy = dbModel.getDataById(25);
//        toy.setName("Rolex");
//        toy.setProbability(new Probability(25.2));
//        dbModel.updateData(toy);
//        System.out.println(String.valueOf(toy.getProbability()));
//        dbModel.shutdownSessionFabric();
//        int[] probabilityArray = new int[15];
//        List<Integer> probabilityList = new ArrayList<>();
//        Arrays.stream(probabilityArray).parallel().forEach(probabilityList::add);
//        probabilityList.forEach((i) -> System.out.print(probabilityList.get(i) + " "));
        iGetModel<Toy> dbModel = new DbModel();
        ToysMachineApi toysMachineApi = new ToysMachineApi(dbModel);
//        toysMachineApi.addToysToStorage("Dinosour", new Probability(1.5), 10);
        System.out.println();
        toysMachineApi.shutDownMachine();
    }
}