package cn.crazy.appium.network.study;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cn.crazy.appium.base.CrazyPath;
import cn.crazy.appium.util.GetByLocator;
import cn.crazy.appium.util.ProUtil;
import cn.crazy.appium.util.RandomUtil;
import cn.crazy.appium.util.SendMail;

public class ZhihuPersonInfo {
	AndroidDriver<AndroidElement> driver;
	
	@BeforeClass
	public void beforeClass(){
		DesiredCapabilities caps=new DesiredCapabilities();
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, "suibianxie");
		caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.zhihu.android");
		caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.zhihu.android.app.ui.activity.MainActivity");
		caps.setCapability(AndroidMobileCapabilityType.NO_SIGN, true);
		caps.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD,true);
		caps.setCapability(AndroidMobileCapabilityType.RESET_KEYBOARD, true);
		caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 600);
		caps.setCapability(MobileCapabilityType.UDID, "127.0.0.1:62001");
		try {
			driver=new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4490/wd/hub"),caps);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ProUtil p=new ProUtil(CrazyPath.globalPath);
			SendMail sm=new SendMail();
			sm.send("driver创建失败",e.getMessage());
			e.printStackTrace();
		}
	}
	@Test
	public void t_personInfo() throws IOException{
		//点击菜单
		driver.findElement(GetByLocator.getLocator("tab5")).click();
		driver.findElement(GetByLocator.getLocator("persioninfo")).click();
		driver.findElement(GetByLocator.getLocator("edit")).click();
		if(isElementExits(GetByLocator.getLocator("male"))){
			driver.findElement(GetByLocator.getLocator("gender")).click();
			driver.findElement(GetByLocator.getLocator("female")).click();
		}else{
			driver.findElement(GetByLocator.getLocator("gender")).click();
			driver.findElement(GetByLocator.getLocator("male")).click();
		}
		String genderValue=driver.findElement(GetByLocator.getLocator("genderReslut")).getText();
		//一句话描述
		AndroidElement headline=driver.findElement(GetByLocator.getLocator("headline"));
		String headlineOldValue=headline.getText();
		String headlineNew=RandomUtil.getRndStrZhByLen(10);
		while(headlineNew.equals(headlineOldValue)){
			headlineNew=RandomUtil.getRndStrZhByLen(10);
		}
		headline.sendKeys(headlineNew);
		//个人介绍
		AndroidElement description=driver.findElement(GetByLocator.getLocator("description"));
		String descriptionOldValue=headline.getText();
		String descriptionNew=RandomUtil.getRndStrZhByLen(12);
		while(descriptionNew.equals(descriptionOldValue)){
			descriptionNew=RandomUtil.getRndStrZhByLen(12);
		}
		description.sendKeys(descriptionNew);
		//居住地
		AndroidElement location=driver.findElement(GetByLocator.getLocator("location"));
		String locationOldValue=headline.getText();
		String locationNew=RandomUtil.getRndStrZhByLen(9);
		while(locationNew.equals(locationOldValue)){
			locationNew=RandomUtil.getRndStrZhByLen(9);
		}
		location.sendKeys(locationNew);
		
		//行业
		String professionOldValue=driver.findElement(GetByLocator.getLocator("professionvalue")).getText();
		driver.findElement(GetByLocator.getLocator("profession")).click();
		AndroidElement listview=driver.findElement(GetByLocator.getLocator("listview"));
		//listview.swipe(SwipeElementDirection.UP, 20, 20, 1000);
		List<AndroidElement> itemList=driver.findElements(GetByLocator.getLocator("item"));
		int index=RandomUtil.randomInt(0, itemList.size());
		while(professionOldValue.equals(itemList.get(index).getText())){
			index=RandomUtil.randomInt(0, itemList.size());
		}
		itemList.get(index).click();
		String professionNew=driver.findElement(GetByLocator.getLocator("professionvalue")).getText();
		driver.findElement(GetByLocator.getLocator("save")).click();
		driver.findElement(GetByLocator.getLocator("edit")).click();
		try {
			Assert.assertEquals(driver.findElement(GetByLocator.getLocator("gender")).getText().equals(genderValue), true);
			Assert.assertEquals(driver.findElement(GetByLocator.getLocator("headline")).getText().equals(headlineNew), true);
			Assert.assertEquals(driver.findElement(GetByLocator.getLocator("description")).getText().equals(descriptionNew), true);
			Assert.assertEquals(driver.findElement(GetByLocator.getLocator("location")).getText().equals(locationNew), true);
			Assert.assertEquals(driver.findElement(GetByLocator.getLocator("professionvalue")).getText().equals(professionNew), true);
		} catch (Error e) {
			// TODO: handle exception
			File file=driver.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(file, new File("images/personerror.png"));
			Assert.fail(e.getMessage());
		}
		
	}
	
	@AfterClass
	public void afterClass(){
		driver.quit();
		
	}
	
	public Boolean isElementExits(By by){
		try {
			driver.findElement(by);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

}
