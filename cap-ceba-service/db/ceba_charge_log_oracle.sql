DROP TABLE ceba_charge_log;
CREATE TABLE ceba_charge_log (
plat_date NUMBER(11) NOT NULL ,
plat_trace NUMBER(11) NOT NULL ,
plat_time NUMBER(11) NULL ,
source_type NVARCHAR2(20) NULL ,
tx_branch NVARCHAR2(20) NULL ,
tx_tel NVARCHAR2(10) NULL ,
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
ceba_check_state NVARCHAR2(1) NULL,
host_check_state NVARCHAR2(1) NULL,
seq_no NVARCHAR2(30) NOT NULL,
host_date NUMBER(11) NULL,
host_traceno NVARCHAR2(20) NULL,
host_state NVARCHAR2(10) NULL,
host_ret_code NVARCHAR2(20) NULL,
host_ret_msg NVARCHAR2(200) NULL
)
;
COMMENT ON TABLE ceba_charge_log IS '缴费单销账流水日志';
COMMENT ON COLUMN ceba_charge_log.plat_date IS '渠道日期';
COMMENT ON COLUMN ceba_charge_log.plat_trace IS '渠道流水';
COMMENT ON COLUMN ceba_charge_log.plat_time IS '交易时间';
COMMENT ON COLUMN ceba_charge_log.source_type IS '交易渠道(区分手机银行、微信、柜面)';
COMMENT ON COLUMN ceba_charge_log.tx_branch IS '交易机构';
COMMENT ON COLUMN ceba_charge_log.tx_tel IS '柜员号';
COMMENT ON COLUMN ceba_charge_log.bill_key IS '机表号';
COMMENT ON COLUMN ceba_charge_log.company_id IS '收费单位标示号';
COMMENT ON COLUMN ceba_charge_log.customer_name IS '客户姓名';
COMMENT ON COLUMN ceba_charge_log.pay_account IS '缴费账号';
COMMENT ON COLUMN ceba_charge_log.pay_amount IS '缴费金额（单位：分）';
COMMENT ON COLUMN ceba_charge_log.ac_type IS '账户类型，1-借记卡';
COMMENT ON COLUMN ceba_charge_log.contract_no IS '合同号';
COMMENT ON COLUMN ceba_charge_log.seq_no IS '来源渠道流水号';
COMMENT ON COLUMN ceba_charge_log.bank_bill_no IS '光大银行处理流水';
COMMENT ON COLUMN ceba_charge_log.receipt_no IS '打印凭证号码';
COMMENT ON COLUMN ceba_charge_log.acct_date IS '光大银行账务日期';
COMMENT ON COLUMN ceba_charge_log.pay_state IS '光大银行记账状态,0-登记，1-超时，2-处理成功，3-处理失败';
COMMENT ON COLUMN ceba_charge_log.error_code IS '光大银行处理失败时返回的错误代码';
COMMENT on column ceba_charge_log.host_check_state IS '核心对账标志，1-未对账，2-已对账，3-核心多，4-渠道多';
COMMENT on column ceba_charge_log.ceba_check_state IS '光大银行对账标志，1-未对账，2-已对账，3-光大银行多，4-渠道多';
COMMENT ON COLUMN ceba_charge_log.host_date IS '核心记账日期';
COMMENT ON COLUMN ceba_charge_log.host_traceno IS '核心记账流水号';
COMMENT ON COLUMN ceba_charge_log.host_state IS '核心记账状态，0-成功，1-冲正成功，2-冲正失败，3-冲正超时，4-处理超时';
COMMENT ON COLUMN ceba_charge_log.host_ret_code IS '核心反馈响应码';
COMMENT ON COLUMN ceba_charge_log.host_ret_msg IS '核心反馈响应信息';

ALTER TABLE ceba_charge_log ADD PRIMARY KEY (plat_date, plat_trace);