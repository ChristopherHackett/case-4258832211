import com.goebl.david.Request;
import com.goebl.david.Response;
import com.goebl.david.Webb;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

/**
 * Created by chackett on 10/08/2017.
 */
public class Helpers {
    static void logResponse(Response response, Logger logger) {
        logger.info("x-amzn-requestid: {}",response.getHeaderField("x-amzn-requestid"));
    }

    static String b64File(File testFile) {

        String content = "";
        try {

            FileInputStream inputFile = new FileInputStream(testFile);

            byte byteArray[] = new byte[(int) testFile.length()];
            inputFile.read(byteArray);

            // Converting byte array into Base64 String
            content = byteToBase64(byteArray);

            inputFile.close();


        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the File " + ioe);
        }

        return content;

    }

    /**
     * Encodes a byte array into base64 string
     *
     * @param byteArray - byte array
     * @return String a {@link String}
     */
    private static String byteToBase64(byte[] byteArray)
    {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(byteArray);
    }

    protected static Request loadLongRequest(Webb w, String endpoint, File testFile) throws Exception {
        JSONObject requestAsJson = new JSONObject();
        requestAsJson.put("hello", "world");
        requestAsJson.put("file", Helpers.b64File(testFile));
        return w.post(endpoint).body(requestAsJson.toString());
    }
}
