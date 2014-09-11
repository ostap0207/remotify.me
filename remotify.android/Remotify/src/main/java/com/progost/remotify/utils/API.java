package com.progost.remotify.utils;

/**
 * Created by Ostap on 9/13/13.
 */
public class API {
//    public final static String SERVER = "http://remotify.eu01.aws.af.cm/";
    public final static String SERVER = "http://api.remotify.me/";
//    public final static String SERVER = "http://107.170.154.103:8080/";


    public final static String GET_COMPUTERS = "user/computers/%s/";
    public final static String ADD_COMPUTER = "user/computer/%s/";
    public final static String COMPUTER = "user/computer/%s/%s/";


    public final static String USER = "user/";
    public final static String SIMPLE_COMMAND = "/user/computer/%s/simplecommand/%s/%s/";
    public final static String FILELIST = "/user/computer/%s/fs/%s/?dirPath=%s";
    public final static String DISKLIST = "/user/computer/%s/fs/%s/";
    public final static String SCREENSHOT = "/user/computer/%s/desktop/%s/";
    public final static String CAMERA = "/user/computer/%s/camera/%s/";
    public final static String BROWSER = "/user/computer/%s/browser/%s/?url=%s";
    public final static String CHANGE_VOLUME = "/user/computer/%s/volume/%s/%s";
    public final static String KEYBOARD = "/user/computer/%s/keyboard/%s/%s/%s";
}
