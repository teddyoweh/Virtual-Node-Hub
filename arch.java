import java.io.File;
import java.io.IOException;

class CreateFileSystem {
    public static void main(String[] args) {
        String fileName = "arch.txt";
        File file = new File(fileName);

        // Create the file if it does not exist
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }

        // Create the folder system
        String[] folders = {"folder1", "folder1/subfolder1", "folder2"};
        for (String folderPath : folders) {
            File folder = new File(folderPath);
            if (folder.mkdirs()) {
                System.out.println("Folder created: " + folder.getPath());
            } else {
                System.out.println("Folder already exists: " + folder.getPath());
            }
        }
    }
}
