import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import javax.crypto.*;
import java.security.*;
import sun.misc.*;
import javax.crypto.spec.*;

public class Server {
    public static int port = 20;
 
    public static void main(String args[]) throws Exception {
        ServerSocket ss = new ServerSocket(port);
        while (true) {
            Socket server = ss.accept();
            System.out.println("Someone connected.");

            // use baos to read byte array , cause InputStream.read() can read 1byte/times
            InputStream input = server.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte [] buffer = new byte[512];
            int length = 0;     // record length to prevent unnecessary data
            while ( (length = input.read(buffer)) !=-1)     // if length = -1 means no data
                baos.write(buffer, 0, length);
            byte data [] = baos.toByteArray();

            // usable key (spec, factory and secret)
            String key = "password";
            DESKeySpec keySpec = new DESKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(keySpec);

            // decrypt
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE , secretKey);
            byte[] plain = cipher.doFinal(data);
            System.out.println(new String(plain));

            // close stream and socket
            input.close();
            server.close();
        }
    }
}