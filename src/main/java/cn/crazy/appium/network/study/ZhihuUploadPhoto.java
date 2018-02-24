package cn.crazy.appium.network.study;

import io.appium.java_client.android.AndroidElement;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.base.CrazyPath;
import cn.crazy.appium.util.GetByLocator;
import cn.crazy.appium.util.ImageUtil;
import cn.crazy.appium.util.RandomUtil;
import cn.crazy.appium.util.SendMail;

public class ZhihuUploadPhoto {
	AndroidDriverBase driver;
	
	@BeforeClass
	public void beforeClass(){
		String server="http://127.0.0.1";
		String port="4490";
		String capsPath=CrazyPath.capsPath;
		String udid="127.0.0.1:62001";
		String input="com.example.android.softkeyboard/.SoftKeyboard";
		try {
			driver=new AndroidDriverBase(server, port, capsPath, udid, input);
			driver.implicitlyWait(5);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SendMail sm=new SendMail();
			sm.send("uploadphoto driver失败", e.getMessage());
		}
	}
	@Test
	public void t001_uploadPhoto() throws Exception{
		driver.findElement(GetByLocator.getLocator("tab5")).click();
		driver.findElement(GetByLocator.getLocator("persioninfo")).click();
		driver.findElement(GetByLocator.getLocator("edit")).click();
		driver.takeScreenForElement(GetByLocator.getLocator("header"), CrazyPath.path+"/images/","current");
		driver.wait(3000);
		int indexPhoto=0;
		for(int i=1;i<6;i++){
			if(ImageUtil.compareImg("images/"+i+".png","images/current.png", 100f)){
				indexPhoto=i;
				break;
			}
		}
		//点击上传开始
		AndroidElement editPhoto=driver.findElement(GetByLocator.getLocator("editPhoto"));
		editPhoto.click();
		AndroidElement photo=driver.findElement(GetByLocator.getLocator("photo"));
		photo.click();
		List<AndroidElement> photolist=driver.findElements(GetByLocator.getLocator("photos"));

		int index=RandomUtil.randomInt(1, photolist.size()-1);
		while(index==indexPhoto){
			index=RandomUtil.randomInt(1, photolist.size()-1);
		}
		photolist.get(index).click();
		AndroidElement cancue=driver.findElement(GetByLocator.getLocator("ingroe"));
		if(cancue!=null){
			cancue.click();
		}
		driver.wait(2000);
		driver.takeScreenForElement(GetByLocator.getLocator("header"), CrazyPath.path+"/images/","current");
		try {
			Assert.assertEquals(ImageUtil.compareImg("images/"+String.valueOf(index)+".png","images/current.png", 100f), true);
		} catch (Error e) {
			// TODO: handle exception
			driver.takeScreen(CrazyPath.path+"/images", "uploaderror");
		}
	
//		for (int i = 1; i < photolist.size(); i++) {
//			photolist.get(i).click();
//			AndroidElement cancue=driver.findElement(GetByLocator.getLocator("ingroe"));
//			if(cancue!=null){
//				cancue.click();
//			}
//			driver.wait(2000);
//			driver.takeScreenForElement(GetByLocator.getLocator("header"), CrazyPath.path+"/images/",String.valueOf(i));
//			editPhoto.click();
//			photo.click();
//		}
	}
	@AfterClass
	public void afterClass(){
		driver.quit();
	}

}
