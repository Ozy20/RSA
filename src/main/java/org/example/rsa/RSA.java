package org.example.rsa;

import java.math.BigInteger;
import java.util.Random;

public class RSA {
    private static final Random random = new Random();

    public static boolean millerTest(BigInteger n) {
        if (n.compareTo(BigInteger.ONE) <= 0) return false;
        if (n.equals(BigInteger.TWO) || n.equals(BigInteger.valueOf(3))) return true;

        BigInteger nMinusOne = n.subtract(BigInteger.ONE);
        BigInteger q = nMinusOne;
        int k = 0;

        while (q.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            q = q.divide(BigInteger.TWO);
            k++;
        }

        BigInteger a;
        do {
            a = new BigInteger(n.bitLength(), random);
        } while (a.compareTo(BigInteger.ONE) <= 0 || a.compareTo(nMinusOne) >= 0);

        BigInteger x = a.modPow(q, n);
        if (x.equals(BigInteger.ONE) || x.equals(nMinusOne)) return true;

        for (int j = 0; j < k - 1; j++) {
            x = x.modPow(BigInteger.TWO, n);
            if (x.equals(nMinusOne)) return true;
        }

        return false;
    }


    public static BigInteger generatePrime(int l) {
        BigInteger prime = BigInteger.ONE;
        for(int i = 0 ; i < 10 ; i++){
            do {
                prime = new BigInteger(l, random);
            } while (!millerTest(prime));
        }
        return prime;
    }

    public static RSAKeyPair generateKeys(int l) {
        BigInteger p = generatePrime(l);
        BigInteger q = generatePrime(l);
        while( p.compareTo(q) == 0){
            p = generatePrime(l);
            q = generatePrime(l);
        }
        BigInteger n = p.multiply(q);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        BigInteger e;
        do {
            e = new BigInteger(phi.bitLength(), random);
        } while (e.compareTo(BigInteger.ONE) <= 0 || e.compareTo(phi) >= 0 || !e.gcd(phi).equals(BigInteger.ONE));

        BigInteger d = e.modInverse(phi);

        return new RSAKeyPair(e, d, n);
    }

    public static BigInteger encrypt(BigInteger m, BigInteger e, BigInteger n) {
        return m.modPow(e, n);
    }

    public static BigInteger decrypt(BigInteger c, BigInteger d, BigInteger n) {
        return c.modPow(d, n);
    }


    public static String encryptText(String plainText, BigInteger e, BigInteger n) {
        byte[] bytes = plainText.getBytes();
        StringBuilder encrypted = new StringBuilder();

        for (byte b : bytes) {
            BigInteger m = BigInteger.valueOf(b);
            BigInteger c = encrypt(m, e, n);
            encrypted.append(c.toString()).append(" ");
        }

        return encrypted.toString().trim();
    }

    public static String decryptText(String cipherText, BigInteger d, BigInteger n) {
        String[] chunks = cipherText.split(" ");
        byte[] decryptedBytes = new byte[chunks.length];

        for (int i = 0; i < chunks.length; i++) {
            BigInteger c = new BigInteger(chunks[i]);
            BigInteger m = decrypt(c, d, n);
            decryptedBytes[i] = m.byteValue();
        }

        return new String(decryptedBytes);
    }

    public static class RSAKeyPair {
        public final BigInteger e, d, n;

        public RSAKeyPair(BigInteger e, BigInteger d, BigInteger n) {
            this.e = e;
            this.d = d;
            this.n = n;
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        int bitLength = 16;
        RSAKeyPair keys = generateKeys(bitLength);

        System.out.println("Public Key: (e = " + keys.e + ", n = " + keys.n + ")");
        System.out.println("Private Key: (d = " + keys.d + ", n = " + keys.n + ")");

        String message = "ggfhgfhgfhgfhgfhfhffgfgfhgffhgfhhgfhgfhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh";
        String encrypted = encryptText(message, keys.e, keys.n);
        String decrypted = decryptText(encrypted, keys.d, keys.n);

        System.out.println("Original message: " + message);
        System.out.println("Encrypted message: " + encrypted);
        System.out.println("Decrypted message: " + decrypted);
    }
}
