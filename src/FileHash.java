/*
Implements secure hashing of files with SHA-256
author: mm
 */

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class FileHash {

    public static void main(String args[]) {

        FileHash fh = new FileHash();
        File f = new File("remove_crw.cmd");

        try {
            System.out.println(fh.hashFile(f));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println("hello world");
    }

    /*
    gives the SHA-256 hash given the absolute path to a file
    inputs: absolute path of file (string)
    output: SHA-256 hash of file (string)
     */

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
        StringBuilder s = new StringBuilder("");
        for (int i = 0; i < hashBytes.length; i++){
            int temp = (hashBytes[i] & 0xff) + 0x100;
            s.append(Integer.toString(temp,16).substring(1));
        }

        return s.toString();
    }

    /*
    gives the SHA-256 hash given a file object
    inputs: file (File)
    output: SHA-256 hash of file (string)
     */

    public String hashFile(File f) throws Exception {
        MessageDigest m = MessageDigest.getInstance("SHA-256");
        FileInputStream fis = new FileInputStream(f);

        byte[] fileBytes = new byte[1024];
        int count = 0;

        //read bytes from file
        while ((count = fis.read(fileBytes)) != -1) {
            m.update(fileBytes, 0, count);
        }

        //get hash of bytes
        byte[] hashBytes = m.digest();

        //convert to hex and represent as string
        StringBuilder s = new StringBuilder("");
        for (int i = 0; i < hashBytes.length; i++) {
            int temp = (hashBytes[i] & 0xff) + 0x100;
            s.append(Integer.toString(temp, 16).substring(1));
        }

        return s.toString();
    }
}
