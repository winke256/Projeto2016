package Util;

import java.io.*;
import javax.crypto.*;
import java.security.*;
import java.security.spec.*;
import java.security.cert.*;
public class CryptRSA {

    private byte[] textoCifrado;
    private byte[] textoDecifrado;
    private final String PUBLICKEY = "public.key";
    private final String PRIVATEKEY = "private.key";

    public CryptRSA() throws Exception{
        textoCifrado = null;
        textoDecifrado = null;
        geraParDeChaves();
    }

    private void geraParDeChaves() throws IOException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, CertificateException, KeyStoreException {
        if(!new File(PUBLICKEY).exists() && !new File(PRIVATEKEY).exists()){
            final int RSAKEYSIZE = 1024;
            KeyPairGenerator kpg = KeyPairGenerator.getInstance ("RSA");
            kpg.initialize (new RSAKeyGenParameterSpec(RSAKEYSIZE, RSAKeyGenParameterSpec.F4));
            KeyPair kpr = kpg.generateKeyPair();
            PrivateKey oPriv = kpr.getPrivate();
            PublicKey oPub = kpr.getPublic();
            //-- Gravando a chave publica em formato serializado
            ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream (PUBLICKEY));
            oos.writeObject (oPub);
            oos.close();
            //-- Gravando a chave privada em formato serializado
            oos = new ObjectOutputStream (new FileOutputStream (PRIVATEKEY));
            oos.writeObject (oPriv);
            oos.close();
        }
    }

    public byte[] geraCifra(byte[] texto) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, Exception {
        ObjectInputStream ois = new ObjectInputStream (new FileInputStream (new File(PUBLICKEY)));
        PublicKey iPub = (PublicKey) ois.readObject();
        ois.close();
        Cipher rsacf = Cipher.getInstance ("RSA");
        rsacf.init (Cipher.ENCRYPT_MODE, iPub);
        return textoCifrado = rsacf.doFinal (texto);
    }

    public byte[] geraDecifra(byte[] texto) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, Exception {
        ObjectInputStream ois = new ObjectInputStream (new FileInputStream (new File(PRIVATEKEY)));
        PrivateKey iPrv = (PrivateKey) ois.readObject();
        ois.close();
        Cipher rsacf = Cipher.getInstance("RSA");
        rsacf.init (Cipher.DECRYPT_MODE, iPrv);
        return textoDecifrado = rsacf.doFinal (texto);
    }

    public byte[] getTextoCifrado() throws Exception {
        return textoCifrado;
    }

    public byte[] getTextoDecifrado() throws Exception {
        return textoDecifrado;
    }
}