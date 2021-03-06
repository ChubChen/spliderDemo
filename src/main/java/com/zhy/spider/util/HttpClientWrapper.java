package com.zhy.spider.util;


import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.CodingErrorAction;
import java.text.SimpleDateFormat;
import java.util.*;

public class HttpClientWrapper {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientWrapper.class);


    private static CloseableHttpClient httpClient;

    protected synchronized static CloseableHttpClient getHttpClient() {
        if (httpClient == null) {
            PoolingHttpClientConnectionManager conManager = new PoolingHttpClientConnectionManager();
            MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200).setMaxLineLength(2000).build();
            //ConnectionConfig
            ConnectionConfig connectionConfig = ConnectionConfig.custom()
                    .setMalformedInputAction(CodingErrorAction.IGNORE)
                    .setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
                    .setMessageConstraints(messageConstraints).build();

            conManager.setDefaultConnectionConfig(connectionConfig);
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setSocketTimeout(10000)
                    .setConnectTimeout(10000)
                    .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
                    .setConnectionRequestTimeout(10000)
                    .setStaleConnectionCheckEnabled(true)
                    .build();
            httpClient = HttpClients.custom().setConnectionManager(conManager).setDefaultRequestConfig(defaultRequestConfig).build();
        }
        return httpClient;
    }

    public static String postMany(String url, Map<String, String> keys) {
        HttpPost request = new HttpPost(url);
        List<NameValuePair> reqParams = new ArrayList<NameValuePair>();
        for (String key : keys.keySet()) {
            reqParams.add(new BasicNameValuePair(key, keys.get(key)));
        }
        request.setEntity(new UrlEncodedFormEntity(reqParams, Consts.UTF_8));
        HttpContext context = HttpClientContext.create();
        String res = "";
        try {
            CloseableHttpResponse response = getHttpClient().execute(request, context);
            res = EntityUtils.toString(response.getEntity());
            res = new String(res.getBytes("ISO-8859-1"), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
    
    public static void getCookies(String url){
    	HttpGet request = new HttpGet(url);
		try {
			CloseableHttpResponse response = getHttpClient().execute(request);
			Header[] headers = response.getAllHeaders();
			for (Header header : headers) {
				System.out.println(header.getName()+"____"+ header.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public static String getUrl(String url){
        HttpGet request = new HttpGet(url);
        //添加timeout属性
        RequestConfig.Builder rcBuilder = RequestConfig.copy(RequestConfig.DEFAULT);
        rcBuilder.setConnectTimeout(90000);
        rcBuilder.setSocketTimeout(90000);
        request.setConfig(rcBuilder.build());
        String res = "";
        try {
            CloseableHttpResponse response = getHttpClient().execute(request);
            res = EntityUtils.toString(response.getEntity());
            res = new String(res.getBytes("ISO-8859-1"), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }


    public static String getGbkUrl(String url){
        HttpGet request = new HttpGet(url);
        //添加timeout属性
        RequestConfig.Builder rcBuilder = RequestConfig.copy(RequestConfig.DEFAULT);
        rcBuilder.setConnectTimeout(90000);
        rcBuilder.setSocketTimeout(90000);
        request.setConfig(rcBuilder.build());
        String res = "";
        try {
            CloseableHttpResponse response = getHttpClient().execute(request);
            res = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 提交json类型
     * @param url
     * @param jsonContent
     * @return
     */
    public static String postJson(String url, String jsonContent){
        HttpClient httpClient = getHttpClient();
        HttpPost method = new HttpPost(url);
        StringEntity entity = null;//解决中文乱码问题
        String res = "";
        try {
            entity = new StringEntity(jsonContent, "UTF-8");
            entity.setContentType("application/json");
            method.setEntity(entity);
            HttpResponse result = httpClient.execute(method);
            res = EntityUtils.toString(result.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
	public static JSONObject getJsonUrl(String url) {
	
		HttpClient client = getHttpClient();
		HttpGet get = new HttpGet(url);
		JSONObject json = null;
		try {
			HttpResponse res = client.execute(get);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				json = new JSONObject(EntityUtils.toString(entity));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
			
		}
		return json;
	}

    public static String cmbcPost(String url, String key, String param) {
        HttpPost request = new HttpPost(url);
        List<NameValuePair> reqParams = new ArrayList<NameValuePair>();
        reqParams.add(new BasicNameValuePair(key, param));
        request.setEntity(new UrlEncodedFormEntity(reqParams, Consts.UTF_8));
        HttpContext context = HttpClientContext.create();
        String res = "";
        try {
            CloseableHttpResponse response = getHttpClient().execute(request, context);
            res = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
    //获取竞猜足球信息
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL

     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            url = url + "&_="+new Date().getTime();
            String urlNameString = url.trim();
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}