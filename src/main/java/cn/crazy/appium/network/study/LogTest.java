package cn.crazy.appium.network.study;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.util.Log;

public class LogTest {
	public static Log logger=Log.getLogger(LogTest.class);
	
	public static void main(String[] args) {
		logger.debug("xx1");
		logger.info("info");
		try {
			AndroidDriverBase driver=new DriverInit().driverInit("127.0.0.1:62001", "4723");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error("错了",e);
		}
	}

}
