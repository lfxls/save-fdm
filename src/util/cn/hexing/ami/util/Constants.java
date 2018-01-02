package cn.hexing.ami.util;

import java.io.Serializable;

/**
 * 用来存放全局常量
 */
public class Constants implements Serializable {
	private static final long serialVersionUID = 2678610616326904827L;
	/** 登录语言 struts2 **/
	public static final String APP_LOCALE = "I18N_LANG";
	/** 登录语言 **/
	public static final String APP_LANG = "appLang";
	/** 登录单位代码 **/
	public static final String UNIT_CODE = "UNIT_CODE";
	/** 登录单位级别 **/
	public static final String UNIT_LEVEL = "UNIT_LEVEL";
	/** 登录单位名称 **/
	public static final String UNIT_NAME = "UNIT_NAME";
	/** 登录操作员 **/
	public static final String CURR_STAFF = "CURR_STAFF";
	/** 登录操作员 **/
	public static final String CURR_SESSIONID = "CURR_SESSIONID";
	/** 登录操作员ID **/
	public static final String CURR_STAFFID = "CURR_STAFFID";
	/** 登录操作部门ID **/
	public static final String CURR_DEPT = "CURR_DEPT";
	/** 登录操作权限 **/
	public static final String CURR_RIGTH = "CURR_RIGTH";
	/** 登录操作IP **/
	public static final String CURR_IP = "CURR_IP";
	/** 超级管理员账号**/
	public static final String SUPER_ADMIN="admin";
	/** 登录操作员角色列表，以逗号分开**/
	public static final String USER_ROLES="user_roles";
	/** 通过菜单授权，无角色**/
	public static final String NULL_ROLES="null_roles";
	public static final String CU = "CU";
	public static final String UCM = "UCM";
	//Grid支持导出的格式列表，逗号分隔
	public static final String GRIDDOC="gridExportDoc";
	
	// grid用
	public static String TMPEFILE; // 零时文件目录
	public static int PAGESIZE; // 部分grid显示分页条数（默认30）

	/*-----------------页面操作类型列表----------------------------*/
	public static final String ADDOPT = "01";
	public static final String UPDOPT = "02";
	public static final String DELOPT = "03";
	//参数分类管理
	public static final String ADDOPT_CATE = "04";
	public static final String UPDOPT_CATE = "05";
	public static final String DELOPT_CATE = "06";

	// 用户类型
	public static final String ZBYH = "01"; // 专变
	public static final String GBYH = "02"; // 公变
	public static final String DYYH = "03"; // 低压
	public static final String CZYH = "04"; // 厂站

	/*-----------------档案状态----------------------------*/
	// 用户 新开户、已装表、已投运、已注销。
	public static final String YHZT_KH = "01";
	public static final String YHZT_ZB = "02";
	public static final String YHZT_TY = "03";
	public static final String YHZT_ZX = "04";

	// 表计 入库、装出、投入、拆回
	public static final String BJZT_RK = "01";
	public static final String BJZT_ZC = "02";
	public static final String BJZT_TY = "03";
	public static final String BJZT_CH = "04";

	// 终端 入库、待投（任务未投成功）、投运、拆回
	public static final String ZDZT_RK = "01";
	public static final String ZDZT_DT = "02";
	public static final String ZDZT_TY = "03";
	public static final String ZDZT_CH = "04";

	// 采集器 入库、投运、拆回
	public static final String CJQZT_RK = "01";
	public static final String CJQZT_TY = "03";
	public static final String CJQZT_CH = "04";
	
	//角色菜单缓存id
	public static final String SYS_ROLE_MENUS = "sys_role_menus";
	//菜单操作缓存id
	public static final String SYS_MENUS_OPT = "sys_menus_opt";
	//系统级分隔符号
	public static final String SYS_SPLIT_STRING = "&nics;";
	
	//系统级分隔符号 二级
	public static final String SYS_SPLIT_STRING_L2 = "&nics:";// 二级
	//系统配置参数对象
	public static final String SYS_PARAMMAP = "sys_parammap";
	//系统配置参数数据映射
	public static final String SYS_DATAMAP = "sys_datamap";
	//系统P_CODE基础数据对象
	public static final String SYS_PCODEMAP = "sys_basicmap";
	//预付费模式
	public static final String PREPAYMODE="prepayMode";
	
	//首页类型：系统默认
	public static final String SY_XTMR="01";
	//首页类型：其他首页
	public static final String SY_QT="02";
	//485表
	public static final String BJZT_LX_485 = "01";
	//GPRS表
	public static final String BJZT_LX_GPRS = "02";
	//虚拟表
	public static final String BJZT_LX_XNB = "03";
	//PLC
	public static final String BJZT_LX_PLC = "04";
	//预付费离线表
	public static final String BJZT_LX_P_LXB = "20";
	
	//伊朗日历类型
	public static final String CALENDAR_TYPE="iran";
	
	//规约类型 浙规
	public static final String GYLX_ZG="01";
	//规约类型 国网
	public static final String GYLX_GW="02";
	//规约类型 DLMS
	public static final String GYLX_DLMS="03";
	//规约类型 广规
	public static final String GYLX_GG="04";
	//规约类型 HX645
	public static final String GYLX_HX645="05";
	//规约类型 ANSI
	public static final String GYLX_ANSI="06";
	
	//电表规约类型 DL/T645_1997
	public static final String DBGYLX_645_97="01";
	//电表规约类型 DL/T645_2007
	public static final String DBGYLX_645_2007="02";
	//电表规约类型 HX645
	public static final String DBGYLX_HX645="05";
	//规约类型 ANSI
	public static final String DBGYLX_ANSI="07";
	
	/*****************档案*******************************/
	//线路
	public static final String DA_XL="XL";
	//台区
	public static final String DA_TQ="TQ";
	//变压器
	public static final String DA_BYQ="BYQ";
	//表计 485
	public static final String DA_BJ_485="485M";
	//表计 GPRS
	public static final String DA_BJ_GPRS="GPRSM";
	//表计 离线表
	public static final String DA_BJ_OLM="OLM";
	public static final String DA_BJ_485_PLC_RF_CT_UO = "485-PLC-RF-CT-UO"; 
	//用户
	public static final String DA_YH="YH";
	//终端
	public static final String DA_ZD="ZD";
	//采集器
	public static final String DA_CJQ="CJQ";
	//G4终端
	public static final String DA_GZD="GZD";
	//G4采集器
	public static final String DA_GCJQ="GCJQ";
	//sim卡
	public static final String DA_SIM="SIM";
	//户表关联
	public static final String DA_HBGL="HBGL";
	//投运终端
	public static final String DA_TYZD="TYZD";
	//集中器档案入库
	public static final String DA_JZQRK="JZQRK";
	//表计档案入库
	public static final String DA_BJRK="BJRK";
	//MDM表计档案入库
	public static final String DA_BJRK_MDM="BJRKMDM";
	//一键导入485
	public static final String ONESTEP_485="ONESTEP485";
	//一键导入GPRS
	public static final String ONESTEP_GPRS="ONESTEPGPRS";
	//水表
	public static final String DA_SB="SB";
	//气表
	public static final String DA_QB="QB";
	//扁平化 表计档案
	public static final String DA_BPH_BJ="BPH_BJ";
	//扁平化 集中器
	public static final String DA_BPH_JZQ="BPH_JZQ";
	//预付费离线表
	public static final String DA_BJ_YFFLXB_CODE="20";
	//PLC表
	public static final String DA_BJ_PLCB_CODE="04";
	//GPRS表
	public static final String DA_BJ_GPRS_CODE="02";
	
	//英语默认
	public static final String LOCALE_EN="en_US";
	//中文默认
	public static final String LOCALE_ZH="zh_CN";
	//阿塞拜疆默认
	public static final String LOCALE_AZ="az_AZ";
	
	//预付费介质  IC卡
	public static final String YFFJZ_IC="01";
	//预付费介质  keypad
	public static final String YFFJZ_KEYPAD="02";
	
	//系统配置参数p_syspara
	public static final String SYSPARA = "syspara";
	//邮件服务器配置信息
	public static final String MAILPARA = "mailPara";
	
	//STS加密孟加拉模式
	public static final String STS_BENGAL="03";
	
	//2010版本不加密伊朗老表，子规约号101写死
	public static final String DLMS_IRAN_101="101";
	
	//测量点类型 表计 01
	public static final String CLDLX_BJ="01";
	//测量点类型 采集器 02
	public static final String CLDLX_CJQ="02";
	//测量点类型 CT表计 03
	public static final String CLDLX_CTBJ="03";
	//测量点类型 终端本身 05
	public static final String CLDLX_SELF="05";
	
	//默认厂商类型：hexing
	public static final String HEXING="14"; 
	
	//小型预付费下，默认当前用户的第一个变压器信息
	public static final String FIRST_TRANS_ID = "first_trans_id";
	
	//测量点规约类型-国网集中器 07表
	public static final String GWJZQ_CLD_07="30";
	//测量点规约类型-国网集中器 97表
	public static final String GWJZQ_CLD_97="1";
	//测量点规约类型-国网集中器 DLMS标准表
	public static final String GWJZQ_CLD_DLMSS="50";
	
	/*---------------license相关---------------------------*/
	//系统模式 小型预付费
	public static final String SYSTEM_MODE_S="1";
	//系统模式 正常模式
	public static final String SYSTEM_MODE_N="0";
	//授权天数 -1为不超期
	public static final String LICENSE_DAYS = "LICENSE_DAYS";
	//系统启动时间
	public static final String LICENCE_SYS_STARTDATE = "LICENCE_SYS_STARTDATE";
	//电表最新支持数量
	public static final String LICENCE_METER_NUMBER="meterNum";
	//系统过期时间
	public static final String LICENCE_EXPDATE = "LICENSE_EXPDATE";
	
	/*---------------小型预付费相关--------------------------------*/
	//费率索引ID
	public static final String S_INDEX_ID="9999";
	//费率方案
	public static final String S_TARIFF_P_ID="9999";
	//小型预付费模式变量
	public static final String SYS_MODE="sysMode";
	
	/**区域标识 孟加拉**/
	public static String AREAID_BENGAL = "bengal";
	/**区域标识 摩洛哥**/
	public static String AREAID_MOROCCO = "morocco";
	/**区域标识 南非**/
	public static String AREAID_NEWCASTLE = "newcastle";
	/**区域标识 泰国路灯**/
	public static String AREAID_THAILIFHTS = "thaiLights";
	/** 掌机下发数据DES 密钥**/
	public static String HHU_DES_KEY = "HHU_DES_KEY";
	
	/**是否使用POS机**/
	public static String USEPOSMACH = "usePosMach";
	/**是否使用射频卡**/
	public static String USERFCARD = "useRfCard";
	
	/**系统模式  0=正常模式 1=小型预付费 2=DTM(公变监测)**/
	public static String SYSMODE_NORMAL = "0";
	public static String SYSMODE_MINI = "1";
	public static String SYSMODE_DTM = "2";
	
	/**标准规约 0=标准规约**/
	public static String PROTOCOL_S = "0";
	
	/**通信模式 0=GPRS 1=CSD 2=GPRS&CSD **/
	public static String REQMODE_GPRS = "0";
	public static String REQMODE_CSD = "1";
	public static String REQMODE_GPRSCSD = "2";
	//Modbus表计
	public static final String BJLX_Modbus="10";
	
	/**短信类型 接收**/
	public static String SM_TYPE_INBOUND = "I";
	/**短信类型 发送状态返回**/
	public static String SM_TYPE_STATUSRM = "S";
	/**短信类型 发送**/
	public static String SM_TYPE_OUTBOUND = "O";
	/**短信类型 WAP**/
	public static String SM_TYPE_WAPSI = "W";
	
	/**状态报告短信 不产生**/
	public static String STATUS_REPORT_NO = "0";
	/**状态报告短信 产生**/
	public static String STATUS_REPORT_YES = "1";
	
	/**短信编码类型 7bit**/
	public static String SM_ENCODETYPE_7 = "7";
	/**短信编码类型 8bit**/
	public static String SM_ENCODETYPE_8 = "8";
	/**短信编码类型 Unicode/UCS2**/
	public static String SM_ENCODETYPE_U = "U";
	
	/**短信优先级 低**/
	public static String SM_PRIORITY_LOW = "-1";
	/**短信优先级 正常**/
	public static String SM_PRIORITY_NORMAL = "0";
	/**短信优先级 高**/
	public static String SM_PRIORITY_HIGH = "1";
	
	/**短信状态 未发送**/
	public static String SM_STATUS_UNSENT = "U";
	/**短信状态 排队**/
	public static String SM_STATUS_QUEUED = "Q";
	/**短信状态 已发送**/
	public static String SM_STATUS_SENT = "S";
	/**短信状态 发送失败**/
	public static String SM_STATUS_FAILED = "F";
	/**短信状态 已接收**/
	public static String SM_STATUS_DELIVERED = "D";
	/**短信状态 挂起**/
	public static String SM_STATUS_PENDING = "P";
	/**短信状态 中断**/
	public static String SM_STATUS_ABORTED = "A";
	
	/**短信状态 已处理**/
	public static String SM_PROCESS_YES = "1";
	/**短信状态 未处理**/
	public static String SM_PROCESS_NO = "0";
	/**销户行为*/
	public static String ACTION_ACCOUNT_LOGOFF = "1";
	
	//日冻结 dlms
	public static String DLMS_SORT_RDJ="rdj";
	//月冻结 dlms
	public static String DLMS_SORT_YDJ="ydj";
	//dlms表接入modbus设备 无功补偿设备 读取
	public static String DLMS_SORT_MODBUSRC_R="modbusRc_R";
	//dlms表接入modbus设备 无功补偿设备 设置
	public static String DLMS_SORT_MODBUSRC_S="modbusRc_S";
	
	/**虚拟终端*/
	public static String TERMINAL_GPRS="05";
	/**集中器*/
	public static String TERMINAL_CONCENTOR="03";
	
	/**操作员状态*/
	//正常
	public static String ACCOUNTSTATUS_NORMAL = "0";
	//注销
	public static String ACCOUNTSTATUS_LOGOFF = "1";
	//锁定
	public static String ACCOUNTSTATUS_LOCKED = "2";
	
	//翻页参数 
	public static int PAGE_START=0;
	public static int PAGE_END=30;
	
	//厂商编码
	public static String FACTORYCODE_HEXING = "14";
	
	//默认语言
	public static String DEFAULT_LANG="en_US";
	//系统模式
	public static String SYSMODE_MDC = "01";
	public static String SYSMODE_PREPAY = "02";
	public static String SYSMODE_MDM = "03";
	
	//操作员的状态
	//在职
	public static String OPERATOR_STATUS_ENABLE = "0";
	//注销
	public static String OPERATOR_STATUS_LOGOFF = "1";
	//锁定
	public static String OPERATOR_STATUS_LOCK = "2";
	
	//数据类型 日冻结
	public static String DATATYPE_DAYFRO = "01";
	//数据类型 月冻结
	public static String DATATYPE_MONFRO = "02";
	//数据类型 负荷
	public static String DATATYPE_LOAD = "03";
	//数据类型 预付费
	public static String DATATYPE_PREPAY = "04";
	//数据类型 抄表日
	public static String DATATYPE_BILLING = "05";
	
	//数据字段类型 日期
	public static String DATAFIELDTYPE_DATE = "01";
	//数据字段类型 数字
	public static String DATAFIELDTYPE_NUM = "02";
	//数据字段类型 字符串
	public static String DATAFIELDTYPE_STR = "03";
	//数据字段类型 表号
	public static String DATAFIELDTYPE_METERNO = "04";
	//数据字段类型 数据时间
	public static String DATAFIELDTYPE_DATADATE = "05";
	
	//文件导入结果 文件导入成功
	public static String DATAIMPRESULT_IMPSUCC = "01";
	//文件导入结果 文件解析失败
	public static String DATAIMPRESULT_IMPFAIL = "02";
	
	//数据导入导出进程类型 单次导入
	public static String PROTYPE_IMPSING = "01";
	//数据导入导出进程类型 单次导出
	public static String PROTYPE_EXPSING = "02";
	//数据导入导出进程类型 定时导入
	public static String PROTYPE_IMPSCHE = "03";
	//数据导入导出进程类型 定时导出
	public static String PROTYPE_EXPSCHE = "04";
	
	//数据导入导出进程状态 执行成功
	public static String PROSTATUS_SUCCESS = "01";
	//数据导入导出进程状态 执行失败
	public static String PROSTATUS_FAIL = "02";
	//数据导入导出进程状态 正在运行
	public static String PROSTATUS_RUNNING = "03";
	
	//数据格式类型 导入
	public static String DATAFORMATTYPE_IMP = "0";
	//数据格式类型 导出
	public static String DATAFORMATTYPE_EXP = "1";
	
	//数据格式状态 启用
	public static String DATAFORMATSTAT_ENABLE = "0";
	//数据格式状态 禁用
	public static String DATAFORMATSTAT_DISABLE = "1";
	
	//数据导出结果 成功
	public static String EXPRESULT_SUCC = "01";
	//数据导出结果 失败
	public static String EXPRESULT_FAIL = "02";
	
	//数据导入导出方式 本地
	public static String FILEIEPTYPE_LOCAL = "01";
	//数据导入导出方式 FTP
	public static String FILEIEPTYPE_FTP = "02"; 
	
	//设备安装计划  新装表导入
	public static String IMPORT_INSP_ADD_METER = "addMeter"; 
	//设备安装计划  换表导入
	public static String IMPORT_INSP_CHANGE_METER = "changeMeter"; 
	//设备安装计划  拆表导入
	public static String IMPORT_INSP_REMOVE_METER = "removeMeter"; 
	
	//设备安装计划  新装集中器导入
	public static String IMPORT_INSP_ADD_DCU = "addDcu"; 
	//设备安装计划  换集中器导入
	public static String IMPORT_INSP_CHANGE_DCU = "changeDcu"; 
	//设备安装计划  拆集中器导入
	public static String IMPORT_INSP_REMOVE_DCU = "removeDcu"; 
	
	//设备安装计划  新装采集器导入
	public static String IMPORT_INSP_ADD_COLL = "addCol"; 
	//设备安装计划  换采集器导入
	public static String IMPORT_INSP_CHANGE_COLL = "changeCol"; 
	//设备安装计划  拆采集器导入
	public static String IMPORT_INSP_REMOVE_COLL = "removeCol"; 
	
	//Token管理  新增Token导入
	public static String IMPORT_PRE_ADD_TOKEN = "addToken"; 
	
	//勘察
	public static String IMPORT_INSP_SRVY="addSrvy";
	
	//表型号 默认值
	public static String BXH_DEFAULT = "hexing";
	
	public static String DATABASETYPE_ORACLE="oracle";
	public static String DATABASETYPE_MYSQL="mysql";
	
	//数据类型 变压器
	public static String DATAUPDATE_DATATYPE_TF = "0";
	//数据类型 参数方案
	public static String DATAUPDATE_DATATYPE_PARAMSOL = "1";
	//数据类型 基础代码
	public static String DATAUPDATE_DATATYPE_CODE = "2";
	//数据类型 TOKEN
	public static String DATAUPDATE_DATATYPE_TOKEN = "3";
	
	//数据操作类型 新增
	public static String DATAUPDATE_OPTYPE_NEW = "0";
	//数据操作类型 修改
	public static String DATAUPDATE_OPTYPE_UPT = "1";
	//数据操作类型 删除
	public static String DATAUPDATE_OPTYPE_DEL = "2";
	
	//数据状态 待更新
	public static String DATAUPDATE_DATASTAT_WAITUPT = "0";
	//数据状态 已完成
	public static String DATAUPDATE_DATASTAT_FINISH = "1";
	
	//日志状态 未知
	public static String DATAUPDATE_LOGSTAT_UNKNOW = "0";
	//日志状态 成功
	public static String DATAUPDATE_LOGSTAT_SUCC = "1";
	//日志状态 失败
	public static String DATAUPDATE_LOGSTAT_FAIL = "2";
	
	//参数类型 读取
	public static String PMSOL_PMTYPE_READ = "0";
	//参数类型设置
	public static String PMSOL_PMTYPE_SET = "1";
	//参数类型 测试
	public static String PMSOL_PMTYPE_TEST = "2";
	
	//树类型 单位树
	public static String TREETYPE_POWERUTILITY = "dw";
	
	//HHU数据更新方式 全量
	public static String DATAUPDATE_UPTWAY_FULL = "0";
	//HHU数据更新方式 增量
	public static String DATAUPDATE_UPTWAY_INC = "1";
		
	//HHU初始化状态 已初始化
	public static String HHU_INIT_YES = "0";
	//HHU初始化状态 未初始化
	public static String HHU_INIT_NO = "1";
		
	//工单日志操作类型 上传
	public static String WO_LOG_UPLOAD = "0";
	//工单日志操作类型 下载
	public static String WO_LOG_DOWNLOAD = "1";
	
	//工单日志操作结果 未知
	public static String WO_LOG_STATUS_UNKNOW = "0";
	//工单日志操作结果 成功
	public static String WO_LOG_STATUS_SUCC = "1";
	//工单日志操作结果 失败
	public static String WO_LOG_STATUS_FAIL = "2";
	
	//HHU和FDM之间操作结果 成功
	public static String HHU_FDM_SUCCESS = "1";
	//HHU和FDM之间操作结果 失败
	public static String HHU_FDM_FAIL = "0";
	
	//工单状态 未处理
	public static String WO_STATUS_UNHANDLED = "0";
	//工单状态 已分配
	public static String WO_STATUS_ASSIGNED = "1";
	//工单状态 已派工
	public static String WO_STATUS_DISPATCHED = "2";
	//工单状态 待撤销
	public static String WO_STATUS_REVOKING = "3";
	//工单状态 已撤销
	public static String WO_STATUS_REVOKED = "4";
	//工单状态 已反馈
	public static String WO_STATUS_FEEDBACKED = "5";
	//工单状态 已完成
	public static String WO_STATUS_COMPLETED = "6";
	
	//工单类型 有序
	public static String WO_TYPE_ORDER = "0";
	//工单类型 无序
	public static String WO_TYPE_UNORDER = "1";
	//工单类型 现场新增
	public static String WO_TYPE_NEW = "2";
	//工单类型 勘察
	public static String WO_TYPE_SRVY = "3";
	
	//工单操作类型 新增
	public static String WO_OPTYPE_NEW = "0";
	//工单操作类型 撤销
	public static String WO_OPTYPE_REVOKE = "1";
	
	//安装计划状态 未处理
	public static String PLN_STATUS_UNHANDLED = "0";
	//安装计划状态 已分配
	public static String PLN_STATUS_ASSIGNED = "1";
	//安装计划状态 已派工
	public static String PLN_STATUS_DISPATCHED = "2";
	//安装计划状态 成功
	public static String PLN_STATUS_SUCCESS = "3";
	//安装计划状态 异常
	public static String PLN_STATUS_ABNORMAL = "4";
	//安装计划状态 失败
	public static String PLN_STATUS_FAIL = "5";
	//安装计划状态 已缴费
	public static String PLN_STATUS_PAID = "6";
	//安装计划状态 取消缴费
	public static String PLN_STATUS_UNPAID = "7";
	
	//安装计划反馈操作结果 成功
	public static String PLN_FB_SUCCESS = "0";
	//安装计划反馈操作结果 异常
	public static String PLN_FB_ABNORMAL = "1";
	//安装计划反馈操作结果 失败
	public static String PLN_FB_FAIL = "2";
	
	//安装计划业务类型 新装
	public static String PLN_BUSSTYPE_INSTALLATION = "0";
	//安装计划业务类型 更换
	public static String PLN_BUSSTYPE_REPLACEMENT = "1";
	//安装计划业务类型 拆回
	public static String PLN_BUSSTYPE_UNINSTALLATION = "2";
	
	//表状态 入库
	public static String METER_STATUS_WAREH = "0";
	//表状态 装出
	public static String METER_STATUS_INS = "1";
	//表状态 拆回
	public static String METER_STATUS_REM = "2";
	
	//安装计划反馈数据同步MDC状态 未同步
	public static String PLN_TOMDC_UNSYNC = "0";
	//安装计划反馈数据同步MDC状态 已同步
	public static String PLN_TOMDC_SYNC = "1";
	//安装计划反馈数据同步MDC状态 同步失败
	public static String PLN_TOMDC_SYNCFAIL = "2";
	
	//HHU状态 有效
	public static String HHU_STATUS_ENABLED = "01";
	//HHU状态 无效
	public static String HHU_STATUS_DISABLED = "02";
	//HHU状态 维修
	public static String HHU_STATUS_REMOVE = "03";
	
	//用户派工状态 未处理
	public static String CUST_DISPSTATUS_UNHANDLED = "0";
	//用户派工状态 已派工
	public static String CUST_DISPSTATUS_DISPATCHED = "1";
	
	//设备类型 表
	public static String DEV_TYPE_METER = "0";
	//设备类型 集中器
	public static String DEV_TYPE_DCU = "1";
	//设备类型 采集器
	public static String DEV_TYPE_COLLECTOR = "2";
	//设备类型 未知
	public static String DEV_TYPE_UNKNOW = "3";
	
	//档案来源 接口同步
	public static String ARCH_SOURCE_INTFARCH = "0";
	//档案来源 FDM录入
	public static String ARCH_SOURCE_FDMINPUT = "1";
	//档案来源 现场反馈
	public static String ARCH_SOURCE_LOCALFB = "2";
	
	//用户状态 新用户
	public static String CUST_STATUS_NEW = "0";
	//用户状态 已装表用户
	public static String CUST_STATUS_INS = "1";
	//用户状态 注销用户
	public static String CUST_STATUS_LOGOFF = "2";
	
	//解析VENDING系统传入xml 唯一标识节点不对
	public static String VENDING_XML_SNODE_NOTRIGHT = "-1";
	//解析VENDING系统传入xml 数据解析异常
	public static String VENDING_XML_ANA_EXCEPTION = "-2";
	
	//VENDING系统缴费状态 取消缴费
	public static String VENDING_FDM_UNPAID = "1";
	//VENDING系统缴费状态 已缴费
	public static String VENDING_FDM_PAID = "2";
	
	//勘察计划反馈数据同步VENDING状态 未同步
	public static String PLN_TOVENDING_UNSYNC = "0";
	//勘察计划反馈数据同步VENDING状态 已同步
	public static String PLN_TOVENDING_SYNC = "1";
	//勘察计划反馈数据同步VENDING状态 同步失败
	public static String PLN_TOVENDING_SYNCFAIL = "2";
}
