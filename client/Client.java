package client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class Client {
	public static final String URL = "http://localhost:8080/servlet-test/TestServlet";
	private BasicCookieStore cookieStore = new BasicCookieStore();

	public static void main(String[] args) throws Exception {
		Client client1 = new Client();
		client1.post();
		client1.post();
		
		Client client2 = new Client();
		client2.post();
	}

	
	public void post() throws IOException, URISyntaxException {        
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        
        String gsonLoginInfo = new Gson().toJson(new LoginInfo("user", "pass"), LoginInfo.class);
        HttpUriRequest request = RequestBuilder.post()
                .setUri(new URI(URL))
                .setEntity(new StringEntity(gsonLoginInfo))
                .build();
        CloseableHttpResponse response = httpclient.execute(request);
        try {
            HttpEntity entity = response.getEntity();
            System.out.println("Entity:");
            System.out.println(EntityUtils.toString(entity));
            System.out.println("Status: " + response.getStatusLine());
            EntityUtils.consume(entity);

            System.out.println("Cookies:");
            List<Cookie> cookies = cookieStore.getCookies();
            if (cookies.isEmpty()) {
                System.out.println("Nothing");
            } else {
                for (Cookie cookie : cookies) {
                    System.out.println("- " + cookie.toString());
                }
            }
        } finally {
            response.close();
        }
	}
}
