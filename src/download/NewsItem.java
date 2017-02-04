package download;

public class NewsItem  
{  
    private int id;  
  
    /** 
     * title
     */  
    private String title;  
    /** 
     * link
     */  
    private String link;  
    /** 
     * date
     */  
    private String date;  
    /** 
     * image link
     */  
    private String imgLink;  
    /** 
     * content
     */  
    private String content;  
  
    /** 
     * type
     *  
     */  
    private int newsType; 
    
    /**
     * package name
     */
    
    private String packageName;  
    /**
     * download number
     */
    
    private String downloadNumber;  
    /**
     * version number
     */
    
    private String versionNumber;

    /**
     * https://apkpure.com
     * apkpurePageLink
     */

    private String apkpurePageLink;

    /**
     * http://apk-dl.com/
     * apkdlPageLink
     */

    private String apkdlPageLink;


    public int getNewsType()  
    {  
        return newsType;  
    }  
  
    public void setNewsType(int newsType)  
    {  
        this.newsType = newsType;  
    }  
  
    public String getTitle()  
    {  
        return title;  
    }  
  
    public void setTitle(String title)  
    {  
        this.title = title;  
    }  
  
    public String getLink()  
    {  
        return link;  
    }  
  
    public void setLink(String link)  
    {  
        this.link = link;  
    }  
  
    public int getId()  
    {  
        return id;  
    }  
  
    public void setId(int id)  
    {  
        this.id = id;  
    }  
  
    public String getDate()  
    {  
        return date;  
    }  
  
    public void setDate(String date)  
    {  
        this.date = date;  
    }  
  
    public String getImgLink()  
    {  
        return imgLink;  
    }  
  
    public void setImgLink(String imgLink)  
    {  
        this.imgLink = imgLink;  
    }  
  
    public String getContent()  
    {  
        return content;  
    }  
  
    public void setContent(String content)  
    {  
        this.content = content;  
    }  
    
    
    public String getPackageName()  
    {  
    	return packageName;  
    }
    
    public void setPackageName(String packageName)  
    {  
        this.packageName = packageName;  
    }  
    
    //add the two kind information
    public String getDownloadNumber()  
    {  
    	return downloadNumber;  
    }
    
    public void setDownloadNumber(String downloadNumber)  
    {  
        this.downloadNumber = downloadNumber;  
    }  
    
    public String getVersionNumber()  
    {  
    	return versionNumber;  
    }
    
    public void setVersionNumber(String versionNumber)  
    {  
        this.versionNumber = versionNumber;  
    }
    public String getapkpureLink()
    {
        return apkpurePageLink;
    }

    public void setapkpureLink(String apkpurePageLink)
    {
        this.apkpurePageLink = apkpurePageLink;
    }

    public String getapkdlLink()
    {
        return apkdlPageLink;
    }

    public void setapkdlLink(String apkdlPageLink)
    {
        this.apkdlPageLink = apkdlPageLink;
    }

    @Override  
    public String toString()  
    {  
        return "NewsItem [id=" + id + ", title=" + title + ", link=" + link + ", date=" + date + ", imgLink=" + imgLink  
                + ", content=" + content + ", newsType=" + newsType + "]";  
    }  
  
}  