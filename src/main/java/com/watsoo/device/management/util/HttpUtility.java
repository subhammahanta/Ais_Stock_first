package com.watsoo.device.management.util;

import java.io.IOException;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import com.google.gson.JsonObject;
import com.squareup.okhttp.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class provides utility function for sending
 * HTTP POST, PATCH, DELETE request to a different endpoint
 */
@Component
@SuppressWarnings("unused")
public class HttpUtility {
	
	@Value("${traccar.base.url}")
	private String traccarBaseUrl;

	@Value("${traccar.user.name}")
	private String traccarUserName;

	@Value("${traccar.user.password}")
	private String traccarUserPassword;

    private static int responseCount(com.squareup.okhttp.Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }

    public String httpPostRequest(String url, String body) {

        com.squareup.okhttp.Response response = null;
        try {
            OkHttpClient client = new OkHttpClient();
            //set to avoid socket timeout connection error
            client.setConnectTimeout(0, TimeUnit.SECONDS);
            client.setReadTimeout(0, TimeUnit.SECONDS);
            client.setWriteTimeout(0, TimeUnit.SECONDS);

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody requestBody = RequestBody.create(mediaType, body.toString());
            Request request = new Request.Builder()
                    .url(encodeUrl(url))
                    .post(requestBody)
                    .addHeader("Accept", "application/json")
                    .addHeader("cache-control", "no-cache")
                    .build();

            response = client.newCall(request).execute();
            String result;
            if (!response.isSuccessful()) {
                return null;
            } else {
                result = response.body().string();
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //body close to avoid memory leak
            try {
                if (response != null) {
                    response.body().close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
  //encode url which browser accepts
    public String encodeUrl(String url) {
        //to encode dataname
        if (url.contains(" "))
            url = url.replace(" ", "%20");
        if (url.contains("'"))
            url = url.replace("'", "%27");

        return url;
    }
    

	public String httpPost(String subUrl, String userName, String password, JsonObject body) {

		com.squareup.okhttp.Response response = null;
		try {
			OkHttpClient client = new OkHttpClient();
			// set to avoid socket timeout connection error
			client.setConnectTimeout(0, TimeUnit.SECONDS);
			client.setReadTimeout(0, TimeUnit.SECONDS);
			client.setWriteTimeout(0, TimeUnit.SECONDS);
			client.setAuthenticator(new Authenticator() {
				@Override
				public Request authenticate(Proxy proxy, com.squareup.okhttp.Response response) {
					if (responseCount(response) >= 3) {
						return null; // If we've failed 3 times, give up. - in real life, never give up!!
					}
					String credential = Credentials.basic(traccarUserName, traccarUserPassword);
					return response.request().newBuilder().header("Authorization", credential).build();
				}

				@Override
				public Request authenticateProxy(Proxy proxy, com.squareup.okhttp.Response response) {
					return null;
				}

			});

			MediaType mediaType = MediaType.parse("application/json");
			RequestBody requestBody = RequestBody.create(mediaType, body.toString());
			Request request = new Request.Builder().url(encodeUrl(traccarBaseUrl + subUrl)).post(requestBody)
					.addHeader("Accept", "application/json").addHeader("cache-control", "no-cache").build();

			response = client.newCall(request).execute();
			System.out.println("response" + response.toString());
			String result;
			if (!response.isSuccessful()) {
				return null;
			} else {
				result = response.toString();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			// body close to avoid memory leak
			try {
				if (response != null) {
					response.body().close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}  

	public String httpGet(String url, String traccarUserName, String traccarUserPassword) {

		com.squareup.okhttp.Response response = null;
		try {
			OkHttpClient client = new OkHttpClient();
			// set to avoid socket timeout connection error
			client.setConnectTimeout(0, TimeUnit.SECONDS);
			client.setReadTimeout(0, TimeUnit.SECONDS);
			client.setWriteTimeout(0, TimeUnit.SECONDS);
			client.setAuthenticator(new Authenticator() {
				@Override
				public Request authenticate(Proxy proxy, com.squareup.okhttp.Response response) throws IOException {
					if (responseCount(response) >= 3) {
						return null; // If we've failed 3 times, give up. - in real life, never give up!!
					}
					String credential = Credentials.basic(traccarUserName, traccarUserPassword);
					return response.request().newBuilder().header("Authorization", credential).build();
				}

				@Override
				public Request authenticateProxy(Proxy proxy, com.squareup.okhttp.Response response)
						throws IOException {
					return null;
				}

			});

			Request request = new Request.Builder().url(encodeUrl(url)).get().addHeader("Accept", "application/json")
					.addHeader("cache-control", "no-cache").build();

			response = client.newCall(request).execute();
			String result = null;
			if (!response.isSuccessful()) {
				return null;
			} else {
				result = response.body().string();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			// body close to avoid memory leak
			try {
				if (response != null) {
					response.body().close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
