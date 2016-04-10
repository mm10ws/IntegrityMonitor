import java.io.FileInputStream;
import java.security.MessageDigest;

public class FileHash {


    public String hashFile(String fileName) throws Exception{
        MessageDigest m = MessageDigest.getInstance("SHA-256");
        FileInputStream f = new FileInputStream(fileName);

        byte[] fileBytes = new byte[1024];
        int count = 0;

        //read bytes from file
        while ((count = f.read(fileBytes)) != -1){
            m.update(fileBytes, 0, count);
        }

        //get hash of bytes
        byte[] hashBytes = m.digest();

        //convert to hex and represent as string
        StringBuffer s  = new StringBuffer("");
        for (int i = 0; i < hashBytes.length; i++){
            int temp = (hashBytes[i] & 0xff) + 0x100;
            s.append(Integer.toString(temp,16).substring(1));
        }

        return s.toString();
    }

    public static void main(String args[]){

        FileHash fh = new FileHash();

        try {
            System.out.println(fh.hashFile("remove_crw.cmd"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println("hello world");
    }
}
