package cn.crazy.appium.page;

import java.util.List;

import io.appium.java_client.android.AndroidElement;
import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.util.GetByLocator;

public class Home extends BasePage{
	public AndroidElement search;
	public List<AndroidElement> titles;
	public AndroidElement article;
	public List<AndroidElement> tabs;
	public List<AndroidElement> getTabs() {
		return super.driver.findElements(GetByLocator.getLocator("tabs"));
	}
	public AndroidElement getSearch() {
		return super.driver.findElement(GetByLocator.getLocator("search"));
	}
	public List<AndroidElement> getTitles(){
		return super.driver.findElements(GetByLocator.getLocator("title"));
	}
	public AndroidElement getArtitle(){
		return super.driver.findElement(GetByLocator.getLocator("article"));
	}
	public Home(AndroidDriverBase driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	//搜索
	public boolean searchTest(String serachKeyword){
		click(getSearch());
		sendkeys(getSearch(), serachKeyword);
		List<AndroidElement> titles=getTitles();
		Boolean flag=false;
		for(AndroidElement ae:titles){
			if(ae.getText().toLowerCase().contains(serachKeyword.toLowerCase())){
				flag=true;
			}else{
				flag=false;
				break;
			}
		}
		return flag;
	}
}
