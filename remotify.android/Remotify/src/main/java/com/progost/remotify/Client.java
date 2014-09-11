package com.progost.remotify;

import android.content.Context;
import android.util.Base64;

import com.progost.remotify.entity.Computer;
import com.progost.remotify.utils.API;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ostap on 27.06.13.
 */
public class Client {

    public Client() {
    }

    public String register(String email, String password) throws Exception {
        JSONObject user = new JSONObject();
        user.put("email",email);
        user.put("password",password);

        JSONObject jsonResp = post(API.USER, user);
        String status = jsonResp.getString("status");

        if (status.equals("OK")){
            return jsonResp.getJSONObject("data").getString("authKey");
        }
        return null;
    }

    public String login(String email, String password) throws Exception {
        Map<String,String> headers = new HashMap<String, String>();
        String credentials = email + ":" + password;
        String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        headers.put("Authorization", "Basic " + base64EncodedCredentials);


        JSONObject jsonResp = get(API.USER, headers);
        String status = jsonResp.getString("status");

        if (status.equals("OK")) {
            return jsonResp.getJSONObject("data").getString("authKey");
        }
        return null;
    }

    public boolean addComputer(String key,String connectionKey)  throws Exception{
        JSONObject request = new JSONObject();
        request.put("connectionKey", connectionKey);

        String url = String.format(API.ADD_COMPUTER,key);
        JSONObject jsonResp = post(url, request);
        String status = jsonResp.getString("status");

        if (status.equals("OK")) {
            return true;
        }
        return false;
    }

    public boolean deleteComputer(String key,String connectionKey)  throws Exception{
        String url = String.format(API.COMPUTER,connectionKey,key);
        JSONObject jsonResp = delete(url);
        String status = jsonResp.getString("status");

        if (status.equals("OK")) {
            return true;
        }
        return false;
    }

    public List<Computer> getComputers(String key) throws Exception {
        String url = String.format(API.GET_COMPUTERS,key);
        JSONObject jsonResp = get(url);

        String status = jsonResp.getString("status");

        List<Computer> computers = new ArrayList();
        if (!status.equals("OK"))
            return computers;

        JSONArray computersJson = jsonResp.getJSONArray("data");
        for (int i = 0; i < computersJson.length(); i++) {
            JSONObject compJson = computersJson.getJSONObject(i);
            Computer comp = Computer.fromJson(compJson);
            computers.add(comp);
        }
        return computers;
    }

    public Computer getComputer(String connectionKey,String key) throws Exception {
        String url = String.format(API.COMPUTER,connectionKey,key);
        JSONObject jsonResp = get(url);

        String status = jsonResp.getString("status");

        if (!status.equals("OK"))
            return null;
        JSONObject compJson = jsonResp.getJSONObject("data");
        Computer comp = Computer.fromJson(compJson);
        return comp;
    }

    public String keyboard(String key,String mac,String type,String k) throws Exception {
        String url = String.format(API.KEYBOARD,mac,type,k,key);
        JSONObject jsonResp = get(url);
        String status = jsonResp.getString("status");
        return status;
    }

    public String simpleCommand(String key,String mac,String command) throws Exception {
        String url = String.format(API.SIMPLE_COMMAND,mac,command,key);
        JSONObject jsonResp = get(url);
        String status = jsonResp.getString("status");
        return status;
    }

    public String changeVolume(String key,String mac,int value) throws Exception {
        String url = String.format(API.CHANGE_VOLUME,mac,value,key);
        JSONObject jsonResp = get(url);
        String status = jsonResp.getString("status");
        return status;
    }

    public String browser(String key,String mac,String browserUrl) throws Exception {
        String url = String.format(API.BROWSER,mac,key,browserUrl);
        JSONObject jsonResp = get(url);
        String status = jsonResp.getString("status");
        return status;
    }

    public JSONObject fileList(String key,String mac,String dirPath) throws Exception {
        String url;
        if (dirPath == null || dirPath.isEmpty())
            url = String.format(API.DISKLIST,mac,key);
        else
            url = String.format(API.FILELIST,mac,key,dirPath);
        JSONObject jsonResp = get(url);
        return jsonResp;
    }

    public JSONObject sendJson(String path,JSONObject req, String method) throws Exception {
        return sendJson(path,req,method,null);
    }

    public JSONObject sendJson(String path,JSONObject req, String method,Map<String,String> headers) throws Exception {
        String request = req == null ? null : req.toString();
        String resp = sendMessage(path,request,method,headers);
        JSONObject jsonResp = new JSONObject(resp);
        return jsonResp;
    }


    public byte[] screenShot(String key,String mac) throws Exception {
        return image(API.SCREENSHOT,key,mac);
    }

    public byte[] camera(String key,String mac) throws Exception {
        return image(API.CAMERA,key,mac);
    }

    public byte[] image(String api,String key,String mac) throws Exception {
        String url = String.format(api,mac,key);

        JSONObject resp = get(url);
        String status = resp.getString("status");

        if (!status.equals("OK"))
            return null;

        String imageBase64 = resp.getString("data");
        return Base64.decode(imageBase64,Base64.DEFAULT);
    }

    public JSONObject post(String path,JSONObject req) throws Exception {
        return sendJson(path, req, "POST",null);
    }

    public JSONObject get(String path) throws Exception {
        return get(path, null);
    }

    public JSONObject delete(String path) throws Exception {
        return delete(path, null);
    }

    public JSONObject delete(String path,Map<String,String> headers) throws Exception {
        return sendJson(path, null, "DELETE",headers);
    }

    public JSONObject get(String path,Map<String,String> headers) throws Exception {
        return sendJson(path, null, "GET",headers);
    }

    public String sendMessage(String path, String json,String method) throws Exception {
        return sendMessage(path, json, method);
    }

    public String sendMessage(String path, String json,String method,Map<String,String> headers) throws Exception {
            System.out.println("URL = " + path);
            System.out.println("METHOD = " + method);
            System.out.println("DATA = " + json);

            URL url = new URL(API.SERVER + path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);

            if(method.equals("POST")){
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json");
            }

            if (headers != null){
                for (String key : headers.keySet()) {
                    conn.setRequestProperty(key, headers.get(key));
                }
            }

            if (json != null){
                OutputStream os = conn.getOutputStream();
                os.write(json.getBytes());
                os.flush();
            }

            if (conn.getResponseCode() != 200) {
                if (conn.getResponseCode() == 400){
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            (conn.getInputStream()),"Windows-1251"));

                    String output;
                    System.out.println("Output from Server .... \n");
                    StringBuilder sb = new StringBuilder();
                    while ((output = br.readLine()) != null) {
                        sb.append(output);
                    }

                    return "{ \"status\":\""+sb.toString()+"\"}";
                }

                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream()),"Windows-1251"));

            String output;
            System.out.println("Output from Server .... \n");
            StringBuilder sb = new StringBuilder();
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            conn.disconnect();

            return sb.toString();
    }
}
