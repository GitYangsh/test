package com.example.retrofit.network;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.example.retrofit.util.Config;
import com.example.retrofit.util.LogUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @time 2017/2/14 15:51
 * Description:
 */

public class NetTool {

    public static String getServerUrl() {
        if (Config.DEBUG) {
            return "http://192.168.20.16:8080"; // 测试环境
        }

        return "http://market.aijiaoyan.com/api/v1/"; // 正式环境
    }

    public static String getSecrectKey() {
        if (Config.DEBUG) {
            return "www.520jy.com"; // 测试环境
        }

        return "vx8ea3uz"; // 正式环境
    }

    public static enum NET_TYPE {
        TYPE_NONE, TYPE_WIFI, TYPE_MOBILE
    };

    public static NET_TYPE getNetworkType(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            return NET_TYPE.TYPE_NONE;
        }
        int type = ni.getType();
        if (type == ConnectivityManager.TYPE_MOBILE) {
            return NET_TYPE.TYPE_MOBILE;
        } else if (type == ConnectivityManager.TYPE_WIFI) {
            return NET_TYPE.TYPE_WIFI;
        }
        return NET_TYPE.TYPE_NONE;
    }

    /**
     * 是否联网
     *
     * @param context
     * @return true为有联网反之没有联网
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo[] info = mgr.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } else {
            return false;
        }
        return false;
    }

    /**
     * 判断当前网络连接是否为cmwap
     *
     * @param context
     * @return
     */
    public static boolean isCmwap(Context activity) {
        ConnectivityManager manager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netWrokInfo = manager.getActiveNetworkInfo();

        if (netWrokInfo == null) {
            return false;
        }

        String typeName = netWrokInfo.getTypeName();
        String extraInfo = netWrokInfo.getExtraInfo();

        if ("MOBILE".equalsIgnoreCase(typeName) && ("cmwap".equalsIgnoreCase(extraInfo)
                || "3gwap".equalsIgnoreCase(extraInfo) || "uniwap".equalsIgnoreCase(extraInfo))) {
            return true;
        } else {

            return false;
        }
    }

    public static HttpURLConnection getCmwapConnect(String requestUrl) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection con;

        LogUtil.i("Tools", "use cmwap...");

        String host = url.getHost();
        int port = url.getPort();
        if (port == -1) {
            requestUrl = requestUrl.replaceAll(host, "10.0.0.172:80");
        } else {
            requestUrl = requestUrl.replaceAll(host + ":" + port, "10.0.0.172:80");
        }

        url = new URL(requestUrl);
        con = (HttpURLConnection) url.openConnection();

        String xOnlineHost = null;
        if (port == -1) {
            xOnlineHost = host;
        } else {
            xOnlineHost = host + ":" + port;
        }
        con.setRequestProperty("Host", "10.0.0.172");
        con.setRequestProperty("X-Online-Host", xOnlineHost);
        return con;
    }

    public static void setCommonHttpHeader(HttpURLConnection con) {
        con.setRequestProperty("Accept", "*/*");
        con.setRequestProperty("Accept-Language", "zh-CN, zh");
        con.setRequestProperty("Charset", "UTF-8,ISO-8859-1,US-ASCII,ISO-10646-UCS-2;q=0.6");
        con.setRequestProperty("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
        con.setRequestProperty("Connection", "Keep-Alive");
    }

    public static Map<String, String> getHttpResponseHeader(HttpURLConnection http) {
        Map<String, String> header = new LinkedHashMap<String, String>();
        for (int i = 0;; i++) {
            String mine = http.getHeaderField(i);
            if (mine == null)
                break;

            String str = http.getHeaderFieldKey(i);
            if (str != null) {
                header.put(str.toLowerCase(Locale.CHINA), mine);
            }
        }
        return header;
    }

    /**
     * 获取机器的IMEI
     *
     * @return
     */
    public static String getIMEI(Context context) {
        String IMEI = "";
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            IMEI = tm.getDeviceId();

            if (IMEI != null && (IMEI.length() < 15 || IMEI.equals("004999010640000") || IMEI.equals("000000000000000")
                    || IMEI.equals("null"))) {
                return null;
            }

            return IMEI;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getVersionName(Context cx) {
        String packName = cx.getPackageName();
        PackageInfo pinfo = null;
        try {
            pinfo = cx.getPackageManager().getPackageInfo(packName, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (pinfo != null)
            return pinfo.versionName;
        else
            return null;
    }

    public static int getVersionCode(Context cx) {
        String packName = cx.getPackageName();
        PackageInfo pinfo = null;
        try {
            pinfo = cx.getPackageManager().getPackageInfo(packName, 0);
        } catch (Exception e) {

            e.printStackTrace();
        }
        if (pinfo != null)
            return pinfo.versionCode;
        else
            return -1;
    }

    public static String getChannelNumber(Context c) {
        try {
            ApplicationInfo ai = c.getPackageManager().getApplicationInfo(c.getPackageName(),
                    PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            String channelNumber = bundle.getString("CHANNEL");
            return channelNumber;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "unknown";
    }

    /**
     * 获取渠道类型。XX线下，XS线上
     *
     * @return
     */
    public static String getChannelType(Context c) {
        try {
            ApplicationInfo ai = c.getPackageManager().getApplicationInfo(c.getPackageName(),
                    PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            String channelNumber = bundle.getString("CHANNEL_TYPE");
            return channelNumber;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "unknown";
    }

    /**
     * 获取Android本机MAC
     *
     * @return
     */
    public static String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * 获取APK签名验证字符
     *
     * @return
     */
    public static String getAppKeyMd5(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);

            Signature signature = pi.signatures[0];

            byte[] sign = signature.toByteArray();
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            java.security.cert.Certificate cert = cf.generateCertificate(new ByteArrayInputStream(sign));

            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] digest = md.digest(cert.getEncoded());
            String md5 = toHexString(digest);

            // Log.d(TAG, md5);
            // md5 = "7CCC9B791E78A8F36745C796A0225052";
            // int ret = NetLib.get(md5);
            // Log.d(TAG, "ret:" + ret);

            return md5;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void byte2hex(byte b, StringBuffer buf) {
        char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        int high = ((b & 0xf0) >> 4);
        int low = (b & 0x0f);
        buf.append(hexChars[high]);
        buf.append(hexChars[low]);
    }

    /**
     * Converts a byte array to hex string
     */
    private static String toHexString(byte[] block) {
        StringBuffer buf = new StringBuffer();
        int len = block.length;
        for (int i = 0; i < len; i++) {
            byte2hex(block[i], buf);
        }
        return buf.toString();
    }

    public static final String urlEncode(String text) {
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        String ret = "";
        try {
            ret = URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // ignore;
        }
        return ret;
    }

    public static final String urlDecode(String text) {
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        String ret = "";
        try {
            ret = URLDecoder.decode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // ignore;
        }
        return ret;
    }

    public static String getMagicNum(Context context) {
        String fileName = ".sysconfigflag";
        File filesFile = context.getFileStreamPath(fileName);
        File sdcardFile = new File(Environment.getExternalStorageDirectory(), fileName);
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String device_id = tm.getDeviceId();

        String magic = md5(android_id + device_id + "qwer", "utf-8");

        if (filesFile.exists()) {
            String s = readConfigFile(filesFile);
            if (TextUtils.isEmpty(s)) {
                writeConfigFile(filesFile, magic);
            } else {
                magic = s;
            }

            if (!sdcardFile.exists()) {
                writeConfigFile(sdcardFile, magic);
            }
        } else {
            if (sdcardFile.exists()) {
                String string = readConfigFile(sdcardFile);
                if (TextUtils.isEmpty(string)) {
                    writeConfigFile(sdcardFile, magic);
                } else {
                    magic = string;
                }
                writeConfigFile(filesFile, magic);
            } else {
                writeConfigFile(sdcardFile, magic);
                writeConfigFile(filesFile, magic);
            }
        }
        return magic;
    }

    private static void writeConfigFile(File file, String content) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(content);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String readConfigFile(File file) {
        String temp = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            temp = br.readLine();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    private static String md5(String str, String encodingType) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        try {
            md5.update(str.getBytes(encodingType));
        } catch (UnsupportedEncodingException e) {
            md5.update(str.getBytes());
        }

        byte[] md5Bytes = md5.digest();

        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
