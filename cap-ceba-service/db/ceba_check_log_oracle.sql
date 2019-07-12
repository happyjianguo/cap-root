DROP TABLE ceba_check_log;
CREATE TABLE ceba_check_log (
plat_date NUMBER(11) NOT NULL ,
plat_trace NUMBER(11) NOT NULL ,
tx_amt NUMBER(16) NULL ,
plat_time NUMBER(11) NOT NULL ,
bank_bill_no VARCHAR2(20) NULL 
);
COMMENT ON TABLE ceba_check_log IS '光大云缴费日终对账光大银行数据';
COMMENT ON COLUMN ceba_check_log.plat_date IS '渠道日期';
COMMENT ON COLUMN ceba_check_log.plat_trace IS '渠道流水';
COMMENT ON COLUMN ceba_check_log.tx_amt IS '交易金额';
COMMENT ON COLUMN ceba_check_log.plat_time IS '渠道时间';
COMMENT ON COLUMN ceba_check_log.bank_bill_no IS '光大银行流水号';

ALTER TABLE ceba_check_log ADD PRIMARY KEY (plat_date, plat_trace);