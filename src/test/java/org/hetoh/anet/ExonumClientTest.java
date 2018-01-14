package org.hetoh.anet;

import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.spec.EdDSAPrivateKeySpec;
import org.junit.Test;

import java.math.BigInteger;
import java.security.spec.PKCS8EncodedKeySpec;
import org.apache.commons.codec.binary.Base64;

public class ExonumClientTest {

    @Test
    public void testTransferFunds() throws Exception {
        ExonumClient exonumClient = new ExonumClient();
        String from = "03e657ae71e51be60a45b4bd20bcf79ff52f0c037ae6da0540a0e0066132b472";
        String to = "d1e877472a4585d515b13f52ae7bfded1ccea511816d7772cb17e1ab20830819";
        int amount = 10;
        exonumClient.transferFunds(from, to, amount);


    }

    @Test
    public void testKeys() throws Exception {
        String TEST_PRIVKEY = "b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZW" +
                "QyNTUxOQAAACAX6f2Tr/+x4LrSSIONE9x03BrL1xFnilhXPlTG+toO2wAAAKjoxUYp6MVG" +
                "KQAAAAtzc2gtZWQyNTUxOQAAACAX6f2Tr/+x4LrSSIONE9x03BrL1xFnilhXPlTG+toO2w" +
                "AAAECaoqFUT7/tItvpzKrkmgPlO97+Rt0gwNLUSHt908ly/hfp/ZOv/7HgutJIg40T3HTc" +
                "GsvXEWeKWFc+VMb62g7bAAAAI2JqdWhuQEJlbmphbWlucy1NYWNCb29rLVByby0yLmxvY2" +
                "FsAQI=";

        PKCS8EncodedKeySpec encoded = new PKCS8EncodedKeySpec(Base64.decodeBase64(TEST_PRIVKEY));
        EdDSAPrivateKey keyIn = new EdDSAPrivateKey(encoded);

        // Encode
        EdDSAPrivateKeySpec decoded = new EdDSAPrivateKeySpec(
                keyIn.getSeed(),
                keyIn.getH(),
                keyIn.geta(),
                keyIn.getA(),
                keyIn.getParams());
        EdDSAPrivateKey keyOut = new EdDSAPrivateKey(decoded);
        System.out.println(keyOut);
    }
}
