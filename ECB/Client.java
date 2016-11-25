import java.net.ServerSocket;
import java.net.Socket;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.crypto.*;
import java.security.*;
import sun.misc.*;
import javax.crypto.spec.*;

public class Client {
    public static int port = 20;
    
    public static void main(String args[]) throws Exception {

        // plainText, key, iv, and random 
        String plainText = "Hello, ECB test.";
        String key = "password";
        SecureRandom random = new SecureRandom(); 

        // usable key (spec, factory and secret)
        DESKeySpec keySpec = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(keySpec);

        // encrypt
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey , random);
        byte[] cipherData = cipher.doFinal(plainText.getBytes("UTF-8"));

        // connect server
        Socket client = new Socket("127.0.0.1", port);

        // print to screen
        System.out.println("...ECB...");
        System.out.println("PlainText :" + plainText);
        System.out.println("CipherText:" + cipherData);

        // write outputstream
        OutputStream out = client.getOutputStream();
        out.write(cipherData);

        // end of connect, close stream and class
        out.close();
        client.close();

        /*
        InputStream in = client.getInputStream();
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(in)
        );
        
        String msg = reader.readLine();
        System.out.println(msg);
        client.close();
        */
    }
}