package roulette;
import javafx.scene.image.ImageView;
public class BetOption {
   private ImageView image;
   private int price;
   public BetOption(ImageView image, int price) {
       this.image = image;
       this.price = price;
   }
   public ImageView getImage() {
       return image;
   }
   public double getPrice() {
       return price;
   }
   @Override
   public String toString() {
       return String.format("%.2f �", price);
   }
}

