package ut2_MUGE;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class FirmaDigital {
    
	PublicKey publicKey;
	PrivateKey privateKey;
    
    public FirmaDigital() {
    }
    
    public boolean existenClaves() {
        return publicKey != null && privateKey != null;
    }
    
    public PublicKey getClavePub() {
    	return this.publicKey;
    }
    
    public PrivateKey getClavePriv() {
    	return this.privateKey;
    }

    public void generarClaves() {
    	try {
    	    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    	    keyPairGenerator.initialize(1024);
    	    KeyPair keyPair = keyPairGenerator.generateKeyPair();

    	    publicKey = keyPair.getPublic();
    	    privateKey = keyPair.getPrivate();
    	    
    	} catch (NoSuchAlgorithmException e) {
    	    e.printStackTrace();
    	}
    }
}
