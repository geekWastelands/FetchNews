package WeiShengTing;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LiShui {
    public static void main (String args[])throws Exception{
        int cnt=0;
        while(cnt<=24) {
            String url = "http://wsjsw.lishui.gov.cn/xwzx/bjdt/index";
            /*
             * 拼接出每一页的url
             */
            if (cnt == 0) {
                url += ".htm";
            } else {
                url += "_" + cnt + ".htm";
            }
            System.out.println("page = " + url);
            cnt++;
            Document doc1 = null;//获取每一页的全部内容
            for (int j = 0; j < 10; j++) {
                try {
                    doc1 = Jsoup
                            .connect(url)
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0")
                            .get();//get请求

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (doc1 != null) break;
            }
            //System.out.println(doc1);
            Elements Lists = null;//通过查找标签获取元素
            try {
                Lists = doc1.select("table.bk > tbody > tr > td > table:nth-child(2) tr");
            } catch (Exception e) {
                e.printStackTrace();
                // TODO: handle exception
            }
            if (Lists == null) continue;
            for (Element list : Lists) {//通过标签得到所需内容
                String titleurl = list.select("td").eq(1).select("a").attr("abs:href");
                System.out.println("titleurl = " + titleurl);//每一篇文章的url
                String title = list.select("td").eq(1).select("a").text();
                System.out.println("title = " + title);
                String date = list.select("td").eq(2).text();
                date = date.substring(date.indexOf("[") + 1, date.indexOf(']'));
                System.out.println("date = " + date);
                Document doc2 = null;//获取每一篇文章的详情
                for (int j = 0; j < 10; j++) {
                    try {
                        doc2 = Jsoup
                                .connect(titleurl)
                                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0")
                                .get();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (doc2 != null) break;
                }
                if (doc2 == null) continue;
                //获取文本和源码
                String txt = doc2.select("table.top8.bk > tbody > tr > td > table:nth-child(3) > tbody").text();
                String doc = doc2.select("table.top8.bk > tbody > tr > td > table:nth-child(3) > tbody").html();
                System.out.println("txt = " + txt);
                User user=new User();
                user.setTitle(title);
                user.setTitleurl(titleurl);
                user.setTxt(txt);
                user.setDate(date);
                user.setDoc(doc);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date_=new Date();
                user.setDownload_time(df.format(date_));
                mybatis Mybatis=new mybatis();
                Mybatis.insertUser(user);
            }
        }










    }
}
