package com.li.honedu.util;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
    private static final Logger LOG = LoggerFactory.getLogger(HttpClientUtil.class);
    private static final String RESPONSE_CONTENT = "通信失败";
    private static CloseableHttpClient client = HttpClients.createDefault();

    public HttpClientUtil() {
    }

    private static String res(HttpRequestBase method) {
        HttpClientContext context = HttpClientContext.create();
        CloseableHttpResponse response = null;
        String content = "通信失败";

        try {
            response = client.execute(method, context);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                Charset charset = ContentType.getOrDefault(entity).getCharset();
                content = EntityUtils.toString(entity, charset);
                EntityUtils.consume(entity);
            }
        } catch (ConnectTimeoutException var20) {
            LOG.error("请求连接超时，由于 " + var20.getLocalizedMessage());
            var20.printStackTrace();
        } catch (SocketTimeoutException var21) {
            LOG.error("请求通信超时，由于 " + var21.getLocalizedMessage());
            var21.printStackTrace();
        } catch (ClientProtocolException var22) {
            LOG.error("协议错误（比如构造HttpGet对象时传入协议不对(将'http'写成'htp')or响应内容不符合），由于 " + var22.getLocalizedMessage());
            var22.printStackTrace();
        } catch (IOException var23) {
            LOG.error("实体转换异常或者网络异常， 由于 " + var23.getLocalizedMessage());
            var23.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException var19) {
                LOG.error("响应关闭异常， 由于 " + var19.getLocalizedMessage());
            }

            if (method != null) {
                method.releaseConnection();
            }

        }

        return content;
    }

    public static String get(String url) {
        HttpGet get = new HttpGet(url);
        return res(get);
    }

    public static String get(String url, String cookie) {
        HttpGet get = new HttpGet(url);
        if (StringUtils.isNotBlank(cookie)) {
            get.addHeader("cookie", cookie);
        }

        return res(get);
    }

    public static String get(String url, Map<String, String> headers) {
        HttpGet get = new HttpGet(url);
        headers.forEach((key, value) -> {
            get.addHeader(key, value);
        });
        return res(get);
    }

    public static byte[] getAsByte(String url) {
        return get(url).getBytes();
    }

    public static String getHeaders(String url, String cookie, String headerName) {
        HttpGet get = new HttpGet(url);
        if (StringUtils.isNotBlank(cookie)) {
            get.addHeader("cookie", cookie);
        }

        res(get);
        Header[] headers = get.getHeaders(headerName);
        return headers == null ? null : headers.toString();
    }

    public static String getWithRealHeader(String url) {
        HttpGet get = new HttpGet(url);
        get.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;");
        get.addHeader("Accept-Language", "zh-cn");
        get.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");
        get.addHeader("Keep-Alive", "300");
        get.addHeader("Connection", "Keep-Alive");
        get.addHeader("Cache-Control", "no-cache");
        return res(get);
    }

    public static String post(String url, Map<String, String> param) {
        return post(url, param, (Map)null);
    }

    public static String post(String url, Map<String, String> params, Map<String, String> headers) {
        HttpPost post = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList();
        Iterator var5 = params.keySet().iterator();

        while(var5.hasNext()) {
            String name = (String)var5.next();
            nvps.add(new BasicNameValuePair(name, (String)params.get(name)));
        }

        try {
            post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        } catch (UnsupportedEncodingException var7) {
            throw new RuntimeException(var7);
        }

        if (headers != null) {
            headers.forEach((k, v) -> {
                post.addHeader(k, v);
            });
        }

        return res(post);
    }

    public static String postJson(String url, String data) {
        return postJson(url, data, (Map)null);
    }

    public static String postJson(String url, String data, Map<String, String> headers) {
        HttpPost post = new HttpPost(url);
        if (StringUtils.isNotBlank(data)) {
            post.addHeader("Content-Type", "application/json");
        }

        if (headers != null) {
            headers.forEach((k, v) -> {
                post.addHeader(k, v);
            });
        }

        post.setEntity(new StringEntity(data, ContentType.APPLICATION_JSON));
        return res(post);
    }

    public static String postXml(String url, String data) {
        return postXml(url, data, (Map)null);
    }

    public static String postXml(String url, String data, Map<String, String> headers) {
        HttpPost post = new HttpPost(url);
        if (StringUtils.isNotBlank(data)) {
            post.addHeader("Content-Type", "text/xml");
        }

        if (headers != null) {
            headers.forEach((k, v) -> {
                post.addHeader(k, v);
            });
        }

        post.setEntity(new StringEntity(data, ContentType.TEXT_XML));
        return res(post);
    }

    public static String postFile(String serverUrl, String fileParamName, File file, Map<String, String> params) throws IOException {
        return postFile(serverUrl, fileParamName, new FileInputStream(file), file.getName(), params);
    }

    public static String postFile(String serverUrl, String fileParamName, InputStream inputStream, String fileName, Map<String, String> params) throws IOException {
        HttpPost httpPost = new HttpPost(serverUrl);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addPart(fileParamName, new InputStreamBody(inputStream, fileName));
        Iterator var7 = params.entrySet().iterator();

        while(var7.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)var7.next();
            builder.addTextBody((String)entry.getKey(), (String)entry.getValue(), ContentType.TEXT_PLAIN.withCharset("UTF-8"));
        }

        HttpEntity httpEntity = builder.build();
        httpPost.setEntity(httpEntity);
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse response = httpClient.execute(httpPost);
        if (null != response && response.getStatusLine() != null) {
            if (response.getStatusLine().getStatusCode() != 200) {
                LOG.info("Post Request For Url[{}] is not ok. Response Status Code is {}", serverUrl, response.getStatusLine().getStatusCode());
                return null;
            } else {
                return EntityUtils.toString(response.getEntity());
            }
        } else {
            LOG.info("Post Request For Url[{}] is not ok. Response is null", serverUrl);
            return null;
        }
    }

    public static void main(String[] args) {
        Map param = Maps.newHashMap();
        param.put("hashCode", "0d0d215773561ea8aa8bdbd0a474b6a3");
        post("http://192.168.8.109:8790/zuul/upload-api/hasfile", param);
    }
}
