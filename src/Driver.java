import java.util.Scanner;

public class Driver {
    public static void main(String args[]) {

        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter target directory:");
        String root = reader.nextLine();
        System.out.println("Target directory set to " + root);


        int option = -1;


        while (true) {
            System.out.println("Please select an option:");
            System.out.println("1. Create new snapshot");
            System.out.println("2. Create report");
            System.out.println("3. Exit");
            int choice = reader.nextInt();
            if (choice >= 1 && choice <= 3) {
                if (choice == 1) {
                    System.out.println("Scanning " + root);
                    FileScan s = new FileScan(root);
                    FileHash f = new FileHash();
                    s.scan(root, true);

                    System.out.print("Snapshot created: ");

                    try {
                        String hash = f.hashFile("snapshot");
                        System.out.println(hash);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (choice == 2) {
                    FileHash f = new FileHash();
                    try {
                        String hash = f.hashFile("snapshot");
                        System.out.println("Snapshot hash value: " + hash);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Monitor m = new Monitor(root);
                    System.out.println("Creating report for " + root);
                    m.check();
                    System.out.println("Report created");
                } else {
                    break;
                }
            } else {
                System.out.println("Please enter correct number");
            }

        }
    }
}
