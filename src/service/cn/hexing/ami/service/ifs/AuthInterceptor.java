package cn.hexing.ami.service.ifs;

import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPException;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.web.listener.AppEnv;

public class AuthInterceptor extends AbstractPhaseInterceptor<SoapMessage>{

	private String username;//用户名
	private String password;//密码
	private static String NODE_USERNAME="username";
	private static String NODE_PASSWORD="password";
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public AuthInterceptor() {
		super(Phase.PRE_INVOKE);
	}
	
	public void handleMessage(SoapMessage message) throws Fault {
		Map<String,String> sysMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_PARAMMAP);				
		if (sysMap != null) {
			setUsername(sysMap.get("WS.FDMUsername"));
			setPassword(sysMap.get("WS.FDMPassword"));
		}
		List<Header> headers = message.getHeaders();
    	
    	if (null == headers || headers.size() < 1) {
			throw new Fault(new SOAPException("the request SOAPHeader format is abnormal!"));
		}
		for (Header header : headers) {
			SoapHeader soapHeader = (SoapHeader) header;  
			 // 取出SOAP的Header元素  
            Element element = (Element) soapHeader.getObject();  
            String elementName = element.getNodeName()==null?"":element.getNodeName();
			if("Authorization".equals(elementName)){
				//用户名
				NodeList nodeList = element.getElementsByTagName(NODE_USERNAME);
				String usernameTmp = "";
				if (nodeList!=null && nodeList.getLength()!=0) {
					usernameTmp = nodeList.item(0).getTextContent();
				}
				//密码
				nodeList = element.getElementsByTagName(NODE_PASSWORD);
				String passwordTmp = "";
				if (nodeList!=null && nodeList.getLength()!=0) {
					passwordTmp = nodeList.item(0).getTextContent();
				}
				if (StringUtil.isEmptyString(usernameTmp) || StringUtil.isEmptyString(passwordTmp)) {
					throw new Fault(new SOAPException("Illegal request,SOAPHeader must include the username and password node!"));
				}else if(!(this.getUsername().equals(usernameTmp) && this.getPassword().equals(passwordTmp))){
					throw new Fault(new SOAPException("the username or password not correct!"));
				}
			}else{
				throw new Fault(new SOAPException("the soapHeader format error!"));
			}
		}
	}

}
