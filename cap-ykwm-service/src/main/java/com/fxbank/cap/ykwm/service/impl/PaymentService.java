package com.fxbank.cap.ykwm.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.ykwm.entity.YkwmTracelog;
import com.fxbank.cap.ykwm.mapper.YkwmTracelogMapper;
import com.fxbank.cap.ykwm.model.YkwmTraceLogModel;
import com.fxbank.cap.ykwm.model.YkwmTraceLogModel.Invoice;
import com.fxbank.cap.ykwm.service.IPaymentService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import org.springframework.beans.factory.annotation.Autowired;

@Service(version = "1.0.0")
public class PaymentService implements IPaymentService {

	@Autowired
	private YkwmTracelogMapper ykwmTracelogMapper;
	@Override
	public void hostSuccessInit(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException {
		YkwmTracelog log = new YkwmTracelog();
		log.setCapDate(record.getSysDate().toString());
		log.setCapTransactionno(record.getSysTraceno().toString());
		//热电交易状态 0-登记，1-超时，2-处理成功，3-处理失败，4-冲正成功，5-冲正超时
		log.setPyResult("0");
		//核心交易状态 0-成功，1-冲正成功，2-冲正失败，3-冲正超时，4-超时
		log.setCoResult("0");
		//对账标志，1-未对账，2-已对账，3-核心多，4-渠道多
		log.setCapResult("1");
		log.setCoDate(record.getCoDate());
		log.setCoTransactionno(record.getCoTransactionno());
		log.setCoRspcode(record.getCoRspcode());
		log.setCoRspmsg(record.getCoRspmsg());
		log.setAcctNoT(record.getAcctNoT());
		log.setPyFeeAmtT(record.getPyFeeAmtT());
		log.setUserDbtAmtT(record.getUserDbtAmtT());
		log.setCourierAmtT(record.getCourierAmtT());
		log.setUserCardNoT(record.getUserCardNoT());
        log.setCnttPhnT(record.getCnttPhnT());
        log.setLnmT3(record.getLnmT3());
        log.setPyFeeTpT(record.getPyFeeTpT());
        log.setReimburseSignT(record.getReimburseSignT());
        log.setHeatCompanyIdT(record.getHeatCompanyIdT());
        log.setMailAddrT(record.getMailAddrT());
        log.setHeatCompanyNmT(record.getHeatCompanyNmT());
        log.setPostNoT5(record.getPostNoT5());
        log.setCourierCmpnyIdT(record.getCourierCmpnyIdT());
        log.setTeCheckNum(record.getTeCheckNum());
        log.setBillGetTpT(record.getBillGetTpT());
        if(record.getInvoiceList()!=null) {
        List<Invoice> list = record.getInvoiceList();
        if(list.size()>=1) {
        	Invoice invoice = list.get(0);
        	log.setInvoicetitle1(invoice.getInvoiceTitle());
        	log.setInvoiceaddress1(invoice.getInvoiceAddress());
        	log.setInvoicename1(invoice.getInvoiceName());
        	log.setInvoicenum1(invoice.getInvoiceNum());
        	log.setBanknum1(invoice.getBankNum());
        	log.setArea1(new BigDecimal(invoice.getArea()));
        }
        if(list.size()>=2) {
        	Invoice invoice = list.get(1);
        	log.setInvoicetitle2(invoice.getInvoiceTitle());
        	log.setInvoiceaddress2(invoice.getInvoiceAddress());
        	log.setInvoicename2(invoice.getInvoiceName());
        	log.setInvoicenum2(invoice.getInvoiceNum());
        	log.setBanknum2(invoice.getBankNum());
        	log.setArea2(new BigDecimal(invoice.getArea()));
        }
        if(list.size()>=3) {
        	Invoice invoice = list.get(2);
        	log.setInvoicetitle3(invoice.getInvoiceTitle());
        	log.setInvoiceaddress3(invoice.getInvoiceAddress());
        	log.setInvoicename3(invoice.getInvoiceName());
        	log.setInvoicenum3(invoice.getInvoiceNum());
        	log.setBanknum3(invoice.getBankNum());
        	log.setArea3(new BigDecimal(invoice.getArea()));
        }
        if(list.size()==4) {
        	Invoice invoice = list.get(3);
        	log.setInvoicetitle4(invoice.getInvoiceTitle());
        	log.setInvoiceaddress4(invoice.getInvoiceAddress());
        	log.setInvoicename4(invoice.getInvoiceName());
        	log.setInvoicenum4(invoice.getInvoiceNum());
        	log.setBanknum4(invoice.getBankNum());
        	log.setArea4(new BigDecimal(invoice.getArea()));
        }
        }
		ykwmTracelogMapper.insertSelective(log);
	}
	
	@Override
	public void hostTimeoutInit(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException {
		YkwmTracelog log = new YkwmTracelog();
		log.setCapDate(record.getSysDate().toString());
		log.setCapTransactionno(record.getSysTraceno().toString());
		log.setAcctNoT(record.getAcctNoT());
		//热电交易状态 0-登记，1-超时，2-处理成功，3-处理失败，4-冲正成功，5-冲正超时
		log.setPyResult("0");
		//核心交易状态 0-成功，1-冲正成功，2-冲正失败，3-冲正超时，4-超时
		log.setCoResult("4");
		//对账标志，1-未对账，2-已对账，3-核心多，4-渠道多
		log.setCapResult("1");
		log.setAcctNoT(record.getAcctNoT());
		log.setPyFeeAmtT(record.getPyFeeAmtT());
		log.setUserDbtAmtT(record.getUserDbtAmtT());
		log.setCourierAmtT(record.getCourierAmtT());
		log.setUserCardNoT(record.getUserCardNoT());
        log.setCnttPhnT(record.getCnttPhnT());
        log.setLnmT3(record.getLnmT3());
        log.setPyFeeTpT(record.getPyFeeTpT());
        log.setReimburseSignT(record.getReimburseSignT());
        log.setHeatCompanyIdT(record.getHeatCompanyIdT());
        log.setMailAddrT(record.getMailAddrT());
        log.setHeatCompanyNmT(record.getHeatCompanyNmT());
        log.setPostNoT5(record.getPostNoT5());
        log.setCourierCmpnyIdT(record.getCourierCmpnyIdT());
        log.setTeCheckNum(record.getTeCheckNum());
        log.setBillGetTpT(record.getBillGetTpT());
        List<Invoice> list = record.getInvoiceList();
        if(list.size()>=1) {
        	Invoice invoice = list.get(0);
        	log.setInvoicetitle1(invoice.getInvoiceTitle());
        	log.setInvoiceaddress1(invoice.getInvoiceAddress());
        	log.setInvoicename1(invoice.getInvoiceName());
        	log.setInvoicenum1(invoice.getInvoiceNum());
        	log.setBanknum1(invoice.getBankNum());
        	log.setInvoiceaddress1(invoice.getInvoiceAddress());
        }
        if(list.size()>=2) {
        	Invoice invoice = list.get(1);
        	log.setInvoicetitle2(invoice.getInvoiceTitle());
        	log.setInvoiceaddress2(invoice.getInvoiceAddress());
        	log.setInvoicename2(invoice.getInvoiceName());
        	log.setInvoicenum2(invoice.getInvoiceNum());
        	log.setBanknum2(invoice.getBankNum());
        	log.setInvoiceaddress2(invoice.getInvoiceAddress());
        }
        if(list.size()>=3) {
        	Invoice invoice = list.get(2);
        	log.setInvoicetitle3(invoice.getInvoiceTitle());
        	log.setInvoiceaddress3(invoice.getInvoiceAddress());
        	log.setInvoicename3(invoice.getInvoiceName());
        	log.setInvoicenum3(invoice.getInvoiceNum());
        	log.setBanknum3(invoice.getBankNum());
        	log.setInvoiceaddress3(invoice.getInvoiceAddress());
        }
        if(list.size()==4) {
        	Invoice invoice = list.get(3);
        	log.setInvoicetitle4(invoice.getInvoiceTitle());
        	log.setInvoiceaddress4(invoice.getInvoiceAddress());
        	log.setInvoicename4(invoice.getInvoiceName());
        	log.setInvoicenum4(invoice.getInvoiceNum());
        	log.setBanknum4(invoice.getBankNum());
        	log.setInvoiceaddress4(invoice.getInvoiceAddress());
        }
		
		ykwmTracelogMapper.insertSelective(log);
	}

	@Override
	public void othSuccessUpdate(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException {
		YkwmTracelog log = new YkwmTracelog();
		log.setCapDate(record.getSysDate().toString());
		log.setCapTransactionno(record.getSysTraceno().toString());
		//热电交易状态 0-登记，1-超时，2-处理成功，3-处理失败，4-冲正成功，5-冲正超时
		log.setPyResult("2");
		log.setTicketNumber(record.getTicketNumber());
		log.setPyRspcode(record.getPyRspcode());
		ykwmTracelogMapper.updateByPrimaryKeySelective(log);
	}
	@Override
	public void othTimeoutUpdate(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException {
		YkwmTracelog log = new YkwmTracelog();
		log.setCapDate(record.getSysDate().toString());
		log.setCapTransactionno(record.getSysTraceno().toString());
		//热电交易状态 0-登记，1-超时，2-处理成功，3-处理失败，4-冲正成功，5-冲正超时
		log.setPyResult("1");
		ykwmTracelogMapper.updateByPrimaryKeySelective(log);
	}
	@Override
	public void othErrorUpdate(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException {
		YkwmTracelog log = new YkwmTracelog();
		log.setCapDate(record.getSysDate().toString());
		log.setCapTransactionno(record.getSysTraceno().toString());
		//热电交易状态 0-登记，1-超时，2-处理成功，3-处理失败，4-冲正成功，5-冲正超时
		log.setPyResult("3");
        log.setPyRspcode(record.getPyRspcode());
        log.setPyErrorMsg(record.getPyErrorMsg());
		ykwmTracelogMapper.updateByPrimaryKeySelective(log);
	}
	@Override
	public void othTimeoutSuccUpdate(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException {
	}
	@Override
	public void hostUndoSuccess(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException {
		YkwmTracelog log = new YkwmTracelog();
		log.setCapDate(record.getSysDate().toString());
		log.setCapTransactionno(record.getSysTraceno().toString());
		//核心交易状态 0-成功，1-冲正成功，2-冲正失败，3-冲正超时，4-超时
		log.setCoResult("1");
        log.setCoDate(record.getCoDate());
        log.setCoRspcode(record.getCoRspcode());
        log.setCoRspmsg(record.getCoRspmsg());
		log.setAcctNoT(record.getAcctNoT());
		ykwmTracelogMapper.updateByPrimaryKeySelective(log);
	}
	@Override
	public void hostUndoTimeout(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException {
		YkwmTracelog log = new YkwmTracelog();
		log.setCapDate(record.getSysDate().toString());
		log.setCapTransactionno(record.getSysTraceno().toString());
		//核心交易状态 0-成功，1-冲正成功，2-冲正失败，3-冲正超时，4-超时
		log.setCoResult("3");
		ykwmTracelogMapper.updateByPrimaryKeySelective(log);
	}
	@Override
	public void hostUndoError(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException {
		YkwmTracelog log = new YkwmTracelog();
		log.setCapDate(record.getSysDate().toString());
		log.setCapTransactionno(record.getSysTraceno().toString());
		//核心交易状态 0-成功，1-冲正成功，2-冲正失败，3-冲正超时，4-超时
		log.setCoResult("2");
		log.setCoRspcode(record.getCoRspcode());
		log.setCoRspmsg(record.getCoRspmsg());
		ykwmTracelogMapper.updateByPrimaryKeySelective(log);
	}

	/** 
	* @Title: queryLogBySeqNo 
	* @Description: 通过渠道流水号和渠道日期查询记录
	* @param @param record
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public YkwmTraceLogModel queryLogBySeqNo(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException {
		YkwmTracelog log = new YkwmTracelog();
		log.setCapDate(record.getSysDate().toString());
		log.setCapTransactionno(record.getSysTraceno().toString());
		YkwmTracelog result = ykwmTracelogMapper.selectOne(log);
		if(null!=result) {
			record.setTeCheckNum(result.getTeCheckNum());
			record.setCoDate(result.getCoDate());
			record.setCoTransactionno(result.getCoTransactionno());
			record.setAcctNoT(result.getAcctNoT());
			record.setPyFeeAmtT(result.getPyFeeAmtT());
		}else {
			return null;
		}
		return record;
	}

	/** 
	* @Title: othUndoSuccUpdate 
	* @Description: 热电冲正成功更新日志
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void othUndoSuccUpdate(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException {
		YkwmTracelog log = new YkwmTracelog();
		log.setCapDate(record.getSysDate().toString());
		log.setCapTransactionno(record.getSysTraceno().toString());
		//热电交易状态 0-登记，1-超时，2-处理成功，3-处理失败，4-冲正成功，5-冲正超时
		log.setPyResult("4");
		//核心交易状态 0-成功，1-冲正成功，2-冲正失败，3-冲正超时，4-超时
		log.setCoResult("0");
		ykwmTracelogMapper.updateByPrimaryKeySelective(log);
		
	}

	/** 
	* @Title: othUndoTimeoutUpdate 
	* @Description: 热电冲正超时更新日志
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void othUndoTimeoutUpdate(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException {
		YkwmTracelog log = new YkwmTracelog();
		log.setCapDate(record.getSysDate().toString());
		log.setCapTransactionno(record.getSysTraceno().toString());
		//热电交易状态 0-登记，1-超时，2-处理成功，3-处理失败，4-冲正成功，5-冲正超时
		log.setPyResult("5");
		
		ykwmTracelogMapper.updateByPrimaryKeySelective(log);
		
	}
	
	@Override
	public List<YkwmTraceLogModel> getCheckTrace(MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno,
			String date) throws SysTradeExecuteException {
		YkwmTracelog log = new YkwmTracelog();
		log.setCapDate(date);
		log.setCapResult("1");
		List<YkwmTracelog> dataList = ykwmTracelogMapper.select(log);
		List<YkwmTraceLogModel> modelList = new ArrayList<>();
		for(YkwmTracelog data : dataList) {
			YkwmTraceLogModel model = new YkwmTraceLogModel(myLog, Integer.parseInt(data.getCapDate()), sysTime, Integer.parseInt(data.getCapTransactionno()));
			model.setCoDate(data.getCoDate());
			model.setCoTransactionno(data.getCoTransactionno());
			model.setCoResult(data.getCoResult());
			model.setPyFeeAmtT(data.getPyFeeAmtT());
			model.setUserCardNoT(data.getUserCardNoT());
			model.setAcctNoT(data.getAcctNoT());
			model.setTeCheckNum(data.getTeCheckNum());
			model.setCourierCmpnyIdT(data.getCourierCmpnyIdT());
			model.setMailAddrT(data.getMailAddrT());
			model.setCnttPhnT(data.getCnttPhnT());
			model.setLnmT3(data.getLnmT3());
			model.setPostNoT5(data.getPostNoT5());
			model.setBillGetTpT(data.getBillGetTpT());
			List<Invoice> list = new ArrayList<Invoice>();
			Invoice invoice1 = new Invoice();
			invoice1.setInvoiceTitle(data.getInvoicetitle1());
			invoice1.setArea(null==data.getArea1()?"":data.getArea1().toString());
			invoice1.setInvoiceName(data.getInvoicename1());
			invoice1.setInvoiceNum(data.getInvoicenum1());
			invoice1.setBankNum(data.getBanknum1());
			invoice1.setInvoiceAddress(data.getInvoiceaddress1());
			list.add(invoice1);
			Invoice invoice2 = new Invoice();
			invoice2.setInvoiceTitle(data.getInvoicetitle2());
			invoice2.setArea(null==data.getArea2()?"":data.getArea2().toString());
			invoice2.setInvoiceName(data.getInvoicename2());
			invoice2.setInvoiceNum(data.getInvoicenum2());
			invoice2.setBankNum(data.getBanknum2());
			invoice2.setInvoiceAddress(data.getInvoiceaddress2());
			list.add(invoice2);
			Invoice invoice3 = new Invoice();
			invoice3.setInvoiceTitle(data.getInvoicetitle3());
			invoice3.setArea(null==data.getArea3()?"":data.getArea3().toString());
			invoice3.setInvoiceName(data.getInvoicename3());
			invoice3.setInvoiceNum(data.getInvoicenum3());
			invoice3.setBankNum(data.getBanknum3());
			invoice3.setInvoiceAddress(data.getInvoiceaddress3());
			list.add(invoice3);
			Invoice invoice4 = new Invoice();
			invoice4.setInvoiceTitle(data.getInvoicetitle4());
			invoice4.setArea(null==data.getArea4()?"":data.getArea4().toString());
			invoice4.setInvoiceName(data.getInvoicename4());
			invoice4.setInvoiceNum(data.getInvoicenum4());
			invoice4.setBankNum(data.getBanknum4());
			invoice4.setInvoiceAddress(data.getInvoiceaddress4());
			list.add(invoice4);
			model.setInvoiceList(list);
			modelList.add(model);
		}
		
		return modelList;
	}

	/** 
	* @Title: updateCheck 
	* @Description: 对账时更新日志核心记账状态和对账状态等 
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateCheck(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException {
		YkwmTracelog log = new YkwmTracelog();
		log.setCapDate(record.getSysDate().toString());
		log.setCapTransactionno(record.getSysTraceno().toString());
		if(null!=record.getCoResult()) {
			log.setCoResult(record.getCoResult());
		}
		if(null!=record.getCapResult()) {
			log.setCapResult(record.getCapResult());
		}
		ykwmTracelogMapper.updateByPrimaryKeySelective(log);
		
	}

	/** 
	* @Title: queryLogByPk 
	* @Description: 通过渠道日期和渠道流水号查询日志
	* @param @param record
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public YkwmTraceLogModel queryLogByPk(MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno) throws SysTradeExecuteException {
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, sysDate, sysTime, sysTraceno);
		YkwmTracelog log = new YkwmTracelog();
		log.setCapDate(record.getSysDate().toString());
		log.setCapTransactionno(record.getSysTraceno().toString());
		YkwmTracelog data = ykwmTracelogMapper.selectOne(log);
		if(null!=data) {
			record.setCoDate(data.getCoDate());
			record.setCoResult(data.getCoResult());
			record.setCoTransactionno(data.getCoTransactionno());
			record.setPyFeeAmtT(data.getPyFeeAmtT());
			record.setUserCardNoT(data.getUserCardNoT());
			record.setAcctNoT(data.getAcctNoT());
			record.setTeCheckNum(data.getTeCheckNum());
			record.setCourierCmpnyIdT(data.getCourierCmpnyIdT());
			record.setMailAddrT(data.getMailAddrT());
			record.setCnttPhnT(data.getCnttPhnT());
			record.setLnmT3(data.getLnmT3());
			record.setPostNoT5(data.getPostNoT5());
			record.setBillGetTpT(data.getBillGetTpT());
			List<Invoice> list = new ArrayList<Invoice>();
			Invoice invoice1 = new Invoice();
			invoice1.setInvoiceTitle(data.getInvoicetitle1());
			invoice1.setArea(null==data.getArea1()?"":data.getArea1().toString());
			invoice1.setInvoiceName(data.getInvoicename1());
			invoice1.setInvoiceNum(data.getInvoicenum1());
			invoice1.setBankNum(data.getBanknum1());
			invoice1.setInvoiceAddress(data.getInvoiceaddress1());
			list.add(invoice1);
			Invoice invoice2 = new Invoice();
			invoice2.setInvoiceTitle(data.getInvoicetitle2());
			invoice2.setArea(null==data.getArea2()?"":data.getArea2().toString());
			invoice2.setInvoiceName(data.getInvoicename2());
			invoice2.setInvoiceNum(data.getInvoicenum2());
			invoice2.setBankNum(data.getBanknum2());
			invoice2.setInvoiceAddress(data.getInvoiceaddress2());
			list.add(invoice2);
			Invoice invoice3 = new Invoice();
			invoice3.setInvoiceTitle(data.getInvoicetitle3());
			invoice3.setArea(null==data.getArea3()?"":data.getArea3().toString());
			invoice3.setInvoiceName(data.getInvoicename3());
			invoice3.setInvoiceNum(data.getInvoicenum3());
			invoice3.setBankNum(data.getBanknum3());
			invoice3.setInvoiceAddress(data.getInvoiceaddress3());
			list.add(invoice3);
			Invoice invoice4 = new Invoice();
			invoice4.setInvoiceTitle(data.getInvoicetitle4());
			invoice4.setArea(null==data.getArea4()?"":data.getArea4().toString());
			invoice4.setInvoiceName(data.getInvoicename4());
			invoice4.setInvoiceNum(data.getInvoicenum4());
			invoice4.setBankNum(data.getBanknum4());
			invoice4.setInvoiceAddress(data.getInvoiceaddress4());
			list.add(invoice4);
			record.setInvoiceList(list);
		}else {
			return null;
		}
		return record;
	}

	/** 
	* @Title: getTotalNum 
	* @Description: 查询对账完成的交易总笔数 
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public String getTotalNum(String date) throws SysTradeExecuteException {
		return ykwmTracelogMapper.selectTotalNum(date);
	}

	/** 
	* @Title: getTotalAmt 
	* @Description: 查询对账完成的交易总金额  
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public String getTotalAmt(String date) throws SysTradeExecuteException {
		return ykwmTracelogMapper.selectTotalSum(date);
	}
	@Override
	public List<YkwmTraceLogModel> getUploadCheckSndTrace(MyLog myLog, Integer sysDate, Integer sysTime,
			Integer sysTraceno, String date) throws SysTradeExecuteException {
		List<YkwmTracelog> dataList = ykwmTracelogMapper.selectCheckedTrace(date);
		List<YkwmTraceLogModel> modelList = new ArrayList<>();
		for(YkwmTracelog data : dataList) {
			YkwmTraceLogModel model = new YkwmTraceLogModel(myLog, Integer.parseInt(data.getCapDate()), sysTime, Integer.parseInt(data.getCapTransactionno()));
			model.setCoDate(data.getCoDate());
			model.setCoTransactionno(data.getCoTransactionno());
			model.setPyFeeAmtT(data.getPyFeeAmtT());
			model.setUserCardNoT(data.getUserCardNoT());
			model.setAcctNoT(data.getAcctNoT());
			model.setTeCheckNum(data.getTeCheckNum());
			model.setCourierCmpnyIdT(data.getCourierCmpnyIdT());
			model.setMailAddrT(data.getMailAddrT());
			model.setCnttPhnT(data.getCnttPhnT());
			model.setLnmT3(data.getLnmT3());
			model.setPostNoT5(data.getPostNoT5());
			model.setBillGetTpT(data.getBillGetTpT());
			List<Invoice> list = new ArrayList<Invoice>();
			Invoice invoice1 = new Invoice();
			invoice1.setInvoiceTitle(data.getInvoicetitle1());
			invoice1.setArea(null==data.getArea1()?"":data.getArea1().toString());
			invoice1.setInvoiceName(data.getInvoicename1());
			invoice1.setInvoiceNum(data.getInvoicenum1());
			invoice1.setBankNum(data.getBanknum1());
			invoice1.setInvoiceAddress(data.getInvoiceaddress1());
			list.add(invoice1);
			Invoice invoice2 = new Invoice();
			invoice2.setInvoiceTitle(data.getInvoicetitle2());
			invoice2.setArea(null==data.getArea2()?"":data.getArea2().toString());
			invoice2.setInvoiceName(data.getInvoicename2());
			invoice2.setInvoiceNum(data.getInvoicenum2());
			invoice2.setBankNum(data.getBanknum2());
			invoice2.setInvoiceAddress(data.getInvoiceaddress2());
			list.add(invoice2);
			Invoice invoice3 = new Invoice();
			invoice3.setInvoiceTitle(data.getInvoicetitle3());
			invoice3.setArea(null==data.getArea3()?"":data.getArea3().toString());
			invoice3.setInvoiceName(data.getInvoicename3());
			invoice3.setInvoiceNum(data.getInvoicenum3());
			invoice3.setBankNum(data.getBanknum3());
			invoice3.setInvoiceAddress(data.getInvoiceaddress3());
			list.add(invoice3);
			Invoice invoice4 = new Invoice();
			invoice4.setInvoiceTitle(data.getInvoicetitle4());
			invoice4.setArea(null==data.getArea4()?"":data.getArea4().toString());
			invoice4.setInvoiceName(data.getInvoicename4());
			invoice4.setInvoiceNum(data.getInvoicenum4());
			invoice4.setBankNum(data.getBanknum4());
			invoice4.setInvoiceAddress(data.getInvoiceaddress4());
			list.add(invoice4);
			model.setInvoiceList(list);
			modelList.add(model);
		}
		
		return modelList;
	}
	@Override
	public String getTraceNum(String date, String capResult) throws SysTradeExecuteException {
		return ykwmTracelogMapper.selectTraceNum(date, capResult);
	}

}
