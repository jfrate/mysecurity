package mysecurity;

import mysecurity.ca.CertificateManager;

public class App {
    public static void main(String[] args) {
        final CertificateManager certificateManager = new CertificateManager();
        certificateManager.generateCertificate();
    }
}
