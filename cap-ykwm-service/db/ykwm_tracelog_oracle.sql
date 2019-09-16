drop table YKWM_TRACELOG;
create table YKWM_TRACELOG
(
  PY_TRANSACTIONNO   VARCHAR2(30),
  CO_TRANSACTIONNO   VARCHAR2(30),
  CAP_TRANSACTIONNO  VARCHAR2(30) not null,
  PY_RESULT          VARCHAR2(1),
  CO_RESULT          VARCHAR2(1),
  CAP_RESULT         VARCHAR2(1),
  CAP_REPMSG         VARCHAR2(20),
  PY_DATE            VARCHAR2(16),
  CO_DATE            VARCHAR2(16),
  CAP_DATE           VARCHAR2(16) not null,
  TE_BRANCHNO        VARCHAR2(10),
  TE_NAME            VARCHAR2(10),
  TE_VIRTUAL_NAME    VARCHAR2(16),
  TE_CHECK_NUM       VARCHAR2(20),
  ACCT_NO_T          VARCHAR2(20),
  PY_FEE_AMT_T       VARCHAR2(20),
  USER_DBT_AMT_T     VARCHAR2(20),
  COURIER_AMT_T      VARCHAR2(20),
  REIMBURSE_SIGN_T   VARCHAR2(20),
  HEAT_SEQ_NO_T      VARCHAR2(20),
  USER_CARD_NO_T     VARCHAR2(20),
  CNTT_PHN_T         VARCHAR2(20),
  LNM_T3             VARCHAR2(20),
  PY_FEE_TP_T        VARCHAR2(10),
  BILL_GET_TP_T      VARCHAR2(10),
  COMPANY_T          VARCHAR2(20),
  HEAT_COMPANY_ID_T  VARCHAR2(20),
  USER_ADDR_T        VARCHAR2(100),
  MAIL_ADDR_T        VARCHAR2(100),
  PWD_T              VARCHAR2(20),
  INVC_NA_HD_T3      VARCHAR2(100),
  HEAT_COMPANY_NM_T  VARCHAR2(100),
  NA_T1              VARCHAR2(50),
  POST_NO_T5         VARCHAR2(10),
  COURIER_CMPNY_ID_T VARCHAR2(10),
  PY_SOURCE          VARCHAR2(20),
  PY_BRANCHNUM       VARCHAR2(10),
  PY_BATCHNUM        VARCHAR2(8),
  EX_ID              VARCHAR2(1),
  EX_NAME            VARCHAR2(20),
  EX_AMT             NUMBER(16,2),
  DT_CHARGEYEAR      VARCHAR2(9),
  DT_ITEMNAME        VARCHAR2(20),
  DT_AREA            NUMBER(15),
  DT_PRICE           NUMBER(10),
  DT_ACCOUNT         NUMBER(15),
  DT_AGIO            NUMBER(15),
  DT_LATEFEE         NUMBER(15),
  DT_PAYMENT         NUMBER(15),
  INVOICETITLE1      VARCHAR2(200),
  AREA1              NUMBER(15),
  INVOICENAME1       VARCHAR2(100),
  INVOICENUM1        VARCHAR2(50),
  BANKNUM1           VARCHAR2(50),
  INVOICEADDRESS1    VARCHAR2(50),
  INVOICETITLE2      VARCHAR2(200),
  AREA2              NUMBER(15),
  INVOICENAME2       VARCHAR2(100),
  INVOICENUM2        VARCHAR2(50),
  BANKNUM2           VARCHAR2(50),
  INVOICEADDRESS2    VARCHAR2(50),
  INVOICETITLE3      VARCHAR2(200),
  AREA3              NUMBER(15),
  INVOICENAME3       VARCHAR2(100),
  INVOICENUM3        VARCHAR2(50),
  BANKNUM3           VARCHAR2(50),
  INVOICEADDRESS3    VARCHAR2(50),
  INVOICETITLE4      VARCHAR2(200),
  AREA4              NUMBER(15),
  INVOICENAME4       VARCHAR2(100),
  INVOICENUM4        VARCHAR2(50),
  BANKNUM4           VARCHAR2(50),
  INVOICEADDRESS4    VARCHAR2(50),
  TICKET_NUMBER      VARCHAR2(100),
  PY_RSPCODE         VARCHAR2(1),
  PY_ERROR_MSG       VARCHAR2(100),
  CO_RSPCODE         VARCHAR2(20),
  CO_RSPMSG          VARCHAR2(100)
);
-- Add comments to the table 
comment on table YKWM_TRACELOG
  is '营口缴费流水表';
-- Add comments to the columns 
comment on column YKWM_TRACELOG.PY_TRANSACTIONNO
  is '热电流水';
comment on column YKWM_TRACELOG.CO_TRANSACTIONNO
  is '核心流水';
comment on column YKWM_TRACELOG.CAP_TRANSACTIONNO
  is '渠道流水';
comment on column YKWM_TRACELOG.PY_RESULT
  is '热电交易状态 0-登记，1-超时，2-处理成功，3-处理失败，4-冲正成功，5-冲正超时';
comment on column YKWM_TRACELOG.CO_RESULT
  is '核心交易状态 0-成功，1-冲正成功，2-冲正失败，3-冲正超时，4-超时';
comment on column YKWM_TRACELOG.CAP_RESULT
  is '对账标志，1-未对账，2-已对账，3-核心多，4-渠道多';
comment on column YKWM_TRACELOG.PY_DATE
  is '热电日期';
comment on column YKWM_TRACELOG.CO_DATE
  is '核心日期';
comment on column YKWM_TRACELOG.CAP_DATE
  is '渠道日期';
comment on column YKWM_TRACELOG.TE_BRANCHNO
  is '所属机构';
comment on column YKWM_TRACELOG.TE_NAME
  is '操作柜员';
comment on column YKWM_TRACELOG.TE_VIRTUAL_NAME
  is '虚拟柜员';
comment on column YKWM_TRACELOG.TE_CHECK_NUM
  is '查询流水号';
comment on column YKWM_TRACELOG.ACCT_NO_T
  is '用户账号';
comment on column YKWM_TRACELOG.PY_FEE_AMT_T
  is '缴费金额';
comment on column YKWM_TRACELOG.USER_DBT_AMT_T
  is '用户欠费金额';
comment on column YKWM_TRACELOG.COURIER_AMT_T
  is '快递金额';
comment on column YKWM_TRACELOG.REIMBURSE_SIGN_T
  is '报销标志';
comment on column YKWM_TRACELOG.HEAT_SEQ_NO_T
  is '热力流水号';
comment on column YKWM_TRACELOG.USER_CARD_NO_T
  is '用户卡号';
comment on column YKWM_TRACELOG.CNTT_PHN_T
  is '联系电话';
comment on column YKWM_TRACELOG.LNM_T3
  is '联系人';
comment on column YKWM_TRACELOG.PY_FEE_TP_T
  is '缴费方式';
comment on column YKWM_TRACELOG.BILL_GET_TP_T
  is '发票获取方式 0未选择，1邮寄，2自取，3电子发票';
comment on column YKWM_TRACELOG.COMPANY_T
  is '公司';
comment on column YKWM_TRACELOG.HEAT_COMPANY_ID_T
  is '供暖公司ID';
comment on column YKWM_TRACELOG.USER_ADDR_T
  is '用户地址';
comment on column YKWM_TRACELOG.MAIL_ADDR_T
  is '邮寄地址';
comment on column YKWM_TRACELOG.PWD_T
  is '密码';
comment on column YKWM_TRACELOG.INVC_NA_HD_T3
  is '发票名头';
comment on column YKWM_TRACELOG.HEAT_COMPANY_NM_T
  is '供暖公司名';
comment on column YKWM_TRACELOG.NA_T1
  is '姓名';
comment on column YKWM_TRACELOG.POST_NO_T5
  is '邮编';
comment on column YKWM_TRACELOG.COURIER_CMPNY_ID_T
  is '快递公司ID';
comment on column YKWM_TRACELOG.PY_SOURCE
  is '缴费渠道（CTS:柜面、MBANK:手机银行、SBANK：自助、EBANK:个人网银、WBANK：微信）';
comment on column YKWM_TRACELOG.PY_BRANCHNUM
  is '网点编号';
comment on column YKWM_TRACELOG.PY_BATCHNUM
  is '批次号，银行方提供的对账批次号，缴费时需要提交此批次号，如果查询和缴费时的批次号不一致，将导致缴费失败';
comment on column YKWM_TRACELOG.EX_ID
  is '快递公司ID';
comment on column YKWM_TRACELOG.EX_NAME
  is '快递公司名称';
comment on column YKWM_TRACELOG.EX_AMT
  is '快递费';
comment on column YKWM_TRACELOG.DT_CHARGEYEAR
  is '供暖年度';
comment on column YKWM_TRACELOG.DT_ITEMNAME
  is '供暖类型';
comment on column YKWM_TRACELOG.DT_AREA
  is '供暖面积';
comment on column YKWM_TRACELOG.DT_PRICE
  is '供暖单价';
comment on column YKWM_TRACELOG.DT_ACCOUNT
  is '欠费金额';
comment on column YKWM_TRACELOG.DT_AGIO
  is '优惠金额';
comment on column YKWM_TRACELOG.DT_LATEFEE
  is '滞纳金';
comment on column YKWM_TRACELOG.DT_PAYMENT
  is '应交金额';
comment on column YKWM_TRACELOG.INVOICETITLE1
  is '第一张发票抬头';
comment on column YKWM_TRACELOG.AREA1
  is '第一张发票面积';
comment on column YKWM_TRACELOG.INVOICENAME1
  is '第一张发票姓名';
comment on column YKWM_TRACELOG.INVOICENUM1
  is '第一张发票的纳税人识别号';
comment on column YKWM_TRACELOG.BANKNUM1
  is '第一张发票的开户行账号';
comment on column YKWM_TRACELOG.INVOICEADDRESS1
  is '第一张发票地址电话';
comment on column YKWM_TRACELOG.INVOICETITLE2
  is '第二张发票抬头';
comment on column YKWM_TRACELOG.AREA2
  is '第二张发票面积';
comment on column YKWM_TRACELOG.INVOICENAME2
  is '第二张发票姓名';
comment on column YKWM_TRACELOG.INVOICENUM2
  is '第二张发票的纳税人识别号';
comment on column YKWM_TRACELOG.BANKNUM2
  is '第二张发票的开户行账号';
comment on column YKWM_TRACELOG.INVOICEADDRESS2
  is '第二张发票地址电话';
comment on column YKWM_TRACELOG.INVOICETITLE3
  is '第三张发票抬头';
comment on column YKWM_TRACELOG.AREA3
  is '第三张发票面积';
comment on column YKWM_TRACELOG.INVOICENAME3
  is '第三张发票姓名';
comment on column YKWM_TRACELOG.INVOICENUM3
  is '第三张发票的纳税人识别号';
comment on column YKWM_TRACELOG.BANKNUM3
  is '第三张发票的开户行账号';
comment on column YKWM_TRACELOG.INVOICEADDRESS3
  is '第三张发票地址电话';
comment on column YKWM_TRACELOG.INVOICETITLE4
  is '第四张发票抬头';
comment on column YKWM_TRACELOG.AREA4
  is '第四张发票面积';
comment on column YKWM_TRACELOG.INVOICENAME4
  is '第四张发票姓名';
comment on column YKWM_TRACELOG.INVOICENUM4
  is '第四张发票的纳税人识别号';
comment on column YKWM_TRACELOG.BANKNUM4
  is '第四张发票的开户行账号';
comment on column YKWM_TRACELOG.INVOICEADDRESS4
  is '第四张发票地址电话';
comment on column YKWM_TRACELOG.TICKET_NUMBER
  is '取票码列表';
comment on column YKWM_TRACELOG.PY_RSPCODE
  is '热电响应码';
comment on column YKWM_TRACELOG.PY_ERROR_MSG
  is '热电错误信息';
comment on column YKWM_TRACELOG.CO_RSPCODE
  is '核心响应码';
comment on column YKWM_TRACELOG.CO_RSPMSG
  is '核心响应信息';
-- Create/Recreate primary, unique and foreign key constraints 
alter table YKWM_TRACELOG add primary key (CAP_TRANSACTIONNO, CAP_DATE)