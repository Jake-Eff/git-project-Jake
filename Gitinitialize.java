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

public class GitInitialize {

    private File git;
    private File objects;
    private File index;
    private File Head;

    public static void main(String[] args) {

        GitInitialize newGit = new GitInitialize();

        try {
            System.out.println(hashFile("Hello.txt"));
            newGit.createBlob("Hello.txt");
            newGit.updateIndex("Hello.txt");
        } catch (Exception e) {
            System.out.println("oops");
        }
    }

    public GitInitialize() {

        instantiate();
    }

    public void instantiate() {
        try {
            int count = 0;
            git = new File("git/");
            if (!git.mkdir()) {
                count++;
            }
            objects = new File(git, "objects/");
            if (!objects.mkdir()) {
                count++;
            }
            index = new File(git, "index");
            if (!index.createNewFile()) {
                count++;
            }
            Head = new File(git, "Head");
            if (!Head.createNewFile()) {
                count++;
            }
            if (count == 4) {
                System.out.println("Git Repository Already Exists");
            } else {
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

    public void createBlob(String filePath) throws IOException {
        try {

            String hash = hashFile(filePath);
            File newFile = new File(objects, hash);
            newFile.createNewFile();

            BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
            String readFile = fileReader.readLine();
            fileReader.close();

            FileWriter fileWriter = new FileWriter(newFile.toPath().toString());
            fileWriter.write(readFile);
            fileWriter.close();


        } catch (Exception e) {
            System.out.println("There's an error");
        }
    }

    public void updateIndex(String filePath) throws IOException {
        try {
            String hash = hashFile(filePath);

            BufferedReader fileReader =
                    new BufferedReader(new FileReader(index.toPath().toString()));
            FileWriter fileWriter = new FileWriter(index.toPath().toString());

            if (fileReader.readLine() == null) {
                fileWriter.write(hash + " " + filePath);
            } else {
                fileWriter.write("\n" + hash + " " + filePath);
            }

            fileReader.close();
            fileWriter.close();

        } catch (Exception e) {
            System.out.println("There's an error");
        }
    }
}
