package cn.crazy.appium.page;

import io.appium.java_client.android.AndroidElement;
import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.util.GetByLocator;

public class Person extends BasePage {
	public AndroidElement edit;
	
	public AndroidElement getEdit(){
		return super.driver.findElement(GetByLocator.getLocator("edit"));
	}
	public Person(AndroidDriverBase driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

}
