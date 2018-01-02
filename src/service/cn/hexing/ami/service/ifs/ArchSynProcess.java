package cn.hexing.ami.service.ifs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.hexing.ami.dao.common.ibatis.BaseDAOIbatis;
import cn.hexing.ami.util.ActionResult;
import cn.hexing.ami.util.Constants;
import cn.hexing.ami.util.MsgCodeConstants;
import cn.hexing.ami.util.StringUtil;
import cn.hexing.ami.util.XmlUtil;
import cn.hexing.ami.web.listener.AppEnv;

/** 
 * @Description 档案同步业务处理
 * @author xcx
 * @Copyright 2016 hexing Inc. All rights reserved
 * @time：2016-4-12
 * @version FDM2.0
 */
public class ArchSynProcess implements ArchSynProcessInf{

	private static Logger logger = Logger.getLogger(ArchSynProcess.class.getName());
	private BaseDAOIbatis baseDAOIbatis;
	private String sqlId = "archSyn.";
	private static String sqlIdSurPlan = "surPlan."; //sql文件命名空间
	public BaseDAOIbatis getBaseDAOIbatis() {
		return baseDAOIbatis;
	}

	public void setBaseDAOIbatis(BaseDAOIbatis baseDAOIbatis) {
		this.baseDAOIbatis = baseDAOIbatis;
	}
	
	/**
	 * 保存同步过来的档案（MDC档案同步到FDM）
	 * @param inXML
	 */
	public String saveArch(String inXML) {
		//创建返回XML文档对象
		Document reDoc = DocumentHelper.createDocument();
		Element root = reDoc.addElement("R");
		//创建输入的XML文档对象
		Document document = null;
		ActionResult re = new ActionResult();
		try {
			document = XmlUtil.GetDocument(inXML);
			re = procArchSynXML(document);
			if(!re.isSuccess()) {
				root.addElement("C").addText(Constants.HHU_FDM_FAIL);
				root.addElement("MSG").addText(re.getMsg());
			} else {
				root.addElement("C").addText(Constants.HHU_FDM_SUCCESS);
				root.addElement("MSG").addText("");
			}
		} catch(Exception e) {
			logger.error(e.getMessage());
			root.addElement("C").addText(Constants.HHU_FDM_FAIL);
			root.addElement("MSG").addText(MsgCodeConstants.HX10001);//xml解析处理异常
		}
		return reDoc.getRootElement().asXML();
	}
	
	/**
	 * 处理档案同步的xml
	 * @param doc xml文档对象
	 * @return
	 */
	public ActionResult procArchSynXML(Document doc) {
		Map<String,String> sysDataMap = (Map<String,String>)AppEnv.getObject(Constants.SYS_DATAMAP);				
		ActionResult re = new ActionResult(true,"");
		List<Object> meterList = new ArrayList<Object>();//表列表
		List<Object> custList = new ArrayList<Object>();//用户列表
		List<Object> tfList = new ArrayList<Object>();//变压器列表
		String msns = "";//表号组合
		String cnos = "";//户号组合
		String tfIds = "";//变压器id组合
		try {
			Element root = doc.getRootElement();
			Element msNode = root.element("MS");//表档案根节点
			if(msNode != null) {
				msns = procMeterNode(msNode,sysDataMap,meterList);
			}
			Element csNode = root.element("CS");//用户档案根节点
			if(csNode != null) {
				cnos = procCustNode(csNode,custList);
			}
			Element tsNode = root.element("TS");//变压器档案根节点
			if(tsNode != null) {
				tfIds = procTFNode(tsNode,tfList);
			}
		} catch(Exception e) {
			re.setMsg(MsgCodeConstants.HX10001);//xml节点处理错误
			re.setSuccess(false);
		}
		try {
			//获取根单位id
			String uid = (String) baseDAOIbatis.queryForObject(sqlId + "getRootUID", null, String.class);
			storeCust(cnos,custList,uid);//存储用户数据
			storeTF(tfIds,tfList,uid);//存储变压器数据
			storeMeter(msns,meterList,uid);//存储表数据
			re.setSuccess(true);
		} catch(Exception e) {
			logger.error(e.getMessage());
			re.setMsg(MsgCodeConstants.HX10010);//数据库操作错误
			re.setSuccess(false);
		}
		return re;
	}
	
	/**
	 * 处理表节点，将表档案信息存入meterList中
	 * @param msNode 表根节点
	 * @param sysDataMap 系统固定数据映射参数
	 * @param meterList 表档案信息列表
	 * @return 表号（多个表号已逗号分隔）
	 */
	public String procMeterNode(Element msNode,Map<String,String> sysDataMap,List<Object> meterList) {
		//查询出存在未同步给MDC的表
		List<Object> unSyMDCMeterList = baseDAOIbatis.queryForList(sqlId + "getUnSyMDCMeter", Constants.PLN_TOMDC_UNSYNC);
		List<Element> mNodeList = msNode.elements("M");//获取表节点
		StringBuilder msnBuilder = new StringBuilder();//记录表号，多个表号用逗号分隔
		for(Element mNode : mNodeList) {
			Map<String,String> meterMap = new HashMap<String,String>();
			String msn = StringUtil.getValue(mNode.elementTextTrim("MN"));//表号
			String cno = StringUtil.getValue(mNode.elementTextTrim("CN"));//户号
			String cm = StringUtil.getValue(mNode.elementTextTrim("CM"));//接线方式
			String mt = StringUtil.getValue(mNode.elementTextTrim("MT"));//表计类型
			String mm = StringUtil.getValue(mNode.elementTextTrim("MM"));//表计模式
			String tn = StringUtil.getValue(mNode.elementTextTrim("TN"));//变压器id
			//物料编码，喀麦隆项目涉及
			String mat = StringUtil.getValue(mNode.elementTextTrim("MAT"));//物料编码
			boolean exist = false;//标志表号是否在FDM系统中存在未同步
			for(Object obj : unSyMDCMeterList) {
				Map<String,String> unSyMDCMeterMap = (Map<String, String>) obj;
				String nmsn = StringUtil.getValue(unSyMDCMeterMap.get("NMSN"));
				String omsn = StringUtil.getValue(unSyMDCMeterMap.get("OMSN"));
				if(!StringUtil.isEmptyString(nmsn) && msn.equals(nmsn)) {
					exist = true;
					break;
				} else if(!StringUtil.isEmptyString(omsn) && msn.equals(omsn)) {
					exist = true;
					break;
				}
			}
			if(exist) {//当前表号在FDM系统中存在未同步的表
				continue;
			}
			msnBuilder.append(msn).append(",");
			if(!StringUtil.isEmptyString(cno)) {//存在户号
				meterMap.put("cno", cno);
				meterMap.put("status", Constants.METER_STATUS_INS);//装出状态
			} else {//不存在户号
				meterMap.put("cno", null);
				meterMap.put("status", Constants.METER_STATUS_WAREH);//入库状态
			}
			if(!StringUtil.isEmptyString(cm)) {//存在接线方式
				meterMap.put("wir",StringUtil.getValue(sysDataMap.get("connection_method_" + cm)));
			}
			if(!StringUtil.isEmptyString(mt)) {//存在表计类型
				meterMap.put("mType", StringUtil.getValue(sysDataMap.get("meter_type_" + mt)));
			}
			if(!StringUtil.isEmptyString(mm)) {//存在表计模式
				meterMap.put("mMode", StringUtil.getValue(sysDataMap.get("meter_mode_" + mm)));
			}
			if(!StringUtil.isEmptyString(tn)) {//存在变压器id
				meterMap.put("tfid", tn);
			} else {
				meterMap.put("tfid", null);
			}
			meterMap.put("matCode", mat);
			meterMap.put("msn", msn);
			meterMap.put("mBox", StringUtil.getValue(mNode.elementTextTrim("MB")));
			meterMap.put("dataSrc", Constants.ARCH_SOURCE_INTFARCH);
			meterList.add(meterMap);
		}
		String msns = "";//表号组合
		if(msnBuilder.length() != 0) {
			msns = msnBuilder.toString();
			msns = msns.substring(0, msns.lastIndexOf(","));
		}
		return msns;
	}
	
	/**
	 * 处理用户节点，将用户档案信息存入custList中
	 * @param csNode 用户根节点
	 * @param custList 表档案信息列表
	 */
	public String procCustNode(Element csNode,List<Object> custList) {
		List<Element> cNodeList = csNode.elements("C");//获取表节点
		StringBuilder cnoBuilder = new StringBuilder();//记录户号，多个户号用逗号分隔
		for(Element cNode : cNodeList) {
			Map<String,String> custMap = new HashMap<String,String>();
			String cno = StringUtil.getValue(cNode.elementTextTrim("NO"));
			String bDate = StringUtil.getValue(cNode.elementTextTrim("BD"));
			if(StringUtil.isEmptyString(bDate) || bDate.equals("0")) {
				bDate = "1";
			}
			custMap.put("cno", cno);
			custMap.put("cname", StringUtil.getValue(cNode.elementTextTrim("CN")));
			custMap.put("addr", StringUtil.getValue(cNode.elementTextTrim("AD")));
			custMap.put("bDate", bDate);
			custMap.put("phone", StringUtil.getValue(cNode.elementTextTrim("TN")));
			custMap.put("dataSrc", Constants.ARCH_SOURCE_INTFARCH);
			custList.add(custMap);
			cnoBuilder.append(cno).append(",");
		}
		String cnos = cnoBuilder.toString();
		if(!StringUtil.isEmptyString(cnos)) {
			cnos = cnos.substring(0,cnos.lastIndexOf(","));
		} else {
			cnos = "";
		}
		return cnos;
	}
	
	/**
	 * 处理变压器节点，将变压器档案信息存入tfList中
	 * @param tsNode 变压器根节点
	 * @param tfList 变压器档案信息列表
	 */
	public String procTFNode(Element tsNode,List<Object> tfList) {
		List<Element> tNodeList = tsNode.elements("T");//获取表节点
		StringBuilder tfIdBuilder = new StringBuilder();//记录变压器id，多个变压器id用逗号分隔
		for(Element tNode : tNodeList) {
			Map<String,String> tfMap = new HashMap<String,String>();
			String tfId = StringUtil.getValue(tNode.elementTextTrim("ID"));
			tfMap.put("tfId", tfId);
			tfMap.put("tfName", StringUtil.getValue(tNode.elementTextTrim("TN")));
			tfMap.put("addr", StringUtil.getValue(tNode.elementTextTrim("AD")));
			tfMap.put("cap", StringUtil.getValue(tNode.elementTextTrim("CP")));
			tfMap.put("dataSrc", Constants.ARCH_SOURCE_INTFARCH);
			tfList.add(tfMap);
			tfIdBuilder.append(tfId).append(",");
		}
		String tfIds = tfIdBuilder.toString();
		if(!StringUtil.isEmptyString(tfIds)) {
			tfIds = tfIds.substring(0,tfIds.lastIndexOf(","));
		} else {
			tfIds = "";
		}
		return tfIds;
	}
	
	/**
	 * 存储表档案
	 * @param msns 同步过来的表号组合
	 * @param meterList 同步过来的表档案信息列表
	 * @param uid 根单位id
	 */
	public void storeMeter(String msns, List<Object> meterList, String uid) {
		List<Object> updMeterList = new ArrayList<Object>();//更新表档案列表
		List<Object> insMeterList = new ArrayList<Object>();//插入表档案列表
		List<Object> delMeterList = new ArrayList<Object>();//存储要删除的表档案列表
		List<Object> updCustList = new ArrayList<Object>();//更新用户状态
		Map<String,Object> map = new HashMap<String,Object>();
		if(!StringUtil.isEmptyString(msns)) {
			map.put("msns", msns.split(","));
		}
		//查询存在的表档案信息
		List<Object> existMeterList = baseDAOIbatis.queryForList(sqlId + "getMeter", map);
		if(existMeterList.size() > 0) {//存在的表档案信息
			for(Object obj : meterList) {
				Map<String,String> meterMap = (Map<String, String>) obj;
				String msn = StringUtil.getValue(meterMap.get("msn"));//表号
				String cno = StringUtil.getValue(meterMap.get("cno"));//户号
				String tfid = StringUtil.getValue(meterMap.get("tfid"));//变压器id
				String m_uid = "";
				boolean exist = existUPDMeterInfo(updMeterList,existMeterList,meterMap,m_uid,uid);
				if(!exist) {//当前表号在系统中不存
					if(StringUtil.isEmptyString(cno)) {//不存在户表关联
						m_uid = uid;
						meterMap.put("uid", uid);//根单位id
					} else {//存在户表关联,设置表单位id
						m_uid = setMeterUID(meterMap,cno,msn,uid,tfid);
					}
					if(StringUtil.isEmptyString(m_uid)) {
						delMeterList.add(meterMap);//记录要删除的表档案信息
					} else {
						insMeterList.add(meterMap);//添加档案信息到插入表列表中
					}
				}
				if(!StringUtil.isEmptyString(m_uid)) {
					//更新表号对应的用户档案信息
					updCustList.add(setCustUPDInfo(cno,msn,m_uid));
				}
			}
		} else {
			for(Object obj : meterList) {
				Map<String, String> meterMap = (Map<String, String>) obj;
				String msn = StringUtil.getValue(meterMap.get("msn"));//表号
				String cno = StringUtil.getValue(meterMap.get("cno"));//户号
				String tfid = StringUtil.getValue(meterMap.get("tfid"));//变压器id
				String m_uid = "";
				if(StringUtil.isEmptyString(cno)) {//不存在户表关联
					meterMap.put("uid", uid);//根单位id
				} else {//存在户表关联
					//设置表单位id
					m_uid = setMeterUID(meterMap,cno,msn,uid,tfid);
					if(StringUtil.isEmptyString(m_uid)) {//如果表所属单位为空
						delMeterList.add(meterMap);//记录要删除的表档案信息
						continue;
					}
				}
				//更新表号对应的用户档案信息
				updCustList.add(setCustUPDInfo(cno,msn,m_uid));
			}
			meterList.removeAll(delMeterList);
			insMeterList.addAll(meterList);
		}
		if(insMeterList.size() > 0) {
			//批量插入表档案
			baseDAOIbatis.executeBatch(sqlId + "insMeter", insMeterList);
		}
		if(updMeterList.size() > 0) {
			//批量更新表档案(不更新表档案状态)
			baseDAOIbatis.executeBatch(sqlId + "updMeter", updMeterList);
		}
		if(updCustList.size() > 0) {
			baseDAOIbatis.executeBatch(sqlId + "updCustStatus", updCustList);
		}
	}
	
	/**
	 * 存储用户档案
	 * @param cnos 同步过来的户号组合
	 * @param custList 同步过来的用户档案信息列表
	 * @param uid 根单位id
	 */
	public void storeCust(String cnos, List<Object> custList, String uid) {
		List<Object> updCustList = new ArrayList<Object>();//更新用户档案列表
		List<Object> insCustList = new ArrayList<Object>();//插入用户档案列表
		Map<String,Object> map = new HashMap<String,Object>();
		if(!StringUtil.isEmptyString(cnos)) {
			map.put("cnos", cnos.split(","));
		}
		//查询存在的用户档案信息
		List<Object> existCustList = baseDAOIbatis.queryForList(sqlId + "getCust", map);
		if(existCustList.size() > 0) {//已存在的用户档案信息
			for(Object obj : custList) {
				Map<String,String> custMap = (Map<String, String>) obj;
				String cno = StringUtil.getValue(custMap.get("cno"));
				boolean exist = true;
				for(Object existCustObj : existCustList) {
					Map<String,String> existCustMap = (Map<String, String>) existCustObj;
					String existCno = StringUtil.getValue(existCustMap.get("CNO"));
					if(cno.equals(existCno)) {//当前户号与系统中存在的户号相同
						updCustList.add(custMap);//将用户档案信息添加到更新用户列表中
						exist = true;
						break;
					} else {
						exist = false;//标记当前表号在与当前系统中存在的表不一致
					}
				}
				if(!exist) {//当前户号在与系统中存在的户号不一致
					custMap.put("uid", uid);//设置户号到根单位id下
					insCustList.add(custMap);//添加档案信息到插入用户列表中
				}
			}
		} else {//不存在户号，则默认户号单位id为根单位id
			for(Object obj : custList) {
				Map<String,String> custMap = (Map<String, String>) obj;
				custMap.put("uid", uid);
				insCustList.add(custMap);
			}
		}
		if(insCustList.size() > 0) {
			//批量插入用户档案
			baseDAOIbatis.executeBatch(sqlId + "insCust", insCustList);
		}
		if(updCustList.size() > 0) {
			//批量更新用户档案
			baseDAOIbatis.executeBatch(sqlId + "updCust", updCustList);
		}
	}
	
	/**
	 * 存储变压器档案
	 * @param tfIds 同步过来的变压器id组合
	 * @param tfList 同步过来的变压器档案信息列表
	 * @param uid 根单位id
	 */
	public void storeTF(String tfIds, List<Object> tfList, String uid) {
		List<Object> updTFList = new ArrayList<Object>();//更新变压器档案列表
		List<Object> insTFList = new ArrayList<Object>();//插入变压器档案列表
		Map<String,Object> map = new HashMap<String,Object>();
		if(!StringUtil.isEmptyString(tfIds)) {
			map.put("tfIds", tfIds.split(","));
		}
		//查询存在的变压器档案信息
		List<Object> existTFList = baseDAOIbatis.queryForList(sqlId + "getTF", map);
		if(existTFList.size() > 0) {//已存在的变压器档案信息
			for(Object obj : tfList) {
				Map<String,String> tfMap = (Map<String, String>) obj;
				String tfId = StringUtil.getValue(tfMap.get("tfId"));
				boolean exist = true;
				for(Object existTFObj : existTFList) {
					Map<String,String> existTFMap = (Map<String, String>) existTFObj;
					String existTFId = StringUtil.getValue(existTFMap.get("TFID"));
					if(tfId.equals(existTFId)) {//当前变压器id与系统中存在的变压器id相同
						updTFList.add(tfMap);//将变压器档案信息添加到更新变压器列表中
						exist = true;
						break;
					} else {
						exist = false;//标记当前变压器id与系统中存在的变压器id不一致
					}
				}
				if(!exist) {//当前变压器id在与系统中存在的变压器id不一致
					tfMap.put("uid", uid);
					insTFList.add(tfMap);//添加档案信息到插入变压器列表中
				}
			}
		} else {//默认设置变压器到根单位id下
			for(Object obj : tfList) {
				Map<String,String> tfMap = (Map<String, String>) obj;
				tfMap.put("uid", uid);
				insTFList.add(tfMap);
			}
		}
		if(insTFList.size() > 0) {
			//批量插入变压器档案
			baseDAOIbatis.executeBatch(sqlId + "insTF", insTFList);
		}
		if(updTFList.size() > 0) {
			//批量更新变压器档案
			baseDAOIbatis.executeBatch(sqlId + "updTF", updTFList);
		}
	}
	
	/**
	 * 设置表的单位id
	 * @param meterMap 表信息Map对象
	 * @param cno 户号
	 * @param msn 表号
	 * @param uid 根单位id
	 * @param tfid 变压器id
	 */
	public String setMeterUID(Map<String,String> meterMap, String cno, String msn, String uid, String tfid) {
		String m_uid = "";//表所属单位
		//获取当前用户信息
		List<Object> curCustList = baseDAOIbatis.queryForList(sqlId + "getCurrentCust", cno);
		//获取当前表对应的变压器信息
		List<Object> curTFList = baseDAOIbatis.queryForList(sqlId + "getCurrentTF", tfid);
		if(curTFList.size() ==1) {//存在当前变压器
			Map<String,String> curTFMap = (Map<String, String>) curTFList.get(0);
			String tfUID = StringUtil.getValue(curTFMap.get("UID"));
			if(curCustList.size() == 1) {//存在当前用户
				Map<String,String> curCustMap = (Map<String, String>) curCustList.get(0);
				String custUID = StringUtil.getValue(curCustMap.get("UID"));
				Map<String,String> p = new HashMap<String,String>();
				p.put("cno", cno);
				p.put("msn", msn);
				//查询用户是否绑定其他表计
				List<Object> otherMeterList = baseDAOIbatis.queryForList(sqlId + "existOMInCno", p);
				if(otherMeterList.size() >= 1) {//当前用户下存在其他表计
					if(custUID.equals(tfUID)) {//用户单位id跟变压器单位id相同，设置表挂在变压器所属单位下
						m_uid = custUID;
						meterMap.put("uid", custUID);
					} else {//不相同，则表不同步(通过设置表所属单位为空来判断)
						m_uid = "";
					}
				} else {
					m_uid = tfUID;
					meterMap.put("uid", tfUID);
				}
			} else {
				m_uid = tfUID;
				meterMap.put("uid", tfUID);
			}
		} else {
			m_uid = uid;
			meterMap.put("uid", uid);
		}
		return m_uid;
	}
	
	/**
	 * 设置用户更新信息
	 * @param cno 户号
	 * @param msn 表号
	 * @param m_uid 表对应的单位
	 * @return
	 */
	public Map<String,String> setCustUPDInfo(String cno, String msn, String m_uid) {
		Map<String,String> custMap = new HashMap<String,String>();
		if(!StringUtil.isEmptyString(cno)) {//户号非空，说明当前为装表情况，更新用户状态为已装表
			custMap.put("cno", cno);
			custMap.put("status", "1");
			custMap.put("uid", m_uid);
		} else{//户号为空，说明当前是拆表情况下
			//获取当前表号对应户号在系统中是否存在其他表
			List<Object> comCnoList = baseDAOIbatis.queryForList(sqlId + "getComCNO", msn);
			if(comCnoList.size() == 1) {//当用户下只挂了当前一只表
				Map<String,String> comCnoMap = (Map<String, String>) comCnoList.get(0);
				custMap.put("cno", StringUtil.getValue(comCnoMap.get("CNO")));
				custMap.put("status", "0");//设置用户状态为新开户
			}
		}
		return custMap;
	}
	
	/**
	 * 判断是否存在更新的表档案信息
	 * @param updMeterList 更新表档案信息列表
	 * @param existMeterList 系统中存在的表档案信息列表
	 * @param meterMap 同步过来的表档案Map对象
	 * @param m_uid 表所属单位
	 * @param uid 根单位
	 * @return
	 */
	public boolean existUPDMeterInfo(List<Object> updMeterList,List<Object> existMeterList,
			Map<String,String> meterMap, String m_uid, String uid) {
		boolean exist = true;
		String msn = StringUtil.getValue(meterMap.get("msn"));//表号
		String cno = StringUtil.getValue(meterMap.get("cno"));//户号
		String tfid = StringUtil.getValue(meterMap.get("tfid"));//变压器id
		for(Object existMeterObj : existMeterList) {
			Map<String,String> existMeterMap = (Map<String, String>) existMeterObj;
			String existMsn = StringUtil.getValue(existMeterMap.get("MSN"));
			if(msn.equals(existMsn)) {//当前表号与系统中存在的表号相同
				if(!StringUtil.isEmptyString(cno)) {
					m_uid = setMeterUID(meterMap,cno,msn,uid,tfid);//设置表所属单位
					if(StringUtil.isEmptyString(m_uid)) {//表所属单位为空
						exist = true;
						break;
					}
				} else {
					m_uid = uid;
					meterMap.put("uid", uid);
				}
				updMeterList.add(meterMap);//将表档案信息添加到更新表列表中
				exist = true;
				break;
			} else {
				exist = false;//标记当前表号在与当前系统中存在的表不一致
			}
		}
		return exist;
	}

	@Override
	public String saveReqest(String inXML) {
		//创建返回XML文档对象
		Document reDoc = DocumentHelper.createDocument();
		Element root = reDoc.addElement("CUSTS");
		Element cust = root.addElement("CUST");
		//创建输入的XML文档对象
		Document document = null;
		ActionResult re = new ActionResult();
		try {
			document = XmlUtil.GetDocument(inXML);
			re = procRequestXML(document);
			if(!re.isSuccess()) {
				cust.addElement("CODE").addText(Constants.HHU_FDM_FAIL);
				cust.addElement("MSG").addText("fail");
			} else {
				cust.addElement("CODE").addText(Constants.HHU_FDM_SUCCESS);
				cust.addElement("MSG").addText("success");
			}
		} catch(Exception e) {
			logger.error(e.getMessage());
			cust.addElement("CODE").addText(Constants.HHU_FDM_FAIL);
			cust.addElement("MSG").addText(MsgCodeConstants.HX10001);//xml解析处理异常
		}
		return reDoc.getRootElement().asXML();
	}
	private ActionResult procRequestXML(Document doc){
		ActionResult re = new ActionResult();
		try {
			Element root = doc.getRootElement();
			Element custsNode = root.element("CUST");
			String hh = custsNode.elementText("HH");
			String hm = custsNode.elementText("HM");
			String lxfs = custsNode.elementText("LXFS");
			String dz = custsNode.elementText("DZ");
			
			//生成勘探计划
			Map<String,String> parmam = new HashMap<String,String>();
			parmam.put("cno", hh);
			parmam.put("cname", hm);
			parmam.put("mno", lxfs);
			parmam.put("addr", dz);
			parmam.put("status", Constants.PLN_STATUS_UNHANDLED);
			String pid = (String) baseDAOIbatis.queryForObject(sqlIdSurPlan + "getPID", null, String.class);
			//安装计划id格式：P00000000000001
			parmam.put("pid", "P" + StringUtil.leftZero(pid, 14));
			parmam.put("CURR_STAFFID", "admin");
			baseDAOIbatis.insert(sqlIdSurPlan+"insAddSurPln", parmam);
			baseDAOIbatis.insert(sqlIdSurPlan+"insPlnOPLog", parmam);
			re.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage());
			re.setSuccess(false);
		}
		return re;
	}
}
