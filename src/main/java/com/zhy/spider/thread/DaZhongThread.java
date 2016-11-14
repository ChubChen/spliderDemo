package com.zhy.spider.thread;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

import com.zhy.spider.bean.City;
import com.zhy.spider.test.DaZhongDianPing;

public class DaZhongThread extends Thread{

	private List<City> cityList;
	private Logger logger;
    private String threadName;
    private CountDownLatch countDownLatch;
    private volatile boolean shutdownReq = false;
    
    public DaZhongThread(List<City> cityList,Logger logger, String threadName,CountDownLatch countDownLatch ) {
		this.cityList = cityList;
		this.logger = logger;
		this.threadName = threadName;
		this.countDownLatch = countDownLatch;
	}
	
	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		try {
			logger.info(threadName+"开始执行抓取任务"+ cityList.size());
			 for (int i = 0; i < cityList.size() && !shutdownReq; ++i) {
				 try {
					 DaZhongDianPing.spider(cityList.get(i));
				} catch (Exception e) {
					continue;
				}
			 }
		} catch (Exception e) {
			logger.error("抓取城市出错了。", e);
		}finally {
            long useTime = System.currentTimeMillis() - startTime;
            logger.info(threadName + ": 抓取城市结束,用时: " + useTime);
            countDownLatch.countDown();
        }
	}
	
	 public final void shutdownThread() {
	        shutdownReq = true;
	        interrupt();
	 }

}
