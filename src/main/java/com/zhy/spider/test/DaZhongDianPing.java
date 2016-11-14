package com.zhy.spider.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
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
import com.zhy.spider.bean.ShopError;
import com.zhy.spider.dao.TblShopDao;
import com.zhy.spider.dao.TblShopErrorDao;
import com.zhy.spider.thread.DaZhongThread;
import com.zhy.spider.util.ApplicationContextUtil;
import com.zhy.spider.util.HttpClientWrapper;

public class DaZhongDianPing {

	private final static long threadTimeoutHours = 24;
	public static final String urlDomain = "http://www.dianping.com/";
	
	private final static int MAX_INSERT_BATCH = 1000;
	
	private static final Logger logger = Logger.getLogger(DaZhongDianPing.class);
	
	
	private static final String[] user_agent = {
			"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36",
			"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.154 Safari/537.36 LBBROWSER",
			"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0",
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36",
			"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0)",
			"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393",
			"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0",
			"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.8 Safari/537.36",
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36",
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/602.2.14 (KHTML, like Gecko) Version/10.0.1 Safari/602.2.14"
			};
	private static final String[] cookiesArray = {
			"_hc.v=\"443ce29a-5ca8-407e-bb07-0ab7d0eaf6fe.1471602796\"",
			"_hc.v=\"518fcc8f-9adc-4e40-8c52-798ca7010e89.1478416240\"",
			"_hc.v=\"a42035cc-1e0b-5e30-b287-112eb65ffd50.1478583910\"",
			"_hc.v=\"f22d5dff-2739-9c6e-f906-7b42c848d2c3.1478605145\"",
			"_hc.v=\"d2d7bde9-2303-41d8-b435-daacb2557446.1440990675\"",
			"_hc.v=\"29f2aebe-167a-677c-c97b-022a382dd59b.1478610307\"",
			"_hc.v=f4f71e52-f823-6ff6-2a63-4b05a3a0f713.1471601234"
	};
	private static final HashMap<String, City> map = new HashMap<String, City>();
	public static void main(String[] args) {
		int  THREADNUM = 40;
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:applicationContext/applicationContext.xml");
		//step 1 获取所有城市。一级目录 城市 根据城市列表多线程开始抓取
		//List<City> cityList = new ArrayList<City>();
		//cityList.addAll(getCity());
		chuliError();
//		List<City> cityListSub = new ArrayList<City>();
//		cityListSub = cityList.subList(1600, cityList.size());
//	    CountDownLatch countDownLatch = new CountDownLatch(THREADNUM);
//        List<DaZhongThread> daZhongThreadList = new ArrayList<DaZhongThread>();
//        
//        for (int threadIndex = THREADNUM; threadIndex >0 ; -- threadIndex ) {
//            try {
//                int countPerThread = (cityListSub.size() + threadIndex - 1)/threadIndex;
//                DaZhongThread daZhongThread = new DaZhongThread(
//                		cityListSub.subList(0,countPerThread),
//                		logger,
//                        "Thread" + threadIndex,
//                        countDownLatch);
//                cityListSub = cityListSub.subList(countPerThread, cityListSub.size());
//                daZhongThreadList.add(daZhongThread);
//                daZhongThread.start();
//            } catch (Exception e) {
//                logger.info("批量还款第" + THREADNUM + "批发生异常");
//            }
//        }
//        try {
//            countDownLatch.await(threadTimeoutHours, TimeUnit.HOURS);
//            logger.info("最终跑完后");
//        } catch (Exception e) {
//            logger.error("批量发生异常", e);
//        } finally {
//            for (DaZhongThread daZhongThread : daZhongThreadList) {
//                if (daZhongThread.isAlive()) {
//                    try {
//                    	daZhongThread.shutdownThread();
//                    	daZhongThread.join();
//                    } catch (InterruptedException e) {
//                        logger.error("中止线程异常", e);
//                    } catch (Exception e) {
//                        logger.error("结束线程异常", e);
//                    }
//                }
//            }
//        }
	}
	
	public static void chuliError(){
		getCity();
		for(int i = 0 ;i<9205; i++)
		{		
			ShopError shopError = selectError();
		if(0 == shopError.getType()){
				spider(map.get(shopError.getCityName()));
			}else if(2 == shopError.getType()){
				spiderShopByPageError(shopError.getUrl(), shopError.getCityName(), shopError.getFirstCateGory(), shopError.getSecondCateGory());
			}else if(3 == shopError.getType()){
				spiderShopByUrlError(shopError.getUrl(),shopError.getCityName(), shopError.getFirstCateGory(), shopError.getSecondCateGory());
			}else if(1 == shopError.getType()){
				spiderPageError(shopError.getUrl(),shopError.getCityName(), shopError.getFirstCateGory(), shopError.getSecondCateGory());
			}
			deleteError(shopError);
		}
	}
	
	public static ShopError selectError(){
		TblShopErrorDao tblShopErrorDao = ApplicationContextUtil.getBean("tblShopErrorDao");
		ShopError shopError = tblShopErrorDao.selectOne();
		
		return shopError;
	}
	public static void deleteError(ShopError shopError){
		TblShopErrorDao tblShopErrorDao = ApplicationContextUtil.getBean("tblShopErrorDao");
		tblShopErrorDao.deleteById(shopError.getId());
	}
	
	public static Connection getConnection(String url){
		Connection conn = Jsoup.connect(url); 
		Random random = new Random();
		conn.header("User-Agent", user_agent[random.nextInt(10)]);
		conn.header("Referer", "http://www.dianping.com/");
		conn.header("Upgrade-Insecure-Requests", "1");
		conn.header("Connection", "keep-alive");
		conn.header("Cache-Control", "0");
		conn.header("Accept-Language", "zh-CN,zh;q=0.8");
		conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		conn.header("Accept-Encoding", "gzip, deflate, sdch");
		conn.header("Cookie","70797558-e4ab-462d-a4af-857cb97ce845.1478757674");
		return conn;
	}
	
	private static List<City> getCity(){
		List<City> returnList = new ArrayList<City>();
		String jsonUrl = "http://www.dianping.com/ajax/json/index/citylist/getCitylist?do=allCitylist";
		String cityUrl = urlDomain+"citylist/citylist?citypage=1";
		JSONObject jsonObject = HttpClientWrapper.getJsonUrl(jsonUrl);
		Connection conn = getConnection(cityUrl);
		Document doc = null;
		try {
			doc = conn.timeout(100000).get();
			JSONObject htmlObject = jsonObject.getJSONObject("msg");
			JSONArray cityArray = htmlObject.getJSONArray("html");
			for (int i = 0; i < cityArray.length(); i++) {
				String cityString = cityArray.getString(i);
				//logger.info(cityString);
				String[] cityStrArray = cityString.split("\\|");
				City city = new City();
				city.setKey(cityStrArray[3]);
				city.setName(cityStrArray[0]);
				city.setSmallName(cityStrArray[1]);
				map.put(city.getSmallName(),city);
			}
			Elements body = doc.select(".glossary-list > li");
			for (Element elementLi : body) {
				Elements elemntA = elementLi.select("a");
				for (Element element : elemntA) {
					String key = element.attr("href").substring(1, element.attr("href").length());
					if(map.containsKey(key))
					returnList.add(map.get(key));
				}
			}
		} catch (Exception e) {
			logger.error("获取城市列表出错了。。。。。。。。",e);
		}
		return returnList;
	}
	
	public static void spider(City city){
		String cityPage = urlDomain + city.getSmallName();
		Connection conn = getConnection(urlDomain + city.getSmallName());
		Document doc = null;
		try {
			doc = conn.timeout(100000).get();
			Elements body = doc.select("#index-nav > li");
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
						Category secondCategory = new Category();
						secondCategory.setKey(pageKey);
						secondCategory.setName(aEle.text());
						//每个城市下的所有页面
						spiderPage(city, firstCategory, secondCategory);
					}
				}
			}
		} catch (IOException e) {
			String errMsg = e.getMessage();
			if(e instanceof HttpStatusException){
				if( 403 == ((HttpStatusException) e).getStatusCode()){
					errMsg = "发生403";
					ShopError shopError = new ShopError();
					shopError.setCityName(city.getSmallName());
					shopError.setErrmsg(errMsg);
					shopError.setType(0);
					shopError.setUrl(cityPage);
					errorRecord(shopError);
					try {
						//休眠1分钟
						Thread.sleep(300000);
					} catch (Exception e2) {
						logger.info("休眠1分钟");
					}
				}
			}
			logger.error("请求城市首页出错。。。。。。。。。。" + cityPage);
		}catch (Exception e) {
			logger.error("解析页面发生错误", e);
			ShopError shopError = new ShopError();
			shopError.setCityName(city.getSmallName());
			shopError.setErrmsg(e.getMessage());
			shopError.setType(90);
			shopError.setUrl(cityPage);
			errorRecord(shopError);
		}
	}
	public static void spiderPageError(String pageUrl, String cityName, String firstCategory, String secondCategory){		
		logger.info(pageUrl);
		//step 1 请求页面获取第一页下面的 总页数和 所有shop id
		Connection conn = getConnection(pageUrl);
		Document doc = null;
		try {
		   doc = conn.timeout(100000).get();
			//step 2获取总共多少页面
		   int page = getMaxPage(doc);
		   List<Shop> list = spiderShopByPage(page, pageUrl, cityName, firstCategory, secondCategory, false);
		   //step 3 根据list 写入数据库
		   if(list != null && list.size() > 0){
			   insertList(list);
		   }
		}catch(IOException e){
			String errMsg = e.getMessage();
			if(e instanceof HttpStatusException){
				if( 403 == ((HttpStatusException) e).getStatusCode()){
					errMsg = "发生403";
					ShopError shopError = new ShopError();
					shopError.setCityName(cityName);
					shopError.setFirstCateGory(firstCategory);
					shopError.setSecondCateGory(secondCategory);
					shopError.setErrmsg(errMsg);
					shopError.setType(1);
					shopError.setUrl(pageUrl);
					errorRecord(shopError);
					try {
						//休眠1分钟
						Thread.sleep(300000);
					} catch (Exception e2) {
						logger.info("休眠1分钟");
					}
				}
			}
			logger.error("请求二级目录页面出错。。。。。。。。。。",e);
			
		}catch(Exception e){
			logger.error("请求二级目录页面出错。。。。。。。。。。",e);
			ShopError shopError = new ShopError();
			shopError.setCityName(cityName);
			shopError.setFirstCateGory(firstCategory);
			shopError.setSecondCateGory(secondCategory);
			shopError.setErrmsg(e.getMessage());
			shopError.setType(91);
			shopError.setUrl(pageUrl);
			errorRecord(shopError);
		}
	}
	
	public static void spiderPage(City city, Category firstCategory, Category secondCategory){		
		String pageUrl  = urlDomain.concat("search/category/").concat(city.getKey()).concat("/").concat(firstCategory.getKey()).concat("/g").concat(secondCategory.getKey());
		logger.info(String.format("开始处理城市(%s),一级目录(%s), 二级目录(%s)下的所有店铺》》》》》》》》》》》》》 ", city.getName(), firstCategory.getName(), secondCategory.getName()));
		logger.info(pageUrl);
		//step 1 请求页面获取第一页下面的 总页数和 所有shop id
		Connection conn = getConnection(pageUrl);
		Document doc = null;
		try {
		   doc = conn.timeout(100000).get();
			//step 2获取总共多少页面
		   int page = getMaxPage(doc);
		   List<Shop> list = spiderShopByPage(page, pageUrl, city.getName(), firstCategory.getName(), secondCategory.getName(), false);
		   //step 3 根据list 写入数据库
		   if(list != null && list.size() > 0){
			   insertList(list);
		   }
		}catch(IOException e){
			String errMsg = e.getMessage();
			if(e instanceof HttpStatusException){
				if( 403 == ((HttpStatusException) e).getStatusCode()){
					errMsg = "发生403";
					ShopError shopError = new ShopError();
					shopError.setCityName(city.getName());
					shopError.setFirstCateGory(firstCategory.getName());
					shopError.setSecondCateGory(secondCategory.getName());
					shopError.setErrmsg(errMsg);
					shopError.setType(1);
					shopError.setUrl(pageUrl);
					errorRecord(shopError);
					try {
						//休眠1分钟
						Thread.sleep(300000);
					} catch (Exception e2) {
						logger.info("休眠1分钟");
					}
				}
			}
			logger.error("请求二级目录页面出错。。。。。。。。。。",e);
		}catch(Exception e){
			logger.error("请求二级目录页面出错。。。。。。。。。。",e);
			ShopError shopError = new ShopError();
			shopError.setCityName(city.getName());
			shopError.setFirstCateGory(firstCategory.getName());
			shopError.setSecondCateGory(secondCategory.getName());
			shopError.setErrmsg(e.getMessage());
			shopError.setType(91);
			shopError.setUrl(pageUrl);
			errorRecord(shopError);
		}
	}
	
	/*public static void spiderNotCateGory(City city){
		String pageUrl  = urlDomain.concat("search/category/").concat(city.getKey()).concat("/").concat("0");
		logger.info(String.format("开始处理城市(%s)下的所有店铺》》》》》》》》》》》》》 ", city.getName()));
		logger.info(pageUrl);
		//step 1 请求页面获取第一页下面的 总页数和 所有shop id
		Connection conn = getConnection(pageUrl);
		conn.cookie("_hc.v", "\"443ce29a-5ca8-407e-bb07-0ab7d0eaf6fe.1471602796\"");
		conn.cookie(" __utma", "1.1510136619.1478062924.1478138928.1478412658."+count);
		conn.header("User-Agent", user_agent[random.nextInt(8)]);
		Document doc = null;
		try {
		   doc = conn.timeout(100000).get();
			//step 2获取总共多少页面
		   int page = getMaxPage(doc);
		   List<Shop> list = spiderShopByPage(page, pageUrl, city.getName(), null, null, true);
		   //step 3 根据list 写入数据库
		   if(list != null && list.size() > 0){
			   insertList(list);
		   }
		}catch(IOException e){
			ShopError shopError = new ShopError();
			shopError.setCityName(city.getSmallName());
			shopError.setErrmsg(e.getCause().getMessage());
			shopError.setType(90);
			shopError.setUrl(cityPage);
			errorRecord(shopError, 1);
		}catch(Exception e){
			logger.error("请求二级目录页面出错。。。。。。。。。。",e);
			errorRecord(pageUrl,e.getMessage(), 91);
		}
	}*/
	private static void errorRecord(ShopError error){
		TblShopErrorDao tblShopErrorDao = ApplicationContextUtil.getBean("tblShopErrorDao");
		//if(error.getErrmsg().length() > 100 ){
			//error.getErrmsg().substring(0,50);
		//}
		tblShopErrorDao.insert(error);
	}
	
	private static void insertList (List<Shop> shopList ){
		int page = (int) (shopList.size()/MAX_INSERT_BATCH + 1);
		if(shopList.size() % MAX_INSERT_BATCH == 0){
			page --;
		}
		TblShopDao tblShopDao = ApplicationContextUtil.getBean("tblShopDao");

		for (int i = 0; i < page; i++) {
			int insertBegin = i * MAX_INSERT_BATCH;
			int insertEndIndex = insertBegin + MAX_INSERT_BATCH;
			if (insertEndIndex > shopList.size()) {
				insertEndIndex = shopList.size();
			}
			List<Shop> insertList = shopList.subList(insertBegin, insertEndIndex);
			tblShopDao.insertBatch(insertList);
		}
	}
	
	private static int getMaxPage(Document doc){
		int page = 0 ;
		Element pageEle = doc.select(".page").first();
		if(pageEle == null){
			return 1;
		}
		Elements pageA = pageEle.select("a");
		for (Element element : pageA) {
			String currentPage = element.attr("data-ga-page");
			if(StringUtils.isEmpty(currentPage)){
				continue;
			}
			int dataPage = Integer.parseInt(currentPage);
			if(dataPage > page){
				page = dataPage;
			}
		}
		return page;
	}
	
	public static List<Shop> spiderShopByPage(int page, String pageHomeUrl, String cityName, String firstCateGory, String secondCateGory,boolean flag){
		List<Shop> list  = new ArrayList<Shop>();
		for (int i = 1; i <= page; i++) {
			String pageUrl = ""; 
			if(flag){
				pageUrl = pageHomeUrl.concat("/p").concat(String.valueOf(i));
			}else{
			   pageUrl = pageHomeUrl.concat("p").concat(String.valueOf(i));
			}
			
			Connection conn = getConnection(pageUrl);
			Document doc = null;
			try {
				doc = conn.timeout(100000).get();
				//获取所有shop .
				Element content = doc.select(".content").first().select("#shop-all-list").first();
				Elements allShop = content.select("li");
				for (Element element : allShop) {
					Element aEle = element.select(".pic").first().select("a").first();
					String url = urlDomain.substring(0,urlDomain.length() - 1) + aEle.attr("href");
					long start = System.currentTimeMillis();
					Shop shop = spiderShopByUrl(url, cityName , secondCateGory, firstCateGory);
					logger.info("抓取一页耗时：" + (System.currentTimeMillis() - start));
					if(StringUtils.isNotEmpty(shop.getName()))
					list.add(shop);
				}
			}catch(IOException e){
				String errMsg = e.getMessage();
				if(e instanceof HttpStatusException){
					if( 403 == ((HttpStatusException) e).getStatusCode()){
						errMsg = "发生403";
						ShopError shopError = new ShopError();
						shopError.setCityName(cityName);
						shopError.setFirstCateGory(firstCateGory);
						shopError.setSecondCateGory(secondCateGory);
						shopError.setErrmsg(errMsg);
						shopError.setType(2);
						shopError.setUrl(pageUrl);
						errorRecord(shopError);
						try {
							//休眠1分钟
							Thread.sleep(300000);
						} catch (Exception e2) {
							logger.info("休眠1分钟");
						}
					}
				}
				logger.error("请求二级目录页面出错。。。。。。。。。。"+pageUrl,e );
			}catch(Exception e){
				logger.error("请求二级目录解析页面错误"+pageUrl,e);
				ShopError shopError = new ShopError();
				shopError.setCityName(cityName);
				shopError.setFirstCateGory(firstCateGory);
				shopError.setSecondCateGory(secondCateGory);
				shopError.setErrmsg(e.getMessage());
				shopError.setType(92);
				shopError.setUrl(pageUrl);
				errorRecord(shopError);
			}
		}
		return list;
	}
	public static List<Shop> spiderShopByPageError(String pageHomeUrl, String cityName, String firstCateGory, String secondCateGory){
		List<Shop> list  = new ArrayList<Shop>();
		Connection conn = getConnection(pageHomeUrl);
		Document doc = null;
		try {
			doc = conn.timeout(100000).get();
			//获取所有shop .
			Element content = doc.select(".content").first().select("#shop-all-list").first();
			Elements allShop = content.select("li");
			for (Element element : allShop) {
				Element aEle = element.select(".pic").first().select("a").first();
				String url = urlDomain.substring(0,urlDomain.length() - 1) + aEle.attr("href");
				long start = System.currentTimeMillis();
				Shop shop = spiderShopByUrl(url, cityName , secondCateGory, firstCateGory);
				logger.info("抓取一页耗时：" + (System.currentTimeMillis() - start));
				if(StringUtils.isNotEmpty(shop.getName())){
					TblShopDao tblShopDao = ApplicationContextUtil.getBean("tblShopDao");
					tblShopDao.insert(shop);
				}
			}
		}catch(IOException e){
			String errMsg = e.getMessage();
			if(e instanceof HttpStatusException){
				if( 403 == ((HttpStatusException) e).getStatusCode()){
					errMsg = "发生403";
					ShopError shopError = new ShopError();
					shopError.setCityName(cityName);
					shopError.setFirstCateGory(firstCateGory);
					shopError.setSecondCateGory(secondCateGory);
					shopError.setErrmsg(errMsg);
					shopError.setType(2);
					shopError.setUrl(pageHomeUrl);
					errorRecord(shopError);
					try {
						//休眠1分钟
						Thread.sleep(300000);
					} catch (Exception e2) {
						logger.info("休眠1分钟");
					}
				}
			}
			logger.error("请求二级目录页面出错。。。。。。。。。。"+pageHomeUrl,e );
		}catch(Exception e){
			logger.error("请求二级目录解析页面错误"+pageHomeUrl,e);
			ShopError shopError = new ShopError();
			shopError.setCityName(cityName);
			shopError.setFirstCateGory(firstCateGory);
			shopError.setSecondCateGory(secondCateGory);
			shopError.setErrmsg(e.getMessage());
			shopError.setType(92);
			shopError.setUrl(pageHomeUrl);
			errorRecord(shopError);
		}
		return list;
	}
	public static Shop spiderShopByUrlError(String url, String cityName, String firstCateGory, String secondCateGory){
		logger.info("开始抓取店铺信息"+ url);
		Shop shop = new Shop();
		Connection conn = getConnection(url);
		Document doc = null;
		try {
			doc = conn.timeout(100000).get();
			String shopName = doc.select(".shop-name").first().ownText();
			String address  = "";
			if(doc.select(".address").first().select(".item").first() != null){
				address = doc.select(".address").first().select(".item").first().ownText();
			}else{
				if(doc.select(".shop-addr").first() != null)
				address = doc.select(".shop-addr").first().select(".road-addr").first().ownText();
				if(doc.select(".shop-address").first() !=null)
				address = doc.select(".shop-address").first().ownText();
			}
			shop.setName(shopName);
			shop.setAddress(address);
			if(doc.select(".basic-info").first() != null)
			if(doc.select(".basic-info").first().select(".tel").first().select(".item").first() != null){
				String telPhone = doc.select(".basic-info").first().select(".tel").first().select(".item").first().ownText();
				shop.setTel(telPhone);
			}
			shop.setCityName(cityName);
			shop.setSecondCateGory(secondCateGory);
			shop.setFirstCateGory(firstCateGory);
			shop.setUrl(url);
			if(doc.select(".main").first()!= null)
			{
				Element otherEle = doc.select(".main").first().select(".other").first();
				String info_name = otherEle.select(".info").first().select(".info-name").first().text();
				if("别       名：".equals(StringUtils.trim(info_name))){
					shop.setOtherName(otherEle.select(".info").first().select(".item").first().text());
				}
				//详细页面url
				String editmemberUrl = shop.getUrl()+"/editmember";
				Connection connedit = getConnection(editmemberUrl);
				Document docedit = connedit.timeout(100000).get();
				String firstTime = docedit.select(".section").first().select(".block-inner > li").first().select("span").first().ownText();
				shop.setFirstTime(firstTime.replace("@", ""));
			}
			TblShopDao tblShopDao = ApplicationContextUtil.getBean("tblShopDao");
			tblShopDao.insert(shop);
		}catch(IOException e){
			String errMsg = e.getMessage();
			if(e instanceof HttpStatusException){
				if( 403 == ((HttpStatusException) e).getStatusCode()){
					errMsg = "发生403";
					ShopError shopError = new ShopError();
					shopError.setCityName(cityName);
					shopError.setFirstCateGory(firstCateGory);
					shopError.setSecondCateGory(secondCateGory);
					shopError.setErrmsg(errMsg);
					shopError.setType(3);
					shopError.setUrl(url);
					errorRecord(shopError);
					try {
						//休眠1分钟
						Thread.sleep(300000);
					} catch (Exception e2) {
						logger.info("休眠1分钟");
					}
				}
			}
			logger.error("请求店铺页面解析出错。。。。。。。。。。"+ url,e);
		}catch(Exception e){
			ShopError shopError = new ShopError();
			shopError.setCityName(cityName);
			shopError.setFirstCateGory(firstCateGory);
			shopError.setSecondCateGory(secondCateGory);
			shopError.setErrmsg(e.getMessage());
			shopError.setType(93);
			shopError.setUrl(url);
			errorRecord(shopError);
			logger.error("请求店铺页面解析出错。。。。。。。。。。"+ url,e);
		}
		return shop;
	}
	public static Shop spiderShopByUrl(String url, String cityName, String firstCateGory, String secondCateGory){
		logger.info("开始抓取店铺信息"+ url);
		Shop shop = new Shop();
		Connection conn = getConnection(url);
		Document doc = null;
		try {
			doc = conn.timeout(100000).get();
			String shopName = doc.select(".shop-name").first().ownText();
			String address  = "";
			if(doc.select(".address").first().select(".item").first() != null){
				address = doc.select(".address").first().select(".item").first().ownText();
			}else{
				if(doc.select(".shop-addr").first() != null)
				address = doc.select(".shop-addr").first().select(".road-addr").first().ownText();
				if(doc.select(".shop-address").first() !=null)
				address = doc.select(".shop-address").first().ownText();
			}
			shop.setName(shopName);
			shop.setAddress(address);
			if(doc.select(".basic-info").first() != null)
			if(doc.select(".basic-info").first().select(".tel").first().select(".item").first() != null){
				String telPhone = doc.select(".basic-info").first().select(".tel").first().select(".item").first().ownText();
				shop.setTel(telPhone);
			}
			shop.setCityName(cityName);
			shop.setSecondCateGory(secondCateGory);
			shop.setFirstCateGory(firstCateGory);
			shop.setUrl(url);
			if(doc.select(".main").first()!= null)
			{
				Element otherEle = doc.select(".main").first().select(".other").first();
				String info_name = otherEle.select(".info").first().select(".info-name").first().text();
				if("别       名：".equals(StringUtils.trim(info_name))){
					shop.setOtherName(otherEle.select(".info").first().select(".item").first().text());
				}
				//详细页面url
				String editmemberUrl = shop.getUrl()+"/editmember";
				Connection connedit = getConnection(editmemberUrl);
				Document docedit = connedit.timeout(100000).get();
				String firstTime = docedit.select(".section").first().select(".block-inner > li").first().select("span").first().ownText();
				shop.setFirstTime(firstTime.replace("@", ""));
			}
		}catch(IOException e){
			String errMsg = e.getMessage();
			if(e instanceof HttpStatusException){
				if( 403 == ((HttpStatusException) e).getStatusCode()){
					errMsg = "发生403";
					ShopError shopError = new ShopError();
					shopError.setCityName(cityName);
					shopError.setFirstCateGory(firstCateGory);
					shopError.setSecondCateGory(secondCateGory);
					shopError.setErrmsg(errMsg);
					shopError.setType(3);
					shopError.setUrl(url);
					errorRecord(shopError);
					try {
						//休眠1分钟
						Thread.sleep(300000);
					} catch (Exception e2) {
						logger.info("休眠1分钟");
					}
				}
			}
			logger.error("请求店铺页面解析出错。。。。。。。。。。"+ url,e);
		}catch(Exception e){
			ShopError shopError = new ShopError();
			shopError.setCityName(cityName);
			shopError.setFirstCateGory(firstCateGory);
			shopError.setSecondCateGory(secondCateGory);
			shopError.setErrmsg(e.getMessage());
			shopError.setType(93);
			shopError.setUrl(url);
			errorRecord(shopError);
			logger.error("请求店铺页面解析出错。。。。。。。。。。"+ url,e);
		}
		return shop;
	}
}
