package com.example.messages.network;
import android.util.Base64;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;


public class TwilioUtil {

    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";
    public static final String from = "+12015001564";
    private final TwilioApi api;

    public TwilioUtil() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder defaultHttpClient= new OkHttpClient.Builder();
        defaultHttpClient.addInterceptor(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.twilio.com/2010-04-01/")
                .client(defaultHttpClient.build())
                .build();
        api = retrofit.create(TwilioApi.class);
    }

    public void sendMessage(String to, String body, Callback callback) {


        String base64EncodedCredentials = "Basic " + Base64.encodeToString(
                (ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes(), Base64.NO_WRAP
        );

        Map<String, String> smsData = new HashMap<>();
        smsData.put("From", from);
        smsData.put("To", to);
        smsData.put("Body", body);



        api.sendMessage(ACCOUNT_SID, base64EncodedCredentials, smsData).enqueue(callback);
    }
}
