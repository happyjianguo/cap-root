DROP TABLE ceba_error_log;
CREATE TABLE ceba_error_log(
plat_date NUMBER(11) NOT NULL ,
plat_trace NUMBER(11) NOT NULL ,
plat_time NUMBER(11) NULL,
pre_pay_state VARCHAR2(10) NULL ,
re_pay_state VARCHAR2(10) NULL ,
check_flag VARCHAR2(10) NULL ,
tx_amt NUMBER NULL ,
bank_bill_no VARCHAR2(50) NULL ,
remark VARCHAR2(100) NULL
);
COMMENT ON TABLE ceba_error_log IS '光大云缴费对账问题数据表';
COMMENT ON COLUMN ceba_error_log.plat_date IS '渠道日期';
COMMENT ON COLUMN ceba_error_log.plat_trace IS '渠道流水';
COMMENT ON COLUMN ceba_error_log.plat_time IS '渠道时间';
COMMENT ON COLUMN ceba_error_log.pre_pay_state IS '调整前光大银行记账状态，0-登记，1-超时，2-处理成功，3-处理失败';
COMMENT ON COLUMN ceba_error_log.re_pay_state IS '调整后光大银行记账状态，0-登记，1-超时，2-处理成功，3-处理失败';
COMMENT ON COLUMN ceba_error_log.check_flag IS '对账标志，1-未对账，2-已对账，3-光大银行多，4-渠道多';
COMMENT ON COLUMN ceba_error_log.tx_amt IS '交易金额';
COMMENT ON COLUMN ceba_error_log.bank_bill_no IS '光大银行处理流水号';
COMMENT ON COLUMN ceba_error_log.remark IS '备注';

ALTER TABLE ceba_error_log ADD PRIMARY KEY (plat_date, plat_trace);