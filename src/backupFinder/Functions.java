/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backupFinder;

import burp.BurpExtender;
import burp.IHttpRequestResponse;
import burp.IRequestInfo;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author "Moein Fatehi moein.fatehi@gmail.com"
 */
public class Functions {
    
    public static void openWebpage(URL url) {
        try {
            Desktop.getDesktop().browse(url.toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static IRequestInfo getReqInfo(IHttpRequestResponse reqResp) {
        return BurpExtender.callbacks.getHelpers().analyzeRequest(reqResp);
    }
    
    public static URL getURL(IHttpRequestResponse reqResp) {
        return BurpExtender.callbacks.getHelpers().analyzeRequest(reqResp).getUrl();
    }
    
    public static List<String> ReadFile(String filename) throws IOException {
        List<String> dict=new ArrayList<String>();
        BufferedReader br = null;
        try {
            InputStream inputStream = Functions.class.getResourceAsStream("/files/"+filename);
            InputStreamReader streamReader = new InputStreamReader(inputStream, "UTF-8");
            br = new BufferedReader(new InputStreamReader(inputStream));
        } catch (Exception e) {
            BurpExtender.output.println(filename+" not Found");
            return null;
        }
        try {
            String line = br.readLine();
            while (line != null) {
                dict.add(line);
                line = br.readLine();
            }
        } finally {
            br.close();
        }
        return dict;
    }
    
    
    public static String getExtensionFromRequest(IHttpRequestResponse reqResp){
        if(extractExtension_From_Request(reqResp).length()!=0){
            return extractExtension_From_Request(reqResp);
        }
        else if(extractExtension_From_Response(new String (reqResp.getResponse())).length()!=0){
            return extractExtension_From_Response(new String (reqResp.getResponse()));
        }
        else{
            return null;
        }
    }

    private static String extractExtension_From_Request(IHttpRequestResponse reqResp) {
        String path = null;
        try {
            path = BurpExtender.callbacks.getHelpers().analyzeRequest(reqResp).getUrl().getPath();
        } catch (Exception e) {
            BurpExtender.output.println(e.getMessage());
        }
        
        //Request
        if (path.toLowerCase().contains(".php4")){
            return "php4";
        }
        else if (path.toLowerCase().contains(".php3")){
           return (path.substring(path.indexOf("php3")));
        }
        else if (path.toLowerCase().contains(".php")){
           return (path.substring(path.indexOf("php")));
        }
        else if (path.toLowerCase().contains(".aspx")){
           return (path.substring(path.indexOf("aspx")));
        }
        else if (path.toLowerCase().contains(".asp")){
           return (path.substring(path.indexOf("asp")));
        }
        else if (path.toLowerCase().contains(".jspx")){
           return (path.substring(path.indexOf("jspx")));
        }
        else if (path.toLowerCase().contains(".jsp")){
           return (path.substring(path.indexOf("jsp")));
        }
        else if (path.toLowerCase().contains(".py")){
           return (path.substring(path.indexOf("py")));
        }
        return "";
    }
    
    private static String extractExtension_From_Response(String response) {
        //Response
        if (response.toLowerCase().contains("apache")){
            return "php";
        }
        else if (response.toLowerCase().contains("php")){
            return "php";
        }
        else if (response.toLowerCase().contains("aspx")){
           return ("aspx");
        }
        else if (response.toLowerCase().contains("AspNet")){
           return ("asp");
        }
        else if (response.toLowerCase().contains("Microsoft-IIS")){
           return ("asp");
        }
        else if (response.toLowerCase().contains("asp")){
           return ("asp");
        }
        return "";
    }
    
    public static String getExtension(IHttpRequestResponse reqResp){
        String url=getURL(reqResp).toString();
        if(url.contains(".")){
            return url.substring(url.lastIndexOf(".")).replace(".","");
        }
        return "";
    }
    
    public static String getExtension(String file){
        if(file.contains(".")){
            return file.substring(file.lastIndexOf(".")).replace(".","");
        }
        return "";
    }
    
    public static String findRegex(String regex,String text){
        Pattern reg = Pattern.compile(regex);
        Matcher mat = reg.matcher(text);
        if(mat.find()) {
            return mat.group();
        }
        else{
            return null;
        }
    }
    
    public static List<String> getAllMatches(String regex, String text) {
        List<String> allMatches = new ArrayList<>();
        Matcher m = null;
        try {
           m = Pattern.compile(regex).matcher(text);
        } catch (Exception e) {
            BurpExtender.output.println(e.getMessage());
        }
        
        while (m.find()) {
            allMatches.add(m.group());
        }
        return allMatches;
    }
    
    public static String urlDecode(String url_encoded){
        return BurpExtender.callbacks.getHelpers().urlDecode(url_encoded);
    }
    
    public static String base64Decode(String base64_encoded){
        String ascii_regex="^[\\x00-\\x7F]*$";
        String base64_decoded=new String(BurpExtender.callbacks.getHelpers().base64Decode(base64_encoded));
        if(findRegex(ascii_regex, base64_decoded)!=null){
            return base64_decoded;
        }
        else{
            return "encrypted_Base64";
        }
    }
    
    public static String getParamType(byte code){
        switch ( code ) {
            case 0x00:
                return "URL";
            case 0x01:
                return "Body";
            case 0x02:
                return "Cookie";
            case 0x03:
                return "XML";
            case 0x04:
                return "XML_Attr";
            case 0x05:
                return "Multipart_Attr";
            case 0x06:
                return "JSON";
            case 0x07:
                return "AMF";
            case 0x20:
                return "Header";
            case 0x21:
                return "URL_REST";
            case 0x22:
                return "Name_URL";
            case 0x23:
                return "Name_Body";
            case 0x40:
                return "User_Provided";
            case 0x41:
                return "Extension_Provided";
            case 0x7f:
                return "Unknown";
        }
        return "Unknown";
    }
    
    
}
