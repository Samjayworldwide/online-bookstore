import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;


public class Main {
    public static void main(String[] args) {




//        int length = 16; // AES-128 requires a 16-byte key
//
//        byte[] keyBytes = encryptionKey.getBytes();
//        byte[] paddedKey = Arrays.copyOf(keyBytes, length);
//        String key =  new String(paddedKey);
//
//        encryptionKey = key;
//
//        // Create AES cipher instance
//        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
//        SecretKeySpec secretKey = new SecretKeySpec(encryptionKey.getBytes(), "AES");
//        cipher.init(Cipher.DECRYPT_MODE, secretKey);
//
//        // Decode Base64 encoded string
//        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(password));
//
//        // Convert bytes to string
//        String decryptedData = new String(decryptedBytes);
    }
}
