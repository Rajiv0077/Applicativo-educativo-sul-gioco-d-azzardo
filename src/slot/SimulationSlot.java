package slot;

import java.util.Random;

public class SimulationSlot {
    private static final Random rand = new Random();

    public static int slotResult(int bet) {
        int slotType = rand.nextInt(3); // 0=3x1, 1=3x3, 2=4x3
        String[] typeNames = {"3x1", "3x3", "4x3"};
        System.out.println("Playing: " + typeNames[slotType]);

        int[] symbols = new int[3];
        for (int i = 0; i < 3; i++) {
            symbols[i] = getSymbol(slotType);
        }
        System.out.println("Symbols: " + symbols[0] + " " + symbols[1] + " " + symbols[2]);

        int matches = countMatches(symbols);
        double multiplier = calculateWin(matches, slotType, symbols);

        if (multiplier > 0) {
            int win = (int) (bet * multiplier);
            System.out.println("Win! " + win);
            return win;
        } else {
            System.out.println("Lost bet");
            return -bet;
        }
    }

    private static int getSymbol(int slotType) {
        int chance = rand.nextInt(100);
        if (slotType == 0) {
            if (chance < 50) return 1;
            if (chance < 85) return 2;
            return 3;
        } else if (slotType == 1) {
            if (chance < 40) return 1;
            if (chance < 75) return 2;
            return 3;
        } else {
            if (chance < 35) return 1;
            if (chance < 70) return 2;
            if (chance < 95) return 3;
            return 4;
        }
    }

    private static int countMatches(int[] symbols) {
        if (symbols[0] == symbols[1] && symbols[1] == symbols[2]) return 3;
        if (symbols[0] == symbols[1] || symbols[0] == symbols[2] || symbols[1] == symbols[2]) return 2;
        return 1;
    }

    private static double calculateWin(int matches, int slotType, int[] symbols) {
    	if (slotType == 0) {
			if (matches == 3) return 1.0; //fair but rare
			if (matches == 2) return 0.4;
			return 0;
    	}else if (slotType == 1) {
			if (matches == 3) return 1.5;
			if (matches == 2) return 0.5;
			return 0;
		}else {
			if (matches == 3 && symbols[0]==4) return 4.0; //rare jackpot
			if (matches == 3) return 1.0; //lower reward for 3 non-4s
			if (matches == 2 && (symbols[0]==4 || symbols[1]==4 || symbols[2]==4)) return 0.6;
			return 0;
		}
    }
}