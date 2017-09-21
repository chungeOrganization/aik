/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.18 : Database - aikang
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`aikang` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `aikang`;

/*Table structure for table `acc_circleComment` */

DROP TABLE IF EXISTS `acc_circleComment`;

CREATE TABLE `acc_circleComment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `circleId` int(11) NOT NULL COMMENT '互助圈id',
  `commenterId` int(11) NOT NULL COMMENT '评论人id',
  `content` varchar(1024) DEFAULT NULL COMMENT '评论内容',
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='互助圈评论';

/*Table structure for table `acc_circleLike` */

DROP TABLE IF EXISTS `acc_circleLike`;

CREATE TABLE `acc_circleLike` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `circleId` int(11) NOT NULL COMMENT '互助圈id',
  `likerId` int(11) NOT NULL COMMENT '点赞人id',
  `createDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='互助圈点赞';

/*Table structure for table `acc_doctorAccount` */

DROP TABLE IF EXISTS `acc_doctorAccount`;

CREATE TABLE `acc_doctorAccount` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `mobileNo` varchar(16) NOT NULL COMMENT '手机号',
  `userName` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `realName` varchar(32) DEFAULT NULL COMMENT '真实姓名',
  `sex` tinyint(4) unsigned DEFAULT '0' COMMENT '0：男 1：女',
  `areaProvince` varchar(32) DEFAULT NULL COMMENT '地区-省',
  `areaCity` varchar(32) DEFAULT NULL COMMENT '地区-市',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `identityCard` varchar(32) DEFAULT NULL COMMENT '身份证',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `hosName` varchar(32) DEFAULT NULL COMMENT '执行医院',
  `hosDepartment` varchar(32) DEFAULT NULL COMMENT '科室',
  `skill` varchar(512) DEFAULT NULL COMMENT '擅长病症',
  `departmentPhone` varchar(32) DEFAULT NULL COMMENT '科室电话',
  `devType` varchar(16) DEFAULT NULL COMMENT 'IOS、Andriod',
  `headImg` varchar(128) DEFAULT NULL COMMENT '头像',
  `position` tinyint(4) DEFAULT NULL COMMENT '职位',
  `price` decimal(18,2) DEFAULT '0.00' COMMENT '回答问题佣金',
  `starLevel` decimal(18,2) DEFAULT '0.00' COMMENT '星级',
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='医生账户';

/*Table structure for table `acc_doctorAttention` */

DROP TABLE IF EXISTS `acc_doctorAttention`;

CREATE TABLE `acc_doctorAttention` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `doctorId` int(11) NOT NULL COMMENT '医生id',
  `userId` int(11) NOT NULL COMMENT '用户id',
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='医生关注';

/*Table structure for table `acc_doctorBankCard` */

DROP TABLE IF EXISTS `acc_doctorBankCard`;

CREATE TABLE `acc_doctorBankCard` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `doctorId` int(11) NOT NULL COMMENT '医生id',
  `bankId` int(11) NOT NULL COMMENT '银行类型id',
  `bankName` varchar(32) NOT NULL COMMENT '银行名称',
  `cardCode` varchar(32) NOT NULL COMMENT '银行卡号',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 0：无效 1：有效',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='医生银行卡';

/*Table structure for table `acc_doctorDealDetail` */

DROP TABLE IF EXISTS `acc_doctorDealDetail`;

CREATE TABLE `acc_doctorDealDetail` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `doctorId` int(11) NOT NULL COMMENT '医生id',
  `userId` int(11) DEFAULT NULL COMMENT '用户id',
  `inAmount` decimal(18,2) DEFAULT '0.00' COMMENT '收入',
  `payAmount` decimal(18,2) DEFAULT '0.00' COMMENT '支出',
  `dealType` tinyint(4) NOT NULL DEFAULT '0' COMMENT '交易类型',
  `decription` varchar(256) DEFAULT NULL COMMENT '交易描述',
  `relationId` int(11) DEFAULT NULL COMMENT '关联id',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='医生账户交易明细';

/*Table structure for table `acc_doctorFile` */

DROP TABLE IF EXISTS `acc_doctorFile`;

CREATE TABLE `acc_doctorFile` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `doctorId` int(11) NOT NULL COMMENT '医生账户id',
  `fileUrl` varchar(128) NOT NULL COMMENT '图片地址',
  `status` tinyint(4) DEFAULT '0' COMMENT '图片状态（0：未审核 1：审核通过 2：审核不通过）',
  `delFlag` tinyint(4) DEFAULT '0' COMMENT '删除状态（0：未删除 1：已删除）',
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='医生-图片信息';

/*Table structure for table `acc_doctorWallet` */

DROP TABLE IF EXISTS `acc_doctorWallet`;

CREATE TABLE `acc_doctorWallet` (
  `id` int(11) unsigned NOT NULL COMMENT '医生用户id',
  `amount` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '医生账户余额',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='医生账户';

/*Table structure for table `acc_inviteCode` */

DROP TABLE IF EXISTS `acc_inviteCode`;

CREATE TABLE `acc_inviteCode` (
  `int` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `inviteCode` varchar(16) NOT NULL COMMENT '邀请码',
  `mobileNo` varchar(16) NOT NULL COMMENT '手机号',
  `createDate` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`int`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邀请码';

/*Table structure for table `acc_mutualCircle` */

DROP TABLE IF EXISTS `acc_mutualCircle`;

CREATE TABLE `acc_mutualCircle` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户id',
  `content` varchar(1024) DEFAULT NULL COMMENT '互助圈内容',
  `isChoiceness` tinyint(4) DEFAULT '0' COMMENT '是否精选（0：不是 1：是）',
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='互助圈';

/*Table structure for table `acc_securityCode` */

DROP TABLE IF EXISTS `acc_securityCode`;

CREATE TABLE `acc_securityCode` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `securityCode` varchar(16) NOT NULL,
  `mobileNo` varchar(16) NOT NULL,
  `isValid` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '0：无效 1：有效',
  `codeType` tinyint(4) unsigned NOT NULL COMMENT 'SecurityCodeTypeEnum枚举',
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='验证码';

/*Table structure for table `acc_userAccount` */

DROP TABLE IF EXISTS `acc_userAccount`;

CREATE TABLE `acc_userAccount` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `mobileNo` varchar(16) NOT NULL COMMENT '手机号',
  `userName` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `userType` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户类型 0：健康的咨询者 1：家属 2：患者',
  `realName` varchar(32) DEFAULT NULL COMMENT '真实姓名',
  `nickName` varchar(64) DEFAULT NULL COMMENT '昵称',
  `sex` tinyint(4) unsigned DEFAULT '0' COMMENT '0：男 1：女',
  `areaProvince` varchar(32) DEFAULT NULL COMMENT '地区-省',
  `areaCity` varchar(32) DEFAULT NULL COMMENT '地区-市',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `isElseIllness` tinyint(4) DEFAULT '0' COMMENT '是否有额外疾病 0：没有 1：有',
  `devType` varchar(16) DEFAULT NULL COMMENT 'IOS、Andriod',
  `headImg` varchar(128) DEFAULT NULL COMMENT '头像',
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户账户';

/*Table structure for table `acc_userAttention` */

DROP TABLE IF EXISTS `acc_userAttention`;

CREATE TABLE `acc_userAttention` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户id',
  `attentionId` int(11) NOT NULL COMMENT '关注id（用户|医生）',
  `type` tinyint(11) NOT NULL DEFAULT '0' COMMENT '关注类型（0：医生 1：用户）',
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户关注';

/*Table structure for table `acc_userFile` */

DROP TABLE IF EXISTS `acc_userFile`;

CREATE TABLE `acc_userFile` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户id',
  `fileUrl` varchar(128) NOT NULL COMMENT '文件地址',
  `type` tinyint(4) NOT NULL COMMENT '文件业务类型',
  `relationId` int(11) DEFAULT NULL COMMENT '关联业务id',
  `delflag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态（0：未删除 1：已删除）',
  `createDate` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='用户文件';

/*Table structure for table `aik_answer` */

DROP TABLE IF EXISTS `aik_answer`;

CREATE TABLE `aik_answer` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `orderId` int(11) NOT NULL COMMENT '订单id',
  `doctorId` int(11) NOT NULL COMMENT '医生id',
  `answer` varchar(512) DEFAULT NULL COMMENT '答案',
  `fromQuestionId` int(11) NOT NULL COMMENT '问题来源',
  `type` tinyint(4) DEFAULT '0' COMMENT '0：初始回答 1：追问回答 2：拒绝回答',
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='提问订单-回答';

/*Table structure for table `aik_commonProblem` */

DROP TABLE IF EXISTS `aik_commonProblem`;

CREATE TABLE `aik_commonProblem` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `question` varchar(256) DEFAULT NULL COMMENT '问题',
  `answer` varchar(1024) DEFAULT NULL COMMENT '问题答案',
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='常见问题';

/*Table structure for table `aik_coupon` */

DROP TABLE IF EXISTS `aik_coupon`;

CREATE TABLE `aik_coupon` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `discountAmount` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '优惠金额',
  `minMonetary` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '最低消费金额',
  `startDate` datetime DEFAULT NULL COMMENT '有效开始时间',
  `endDate` datetime DEFAULT NULL COMMENT '有效结束时间',
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='优惠券';

/*Table structure for table `aik_doctorFeedback` */

DROP TABLE IF EXISTS `aik_doctorFeedback`;

CREATE TABLE `aik_doctorFeedback` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `doctorId` int(11) NOT NULL COMMENT '医生id',
  `content` varchar(512) NOT NULL COMMENT '反馈内容',
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='医生反馈问题';

/*Table structure for table `aik_doctorSick` */

DROP TABLE IF EXISTS `aik_doctorSick`;

CREATE TABLE `aik_doctorSick` (
  `id` int(11) unsigned NOT NULL,
  `doctorId` int(11) NOT NULL COMMENT '医生id',
  `userId` int(11) NOT NULL COMMENT '患者id',
  `questionOrderId` int(11) DEFAULT NULL COMMENT '患者问题订单id',
  `groupId` int(11) DEFAULT '0' COMMENT '分组id',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='医生患者';

/*Table structure for table `aik_doctorSickGroup` */

DROP TABLE IF EXISTS `aik_doctorSickGroup`;

CREATE TABLE `aik_doctorSickGroup` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `doctorId` int(11) NOT NULL COMMENT '医生id',
  `groupName` varchar(32) NOT NULL COMMENT '分组名称',
  `createDate` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='医生患者分组';

/*Table structure for table `aik_doctorTips` */

DROP TABLE IF EXISTS `aik_doctorTips`;

CREATE TABLE `aik_doctorTips` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `doctorId` int(11) NOT NULL COMMENT '医生id',
  `tipsType` tinyint(4) NOT NULL COMMENT '提示类型',
  `relationId` int(11) NOT NULL COMMENT '关联id',
  `tipsMessage` varchar(128) DEFAULT NULL COMMENT '提示内容',
  `isCheck` tinyint(4) DEFAULT '0' COMMENT '是否已查看（0：未查看 1：已查看）',
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='医生提示';

/*Table structure for table `aik_expertsAnswer` */

DROP TABLE IF EXISTS `aik_expertsAnswer`;

CREATE TABLE `aik_expertsAnswer` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `question` varchar(256) DEFAULT NULL COMMENT '问题',
  `expertsName` varchar(32) DEFAULT NULL COMMENT '专家名称',
  `education` varchar(64) DEFAULT NULL COMMENT '专家学历',
  `answer` varchar(1024) DEFAULT NULL COMMENT '专家回答',
  `questionDate` datetime DEFAULT NULL COMMENT '提问时间',
  `program` varchar(64) DEFAULT NULL COMMENT '提问栏目',
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='专家回答';

/*Table structure for table `aik_expertsAnswerView` */

DROP TABLE IF EXISTS `aik_expertsAnswerView`;

CREATE TABLE `aik_expertsAnswerView` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户id',
  `expertsAnswerId` int(11) NOT NULL COMMENT '专家回答问题id',
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='专家回答阅读';

/*Table structure for table `aik_freeQuestionOrder` */

DROP TABLE IF EXISTS `aik_freeQuestionOrder`;

CREATE TABLE `aik_freeQuestionOrder` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `orderId` int(11) NOT NULL COMMENT '订单id',
  `freeEndTime` datetime NOT NULL COMMENT '限时免费结束时间',
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='限时免费订单';

/*Table structure for table `aik_freeQuestionOrderView` */

DROP TABLE IF EXISTS `aik_freeQuestionOrderView`;

CREATE TABLE `aik_freeQuestionOrderView` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `freeOrderId` int(11) NOT NULL COMMENT '限免id',
  `userId` int(11) NOT NULL COMMENT '用户id',
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='限时免费问题查看';

/*Table structure for table `aik_healthRecord` */

DROP TABLE IF EXISTS `aik_healthRecord`;

CREATE TABLE `aik_healthRecord` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户id',
  `height` decimal(18,2) DEFAULT '0.00' COMMENT '身高CM',
  `weight` decimal(18,2) DEFAULT '0.00' COMMENT '体重KG',
  `medicalRecord` varchar(128) DEFAULT NULL COMMENT '病历（图片地址）',
  `remark` varchar(128) DEFAULT NULL COMMENT '体检主题',
  `hwRecordDate` datetime DEFAULT NULL COMMENT '身高体重记录时间',
  `mrRecordDate` datetime DEFAULT NULL COMMENT '病历记录时间',
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='健康档案';

/*Table structure for table `aik_hrBloodSugar` */

DROP TABLE IF EXISTS `aik_hrBloodSugar`;

CREATE TABLE `aik_hrBloodSugar` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户id',
  `hrId` int(11) NOT NULL COMMENT '健康档案id',
  `bloodSugar` decimal(18,2) DEFAULT '0.00' COMMENT '血糖mg/l',
  `tch` decimal(18,2) DEFAULT '0.00' COMMENT '胆固醇mg',
  `tg` decimal(18,2) DEFAULT '0.00' COMMENT '甘油三酯%',
  `hdlC` decimal(18,2) DEFAULT '0.00' COMMENT '高密度脂蛋白mmol/l',
  `ldlC` decimal(18,2) DEFAULT '0.00' COMMENT '低密度脂蛋白mmol/l',
  `cr` decimal(18,2) DEFAULT '0.00' COMMENT '肌酐mg/l',
  `bun` decimal(18,2) DEFAULT '0.00' COMMENT '尿素氮mg/l',
  `recordDate` datetime DEFAULT NULL COMMENT '记录时间',
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='健康档案-血糖类';

/*Table structure for table `aik_hrRoutineBlood` */

DROP TABLE IF EXISTS `aik_hrRoutineBlood`;

CREATE TABLE `aik_hrRoutineBlood` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户id',
  `hrId` int(11) NOT NULL COMMENT '健康档案id',
  `hb` decimal(18,2) DEFAULT '0.00' COMMENT '血红蛋白g/l',
  `rbc` decimal(18,2) DEFAULT '0.00' COMMENT '红细胞^12/l',
  `wbc` decimal(18,2) DEFAULT '0.00' COMMENT '白细胞^9/l',
  `plt` decimal(18,2) DEFAULT '0.00' COMMENT '血小板^9/l',
  `ret` decimal(18,2) DEFAULT '0.00' COMMENT '网织红细胞计数%',
  `bn` decimal(18,2) DEFAULT '0.00' COMMENT '中性杆状核粒细胞%',
  `sn` decimal(18,2) DEFAULT '0.00' COMMENT '中性分叶核粒细胞mmol/l',
  `eos` decimal(18,2) DEFAULT '0.00' COMMENT '嗜酸性粒细胞mg/l',
  `baso` decimal(18,2) DEFAULT '0.00' COMMENT '嗜碱性粒细胞mg/l',
  `lym` decimal(18,2) DEFAULT '0.00' COMMENT '淋巴细胞mg/l',
  `mnc` decimal(18,2) DEFAULT '0.00' COMMENT '单核细胞mg/l',
  `recordDate` datetime DEFAULT NULL COMMENT '记录时间',
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='健康档案-血常规指标';

/*Table structure for table `aik_hrTumorMarkers` */

DROP TABLE IF EXISTS `aik_hrTumorMarkers`;

CREATE TABLE `aik_hrTumorMarkers` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户id',
  `hrId` int(11) NOT NULL COMMENT '健康档案id',
  `afp` decimal(18,2) DEFAULT '0.00' COMMENT '甲胎蛋白',
  `cea` decimal(18,2) DEFAULT '0.00' COMMENT '癌胚抗原',
  `ca19` decimal(18,2) DEFAULT '0.00' COMMENT '糖类抗原ca19',
  `pg` decimal(18,2) DEFAULT '0.00' COMMENT '胃蛋白酶原I和II',
  `ca72` decimal(18,2) DEFAULT '0.00' COMMENT '糖类抗原ca72',
  `scc` decimal(18,2) DEFAULT '0.00' COMMENT '鳞癌相关抗原',
  `recordDate` datetime DEFAULT NULL COMMENT '记录时间',
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='健康档案-肿瘤标志物指标';

/*Table structure for table `aik_nutritionLesson` */

DROP TABLE IF EXISTS `aik_nutritionLesson`;

CREATE TABLE `aik_nutritionLesson` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(128) DEFAULT NULL COMMENT '课堂标题',
  `image` varchar(128) DEFAULT NULL COMMENT '课堂图标',
  `content` varchar(1024) DEFAULT NULL COMMENT '课堂内容',
  `issueDate` datetime DEFAULT NULL COMMENT '发布时间',
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='营养课堂';

/*Table structure for table `aik_nutritionLessonView` */

DROP TABLE IF EXISTS `aik_nutritionLessonView`;

CREATE TABLE `aik_nutritionLessonView` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户id',
  `lessonId` int(11) NOT NULL COMMENT '课堂id',
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='营养课堂阅读';

/*Table structure for table `aik_nutritionalIndex` */

DROP TABLE IF EXISTS `aik_nutritionalIndex`;

CREATE TABLE `aik_nutritionalIndex` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户id',
  `height` decimal(18,2) DEFAULT '0.00' COMMENT '身高',
  `weight` decimal(18,2) DEFAULT '0.00' COMMENT '体重',
  `bmi` decimal(18,2) DEFAULT '0.00' COMMENT 'BMI',
  `recordDate` date DEFAULT NULL COMMENT '记录时间',
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='营养指标';

/*Table structure for table `aik_question` */

DROP TABLE IF EXISTS `aik_question`;

CREATE TABLE `aik_question` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `orderId` int(11) NOT NULL COMMENT '订单id',
  `description` varchar(512) NOT NULL COMMENT '问题描述',
  `fromAnswerId` int(11) DEFAULT NULL COMMENT '追问时来源回答id',
  `type` tinyint(4) DEFAULT '0' COMMENT '0：初始提问 1：追问 2：评分',
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='提问订单-问题';

/*Table structure for table `aik_questionOrder` */

DROP TABLE IF EXISTS `aik_questionOrder`;

CREATE TABLE `aik_questionOrder` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户id',
  `description` varchar(512) NOT NULL COMMENT '病症描述',
  `illType` tinyint(4) NOT NULL COMMENT '疾病类型',
  `illName` varchar(32) DEFAULT NULL COMMENT '疾病类型名称（冗余）',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '询问方式（0：匹配医生 1：公开）',
  `doctorId` int(11) DEFAULT NULL COMMENT '医生id',
  `amount` decimal(18,2) DEFAULT '0.00' COMMENT '问题赏金（医生金额、悬赏金额）',
  `status` tinyint(4) DEFAULT '0' COMMENT '订单状态（0：审核中 1：付款中 2：处理中 3：评价中 4：退款中 5：正常结束 6：失败订单）',
  `failType` tinyint(4) DEFAULT '-1' COMMENT '失败类型（-1：非失败订单 0：审核不通过 1：医生拒绝）',
  `refuseReason` varchar(512) DEFAULT NULL COMMENT '拒绝原因',
  `isPayDoctor` tinyint(4) DEFAULT '0' COMMENT '是否支付给医生（0：未支付 1：已支付）',
  `auditStatus` tinyint(4) DEFAULT '-1' COMMENT '订单审核状态（-1：未审核 0：审核不通过 1：审核通过）',
  `auditorId` int(11) DEFAULT NULL COMMENT '订单审核人员id',
  `auditReason` varchar(512) DEFAULT NULL COMMENT '订单审核原因',
  `auditDate` datetime DEFAULT NULL COMMENT '审核时间',
  `payDate` datetime DEFAULT NULL COMMENT '支付时间',
  `refundStatus` tinyint(4) DEFAULT '-1' COMMENT '退款审核状态（-1：未审核 0：审核不通过 1：审核通过）',
  `refundDate` datetime DEFAULT NULL COMMENT '退款申请时间',
  `refundAuditorId` int(11) DEFAULT NULL COMMENT '退款审核人员id',
  `refundAuditDate` datetime DEFAULT NULL COMMENT '退款申请时间',
  `evaluation` varchar(512) DEFAULT NULL COMMENT '对医生评价',
  `serviceAttitude` tinyint(4) DEFAULT '0' COMMENT '服务态度评分',
  `answerQuality` tinyint(4) DEFAULT '0' COMMENT '回答质量评分',
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='提问订单';

/*Table structure for table `aik_questionViewHis` */

DROP TABLE IF EXISTS `aik_questionViewHis`;

CREATE TABLE `aik_questionViewHis` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户id',
  `orderId` int(11) NOT NULL COMMENT '问题订单id',
  `createDate` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='问题查看历史';

/*Table structure for table `aik_userCoupon` */

DROP TABLE IF EXISTS `aik_userCoupon`;

CREATE TABLE `aik_userCoupon` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户id',
  `couponId` int(11) NOT NULL COMMENT '优惠券id',
  `status` tinyint(4) DEFAULT '0' COMMENT '0：未使用 1：已使用 2：已过期',
  `serviceTime` datetime DEFAULT NULL COMMENT '使用时间',
  `serviceType` tinyint(4) DEFAULT NULL COMMENT '使用类型',
  `serviceId` int(11) DEFAULT NULL COMMENT '使用业务id',
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户优惠券';

/*Table structure for table `aik_userFeedback` */

DROP TABLE IF EXISTS `aik_userFeedback`;

CREATE TABLE `aik_userFeedback` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `content` varchar(512) NOT NULL COMMENT '反馈内容',
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户意见反馈';

/*Table structure for table `diet_dailyDietPlan` */

DROP TABLE IF EXISTS `diet_dailyDietPlan`;

CREATE TABLE `diet_dailyDietPlan` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `recordDate` date NOT NULL COMMENT '记录日期',
  `foodId` int(11) NOT NULL COMMENT '食物id',
  `weight` int(11) NOT NULL DEFAULT '0' COMMENT '份量',
  `dietType` tinyint(4) NOT NULL COMMENT '0：早餐 1：中餐 2：晚餐 3：营养补充 4：加餐',
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='饮食计划-每日饮食计划';

/*Table structure for table `diet_dailyDietRecord` */

DROP TABLE IF EXISTS `diet_dailyDietRecord`;

CREATE TABLE `diet_dailyDietRecord` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户id',
  `recordDate` date NOT NULL COMMENT '记录日期',
  `foodId` int(11) NOT NULL COMMENT '食物id',
  `weight` int(11) NOT NULL DEFAULT '0' COMMENT '份量',
  `dietType` tinyint(4) NOT NULL COMMENT '0：早餐 1：中餐 2：晚餐 3：营养补充 4：加餐',
  `createDate` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='饮食计划-每日饮食记录';

/*Table structure for table `diet_dailyNutrition` */

DROP TABLE IF EXISTS `diet_dailyNutrition`;

CREATE TABLE `diet_dailyNutrition` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户id',
  `recordDate` date NOT NULL COMMENT '记录日期',
  `protein` decimal(18,2) DEFAULT '0.00' COMMENT '蛋白质',
  `lipid` decimal(18,2) DEFAULT '0.00' COMMENT '脂类',
  `carbs` decimal(18,2) DEFAULT '0.00' COMMENT '碳水化合物',
  `vitamin` decimal(18,2) DEFAULT '0.00' COMMENT '维他命',
  `minerals` decimal(18,2) DEFAULT '0.00' COMMENT '矿物质',
  `water` decimal(18,2) DEFAULT '0.00' COMMENT '水',
  `dietaryFiber` decimal(18,2) DEFAULT '0.00' COMMENT '膳食纤维',
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='饮食计划-每日营养';

/*Table structure for table `diet_food` */

DROP TABLE IF EXISTS `diet_food`;

CREATE TABLE `diet_food` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL COMMENT '食物名称',
  `category` int(11) NOT NULL COMMENT '食物类别',
  `image` varchar(128) NOT NULL COMMENT '食物图片',
  `recommend` varchar(32) DEFAULT NULL COMMENT '推荐|禁食',
  `brightSpot` tinyint(4) DEFAULT '-1' COMMENT '食物亮点（-1：无 1：GI最低）',
  `spotRank` int(11) DEFAULT NULL COMMENT '食物亮点排名',
  `proteinRadio` decimal(18,2) DEFAULT '0.00' COMMENT '蛋白质比例',
  `fatRadio` decimal(18,2) DEFAULT '0.00' COMMENT '脂肪比例',
  `carbsRadio` decimal(18,2) DEFAULT '0.00' COMMENT '碳水化合物比例',
  `type` tinyint(4) DEFAULT '0' COMMENT '0：膳食 1：营养',
  `weightUnit` varchar(16) DEFAULT NULL COMMENT '食物份量单位',
  `weight` int(11) DEFAULT '0' COMMENT '每份重量',
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='饮食计划-食物';

/*Table structure for table `diet_foodBloodSugar` */

DROP TABLE IF EXISTS `diet_foodBloodSugar`;

CREATE TABLE `diet_foodBloodSugar` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `foodId` int(11) NOT NULL COMMENT '食物id',
  `gi` decimal(18,2) DEFAULT '0.00' COMMENT 'GI值',
  `gl` decimal(18,2) DEFAULT '0.00' COMMENT 'GL值',
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='饮食计划-食物血糖';

/*Table structure for table `diet_foodCategory` */

DROP TABLE IF EXISTS `diet_foodCategory`;

CREATE TABLE `diet_foodCategory` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL COMMENT '分类名称',
  `image` varchar(128) NOT NULL COMMENT '分类图片',
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='饮食计划-食物类别';

/*Table structure for table `diet_foodFat` */

DROP TABLE IF EXISTS `diet_foodFat`;

CREATE TABLE `diet_foodFat` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `foodId` int(11) NOT NULL COMMENT '食物id',
  `saturatedFattyAcid` decimal(18,2) DEFAULT '0.00' COMMENT '饱和脂肪酸',
  `unsaturatedFattyAcid` decimal(18,2) DEFAULT '0.00' COMMENT '不饱和脂肪酸',
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `diet_foodMoreElement` */

DROP TABLE IF EXISTS `diet_foodMoreElement`;

CREATE TABLE `diet_foodMoreElement` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `foodId` int(11) NOT NULL COMMENT '食物id',
  `nutrientElem` varchar(32) NOT NULL COMMENT '营养元素',
  `content` decimal(18,2) DEFAULT '0.00' COMMENT '营养含量（每100g）',
  `unit` varchar(16) DEFAULT NULL COMMENT '营养含量单位',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注信息',
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='饮食计划-食物更多元素';

/*Table structure for table `diet_foodProtein` */

DROP TABLE IF EXISTS `diet_foodProtein`;

CREATE TABLE `diet_foodProtein` (
  `id` int(11) unsigned NOT NULL,
  `foodId` int(11) NOT NULL COMMENT '食物id',
  `aminoAcid1` decimal(18,2) DEFAULT '0.00' COMMENT '氨基酸1',
  `aminoAcid2` decimal(18,2) DEFAULT '0.00' COMMENT '氨基酸2',
  `aminoAcid3` decimal(18,2) DEFAULT '0.00' COMMENT '氨基酸3',
  `createDate` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='饮食计划-食物蛋白质';

/*Table structure for table `diet_userCollectFood` */

DROP TABLE IF EXISTS `diet_userCollectFood`;

CREATE TABLE `diet_userCollectFood` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户id',
  `foodId` int(11) NOT NULL COMMENT '食物id',
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='饮食计划-用户收藏';

/*Table structure for table `sto_acceptAddress` */

DROP TABLE IF EXISTS `sto_acceptAddress`;

CREATE TABLE `sto_acceptAddress` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户id',
  `name` varchar(32) NOT NULL COMMENT '收货人姓名',
  `mobileNo` varchar(16) NOT NULL COMMENT '收货人手机号',
  `province` varchar(32) NOT NULL COMMENT '地址-省',
  `city` varchar(32) NOT NULL COMMENT '地址-市',
  `area` varchar(32) NOT NULL COMMENT '地址-区',
  `address` varchar(128) NOT NULL COMMENT '详细地址',
  `isDefault` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0：非默认地址 1：默认地址',
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='爱康商店-收货地址';

/*Table structure for table `sto_goods` */

DROP TABLE IF EXISTS `sto_goods`;

CREATE TABLE `sto_goods` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL COMMENT '商品名称',
  `price` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '商品价格',
  `image` varchar(128) NOT NULL COMMENT '商品图片',
  `description` varchar(256) DEFAULT NULL COMMENT '商品描述',
  `type` tinyint(4) DEFAULT NULL COMMENT '0：特医食品 1：保健食品 2：智能硬件 3：产品套餐',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0：未下架（上架中） 1：已下架',
  `isRecommend` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0：未推荐 1：推荐',
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='爱康商店-商品';

/*Table structure for table `sto_shoppingCart` */

DROP TABLE IF EXISTS `sto_shoppingCart`;

CREATE TABLE `sto_shoppingCart` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户id',
  `goodsId` int(11) DEFAULT NULL COMMENT '商品id',
  `number` int(11) DEFAULT '0' COMMENT '商品数量',
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='爱康商店-购物车';

/*Table structure for table `sto_userOrder` */

DROP TABLE IF EXISTS `sto_userOrder`;

CREATE TABLE `sto_userOrder` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `orderNum` varchar(32) NOT NULL COMMENT '订单号',
  `userId` int(11) NOT NULL COMMENT '用户id',
  `acceptAddressId` int(11) DEFAULT NULL COMMENT '收货地址',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单状态： 0待付款、1待发货、2已发货、3已完成',
  `amount` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '订单金额',
  `realAmount` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '实付订单金额',
  `freightAmount` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '运费',
  `leaveMsg` varchar(256) DEFAULT NULL COMMENT '买家留言',
  `payTime` datetime DEFAULT NULL COMMENT '支付时间',
  `deliveryTime` datetime DEFAULT NULL COMMENT '发货时间',
  `endTime` datetime DEFAULT NULL COMMENT '订单结束时间',
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='爱康商店-用户订单';

/*Table structure for table `sto_userOrderDetail` */

DROP TABLE IF EXISTS `sto_userOrderDetail`;

CREATE TABLE `sto_userOrderDetail` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户id',
  `orderId` int(11) NOT NULL COMMENT '订单id',
  `goodsId` int(11) NOT NULL COMMENT '商品id',
  `number` int(11) DEFAULT '0' COMMENT '数量',
  `amount` decimal(18,2) DEFAULT '0.00' COMMENT '金额',
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='爱康商店-用户订单详情';

/*Table structure for table `sys_areaTree` */

DROP TABLE IF EXISTS `sys_areaTree`;

CREATE TABLE `sys_areaTree` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT 'ex：xx省、xx市',
  `parent` int(11) NOT NULL DEFAULT '0' COMMENT '省级别parent为0，市级别对应其parent id',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `createDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='省市地区树结构';

/*Table structure for table `sys_bank` */

DROP TABLE IF EXISTS `sys_bank`;

CREATE TABLE `sys_bank` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `bankName` varchar(64) NOT NULL COMMENT '银行名称',
  `bankIcon` varchar(256) DEFAULT NULL COMMENT '银行图标',
  `bankColor` varchar(64) DEFAULT NULL COMMENT '银行卡背景颜色',
  `bankType` varchar(32) DEFAULT NULL COMMENT '银行卡类型',
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='银行信息';

/*Table structure for table `sys_hospitalTree` */

DROP TABLE IF EXISTS `sys_hospitalTree`;

CREATE TABLE `sys_hospitalTree` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `areaId` int(11) NOT NULL DEFAULT '0' COMMENT '对应市级别areaTree id(科室默认对应0)',
  `name` varchar(64) NOT NULL COMMENT 'ex：医院名称、科室名称',
  `parent` int(11) NOT NULL DEFAULT '0' COMMENT '医院级别parent为0，科室对应其医院 id',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `createDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='医院科室树结构';

/*Table structure for table `sys_illTypeTree` */

DROP TABLE IF EXISTS `sys_illTypeTree`;

CREATE TABLE `sys_illTypeTree` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `typeName` varchar(64) NOT NULL COMMENT '疾病类型名称',
  `parentId` int(11) DEFAULT '0' COMMENT '父类疾病类型id，无上级默认为0',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='疾病类型';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
