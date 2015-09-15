package com.hua.http.security;

import java.util.Map;


/**
 *
 */
public class Encrypt2 {

    /**
     * 秘要索引
     */
    public static final int KEY_MM_COMMON = 0;
    public static final int KEY_MM_DUC = 1;
    public static final int KEY_MM_IMAGE = 2;
    public static final int KEY_MM_HAPPY_PREGNACE = 3;
    public static final int KEY_MQTT = 4;
    public static final int KEY_MQTT_LOG = 5;
    public static final int KEY_MQTT_LOG_TEST = 6;
    public static final int KEY_MM_WEB_LOGIN = 7;
    public static final int KEY_MM_HUODONG = 8;
    public static final int KEY_MM_AI_PASSPORT = 9;
    public static final int kEY_MM_FRIEND = 10;
    public static final int KEY_USERINFO = 11;
    public static final int KEY_MM_OTHER_COMMON = 12;
    public static final int KEY_MM_BUY = 13;
    public static final int KEY_MM_CITY = 14;

    //算法索引
    /**
     * 秘钥+[key+value]+秘钥
     */
    public static final int Algorithm1 = 0;

    /**
     * [value]+秘钥
     */
    public static final int Algorithm2 = 1;

    static String keyIdToString(int key) {
        String str = "";
        switch (key) {
            case KEY_MM_COMMON:
                str = "common";
                break;
            case KEY_MM_DUC:
                str = "duc";
                break;
            case KEY_MM_IMAGE:
                str = "image";
                break;
            case KEY_MM_HAPPY_PREGNACE:
                str = "happy_pregnanc";
                break;
            case KEY_MQTT:
                str = "mqtt";
                break;
            case KEY_MQTT_LOG:
                str = "mqtt_log";
                break;
            case KEY_MQTT_LOG_TEST:
                str = "mqtt_log_test";
                break;
            case KEY_MM_WEB_LOGIN:
                str = "web_login";
                break;
            case KEY_MM_HUODONG:
                str = "huodong";
                break;
            case KEY_MM_AI_PASSPORT:
                str = "ai_passport";
                break;
            case kEY_MM_FRIEND:
                str = "friend";
                break;
            case KEY_USERINFO:
                str = "userinfo";
                break;
            case KEY_MM_OTHER_COMMON:
                str = "other_common";
                break;
            case KEY_MM_BUY:
                str = "buy";
                break;
            case KEY_MM_CITY:
                str = "city";
                break;
            default:
                break;
        }
        return str;
    }

    /**
     * token生成算法
     * @param params
     * @param algorithm
     * @param key
     * @return
     */
    public static String genToken(Map<String, Object> params, int algorithm, int key) {
        String token = "";
        switch(algorithm) {
            case Algorithm1:
//                token = JCC.genToken(params, keyIdToString(key), JCC.Algorithm1);
                break;
            case Algorithm2:
//                token = JCC.genToken(params, keyIdToString(key), JCC.Algorithm2);
                break;
            default:
                break;
        }

        return token;
    }
}
