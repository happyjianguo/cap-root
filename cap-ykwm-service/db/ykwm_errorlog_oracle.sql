DROP TABLE ykwm_errorlog;
CREATE TABLE ykwm_errorlog(
plat_date NUMBER(11) NOT NULL ,
plat_trace NUMBER(11) NOT NULL ,
pre_host_state VARCHAR2(10) NULL ,
re_host_state VARCHAR2(10) NULL ,
check_flag VARCHAR2(10) NULL ,
tx_amt NUMBER NULL ,
user_name VARCHAR2(50) NULL ,
acct_no VARCHAR2(50) NULL ,
user_card VARCHAR2(50) NULL,
remark VARCHAR2(100) NULL
);
COMMENT ON TABLE ykwm_errorlog IS '营口热电对账问题数据表';
COMMENT ON COLUMN ykwm_errorlog.plat_date IS '渠道日期';
COMMENT ON COLUMN ykwm_errorlog.plat_trace IS '渠道流水';
COMMENT ON COLUMN ykwm_errorlog.pre_host_state IS '调整前核心记账状态，0-成功，1-冲正成功，2-冲正失败，3-冲正超时，4-超时';
COMMENT ON COLUMN ykwm_errorlog.re_host_state IS '调整后核心记账状态，0-成功，1-冲正成功，2-冲正失败，3-冲正超时，4-超时';
COMMENT ON COLUMN ykwm_errorlog.check_flag IS '对账标志，1-未对账，2-已对账，3-核心多，4-渠道多';
COMMENT ON COLUMN ykwm_errorlog.tx_amt IS '交易金额';
COMMENT ON COLUMN ykwm_errorlog.acct_no IS '账号';
COMMENT ON COLUMN ykwm_errorlog.user_name IS '用户名';
COMMENT ON COLUMN ykwm_errorlog.user_card IS '用户卡号';
COMMENT ON COLUMN ykwm_errorlog.remark IS '备注';

ALTER TABLE ykwm_errorlog ADD PRIMARY KEY (plat_date, plat_trace);