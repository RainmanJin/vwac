/*
MySQL Data Transfer
Source Host: localhost
Source Database: ecan_tech
Target Host: localhost
Target Database: ecan_tech
Date: 2015/8/12 14:52:37
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for ecan_children_tag
-- ----------------------------
DROP TABLE IF EXISTS `ecan_children_tag`;
CREATE TABLE `ecan_children_tag` (
  `id` varchar(32) NOT NULL default '',
  `ctag_name` varchar(255) default NULL,
  `main_id` varchar(32) NOT NULL default '',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ecan_main_tag
-- ----------------------------
DROP TABLE IF EXISTS `ecan_main_tag`;
CREATE TABLE `ecan_main_tag` (
  `main_id` varchar(32) NOT NULL default '',
  `tag_name` varchar(255) NOT NULL,
  `lastupdatetime` datetime NOT NULL,
  PRIMARY KEY  (`main_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------

