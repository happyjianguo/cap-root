DROP TABLE ceba_error_log;
CREATE TABLE ceba_error_log(
plat_date NUMBER(11) NOT NULL ,
plat_trace NUMBER(11) NOT NULL ,
plat_time NUMBER(11) NULL,
pre_pay_state VARCHAR2(10) NULL ,
re_pay_state VARCHAR2(10) NULL ,
ceba_check_flag VARCHAR2(10) NULL ,
pre_host_state VARCHAR2(10) NULL ,
re_host_state VARCHAR2(10) NULL ,
host_check_flag VARCHAR2(10) NULL ,
tx_amt NUMBER NULL ,
host_date NUMBER(11) NULL,
host_traceno NVARCHAR2(20) NULL,
bank_bill_no VARCHAR2(50) NULL ,
remark VARCHAR2(100) NULL
);
COMMENT ON TABLE ceba_error_log IS '光大云缴费对账问题数据表';
COMMENT ON COLUMN ceba_error_log.plat_date IS '渠道日期';
COMMENT ON COLUMN ceba_error_log.plat_trace IS '渠道流水';
COMMENT ON COLUMN ceba_error_log.plat_time IS '渠道时间';
COMMENT ON COLUMN ceba_error_log.pre_pay_state IS '调整前光大银行记账状态，0-登记，1-超时，2-处理成功，3-处理失败';
COMMENT ON COLUMN ceba_error_log.re_pay_state IS '调整后光大银行记账状态，0-登记，1-超时，2-处理成功，3-处理失败';
COMMENT ON COLUMN ceba_error_log.ceba_check_flag IS '光大银行对账标志，1-记账状态不符，2-光大银行多，3-渠道多';
COMMENT ON COLUMN ceba_error_log.pre_host_state IS '调整前核心记账状态，0-成功，1-冲正成功，2-冲正失败，3-冲正超时，4-超时';
COMMENT ON COLUMN ceba_error_log.re_host_state IS '调整后核心记账状态，0-成功，1-冲正成功，2-冲正失败，3-冲正超时，4-超时';
COMMENT ON COLUMN ceba_error_log.host_check_flag IS '核心对账标志，1-记账状态不符，2-核心多，3-渠道多';
COMMENT ON COLUMN ceba_error_log.tx_amt IS '交易金额';
COMMENT ON COLUMN ceba_error_log.host_date IS '核心记账日期';
COMMENT ON COLUMN ceba_error_log.host_traceno IS '核心记账流水号';
COMMENT ON COLUMN ceba_error_log.bank_bill_no IS '光大银行处理流水号';
COMMENT ON COLUMN ceba_error_log.remark IS '备注';

ALTER TABLE ceba_error_log ADD PRIMARY KEY (plat_date, plat_trace);