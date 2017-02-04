package download;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParsePageByApkDl {

    private final static String TAG = "ParsePageByApkDl";
	
	/*
	 * 
	 * 
	 * the first source is http://apk-dl.com/
	 * 
	 * 
	 *
	 */
	

	
	//http://apk-dl.com/
	/*
	 * input: package name
	 * output: apkdl link address
	 */
	public static String parseApkDLDownloadApkFile(String packageName){
		Document pageDoc;
		try{
			
			if(null == packageName){
				return null;
			}
			//get download info from http://apk-dl.com/ 

				
		    String pageUrl = "http://apk-dl.com/"+packageName;
//			    System.out.println("-----pageUrl="+pageUrl);
		    //should set user agent to avoid status 403
		    pageDoc = Jsoup.connect(pageUrl).userAgent("Mozilla").get();
		    
		    //parser by class and attribute
		    Elements textCenterClazz = pageDoc.getElementsByClass("text-center");
//			    System.out.println("-----textCenterClazz size="+textCenterClazz.size());
		    boolean findFlag = false;
		    for(Element l: textCenterClazz){
		    	//parse by tag <a>
		    	Elements aTag = l.getElementsByTag("a");
				// parser one by one
				for(Element k: aTag){
					String text = k.text();
//						System.out.println("---text="+text);
					//we find the button text "Download APK File" is special for us to find the link
					if(null != text && text.contains("Download APK File")){
						String href = k.attr("href");
//							System.out.println("---parseApkDLHtml href="+href);
						//compose the link address and save to list array
						String link = "http:"+href;
						//remove the space in the link address
						link = link.replace(" ", "");
						return link;

					}
					
				}		    				    	

			}
			    
				
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;
	}

	//http://dl3.apk-dl.com/root/apk/2016/6/15/com.facebook.orca_31886168.apk?f=Messenger_75.0.0.21.70_apk-dl.com.apk
	/*
	 * input: apkdl link address
	 * output: the final download link address
	 */
	public static String parseApkDLClickHere(String linkAddr){
		Document pageDoc;
		try{
			
			//get download info from http://apk-dl.com/ 
			if(null == linkAddr){
				return null;
			}
				

		    //should set user agent to avoid status 403
		    pageDoc = Jsoup.connect(linkAddr).userAgent("Mozilla").get();
		    
		    //parser by class and attribute
		    Elements textCenterClazz = pageDoc.getElementsByClass("contents");
//			    System.out.println("-----textCenterClazz size="+textCenterClazz.size());
		    boolean findFlag = false;
		    for(Element l: textCenterClazz){
		    	//parse by tag <a>
		    	Elements aTag = l.getElementsByTag("a");
				// parser one by one
				for(Element k: aTag){
					String text = k.text();
					if(null != text && text.contains("click here")){
						String href = k.attr("href");
						//replace the space to 20 ascii
						href = href.replace(" ", "%20");
						//for this case-->href=//dl3.apk-dl.com/store/download?id=com.viu.phonem maybe can not download.
						if(!href.contains("http:")){
							href= "http:"+href;
						}
						
						return href;
					}
					
				}					
		    				    						
			}
			    				
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;
	}

}
