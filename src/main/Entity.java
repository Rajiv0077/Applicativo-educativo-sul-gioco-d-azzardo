package main;

public interface Entity {
    
    String getName();            // Common identifier for Player & Bot
    Integer getGamesPlayed();    // Total games played
    Integer getGamesWon();       // Total games won
    Integer getGamesLost();      // Total games lost
    
    void incrementGamesPlayed(); // Increment number of games played
    void incrementGamesWon();    // Increment wins
    void incrementGamesLost();   // Increment losses
    
}
