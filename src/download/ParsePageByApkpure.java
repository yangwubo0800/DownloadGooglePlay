package download;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParsePageByApkpure {
	
	    private final static String TAG = "ParsePageByApkpure";


	    //TODO: https://apkpure.com/search?q=com.tencent.mm
	    /*
	     * function: get the package web address
	     * input: packageName
	     * return value: the package website address in apkPure
	     */
	    public static String parseChooseApkpurePackage(String packageName){
	    	
	    	if(null == packageName){
	    		return "error";
	    	}
	    	
	    	System.out.println("parseChooseApkpurePackage packageName="+packageName);
	        final String googlePlayUrl = "https://apkpure.com/search?q=";

	        try {


	     
                String pageUrl = googlePlayUrl+ packageName;
                Document pageDoc = Jsoup.connect(pageUrl)
                        .userAgent("Mozilla")
                        .get();



                //parser by class and attribute
                Elements textCenterClazz = pageDoc.getElementsByClass("search-dl");

                for(Element l: textCenterClazz){
                    //parse by tag <a>
                    Elements aTag = l.getElementsByTag("a");
                    String href ="";

                    if(aTag.size() >0){
                        href = aTag.get(0).attr("href");
                        String pageLink = "https://apkpure.com"+href;
                        return pageLink;

                    }
                }
        



	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        
	        return "";
	    }

	    

	    // TODO: https://apkpure.com/facebook-lite/com.facebook.lite
	    // /facebook-lite/com.facebook.lite/download?from=details
	    /*
	     * function: get the detail address for the first one
	     * input: package web address
	     * return value: the detail address
	     */
	    public static String parseApkpureDownloadApk(String apkPureLink){

	    	if(null == apkPureLink){
	    		
	    		return "error";
	    	}
	    	
//	    	System.out.println("parseApkpureHtml apkPureLink="+apkPureLink);
	    	
	        try {
	               String apkpureUrl="https://apkpure.com";
	            	            

	                Document pageDoc = Jsoup.connect(apkPureLink)
	                        .userAgent("Mozilla")
	                        .get();

	                //parser by class and attribute
	                Elements textCenterClazz = pageDoc.getElementsByClass("ny-down");

	                boolean findFlag = false;
	                for(Element l: textCenterClazz){
	                    //parse by tag <a>
	                    Elements aTag = l.getElementsByTag("a");
	                    // parser one by one
	                    for(Element k: aTag){
	                        String text = k.text();

	                        //we find the button text "Download APK File" is special for us to find the link
	                        if(null != text && text.contains("Download APK")){
	                            String href = k.attr("href");
//	                            System.out.println("---parseApkpureHtml href="+href);
	                            // TODO: add the suffix website
	                            if(!href.contains("https://apkpure.com")){
	                            	href = "https://apkpure.com"+href;
	                            }
	                            return href;

	                        }

	                    }

	                    // do not query anymore
	                    if(findFlag){
	                        break;
	                    }

	                }

	           

	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        
	        return "";
	    }
	    
	    
	    /*
	     * function: get the download url from the address like://https://apkpure.com/facebook-lite/com.facebook.lite/download?from=details
	     * input: detail  address
	     * return value: the final download address
	     */	    
	    
	    // TODO: view-source:https://apkpure.com/facebook/com.facebook.katana/download?from=details
	    //https://download.apkpure.com/c/APK/1467/01e3b2fdb9564128?_fn=RmFjZWJvb2tfdjgxLjAuMC4yMi43MF9hcGtwdXJlLmNvbS5hcGs%3D&k=421435616798e2bd14fa29fe12f86945576505b6&_p=Y29tLmZhY2Vib29rLmthdGFuYQ%3D%3D&c=1%7CSOCIAL">click here
	    public static String parseApkpureClickHere(String apkPureLink){

	    	if(null == apkPureLink){
	    		
	    		return "error";
	    	}
	    	
//	    	System.out.println("parseApkpureClickHere apkPureLink="+apkPureLink);
	    	
	        try {
	            	           
	                Document pageDoc = Jsoup.connect(apkPureLink)
	                        .userAgent("Mozilla")
	                        .get();

	                //parser by class and attribute
	                Elements textCenterClazz = pageDoc.getElementsByClass("fast-download-box");

	                for(Element l: textCenterClazz){
	                    //parse by tag <a>
	                    Elements aTag = l.getElementsByTag("a");
	                    // parser one by one
	                    for(Element k: aTag){
	                        String text = k.text();

	                        //we find the button text "Download APK File" is special for us to find the link
	                        if(null != text && text.contains("click here")){
	                            String href = k.attr("href");
	                            
	                            System.out.println("---parseApkpureClickHere href="+href);
	                            return href;

	                        }

	                    }

	                }

	          
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        
	        return "";
	    }





}
