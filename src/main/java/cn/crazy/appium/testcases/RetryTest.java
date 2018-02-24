package cn.crazy.appium.testcases;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import cn.crazy.appium.testng.RetryListener;
import cn.crazy.appium.testng.TestngListener;
import cn.crazy.appium.testng.TestngRetry;

@Listeners(TestngListener.class)
public class RetryTest {
	
	@Test(retryAnalyzer=TestngRetry.class)
	public void test(){
		System.out.println("xx");
		Assert.fail();
	}
}
