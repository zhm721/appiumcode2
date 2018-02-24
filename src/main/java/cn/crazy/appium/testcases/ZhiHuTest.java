package cn.crazy.appium.testcases;

import java.io.IOException;

import io.appium.java_client.remote.AndroidMobileCapabilityType;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.base.CrazyPath;
import cn.crazy.appium.datadriver.ExcelUtil;
import cn.crazy.appium.page.Article;
import cn.crazy.appium.page.EditPersonInfo;
import cn.crazy.appium.page.Home;
import cn.crazy.appium.page.Login;
import cn.crazy.appium.testng.Assertion;
import cn.crazy.appium.testng.TestngRetry;
import cn.crazy.appium.util.ProUtil;
import cn.crazy.appium.util.SendMail;

public class ZhiHuTest {
	AndroidDriverBase driver;
	Assertion myAssert;
	@Parameters({"udid","port"})
	@BeforeClass
	public void beforeClass(String udid,String port){
		ProUtil p=new ProUtil(CrazyPath.globalPath);
		String server=p.getPro("server");
		//String port="4490";
		String capsPath=CrazyPath.capsPath;
		//String udid="127.0.0.1:62001";
		String input="com.example.android.softkeyboard/.SoftKeyboard";
		try {
			driver=new AndroidDriverBase(server, port, capsPath, udid, input);
			myAssert=new Assertion(driver);
			driver.implicitlyWaitDefault();
		} catch (Exception e) {
			// TODO: handle exception
			SendMail sm=new SendMail();
			sm.send("driver失败", e.getMessage());
		}
	}
	@DataProvider
	public Object[][] loginData() throws Exception{
		Object[][] ob=ExcelUtil.getTestData("C:\\Users\\LXG\\Desktop/test.xlsx", "Sheet1");
		return ob;
	}
	@Test(dataProvider="loginData")
	public void t001_login(String caseNumber,String caseName,String username,String password,String assertValue,String filename) throws Exception{
		ExcelUtil eu=new ExcelUtil("C:\\Users\\LXG\\Desktop/test.xlsx", "Sheet1");
		Login l=new Login(driver);
		Home home=l.loginTest(username,password);
		try {
			myAssert.assertEquals(driver.getPageSouce().contains(assertValue), true, filename);
			eu.setCellData(Integer.valueOf(caseNumber), eu.getLastColumnNum(), "测试通过");
		} catch (Error e) {
			// TODO: handle exception
			eu.setCellData(Integer.valueOf(caseNumber), eu.getLastColumnNum(), "测试失败");
			Assert.fail(e.getMessage());
		}
		
//		try {
//			Assert.assertEquals(home.getSearch()!=null, true);
//		} catch (Exception e) {
//			// TODO: handle exception
//			try {
//				driver.takeScreen("images", "loginerror");
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			Assert.fail(e.getMessage());
//		}
	}
	@Test(enabled=false)
	public void t002_search(){
		Home home=new Home(driver);
		Boolean flag=home.searchTest("test");
		myAssert.assertEquals(flag==true, true, "searcherror");
//		try {
//			Assert.assertEquals(flag==true, true);
//		} catch (Exception e) {
//			// TODO: handle exception
//			try {
//				driver.takeScreen("images", "searcherror");
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			Assert.fail(e.getMessage());
//		}
	}
	@Test(enabled=false)
	public void t003_attention(){
		Article	ar=new Article(driver);
		boolean result=ar.attention();
		myAssert.assertEquals(result==true, true, "attentionerror");
//		try {
//			Assert.assertEquals(result==true, true);
//		} catch (Exception e) {
//			// TODO: handle exception
//			try {
//				driver.takeScreen("images", "attentionerror");
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			Assert.fail(e.getMessage());
//		}
	}
	@Test(enabled=false)
	public void t004_personInfo(){
		EditPersonInfo edinfo=new EditPersonInfo(driver);
		boolean flag=edinfo.modifyPersonInfo();
		myAssert.assertEquals(flag, true, "personinfoerror");
//		try {
//			Assert.assertEquals(flag, true);
//		} catch (Error e) {
//			// TODO: handle exception
//			try {
//				driver.takeScreen("images", "personinfoerror");
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			Assert.fail(e.getMessage());
//		}
		System.out.println(1);
		
	}
	
	@AfterMethod
	public void restart(){
		String appPackge=(String)driver.getCapabilities().getCapability(AndroidMobileCapabilityType.APP_PACKAGE);
		String appActivity=(String)driver.getCapabilities().getCapability(AndroidMobileCapabilityType.APP_ACTIVITY);
		driver.startActivity(appPackge,appActivity);
	}
	
	@AfterClass
	public void afterClass(){
		driver.resetApp();
		driver.quit();
	}

}
