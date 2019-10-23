# Internet-Insect
Java 爬取网站图片实现批量下载

- 效果图

![效果图](https://github.com/wss981086665/img-forder/blob/master/Internet-Insect/result.jpg)

若要下载指定网页的图片，需要自行将下面几个配置根据实际网站进行更改
``` java
      网站地址
      private static final String URL = httpwww.win4000.commeinvtag2_2.html;
      匹配图片的正则表达式
      private static final String IMGURL_REG = data-original=(.)[^] ;
       获取src路径的正则
      private static final String IMGSRC_REG = [a-zA-z]+[^s];
```
