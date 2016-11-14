package com.zhy.spider.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Splider {

	
	public static void main(String[] args) {
		File file  = new File("D:\\test.xls");
		
		String param = "有利网";
		Connection conn = Jsoup.connect("http://www.baidu.com/s");
		conn.data("wd", param);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("BAIDUID", "BB5CF2E709B9599239565AF7BF86E137:FG=1");
		map.put("BIDUPSID", "BB5CF2E709B9599239565AF7BF86E137");
		map.put("PSTM", "1438740832");
		map.put("BDUSS", "VJ4U25USmNBN1gySWVBcHFVVWM5NmNNV29Ga3FDZUpDZVVsczdIMExWdGdHQmRXQVFBQUFBJCQAAAAAAAAAAAEAAACBgHMQY2hlbnBlbmc0NTYxMTAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGCL71Vgi-9Vc");
		map.put("BDRCVFR[feWj1Vr5u3D]", "mk3SLVN4HKm-9Vc");
		map.put("BD_CK_SAM", "1");
		map.put("H_PS_PSSID", "17746_1464_17712_17949_18205_17001_15423_11965_18453");
		map.put("BD_UPN", "12314753");
		map.put("H_PS_645EC", "f8415%2BU5tqh%2BB4IacVsNXVMJOQq4voy3LLK9KTW0OHRsPKNoqVn392dYWGw");
		conn.cookies(map);
		Document doc = null;
		try {
			doc = conn.timeout(100000).get();
			Element body = doc.select("#con-ar").first().child(0);
			Element content = body.select("div.cr-content").first();
			Element xiangguandiv = content.select("div.opr-recommends-merge-content").first();
			Elements panlDiv = xiangguandiv.select("div.opr-recommends-merge-panel");
			Elements titleDiv = xiangguandiv.select("div.cr-title");
			for (int i = 0; i < panlDiv.size(); i++) {
				String title = titleDiv.get(i).select("span").attr("title");
				Elements items = panlDiv.get(i).select(".opr-recommends-merge-item");
				Elements textArea = panlDiv.get(i).select(".opr-recommends-merge-hide");
				/*System.out.println(textArea.select(".opr-recommends-merge-morelists"));
				System.out.println(textArea.html());*/
				for (Element element : items) {
					String result = element.select("div > a").attr("title");
					System.out.println(param + "-" + title + " - " +result);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
}
