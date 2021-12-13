import java.security.*;
import java.util.Random;

public class UserBob {
    KeyPair par_klucevi;
    static PublicKey Bob_JavenKluc;
    static PrivateKey Bob_PrivatenKluc;
    //DSA dsa;
   static PublicKey Alice_javenKluc;
    private int Bob_randomNumber;

    public UserBob() {
      //  User_Alice Alice = new User_Alice();
        Alice_javenKluc = User_Alice.getAlice_JavenKluc();
        //UserBob.setAlice_javenKluc(Alice_javenKluc);
    }

    public static void setAlice_javenKluc(PublicKey alice_javenKluc) {
        Alice_javenKluc = alice_javenKluc;
    }

    public int generate_RandomNumber() {
        Random random = new Random(); //instance of random class
        int gorna_granica = 100;
        //generate random values from 0-24
        int random_number = random.nextInt(gorna_granica);
        // System.out.println(nonce);
        Bob_randomNumber = random_number;
        return Bob_randomNumber;
    }


    public KeyPair getPar_klucevi() {
        return par_klucevi;
    }

    public void setPar_klucevi(KeyPair par_klucevi) {
        this.par_klucevi = par_klucevi;
    }

    public static PublicKey getBob_JavenKluc() {
        return Bob_JavenKluc;
    }

    public void setBob_JavenKluc(PublicKey bob_JavenKluc) {
        Bob_JavenKluc = bob_JavenKluc;
    }

    public PrivateKey getBob_PrivatenKluc() {
        return Bob_PrivatenKluc;
    }

    public void setBob_PrivatenKluc(PrivateKey bob_PrivatenKluc) {
        Bob_PrivatenKluc = bob_PrivatenKluc;
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
            KeyPair par_klucevi = generateKeyPair();
            Bob_JavenKluc = par_klucevi.getPublic();
            Bob_PrivatenKluc = par_klucevi.getPrivate();
            //  par_klucevi = generateKeyPair();
            System.out.println("Bob javen kluc: " + Bob_JavenKluc);
            System.out.println("Bob privaten kluc: " + Bob_PrivatenKluc);


        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }

    }



}
