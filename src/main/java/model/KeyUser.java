package model;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyUser {
    private KeyPair keyPair;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private String idUser;
    private boolean isActive;

    public void genKey() throws Exception {
        KeyPairGenerator kGenerator = KeyPairGenerator.getInstance("RSA");
        kGenerator.initialize(2048);
        keyPair = kGenerator.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    public String publicKeyToString() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public String privateKeyToString() {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    public PublicKey stringToPublicKey(String publicKeyString) {
        try {
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        } catch (Exception e) {
            // Xử lý ngoại lệ khi chuyển đổi không thành công
            e.printStackTrace();
            return null;
        }
    }

    public PrivateKey stringToPrivateKey(String privateKeyString) {
        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        } catch (Exception e) {
            // Xử lý ngoại lệ khi chuyển đổi không thành công
            e.printStackTrace();
            return null;
        }
    }


    public KeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
