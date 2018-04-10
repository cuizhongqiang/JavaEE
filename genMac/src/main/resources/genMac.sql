# SQL Manager 2007 Lite for MySQL 4.4.0.3
# ---------------------------------------
# Host     : localhost
# Port     : 3306
# Database : genMac


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

SET FOREIGN_KEY_CHECKS=0;

#
# Structure for the `adver` table : 
#

CREATE TABLE `adver` (
  `ID` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(255) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `IMG` VARCHAR(255) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `URL` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `SORT` INTEGER(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
)ENGINE=InnoDB
AUTO_INCREMENT=1 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `area_info` table : 
#

CREATE TABLE `area_info` (
  `ID` INTEGER(9) NOT NULL AUTO_INCREMENT,
  `AREA_CODE` VARCHAR(12) COLLATE utf8_general_ci DEFAULT NULL,
  `AREA_NAME` VARCHAR(50) COLLATE utf8_general_ci DEFAULT NULL,
  `PID` INTEGER(9) DEFAULT NULL,
  `SORT` INTEGER(3) DEFAULT NULL,
  PRIMARY KEY (`ID`)
)ENGINE=InnoDB
AUTO_INCREMENT=3 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `goods_type` table : 
#

CREATE TABLE `goods_type` (
  `ID` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `PID` INTEGER(11) DEFAULT NULL,
  `NAME` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `CODE` VARCHAR(30) COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
)ENGINE=InnoDB
AUTO_INCREMENT=14 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `commodity` table : 
#

CREATE TABLE `commodity` (
  `ID` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(255) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `COVER` VARCHAR(255) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `IMG` VARCHAR(255) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `TYPE_ID` INTEGER(11) NOT NULL,
  `PRICE` DOUBLE NOT NULL COMMENT '标价',
  `MARKET_PRICE` DOUBLE DEFAULT NULL COMMENT '市场价',
  `INTRODUCE` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '介绍',
  `BRIEF` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '摘要',
  `IS_SOLD` CHAR(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否上架',
  `SALES` INTEGER(11) DEFAULT NULL COMMENT '销量',
  `POSTAGE` DOUBLE DEFAULT NULL COMMENT '邮费',
  `PV` INTEGER(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `TYPE_ID` (`TYPE_ID`),
  CONSTRAINT `commodity_fk` FOREIGN KEY (`TYPE_ID`) REFERENCES `goods_type` (`ID`)
)ENGINE=InnoDB
AUTO_INCREMENT=6 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `dict` table : 
#

CREATE TABLE `dict` (
  `ID` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `LABEL` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `VALUE` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `TYPE` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `DESCRIPTION` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `SORT` INTEGER(11) DEFAULT NULL,
  `REMARK` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `DEL_FLAG` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
)ENGINE=InnoDB
AUTO_INCREMENT=7 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `goods` table : 
#

CREATE TABLE `goods` (
  `ID` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(255) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `COVER` VARCHAR(255) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `IMG` VARCHAR(255) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `GOODSTYPEID` INTEGER(11) DEFAULT NULL,
  `PRICE` DOUBLE NOT NULL COMMENT '标价',
  `MARKET_PRICE` DOUBLE DEFAULT NULL COMMENT '市场价',
  `INTRODUCE` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '介绍',
  `BRIEF` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '摘要',
  `IS_SOLD` CHAR(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否上架',
  `SALES` INTEGER(11) DEFAULT NULL COMMENT '销量',
  `POSTAGE` DOUBLE DEFAULT NULL COMMENT '邮费',
  `PV` INTEGER(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
)ENGINE=InnoDB
AUTO_INCREMENT=6 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `log` table : 
#

CREATE TABLE `log` (
  `ID` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `OPERATION_CODE` VARCHAR(50) COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '操作编码',
  `CREATER` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作用户名称',
  `CREATE_DATE` DATETIME NOT NULL COMMENT '日志生成时间',
  `TYPE` INTEGER(11) DEFAULT NULL COMMENT '日志类型: 1：安全日志 2：表示操作日志',
  `OS` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL,
  `BROWSER` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL COMMENT '浏览器类型',
  `IP` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL COMMENT 'IP地址',
  `MAC` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL COMMENT '物理地址',
  `EXECUTE_TIME` INTEGER(11) DEFAULT NULL COMMENT '执行时间',
  `DESCRIPTION` VARCHAR(500) COLLATE utf8_general_ci DEFAULT NULL COMMENT '详细描述',
  `REQUEST_PARAM` VARCHAR(500) COLLATE utf8_general_ci DEFAULT NULL COMMENT '请求参数',
  PRIMARY KEY (`ID`)
)ENGINE=InnoDB
AUTO_INCREMENT=2386 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'
COMMENT='日录资料表';

#
# Structure for the `organization` table : 
#

CREATE TABLE `organization` (
  `id` INTEGER(9) NOT NULL AUTO_INCREMENT,
  `org_name` VARCHAR(255) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `pid` INTEGER(9) DEFAULT NULL,
  `org_type` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `org_sort` INTEGER(3) DEFAULT '0',
  `org_level` INTEGER(3) DEFAULT NULL,
  `org_code` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `area_id` INTEGER(9) DEFAULT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB
AUTO_INCREMENT=4 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `permission` table : 
#

CREATE TABLE `permission` (
  `ID` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `PID` INTEGER(11) DEFAULT NULL COMMENT '父节点名称',
  `NAME` VARCHAR(50) COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `TYPE` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL COMMENT '类型:菜单or功能',
  `SORT` INTEGER(11) DEFAULT NULL COMMENT '排序',
  `URL` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `PERM_CODE` VARCHAR(50) COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单编码',
  `ICON` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `STATE` VARCHAR(10) COLLATE utf8_general_ci DEFAULT NULL,
  `DESCRIPTION` TEXT COLLATE utf8_general_ci,
  PRIMARY KEY (`ID`)
)ENGINE=InnoDB
AUTO_INCREMENT=83 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `qrtz_job_details` table : 
#

CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` VARCHAR(120) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `JOB_NAME` VARCHAR(200) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `JOB_GROUP` VARCHAR(200) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `DESCRIPTION` VARCHAR(250) COLLATE utf8_general_ci DEFAULT NULL,
  `JOB_CLASS_NAME` VARCHAR(250) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `IS_DURABLE` VARCHAR(1) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `IS_NONCONCURRENT` VARCHAR(1) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `IS_UPDATE_DATA` VARCHAR(1) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `REQUESTS_RECOVERY` VARCHAR(1) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `JOB_DATA` BLOB,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `qrtz_triggers` table : 
#

CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` VARCHAR(120) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `TRIGGER_NAME` VARCHAR(200) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `TRIGGER_GROUP` VARCHAR(200) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `JOB_NAME` VARCHAR(200) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `JOB_GROUP` VARCHAR(200) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `DESCRIPTION` VARCHAR(250) COLLATE utf8_general_ci DEFAULT NULL,
  `NEXT_FIRE_TIME` BIGINT(13) DEFAULT NULL,
  `PREV_FIRE_TIME` BIGINT(13) DEFAULT NULL,
  `PRIORITY` INTEGER(11) DEFAULT NULL,
  `TRIGGER_STATE` VARCHAR(16) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `TRIGGER_TYPE` VARCHAR(8) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `START_TIME` BIGINT(13) NOT NULL,
  `END_TIME` BIGINT(13) DEFAULT NULL,
  `CALENDAR_NAME` VARCHAR(200) COLLATE utf8_general_ci DEFAULT NULL,
  `MISFIRE_INSTR` SMALLINT(2) DEFAULT NULL,
  `JOB_DATA` BLOB,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `qrtz_blob_triggers` table : 
#

CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` VARCHAR(120) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `TRIGGER_NAME` VARCHAR(200) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `TRIGGER_GROUP` VARCHAR(200) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `BLOB_DATA` BLOB,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `qrtz_calendars` table : 
#

CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` VARCHAR(120) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `CALENDAR_NAME` VARCHAR(200) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `CALENDAR` BLOB NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `qrtz_cron_triggers` table : 
#

CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` VARCHAR(120) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `TRIGGER_NAME` VARCHAR(200) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `TRIGGER_GROUP` VARCHAR(200) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `CRON_EXPRESSION` VARCHAR(200) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `TIME_ZONE_ID` VARCHAR(80) COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `qrtz_fired_triggers` table : 
#

CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` VARCHAR(120) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `ENTRY_ID` VARCHAR(95) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `TRIGGER_NAME` VARCHAR(200) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `TRIGGER_GROUP` VARCHAR(200) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `INSTANCE_NAME` VARCHAR(200) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `FIRED_TIME` BIGINT(13) NOT NULL,
  `SCHED_TIME` BIGINT(13) NOT NULL,
  `PRIORITY` INTEGER(11) NOT NULL,
  `STATE` VARCHAR(16) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `JOB_NAME` VARCHAR(200) COLLATE utf8_general_ci DEFAULT NULL,
  `JOB_GROUP` VARCHAR(200) COLLATE utf8_general_ci DEFAULT NULL,
  `IS_NONCONCURRENT` VARCHAR(1) COLLATE utf8_general_ci DEFAULT NULL,
  `REQUESTS_RECOVERY` VARCHAR(1) COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `qrtz_locks` table : 
#

CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` VARCHAR(120) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `LOCK_NAME` VARCHAR(40) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `qrtz_paused_trigger_grps` table : 
#

CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` VARCHAR(120) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `TRIGGER_GROUP` VARCHAR(200) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `qrtz_scheduler_state` table : 
#

CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` VARCHAR(120) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `INSTANCE_NAME` VARCHAR(200) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `LAST_CHECKIN_TIME` BIGINT(13) NOT NULL,
  `CHECKIN_INTERVAL` BIGINT(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `qrtz_simple_triggers` table : 
#

CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` VARCHAR(120) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `TRIGGER_NAME` VARCHAR(200) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `TRIGGER_GROUP` VARCHAR(200) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `REPEAT_COUNT` BIGINT(7) NOT NULL,
  `REPEAT_INTERVAL` BIGINT(12) NOT NULL,
  `TIMES_TRIGGERED` BIGINT(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `qrtz_simprop_triggers` table : 
#

CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` VARCHAR(120) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `TRIGGER_NAME` VARCHAR(200) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `TRIGGER_GROUP` VARCHAR(200) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `STR_PROP_1` VARCHAR(512) COLLATE utf8_general_ci DEFAULT NULL,
  `STR_PROP_2` VARCHAR(512) COLLATE utf8_general_ci DEFAULT NULL,
  `STR_PROP_3` VARCHAR(512) COLLATE utf8_general_ci DEFAULT NULL,
  `INT_PROP_1` INTEGER(11) DEFAULT NULL,
  `INT_PROP_2` INTEGER(11) DEFAULT NULL,
  `LONG_PROP_1` BIGINT(20) DEFAULT NULL,
  `LONG_PROP_2` BIGINT(20) DEFAULT NULL,
  `DEC_PROP_1` DECIMAL(13,4) DEFAULT NULL,
  `DEC_PROP_2` DECIMAL(13,4) DEFAULT NULL,
  `BOOL_PROP_1` VARCHAR(1) COLLATE utf8_general_ci DEFAULT NULL,
  `BOOL_PROP_2` VARCHAR(1) COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
)ENGINE=InnoDB
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `role` table : 
#

CREATE TABLE `role` (
  `ID` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(20) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `ROLE_CODE` VARCHAR(20) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `DESCRIPTION` TEXT COLLATE utf8_general_ci,
  `SORT` SMALLINT(6) DEFAULT NULL,
  `DEL_FLAG` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
)ENGINE=InnoDB
AUTO_INCREMENT=14 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `role_permission` table : 
#

CREATE TABLE `role_permission` (
  `ID` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` INTEGER(11) DEFAULT NULL,
  `PERMISSION_ID` INTEGER(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_ROLE_PER_REFERENCE_PERMISSI` (`PERMISSION_ID`),
  KEY `FK_ROLE_PER_REFERENCE_ROLE` (`ROLE_ID`),
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`PERMISSION_ID`) REFERENCES `permission` (`ID`),
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ID`)
)ENGINE=InnoDB
AUTO_INCREMENT=253 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `user` table : 
#

CREATE TABLE `user` (
  `ID` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `LOGIN_NAME` VARCHAR(20) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `NAME` VARCHAR(20) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `PASSWORD` VARCHAR(255) COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `SALT` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `BIRTHDAY` DATETIME DEFAULT NULL,
  `GENDER` SMALLINT(6) DEFAULT NULL,
  `EMAIL` VARCHAR(255) COLLATE utf8_general_ci DEFAULT NULL,
  `PHONE` VARCHAR(20) COLLATE utf8_general_ci DEFAULT NULL,
  `ICON` VARCHAR(500) COLLATE utf8_general_ci DEFAULT NULL,
  `CREATE_DATE` DATETIME DEFAULT NULL,
  `STATE` CHAR(1) COLLATE utf8_general_ci DEFAULT NULL,
  `DESCRIPTION` TEXT COLLATE utf8_general_ci,
  `LOGIN_COUNT` INTEGER(11) DEFAULT NULL,
  `PREVIOUS_VISIT` DATETIME DEFAULT NULL,
  `LAST_VISIT` DATETIME DEFAULT NULL,
  `DEL_FLAG` VARCHAR(1) COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
)ENGINE=InnoDB
AUTO_INCREMENT=2 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `user_org` table : 
#

CREATE TABLE `user_org` (
  `id` INTEGER(9) NOT NULL AUTO_INCREMENT,
  `user_id` INTEGER(9) NOT NULL,
  `org_id` INTEGER(9) NOT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB
AUTO_INCREMENT=12 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Structure for the `user_role` table : 
#

CREATE TABLE `user_role` (
  `ID` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` INTEGER(11) NOT NULL,
  `ROLE_ID` INTEGER(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_USER_ROL_REFERENCE_ROLE` (`ROLE_ID`),
  KEY `FK_USER_ROL_REFERENCE_USERS` (`USER_ID`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ID`),
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`ID`)
)ENGINE=InnoDB
AUTO_INCREMENT=2 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

#
# Data for the `area_info` table  (LIMIT 0,500)
#

INSERT INTO `area_info` (`ID`, `AREA_CODE`, `AREA_NAME`, `PID`, `SORT`) VALUES 
  (1,'100000','中国',NULL,1),
  (2,'010','北京',1,NULL);
COMMIT;

#
# Data for the `goods_type` table  (LIMIT 0,500)
#

INSERT INTO `goods_type` (`ID`, `PID`, `NAME`, `CODE`) VALUES 
  (1,NULL,'球类','001'),
  (3,NULL,'手机·数码',NULL),
  (4,NULL,'家电·办公',NULL),
  (5,1,'篮球','001001'),
  (9,3,'笔记本',NULL),
  (10,3,'台式机',NULL),
  (11,4,'电视1',''),
  (12,NULL,'测试父类型',''),
  (13,12,'测试商品','');
COMMIT;

#
# Data for the `commodity` table  (LIMIT 0,500)
#

INSERT INTO `commodity` (`ID`, `NAME`, `COVER`, `IMG`, `TYPE_ID`, `PRICE`, `MARKET_PRICE`, `INTRODUCE`, `BRIEF`, `IS_SOLD`, `SALES`, `POSTAGE`, `PV`) VALUES 
  (1,'羽毛球','bb','bb',8,55,11,'11','','1',1,1,11),
  (2,'q','q','q',7,11.01,1,'1','','1',1,1,NULL),
  (4,'tt天天','tt','tt',7,1,NULL,'','','0',NULL,NULL,NULL),
  (5,'yy','yy','yy',8,33,NULL,'','','0',NULL,9,NULL);
COMMIT;

#
# Data for the `dict` table  (LIMIT 0,500)
#

INSERT INTO `dict` (`ID`, `LABEL`, `VALUE`, `TYPE`, `DESCRIPTION`, `SORT`, `REMARK`, `DEL_FLAG`) VALUES 
  (1,'正常','0','user','用户状态',1,NULL,NULL),
  (4,'普通用户','0','user','用户类型',NULL,NULL,NULL),
  (5,'会员用户','1','user','用户类型',NULL,NULL,NULL),
  (6,'1','1','1','1',NULL,NULL,NULL);
COMMIT;

#
# Data for the `goods` table  (LIMIT 0,500)
#

INSERT INTO `goods` (`ID`, `NAME`, `COVER`, `IMG`, `GOODSTYPEID`, `PRICE`, `MARKET_PRICE`, `INTRODUCE`, `BRIEF`, `IS_SOLD`, `SALES`, `POSTAGE`, `PV`) VALUES 
  (1,'羽毛球','bb','bb',8,55,11,'11','','1',1,1,11),
  (2,'q','q','q',7,11.01,1,'1','','1',1,1,NULL),
  (4,'tt天天','tt','tt',7,1,NULL,'','','0',NULL,NULL,NULL),
  (5,'yy','yy','yy',8,33,NULL,'','','0',NULL,9,NULL);
COMMIT;

#
# Data for the `organization` table  (LIMIT 0,500)
#

INSERT INTO `organization` (`id`, `org_name`, `pid`, `org_type`, `org_sort`, `org_level`, `org_code`, `area_id`) VALUES 
  (1,'中建材集团进出口公司',NULL,'总部',1,1,'000000',NULL),
  (2,'信息中心',1,'w',1,2,'000001',2),
  (3,'业务五部',1,'',2,2,'000002',2);
COMMIT;

#
# Data for the `permission` table  (LIMIT 0,500)
#

INSERT INTO `permission` (`ID`, `PID`, `NAME`, `TYPE`, `SORT`, `URL`, `PERM_CODE`, `ICON`, `STATE`, `DESCRIPTION`) VALUES 
  (1,NULL,'系统管理','F',1,'','','icon-standard-cog','',''),
  (2,1,'角色管理','F',3,'system/role','','icon-hamburg-my-account','closed',''),
  (3,1,'用户管理','F',2,'system/user','','icon-hamburg-user','closed',''),
  (4,2,'添加','O',NULL,'','sys:role:add','','','角色添加'),
  (5,2,'删除','O',NULL,'','sys:role:delete','','','角色删除'),
  (6,2,'修改','O',NULL,'','sys:role:update','','','角色修改'),
  (7,3,'添加','O',NULL,'','sys:user:add','','','用户添加'),
  (8,3,'删除','O',NULL,'','sys:user:delete','','','用户删除'),
  (12,1,'权限管理','F',5,'system/permission','','icon-hamburg-login','closed',''),
  (14,15,'数据源监控','F',6,'druid','','icon-hamburg-database','',''),
  (15,NULL,'系统监控','F',5,'','','icon-hamburg-graphic','',''),
  (16,3,'修改','O',NULL,'','sys:user:update','','','用户修改'),
  (20,15,'日志管理','F',7,'system/log','','icon-hamburg-archives','',''),
  (25,12,'添加','O',NULL,'','sys:perm:add','','','菜单添加'),
  (26,12,'修改','O',NULL,'','sys:perm:update','','','菜单修改'),
  (27,12,'删除','O',NULL,'','sys:perm:delete','','','菜单删除'),
  (28,2,'查看','O',NULL,'','sys:role:view','','','角色查看'),
  (29,3,'查看','O',NULL,'','sys:user:view','',NULL,'用户查看'),
  (30,12,'查看','O',NULL,'','sys:perm:view','',NULL,'权限查看'),
  (31,20,'删除','O',NULL,'','sys:log:delete','',NULL,'删除日志'),
  (32,20,'导出excel','O',NULL,'','sys:log:exportExcel','',NULL,'导出日志excel'),
  (33,3,'查看用户角色','O',NULL,'','sys:user:roleView','',NULL,'查看用户角色'),
  (34,2,'保存授权','O',NULL,'','sys:role:permUpd','',NULL,'保存修改的角色权限'),
  (35,3,'修改用户角色','O',NULL,'','sys:user:roleUpd','',NULL,'修改用户拥有的角色'),
  (36,2,'查看角色权限','O',NULL,'','sys:role:permView','',NULL,'查看角色拥有的权限'),
  (37,15,'定时任务管理','F',NULL,'system/scheduleJob','','icon-hamburg-full-time',NULL,'定时任务管理，支持集群'),
  (38,15,'cron表达式生成','F',NULL,'system/scheduleJob/quartzCron','','icon-hamburg-future',NULL,''),
  (39,1,'菜单管理','F',4,'system/permission/menu','','icon-hamburg-old-versions',NULL,''),
  (40,1,'字典管理','F',6,'system/dict',NULL,'icon-hamburg-address',NULL,'数据字典管理'),
  (45,39,'修改','O',NULL,'','sys:perm:update',NULL,NULL,'菜单管理'),
  (58,39,'添加','O',NULL,'','sys:perm:add',NULL,NULL,'菜单管理'),
  (59,39,'删除','O',NULL,'','sys:perm:delte',NULL,NULL,'菜单管理'),
  (61,40,'添加','O',NULL,'','sys:dict:add',NULL,NULL,'字典管理'),
  (62,40,'删除','O',NULL,'','sys:dict:delete',NULL,NULL,'字典管理'),
  (63,40,'修改','O',NULL,'','sys:dict:update',NULL,NULL,'字典管理'),
  (68,20,'查看','O',NULL,'','sys:log:view',NULL,NULL,'查看日志'),
  (69,40,'查看','O',NULL,'','sys:dict:view',NULL,NULL,'字典管理'),
  (70,39,'查看','O',NULL,'','sys:perm:menu:view',NULL,NULL,'菜单管理'),
  (74,1,'区域信息','F',7,'system/area',NULL,'icon-hamburg-world',NULL,'管理行政区划'),
  (75,1,'机构管理','F',8,'system/organization',NULL,'icon-cologne-home',NULL,'组织机构管理'),
  (76,3,'查看用户机构','O',NULL,'','sys:user:orgView',NULL,NULL,'查看用户机构'),
  (77,3,'修改用户机构','O',NULL,'','sys:user:orgUpd',NULL,NULL,'修改用户所在的机构'),
  (78,NULL,'商品示例','F',3,'',NULL,'icon-hamburg-basket',NULL,''),
  (79,78,'商品类型','F',1,'shop/goodsType',NULL,'icon-hamburg-archives',NULL,''),
  (80,78,'商品列表','F',2,'shop/goods',NULL,'icon-hamburg-cost',NULL,''),
  (81,82,'商品外键关联','F',3,'shop/commodity',NULL,'icon-hamburg-current-work',NULL,''),
  (82,78,'测试层级','F',0,'',NULL,'icon-hamburg-archives',NULL,'');
COMMIT;

#
# Data for the `qrtz_job_details` table  (LIMIT 0,500)
#

INSERT INTO `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`, `DESCRIPTION`, `JOB_CLASS_NAME`, `IS_DURABLE`, `IS_NONCONCURRENT`, `IS_UPDATE_DATA`, `REQUESTS_RECOVERY`, `JOB_DATA`) VALUES 
  ('scheduler','test1','testgroup',NULL,'com.tianyu.jty.system.service.TaskA','0','1','0','0',0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000B7363686564756C654A6F6273720028636F6D2E7469616E79752E6A74792E73797374656D2E656E746974792E5363686564756C654A6F6200000000000000010200064C0009636C6173734E616D657400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C000B6465736372697074696F6E71007E00094C000567726F757071007E00094C00046E616D6571007E00094C000673746174757371007E00097870740023636F6D2E7469616E79752E6A74792E73797374656D2E736572766963652E5461736B4174000D302F35202A202A202A202A203F707400097465737467726F75707400057465737431740001317800);
COMMIT;

#
# Data for the `qrtz_triggers` table  (LIMIT 0,500)
#

INSERT INTO `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `JOB_NAME`, `JOB_GROUP`, `DESCRIPTION`, `NEXT_FIRE_TIME`, `PREV_FIRE_TIME`, `PRIORITY`, `TRIGGER_STATE`, `TRIGGER_TYPE`, `START_TIME`, `END_TIME`, `CALENDAR_NAME`, `MISFIRE_INSTR`, `JOB_DATA`) VALUES 
  ('scheduler','test1','testgroup','test1','testgroup',NULL,1421206460000,1421206455000,5,'PAUSED','CRON',1421206412000,0,NULL,0,'');
COMMIT;

#
# Data for the `qrtz_cron_triggers` table  (LIMIT 0,500)
#

INSERT INTO `qrtz_cron_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `CRON_EXPRESSION`, `TIME_ZONE_ID`) VALUES 
  ('scheduler','test1','testgroup','0/5 * * * * ?','Asia/Shanghai');
COMMIT;

#
# Data for the `qrtz_locks` table  (LIMIT 0,500)
#

INSERT INTO `qrtz_locks` (`SCHED_NAME`, `LOCK_NAME`) VALUES 
  ('scheduler','STATE_ACCESS'),
  ('scheduler','TRIGGER_ACCESS');
COMMIT;

#
# Data for the `qrtz_scheduler_state` table  (LIMIT 0,500)
#

INSERT INTO `qrtz_scheduler_state` (`SCHED_NAME`, `INSTANCE_NAME`, `LAST_CHECKIN_TIME`, `CHECKIN_INTERVAL`) VALUES 
  ('scheduler','Tony-TOP1441933417101',1441964971367,15000);
COMMIT;

#
# Data for the `role` table  (LIMIT 0,500)
#

INSERT INTO `role` (`ID`, `NAME`, `ROLE_CODE`, `DESCRIPTION`, `SORT`, `DEL_FLAG`) VALUES 
  (1,'admin','admin','admin',2,NULL),
  (5,'guest','guest','guest',3,NULL),
  (13,'superadmin','superadmin','超级管理员',1,NULL);
COMMIT;

#
# Data for the `role_permission` table  (LIMIT 0,500)
#

INSERT INTO `role_permission` (`ID`, `ROLE_ID`, `PERMISSION_ID`) VALUES 
  (1,1,2),
  (2,1,1),
  (28,5,1),
  (61,13,1),
  (62,13,3),
  (63,13,16),
  (64,13,7),
  (65,13,2),
  (66,13,4),
  (67,13,5),
  (68,13,6),
  (69,13,12),
  (70,13,25),
  (71,13,26),
  (72,13,27),
  (74,13,15),
  (75,13,14),
  (76,13,20),
  (77,13,8),
  (81,1,3),
  (88,1,12),
  (93,1,15),
  (94,1,14),
  (95,1,20),
  (118,1,28),
  (120,1,30),
  (121,1,31),
  (125,1,33),
  (126,1,36),
  (127,1,35),
  (129,1,34),
  (130,1,32),
  (133,5,15),
  (135,1,37),
  (142,1,38),
  (145,1,40),
  (147,1,29),
  (151,1,61),
  (152,1,62),
  (153,1,63),
  (162,5,39),
  (164,5,58),
  (176,5,40),
  (177,1,39),
  (178,1,58),
  (179,1,59),
  (183,1,4),
  (184,1,6),
  (185,1,26),
  (186,1,27),
  (187,1,5),
  (189,1,25),
  (190,1,45),
  (191,1,7),
  (192,1,8),
  (193,1,16),
  (194,13,28),
  (195,13,34),
  (196,13,36),
  (197,13,29),
  (198,13,33),
  (199,13,35),
  (200,13,30),
  (201,13,39),
  (202,13,45),
  (203,13,58),
  (204,13,59),
  (205,13,40),
  (206,13,61),
  (207,13,62),
  (208,13,63),
  (209,13,31),
  (210,13,32),
  (211,13,37),
  (212,13,38),
  (213,1,69),
  (215,5,69),
  (216,5,20),
  (219,5,68),
  (220,5,38),
  (221,1,70),
  (222,5,70),
  (223,5,3),
  (227,5,29),
  (228,5,33),
  (229,5,35),
  (231,5,2),
  (234,5,28),
  (235,5,45),
  (236,5,59),
  (239,5,36),
  (240,1,68),
  (244,1,74),
  (245,1,75),
  (246,1,76),
  (247,1,77),
  (248,1,79),
  (249,1,78),
  (250,1,80),
  (251,1,81),
  (252,1,82);
COMMIT;

#
# Data for the `user` table  (LIMIT 0,500)
#

INSERT INTO `user` (`ID`, `LOGIN_NAME`, `NAME`, `PASSWORD`, `SALT`, `BIRTHDAY`, `GENDER`, `EMAIL`, `PHONE`, `ICON`, `CREATE_DATE`, `STATE`, `DESCRIPTION`, `LOGIN_COUNT`, `PREVIOUS_VISIT`, `LAST_VISIT`, `DEL_FLAG`) VALUES 
  (1,'admin','admin','c092e6436c756224735b81b55d94505abaf821db','9fd729b0ace3921e','2014-03-16 22:44:39',1,'779205344@qq.com','123456789','aaa','2014-03-20 14:38:57','0',NULL,134,'2015-09-06 09:42:42','2015-09-10 14:14:36',NULL);
COMMIT;

#
# Data for the `user_org` table  (LIMIT 0,500)
#

INSERT INTO `user_org` (`id`, `user_id`, `org_id`) VALUES 
  (8,6,1),
  (10,3,3),
  (11,1,3);
COMMIT;

#
# Data for the `user_role` table  (LIMIT 0,500)
#

INSERT INTO `user_role` (`ID`, `USER_ID`, `ROLE_ID`) VALUES 
  (1,1,1);
COMMIT;



/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;