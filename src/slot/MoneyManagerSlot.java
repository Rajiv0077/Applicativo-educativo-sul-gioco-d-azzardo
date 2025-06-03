package slot;
import java.net.URL;
import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import main.Main;

public class MoneyManagerSlot {
    Integer currentMoney;

    public Integer moneySelection(Integer maxMoney, Spinner<Integer> spinner, Label lblMoneySelect,Integer lastSpinnerValue) {
        try {
            // Usa lastSpinnerValue come valore iniziale
            SpinnerValueFactory.IntegerSpinnerValueFactory factory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                    0, 
                    maxMoney, 
                    Math.min(lastSpinnerValue, maxMoney)
                );
            
            spinner.setValueFactory(factory);
            spinner.getValueFactory().setValue(lastSpinnerValue);
            
            lblMoneySelect.setText(factory.getValue() + " $");
            return lastSpinnerValue;
            
        } catch(Exception e) {
            lblMoneySelect.setText("Valore non valido!");
            return 0;
        }
    }

    public Integer moneyMultiplied(String winDescriptor, Integer moneyUsed, int maxSlot, ArrayList<Integer> winCounts) {
        if(!winDescriptor.equals("multi-win") && !winDescriptor.equals("f")) return 0;

        System.out.println("\n=== CALCOLO VINCITA ===");
        double total = 0;
        int minWin = (maxSlot == 12) ? 3 : 2;

        for(int i = 0; i < winCounts.size(); i++) {
            int count = winCounts.get(i);
            if(count >= minWin) {
                double multiplier = getMultiplier(count, maxSlot);
                System.out.println("Riga " + (i+1) + ": " + count + " simboli → " + multiplier + "x");
                total += moneyUsed * multiplier;
            }
        }


        System.out.println("TOTALE: " + Math.round(total * 100.0) / 100.0);
        return (int) (Math.round(total * 100.0) / 100.0);
    }
    
    
    
    
    
    
    public void moneyLeftDisplay(Label lblMLCurrency, Integer moneyLeft, ArrayList<Integer> endAnim, Integer maxSlot) {
        Timeline timeline = new Timeline();
        
        try {
            // Use the last element in endAnim
            int lastIndex = endAnim.size() - 1;
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(endAnim.get(lastIndex)), event -> {
                if(moneyLeft > 0) {
                    lblMLCurrency.setText(moneyLeft.toString() + " $");
                } else {
                    lblMLCurrency.setText("No Money");
                }
            }));

            timeline.setCycleCount(1);
            timeline.play();
        } catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }



    
    
    
    
    
    
    public void moneyMultipliedDisplay(Label lblMWCurrency, Integer moneyMultiplied, ArrayList<Integer> endAnim, Integer maxSlot) {
        Timeline timeline = new Timeline();
        try {
        	 int lastIndex = endAnim.size() - 1;
           
            
            timeline.getKeyFrames().add(new KeyFrame(
            		Duration.millis(endAnim.get(lastIndex)),
                event -> {
                    if (moneyMultiplied > 0) {
                        lblMWCurrency.setText(String.format(moneyMultiplied +" $", moneyMultiplied));
                    } else {
                        lblMWCurrency.setText("None");
                    }
                }
            ));

            timeline.setCycleCount(1);
            timeline.play();
            
        } catch(Exception e) {
            System.err.println("Errore visualizzazione vincita: " + e.getMessage());
            lblMWCurrency.setText("Error");
        }
    }

    
    
    public Integer setMoneyLeft(Integer moneyMultiplied, Integer moneyUsed, Integer maxValue) {
        if(moneyMultiplied > 0) {
            maxValue = maxValue + moneyMultiplied;
            return maxValue;
        } else {
            maxValue = maxValue - moneyUsed;
            return maxValue;
        }
    }
    
    private double getMultiplier(int count, int maxSlot) {
        if (maxSlot == 12) { // 4x3
            return count == 3 ? 2.0 : count == 4 ? 3.0 : count == 12 ? 10.0:0.0;
        } 
        else if (maxSlot == 9) { // 3x3
            return count == 2 ? 1.2 : count == 3 ? 2.0 : count == 9 ? 7.5:0.0;
        } 
        else { // 3x1
            return count == 2 ? 1.2 : count == 3 ? 5:0.0; // Rimosso il caso 3 simboli
        }
        
        
    }
    
    Double calculateMultiplier(int count, int maxSlot) {
    	if(maxSlot == 12) { // 4x3
            if(count == 3) return 2.0;
            if(count == 4) return 3.0;
            return 0.0;
        }
    	
        switch (maxSlot) {
            case 3: // 3x1
                switch (count) {
                    case 2:
                        return 1.2;
                    
                    default:
                        return 0.0;
                }
            case 9: // 3x3
                switch (count) {
                    case 2:
                        return 1.2;
                    case 3:
                        return 2.0;
                    
                    default:
                        return 0.0;
                }
            case 12: // 4x3
                switch (count) {
                    case 3:
                        return 2.0;
                    case 4:
                        return 3.0;
                    default:
                        return 0.0;
                }
            default:
                return 0.0;
        }
        
        
        
    }
	public double getJackpotMultiplier(int maxSlot) {
		switch (maxSlot) {
		case 3:
			return 5.0;
		case 9:
			return 7.5;
		case 12:
			return 10.0;
		default:
			return 5.0;
		}
	}
   
}