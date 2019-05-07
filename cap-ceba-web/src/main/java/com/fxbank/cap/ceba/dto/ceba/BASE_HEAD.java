package com.fxbank.cap.ceba.dto.ceba;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/** 
* @ClassName: BASE_HEAD 
* @Description: 公共报文头 
* @作者 杜振铎
* @date 2019年5月7日 下午5:18:00 
*  
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "Version", "InstId", "AnsTranCode", "TrmSeqNum" })
    public class BASE_HEAD implements Serializable{
        private static final long serialVersionUID = 7162761168675476519L;
		private String Version = "1.0.1";
        private String InstId = null;
        private String AnsTranCode = null;
        private String TrmSeqNum = null;
		public String getVersion() {
			return Version;
		}
		public void setVersion(String version) {
			Version = version;
		}
		public String getInstId() {
			return InstId;
		}
		public void setInstId(String instId) {
			InstId = instId;
		}
		public String getAnsTranCode() {
			return AnsTranCode;
		}
		public void setAnsTranCode(String ansTranCode) {
			AnsTranCode = ansTranCode;
		}
		public String getTrmSeqNum() {
			return TrmSeqNum;
		}
		public void setTrmSeqNum(String trmSeqNum) {
			TrmSeqNum = trmSeqNum;
		}

       
    }