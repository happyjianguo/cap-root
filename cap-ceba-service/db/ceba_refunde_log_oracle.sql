DROP TABLE ceba_refunde_log;
CREATE TABLE ceba_refunde_log (
plat_date NUMBER(11) NOT NULL ,
plat_traceno NUMBER(16) NOT NULL ,
flag VARCHAR2(1) NULL ,
status VARCHAR2(20) NULL,
host_code VARCHAR2(20) NULL,
host_msg VARCHAR2(100) NULL,
req_date NUMBER(11)
);
COMMENT ON TABLE ceba_refunde_log IS '光大云缴费退款流水表';
COMMENT ON COLUMN ceba_refunde_log.plat_date IS '渠道日期';
COMMENT ON COLUMN ceba_refunde_log.plat_traceno IS '渠道流水';
COMMENT ON COLUMN ceba_refunde_log.flag IS '来源类型，0对账退款，1退款文件退款';
COMMENT ON COLUMN ceba_refunde_log.status IS '退款状态，0未退款,1已退款,2退款失败,3退款超时,4销账流水日志未查到';
COMMENT ON COLUMN ceba_refunde_log.host_code IS '核心响应码';
COMMENT ON COLUMN ceba_refunde_log.host_msg IS '核心响应信息';
COMMENT ON COLUMN ceba_refunde_log.req_date IS '退款文件申请日期';


ALTER TABLE ceba_refunde_log ADD PRIMARY KEY (plat_date,plat_traceno);