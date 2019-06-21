# DownloadGooglePlay
the tool can parser google play website and then download application atuomatically

---------项目描述---------

此项目可以生产jar包，然后在JDK环境下直接运行此jar包可以抓取Google play 上面排名前1000的应用，当然运行的前提条件是要能访问谷歌。
抓取的信息如下：
app 包名
下载链接
应用名
版本号
下载量


---------实现方法---------

1、包名和应用名称，从https://play.google.com/store/apps/collection/topselling_free可以直接获取

2、根据包名，下载源网站：

 http://apk-dl.com/
 https://apkpure.com/

3、需要进入详情页面获取 版本号 和 下载量

4、需要获取更多页面信息




---------调试网页---------

http://www.wandoujia.com/apps

https://play.google.com/store/apps/collection/topselling_free


---------功能优化点---------

1、将信息源头获取之后，直接使用下载地址

2、使用双源下载，提高下载成功率

3、使用csv文件记录下载中的每条记录状态

4、开启多线程下载，加快下载进度
