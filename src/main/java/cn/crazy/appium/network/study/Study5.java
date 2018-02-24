package cn.crazy.appium.network.study;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class Study5 {
	
	@Test(description="登录")
	public void test(){
		System.out.println("xxx");
	}
	@Test(description="退出")
	public void test1(){
		System.out.println("xxx");
		Reporter.log("<a href=imags/zhihu.png"   + " target=_blank>Failed Screen Shot</a>", true);
		Assert.fail("错了");
	}

}
