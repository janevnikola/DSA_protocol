import java.security.*;
import java.util.Random;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

public class User_Alice {
   // KeyPair par_klucevi;
    static PublicKey Alice_JavenKluc;
    static PrivateKey Alice_PrivatenKluc;
   // DSA dsa;
  static  PublicKey Bob_javenKluc;
    int Alice_randomNumber;

    public User_Alice() {
       // UserBob Bob = new UserBob();
        Bob_javenKluc = UserBob.getBob_JavenKluc();
        //UserBob.setAlice_javenKluc(Alice_javenKluc);
    }


    public int getAlice_randomNumber() {
        return Alice_randomNumber;
    }

    public int generate_RandomNumber() {
        Random random = new Random(); //instance of random class
        int gorna_granica = 100;
        //generate random values from 0-24
        int random_number = random.nextInt(gorna_granica);
        // System.out.println(nonce);
        Alice_randomNumber = random_number;
        System.out.println("Random na alice: "+Alice_randomNumber);
        return Alice_randomNumber;
    }


    public static PublicKey getAlice_JavenKluc() {
        return Alice_JavenKluc;
    }

    public void setAlice_JavenKluc(PublicKey alice_JavenKluc) {
        Alice_JavenKluc = alice_JavenKluc;
    }

    public PrivateKey getAlice_PrivatenKluc() {
        return Alice_PrivatenKluc;
    }

    public void setAlice_PrivatenKluc(PrivateKey alice_PrivatenKluc) {
        Alice_PrivatenKluc = alice_PrivatenKluc;
    }

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(1024, random);
        KeyPair pair = keyGen.generateKeyPair();
        return pair;
    }

    public static byte[] signMessage(PrivateKey sk, String message)
            throws NoSuchAlgorithmException, NoSuchProviderException, SignatureException, InvalidKeyException {
        Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
        sig.initSign(sk);
        sig.update(message.getBytes());
        return sig.sign();
    }


    public static boolean verify(PublicKey pk, String message, byte[] signatrue)
            throws NoSuchAlgorithmException, NoSuchProviderException, SignatureException, InvalidKeyException {
        Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
        sig.initVerify(pk);
        sig.update(message.getBytes());
        return sig.verify(signatrue);
    }

    public static void GenerirajKlucevi() {
//PublicKey Alice_JavenKluc;
//    PrivateKey Alice_PrivatenKluc;
    //    KeyPair par_klucevi = null;

        try {
            KeyPair par_klucevi=generateKeyPair();
            Alice_JavenKluc  = par_klucevi.getPublic();
            Alice_PrivatenKluc = par_klucevi.getPrivate();
          //  par_klucevi = generateKeyPair();
             System.out.println("Alice javen kluc: "+Alice_JavenKluc);
            System.out.println("Alice privaten kluc: "+Alice_PrivatenKluc);



        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }

    }
}
