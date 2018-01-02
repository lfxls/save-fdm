package cn.hexing.ami.util;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 采用DOM4J解析XML
 * @author jun
 *
 */
public class XmlUtil {
	private static Logger logger = Logger.getLogger(XmlUtil.class.getName());
	
	/**
	 * 获取Document
	 * @param String filePath 文件路径
	 * @return
	 * @throws Exception
	 */
	public static Document getDocument(String filePath) throws Exception {
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new File(filePath));
		saxReader=null;
		return document;
	}
	
	/**
	 * 获取Document
	 * @param xmlContent xml的内容字符串
	 * @return
	 * @throws Exception
	 */
	public static Document GetDocument(String xmlContent)throws Exception {
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new StringReader(xmlContent));
		saxReader=null;
		return document;
	}
	/**
	 * 返回指定名称节点Element列表
	 * @param xmlContent xml的内容字符串
	 * @param nodeName 节点名称
	 * @return
	 */
	public static Element[] getElement(String filePath,String nodeName){
		Element elements[] = null;
		try{
			Document document = getDocument(filePath);
			//没有加上相对路径，自动补上
			if (!nodeName.startsWith("//")) {
				nodeName = "//"+nodeName;
			}
			List nodeList = document.selectNodes(nodeName);
			
			elements = new Element[nodeList.size()];
			Iterator iter = nodeList.iterator();
			
			int i=0;
			while (iter.hasNext()){
				Element element=(Element)iter.next();
				elements[i]= element;
				i++;
			}
		}catch(Exception ex){
			logger.error("解析XML时出错："+ex.getMessage());
		}
		return elements;
	}
	
	/**
	 * 返回指定名称节点Element列表
	 * @param xmlContent xml的内容字符串
	 * @param nodeName 节点名称
	 * @return
	 */
	public static Element[] getElementByXml(String xmlContent,String nodeName){
		Element elements[] = null;
		try{
			Document document = GetDocument(xmlContent);
			//没有加上相对路径，自动补上
			if (!nodeName.startsWith("//")) {
				nodeName = "//"+nodeName;
			}
			List nodeList = document.selectNodes(nodeName);
			
			elements = new Element[nodeList.size()];
			Iterator iter = nodeList.iterator();
			
			int i=0;
			while (iter.hasNext()){
				Element element=(Element)iter.next();
				elements[i]= element;
				i++;
			}
		}catch(Exception ex){
			logger.error("解析XML时出错："+ex.getMessage());
		}
		return elements;
	}
	
	/**
	 * 返回某个节点的所有属性列表，以HashMap方式返回
	 * @param element 指定节点
	 * @return
	 */
	public static HashMap<String,String> getAttributeMap(Element element){
		HashMap<String,String> attributeMap = new HashMap<String,String>();
		List attrList = element.attributes();
		for (int i = 0; i < attrList.size(); i++) {
			Attribute abttibute = (Attribute)attrList.get(i);
			
			String abttibuteName = abttibute.getName();
			String abttibuteValue = abttibute.getValue();
			attributeMap.put(abttibuteName,abttibuteValue);
		}
		return attributeMap;
	}
	
	/**
	 * 获取某个节点的指定子节点
	 * @param element
	 * @param childElementName
	 * @return
	 */
	public static Element[] getChildElement(Element element,String childElementName){
		ArrayList<Element> elementList = new ArrayList<Element>();
		Iterator itemIter = element.elementIterator(childElementName);
		while (itemIter.hasNext()) {
			Element itemElement = (Element) itemIter.next();
			elementList.add(itemElement);
		}
		
		Element[] elements = new Element[elementList.size()];
		for (int i = 0; i < elementList.size(); i++) {
			elements[i] = (Element)elementList.get(i);
		}
		return elements;
	}
	
	/**
	 * 获取指定节点（属性为name）名称的Element，只匹配第一个
	 * @param elements
	 * @param name
	 * @return
	 */
	public static Element getElementByAttributeName(Element[] elements,String name){
		for (int i = 0; i < elements.length; i++) {
			HashMap<String,String> tempMap = getAttributeMap(elements[i]);
			String attibuteName = (String)tempMap.get("name");
			if (name!=null && attibuteName!=null && name.indexOf(attibuteName)!=-1) {
				return elements[i];
			}
		}
		return null;
	}
	
	/**
	 * 获取指定节点（属性为name）名称的Element，返回所有匹配的节点
	 * @param elements
	 * @param name
	 * @return
	 */
	public static Element[] getElementArrayByAttributeName(Element[] elements,String attName){
		Element[] element = null;
		ArrayList<Element> elementList = new ArrayList<Element>();
		for (int i = 0; i < elements.length; i++) {
			HashMap<String,String> tempMap = getAttributeMap(elements[i]);
			for (Iterator iterator = tempMap.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				if (key.toUpperCase().equals(attName.toUpperCase())) {
					elementList.add(elements[i]);
				}
			}
		}
		
		element = new Element[elementList.size()];
		for (int i = 0; i < elementList.size(); i++) {
			element[i] = (Element)elementList.get(i);
		}
		return element;
	}
	
	/**
	 * 获取指定节点（属性为name）名称的Element，返回所有匹配的节点
	 * @param elements
	 * @param name
	 * @return
	 */
	public static Element[] getElementArrayByAttributeName(Element[] elements,String attName,String attValue){
		Element[] element = null;
		ArrayList<Element> elementList = new ArrayList<Element>();
		for (int i = 0; i < elements.length; i++) {
			HashMap<String,String> tempMap = getAttributeMap(elements[i]);
			String attValueTmp = (String)tempMap.get(attName);
			if (attValueTmp.indexOf(attValue)!=-1) {
				elementList.add(elements[i]);
			}
		}
		
		element = new Element[elementList.size()];
		for (int i = 0; i < elementList.size(); i++) {
			element[i] = (Element)elementList.get(i);
		}
		return element;
	}
}
