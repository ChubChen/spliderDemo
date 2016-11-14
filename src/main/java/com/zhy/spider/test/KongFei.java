package com.zhy.spider.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zhy.spider.bean.Category;
import com.zhy.spider.bean.City;
import com.zhy.spider.bean.Shop;
import com.zhy.spider.dao.TblShopDao;
import com.zhy.spider.util.ApplicationContextUtil;
import com.zhy.spider.util.HttpClientWrapper;

public class KongFei {

	private static Logger logger = Logger.getLogger(KongFei.class);
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:applicationContext/applicationContext.xml");
		/*TblShopDao tblShop = ApplicationContextUtil.getBean("tblShopDao");
		Shop shop = new Shop();
		shop.setId(1231L);
		shop.setName("123333");
		shop.setAddress("sdfffff");
		shop.setKeyStr("123");
		Shop shop1 = new Shop();
		shop1.setId(122L);
		shop1.setName("123333");
		shop1.setKeyStr("123");
		shop1.setAddress("sdfffff");
		logger.info("1233333333333333333333333");
		logger.info("1233333333333333333333333");
		logger.info("1233333333333333333333333");
		logger.info("1233333333333333333333333");
		logger.info("1233333333333333333333333");*/

		/*List<Shop> list = new ArrayList<Shop>();
		list.add(shop1);
		list.add(shop);
		tblShop.insertBatch(list);
		System.out.println(tblShop);
		City city = new City();
		city.setKey("23");
		city.setName("海口");
		city.setSmallName("haikou");
		String urlDomain = "http://www.dianping.com/";
		//Header[] header = HttpClientWrapper.getCookies(urlDomain + city.getSmallName());
		Connection conn = Jsoup.connect(urlDomain + city.getSmallName());
		//conn.cookie(" __utma", "1.1510136619.1478062924.1478138928.1417658.5");
		conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.154 Safari/537.36 LBBROWSER");
		//conn.header("Host", "www.dianping.com");
		//conn.header("Upgrade-Insecure-Requests", "1");
		Document doc = null;
		try {
			doc = conn.timeout(100000).get();
			Elements body = doc.select("#index-nav > li");
			if(body.size() >0){
				for (Element element : body) {
					Category firstCategory = new Category();
					String key = element.attr("data-key");
					if(StringUtils.isEmpty(key)){
						continue;
					}
					firstCategory.setKey(key);
					firstCategory.setName(element.select(".name").first().text());
					Elements a = element.select(".secondary-category").first().select("a");
					for (Element aEle : a) {
						String pageKey = aEle.attr("data-key");
						if(StringUtils.isEmpty(pageKey)){
							continue;
						}else{
							//每个城市下的所有页面
							Category secondCategory = new Category();
							secondCategory.setKey(pageKey);
							secondCategory.setName(aEle.text());
							DaZhongDianPing.spiderPage(city, firstCategory, secondCategory);
						}
					}
				}
		  }else{
			  //DaZhongDianPing.spiderNotCateGory(city);
		  }
		}catch(Exception e){
			if(e instanceof HttpStatusException){
				((HttpStatusException) e).getStatusCode();
			}
			e.printStackTrace();
		}
		//DaZhongDianPing.spiderShopByUrl("http://www.dianping.com/shop/18244671");
*/
		String rest = HttpClientWrapper.getUrl("http://api.xicidaili.com/free2016.txt");
		String[] proxyStr = rest.split("\\r\n");
		//System.out.println(proxyStr[0].split(":")[0]);
		int port = Integer.parseInt(proxyStr[0].split(":")[1]);
		Connection conn = Jsoup.connect("http://www.dianping.com/shop/18244671").proxy(proxyStr[0].split(":")[0], port);
		Document doc = null;
		try {
			doc = conn.timeout(100000).get();
		} catch (Exception e) {
			e.printStackTrace();
			}
		//System.out.println());
	}
	
}
