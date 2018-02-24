package cn.crazy.appium.page;

import io.appium.java_client.android.AndroidElement;
import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.util.GetByLocator;

public class Article extends BasePage {
	public AndroidElement attention;
	public AndroidElement notAttention;
	public Home home;
	public More more;
	public Article(AndroidDriverBase driver) {
		super(driver);
		home=new Home(driver);
		more=new More(driver);
		// TODO Auto-generated constructor stub
	}
	
	public AndroidElement getAttention() {
		return super.driver.findElement(GetByLocator.getLocator("attention"));
	}
	public AndroidElement getNotAttention() {
		return super.driver.findElement(GetByLocator.getLocator("notAttention"));
	}
	//关注
	public boolean attention(){
		String text=home.getArtitle().getText();
		click(home.getArtitle());
		boolean flag=false;
		if(driver.isElementExist(GetByLocator.getLocator("attention"))){
			//getAttention().click();
			click(getAttention());
			driver.findElement(GetByLocator.getLocator("tab5")).click();
			more.getMyFollow().click();
			if(getPageSource().contains("text")){
				flag=true;
			}else{
				flag=false;
			}
		}
		return flag;
//		}else{
//			click(getNotAttention());
//			driver.findElement(GetByLocator.getLocator("tab5")).click();
//			more.getMyFollow().click();
//			if(!getPageSource().contains("text")){
//				return "取消关注成功";
//			}else{
//				return "取消关注失败";
//			}
//		}
	}
}
