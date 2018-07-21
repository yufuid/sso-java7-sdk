package com.yufu.idaas.sdk.bouncycastle.operator;

import com.yufu.idaas.sdk.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.yufu.idaas.sdk.bouncycastle.cert.X509CertificateHolder;

/**
 * Created by mac on 2017/1/18.
 */
public interface ContentVerifierProvider {
    boolean hasAssociatedCertificate();

    X509CertificateHolder getAssociatedCertificate();

    ContentVerifier get(AlgorithmIdentifier var1) throws OperatorCreationException;
}

