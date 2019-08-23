package com.fxbank.cap.ykwm.trade.esb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.dcfs.esb.ftp.client.FtpClientConfigSet;
import com.dcfs.esb.ftp.client.FtpGet;
import com.dcfs.esb.ftp.server.error.FtpException;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.cip.base.util.FtpUtil;
import com.fxbank.cap.esb.model.ses.ESB_REP_50015000101;
import com.fxbank.cap.esb.model.ses.ESB_REQ_50015000101;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.ykwm.dto.esb.REP_30062001201;
import com.fxbank.cap.ykwm.dto.esb.REQ_30062001201;
import com.fxbank.cap.ykwm.exception.YkwmTradeExecuteException;
import com.fxbank.cap.ykwm.model.CheckErrorModel;
import com.fxbank.cap.ykwm.model.DayCheckLogInitModel;
import com.fxbank.cap.ykwm.model.YkwmTraceLogModel;
import com.fxbank.cap.ykwm.service.ICheckErrorService;
import com.fxbank.cap.ykwm.service.IDayCheckLogService;
import com.fxbank.cap.ykwm.service.IPaymentService;

import redis.clients.jedis.Jedis;

@Service("REQ_30062001201")
public class PY_Check extends TradeBase implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(PY_Check.class);

	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private IDayCheckLogService dayCheckLogService;
	
	@Reference(version = "1.0.0")
	private ICheckErrorService checkErrorService;
	
	@Reference(version = "1.0.0")
	IPaymentService paymentService;
	
	@Resource
	private MyJedis myJedis;

	private final static String COMMON_PREFIX = "ykwm.";
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30062001201 reqDto = (REQ_30062001201)dto;
		REP_30062001201 repDto = new REP_30062001201();
		String date = reqDto.getReqBody().getCollateDt();
		myLog.info(logger, "核心与外围对账开始");
		checkErrorService.delete(date);
		List<DayCheckLogInitModel> checkLogList = getCheckLogList(myLog,dto,date);
		checkTraceLog(myLog, dto, checkLogList);
		myLog.info(logger, "核心与外围对账结束");
		myLog.info(logger, "外围与核心对账开始");
		//获取未对账的缴费记录
		List<YkwmTraceLogModel> traceList = paymentService.getCheckTrace(myLog,dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno(), date);
		for(YkwmTraceLogModel model:traceList) {
			YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, model.getSysDate(), model.getSysTime(), model.getSysTraceno());
			record.setCapResult("4");
			paymentService.updateCheck(record);
			
			if(model.getCoResult().equals("1")) {
				myLog.error(logger,"营口热电【"+date+"】对账失败: 多出记录，渠道流水号【"+model.getSysTraceno()+"】，核心状态【"+model.getCoResult()+"】");
				YkwmTradeExecuteException e = new YkwmTradeExecuteException(YkwmTradeExecuteException.YKWM_E_10006);
				throw e;
			}else {
				myLog.info(logger, "渠道多出往账数据，渠道日期【"+model.getSysDate()+"】，渠道流水【"+model.getSysTraceno()+"】，核心状态【"+model.getCoResult()+"】");
			}
		}
		myLog.info(logger, "外围与核心对账结束");
		
		StringBuffer sb = new StringBuffer();
		//首行:总笔数|总金额|公司名称|批次号
		String totalNum = paymentService.getTotalNum(date);
		String totalAmt = paymentService.getTotalAmt(date);
		sb.append(totalNum).append("|");
		sb.append(totalAmt).append("|");
		String companyName = "";
		try (Jedis jedis = myJedis.connect()) {
			companyName = jedis.get(COMMON_PREFIX + "company_name");
		}
		sb.append(companyName).append("|");
		sb.append(date).append("|");
		sb.append("\n"); 
		List<YkwmTraceLogModel> upSndTraceList = paymentService.getUploadCheckSndTrace(myLog, dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno(), date);
		for(YkwmTraceLogModel model : upSndTraceList) {
			sb.append(model.getUserCardNoT()).append("|"); //用户卡号
		    sb.append(model.getSysDate()).append("|");//批次号
		    sb.append(model.getTeCheckNum()).append("|");//流水号
		    sb.append(model.getPyFeeAmtT()).append("|");//交费金额
		    sb.append(model.getBillGetTpT()).append("|");//发票处理方式
		    sb.append(model.getCourierCmpnyIdT()).append("|");//快递公司ID
		    sb.append(model.getMailAddrT()).append("|");//邮寄地址
		    sb.append(model.getCnttPhnT()).append("|");//联系电话
		    sb.append(model.getLnmT3()).append("|");//联系人
		    sb.append(model.getPostNoT5()).append("|");//邮编
		    sb.append(model.getInvoiceList().get(0).getInvoiceTitle()).append("|");//发票抬头
			sb.append("\n"); 
		}
		
		//生成本地对账文件并上传至FTP服务器
		String fileName=companyName+date+".txt";
		String localFile = ftpUpload(myLog, fileName, sb.toString());
		String num2 = paymentService.getTraceNum(date, "2");
		String num3 = paymentService.getTraceNum(date, "3");
		String num4 = paymentService.getTraceNum(date, "4");
		int total = Integer.parseInt(num2)+Integer.parseInt(num3)+Integer.parseInt(num4);
		String s = "营口热电【"+date+"】对账统计：共【"+total+"】笔，其中已对账【"+num2+"】笔，核心多出【"+num3+"】笔，渠道多出【"+num4+"】笔";

		myLog.info(logger, s);
		
		return repDto;
	}

	private String ftpUpload(MyLog myLog,String fileName,String str) throws YkwmTradeExecuteException {
		String localPath="";
		File file = null;
		BufferedWriter bw = null;
		try {
			try (Jedis jedis = myJedis.connect()) {
				localPath = jedis.get(COMMON_PREFIX+"txt_path");
			}
			file = new File(localPath+File.separator+fileName);
			if(!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream writerStream = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(writerStream, "GBK"));
			bw.write(str);
			
		}catch (Exception e) {
			myLog.error(logger,"生成营口热电对账文件失败: "+e);
			YkwmTradeExecuteException e1 = new YkwmTradeExecuteException(YkwmTradeExecuteException.YKWM_E_10006);
			throw e1;
		}finally {
			try {
				bw.close();
			} catch (IOException e) {
				logger.error("关闭临时文件异常", e);
			}
		}
		
		FtpUtil ftp = new FtpUtil("GBK");
		try {
			String host, port, user, password;
			try (Jedis jedis = myJedis.connect()) {
				host = jedis.get(COMMON_PREFIX+"ftp_ip");
				port = jedis.get(COMMON_PREFIX+"ftp_port");
				user = jedis.get(COMMON_PREFIX+"ftp_user");
				password = jedis.get(COMMON_PREFIX+"ftp_pass");
			}
			ftp.connect(host, Integer.parseInt(port), user, password);
			ftp.upload(fileName, file);
		}catch (Exception e) {
			myLog.error(logger,"FTP上传营口热电对账文件失败: "+e);
			YkwmTradeExecuteException e1 = new YkwmTradeExecuteException(YkwmTradeExecuteException.YKWM_E_10006);
			throw e1;
		}finally {
			try {
				ftp.disconnect();
			} catch (IOException e) {
				logger.error("关闭FTP异常", e);
			}
		}
		
		return localPath+File.separator+fileName;
	}
	
	private void checkTraceLog(MyLog myLog, DataTransObject dto, List<DayCheckLogInitModel> dayCheckLogList) throws SysTradeExecuteException {
		REQ_30062001201 reqDto = (REQ_30062001201)dto;
		String date = reqDto.getReqBody().getCollateDt();
		for(DayCheckLogInitModel model:dayCheckLogList) {
			//根据核心对账数据取渠道往账数据
			YkwmTraceLogModel ykwmTracelogModel = paymentService.queryLogByPk(myLog,model.getSysDate(),model.getSysTime(),model.getSysTraceno());
			//若渠道缺少数据则报错
			if(ykwmTracelogModel == null) {
				CheckErrorModel aceModel = new CheckErrorModel(myLog, model.getSysDate(), dto.getSysTime(), model.getSysTraceno());
				aceModel.setPlatDate(model.getSysDate());
				aceModel.setPlatTrace(model.getSysTraceno());
				aceModel.setPreHostState("");
				aceModel.setReHostState("0");
				aceModel.setCheckFlag("3");
				aceModel.setRemark("渠道补充数据，渠道日期【"+model.getSysDate()+"】，渠道流水【"+model.getSysTraceno()+"】");
				checkErrorService.insert(aceModel);
				
				myLog.error(logger, "营口热电【"+date+"】对账失败,渠道数据丢失: 核心流水号【"+model.getHostTraceno()+"】核心日期为【"+model.getHostDate()+"】");
				YkwmTradeExecuteException e = new YkwmTradeExecuteException(YkwmTradeExecuteException.YKWM_E_10006);
				throw e;
			}else {
				String hostState = ykwmTracelogModel.getCoResult(); //渠道记录的核心状态
				
					if(hostState.equals("0")) {
						//核心与渠道状态一致
						YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, model.getSysDate(), model.getSysTime(), model.getSysTraceno());
						record.setCapResult("2");
						paymentService.updateCheck(record);
					}else if(hostState.equals("2")||hostState.equals("3")||hostState.equals("4")) {
						//核心记账成功，渠道状态为超时、存款确认、冲正超时、冲正失败，修改渠道状态为成功
						YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, model.getSysDate(), model.getSysTime(), model.getSysTraceno());
						record.setCoResult("0");
						record.setCapResult("2");
						paymentService.updateCheck(record);
						myLog.info(logger,"渠道调整往账数据核心状态，渠道日期【"+ykwmTracelogModel.getSysDate()+"】，渠道流水【"+model.getSysTraceno()+"】，调整前状态【"+hostState+"】，调整后状态【1】");
					
						CheckErrorModel ceModel = new CheckErrorModel(myLog, model.getSysDate(), dto.getSysTime(), model.getSysTraceno());
						ceModel.setPlatDate(model.getSysDate());
						ceModel.setPlatTrace(model.getSysTraceno());
						ceModel.setPreHostState(hostState);
						ceModel.setReHostState("0");
						ceModel.setCheckFlag("2");
						ceModel.setTxAmt(new BigDecimal(ykwmTracelogModel.getPyFeeAmtT()));
						ceModel.setAcctNo(ykwmTracelogModel.getAcctNoT());
						ceModel.setUserCard(ykwmTracelogModel.getUserCardNoT());
						ceModel.setUserName(ykwmTracelogModel.getLnmT3());
						ceModel.setRemark("渠道调整往账数据核心状态，渠道日期【"+ykwmTracelogModel.getSysDate()+"】，渠道流水【"+ykwmTracelogModel.getSysTraceno()+"】，调整前状态【"+hostState+"】，调整后状态【0】");
						checkErrorService.insert(ceModel);
					}else {
						myLog.error(logger, "营口热电【"+date+"】对账失败: 渠道流水号【"+ykwmTracelogModel.getSysTraceno()+"】记录核心状态为【"+ykwmTracelogModel.getCoResult()+"】,与核心记账状态不符");
						YkwmTradeExecuteException e = new YkwmTradeExecuteException(YkwmTradeExecuteException.YKWM_E_10006);
						throw e;
					}
			}
		}
	}
	private List<DayCheckLogInitModel> getCheckLogList(MyLog myLog,DataTransObject dto,String platDate) throws SysTradeExecuteException{
		//请求核心接口，获取来账文件
		String localFile = getEsbCheckFile(myLog,dto);
		//对账文件入库
		initCheckLog(localFile,myLog,dto,platDate);
		//取对账文件数据
		List<DayCheckLogInitModel> dayCheckLogList = dayCheckLogService.getDayCheckLog(myLog, dto.getSysTime(), dto.getSysTraceno(), dto.getSysDate());
		return 	dayCheckLogList;
	}
	
	private void initCheckLog(String localFile, MyLog myLog, DataTransObject dto,String platDate) throws SysTradeExecuteException {
		BufferedReader br = null;
		myLog.info(logger, "账户变动信息入库开始");
		try {
			dayCheckLogService.delete(platDate);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(localFile)),"GBK"));
			String lineTxt=null;
			while ((lineTxt = br.readLine()) != null) {
				lineTxt += "*|";
				String[] array = lineTxt.split("\\|");
                if(array.length<20){
                    myLog.info(logger,"文件【"+localFile+"】内容缺失");
                    continue;
                }
                if(array[20].equals("02")) {
                	continue;
                }
                DayCheckLogInitModel model = new DayCheckLogInitModel(myLog, dto.getSysDate(),dto.getSysTime(),Integer.parseInt(array[1].substring(14)));
                model.setSettleDate(Integer.parseInt(array[2]));//清算日期
                model.setSettleBranch(array[3]); //清算机构
                model.setHostDate(Integer.parseInt(array[4])); //核心交易日期
                model.setHostTraceno(array[5]); //核心流水号
                model.setCcy(array[10]); //交易币种
                BigDecimal bg = new BigDecimal(array[11]==null?"0":array[11]);
                model.setTxAmt(bg); //交易金额
                model.setAccountno(array[12]); //交易账户 
                model.setReversal(array[19]); //冲正标志
                model.setTxStatus(array[20]); //交易状态
                
        		dayCheckLogService.dayCheckLogInit(model);
			}

		} catch (Exception e) {
            myLog.error(logger, "文件【"+localFile+"】插入失败", e);
            throw new RuntimeException("文件【"+localFile+"】插入失败");
		} finally {
			if(null != br) {
				try {
					br.close();
				} catch (IOException e) {
					myLog.error(logger, "文件流关闭失败", e);
				}
			}
			myLog.info(logger, "核心对账信息入库结束");
		}
		
		
	}
	
	private String getEsbCheckFile(MyLog myLog, DataTransObject dto) throws SysTradeExecuteException {
		ESB_REQ_50015000101 esbReq_50015000101 = new ESB_REQ_50015000101(myLog, dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno());
		REQ_30062001201 reqDto = (REQ_30062001201) dto;
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_50015000101.getReqSysHead(),dto)
				.setBranchId(reqDto.getReqSysHead().getBranchId()).setUserId(reqDto.getReqSysHead().getUserId()).
				setSourceType("YKWM").build();
		esbReq_50015000101.setReqSysHead(reqSysHead);
		ESB_REQ_50015000101.REQ_BODY esbReqBody_50015000101 = esbReq_50015000101.getReqBody();
		esbReqBody_50015000101.setChannelType("BH");
		esbReqBody_50015000101.setStartDate(reqDto.getReqBody().getCollateDt());
		esbReqBody_50015000101.setEndDate(reqDto.getReqBody().getCollateDt());
		esbReqBody_50015000101.setDirection("O");
		
		ESB_REP_50015000101 esbRep_50015000101 = forwardToESBService.sendToESB(esbReq_50015000101, esbReqBody_50015000101, ESB_REP_50015000101.class);
		String remoteFile = esbRep_50015000101.getRepSysHead().getFilePath();
		String fileName = esbRep_50015000101.getRepBody().getFileName();
		String localPath="";
		try (Jedis jedis = myJedis.connect()) {
			localPath = jedis.get(COMMON_PREFIX+"txt_path");
		}
		loadTraceLogFile(myLog, remoteFile, localPath+File.separator+fileName);
		return localPath+File.separator+fileName;
	}
	/**
	 * @Title: loadFile @Description: 从文件传输平台下载文件 @param @param
	 * myLog @param @param remoteFile 文件传输平台路径+文件名 @param @param localFile
	 * 文件本地路径+文件名 @param @throws PafTradeExecuteException 设定文件 @return void
	 * 返回类型 @throws
	 */
	private void loadTraceLogFile(MyLog myLog, String remoteFile, String localFile) throws SysTradeExecuteException {
		FtpClientConfigSet configSet = new FtpClientConfigSet();
		FtpGet ftpGet = null;
		try {
			ftpGet = new FtpGet(remoteFile, localFile, configSet);
			boolean result = ftpGet.doGetFile();
			if (!result) {
				myLog.error(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载失败！");
				YkwmTradeExecuteException e = new YkwmTradeExecuteException(YkwmTradeExecuteException.YKWM_E_10005);
				throw e;
			}
			myLog.info(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载成功！");
		} catch (Exception e) {
			myLog.error(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载失败！", e);
			YkwmTradeExecuteException e1 = new YkwmTradeExecuteException(YkwmTradeExecuteException.YKWM_E_10005,
					e.getMessage());
			throw e1;
		} finally {
			if (ftpGet != null) {
				try {
					ftpGet.close();
				} catch (FtpException e) {
					myLog.error(logger, "文件传输关闭连接失败！", e);
				}
			}
		}
	}

}
