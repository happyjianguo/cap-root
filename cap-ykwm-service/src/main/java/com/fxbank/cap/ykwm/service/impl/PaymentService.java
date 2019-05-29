package com.fxbank.cap.ykwm.service.impl;

import javax.validation.Valid;
import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.ykwm.entity.YkwmTracelog;
import com.fxbank.cap.ykwm.mapper.YkwmTracelogMapper;
import com.fxbank.cap.ykwm.model.YkwmTraceLogModel;
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
		//热电交易状态 0-登记，1-超时，2-处理成功，3-处理失败
		log.setPyResult("0");
		//核心交易状态 0-成功，1-冲正成功，2-冲正失败，3-冲正超时，4-超时
		log.setCoResult("0");
		//对账状态 0 未对账, 1 已对账
		log.setCapResult("0");
		log.setCoDate(record.getCoDate());
		log.setCoTransactionno(record.getCoTransactionno());
		log.setCoRspcode(record.getCoRspcode());
		log.setCoRspmsg(record.getCoRspmsg());
		//TODO 登记柜面输入项和核心记账成功返回的信息
		
		ykwmTracelogMapper.insertSelective(log);
	}
	
	@Override
	public void hostTimeoutInit(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException {
		YkwmTracelog log = new YkwmTracelog();
		log.setCapDate(record.getSysDate().toString());
		log.setCapTransactionno(record.getSysTraceno().toString());
		log.setAcctNoT(record.getAcctNoT());
		//热电交易状态 0-登记，1-超时，2-处理成功，3-处理失败
		log.setPyResult("0");
		//核心交易状态 0-成功，1-冲正成功，2-冲正失败，3-冲正超时，4-超时
		log.setCoResult("4");
		//对账状态 0 未对账, 1 已对账
		log.setCapResult("0");
		//TODO 登记柜面输入项和核心记账成功返回的信息
		
		ykwmTracelogMapper.insertSelective(log);
	}

	@Override
	public void othSuccessUpdate(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException {
		YkwmTracelog log = new YkwmTracelog();
		log.setCapDate(record.getSysDate().toString());
		log.setCapTransactionno(record.getSysTraceno().toString());
		//热电交易状态 0-登记，1-超时，2-处理成功，3-处理失败
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
		//热电交易状态 0-登记，1-超时，2-处理成功，3-处理失败
		log.setPyResult("1");
		ykwmTracelogMapper.updateByPrimaryKeySelective(log);
	}
	@Override
	public void othErrorUpdate(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException {
		YkwmTracelog log = new YkwmTracelog();
		log.setCapDate(record.getSysDate().toString());
		log.setCapTransactionno(record.getSysTraceno().toString());
		//热电交易状态 0-登记，1-超时，2-处理成功，3-处理失败
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
		//TODO 更新核心冲正失败返回的错误信息
		
		ykwmTracelogMapper.updateByPrimaryKeySelective(log);
	}
	



}
