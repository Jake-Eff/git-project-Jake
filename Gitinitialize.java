import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HexFormat;

public class Gitinitialize{
    public static void main(String[] args) {
        instantiate();
        try {
            System.out.println(hashFile("Hello.txt"));
        } catch (Exception e) {
            System.out.println("oops");
        }
    }

    public static void instantiate(){
        try {
            int count = 0;
            File git = new File("git/");
            if(!git.mkdir()){
                count++;
            }
            File objects = new File("objects/");
            if(!objects.mkdir()){
                count++;
            }
            File index = new File("index");
            if(!index.createNewFile()){
                count++;
            }
            File head = new File("head");
            if(!head.createNewFile()){
                count++;
            }
            if(count == 4){
                System.out.println("Git Repository Already Exists");
            } else{
                System.out.println("Git Repository Created");
            }
        } catch (Exception e) {
            System.out.println("There's an error.");
        }
        
    }

    public static String hashFile(String filePath) throws IOException {
        // TODO (FH-4): read the whole file, digest it, convert the bytes to hex
        Path path = Path.of(filePath);
        if (!Files.isRegularFile(path)) {
            throw new IOException("no such file: " + filePath);
        }

        byte[] fileBytes = Files.readAllBytes(path);

        MessageDigest digest;

        try {
            digest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-1 is not available", e);
        }

        byte[] hash = digest.digest(fileBytes);

        return HexFormat.of().formatHex(hash);
    }
}