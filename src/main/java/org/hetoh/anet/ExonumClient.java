package org.hetoh.anet;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.codec.binary.Hex;
import org.eclipse.jetty.util.log.StdErrLog;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class ExonumClient {

    private static final JsonParser parser = new JsonParser();

    private static final Client client = Client.create();

    private static final StdErrLog logger = new StdErrLog();

//    String from = "03e657ae71e51be60a45b4bd20bcf79ff52f0c037ae6da0540a0e0066132b472";
//    String to = "d1e877472a4585d515b13f52ae7bfded1ccea511816d7772cb17e1ab20830819";
//    int amount = 123;

    public void transferFunds(String from, String to, int amount) {
        try {
            WebResource webResource = client.resource("http://127.0.0.1:8000/api/services/cryptocurrency/v1/wallets/transfer");
            String input = getTransaction(from, to, amount).toString();
            logger.info("Initiating transfer of funds...");
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
            logger.info("Transfer request succesful.  Response: \n");
            String output = response.getEntity(String.class);
            logger.info(output);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e);
        }
    }

    public JsonObject getTransaction(String from, String to, int amount) {
        String body = "{\n" +
                "    \"from\": \"" + from + "\",\n" +
                "    \"to\": \"" + to + "\",\n" +
                "    \"amount\": \"" + Integer.toString(amount) + "\",\n" +
                "    \"seed\": \"12623766328194547469\"\n" +
                "  }";


        String json = "{\n" +
                "  \"body\": " + body + ",\n" +
                "  \"network_id\": 0,\n" +
                "  \"protocol_version\": 0,\n" +
                "  \"service_id\": 1,\n" +
                "  \"message_id\": 2,\n" +
                "  \"signature\": \"2c5e9eee1b526299770b3677ffd0d727f693ee181540e1914f5a84801dfd410967fce4c22eda621701c2b9c676ed62bc48df9c973462a8514ffb32bec202f103\"\n" +
                "}";

        try {
            String slimBody = parser.parse(body).toString();
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(slimBody.getBytes(StandardCharsets.UTF_8));
            String signature = Hex.encodeHexString(hash);

            logger.info(signature);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }


        JsonElement result = parser.parse(json);

        return result.getAsJsonObject();
    }
}
