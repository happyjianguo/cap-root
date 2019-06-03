package com.fxbank.cap.ykwm.dto.esb;

import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/** 
* @ClassName: REP_30061001201 
* @Description: 营口供热缴费应答 
* @作者 杜振铎
* @date 2019年5月31日 下午2:23:15 
*  
*/
public class REP_30061001201 extends REP_BASE {

	@JSONField(name = "APP_HEAD")
	private REP_APP_HEAD repAppHead = new REP_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private REP_SYS_HEAD repSysHead = new REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REP_BODY repBody = new REP_BODY();

	public REP_APP_HEAD getRepAppHead() {
		return repAppHead;
	}

	public void setRepAppHead(REP_APP_HEAD repAppHead) {
		this.repAppHead = repAppHead;
	}

	public REP_SYS_HEAD getRepSysHead() {
		return repSysHead;
	}

	public void setRepSysHead(REP_SYS_HEAD repSysHead) {
		this.repSysHead = repSysHead;
	}

	public REP_BODY getRepBody() {
		return repBody;
	}

	public void setRepBody(REP_BODY repBody) {
		this.repBody = repBody;
	}

	public class REP_BODY {

		@JSONField(name = "CHANNEL_DATE")
		private String channelDate;// 渠道日期
		@JSONField(name = "CHANNEL_SEQ_NO")
		private String channelSeqNo;// 渠道流水号
		@JSONField(name = "TICKET_CODE_ARRAY")
		private List<TicketCode> ticketCodeArray;// 取票码数组

		public String getChannelDate() {
			return channelDate;
		}

		public void setChannelDate(String channelDate) {
			this.channelDate = channelDate;
		}

		public String getChannelSeqNo() {
			return channelSeqNo;
		}

		public void setChannelSeqNo(String channelSeqNo) {
			this.channelSeqNo = channelSeqNo;
		}

		public List<TicketCode> getTicketCodeArray() {
			return ticketCodeArray;
		}

		public void setTicketCodeArray(List<TicketCode> ticketCodeArray) {
			this.ticketCodeArray = ticketCodeArray;
		}

	}

	public static class TicketCode {
		@JSONField(name = "TICKET_CODE_T")
		private String ticketCodeT;// 取票码

		public String getTicketCodeT() {
			return ticketCodeT;
		}

		public void setTicketCodeT(String ticketCodeT) {
			this.ticketCodeT = ticketCodeT;
		}

	}
}
