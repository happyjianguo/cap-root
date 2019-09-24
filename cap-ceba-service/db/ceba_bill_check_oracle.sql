DROP TABLE ceba_bill_check;
CREATE TABLE ceba_bill_check (
sign_date NUMBER(8) NOT NULL ,
file_name VARCHAR2(50) NOT NULL ,
upload_date NUMBER(14) NOT NULL ,
status VARCHAR2(1) NOT NULL,
remark VARCHAR2(100) NULL
);
COMMENT ON TABLE ceba_bill_check IS '光大云缴费日终对账文件上传流水日志';
COMMENT ON COLUMN ceba_bill_check.sign_date IS '对账日期';
COMMENT ON COLUMN ceba_bill_check.file_name IS '文件名称';
COMMENT ON COLUMN ceba_bill_check.upload_date IS '文件上传日期和时间';
COMMENT ON COLUMN ceba_bill_check.status IS '状态：0-接收完成，1-导入成功，2-导入失败，3-对账成功，4-对账失败';
COMMENT ON COLUMN ceba_bill_check.remark IS '备注';

ALTER TABLE ceba_bill_check ADD PRIMARY KEY (sign_date,upload_date);