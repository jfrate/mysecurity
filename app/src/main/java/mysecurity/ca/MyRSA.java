package mysecurity.ca;

import java.math.BigInteger;
import java.security.SecureRandom;

public class MyRSA {
    private BigInteger n, d, e;

    private int bitlen = 2048;

    /** Constructor: Generate a new RSA key pair with a given bit length */
    public MyRSA(int bits) {
        bitlen = bits;
        SecureRandom r = new SecureRandom();
        BigInteger p = new BigInteger(bitlen / 2, 100, r);
        BigInteger q = new BigInteger(bitlen / 2, 100, r);
        n = p.multiply(q);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        e = new BigInteger("65537"); // Common value for e
        d = e.modInverse(phi);
    }

    /** Encrypt the given plaintext message using the public key */
    public synchronized String encrypt(String message) {
        return (new BigInteger(message.getBytes())).modPow(e, n).toString();
    }

    /** Decrypt the given ciphertext message using the private key */
    public synchronized String decrypt(String message) {
        return new String((new BigInteger(message)).modPow(d, n).toByteArray());
    }

    /** Get the public key */
    public BigInteger[] getPublicKey() {
        return new BigInteger[] { e, n };
    }

    /** Get the private key */
    public BigInteger[] getPrivateKey() {
        return new BigInteger[] { d, n };
    }

    public static void main(String[] args) {
        MyRSA rsa = new MyRSA(2048);

        // Print public and private keys
        System.out.println("Public Key (e, n): " + rsa.getPublicKey()[0] + ", " + rsa.getPublicKey()[1]);
        System.out.println("Private Key (d, n): " + rsa.getPrivateKey()[0] + ", " + rsa.getPrivateKey()[1]);

        String plaintext = "This is a secret message";
        System.out.println("Original Message: " + plaintext);

        // Encrypt the message
        String ciphertext = rsa.encrypt(plaintext);
        System.out.println("Encrypted Message: " + ciphertext);

        // Decrypt the message
        String decryptedMessage = rsa.decrypt(ciphertext);
        System.out.println("Decrypted Message: " + decryptedMessage);
    }
}
