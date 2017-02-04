package download;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.FileUtils;

public class DownloadThread implements Runnable{
	
	private static int mCount = 0;
    public static String downpath = DownloadTop.mDirecotry+":/apkTop/download/";
    public static String csvFilePath = DownloadTop.mDirecotry+":/apkTop/download/"+"log_"+DownloadTop.mRegion+".csv";
    public static boolean aBoolean=true;
    
    private static final String STATUS_SUCCEED="succeed";
    private static final String STATUS_FAIL="fail";
    private static final String STATUS_EXCEPTION="exception";
    
    //record the total time spent
    private static boolean mFirstFlag = true;
    private static long mStart ;
    private static long mEnd ;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(Thread.currentThread().getName()+" running...");
		if(mFirstFlag){
			mStart =  System.currentTimeMillis();
			mFirstFlag = false;
		}
		
		if(null == DownloadTop.mGooglePlayItem){
			return;
		}
		
		int index = 0;
		
		while(mCount < DownloadTop.mGooglePlayItem.size()){
			System.out.println(Thread.currentThread().getName()+"  run ready to get sync index...");
			synchronized(this){
				index = mCount++;
				System.out.println(Thread.currentThread().getName()+" index="+index);
			}
			
	    	String packageName = DownloadTop.mGooglePlayItem.get(index).getPackageName();
        	System.out.println("\n packageName="+packageName);
        	
        	
        	//first source
        	String downloadAddr = sourceApkDl(packageName);
        	//compose the region+id+packageName+.apk as download file name
        	//just let the download file as the package name 
        	//String downloadName = DownloadTop.mRegion+"_"+Integer.toString(index)+"_"+packageName+".apk";
        	String downloadName = packageName+".apk";
        	
        	//record the ID from 1 not 0
        	String apkId = Integer.toString(index+1);
        	
        	String firstSourcResult = download(apkId,downloadAddr, downloadName,packageName);
        	
        	System.out.println("firstSourcResult="+firstSourcResult);
        	
        	//TODO: if succeed, record it, if fail,use the next source
        	if(STATUS_SUCCEED.equals(firstSourcResult)){
        		WriteCSV(apkId, packageName, downloadAddr, STATUS_SUCCEED);    		
        	}
        	
        	if(STATUS_FAIL.equals(firstSourcResult) || STATUS_EXCEPTION.equals(firstSourcResult)){
            	//second source
            	String secondAddr = sourceApkPure(packageName);
            	String secondSourcResult = download(Integer.toString(index),secondAddr, downloadName,packageName);
            	System.out.println("secondSourcResult="+secondSourcResult);
            	
            	//TODO: the last source, everything record it.
            	WriteCSV(apkId, packageName, downloadAddr, secondSourcResult);    		

        	}
		}
		
		mEnd =  System.currentTimeMillis();
        System.out.println("Total time is "+(mEnd-mStart)/1000+" s");
					
		
	}
	
	
	
	   
    /*
     * the first source for download
     * 
     */

    public static String sourceApkDl(String packageName){
    	if(null == packageName){
    		return null;
    	}
    	
    	String apkDl = ParsePageByApkDl.parseApkDLDownloadApkFile(packageName);
    	String finalAddr = ParsePageByApkDl.parseApkDLClickHere(apkDl);
    	
    	return finalAddr;
    }
    
    /*
     * the second source for download
     * 
     */

    public static String sourceApkPure(String packageName){
    	if(null == packageName){
    		return null;
    	}
    	
    	String apkPure = ParsePageByApkpure.parseChooseApkpurePackage(packageName);
    	String detailAddr = ParsePageByApkpure.parseApkpureDownloadApk(apkPure);
    	String finalAddr = ParsePageByApkpure.parseApkpureClickHere(detailAddr);
    	
    	return finalAddr;
    }
    
    
    /*
     * url: download link
     * name: the download apk name in the PC
     * oldName: apk package name
     */
     public static String download(String id ,String url, String name,String oldName){
         URL e = null;
         File file;
         URLConnection uc;
         int mFileSize = -1;
         System.out.println("-----download id="+id+" url="+url+" name="+name+" oldName="+oldName);
         try {
             e = new URL(url);
             uc = e.openConnection();
             uc.addRequestProperty("User-Agent",
                     "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
             uc.connect();

             file = new File(downpath + name);
             
             //avoid download the same file again
             if(file.exists()){
            	 return STATUS_SUCCEED;
             }
            
             //get the download file size by 'content-length' field
             mFileSize = uc.getContentLength();
             
             InputStream is = uc.getInputStream();

             System.out.println("download begin to copy stream....");
             long start = System.currentTimeMillis();
             //copy the stream to local storage
             FileUtils.copyInputStreamToFile(is,file);
             long end = System.currentTimeMillis();
   
             System.out.println("download copy stream over!! spent time: "+(end-start)/1000+" s");
             //record the result
             if(mFileSize != -1){
            	 //succeed , do nothing
             }else{
            	 //delete it if failed
	             if(file.isFile() && file.exists()) {
	                 file.delete();
	             } 

	             return STATUS_FAIL;
             }
             System.out.println(name+"  "+mFileSize);
         }  catch (Exception var) {
        	 
        	 System.out.println(" download Exception");
        	 var.printStackTrace();
        	 
             file = new File(downpath +name);
             if(file.isFile() && file.exists()) {
                 file.delete();
             }             
             
             return STATUS_EXCEPTION; 
         }
         
        return STATUS_SUCCEED;
     }

     
     /*
      * record the download status to csv file
      */
     public static void WriteCSV(String id,String oldName,String url ,String log){

         try {
        	 
             
             File csv = new File(csvFilePath);
             BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); // 附加
             // 添加新的数据行
             if (aBoolean){
                 bw.write("ID"+ "," + "Name" + "," + "Download URL" +"," +"Results"+","+"Size(kB)");
                 bw.newLine();
                 aBoolean=false;
             }
             //TODO: remove the comma in the url
             if(null != url){
            	 url = url.replaceAll(",", "");
             }
             System.out.println("WriteCSV url="+url);
                       
             bw.write(id + "," + oldName + "," + url +"," +log);
             bw.newLine();
             bw.close();

         } catch (FileNotFoundException e) {
             // File对象的创建过程中的异常捕获
             e.printStackTrace();
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

}
