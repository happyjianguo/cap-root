
-- ----------------------------
--  Records of sys_permission
-- ----------------------------
insert into SYS_PERMISSION (ID, NAME, RESOURCE_TYPE, URL, PERMISSION, PARENT_ID, PARENT_IDS, AVAILABLE, IMG_URL)
values (14, '缴费单销账流水日志', 'menu', 'ceba/chargeLogList', 'ceba:view', 13, '0/13', 0, null);
insert into SYS_PERMISSION (ID, NAME, RESOURCE_TYPE, URL, PERMISSION, PARENT_ID, PARENT_IDS, AVAILABLE, IMG_URL)
values (15, '光大云缴费对账问题日志', 'menu', 'ceba/errorLogList', 'ceba:view', 13, '0/13', 0, 'ui/images/image1.jpg');
insert into SYS_PERMISSION (ID, NAME, RESOURCE_TYPE, URL, PERMISSION, PARENT_ID, PARENT_IDS, AVAILABLE, IMG_URL)
values (16, '光大云缴费退款流水日志', 'menu', 'ceba/refundeLogList', 'ceba:view', 13, '0/13', 0, 'ui/images/image1.jpg');
insert into SYS_PERMISSION (ID, NAME, RESOURCE_TYPE, URL, PERMISSION, PARENT_ID, PARENT_IDS, AVAILABLE, IMG_URL)
values (17, '光大云缴费清算流水日志', 'menu', 'ceba/settleLogList', 'ceba:view', 13, '0/13', 0, 'ui/images/image1.jpg');
insert into SYS_PERMISSION (ID, NAME, RESOURCE_TYPE, URL, PERMISSION, PARENT_ID, PARENT_IDS, AVAILABLE, IMG_URL)
values (13, '光大云缴费业务', 'menu', '1', '1', 0, '0', 0, 'ui/images/image1.jpg');
COMMIT;


-- ----------------------------
--  Records of sys_role_permission
-- ----------------------------
insert into SYS_ROLE_PERMISSION (PERMISSION_ID, ROLE_ID)
values (13, 1);
insert into SYS_ROLE_PERMISSION (PERMISSION_ID, ROLE_ID)
values (14, 1);
insert into SYS_ROLE_PERMISSION (PERMISSION_ID, ROLE_ID)
values (15, 1);
insert into SYS_ROLE_PERMISSION (PERMISSION_ID, ROLE_ID)
values (16, 1);
insert into SYS_ROLE_PERMISSION (PERMISSION_ID, ROLE_ID)
values (17, 1);
COMMIT;



