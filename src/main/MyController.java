package main;

import java.io.*;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyController {

    //网站地址
    private static final String URL = "http://www.win4000.com/meinvtag2_2.html";
    //匹配图片的正则表达式
    private static final String IMGURL_REG = "data-original=(.*?)[^\"]* ";
    // 获取src路径的正则
    private static final String IMGSRC_REG = "[a-zA-z]+://[^\\s]*";

    public static void main(String[] args){
        try {
            MyController cm = new MyController();
            //获取HTML内容
            String HTML = cm.getHtml(URL);
//            System.out.println(HTML);
            //获取图片标签
            List<String> imgUrl = cm.getImageUrl(HTML);
            //获取图片src地址
            List<String> imgSrc = cm.getImageSrc(imgUrl);
            //下载图片
            cm.Download(imgSrc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取HTML内容
    private String getHtml(String url)throws Exception{
        URL url1 = new URL(url);
        URLConnection connection = url1.openConnection();
        InputStream in = connection.getInputStream();
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(isr);

        String line;
        StringBuffer sb = new StringBuffer();
        while((line = br.readLine())!=null){
            sb.append(line,0,line.length());
            sb.append('\n');
        }
        br.close();
        isr.close();
        in.close();
        return sb.toString();
    }

    //获取ImageUrl地址
    private List<String> getImageUrl(String html){
        Matcher matcher= Pattern.compile(IMGURL_REG).matcher(html);
        List<String> listimgurl = new ArrayList<String>();
        while (matcher.find()){
            listimgurl.add(matcher.group());
        }
        return listimgurl;
    }

    //获取ImageSrc地址
    private List<String> getImageSrc(List<String> listimageurl){
        List<String> listImageSrc=new ArrayList<String>();
        for (String image:listimageurl){
            Matcher matcher=Pattern.compile(IMGSRC_REG).matcher(image);
            while (matcher.find()){
                listImageSrc.add(matcher.group().substring(0, matcher.group().length()-1));
            }
        }
        return listImageSrc;
    }

    //下载图片
    private void Download(List<String> listImgSrc) {
        try {
            for (String url : listImgSrc) {
                String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());

                URL uri = new URL(url);
                InputStream in = uri.openStream();

                FileOutputStream fo = new FileOutputStream(new File("src/image/"+imageName));
                byte[] buf = new byte[1024];
                int length = 0;
                while ((length = in.read(buf, 0, buf.length)) != -1) {
                    fo.write(buf, 0, length);
                }
                in.close();
                fo.close();
                System.out.println(uri + "下载完成");
            }
        } catch (Exception e) {
            System.out.println("下载失败");
        }
    }

}
