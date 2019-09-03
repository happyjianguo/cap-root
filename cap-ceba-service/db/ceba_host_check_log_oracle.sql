DROP TABLE ceba_host_check_log;
CREATE TABLE ceba_host_check_log (
plat_date NUMBER(11) NOT NULL ,
plat_trace NUMBER(11) NOT NULL ,
tran_type NVARCHAR2(10) NULL ,
settle_date NUMBER(11) NULL ,
settle_branch NVARCHAR2(50) NULL ,
host_date NUMBER(11) NULL ,
host_traceno NVARCHAR2(20) NULL ,
ccy NVARCHAR2(10) NULL ,
tx_amt NUMBER NULL ,
accountno NVARCHAR2(50) NULL ,
reversal NVARCHAR2(10) NULL ,
tx_status NVARCHAR2(10) NULL 
)
;
COMMENT ON TABLE ceba_host_check_log IS '光大银行云缴费日终核心对账日志';
COMMENT ON COLUMN ceba_host_check_log.plat_date IS '渠道日期';
COMMENT ON COLUMN ceba_host_check_log.plat_trace IS '渠道流水';
COMMENT ON COLUMN ceba_host_check_log.tran_type IS '交易类型';
COMMENT ON COLUMN ceba_host_check_log.settle_date IS '清算日期';
COMMENT ON COLUMN ceba_host_check_log.settle_branch IS '清算机构';
COMMENT ON COLUMN ceba_host_check_log.host_date IS '核心交易日期';
COMMENT ON COLUMN ceba_host_check_log.host_traceno IS '核心流水号';
COMMENT ON COLUMN ceba_host_check_log.ccy IS '交易币种';
COMMENT ON COLUMN ceba_host_check_log.tx_amt IS '交易金额';
COMMENT ON COLUMN ceba_host_check_log.accountno IS '交易账户';
COMMENT ON COLUMN ceba_host_check_log.reversal IS '冲正标志';
COMMENT ON COLUMN ceba_host_check_log.tx_status IS '交易状态；00-成功 02-冲正';

ALTER TABLE ceba_host_check_log ADD PRIMARY KEY (plat_date, plat_trace);