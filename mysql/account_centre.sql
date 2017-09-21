
DROP TABLE IF EXISTS `aikang`.`acc_inviteCode`;
CREATE TABLE IF NOT EXISTS `aikang`.`acc_inviteCode` (
  `id` INT UNSIGNED NOT NULL,
  `inviteCode` VARCHAR(16) NOT NULL COMMENT '注册邀请码',
  `mobileNo` VARCHAR(16) NOT NULL COMMENT '邀请手机号',
  `createDate` DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `aikang`.`acc_securityCode`;
CREATE TABLE IF NOT EXISTS `aikang`.`acc_securityCode` (
  `id` INT UNSIGNED NOT NULL,
  `securityCode` VARCHAR(16) NOT NULL COMMENT '验证码',
  `mobileNo` VARCHAR(16) NOT NULL COMMENT '验证手机号',
  `createDate` DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `aikang`.`acc_basicInfo`;
CREATE TABLE IF NOT EXISTS `aikang`.`acc_basicInfo` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `mobileNo` VARCHAR(16) NOT NULL COMMENT '注册手机号',
  `userName` VARCHAR(64) NOT NULL COMMENT '登录用户名',
  `password` VARCHAR(64) NOT NULL COMMENT '登录密码（MD5加密）',
  `userType` TINYINT UNSIGNED NOT NULL COMMENT '1：医生 11：健康的咨询者 12：家属 13：患者',
  `realName` VARCHAR(32) NOT NULL COMMENT '真实姓名',
  `sex` TINYINT UNSIGNED NOT NULL COMMENT '1：男 2：女',
  `areaProvince` VARCHAR(32) NULL COMMENT '地区-省',
  `areaCity` VARCHAR(32) NULL COMMENT '地区-市',
  `birthday` DATE NULL COMMENT '出生日期',
  `createDate` DATETIME NOT NULL COMMENT '创建时间',
  `updateDate` DATETIME NULL COMMENT '修改时间',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `aikang`.`acc_doctorInfo`;
CREATE TABLE IF NOT EXISTS `aikang`.`acc_doctorInfo` (
  `id` INT UNSIGNED NOT NULL,
  `identityCard` VARCHAR(32) NULL COMMENT '身份证号码',
  `email` VARCHAR(64) NULL COMMENT '邮箱地址',
  `hosProvince` VARCHAR(32) NOT NULL COMMENT '执行医院-省',
  `hosCity` VARCHAR(32) NOT NULL COMMENT '执行医院-市',
  `hosName` VARCHAR(32) NOT NULL COMMENT '执行医院-名称',
  `hosDepartment` VARCHAR(32) NOT NULL COMMENT '执行医院-科室',
  `skill` VARCHAR(128) NOT NULL COMMENT '擅长病症',
  `departmentPhone` VARCHAR(32) NOT NULL COMMENT '科室电话',
  `createDate` DATETIME NOT NULL COMMENT '创建时间',
  `updateDate` VARCHAR(45) NULL COMMENT '修改时间',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `aikang`.`acc_userInfo`;
CREATE TABLE IF NOT EXISTS `aikang`.`acc_userInfo` (
  `id` INT UNSIGNED NOT NULL,
  `isElseIllness` TINYINT UNSIGNED NULL DEFAULT 0 COMMENT '是否有额外疾病（0：否 1：是）',
  `createDate` DATETIME NOT NULL COMMENT '创建时间',
  `updateDate` DATETIME NULL COMMENT '修改时间',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

DROP TABLE IF EXISTS `aikang`.`acc_doctorFile`;
CREATE TABLE IF NOT EXISTS `aikang`.`acc_doctorFile` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `accId` INT NOT NULL COMMENT '账户id',
  `fileUrl` VARCHAR(128) NOT NULL COMMENT '图片地址（图片服务器域名通过配置获取）',
  `status` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '图片状态（0：未审核 1：审核通过 2：审核不通过）',
  `delFlag` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '0：未删除 1：已删除',
  `createDate` DATETIME NOT NULL COMMENT '创建时间',
  `updateDate` DATETIME NULL COMMENT '修改时间',
  PRIMARY KEY (`id`))
ENGINE = InnoDB