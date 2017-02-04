package download;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetGoogleTopInfo {
	
	private static int count = 0;
	//the page you want get in website.
	private final static int mpageCount = 9;
	
	
	/*
	 * https://play.google.com/store/apps/collection/topselling_free?gl=vn
	 * 
	 * parse google play website page information
	 * 
	 */
	private static void parseHtml(Document doc, List<NewsItem> newsItems){
		
		try{
			//parse the class is "card no-rationale square-cover apps small"
			String clazzName = "no-rationale";
			Elements apkInfoClazz = doc.getElementsByClass(clazzName);
			
			//parse every class to find the attribute is "data-docid"
			String attriName = "data-docid";
			for(Element l: apkInfoClazz){
				Elements docid = l.getElementsByAttribute(attriName);
				
				//get the data
				String packageName = docid.get(0).attr(attriName);
				String appName = docid.get(0).getElementsByClass("title").get(0).attr("title");

				// add all the suitable data to list
				//every object is just a reference, so it need new every time
				NewsItem mItem = new  NewsItem();
				mItem.setPackageName(packageName);
				mItem.setTitle(appName);
				
				newsItems.add(mItem);
				
			}
			
			count++;
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
				
	}
	
	
	
	
	
	


	/*
	 * to get the google play top apk information for each region
	 */
	public static List<NewsItem> getAndShowGooglePlayTop(String region) {
		
		PrintStream original = System.out;
		System.out.println("The program start run, please wait...");
		long startTime = System.currentTimeMillis();
		
        
        System.out.println("The region you input is £º"+region+", wait for getting...");


//		// TODO Auto-generated method stub
//		try {
//			System.setOut(new PrintStream("d:/google_play_top_information.txt"));
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

        
        //TODO: GET GOOGLE PLAY top 1000
        
        List<NewsItem> newsItems = new ArrayList<NewsItem>();  

        Document doc;
	
		String googlePlayUrl = "https://play.google.com/store/apps/collection/topselling_free?gl="+region;
		/*
		   start:
			180
			num:
			60
			numChildren:
			0
			cctcss:
			square-cover
			cllayout:
			NORMAL
			ipf:
			1
			xhr:
			1
			token:
			wnSknpizMthScOh150i9uD4Qk5s:1461057853711
		 */
			
			String token = "skjdfkldjfkljsdk";
			Map<String, String> dataMap = new HashMap<String, String>();
			dataMap.put("start", "1");
			dataMap.put("num", "60");
			dataMap.put("numChildren", "0");
			dataMap.put("cctcss", "square-cover");
			dataMap.put("cllayout", "NORMAL");
			dataMap.put("ipf", "1");
			dataMap.put("xhr", "1");
			dataMap.put("token", token);
		
			

			
			int start = 0;
			for(int i=0; i<mpageCount; i++){
				
				String from = Integer.toString(start);
				dataMap.put("start", from);

				//for the next time
				start = start + 60;
				
				try {
					//connect url with token
					doc = Jsoup.connect(googlePlayUrl)
							.userAgent("Mozilla")
							.data(dataMap)
							.post();
					
					//parse doc 60 by 60
					parseHtml(doc, newsItems);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			//debug to show
			for(int i=0; i< newsItems.size();i++){
				String packageName = newsItems.get(i).getPackageName();
				String appName = newsItems.get(i).getTitle();
				System.out.println("---i="+i+"  packageName="+packageName+"  appName="+appName);
			}

			
		
			// TODO: here we should visit and phaser every web page one by one
			//https://apkpure.com/
	
			//get download info from https://apkpure.com/
//			parseChooseApkpurePackage(newsItems);
//			
//			//after get the apkpure page link, parse the page to get the download apk address
//			parseApkpureHtml(newsItems);
			
			
			
			
			
			
			
			// TODO: download from apkdl 
//			
//			//get the apkdl  download page address
//			parseApkDLHtml(newsItems);
//			
//			//parse apkdl page real download link
//			parseFinalLinkByapkdl(newsItems);
//			
			
						
		
		//TODO: get app version number and download count
		//https://play.google.com/store/apps/details?id=com.viu.phone
//		Document detailDoc;
//		try{
//			
//			//get download info from http://apk-dl.com/ 
//			for(int i=0; i< newsItems.size();i++){
//				
//			    String detialUrl = "https://play.google.com/store/apps/details?id="+newsItems.get(i).getPackageName();
////			    System.out.println("-----detialUrl="+detialUrl);
//			    //should set user agent to avoid status 403
//			    detailDoc = Jsoup.connect(detialUrl).userAgent("Mozilla").get();
//			    
//			    //parser by class and attribute
//			    Elements detailClazz = detailDoc.getElementsByClass("details-section-contents");
////			    System.out.println("-----textCenterClazz size="+textCenterClazz.size());
//			    boolean findDownloadFlag = false;
//			    boolean findVersionFlag = false;
//			    for(Element l: detailClazz){
//			    	
//			    	Elements itemprop = l.getElementsByAttribute("itemprop");
//					// parser one by one
//					for(Element k: itemprop){
//						String itempropValue = k.attr("itemprop");
////						System.out.println("---itempropValue="+itempropValue);
//						
//						//download number
//						if(null!= itempropValue && itempropValue.equals("numDownloads")){
//							String downloadNumber = k.text();
//							newsItems.get(i).setDownloadNumber(downloadNumber);
////							System.out.println("---k.download="+downloadNumber);
//							findDownloadFlag = true;
//						}
//						
//						//version number
//						if(null!= itempropValue && itempropValue.equals("softwareVersion")){
//							String versionNumber = k.text();
//							newsItems.get(i).setVersionNumber(versionNumber);
////							System.out.println("---k.version="+versionNumber);
//							findVersionFlag = true;
//						}
//						
//					}		    	
//			    				    						
//					// do not query anymore
//					if(findDownloadFlag && findVersionFlag){
//						break;
//					}
//
//				}
//			    
//			    
//			}
//				
//		}catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		
		
		
		
		
		
		long endTime = System.currentTimeMillis();
		long totalTimeSeconds = (endTime - startTime)/1000;
		System.out.println("The program spent "+totalTimeSeconds/60+" minutes "+totalTimeSeconds%60+" seconds");
		
       return newsItems;
	}

}
