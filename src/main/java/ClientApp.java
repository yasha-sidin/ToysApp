import clientPartController.ClientFrame;
import model.DbModel;
import model.entity.Toy;
import model.iGetModel;

public class ClientApp {
    public static void main(String[] args) {
        iGetModel<Toy> dbModel = new DbModel();
        new ClientFrame(dbModel);
    }
}
