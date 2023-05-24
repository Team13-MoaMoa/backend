package sku.moamoa.domain.user.factory;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

import java.io.IOException;
import java.net.HttpURLConnection;

public class SimpleClientHttpRequestWithBodyFactory extends SimpleClientHttpRequestFactory {
    @Override
    protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
        super.prepareConnection(connection, httpMethod);
        if ("DELETE".equals(httpMethod)) {
            connection.setDoOutput(true);
        }
    }
}
