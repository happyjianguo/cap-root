DROP TABLE ceba_bill_info_log;
CREATE TABLE ceba_bill_info_log (
seq_no VARCHAR2(30) NOT NULL ,
bill_key VARCHAR2(35) NOT NULL ,
company_id VARCHAR2(30) NOT NULL ,
contract_no VARCHAR2(30) NULL ,
customer_name VARCHAR2(100) NULL ,
balance NUMBER NULL ,
pay_amount NUMBER NULL ,
begin_date VARCHAR2(10) NULL ,
end_date VARCHAR2(10) NULL ,
item1 VARCHAR2(100) NULL ,
item2 VARCHAR2(100) NULL ,
item3 VARCHAR2(100) NULL ,
item4 VARCHAR2(100) NULL ,
item5 VARCHAR2(100) NULL ,
item6 VARCHAR2(100) NULL ,
item7 VARCHAR2(100) NULL ,
filed1 VARCHAR2(100) NULL ,
filed2 VARCHAR2(100) NULL ,
filed3 VARCHAR2(100) NULL ,
filed4 VARCHAR2(100) NULL ,
filed5 VARCHAR2(100) NULL 
);
COMMENT ON TABLE ceba_bill_info_log IS '光大云缴费查询缴费单信息日志表';
COMMENT ON COLUMN ceba_bill_info_log.seq_no IS '查询方生成的流水号（如手机银行）';
COMMENT ON COLUMN ceba_bill_info_log.bill_key IS '机表号';
COMMENT ON COLUMN ceba_bill_info_log.company_id IS '收费单位标示号';
COMMENT ON COLUMN ceba_bill_info_log.contract_no IS '合同号';
COMMENT ON COLUMN ceba_bill_info_log.customer_name IS '客户姓名';
COMMENT ON COLUMN ceba_bill_info_log.balance IS '余额';
COMMENT ON COLUMN ceba_bill_info_log.pay_amount IS '应缴金额';
COMMENT ON COLUMN ceba_bill_info_log.begin_date IS '起始日期';
COMMENT ON COLUMN ceba_bill_info_log.end_date IS '截止日期';
COMMENT ON COLUMN ceba_bill_info_log.item1 IS '备用字段';
COMMENT ON COLUMN ceba_bill_info_log.item2 IS '备用字段';
COMMENT ON COLUMN ceba_bill_info_log.item3 IS '备用字段';
COMMENT ON COLUMN ceba_bill_info_log.item4 IS '备用字段';
COMMENT ON COLUMN ceba_bill_info_log.item5 IS '备用字段';
COMMENT ON COLUMN ceba_bill_info_log.item6 IS '备用字段';
COMMENT ON COLUMN ceba_bill_info_log.item7 IS '备用字段';
COMMENT ON COLUMN ceba_bill_info_log.filed1 IS '备用字段';
COMMENT ON COLUMN ceba_bill_info_log.filed2 IS '备用字段';
COMMENT ON COLUMN ceba_bill_info_log.filed3 IS '备用字段';
COMMENT ON COLUMN ceba_bill_info_log.filed4 IS '备用字段';
COMMENT ON COLUMN ceba_bill_info_log.filed5 IS '备用字段';

ALTER TABLE ceba_bill_info_log ADD PRIMARY KEY (seq_no);