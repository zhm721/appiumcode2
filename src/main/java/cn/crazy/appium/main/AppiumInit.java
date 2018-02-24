package cn.crazy.appium.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.crazy.appium.server.Port;
import cn.crazy.appium.server.Servers;
import cn.crazy.appium.util.DosCmd;
import cn.crazy.appium.util.XmlUtil;

public class AppiumInit {
	public static void init(){
		Servers servers=new Servers(new Port(new DosCmd()), new DosCmd());
		DosCmd dc=new DosCmd();
		if(dc.killServer()){
			try {
				System.out.println("开始启动服务");
				servers.startServers();
				System.out.println("服务启动完毕");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
//				List<String> classes=new ArrayList<String>();
//				classes.add("xxx");
//				classes.add("yyyy");
//				XmlUtil.createTestngSingleXml(classes);
				XmlUtil.createTestngXml("cn.crazy.appium.testcases.ZhiHuTest");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("清除appium服务失败");
		}
	}
	public static void main(String[] args) {
		init();
	}

}
