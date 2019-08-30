DROP TABLE YKWM_checklog;
CREATE TABLE ykwm_checklog (
plat_date NUMBER(11) NOT NULL ,
plat_trace NUMBER(11) NOT NULL ,
tel_no VARCHAR2(50) NULL ,
branch_no VARCHAR2(50) NULL ,
host_date NUMBER(11) NULL ,
host_traceno VARCHAR2(20) NULL ,
tx_amt NUMBER NULL ,
accountno VARCHAR2(50) NULL
);
COMMENT ON TABLE ykwm_checklog IS '营口热电日终对账核心数据';
COMMENT ON COLUMN ykwm_checklog.plat_date IS '渠道日期';
COMMENT ON COLUMN ykwm_checklog.plat_trace IS '渠道流水';
COMMENT ON COLUMN ykwm_checklog.tel_no IS '柜员号';
COMMENT ON COLUMN ykwm_checklog.branch_no IS '机构号';
COMMENT ON COLUMN ykwm_checklog.host_date IS '核心交易日期';
COMMENT ON COLUMN ykwm_checklog.host_traceno IS '核心流水号';
COMMENT ON COLUMN ykwm_checklog.tx_amt IS '交易金额';
COMMENT ON COLUMN ykwm_checklog.accountno IS '交易账户';

ALTER TABLE ykwm_checklog ADD PRIMARY KEY (plat_date, plat_trace);