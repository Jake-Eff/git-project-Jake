import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Verify {
    public static void main(String[] args) throws IOException {
    GitInitialize git = new GitInitialize();        
    // Recreating git should not reinitialize the repository.
        git = new GitInitialize(); 
        try {
            System.out.println("SHA-1 of Verify.txt is 9a26e18fbc5b2435f4cabb1e91b6e780bb0d8e4d");
            git.createBlob("Hello.txt");
            git.updateIndex("Hello.txt");
            git.createBlob("Verify.txt");
            git.updateIndex("Verify.txt");
            git.createBlob("test/awesome.txt");
            git.updateIndex("test/awesome.txt");
            git.createBlob("test/Hello.txt");
            git.updateIndex("test/Hello.txt");
            git.updateIndex("test/awesome.txt");
            System.out.println("== EMPTY ==");
            git.createBlob("empty.txt");
            git.updateIndex("empty.txt");
            git.createBlob("Hello2.txt");
            git.updateIndex("Hello2.txt");
            git.createBlob("test/empty.txt");
            git.updateIndex("test/empty.txt");
            git.createBlob("test/whoa/anotherTest.txt");
            git.updateIndex("test/whoa/anotherTest.txt");
            git.createBlob("test/test2/wow.txt");
            git.updateIndex("test/test2/wow.txt");
            git.createBlob("test/test3/test4/hey.txt");
            git.updateIndex("test/test3/test4/hey.txt");
            // System.out.println("== TESTING CREATING TREES ==");
            File index = new File("git/index");
            // System.out.println(git.createTree(index, "myProject/test"));
            // git.createWorkingTree(index);
            System.out.println(git.createTreesFromIndex());
        } catch (Exception e) {
            System.out.println("File error: " + e.getMessage());
        }

        //commit tester
        Files.writeString(Path.of("Hello.txt"), "Hello\n");
        Files.createDirectories(Path.of("test"));
        Files.writeString(Path.of("test/awesome.txt"), "Awesome\n");

        System.out.println("start;");
        GitInitialize coolgit = new GitInitialize();
        coolgit.instantiate();
        System.out.println("hashFile" + GitInitialize.hashFile("Hello.txt"));
        for (String file : new String[]{"Hello.txt", "test/awesome.txt"}) {
            coolgit.createBlob(file);
            System.out.println("Blob " + file + Files.exists(
                    Path.of("git/objects", GitInitialize.hashFile(file))));
                    coolgit.updateIndex(file);
        }
        System.out.println("Index" + Files.readString(Path.of("git/index")));

        File working = coolgit.createWorkingList(new File("git/index"));
        System.out.println("createWorkingList" + Files.readString(working.toPath()));
        String leaf = coolgit.findLeafMostDirectory(working);
        System.out.println("findLeafMostDirectory: " + leaf);
        String subtree = coolgit.createTree(working, leaf);
        System.out.println("createTree: " + subtree);
        coolgit.rewriteWorkingList(working, subtree, leaf);
        System.out.println("rewriteWorkingList" + Files.readString(working.toPath()));
        String root = coolgit.createTreesFromIndex();
        System.out.println("createTreesFromIndex: " + root);
        System.out.println("Root tree" + Files.readString(Path.of("git/objects", root)));

        String first = coolgit.commit("Asher", "First test commit");
        System.out.println("commit returned: " + first);
        System.out.println(Files.readString(Path.of("git/objects", first)));
        System.out.println("HEAD: " + Files.readString(Path.of("git/HEAD")));

        System.out.println("GitWrapper");
        GitInitialize.GitWrapper.init();
        GitInitialize.GitWrapper.add("Hello.txt");
        System.out.println("GitWrapper.add:\n" + Files.readString(Path.of("git/index")));
        String second = GitInitialize.GitWrapper.commit("Asher", "test commit");
        System.out.println("GitWrapper.commit returned: " + second);
        System.out.println(Files.readString(Path.of("git/objects", second)));
        System.out.println("HEAD: " + Files.readString(Path.of("git/HEAD")));
        System.out.println("GitWrapper.checkout:");
        GitInitialize.GitWrapper.checkout(second);
    }
}
