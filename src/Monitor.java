import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

public class Monitor {
    public HashMap<String, String> snapshotHashMap = new HashMap<String, String>();
    public HashMap<String, String> currentHashMap = new HashMap<String, String>();
    public String root = "";


    public Monitor(String root) {
        this.root = root;
    }

    public static void main(String args[]) {
        Monitor m = new Monitor(""); //set root directory here
        m.check();


    }

    public void compare() {
        String diff = "";
        Iterator<String> itr1 = snapshotHashMap.keySet().iterator();
        while (itr1.hasNext()) {
            String s1 = itr1.next();
            if (currentHashMap.containsKey(s1)) {
                if (!snapshotHashMap.get(s1).equals(currentHashMap.get(s1))) {
                    diff += "change: " + s1 + ", " + snapshotHashMap.get(s1) + " -> " + currentHashMap.get(s1) + "\n";
                }
                itr1.remove();
                currentHashMap.remove(s1);
            }
        }

        Iterator<String> itr2 = snapshotHashMap.keySet().iterator();
        while (itr2.hasNext()) {
            String s2 = itr2.next();
            diff += "deleted: " + s2 + ", " + snapshotHashMap.get(s2) + "\n";
        }

        Iterator<String> itr3 = currentHashMap.keySet().iterator();
        while (itr3.hasNext()) {
            String s3 = itr3.next();
            diff += "added: " + s3 + ", " + currentHashMap.get(s3) + "\n";
        }

        File out = new File("report.txt");
        try (FileOutputStream fout = new FileOutputStream(out)) {
            // create file if there is none
            if (!out.exists()) {
                out.createNewFile();
            }

            byte[] dataBytes = diff.getBytes();

            fout.write(dataBytes);
            fout.flush();
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void check() {
        snapshotHashMap.clear();
        currentHashMap.clear();

        File snapshotFile = new File("snapshot");
        if (snapshotFile.exists()) {
            try (BufferedReader b = new BufferedReader(new FileReader(snapshotFile))) {
                String row;
                while ((row = b.readLine()) != null) {
                    String[] split = row.split(",");
                    snapshotHashMap.put(split[0], split[1]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            FileScan s = new FileScan(this.root);
            String newData = s.scan(s.getRoot(), false);
            try (BufferedReader b = new BufferedReader(new StringReader(newData))) {
                String row;
                while ((row = b.readLine()) != null) {
                    String[] split = row.split(",");
                    currentHashMap.put(split[0], split[1]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("No snapshot file exists");
        }

        this.compare();


    }

}
