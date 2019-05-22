package com.yufu.idaas.sdk.authn;

import com.yufu.idaas.sdk.bouncycastle.asn1.pkcs.RSAPublicKey;
import com.yufu.idaas.sdk.bouncycastle.util.io.pem.PemReader;
import com.yufu.idaas.sdk.exception.CannotRetrieveKeyException;
import org.apache.commons.io.Charsets;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by shuowang on 2018/5/2.
 */
public class FilePublicKeySupplier implements IKeySupplier<RSAPublicKey> {

    public RSAPublicKey getKeyFromPath(final String path) throws CannotRetrieveKeyException {

        try {
            PemReader reader = new PemReader(new InputStreamReader(
                new FileInputStream(path), Charsets.UTF_8
            ));
            byte[] keyBytes = reader.readPemObject().getContent();
            KeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) factory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new CannotRetrieveKeyException(e);
        }
    }

    public RSAPublicKey getKeyFromString(final String keyString) throws CannotRetrieveKeyException {
        try {
            byte[] keyBytes = keyString.getBytes(Charsets.UTF_8);
            KeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) factory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new CannotRetrieveKeyException(e);
        }
    }
}
