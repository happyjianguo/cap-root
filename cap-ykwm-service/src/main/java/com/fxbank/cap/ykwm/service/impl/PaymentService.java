package com.fxbank.cap.ykwm.service.impl;

import java.util.List;
import javax.validation.Valid;
import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.ykwm.entity.YkwmTracelog;
import com.fxbank.cap.ykwm.mapper.YkwmTracelogMapper;
import com.fxbank.cap.ykwm.model.YkwmTraceLogModel;
import com.fxbank.cap.ykwm.model.YkwmTraceLogModel.Invoice;
import com.fxbank.cap.ykwm.service.IPaymentService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
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
		//对账状态 0 未对账, 1 已对账
		log.setCapResult("0");
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
        List<Invoice> list = record.getInvoiceList();
        if(list.size()>=1) {
        	Invoice invoice = list.get(0);
        	log.setInvoicetitle1(invoice.getInvoiceTitle());
        	log.setInvoiceaddress1(invoice.getInvoiceAddress());
        	log.setInvoicename1(invoice.getInvoiceName());
        	log.setInvoicenum1(invoice.getInvoiceNum());
        	log.setBanknum1(invoice.getBankNum());
        	log.setArea1(Long.parseLong(invoice.getArea()));
        }
        if(list.size()>=2) {
        	Invoice invoice = list.get(1);
        	log.setInvoicetitle2(invoice.getInvoiceTitle());
        	log.setInvoiceaddress2(invoice.getInvoiceAddress());
        	log.setInvoicename2(invoice.getInvoiceName());
        	log.setInvoicenum2(invoice.getInvoiceNum());
        	log.setBanknum2(invoice.getBankNum());
        	log.setArea2(Long.parseLong(invoice.getArea()));
        }
        if(list.size()>=3) {
        	Invoice invoice = list.get(2);
        	log.setInvoicetitle3(invoice.getInvoiceTitle());
        	log.setInvoiceaddress3(invoice.getInvoiceAddress());
        	log.setInvoicename3(invoice.getInvoiceName());
        	log.setInvoicenum3(invoice.getInvoiceNum());
        	log.setBanknum3(invoice.getBankNum());
        	log.setArea3(Long.parseLong(invoice.getArea()));
        }
        if(list.size()==4) {
        	Invoice invoice = list.get(3);
        	log.setInvoicetitle4(invoice.getInvoiceTitle());
        	log.setInvoiceaddress4(invoice.getInvoiceAddress());
        	log.setInvoicename4(invoice.getInvoiceName());
        	log.setInvoicenum4(invoice.getInvoiceNum());
        	log.setBanknum4(invoice.getBankNum());
        	log.setArea4(Long.parseLong(invoice.getArea()));
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
		//对账状态 0 未对账, 1 已对账
		log.setCapResult("0");
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
		//核心交易状态 0-成功，1-冲正成功，2-冲正失败，3-冲正超时，4-超时
		log.setCoResult("0");
		//热电交易状态 0-登记，1-超时，2-处理成功，3-处理失败，4-冲正成功，5-冲正超时
		log.setPyResult("2");
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
		log.setPyResult("2");
		
		ykwmTracelogMapper.updateByPrimaryKeySelective(log);
		
	}

}
