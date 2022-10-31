import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    private static final String BASE_PATH = "C:\\GAMES\\savegames\\";

    public static void openZip(String pathToZipFile, String pathToFolder) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(pathToZipFile))) {
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null) {
                FileOutputStream fout = new FileOutputStream(pathToFolder + entry.getName());
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (GameProgress) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        openZip(BASE_PATH + "zipped.zip", BASE_PATH);
        System.out.println(openProgress(BASE_PATH + "save1.dat"));
    }
}
