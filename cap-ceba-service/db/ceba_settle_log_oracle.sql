DROP TABLE ceba_settle_log;
CREATE TABLE ceba_settle_log (
chk_date NUMBER(11) NOT NULL ,
tx_sts VARCHAR2(1) NOT NULL ,
cnaps_date NUMBER(11) NULL ,
cnaps_traceno VARCHAR2(20) NULL ,
cnaps_code VARCHAR2(20) NULL ,
cnaps_msg VARCHAR2(100) NULL
);
COMMENT ON TABLE ceba_settle_log IS '光大云缴费清算流水表';
COMMENT ON COLUMN ceba_settle_log.chk_date IS '对账日期';
COMMENT ON COLUMN ceba_settle_log.tx_sts IS '状态 （0:对账完成；1:等待重新汇款；2:汇款完成；3:汇款成功；4:汇款失败）';
COMMENT ON COLUMN ceba_settle_log.cnaps_date IS '二代记账日期';
COMMENT ON COLUMN ceba_settle_log.cnaps_traceno IS '二代记账流水号';
COMMENT ON COLUMN ceba_settle_log.cnaps_code IS '二代记账响应码';
COMMENT ON COLUMN ceba_settle_log.cnaps_msg IS '二代记账响应信息';

ALTER TABLE ceba_settle_log ADD PRIMARY KEY (chk_date);