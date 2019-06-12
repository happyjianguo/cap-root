DROP TABLE YKWM_checklog;
CREATE TABLE ykwm_checklog (
plat_date NUMBER(11) NOT NULL ,
plat_trace NUMBER(11) NOT NULL ,
settle_date NUMBER(11) NULL ,
settle_branch VARCHAR2(50) NULL ,
host_date NUMBER(11) NULL ,
host_traceno VARCHAR2(20) NULL ,
ccy VARCHAR2(10) NULL ,
tx_amt NUMBER NULL ,
accountno VARCHAR2(50) NULL ,
reversal VARCHAR2(10) NULL ,
tx_status VARCHAR2(10) NULL 
);
COMMENT ON TABLE ykwm_checklog IS '营口热电日终对账核心数据';
COMMENT ON COLUMN ykwm_checklog.plat_date IS '渠道日期';
COMMENT ON COLUMN ykwm_checklog.plat_trace IS '渠道流水';
COMMENT ON COLUMN ykwm_checklog.settle_date IS '清算日期';
COMMENT ON COLUMN ykwm_checklog.settle_branch IS '清算机构';
COMMENT ON COLUMN ykwm_checklog.host_date IS '核心交易日期';
COMMENT ON COLUMN ykwm_checklog.host_traceno IS '核心流水号';
COMMENT ON COLUMN ykwm_checklog.ccy IS '交易币种';
COMMENT ON COLUMN ykwm_checklog.tx_amt IS '交易金额';
COMMENT ON COLUMN ykwm_checklog.accountno IS '交易账户';
COMMENT ON COLUMN ykwm_checklog.reversal IS '冲正标志';
COMMENT ON COLUMN ykwm_checklog.tx_status IS '交易状态；00-成功 02-冲正';

ALTER TABLE ykwm_checklog ADD PRIMARY KEY (plat_date, plat_trace);