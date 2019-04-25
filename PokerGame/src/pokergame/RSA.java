/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokergame;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.*;

public class RSA {
    public static final String ALGORITHM = "RSA";
    static PrivateKey PRIV_KEY = null;
    static PublicKey PUB_KEY = null;

public static void generateKey() throws Exception{
    KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
    keyGen.initialize(1024);
    KeyPair key = keyGen.generateKeyPair();
    
    PRIV_KEY = key.getPrivate();
    PUB_KEY = key.getPublic();
}
public String encrypt(String in, PublicKey key) throws Exception{
    byte[] encrypt;
   
    final Cipher cipher = Cipher.getInstance(ALGORITHM);
    
    cipher.init(Cipher.ENCRYPT_MODE,key);
    encrypt = cipher.doFinal(in.getBytes("UTF8"));
    
    return new sun.misc.BASE64Encoder().encode(encrypt);
}    

public String decrypt(String in, PrivateKey key) throws Exception{
    byte[] decrypt;
    final Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(Cipher.DECRYPT_MODE,key);
    decrypt = cipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(in));
    
    return new String(decrypt,"UTF8");
}
public PublicKey getPubKey(){
    return PUB_KEY;
  }
  public PrivateKey getPrivKey(){
    return PRIV_KEY;
  }


}

