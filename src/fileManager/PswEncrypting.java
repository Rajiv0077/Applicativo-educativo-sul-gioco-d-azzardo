package fileManager;


public class PswEncrypting {
	private static UserFileManager userFile;
	
	public PswEncrypting(UserFileManager userFile) {
		PswEncrypting.userFile = userFile;
	}
	

    // Method to encrypt the text (Caesar Cipher)
    public void cifraCesare(String psw, int shift) {
        StringBuilder encryptedText = new StringBuilder();

        // Encrypt each character in the string
        for (int i = 0; i < psw.length(); i++) {
            char c = psw.charAt(i);
            if (Character.isLetter(c)) {
                // Determine if the character is uppercase or lowercase
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                // Shift the character and append it to the result
                c = (char) ((c - base + shift) % 26 + base);
            }
            // Append the encrypted character to the string
            encryptedText.append(c);
        }
        
        String encryptedPsw =  encryptedText.toString(); 
        userFile.saveToFile(encryptedPsw, "psw.txt");
        //saveToFile(encryptedPsw);
    }

    // Method to decrypt the text (Caesar Cipher)
    public String decifraCesare(int shift) {
    	String text = userFile.readFromFile("psw.txt");
        StringBuilder decryptedText = new StringBuilder();

        // decrypt each character in the string
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetter(c)) {
                // Determine if the character is uppercase or lowercase
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                // Shift the character in the opposite direction to decrypt
                c = (char) ((c - base - shift + 26) % 26 + base);
            }
            // Append the decrypted character to the string
            decryptedText.append(c);
        }
        return decryptedText.toString();
    }


    
}
