package com.op.des.web.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class HttpUtils {

    static HttpClient httpClient = HttpClients.createDefault();

    public static boolean doGet(String phone, String smsCode) throws IOException, URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder("https://yzm.mb345.com/ws/BatchSend2.aspx");
        uriBuilder.addParameter("CorpID", "XALKY00617");
        uriBuilder.addParameter("Pwd", "abc!23DeF456");
        uriBuilder.addParameter("Mobile", phone);
        uriBuilder.addParameter("Content", smsCode);
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        String result = httpClient.execute(httpGet, httpResponse ->
                EntityUtils.toString(httpResponse.getEntity()));
        return Integer.valueOf(result) > 0;
    }
}
