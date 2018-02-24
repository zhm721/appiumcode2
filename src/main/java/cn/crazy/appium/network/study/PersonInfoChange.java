package cn.crazy.appium.network.study;

import java.util.ArrayList;
import java.util.List;

import io.appium.java_client.android.AndroidElement;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.base.CrazyPath;
import cn.crazy.appium.util.GetByLocator;
import cn.crazy.appium.util.ProUtil;
import cn.crazy.appium.util.RandomUtil;
import cn.crazy.appium.util.SendMail;

public class PersonInfoChange extends DriverInit {
	AndroidDriverBase driver;
	@Parameters({"udid","port"})
	@BeforeClass
	public void beforeClass(String udid,String port) throws Exception{
		try {
			driver=driverInit(udid, port);
		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			SendMail sm=new SendMail();
			ProUtil p=new ProUtil(CrazyPath.globalPath);
	    	String[] to=p.getPro("tomail").split(",");
	    	sm.send("driver初始化失败",udid+"driver初始化失败");
		}
		
		System.out.println("study 1 driver is "+driver);
	}
	
	@Test
	public void personInfo(){
		
		driver.findElement(GetByLocator.getLocator("tab5")).click();
		driver.findElement(GetByLocator.getLocator("persioninfo")).click();
		driver.findElement(GetByLocator.getLocator("edit")).click();
		
		//如果存在男那么就选女，否则就选男
		String genderVlaue="";
		AndroidElement gender=driver.findElement(GetByLocator.getLocator("gender"));
		if(driver.isElementExist(GetByLocator.getLocator("male"))){
			gender.click();
			driver.findElement(GetByLocator.getLocator("female")).click();
			genderVlaue="女";
		}else{
			gender.click();
			driver.findElement(GetByLocator.getLocator("male")).click();
			genderVlaue="男";
		}
		//一句话描述
		AndroidElement headline=driver.findElement(GetByLocator.getLocator("headline"));
		String headlineOld=headline.getText();
		String headlinNew=RandomUtil.getRndStrZhByLen(12);
		while(headlineOld.equals(headlinNew)){
			headlinNew=RandomUtil.getRndStrZhByLen(12);
		}
		headline.sendKeys(headlinNew);
		
		//个人介绍
		AndroidElement description=driver.findElement(GetByLocator.getLocator("description"));
		String descriptionOld=description.getText();
		String descriptionNew=RandomUtil.getRndStrZhByLen(20);
		while(descriptionOld.equals(descriptionNew)){
			descriptionNew=RandomUtil.getRndStrZhByLen(20);
		}
		description.sendKeys(descriptionNew);
		
		//居住地
		AndroidElement location=driver.findElement(GetByLocator.getLocator("location"));
		String locationOld=location.getText();
		String locationNew=RandomUtil.getRndStrZhByLen(15);
		while(locationOld.equals(locationNew)){
			locationNew=RandomUtil.getRndStrZhByLen(15);
		}
		location.sendKeys(locationNew);
		
		//行业
		AndroidElement profession=driver.findElement(GetByLocator.getLocator("profession"));
		String professionOld=profession.getAttribute("text");
		profession.click();
		int swipeCount=RandomUtil.getExtentRandomNumber(3);
		while(swipeCount>0){
			driver.swipeOnElement(GetByLocator.getLocator("listview"), "UP", 1500);
		}
		List<AndroidElement> itemList=driver.findElements(GetByLocator.getLocator("item"));
		int index=Integer.valueOf(RandomUtil.randomInt(1, itemList.size()-1));
		AndroidElement item=itemList.get(index);
		while(item.getText().equals(professionOld)){
			index=Integer.valueOf(RandomUtil.randomInt(1, itemList.size()-1));
			item=itemList.get(index);
		}
		String professionNew=item.getText();
		item.click();
		
		//点击保存之前各项的值存在一个集合里
		List<String> oldInfoValue=new ArrayList<String>();
		oldInfoValue.add(genderVlaue);//性别
		oldInfoValue.add(headlinNew);//一句话描述
		oldInfoValue.add(descriptionNew);//个人介绍
		oldInfoValue.add(locationNew);//居住地
		oldInfoValue.add(professionNew);//行业
		
		//点击保存
		driver.findElement(GetByLocator.getLocator("save")).click();
		//再次点击编辑按钮，进入编辑界面，获取各项信息
		driver.findElement(GetByLocator.getLocator("edit")).click();
		
		String genderResult=driver.findElement(GetByLocator.getLocator("genderReslut")).getAttribute("text");
		String headlineResult=driver.findElement(GetByLocator.getLocator("headline")).getText();
		String descriptionResult=driver.findElement(GetByLocator.getLocator("description")).getText();
		String locationResult=driver.findElement(GetByLocator.getLocator("location")).getText();
		String professionResult=driver.findElement(GetByLocator.getLocator("professionvalue")).getText();
		
		//把保存呢后的信息都存在结果集合里
		List<String> resultList=new ArrayList<String>();
		resultList.add(genderResult);
		resultList.add(headlineResult);
		resultList.add(descriptionResult);
		resultList.add(locationResult);
		resultList.add(professionResult);
		
		Boolean result=driver.listStrEquals(oldInfoValue, resultList);
		//断言两个集合是否完全一致，一致则表明保存成功
		try {
			Assert.assertTrue(result);
		} catch (Error e) {
			// TODO: handle exception
			SendMail sm=new SendMail();
			sm.send("个人资料测试用例", "个人资料测试用例修改失败");
			Assert.fail("个人资料修改失败",e);
		}
		
	}
	@AfterClass
	public void afterClass(){
		driver.quit();
	}

}
