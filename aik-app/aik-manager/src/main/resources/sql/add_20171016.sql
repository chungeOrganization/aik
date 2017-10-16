ALTER TABLE `acc_userAccount`   
  ADD COLUMN `deleteStatus` TINYINT(4) DEFAULT 0  NULL  COMMENT '是否封号 0正常 1封号' AFTER `updateDate`;


ALTER TABLE `acc_doctorAccount`   
  ADD COLUMN `deleteStatus` TINYINT(4) DEFAULT 0  NULL  COMMENT '是否封号 0正常 1封号' AFTER `updateDate`;