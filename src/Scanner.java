import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Scanner {
    public String root = "";
    public ArrayList<String> listOfFiles = new ArrayList<String>();
    public ArrayList<String> listOfDirs = new ArrayList<String>();
    public FileHash fh = new FileHash();
    public String fileData = "";

    public Scanner(String root) {

        this.root = root;
    }

    public static void main(String args[]) {
        Scanner s = new Scanner(""); //set root directory here

        try {
            s.scan(s.getRoot(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println("test " + s.fileData);

//        System.out.println("Files:");
//        for (int i = 0; i < s.listOfFiles.size(); i++) {
//            System.out.print(s.listOfFiles.get(i) + " ");
//        }
//
//        System.out.println();
//        System.out.println("Dirs:");
//        for (int i = 0; i < s.listOfDirs.size(); i++) {
//            System.out.print(s.listOfDirs.get(i) + " ");
//        }
    }

    public String getRoot() {

        return root;
    }

    public void setRoot(String root) {

        this.root = root;
    }

    public ArrayList<String> getListOfFiles() {
        return listOfFiles;
    }

    public void setListOfFiles(ArrayList<String> listOfFiles) {

        this.listOfFiles = listOfFiles;
    }

    public ArrayList<String> getListOfDirs() {

        return listOfDirs;
    }

    public void setListOfDirs(ArrayList<String> listOfDirs) {

        this.listOfDirs = listOfDirs;
    }

    public void getFiles(String path) throws Exception {
        File currentDir = new File(path);
        File[] all = currentDir.listFiles();

        for (int i = 0; i < all.length; i++) {
            if (all[i].isFile()) {
                this.listOfFiles.add(all[i].getName());
            } else if (all[i].isDirectory()) {
                this.listOfDirs.add(all[i].getName());
            } else {
                //not a file or directory
            }
        }
    }

    public String scan(String path, boolean outputToFile) {
        try {
            this.scanDir(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (outputToFile) {
            File out = new File("snapshot");
            try (FileOutputStream fout = new FileOutputStream(out)) {
                // create file if there is none
                if (!out.exists()) {
                    out.createNewFile();
                }

                byte[] dataBytes = fileData.getBytes();

                fout.write(dataBytes);
                fout.flush();
                fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return this.fileData;
    }

    public void scanDir(String path) throws Exception {
        File currentDir = new File(path);
        fileData += currentDir.getAbsoluteFile() + ",";

        if (currentDir.isFile()) {
            fileData += fh.hashFile(currentDir);
        } else {
            fileData += "directory";
        }
        fileData += "\n";

        if (currentDir.isDirectory()) {
            String[] items = currentDir.list();
            for (String name : items) {
                scanDir(path + "/" + name);
            }
        }
    }
}
