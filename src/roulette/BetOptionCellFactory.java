package roulette;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class BetOptionCellFactory implements Callback<ListView<BetOption>, ListCell<BetOption>> {
    @Override
    public ListCell<BetOption> call(ListView<BetOption> param) {
        return new ListCell<BetOption>() {
            @Override
            protected void updateItem(BetOption item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    // Mostra l'immagine e il prezzo
                    ImageView imageView = new ImageView(item.getImage().getImage());
                    imageView.setFitWidth(20); // Ridimensiona l'immagine
                    imageView.setFitHeight(20);
                    setGraphic(imageView);
                    setText(String.format("%.2f �", item.getPrice()));
                    setContentDisplay(ContentDisplay.LEFT); // Posiziona l'immagine a sinistra
                }
            }
        };
    }
}

