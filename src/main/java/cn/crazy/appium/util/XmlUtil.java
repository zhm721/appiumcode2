package cn.crazy.appium.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import cn.crazy.appium.server.Port;
import cn.crazy.appium.server.Servers;


public class XmlUtil {
	/**
	 * 读取device.xml配置文件  127.0.0.1:62025，4490，127.0.0.1:62001,4491
	 * @param filePath   
	 * @return
	 * @throws DocumentException
	 */
	public static List<String> readXML(String filePath) throws DocumentException {
		
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new File(filePath));
		// 获取根元素
		Element root = document.getRootElement();
		//System.out.println(root.getName());

		// 获取特定名称的子元素
		@SuppressWarnings("unchecked")
		List<Element> deviceList = root.elements("deviceId");
		// 迭代输出
		//{"127.0.0.1:62001","4490","127.0.0.1:62025","4491"}
		List<String> deviceData = new ArrayList<String>();
		for (Element e : deviceList) {
			for (Iterator iter = e.elementIterator(); iter.hasNext();) {
				Element e1 = (Element) iter.next();
				deviceData.add(e1.getText());
			}
		}
		return deviceData;
	}
	/**
	 * 创建device.xml文件
	 * @param deviceList
	 * @param appiumPortList
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void createDeviceXml(List<String> deviceList,List<Integer> appiumPortList) throws IOException,
	InterruptedException {
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("Device");
		document.setRootElement(root);
		root.addAttribute("name", "appiumstartlist");
		if (deviceList.size() > 0) {
			for (int j = 0; j < deviceList.size(); j++) {
				Element deviceId = root.addElement("deviceId");
				deviceId.addAttribute("id", String.valueOf(j));
				Element deviceName = deviceId.addElement("deviceName");
				Element appiumPort = deviceId.addElement("appiumPort");
				deviceName.setText(deviceList.get(j));
				appiumPort.setText(String.valueOf(appiumPortList.get(j)));
			}
		}
		OutputFormat format = new OutputFormat("    ", true);
		XMLWriter xmlWrite2 = new XMLWriter(new FileOutputStream("configs/device.xml"),
				format);
		xmlWrite2.write(document);
	}
	/**
	 * 创建testng.xml配置文件
	 * 
	 * @param classname
	 * @throws Exception
	 */
	public static void createTestngXml(String classname) throws Exception {
		Servers servers=new Servers(new Port(new DosCmd()), new DosCmd());
		List<String> deviceList=servers.getDevices();
		System.out.println("设备数量"+deviceList.size());
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("suite");
		document.setRootElement(root);
		root.addAttribute("name", "Suite");
		root.addAttribute("parallel", "tests");
		root.addAttribute("thread-count", String.valueOf(deviceList.size()));
		Element listeners=root.addElement("listeners");
		Element listener1=listeners.addElement("listener");
		listener1.addAttribute("class-name", "org.uncommons.reportng.HTMLReporter");
		Element listener2=listeners.addElement("listener");
		listener2.addAttribute("class-name", "org.uncommons.reportng.JUnitXMLReporter");
		List<String> s=readXML("configs/device.xml");
		//{192.168.56.101:5555,4490,192.168.56.102:5555,4491,xxx,4492,yyy,4493}
		for(int j=0;j<deviceList.size();j++){
			Element test = root.addElement("test");
			test.addAttribute("name", deviceList.get(j));
			Element paramUuid=test.addElement("parameter");
			paramUuid.addAttribute("name","udid");
			paramUuid.addAttribute("value",s.get(2*j));
			Element paramPort=test.addElement("parameter");
			paramPort.addAttribute("name", "port");
			paramPort.addAttribute("value",s.get(2*j+1));
			Element classes = test.addElement("classes");
			Element classNode=classes.addElement("class");
			classNode.addAttribute("name", classname);
		}
		OutputFormat format = new OutputFormat("    ", true);
		XMLWriter xmlWrite2;
		try {
			xmlWrite2 = new XMLWriter(new FileOutputStream("testng.xml"),format);
			xmlWrite2.write(document);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void createTestngXml(List<String> classesList) throws Exception {
		Servers servers=new Servers(new Port(new DosCmd()), new DosCmd());
		List<String> deviceList=servers.getDevices();
		System.out.println("设备数量"+deviceList.size());
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("suite");
		document.setRootElement(root);
		root.addAttribute("name", "Suite");
		root.addAttribute("parallel", "tests");
		root.addAttribute("thread-count", String.valueOf(deviceList.size()));
		Element listeners=root.addElement("listeners");
		Element listener1=listeners.addElement("listener");
		listener1.addAttribute("class-name", "org.uncommons.reportng.HTMLReporter");
		Element listener2=listeners.addElement("listener");
		listener2.addAttribute("class-name", "org.uncommons.reportng.JUnitXMLReporter");
		List<String> s=readXML("configs/device.xml");
		//{192.168.56.101:5555,4490,192.168.56.102:5555,4491,xxx,4492,yyy,4493}
		for(int j=0;j<deviceList.size();j++){
			Element test = root.addElement("test");
			test.addAttribute("name", deviceList.get(j));
			Element paramUuid=test.addElement("parameter");
			paramUuid.addAttribute("name","udid");
			paramUuid.addAttribute("value",s.get(2*j));
			Element paramPort=test.addElement("parameter");
			paramPort.addAttribute("name", "port");
			paramPort.addAttribute("value",s.get(2*j+1));
			Element classes = test.addElement("classes");
			for(String className:classesList){
				Element classNode=classes.addElement("class");
				classNode.addAttribute("name", className);
			}
		}
		OutputFormat format = new OutputFormat("    ", true);
		XMLWriter xmlWrite2;
		try {
			xmlWrite2 = new XMLWriter(new FileOutputStream("testng.xml"),format);
			xmlWrite2.write(document);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void createTestngSingleXml(List<String> classesList) throws Exception {
		Servers servers=new Servers(new Port(new DosCmd()), new DosCmd());
		List<String> deviceList=servers.getDevices();
		System.out.println("设备数量"+deviceList.size());
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("suite");
		document.setRootElement(root);
		root.addAttribute("name", "Suite");
		root.addAttribute("parallel", "tests");
		root.addAttribute("thread-count", String.valueOf(deviceList.size()));
		Element listeners=root.addElement("listeners");
		Element listener1=listeners.addElement("listener");
		listener1.addAttribute("class-name", "org.uncommons.reportng.HTMLReporter");
		Element listener2=listeners.addElement("listener");
		listener2.addAttribute("class-name", "org.uncommons.reportng.JUnitXMLReporter");
		List<String> s=readXML("configs/device.xml");
		//{192.168.56.101:5555,4490,192.168.56.102:5555,4491,xxx,4492,yyy,4493}
		for(int j=0;j<deviceList.size();j++){
			Element test = root.addElement("test");
			test.addAttribute("name", deviceList.get(j));
			Element paramUuid=test.addElement("parameter");
			paramUuid.addAttribute("name","udid");
			paramUuid.addAttribute("value",s.get(2*j));
			Element paramPort=test.addElement("parameter");
			paramPort.addAttribute("name", "port");
			paramPort.addAttribute("value",s.get(2*j+1));
			Element classes = test.addElement("classes");
			Element classNode=classes.addElement("class");
			classNode.addAttribute("name", classesList.get(j));
		}
		OutputFormat format = new OutputFormat("    ", true);
		XMLWriter xmlWrite2;
		try {
			xmlWrite2 = new XMLWriter(new FileOutputStream("testng.xml"),format);
			xmlWrite2.write(document);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception {
//		List<String>  deviceData=readXML("configs/device.xml");
//		for(String s:deviceData){
//			System.out.println(s);
//		}
//		SAXReader reader=new SAXReader();
//		Document doc=reader.read(new File("configs/student1.xml"));
//		Element root=doc.getRootElement();
//		List<Element> s=root.elements("student");
//		for(Element e:s){
//			System.out.println(e.getText());
//			System.out.println(e.attributeValue("id"));
//		}
//		createTestngXml("xx.x.xx.xx.xx.appium1");
//		createTestngXml("cn.crazy.appium.testcases.ZhiHuTest");
		
		
		
		//createTestngXml("cn.crazy.appium.network.study.Study");
//	
//		createDeviceXml(new Servers(new Port(new DosCmd()),new DosCmd()).getDevices() ,new Port(new DosCmd()).GeneratPortList(4490, 2));
		List<String> classesList=new ArrayList<String>();
		classesList.add("cn.crazy.appium.network.study.Study");
		classesList.add("cn.crazy.appium.network.study.Study1");
//		classesList.add("cn.crazy.appium.network.study.Study2");
		createTestngSingleXml(classesList);
		//写xml文件的例子
//		Document d=DocumentHelper.createDocument();
//		Element root=DocumentHelper.createElement("teacher");
//		d.setRootElement(root);
//		root.addAttribute("name", "沙陌1");
//		root.addAttribute("xxx", "沙陌22");
//		String[] studentArr={"艳1","tracy-yan1","你给我滚出去1","流星1","下个路口1","神庙1","心雪1"};
//		for(int i=0;i<studentArr.length;i++){
//			Element student=root.addElement("student");
//			student.addAttribute("id", String.valueOf(i));
//			student.setText(studentArr[i]);
//		}
//		OutputFormat format=new OutputFormat("    ", true);
//		XMLWriter w=new XMLWriter(new FileOutputStream("configs/student1.xml"), format);
//		w.write(d);
		
	}
}
