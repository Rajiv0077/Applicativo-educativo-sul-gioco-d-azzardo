package main;

import fileManager.UserFileManager;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static Player player;
    public static Bot bot = new Bot("Alfredo");
    private static Stage stage;
    private UserFileManager fileManager = new UserFileManager();

    // Media player variables
    private List<String> songPaths = new ArrayList<>();
    private MediaPlayer mediaPlayer;

    static {
        UserFileManager.initializeFolders();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;

            //Load song paths from resources
            int i = 1;
            while (true) {
                String path = "../music/casino_" + i + ".mp3";
                URL url = getClass().getResource(path);
                if (url == null) {
                    break;
                }
                songPaths.add(url.toExternalForm());
                i++;
            }

            //Play background music if songs are available
            if (!songPaths.isEmpty()) {
                playRandomSong();
            } else {
                System.err.println("No background music files found!");
            }

            BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/menu/fxml/StartMenu.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Casino Clamoroso");
            primaryStage.setScene(scene);

            primaryStage.setOnCloseRequest(event -> { //while window is closed, save everything and stop music playback
                fileManager.updateFileContent(player.getMoney().toString(), "money.txt");
                fileManager.updateFileContent(player.getGamesPlayed().toString(), "gamesPlayed.txt");
                fileManager.updateFileContent(player.getGamesWon().toString(), "gamesWon.txt");
                fileManager.updateFileContent(player.getGamesLost().toString(), "gamesLost.txt");
                fileManager.updateFileContent(player.getHighestEarning().toString(), "highestEarning.txt");
                
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
            });

            primaryStage.setFullScreen(true);
            primaryStage.setFullScreenExitHint(""); // no hint appearing while program launches
            
            primaryStage.fullScreenProperty().addListener((obs, wasFull, isNowFull) -> {
                if (!isNowFull) {
                    primaryStage.setFullScreen(true);
                }
            });

            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playRandomSong() {
        if (songPaths.isEmpty()) return; //check for files in folder

        int randomIndex = (int) (Math.random() * songPaths.size());
        String randomSong = songPaths.get(randomIndex); //random song path

        try {
            Media media = new Media(randomSong); //put the path inside Media
            mediaPlayer = new MediaPlayer(media); // play Media with a MediaPlayer
            mediaPlayer.setVolume(0.1); // Adjust volume as needed
            mediaPlayer.setCycleCount(1);
            mediaPlayer.play();

            mediaPlayer.setOnEndOfMedia(() -> playRandomSong()); // obv play another after current finishes
        } catch (Exception e) {
            System.err.println("Error playing media: " + e.getMessage());
            playRandomSong(); // Try next song if current fails
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() { return stage; }
    public static Player getPlayer() { return player; }
    public static Bot getBot() { return bot; }
}