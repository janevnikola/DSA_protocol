import java.security.*;
import java.sql.SQLOutput;
import java.util.Random;

//sb znaci signature

public class StationToStationProtocol {
    private static final int a = 255;


    public static void AliceSendExponentToBob(int x) {

        double rezultat = Math.pow(a, x);
        System.out.println("Eskponent na " + a + " so x ="+x+ " so a^x: " + rezultat);
    }

    public static void main(String[] args) {
        User_Alice Alice = new User_Alice();
        UserBob Bob = new UserBob();
        int x = Alice.generate_RandomNumber();
        //Alice isprakja a^x do bob
        System.out.println("Test");


        DSA dsa = new DSA();

        try {
            KeyPair par_kluceviBob = dsa.generateKeyPair();
            PublicKey Bob_javen_kluc = par_kluceviBob.getPublic();
            PrivateKey Bob_privaten_kluc = par_kluceviBob.getPrivate();

            Bob.setBob_JavenKluc(Bob_javen_kluc);
            Bob.setBob_PrivatenKluc(Bob_privaten_kluc);


            KeyPair par_kluceviAlice = dsa.generateKeyPair();
            PublicKey Alice_javen_kluc = par_kluceviAlice.getPublic();
            PrivateKey Alice_privaten_kluc = par_kluceviAlice.getPrivate();


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }


        AliceSendExponentToBob(x);
    }
}
