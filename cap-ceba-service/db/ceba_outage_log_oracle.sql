DROP TABLE ceba_outage_log;
CREATE TABLE ceba_outage_log (
notice_time NUMBER(14) NOT NULL,
outage_notice_id VARCHAR2(20) NOT NULL ,
company_id VARCHAR2(30) NOT NULL ,
company_name VARCHAR2(50) NULL ,
outage_reason VARCHAR2(50) NULL ,
outage_desc VARCHAR2(50) NULL ,
outage_begin_time NUMBER(14) NULL ,
outage_end_time NUMBER(14) NULL , 
fix_date NUMBER(8) NULL,
fix_time NUMBER(6) NULL,
remark VARCHAR2(100) NULL
);

COMMENT ON TABLE ceba_outage_log IS '光大云缴费停运流水表';
COMMENT ON COLUMN ceba_outage_log.notice_time IS '停运通知文件上传日期和时间';
COMMENT ON COLUMN ceba_outage_log.outage_notice_id IS '停运通知号';
COMMENT ON COLUMN ceba_outage_log.company_id IS '缴费编号';
COMMENT ON COLUMN ceba_outage_log.company_name IS '缴费项目名称';
COMMENT ON COLUMN ceba_outage_log.outage_reason IS '停运原因';
COMMENT ON COLUMN ceba_outage_log.outage_desc IS '停运说明';
COMMENT ON COLUMN ceba_outage_log.outage_begin_time IS '停运起始时间';
COMMENT ON COLUMN ceba_outage_log.outage_end_time IS '停运终止时间';
COMMENT ON COLUMN ceba_outage_log.fix_date IS '维护日期';
COMMENT ON COLUMN ceba_outage_log.fix_time IS '维护时间';
COMMENT ON COLUMN ceba_outage_log.remark IS '备注';

ALTER TABLE ceba_outage_log ADD PRIMARY KEY (notice_time,company_id);