package menu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MoneyLost {

    public static void showPopupIfZero(boolean isMoneyZero, Stage currentStage) {
        if (isMoneyZero) {
            try {
                // Close the current stage before opening the new one
                if (currentStage != null) {
                    currentStage.close();
                }

                // Carica il FXML di AllMoneyLostMenu
                FXMLLoader loader = new FXMLLoader(MoneyLost.class.getResource("/menu/fxml/AllMoneyLostMenu.fxml"));
                Parent root = loader.load();

                // Crea un nuovo Stage per la finestra "AllMoneyLostMenu"
                Stage newStage = new Stage();
                
                // Crea la scena per il nuovo menu
                Scene newScene = new Scene(root);

                // Imposta la scena nello stage e mostra la finestra
                newStage.setScene(newScene);
                newStage.setTitle("All Money Lost Menu");
                newStage.show();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}