package com.fxbank.cap.ykwm.dto.ykwm;

/** 
* @ClassName: REP_ERROR 
* @Description: 错误应答报文
* @作者 杜振铎
* @date 2019年4月29日 下午2:00:57 
*  
*/
public class REP_ERROR extends REP_BASE {
	
	private String repMsg;

	@Override
	public String creaFixPack() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.getHeader().creaFixPack());
		sb.append(this.repMsg == null ? "" : this.repMsg).append("|");

		return sb.toString();
	}

	@Override
	public void chanFixPack(String pack) {
		String[] array = pack.split("\\|");
		super.getHeader().chanFixPack(array[0]);
		this.repMsg = array[1].trim();
	}

	public String getRepMsg() {
		return repMsg;
	}

	public void setRepMsg(String repMsg) {
		this.repMsg = repMsg;
	}

}