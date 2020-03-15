package utils;

import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtils {

    private static String url_remote = "http://47.111.122.217:8082/ScsyERP";
    private static String url_local = "http://127.0.0.1:8095/boss";
    private static String URL = url_remote;

	private static final int CONNECT_TIMEOUT = 2*1000;  
	private static final int READ_TIMEOUT = 10*1000;
		
	private static String encoderUTF(String in)
	{
		try {
			return URLEncoder.encode(in, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static String Map2param(Map<String, String> params) throws UnsupportedEncodingException
	{
		StringBuilder param = new StringBuilder();
        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
        while(iterator.hasNext())
        {
        	Entry<String, String> entry = iterator.next();
        	param.append(encoderUTF(entry.getKey())+"="+encoderUTF(entry.getValue())+"&");
        }
        if(param.length() >= 1)
            param.deleteCharAt(param.length()-1);
        return param.toString();
	}
	
	public static String Get(String url, Map<String, String> params) throws UnsupportedEncodingException {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = URL + url + "?" + Map2param(params);
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "No-Alive");
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
//            connection.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            try{
                Map<String, List<String>> map = connection.getHeaderFields();
                for (String key : map.keySet()) {
                    System.out.println(key + ": " + map.get(key));
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            int status = ((HttpURLConnection)connection).getResponseCode();
            if(status >= 400){
                System.err.println("Status : " + status);
                in = new BufferedReader(
                        new InputStreamReader(((HttpURLConnection) connection).getErrorStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                    result += "\r\n";
                }
            }else{
                in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                    result += "\r\n";
                }
            }
        } catch (Exception e) {
            System.out.println("Error in Get" + e);
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return URLDecoder.decode(result, "UTF-8");
    }
	
    public static String Post(String url, Map<String, String> params) throws UnsupportedEncodingException {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(URL + url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
//            conn.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(Map2param(params));
            out.flush();
            try{
                Map<String, List<String>> map = conn.getHeaderFields();
                for (String key : map.keySet()) {
                    System.out.println(key + ": " + map.get(key));
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            int status = ((HttpURLConnection)conn).getResponseCode();
            if(status >= 400){
                System.err.println("Status : " + status);
                in = new BufferedReader(
                        new InputStreamReader(((HttpURLConnection) conn).getErrorStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                    result += "\r\n";
                }
            }else{
                in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                    result += "\r\n";
                }
            }
        } catch (Exception e) {
            System.out.println("Error in Post"+e);
            e.printStackTrace();
        }
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return URLDecoder.decode(result, "UTF-8");
    }    
}
