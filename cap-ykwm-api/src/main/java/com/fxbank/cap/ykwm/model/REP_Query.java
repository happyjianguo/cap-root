package com.fxbank.cap.ykwm.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fxbank.cip.base.log.MyLog;


/** 
* @ClassName: REP_Query 
* @Description: 欠费查询响应报文
* @作者 杜振铎
* @date 2019年4月29日 下午3:02:09 
*  
*/
public class REP_Query extends REP_BASE {

	private static final long serialVersionUID = -5008748819109644373L;

	private static final String RESULT = "0";
	
	   private String ownerName;

	    private String address;
	    
	    private BigDecimal minPayment;
	    
	    private BigDecimal total;
	    
	    private String unit;
	    
	    private BigDecimal area;
	    
	    private List<AccountDetail> data;
	    
	    private String description;
	    
	    private String checkNum;
	    
	    private String extend;
	    
	    private List<Express> expressList;

    public String getOwnerName() {
			return ownerName;
		}

		public void setOwnerName(String ownerName) {
			this.ownerName = ownerName;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public BigDecimal getMinPayment() {
			return minPayment;
		}

		public void setMinPayment(BigDecimal minPayment) {
			this.minPayment = minPayment;
		}

		public BigDecimal getTotal() {
			return total;
		}

		public void setTotal(BigDecimal total) {
			this.total = total;
		}

		public String getUnit() {
			return unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}

		public BigDecimal getArea() {
			return area;
		}

		public void setArea(BigDecimal area) {
			this.area = area;
		}

		public List<AccountDetail> getData() {
			return data;
		}

		public void setData(List<AccountDetail> data) {
			this.data = data;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getCheckNum() {
			return checkNum;
		}

		public void setCheckNum(String checkNum) {
			this.checkNum = checkNum;
		}

		public String getExtend() {
			return extend;
		}

		public void setExtend(String extend) {
			this.extend = extend;
		}

		public List<Express> getExpressList() {
			return expressList;
		}

		public void setExpressList(List<Express> expressList) {
			this.expressList = expressList;
		}

	@Deprecated
	public REP_Query() {
		super(null, 0, 0, 0);
	}

    public REP_Query(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
    }

    @Override
    public String creaFixPack() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.getHeader().creaFixPack());
        if(!RESULT.equals(super.getHeader().getResult())) {
        	return sb.toString();
        }
        sb.append(this.ownerName==null?"":this.ownerName).append("|");
        sb.append(this.address==null?"":this.address).append("|");
        sb.append(this.minPayment==null?0:this.minPayment).append("|");
        sb.append(this.total==null?0:this.total).append("|");
        sb.append(this.unit==null?"":this.unit).append("|");
        sb.append(this.area==null?0:this.area).append("|");
        sb.append(this.data==null?0:this.data.size()).append("|");
        if(this.data!=null) {
        	for(AccountDetail temp:data) {
        		sb.append(temp.getChargeYear()==null?"":temp.getChargeYear()).append("|");
        		sb.append(temp.getItemName()==null?"":temp.getItemName()).append("|");
        		sb.append(temp.getArea()==null?0:temp.getArea()).append("|");
        		sb.append(temp.getPrice()==null?0:temp.getPrice()).append("|");
        		sb.append(temp.getAccount()==null?0:temp.getAccount()).append("|");
        		sb.append(temp.getAgio()==null?0:temp.getAgio()).append("|");
        		sb.append(temp.getLateFee()==null?0:temp.getLateFee()).append("|");
        		sb.append(temp.getPayment()==null?0:temp.getPayment()).append("|");
        	}
        }
        sb.append(this.description==null?"":this.description).append("|");
        sb.append(this.checkNum==null?"":this.checkNum).append("|");
        sb.append(this.extend==null?"":this.extend).append("|");
        sb.append(this.expressList==null?0:this.expressList.size()).append("|");
        if(this.expressList!=null) {
        	for(Express temp:expressList) {
        		sb.append(temp.getExpressID()==null?"":temp.getExpressID()).append("|");
        		sb.append(temp.getExpress()==null?"":temp.getExpress()).append("|");
        		sb.append(temp.getPrice()==null?0:temp.getPrice()).append("|");
        	}
        }
        
        return sb.toString();
    }

    @Override
    public void chanFixPack(String pack) {
    	String[] array = pack.split("\\|");
    	int i = 1;
		super.getHeader().chanFixPack(pack);
		if(!RESULT.equals(super.getHeader().getResult())) {
        	return;
        }
		this.ownerName = array[i++].trim();
		this.address = array[i++].trim();
		this.minPayment = new BigDecimal(array[i++].trim());
		this.total = new BigDecimal(array[i++].trim());
		this.unit = array[i++].trim();
		this.area = new BigDecimal(array[i++].trim());
		List<AccountDetail> data = new ArrayList<AccountDetail>();
		int dataSize = Integer.parseInt(array[i++].trim());
		for(int j=0;j<dataSize;j++) {
			AccountDetail account = new AccountDetail();
			account.setChargeYear(array[i++].trim());
			account.setItemName(array[i++].trim());
			account.setArea(new BigDecimal(array[i++].trim()));
			account.setPrice(new BigDecimal(array[i++].trim()));
			account.setAccount(new BigDecimal(array[i++].trim()));
			account.setAgio(new BigDecimal(array[i++].trim()));
			account.setLateFee(new BigDecimal(array[i++].trim()));
			account.setPayment(new BigDecimal(array[i++].trim()));
			data.add(account);
		}
		this.data = data;
		this.description = array[i++].trim();
		this.checkNum = array[i++].trim();
		this.extend = array[i++].trim();
		List<Express> expressList = new ArrayList<Express>();
		int expressListSize = Integer.parseInt(array[i++].trim());
		for(int k=0;k<expressListSize;k++) {
			Express express = new Express();
			express.setExpressID(Integer.parseInt(array[i++].trim()));
			express.setExpress(array[i++].trim());
			express.setPrice(new BigDecimal(array[i++].trim()));
			expressList.add(express);
		}
		this.expressList = expressList;
		
    }

	
public static class AccountDetail implements Serializable{
	private static final long serialVersionUID = -1451187044949161814L;
	private String chargeYear;
	private String itemName;
	private BigDecimal area;
	private BigDecimal price;
	private BigDecimal account;
	private BigDecimal agio;
	private BigDecimal lateFee;
	private BigDecimal payment;
	public String getChargeYear() {
		return chargeYear;
	}
	public void setChargeYear(String chargeYear) {
		this.chargeYear = chargeYear;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public BigDecimal getArea() {
		return area;
	}
	public void setArea(BigDecimal area) {
		this.area = area;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getAccount() {
		return account;
	}
	public void setAccount(BigDecimal account) {
		this.account = account;
	}
	public BigDecimal getAgio() {
		return agio;
	}
	public void setAgio(BigDecimal agio) {
		this.agio = agio;
	}
	public BigDecimal getLateFee() {
		return lateFee;
	}
	public void setLateFee(BigDecimal lateFee) {
		this.lateFee = lateFee;
	}
	public BigDecimal getPayment() {
		return payment;
	}
	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}
	
}

public static class Express implements Serializable{
	private static final long serialVersionUID = 5467842687224030845L;
	private Integer expressID;
	private String express;
	private BigDecimal price;
	public Integer getExpressID() {
		return expressID;
	}
	public void setExpressID(Integer expressID) {
		this.expressID = expressID;
	}
	public String getExpress() {
		return express;
	}
	public void setExpress(String express) {
		this.express = express;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
}

}