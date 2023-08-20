import model.DbModel;
import toysMachineApi.Money;
import toysMachineApi.ToysMachineApi;

public class Test {
    public static void main(String[] args) {
        ToysMachineApi toysMachineApi = new ToysMachineApi(new DbModel());
        toysMachineApi.addMoneyToWallet(new Money(1000.0));
        System.out.println(toysMachineApi.usePlayingChoiceAndGetInfo());
        toysMachineApi.shutDownMachine();
        System.out.println(Integer.parseInt("15.0".split("\\.")[0]));
    }
}
