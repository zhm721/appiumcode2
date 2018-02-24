package cn.crazy.appium.page;

import java.util.ArrayList;
import java.util.List;

import io.appium.java_client.android.AndroidElement;
import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.util.GetByLocator;
import cn.crazy.appium.util.RandomUtil;

public class EditPersonInfo extends BasePage{
	public AndroidElement save;
	public AndroidElement editPhoto;
	public AndroidElement male;
	public AndroidElement female;
	public AndroidElement gender;
	public AndroidElement headline;
	public AndroidElement description;
	public AndroidElement location;
	public AndroidElement profession;
	public AndroidElement professionvalue;
	public AndroidElement listview;
	public AndroidElement item;
	public AndroidElement genderReslut;
	
	public Home home;
	public More more;
	public Person person;
	public AndroidElement getSave() {
		return super.driver.findElement(GetByLocator.getLocator("save"));
	}

	public AndroidElement getEditPhoto() {
		return super.driver.findElement(GetByLocator.getLocator("editPhoto"));
	}

	public AndroidElement getMale() {
		return super.driver.findElement(GetByLocator.getLocator("male"));
	}

	public AndroidElement getFemale() {
		return super.driver.findElement(GetByLocator.getLocator("female"));
	}

	public AndroidElement getGender() {
		return super.driver.findElement(GetByLocator.getLocator("gender"));
	}

	public AndroidElement getHeadline() {
		return super.driver.findElement(GetByLocator.getLocator("headline"));
	}

	public AndroidElement getDescription() {
		return super.driver.findElement(GetByLocator.getLocator("description"));
	}

	public AndroidElement getLocation() {
		return super.driver.findElement(GetByLocator.getLocator("location"));
	}

	public AndroidElement getProfession() {
		return super.driver.findElement(GetByLocator.getLocator("profession"));
	}

	public AndroidElement getProfessionvalue() {
		return super.driver.findElement(GetByLocator.getLocator("professionvalue"));
	}

	public AndroidElement getListview() {
		return super.driver.findElement(GetByLocator.getLocator("listview"));
	}

	public AndroidElement getItem() {
		return super.driver.findElement(GetByLocator.getLocator("item"));
	}

	public AndroidElement getGenderReslut() {
		return super.driver.findElement(GetByLocator.getLocator("genderReslut"));
	}

	public EditPersonInfo(AndroidDriverBase driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		home=new Home(driver);
		more=new More(driver);
		person=new Person(driver);
	}
	
	public Boolean modifyPersonInfo(){
		click(home.getTabs().get(4));
		click(more.getPersonInfo());
		click(person.getEdit());
		
		//性别
		boolean flag=driver.isElementExist(getMale());
		click(getGender());
		if(flag){
			click(getFemale());
		}else{
			click(getMale());
		}
		String genderValue=getGenderReslut().getText();
		
		//一句话介绍
		String headlineNew=RandomUtil.getRndStrZhByLen(7);
		AndroidElement headlineElement=getHeadline();
		String heallineOld=headlineElement.getText();
		while(heallineOld.equals(headlineNew)){
			headlineNew=RandomUtil.getRndStrZhByLen(7);
		}
		sendkeys(getHeadline(), headlineNew);
		List<String> newValues=new ArrayList<String>();
		newValues.add(genderValue);
		newValues.add(headlineNew);
		click(getSave());
		click(person.getEdit());
		List<String> newValuesNew=new ArrayList<String>();
		String genderValueNew=getGenderReslut().getText();
		String headlineValues=getHeadline().getText();
		newValuesNew.add(genderValueNew);
		newValuesNew.add(headlineValues);
		boolean result=driver.listStrEquals(newValues, newValuesNew);
		return result;
	}

}
