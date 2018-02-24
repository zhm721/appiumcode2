package cn.crazy.appium.network.study;

import io.appium.java_client.android.AndroidElement;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.base.CrazyPath;
import cn.crazy.appium.util.GetByLocator;
import cn.crazy.appium.util.ImageUtil;
import cn.crazy.appium.util.RandomUtil;
import cn.crazy.appium.util.SendMail;

public class UploadPhoto {
	AndroidDriverBase driver;
	@BeforeClass
	public void beforeClass() {
		String server="http://127.0.0.1";
		String port="4490";
		String capsPath=CrazyPath.capsPath;
		String udid="127.0.0.1:62001";
		String input="com.example.android.softkeyboard/.SoftKeyboard";
		try {
			driver=new AndroidDriverBase(server, port, capsPath, udid, input);
			driver.implicitlyWaitDefault();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SendMail sm=new SendMail();
			sm.send("driver创建失败", e.getMessage());
		}
		
	}
	@Test
	public void t001_upload() throws Exception{
		driver.findElement(GetByLocator.getLocator("tab5")).click();
		driver.findElement(GetByLocator.getLocator("persioninfo")).click();
		driver.findElement(GetByLocator.getLocator("edit")).click();
		driver.takeScreenForElement(GetByLocator.getLocator("header"), "images/","current");
		int current=0;
		for(int i=1;i<5;i++){
			if(ImageUtil.compareImg("images/"+i+".png","images/current.png", 100f)){
				current=i;
				break;
			}
		}
		AndroidElement editPhoto=driver.findElement(GetByLocator.getLocator("editPhoto"));
		editPhoto.click();
		AndroidElement photo=driver.findElement(GetByLocator.getLocator("photo"));
		photo.click();
		List<AndroidElement> photos=driver.findElements(GetByLocator.getLocator("photos"));
		int index=RandomUtil.randomInt(1, photos.size());
		while(index==current){
			index=RandomUtil.randomInt(1, photos.size());
		}
		photos.get(index).click();
		AndroidElement cancue=driver.findElement(GetByLocator.getLocator("ingroe"));
		if(cancue!=null){
			cancue.click();
		}
		driver.takeScreenForElement(GetByLocator.getLocator("header"),"images/","result");
		try {
			Assert.assertEquals(ImageUtil.compareImg("images/"+index+".png","images/result.png", 100f), true);
		} catch (Error e) {
			// TODO: handle exception
			driver.takeScreen("images", "uploaderror");
			Assert.fail(e.getMessage()+"上传的第"+index+"张");
		}
	
//		//下面是采集原图的过程
//		for(int i=1;i<photos.size();i++){
//			photos.get(i).click();
//			driver.wait(2000);
//			AndroidElement cancue=driver.findElement(GetByLocator.getLocator("ingroe"));
//			if(cancue!=null){
//				cancue.click();
//			}
//			driver.takeScreenForElement(GetByLocator.getLocator("header"), "images/",String.valueOf(i));
//			editPhoto.click();
//			photo.click();
//		}
	}
	@AfterClass
	public void afterClass(){
		driver.quit();
	}
	
}
