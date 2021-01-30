package me.wuxie.wakeshow.wakeshow.encrypt;

import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileOutputStream;
        import java.io.FileWriter;
        import java.io.IOException;
        import java.security.GeneralSecurityException;
        import java.util.Arrays;
        import javax.crypto.Cipher;
        import javax.crypto.SecretKey;
        import javax.crypto.SecretKeyFactory;
        import javax.crypto.spec.PBEKeySpec;
        import javax.crypto.spec.PBEParameterSpec;

public class FileEncryptor {
    private static final byte[] salt = {
            (byte) 0x43, (byte) 0x76, (byte) 0x95, (byte) 0xc7,
            (byte) 0x5b, (byte) 0xd7, (byte) 0x45, (byte) 0x17
    };

    private static Cipher makeCipher(String pass, Boolean decryptMode) throws GeneralSecurityException{
        PBEKeySpec keySpec = new PBEKeySpec(pass.toCharArray());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(keySpec);
        PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, 42);
        Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
        if(decryptMode){
            cipher.init(Cipher.ENCRYPT_MODE, key, pbeParamSpec);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, key, pbeParamSpec);
        }
        return cipher;
    }

    public static void encryptFile(File resFile,File encryptFile, String pass)
            throws IOException, GeneralSecurityException{
        byte[] decData;
        byte[] encData;
        Cipher cipher = FileEncryptor.makeCipher(pass, true);
        FileInputStream inStream = new FileInputStream(resFile);
        int blockSize = 8;
        int paddedCount = blockSize - ((int) resFile.length()  % blockSize );
        int padded = (int) resFile.length() + paddedCount;
        decData = new byte[padded];
        inStream.read(decData);
        inStream.close();
        for(int i = (int) resFile.length(); i < padded; ++i ) {
            decData[i] = (byte)paddedCount;
        }
        encData = cipher.doFinal(decData);
        FileOutputStream outStream = new FileOutputStream(encryptFile);
        outStream.write(encData);
        outStream.close();
    }

    /*public static void decryptFile(File encryptFile,File decryptFile, String pass)
            throws GeneralSecurityException, IOException{
        byte[] encData;
        byte[] decData;
        Cipher cipher = FileEncryptor.makeCipher(pass, false);
        FileInputStream inStream = new FileInputStream(encryptFile);
        encData = new byte[(int) encryptFile.length()];
        inStream.read(encData);
        inStream.close();
        decData = cipher.doFinal(encData);
        int padCount = decData[decData.length - 1];
        if( padCount >= 1 && padCount <= 8 ) {
            decData = Arrays.copyOfRange( decData , 0, decData.length - padCount);
        }
        FileOutputStream target = new FileOutputStream(decryptFile);
        target.write(decData);
        target.close();
    }*/

    /*private static void keyToFile(SecretKey key){
        try {
            File keyFile = new File("C:\\keyfile.txt");
            FileWriter keyStream = new FileWriter(keyFile);
            String encodedKey = "\n" + "Encoded version of key:  " + key.getEncoded().toString();
            keyStream.write(key.toString());
            keyStream.write(encodedKey);
            keyStream.close();
        } catch (IOException e) {
            System.err.println("Failure writing key to file");
            e.printStackTrace();
        }
    }*/
}