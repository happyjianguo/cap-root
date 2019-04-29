package com.fxbank.cap.ykwm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;


/** 
* @ClassName: REQ_30012002001 
* @Description: 柜面欠费查询请求
* @author Duzhenduo
* @date 2019年4月29日 下午1:56:39 
*  
*/
public class REQ_30012002001 extends REQ_BASE {
	
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30012002001(){
		super.txDesc = "查询欠费";
	}

	public REQ_APP_HEAD getReqAppHead() {
		return reqAppHead;
	}

	public void setReqAppHead(REQ_APP_HEAD reqAppHead) {
		this.reqAppHead = reqAppHead;
	}

    @Override
	public REQ_SYS_HEAD getReqSysHead() {
		return reqSysHead;
	}

    @Override
	public void setReqSysHead(REQ_SYS_HEAD reqSysHead) {
		this.reqSysHead = reqSysHead;
	}


	public REQ_BODY getReqBody() {
		return reqBody;
	}


	public void setReqBody(REQ_BODY reqBody) {
		this.reqBody = reqBody;
	}

	public class REQ_BODY {

		/** 
		* @Fields companyID : 公司ID
		*/ 
		@JSONField(name = "CompanyID")
		private String companyID;	

		/** 
		* @Fields cardNum : 用户卡号
		*/ 
		@JSONField(name = "CardNum")
		private String cardNum;	
		
		/** 
		* @Fields batchNum : 批次号
		*/ 
		@JSONField(name = "BatchNum")
		private String batchNum;		

		public String getCompanyID() {
			return companyID;
		}

		public void setCompanyID(String companyID) {
			this.companyID = companyID;
		}

		public String getCardNum() {
			return cardNum;
		}

		public void setCardNum(String cardNum) {
			this.cardNum = cardNum;
		}

		public String getBatchNum() {
			return batchNum;
		}

		public void setBatchNum(String batchNum) {
			this.batchNum = batchNum;
		}
		
		
	
	}
}
