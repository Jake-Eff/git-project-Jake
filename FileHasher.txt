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

public class FileHasher {

    public static void main(String[] args) {
        try {
            // TODO (FH-1): create the JavaFileSystem directory
            File root = new File("JavaFileSystem");
            root.mkdir();

            // TODO (FH-2): create notes.txt, data.txt, log.txt and write a sentence into each
            FileWriter notesWriter = new FileWriter("JavaFileSystem/notes.txt");
            notesWriter.write("Hashing turns any content into a fixed-size fingerprint.\n");
            notesWriter.close();

            FileWriter dataWriter = new FileWriter("JavaFileSystem/data.txt");
            dataWriter.write("Git names every object by the hash of its contents.\n");
            dataWriter.close();

            FileWriter logWriter = new FileWriter("JavaFileSystem/log.txt");
            logWriter.write("Two different files essentially never share a SHA-256 hash.\n");
            logWriter.close();

            // TODO (FH-3): read each file back, print it, and write all three into
            // Backup/backup.txt
            System.out.println("-- Reading files back --");
            BufferedReader notesReader =
                    new BufferedReader(new FileReader("JavaFileSystem/notes.txt"));
            String notes = notesReader.readLine();
            notesReader.close();
            System.out.println("notes.txt: " + notes);

            System.out.println();

            BufferedReader dataReader =
                    new BufferedReader(new FileReader("JavaFileSystem/data.txt"));
            String data = dataReader.readLine();
            dataReader.close();
            System.out.println("data.txt: " + data);

            System.out.println();

            BufferedReader logReader = new BufferedReader(new FileReader("JavaFileSystem/log.txt"));
            String log = logReader.readLine();
            logReader.close();
            System.out.println("log.txt: " + log);

            File backup = new File("JavaFileSystem/backup");
            backup.mkdir();

            FileWriter backupWriter = new FileWriter("JavaFileSystem/backup/backup.txt");
            backupWriter.write(notes + "\n" + data + "\n" + log + "\n");
            backupWriter.close();

            // TODO (FH-4): print each file's name next to hashFile(path)
            try {
                String hashedFile = hashFile("JavaFileSystem/backup/backup.txt");
                System.out.println(hashedFile);
            } catch (IOException e) {

            }

        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }

    /**
     * Reads the file at filePath and returns its SHA-256 hash as a lowercase 64-character
     * hexadecimal string.
     */
    public static String hashFile(String filePath) throws IOException {
        // TODO (FH-4): read the whole file, digest it, convert the bytes to hex
        Path path = Path.of(filePath);
        if (!Files.isRegularFile(path)) {
            throw new IOException("no such file: " + filePath);
        }

        byte[] fileBytes = Files.readAllBytes(path);

        MessageDigest digest;

        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is not available", e);
        }

        byte[] hash = digest.digest(fileBytes);

        return HexFormat.of().formatHex(hash);
    }
}
