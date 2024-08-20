package mysecurity.ca;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class CertificateManager {
    public void generateCertificate() {
        System.out.println("start generate certificate");
        
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            final PublicKey publicKey = keyPair.getPublic();
            final PrivateKey privateKey = keyPair.getPrivate();
            final byte[] publicBytes = publicKey.getEncoded();
            final byte[] privateBytes = privateKey.getEncoded();
            final String publicString = Base64.getEncoder().encodeToString(publicBytes);
            final String privateString = Base64.getEncoder().encodeToString(privateBytes);
            System.out.println("... public: " + publicString);
            System.out.println("... private: " + privateString);
            System.out.println("end generate certificate");
        }
        catch(final Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}