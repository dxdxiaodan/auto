package com.auto.api.utils;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.net.URI;
import java.util.List;

/**
 * api请求工具
 *
 */
public class HttpClientUtil {
    public static final int OK = 200;
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * url请求是否成功
     * @param url
     * @return 响应文本
     * @throws Exception
     */
    public static Boolean getResponse(String url){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        Boolean result = true;
        try {
            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() != OK) {
                result = false;
            }
        }catch (Exception e){
            e.printStackTrace();
            result = false;
        }finally {
            try{
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            }catch (Exception e){}
        }
        return result;
    }

    /**
     * 通过url获得响应结果
     * @param url
     * @return 响应文本
     * @throws Exception
     */
    public static String getResult(String url,String charset) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        String responseContent = "";
        try {
            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == OK) {
                responseContent = EntityUtils.toString(response.getEntity(), ("".equals(charset))?DEFAULT_CHARSET:charset);
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
        return responseContent;
    }

    /**
     * 通过url获得响应结果,并生成指定文件
     * @param url
     * @param filePath
     * @return
     * @throws Exception
     */
    public String getResult(String url,String charset,String filePath) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        String content = "";
        try {
            // 执行请求
            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == OK) {
                //请求体内容
                content = EntityUtils.toString(response.getEntity(), ("".equals(charset))?DEFAULT_CHARSET:charset);
                //内容写入文件
                FileUtils.writeStringToFile(new File(filePath), content, ("".equals(charset))?DEFAULT_CHARSET:charset);
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
        return content;
    }

    /**
     * 通过url及请求参数获得响应结果
     * @param url 请求url
     * @param charset 编码集
     * @param params 待发送的参数集
     * @throws Exception
     */
    public String getResult(String url,String charset,List<NameValuePair> params) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URI uri = new URIBuilder(url).setParameters(params).build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = null;
        String content = "";
        try {
            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == OK) {
                content = EntityUtils.toString(response.getEntity(), ("".equals(charset))?DEFAULT_CHARSET:charset);
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
        return content;
    }

    /**
     * 执行无参数的POST请求,获得返回文本
     * @param url
     * @param charset 编码集
     * @throws Exception
     */
    public String doPOST(String url,String charset) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        String content = "";
        try {
            response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == OK) {
                content = EntityUtils.toString(response.getEntity(), ("".equals(charset))?DEFAULT_CHARSET:charset);
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
        return content;
    }

    /**
     * 执行无参数,带响应头的POST请求,获得返回文本
     * @param url
     * @param charset 编码集
     * @param headers 请求头
     * @throws Exception
     */
    public String doPOST(String url,String charset,Header[] headers) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeaders(headers);
        CloseableHttpResponse response = null;
        String content = "";
        try {
            response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == OK) {
                content = EntityUtils.toString(response.getEntity(), ("".equals(charset))?DEFAULT_CHARSET:charset);
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
        return content;
    }

    /**
     * 执行有参数,带响应头的POST请求,获得返回文本
     * @param url
     * @param charset 编码集
     * @param headers 请求头
     * @param params 请求参数
     * @throws Exception
     */
    public String doPOST(String url,String charset,Header[] headers,List<NameValuePair> params) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params);
        httpPost.setEntity(formEntity);
        httpPost.setHeaders(headers);
        CloseableHttpResponse response = null;
        String content = "";
        try {
            // 执行请求
            response = httpclient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == OK) {
                content = EntityUtils.toString(response.getEntity(), ("".equals(charset))?DEFAULT_CHARSET:charset);
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
        return content;
    }
}
