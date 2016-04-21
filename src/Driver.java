import java.util.Scanner;

public class Driver {
    public static void main(String args[]) {

        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter target directory:");
        String root = reader.nextLine();
        System.out.println("Target directory set to " + root);


        int option = -1; //menu option


        while (true) {
            System.out.println("Please select an option:");
            System.out.println("1. Create new snapshot");
            System.out.println("2. Create report");
            System.out.println("3. Exit");

            int choice = reader.nextInt();

            //make sure choice is in valid range
            if (choice >= 1 && choice <= 3) {
                if (choice == 1) {
                    System.out.println("Scanning " + root);
                    FileScan s = new FileScan(root);
                    FileHash f = new FileHash();
                    s.scan(root, true); //scan target and create snapshot file

                    System.out.print("Snapshot created: ");

                    //hash the snapshot file and display to user
                    try {
                        String hash = f.hashFile("snapshot");
                        System.out.println(hash);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (choice == 2) {
                    FileHash f = new FileHash();
                    //hash the snapshot file and display to user
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
