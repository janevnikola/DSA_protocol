import java.security.*;
import java.sql.SQLOutput;

//sb znaci signature

public class StationToStationProtocol {
    private static final int a = 3;


    public static void STS(int new_randomAlice_x, UserBob Bob) {

        double rezultatAliceEksponent = Math.pow(a, new_randomAlice_x);//a^x
        BobSendToAlice(Bob, rezultatAliceEksponent, new_randomAlice_x);
    }


    public static void BobSendToAlice(UserBob new_Bob, double new_rezultatEksponentAlice, int new_randomAlice_x) {
        int randomBob_Y = new_Bob.generate_RandomNumber();//random y
        double rezultatBobEksponent = Math.pow(a, randomBob_Y);//a^y na bob
        //new_rezultatEksponetAlice e a^x
        double x_y = new_randomAlice_x * randomBob_Y;//a^x*y presmetuva
        User_Alice.setAlice_randomNumber(new_randomAlice_x);
//        System.out.println("random number Y vo funkcijata e: " + User_Alice.getAlice_randomNumber());
        double kluc_K = Math.pow(a, x_y);//kluc K=a^k*y so vo ovoj slucaj e x_y
       /* System.out.println("Rezultat eksponent: " + rezultatBobEksponent);
        System.out.println("Random na bob: " + randomBob_Y);
        System.out.println("x*y: " + x_y);
        System.out.println("Klucot e: " + kluc_K);//klucot
        */

        String rezultatEksBob = Double.toString(rezultatBobEksponent);
        String rezultatEksAlice = Double.toString(new_rezultatEksponentAlice);

//PORAKA ZA POTPIS
        String poraka_zaPotpis = rezultatEksAlice + " " + rezultatEksBob;
        System.out.println("Porakata za potpis a^x i a^y: " + poraka_zaPotpis);//a^x, a^y rezultatite

        // System.out.println("String za bob: " + rezultatEksBob + " a obicno double: " + rezultatBobEksponent);
        //System.out.println("String za alice: " + rezultatEksAlice + " a obicno double: " + new_rezultatEksponentAlice);
        byte[] porakaDoAlice = new byte[0];
        //String potpisot = poraka_zaPotpis;
        byte[] signatureToSend = new byte[0];
        try {

            byte[] signature = DSA.signMessage(UserBob.getBob_PrivatenKluc(), poraka_zaPotpis);
            // System.out.println("potpisot e: " + signature);
            signatureToSend = signature;
            StringBuilder ds = new StringBuilder();
            for (byte b : signature) {
                ds.append(String.format("%02X ", b));
            }
            System.out.println("\nPotpisot pred da se enkriptira so kluc K: \n" + ds.toString());
            System.out.println();
            String klucK = Double.toString(kluc_K);

            byte[] new_porakaDoAlice = AES.encrypt(signature, klucK);
            porakaDoAlice = new_porakaDoAlice;
            StringBuilder es = new StringBuilder();
            for (byte b : new_porakaDoAlice) {
                es.append(String.format("%02X ", b));
            }
            System.out.println("Bob koga ke go enkriptira so kluc K potpisot: \n" + es.toString());
            //   System.out.println(new String(porakaDoAlice));
        } catch (NoSuchAlgorithmException | NoSuchProviderException | SignatureException | InvalidKeyException e) {
            e.printStackTrace();
        }
//a^y go isprakja na alice vo ovoj slucaj rezultatBobEksponent i enkripcijata porakaDoAlice
        AliceReceive(new_Bob, randomBob_Y, rezultatBobEksponent, porakaDoAlice,
                signatureToSend, new_rezultatEksponentAlice, new_randomAlice_x);
    }                                       //a^y

    //new_rezultatEksponentAlice
    //new_randomAlice_x
    public static void AliceReceive(UserBob bob, double new_randomBobY,
                                    double new_rezultatBobEksponent, byte[] enkriptirano, byte[] new_potpisot,
                                    double new_rezultatEksponentAlice, int new_randomAlice_x) {
        //  double randomAliceY = User_Alice.getAlice_randomNumber();
        double x_y = new_randomAlice_x * new_randomBobY;
        // System.out.println("Rezultatot e: " + x_y);
        double kluc_K = Math.pow(a, x_y);
        //     System.out.println("Kluc verifikacija:" + kluc_K);
        String klucK = Double.toString(kluc_K);
        //  System.out.println("String klucK " + klucK);
//new potpisot e signature pred da se dekriptira. a dekriptiran_signature e dekriptirano so kluc k
        byte[] dekriptiran_signature = AES.decrypt(enkriptirano, klucK);//koga ke se dekriptira Ek
 
        String New_rezultatEksponentAlice = Double.toString(new_rezultatEksponentAlice);
        String New_rezultatEksponentBob = Double.toString(new_rezultatBobEksponent);

        String poraka_zaPotpis = New_rezultatEksponentAlice + " " + New_rezultatEksponentBob;
        System.out.println("\nPorakata za potpis koga Alice ke treba povtorno da ja potpise: " + poraka_zaPotpis);
        try {//PublicKey pk, String message, byte[] signatrue
            System.out.println();
            //   System.out.println(dekriptiran_signature);
            boolean testA = DSA.verify(UserBob.getBob_JavenKluc(), poraka_zaPotpis, dekriptiran_signature);
            System.out.println("Verifikacija na potpisot: " + testA);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | SignatureException | InvalidKeyException e) {
            e.printStackTrace();
        }


        System.out.println();

    }//istoto scenario i kaj bob
    //Alice                  Bob
    // -------------------->
    //        EK(sA{αx, αy})
//Alice sends to Bob her corresponding encrypted
//signature on the exponentials, EK(sA{αx, αy}).

// Finally, Bob similarly verifies Alice’s
//encrypted signature using K and Alice’s public key.


    public static void main(String[] args) {
        User_Alice Alice = new User_Alice();
        UserBob Bob = new UserBob();
        int randomAlice_x = Alice.generate_RandomNumber();
        //Alice isprakja a^x do bob


        DSA dsa = new DSA();
        //   PublicKey javen_bob = null;
        try {
            KeyPair par_kluceviBob = dsa.generateKeyPair();
            PublicKey Bob_javen_kluc = par_kluceviBob.getPublic();
            PrivateKey Bob_privaten_kluc = par_kluceviBob.getPrivate();

            Bob.setBob_JavenKluc(Bob_javen_kluc);
            Bob.setBob_PrivatenKluc(Bob_privaten_kluc);

            KeyPair par_kluceviAlice = dsa.generateKeyPair();
            PublicKey Alice_javen_kluc = par_kluceviAlice.getPublic();
            PrivateKey Alice_privaten_kluc = par_kluceviAlice.getPrivate();


        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }

        STS(randomAlice_x, Bob);

    }
}
