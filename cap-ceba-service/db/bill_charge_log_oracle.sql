DROP TABLE bill_charge_log;
CREATE TABLE bill_charge_log (
plat_date NUMBER(11) NOT NULL ,
plat_trace NUMBER(11) NOT NULL ,
plat_time NUMBER(11) NULL ,
source_type NVARCHAR2(20) NULL ,
tx_branch NVARCHAR2(20) NULL ,
bill_key NVARCHAR2(35) NOT NULL ,
company_id NVARCHAR2(30) NOT NULL ,
customer_name NVARCHAR2(100) NULL ,
pay_account NVARCHAR2(21) NULL ,
pay_amount NUMBER NOT NULL ,
ac_type NVARCHAR2(50) NULL ,
contract_no NVARCHAR2(30) NULL ,
bank_bill_no NVARCHAR2(16) NULL ,
receipt_no NVARCHAR2(30) NULL ,
acct_date NVARCHAR2(8) NULL ,
pay_state NVARCHAR2(10) NULL,
error_code NVARCHAR2(7) NULL,
check_state NVARCHAR2(1) NULL
)

;
COMMENT ON TABLE bill_charge_log IS '缴费单销账流水日志';
COMMENT ON COLUMN bill_charge_log.plat_date IS '渠道日期';
COMMENT ON COLUMN bill_charge_log.plat_trace IS '渠道流水';
COMMENT ON COLUMN bill_charge_log.plat_time IS '交易时间';
COMMENT ON COLUMN bill_charge_log.source_type IS '交易渠道';
COMMENT ON COLUMN bill_charge_log.tx_branch IS '交易机构';
COMMENT ON COLUMN bill_charge_log.bill_key IS '机表号';
COMMENT ON COLUMN bill_charge_log.company_id IS '收费单位标示号';
COMMENT ON COLUMN bill_charge_log.customer_name IS '客户姓名';
COMMENT ON COLUMN bill_charge_log.pay_account IS '缴费账号';
COMMENT ON COLUMN bill_charge_log.pay_amount IS '缴费金额（单位：分）';
COMMENT ON COLUMN bill_charge_log.ac_type IS '账户类型，1-借记卡，2-活期一本通，C-信用卡';
COMMENT ON COLUMN bill_charge_log.contract_no IS '合同号';
COMMENT ON COLUMN bill_charge_log.bank_bill_no IS '光大银行处理流水';
COMMENT ON COLUMN bill_charge_log.receipt_no IS '打印凭证号码';
COMMENT ON COLUMN bill_charge_log.acct_date IS '光大银行账务日期';
COMMENT ON COLUMN bill_charge_log.pay_state IS '订单状态,0-登记，1-超时，2-处理成功，3-处理失败';
COMMENT ON COLUMN bill_charge_log.error_code IS '光大银行处理失败时返回的错误代码';
COMMENT ON COLUMN bill_charge_log.check_state IS '对账状态：0-未对账，1-已对账';

ALTER TABLE bill_charge_log ADD PRIMARY KEY (plat_date, plat_trace);