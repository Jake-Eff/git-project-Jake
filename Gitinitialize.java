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
}