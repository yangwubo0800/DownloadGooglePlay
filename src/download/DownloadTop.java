package download;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Scanner;


public class DownloadTop {
	
	    	    
	    public static List<NewsItem> mGooglePlayItem;
	    public static String mRegion="";
	    
	    //Choose the directory
	    public static String mDirecotry="D";

	    public static void main(String[] args) {
	    	
	            
	            if(args.length < 2){
	            	System.out.println("Please enter  the Direcotry you want to save the apk files(C¡¢D¡¢E¡¢F...) and the region you want get the inforamtion!! ");		           
	            	return;
	            }
	            
	            //change the args get way.
	            mDirecotry = args[0];
	            mRegion = args[1];
	            System.out.println("mRegion="+mRegion);
	            System.out.println("mDirecotry="+mDirecotry);

	            
	            String dir = mDirecotry+":";
	            File testFile =  new File(dir);
	            if(testFile.exists() && testFile.isDirectory()){
	            	System.out.println("mDirecotry exists dir="+dir);
	            	
	            }else{
	            	System.out.println("mDirecotry not exists, please input a right dircorty!");
	            	return;
	            }
	            
	            
	            // TODO: get the google play top application information
	            mGooglePlayItem = GetGoogleTopInfo.getAndShowGooglePlayTop(mRegion);
	            
	            System.out.println("Please do not open the \"log.cvs\" file until the download is complete ");
	            
	            //TODO: use multi thread to download
	            DownloadThread dt = new DownloadThread();
	            Thread t1 = new Thread(dt,"task1");
	            Thread t2 = new Thread(dt,"task2");
	            Thread t3 = new Thread(dt,"task3");
	            Thread t4 = new Thread(dt,"task4");
	            Thread t5 = new Thread(dt,"task5");
	            
	            t1.start();
	            //do not start all thread at the same time
	            try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            t2.start();
	            try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            t3.start();	            
	            	          
	    }
		    

}
