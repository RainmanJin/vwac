/*
MySQL Data Transfer
Source Host: localhost
Source Database: ecan_tech
Target Host: localhost
Target Database: ecan_tech
Date: 2015/8/12 14:46:02
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for ecan_message_type
-- ----------------------------
DROP TABLE IF EXISTS `ecan_message_type`;
CREATE TABLE `ecan_message_type` (
  `id` varchar(32) NOT NULL,
  `typename` varchar(255) NOT NULL,
  `typecode` varchar(16) NOT NULL,
  `status` varchar(16) NOT NULL,
  `lastupdatetime` datetime NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `ecan_message_type` VALUES ('1', '系统通知', 'S', '0', '2015-08-07 13:21:17');
INSERT INTO `ecan_message_type` VALUES ('2', '分配任务', 'D', '0', '2015-08-07 13:21:36');

