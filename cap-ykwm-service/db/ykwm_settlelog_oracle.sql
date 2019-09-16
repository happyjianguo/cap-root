DROP TABLE ykwm_settlelog;
CREATE TABLE ykwm_settlelog (
chk_date NUMBER(11) NOT NULL ,
tx_sts VARCHAR2(1) NOT NULL ,
host_date NUMBER(11) NULL ,
host_traceno VARCHAR2(20) NULL ,
host_code VARCHAR2(20) NULL ,
host_msg VARCHAR2(50) NULL ,
tx_amt NUMBER(16,2) NULL
);
COMMENT ON TABLE ykwm_settlelog IS '营口热力缴费清算流水表';
COMMENT ON COLUMN ykwm_settlelog.chk_date IS '对账日期';
COMMENT ON COLUMN ykwm_settlelog.tx_sts IS '状态 （0:对账完成；1:等待重新汇款；2:转账成功；3:转账失败；4:转账超时）';
COMMENT ON COLUMN ykwm_settlelog.host_date IS '核心记账日期';
COMMENT ON COLUMN ykwm_settlelog.host_traceno IS '核心记账流水号';
COMMENT ON COLUMN ykwm_settlelog.host_code IS '核心记账响应码';
COMMENT ON COLUMN ykwm_settlelog.host_msg IS '核心记账响应信息';
COMMENT ON COLUMN ykwm_settlelog.tx_amt IS '交易金额';

ALTER TABLE ykwm_settlelog ADD PRIMARY KEY (chk_date);
