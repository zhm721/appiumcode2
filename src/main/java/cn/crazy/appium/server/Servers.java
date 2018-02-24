package cn.crazy.appium.server;

import java.util.ArrayList;
import java.util.List;

import cn.crazy.appium.util.DosCmd;
import cn.crazy.appium.util.XmlUtil;

public class Servers {
	
	
	public static void main(String[] args) throws Exception {
		DosCmd dc=new DosCmd();
//		List<String> deviceList=dc.execCmdConsole("adb devices");
//		for(int i=1;i<deviceList.size()-1;i++){
//			String[] deviceInfo=deviceList.get(i).split("\t");
//			//System.out.println(deviceList.get(i));
//			if(deviceInfo[1].equals("device")){
//				System.out.println(deviceInfo[0]);
//			}
//		}
		Servers server=new Servers(new Port(new DosCmd()), new DosCmd());
		List<String>  devices=server.getDevices();
		for(String s:devices){
			System.out.println(s);
		}
		//server.startServers();
//		List<String> serverCommands=server.GeneratServerCommand();
//		for(String s:serverCommands){
//			System.out.println(s);
//		}
//		if(dc.killServer()){
//			server.startServers();
//		}
		//server.startServers();
		
	}
	private List<Integer> appiumPortList;
	private List<Integer> bootstrapPortList;
	private List<String> deviceList;
	private Port port;
	private DosCmd dos;
	private String path=System.getProperty("user.dir");
	public Servers(Port port,DosCmd dos){
		this.port=port;
		this.dos=dos;
	}
	/**
	 * 根据设备数量生成可用端口列表
	 * @param start 端口起始号
	 * @return 返回值是一个List<Integer>
	 * @throws Exception
	 */
	//
	private List<Integer> getPortList(int start) throws Exception{
		List<String> deviceList=getDevices();
		List<Integer> portList=port.GeneratPortList(start, deviceList.size());
		return portList;	
	}
	/**
	 * 获取当前可用设备{"xxx1","xx2","xx3"}
	 * @return
	 * @throws Exception
	 */
	public  List<String> getDevices() throws Exception {
		List<String> devList = dos.execCmdConsole("adb devices");
		List<String> deviceRes = new ArrayList<String>();
		if (devList.size() > 2) {
			for(int i = 1; i < devList.size() - 1; i++) {
				String deviceInfo[] = devList.get(i).split("\t");
				if (deviceInfo[1].trim().equals("device")) {
					deviceRes.add(deviceInfo[0].trim());
				}
			}
		} else {
			System.out.println("当前没有设备或设备连接状态不正确");
		}
		return deviceRes;
	}
	/**
	 * 生成服务端启动命令字符串存入List
	 * @return
	 * @throws Exception
	 */
	public List<String> GeneratServerCommand() throws Exception{
		appiumPortList=getPortList(4490);
		bootstrapPortList=getPortList(2233);
		deviceList=getDevices();
		List<String> commandList=new ArrayList<String>();
		//127.0.0.1:62001   xxxxxxxxx
		for(int i=0;i<deviceList.size();i++){
			String command="appium -p "+appiumPortList.get(i)+" -bp "+bootstrapPortList.get(i)
					+" -U "+deviceList.get(i)+">"+path+"/logs/"+deviceList.get(i).split(":")[0]+i+".log";
			System.out.println(command);
			commandList.add(command);
		}
		XmlUtil.createDeviceXml(deviceList,appiumPortList);
		return commandList;
	}
	/**
	 * 启动所有appium服务端
	 * @return
	 * @throws Exception
	 */
	public boolean startServers() throws Exception{
		List<String> startCommand=GeneratServerCommand();
		boolean flag=false;
		
		if(startCommand.size()>0){
			for(String s:startCommand){
				dos.execCmd(s);
			}
			flag=true;
		}else{
			flag=false;	
		}
		return flag;
	}

}
