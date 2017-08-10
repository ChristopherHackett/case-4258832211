import com.goebl.david.Request;
import com.goebl.david.Response;
import com.goebl.david.Webb;

import static org.junit.Assert.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by chackett on 10/08/2017.
 */
public class ReproductionTest {

    String baseUri = "https://wx9xntolee.execute-api.eu-west-1.amazonaws.com";
    String endpoint = "/prod";
    Request simpleRequest;
    Request slightlyLongerRequest;
    Request longRequest;

    private static Logger logger = LogManager.getLogger();

    @Before
    public void setup() throws Exception {
        Webb w = Webb.create();
        w.setBaseUri(baseUri);
        simpleRequest = w.post(endpoint).body("{\"hello\":\"world!\"}");
        slightlyLongerRequest = w.post(endpoint).body("{\"hello\":\"world!world!world!world!world!world!world!world!world!world!world!world!world!world!world!world!world!world!world!\"}");
        ClassLoader classLoader = getClass().getClassLoader();
        File testFile = new File(classLoader.getResource("test.bmp").getFile());
        longRequest = Helpers.loadLongRequest(w, endpoint, testFile);
    }

    @Test
    public void simpleBodyPostWithCompression()
    {
        Request request = simpleRequest;
        Response response = request
            .compress()
            .asJsonObject();
        Helpers.logResponse(response, logger);
        assertEquals(200, response.getStatusCode());
    }



    @Test
    public void simpleBodyPostWithoutCompression()
    {
        Request request = simpleRequest;
        Response response = request
                .asJsonObject();
        Helpers.logResponse(response, logger);
        assertEquals(200, response.getStatusCode());
    }


    @Test
    public void slightlyLongerBodyPostWithCompression()
    {
        Request request = slightlyLongerRequest;
        Response response = request
                .compress()
                .asJsonObject();
        Helpers.logResponse(response, logger);
        assertEquals(200, response.getStatusCode());
    }



    @Test
    public void slightlyLongerBodyPostWithoutCompression()
    {
        Request request = slightlyLongerRequest;
        Response response = request
                .asJsonObject();
        Helpers.logResponse(response, logger);
        assertEquals(200, response.getStatusCode());
    }



    @Test
    public void longBodyPostWithCompression()
    {
        Request request = longRequest;
        Response response = request
                .compress()
                .asJsonObject();
        Helpers.logResponse(response, logger);
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void longBodyPostWithoutCompression()
    {
        Request request = longRequest;
        Response response = request
                .asJsonObject();
        Helpers.logResponse(response, logger);
        assertEquals(200, response.getStatusCode());
    }
}
