import model.DbModel;
import model.entity.Toy;
import model.iGetModel;
import ownerPartController.OwnerFrame;

public class OwnerApp {
    public static void main(String[] args) {
        iGetModel<Toy> dbModel = new DbModel();
        OwnerFrame ownerApp = new OwnerFrame(dbModel);
    }
}
