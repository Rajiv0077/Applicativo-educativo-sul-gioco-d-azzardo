package fileManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class UserFileManager {
	private static String userPath; //Shared UserFileManager instance
	
    //desktop path
    private static Path desktopPath = Paths.get(System.getProperty("user.home"), "Desktop");
    //path for all accounts folder
    private static Path accountPath = Paths.get(System.getProperty("user.home"), "Desktop").resolve("CasinoFiles")
																				           .resolve("users");
    
    private static Path autoModePath = Paths.get(System.getProperty("user.home"), "Desktop").resolve("CasinoFiles")
	           																				.resolve("automode");
	
	
	public void saveToFile(String text, String fileName) {
		//for saving any kind of string in any file. Save in the userFile directory specified. Receive the file name whit it's extension
		String path = userPath + "\\" + fileName;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(text);
            System.out.println("Text successfully saved to " + path);
        } catch (IOException e) {
            System.out.println("Error saving the file: " + e.getMessage());
        }
	}
	
	public String readFromFile(String fileName) {
		//for reading any kind of file. Return the file content. Read from file passed whit it's extension
		StringBuilder textFromFile = new StringBuilder();
        
        String path = userPath + "\\" + fileName;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                textFromFile.append(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
        return textFromFile.toString();
	}
	
	public Boolean appendTextToFile(String text, String fileName) {
		//for adding any kind of string in any file. Save in the userFile directory specified. Receive the file name whit it's extension
		String path = userPath + "/" + fileName;
		
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.newLine(); //create a new line on the file 
        	writer.append(text); //write the text on the new line created before
            System.out.println("Text successfully added to " + path);
            return true;
        } catch (IOException e) {
            System.out.println("Error saving the file: " + e.getMessage());
            return false;
        }
	}
	
	public Boolean updateFileContent(String text, String fileName) {
	    String file = userPath + "\\" + fileName;
	    
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {  //overwrites, not appends
	        // Clear the file content and write new text
	        writer.write(text);
	        System.out.println("File content updated successfully at " + file);
	        return true;
	    } catch (IOException e) {
	        System.out.println("Error updating the file: " + e.getMessage());
	        return false;
	    }
	}

	
	 public static void initializeFolders() {
	        try {
	            // Create directories if they don't exist
	            Files.createDirectories(accountPath);
	            Files.createDirectories(autoModePath);
	            System.out.println("Folders for users are ready: " + accountPath);
	            System.out.println("Folders for automode are ready: " + autoModePath);
	        } catch (IOException e) {
	            System.err.println("Error creating directories: " + e.getMessage());
	        }
	 }
	
	 public static boolean deleteUserFolderIfExists(String username) {
        Path userFolderPath = Paths.get(accountPath.toString(), username);
        
        if (!Files.exists(userFolderPath)) {
            return true; // Folder doesn't exist, nothing to do
        }

        try {
            // Delete the folder and all its contents
            Files.walkFileTree(userFolderPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    try {
                        // This will ensure the file is closed if it was open
                        Files.delete(file);
                    } catch (IOException e) {
                        System.err.println("Failed to delete file: " + file + " - " + e.getMessage());
                        throw e; // Re-throw to abort the operation
                    }
                    return FileVisitResult.CONTINUE;
                }

                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    if (exc != null) {
                        throw exc; // If there was error during file visit
                    }
                    try {
                        Files.delete(dir);
                    } catch (IOException e) {
                        System.err.println("Failed to delete directory: " + dir + " - " + e.getMessage());
                        throw e; // Re-throw to abort the operation
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
            
            System.out.println("Successfully deleted user folder: " + userFolderPath);
            return true;
        } catch (IOException e) {
            System.err.println("Error deleting user folder: " + userFolderPath + " - " + e.getMessage());
            return false;
        }
	}
	
	
	public String getUserPath() {return userPath;	}
	public void setDirectoryPath(String directoryPath) {UserFileManager.userPath = directoryPath;}
    public static String getDesktopPath() {return desktopPath.toString();}
    public static String getAccountPath() {return accountPath.toString();}
    public static String getAutoModePath() {return autoModePath.toString();}
}
