/*
MySQL Data Transfer
Source Host: localhost
Source Database: ecan_tech
Target Host: localhost
Target Database: ecan_tech
Date: 2015/7/31 23:02:00
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for techmdttln
-- ----------------------------
DROP TABLE IF EXISTS `techmdttln`;
CREATE TABLE `techmdttln` (
  `id` varchar(50) NOT NULL,
  `title` varchar(100) default NULL COMMENT '标题',
  `style` varchar(10) default NULL COMMENT '任务类型',
  `starttime` date default NULL COMMENT '开始时间',
  `endtime` date default NULL COMMENT '结束时间',
  `status` varchar(10) default NULL COMMENT '任务状态',
  `userid` text COMMENT '分配人员id',
  `courseware` text,
  `lastupdatetime` datetime default NULL COMMENT '更新时间',
  `username` text COMMENT '分配人员姓名',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for techmdttln_courses
-- ----------------------------
DROP TABLE IF EXISTS `techmdttln_courses`;
CREATE TABLE `techmdttln_courses` (
  `id` varchar(50) NOT NULL,
  `pkgid` varchar(50) default NULL,
  `pkgname` varchar(50) default NULL,
  `pkgfilepath` varchar(255) default NULL,
  `mdttln_id` varchar(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed8f655014ed8fad4090003', '78', '0', '2015-07-06', '2015-07-29', null, null, null, '2015-07-29 16:43:13', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed8fb5f014ed8ffcf030001', 'd', '0', '2015-07-06', '2015-07-30', null, null, null, '2015-07-29 16:48:40', null);
INSERT INTO `techmdttln` VALUES ('40288a654ed5a78d014ed5aa94780002', '1234', '0', '2015-07-05', '2015-06-28', '1', 'ff808081388b894c01388d5145a70001', null, '2015-07-29 01:45:32', 'Jacky Yao');
INSERT INTO `techmdttln` VALUES ('40288a654ed5b7a6014ed5bc66ec0003', '22334455', '0', '2015-07-01', '2015-07-30', '0', null, null, '2015-07-29 01:36:10', null);
INSERT INTO `techmdttln` VALUES ('40288a654ed5b7a6014ed5bd36720008', '22334455', '0', '2015-07-01', '2015-07-30', '1', null, null, '2015-07-29 01:37:04', null);
INSERT INTO `techmdttln` VALUES ('40288a654ed5c2b6014ed5c53d1c0005', '太太太太太', '0', '2015-07-06', '2015-07-31', '0', null, null, '2015-07-29 01:45:50', null);
INSERT INTO `techmdttln` VALUES ('40288a654ed5c2b6014ed5c5c6b40008', '太太太太太', '0', '2015-07-06', '2015-07-31', '3', 'ff808081388b894c01388dc7ea360027,ff808081388b894c01388dc7ea3b0028', null, '2015-07-29 01:50:42', '高镜慧,奇辰,');
INSERT INTO `techmdttln` VALUES ('40288a654ed5c928014ed5ca9c1e0004', '6666666', '0', '2015-06-28', '2015-07-29', '0', null, null, '2015-07-29 01:51:42', null);
INSERT INTO `techmdttln` VALUES ('40288a654ed5c928014ed5cabeec0007', '6666666', '0', '2015-06-28', '2015-07-29', '0', null, null, '2015-07-29 01:51:51', null);
INSERT INTO `techmdttln` VALUES ('40288a654ed5d1b2014ed5d1cd800001', '6666666', '0', '2015-06-28', '2015-07-29', '0', 'ff808081388b894c01388dc7ea2a0026', null, '2015-07-29 09:07:37', 'Xiaoyan');
INSERT INTO `techmdttln` VALUES ('40288a654ed5d1b2014ed5d28b520006', '吞吞吐吐', '0', '2015-06-29', '2015-07-30', '1', null, null, '2015-07-29 02:00:22', null);
INSERT INTO `techmdttln` VALUES ('40288a654ed5d1b2014ed5d29e23000b', '吞吞吐吐', '0', '2015-06-29', '2015-07-30', '0', null, null, '2015-07-29 02:00:26', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed86528014ed86d76ef0001', 'ddfgg', '0', '2015-07-01', '2015-07-16', '0', null, null, '2015-07-29 14:08:49', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed86528014ed86d927f0004', 'ddfgg', '0', '2015-07-01', '2015-07-16', '0', null, null, '2015-07-29 14:12:18', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed86528014ed870ce560008', 'd', '0', '2015-07-07', '2015-07-30', '0', null, null, '2015-07-29 14:12:28', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed86528014ed870fd3f000b', 'd', '0', '2015-07-07', '2015-07-30', '0', null, null, '2015-07-29 14:12:40', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed87187014ed87225580001', 'd', '0', '2015-07-07', '2015-07-30', '0', null, null, '2015-07-29 14:13:56', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed87187014ed8724abd0002', 'f', '0', '2015-07-06', '2015-07-31', '0', null, null, '2015-07-29 14:14:05', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed873de014ed889612f0001', '1', '0', '2015-07-01', '2015-07-30', '0', null, null, '2015-07-29 14:40:09', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed873de014ed889b3a60004', '1', '0', '2015-07-01', '2015-07-30', '0', null, null, '2015-07-29 14:40:53', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed873de014ed893c8d00007', '2', '0', '2015-07-06', '2015-07-31', '0', null, null, '2015-07-29 14:50:40', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed873de014ed89433df000a', '2', '0', '2015-07-06', '2015-07-31', '0', null, null, '2015-07-29 14:51:08', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed89960014ed89a62620001', '3', '0', '2015-07-07', '2015-07-30', '0', null, null, '2015-07-29 14:57:53', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed89960014ed89b46710004', '3', '0', '2015-07-07', '2015-07-30', '0', null, null, '2015-07-29 14:58:51', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed89edf014ed89f2bb50001', '5', '0', '2015-07-01', '2015-07-30', '0', null, null, '2015-07-29 15:03:06', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed89edf014ed89f683d0004', '5', '0', '2015-07-01', '2015-07-30', '0', null, null, '2015-07-29 15:03:22', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed89edf014ed8a13aeb0007', '5', '0', '2015-07-01', '2015-07-30', '0', '4aea47a53a81840d013ac053a9cc00bb,4aea47a53a81840d013ac05c971700bc,admin,ff808081388b894c01388d5145a70001,ff808081388b894c01388dc7ea210024,ff808081388b894c01388dc7ea260025,ff808081388b894c01388dc7ea2a0026,ff808081388b894c01388dc7ea360027,ff808081388b894c01388dc7ea3b0028,ff808081388b894c01388dc7ea400029,ff808081388b894c01388dc7ea4a002b', null, '2015-07-29 15:10:30', 'Jenny Zhao,金晶,Jacky Yao,赵圣纳,Hui Jiang,耿赞,高镜慧,奇辰,Xiaoyan,Admin,蔡荣源,');
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed8a524014ed8a6c17c0004', '7', '0', '2015-07-06', '2015-07-30', '0', '4aea47a53a81840d013ac05c971700bc,admin,ff808081388b894c01388d5145a70001,ff808081388b894c01388dc7ea210024,ff808081388b894c01388dc7ea260025,ff808081388b894c01388dc7ea2a0026,ff808081388b894c01388dc7ea360027,ff808081388b894c01388dc7ea3b0028,ff808081388b894c01388dc7ea400029', null, '2015-07-29 17:12:31', 'Jenny Zhao,Jacky Yao,赵圣纳,Hui Jiang,耿赞,高镜慧,奇辰,Xiaoyan,Admin,');
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed8a524014ed8a6f14e0007', '7', '0', '2015-07-06', '2015-07-30', '0', '4aea47a53a81840d013ac053a9cc00bb,4aea47a53a81840d013ac05c971700bc,admin,ff808081388b894c01388d5145a70001,ff808081388b894c01388dc7ea210024,ff808081388b894c01388dc7ea260025,ff808081388b894c01388dc7ea2a0026,ff808081388b894c01388dc7ea360027,ff808081388b894c01388dc7ea3b0028,ff808081388b894c01388dc7ea400029', null, '2015-07-29 15:12:58', 'Jenny Zhao,金晶,Jacky Yao,赵圣纳,Hui Jiang,耿赞,高镜慧,奇辰,Xiaoyan,Admin,');
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed8e5f7014ed8e656260001', '234', '0', '2015-07-01', '2015-07-29', null, null, null, '2015-07-29 16:20:50', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed8e5f7014ed8e661430003', '234', '0', '2015-07-01', '2015-07-29', null, null, null, '2015-07-29 16:20:53', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed8f655014ed8fab8d60001', '78', '0', '2015-07-06', '2015-07-29', null, null, null, '2015-07-29 16:43:06', null);
INSERT INTO `techmdttln` VALUES ('40288a654ed5953d014ed59a3f8e0004', '任务123', '0', '2015-06-29', '2015-07-30', '2', null, null, '2015-07-29 01:06:23', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed8fb5f014ed8ffdbd30003', 'd', '0', '2015-07-06', '2015-07-30', null, null, null, '2015-07-29 16:48:43', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed9091b014ed9096ac50001', 'd', '0', '2015-07-06', '2015-07-30', null, null, null, '2015-07-29 16:59:09', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed90d3a014ed90ddcee0001', '9', '0', '2015-07-06', '2015-07-22', null, null, null, '2015-07-29 17:04:01', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed90d3a014ed90df41a0003', '9', '0', '2015-07-06', '2015-07-22', null, null, null, '2015-07-29 17:04:07', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed910cd014ed91120b20001', 'ee', '0', '2015-07-14', '2015-07-30', null, null, null, '2015-07-29 17:07:35', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed91181014ed911f1370001', '学习任务标题', '0', '2015-07-07', '2015-07-22', null, null, null, '2015-07-29 17:08:28', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed92050014ed920a0f20001', 'ggttt', '0', '2015-07-01', '2015-07-29', null, null, null, '2015-07-29 17:24:31', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed92050014ed920b0880005', 'ggttt', '0', '2015-07-01', '2015-07-29', null, null, null, '2015-07-29 17:24:35', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed92050014ed92193aa0006', 'ggttt', '0', '2015-07-01', '2015-07-29', null, null, null, '2015-07-29 17:25:33', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed92050014ed921a99b0007', 'ggttt', '0', '2015-07-01', '2015-07-29', null, null, null, '2015-07-29 17:25:38', null);
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed92050014ed921d4170008', 'ddddgghh', '0', '2015-07-07', '2015-07-22', '0', '4aea47a53a81840d013ac053a9cc00bb,4aea47a53a81840d013ac05c971700bc,admin,ff808081388b894c01388d5145a70001,ff808081388b894c01388dc7ea210024,ff808081388b894c01388dc7ea260025,ff808081388b894c01388dc7ea2a0026,ff808081388b894c01388dc7ea360027,ff808081388b894c01388dc7ea3b0028,ff808081388b894c01388dc7ea400029,ff808081388b894c01388dc7ea4a002b', null, '2015-07-29 17:43:47', 'Jenny Zhao,金晶,Jacky Yao,赵圣纳,Hui Jiang,耿赞,高镜慧,奇辰,Xiaoyan,Admin,蔡荣源,');
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed93530014ed93d8a730001', '啪啪啪啪啪啪啪', '0', '2015-07-06', '2015-07-28', '2', '4aea47a53a81840d013ac053a9cc00bb,4aea47a53a81840d013ac05c971700bc,admin,ff808081388b894c01388d5145a70001,ff808081388b894c01388dc7ea210024,ff808081388b894c01388dc7ea260025,ff808081388b894c01388dc7ea2a0026,ff808081388b894c01388dc7ea360027,ff808081388b894c01388dc7ea3b0028,ff808081388b894c01388dc7ea400029,ff808081388b894c01388dc7ea4a002b,ff808081388b894c01388dc7ea4f002c,ff808081388b894c01388dc7ea54002d,ff808081388b894c01388dc7ea59002e,ff808081388b894c01388dc7ea670031,ff808081388b894c01388dc7ea6c0032,ff808081388b894c01388dc7ea710033,ff80808139675b2b01398a40ca420008,ff8080813c8bdbfc013c8bddc4870001,ff8080813d0cba87013d1446e86a0002,ff8080813d0cba87013d14480cce0003,ff8080813d0cba87013d1449a34c0004,ff8080813d0cba87013d144ebbf00006,ff8080813d0cba87013d144ff8d70007,ff8080813d5e83ef013d6cc1b064000f,ff8080813d5e83ef013e0baafe8f0706,ff808081409a6e6e0140c3c7d5680002,ff808081409a6e6e0140c406eb7a0003,ff808081409a6e6e0140c40bee280007,ff808081411023160142c21facf90009,ff808081411023160142c22038c6000a,ff808081411023160142c221e537000b,ff808081411023160142c2228c30000c,ff808081411023160142c2258e2d000d,ff808081411023160142c22bafc8000e,ff808081411023160142c22c9187000f,ff808081411023160142c22e68760010,ff808081411023160142c22ef0b30011,ff808081411023160143e125df76046b,ff808081411023160144bf1688da046c,ff808081411023160144bf172f41046d,ff808081411023160144bf17cafe046e,ff808081411023160144bf1858c4046f,ff808081411023160144bfc406f70470,ff808081411023160144bfc477260471,ff808081411023160144bff35ca60472,ff808081411023160144bff3c1c80473,ff8080814a1a2438014a74b0be730001,ff8080814a1a2438014adeb7de33001a,ff8080814af23a1e014bd93961be0005', null, '2015-07-30 22:27:37', '金晶,耿赞,Admin,Richard,Jenny Zhao,王玮,Jane Zhou,张宇,Jacky Yao,赵圣纳,测试帐号,高镜慧,user13,Wang Hui,Laura Shen,user12,user15,user14,user11,user10,Jiang Ying,家云燕,Hui Jiang,奇辰,user16,user17,testaaa,Christy,Peter,吴勤,G Maune,沙磊,CT Intern,Xiaoyan,Wenli Chen,韩涛,陈庆贵,Laura Luo,user2,user1,user4,user3,user6,David Liu,user5,user8,user7,蔡荣源,user9,');
INSERT INTO `techmdttln` VALUES ('8a8c80f44ed94a4f014ed95466ad0001', '怕怕怕怕怕怕', '0', '2015-07-06', '2015-07-16', '0', '4aea47a53a81840d013ac053a9cc00bb,4aea47a53a81840d013ac05c971700bc,admin,ff808081388b894c01388d5145a70001,ff808081388b894c01388dc7ea210024,ff808081388b894c01388dc7ea260025,ff808081388b894c01388dc7ea2a0026,ff808081388b894c01388dc7ea360027,ff808081388b894c01388dc7ea3b0028,ff808081388b894c01388dc7ea400029,ff808081388b894c01388dc7ea4a002b,ff808081388b894c01388dc7ea4f002c,ff808081388b894c01388dc7ea54002d,ff808081388b894c01388dc7ea59002e,ff808081388b894c01388dc7ea670031,ff808081388b894c01388dc7ea6c0032,ff808081388b894c01388dc7ea710033,ff80808139675b2b01398a40ca420008,ff8080813c8bdbfc013c8bddc4870001,ff8080813d0cba87013d1446e86a0002,ff8080813d0cba87013d14480cce0003,ff8080813d0cba87013d1449a34c0004,ff8080813d0cba87013d144ebbf00006,ff8080813d0cba87013d144ff8d70007,ff8080813d5e83ef013d6cc1b064000f,ff8080813d5e83ef013e0baafe8f0706,ff808081409a6e6e0140c3c7d5680002,ff808081409a6e6e0140c406eb7a0003,ff808081409a6e6e0140c40bee280007,ff808081411023160142c21facf90009,ff808081411023160142c22038c6000a,ff808081411023160142c221e537000b,ff808081411023160142c2228c30000c,ff808081411023160142c2258e2d000d,ff808081411023160142c22bafc8000e,ff808081411023160142c22c9187000f,ff808081411023160142c22e68760010,ff808081411023160142c22ef0b30011,ff808081411023160143e125df76046b,ff808081411023160144bf1688da046c,ff808081411023160144bf172f41046d,ff808081411023160144bf17cafe046e,ff808081411023160144bf1858c4046f,ff808081411023160144bfc406f70470,ff808081411023160144bfc477260471,ff808081411023160144bff35ca60472,ff808081411023160144bff3c1c80473,ff8080814a1a2438014a74b0be730001,ff8080814a1a2438014adeb7de33001a,ff8080814af23a1e014bd93961be0005', null, '2015-07-29 18:21:23', '金晶,耿赞,Admin,Richard,Jenny Zhao,王玮,Jane Zhou,张宇,Jacky Yao,赵圣纳,测试帐号,高镜慧,user13,Wang Hui,Laura Shen,user12,user15,user14,user11,user10,Jiang Ying,家云燕,Hui Jiang,奇辰,user16,user17,testaaa,Christy,Peter,吴勤,G Maune,沙磊,CT Intern,Xiaoyan,Wenli Chen,韩涛,陈庆贵,Laura Luo,user2,user1,user4,user3,user6,David Liu,user5,user8,user7,蔡荣源,user9,');
INSERT INTO `techmdttln` VALUES ('40288a654edf6ab8014edf6bb3ef0001', '123', '0', '2015-07-01', '2015-07-29', '3', 'ff808081388b894c01388dc7ea210024', null, '2015-07-31 22:35:18', 'Hui Jiang');
INSERT INTO `techmdttln` VALUES ('40288a654ee49204014ee49283540001', '测试学习任务1', '0', '2015-07-01', '2015-07-31', '3', '4aea47a53a81840d013ac05c971700bc,admin,ff808081388b894c01388d5145a70001,ff808081388b894c01388dc7ea210024,ff808081388b894c01388dc7ea260025,ff808081388b894c01388dc7ea2a0026', null, '2015-07-31 22:52:34', 'Jenny Zhao,Jacky Yao,Hui Jiang,耿赞,Xiaoyan,Admin,');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed873de014ed88ad3080005', 'f877517b-8db5-4b8f-a93e-c0fe91c6e67a', '大众品牌', '/tech/upload/dmppkg/f877517b-8db5-4b8f-a93e-c0fe91c6e67a.pdf?_t=1421229859697', '8a8c80f44ed873de014ed889b3a60004');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed873de014ed88ad31c0006', '4cb80f6e-37b2-4a69-9542-f21a70c211e5', '大众历史', '/tech/upload/dmppkg/4cb80f6e-37b2-4a69-9542-f21a70c211e5.pdf?_t=1421229744478', '8a8c80f44ed873de014ed889b3a60004');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed873de014ed89433c10008', '34578fa8-f1c9-48b6-8cf7-914d81386f28', 'SSP501_CN_vwac.pdf', '/tech/upload/dmppkg/34578fa8-f1c9-48b6-8cf7-914d81386f28.pdf?_t=1432871153305', '8a8c80f44ed873de014ed893c8d00007');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed873de014ed89433ca0009', '9b0d0536-fe06-44b3-989e-25ee56b65060', '大众品牌大使', '/tech/upload/dmppkg/9b0d0536-fe06-44b3-989e-25ee56b65060.pdf?_t=1421229933747', '8a8c80f44ed873de014ed893c8d00007');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed89960014ed89b46340002', 'f877517b-8db5-4b8f-a93e-c0fe91c6e67a', '大众品牌', '/tech/upload/dmppkg/f877517b-8db5-4b8f-a93e-c0fe91c6e67a.pdf?_t=1421229859697', '8a8c80f44ed89960014ed89a62620001');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed89960014ed89b463c0003', '4cb80f6e-37b2-4a69-9542-f21a70c211e5', '大众历史', '/tech/upload/dmppkg/4cb80f6e-37b2-4a69-9542-f21a70c211e5.pdf?_t=1421229744478', '8a8c80f44ed89960014ed89a62620001');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed89edf014ed89f67fe0002', 'f877517b-8db5-4b8f-a93e-c0fe91c6e67a', '大众品牌', '/tech/upload/dmppkg/f877517b-8db5-4b8f-a93e-c0fe91c6e67a.pdf?_t=1421229859697', '8a8c80f44ed89edf014ed89f2bb50001');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed89edf014ed89f68050003', '4cb80f6e-37b2-4a69-9542-f21a70c211e5', '大众历史', '/tech/upload/dmppkg/4cb80f6e-37b2-4a69-9542-f21a70c211e5.pdf?_t=1421229744478', '8a8c80f44ed89edf014ed89f2bb50001');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed89edf014ed8a13ad30005', 'f877517b-8db5-4b8f-a93e-c0fe91c6e67a', '大众品牌', '/tech/upload/dmppkg/f877517b-8db5-4b8f-a93e-c0fe91c6e67a.pdf?_t=1421229859697', '8a8c80f44ed89edf014ed89f683d0004');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed89edf014ed8a13ad90006', '4cb80f6e-37b2-4a69-9542-f21a70c211e5', '大众历史', '/tech/upload/dmppkg/4cb80f6e-37b2-4a69-9542-f21a70c211e5.pdf?_t=1421229744478', '8a8c80f44ed89edf014ed89f683d0004');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed8a524014ed8a58a6f0001', '9b0d0536-fe06-44b3-989e-25ee56b65060', '大众品牌大使', '/tech/upload/dmppkg/9b0d0536-fe06-44b3-989e-25ee56b65060.pdf?_t=1421229933747', '8a8c80f44ed89edf014ed8a13aeb0007');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed8a524014ed8a58a790002', 'f877517b-8db5-4b8f-a93e-c0fe91c6e67a', '大众品牌', '/tech/upload/dmppkg/f877517b-8db5-4b8f-a93e-c0fe91c6e67a.pdf?_t=1421229859697', '8a8c80f44ed89edf014ed8a13aeb0007');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed8a524014ed8a58a810003', '4cb80f6e-37b2-4a69-9542-f21a70c211e5', '大众历史', '/tech/upload/dmppkg/4cb80f6e-37b2-4a69-9542-f21a70c211e5.pdf?_t=1421229744478', '8a8c80f44ed89edf014ed8a13aeb0007');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed8a524014ed8a6f1350005', 'f877517b-8db5-4b8f-a93e-c0fe91c6e67a', '大众品牌', '/tech/upload/dmppkg/f877517b-8db5-4b8f-a93e-c0fe91c6e67a.pdf?_t=1421229859697', '8a8c80f44ed8a524014ed8a6c17c0004');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed8a524014ed8a6f13e0006', '4cb80f6e-37b2-4a69-9542-f21a70c211e5', '大众历史', '/tech/upload/dmppkg/4cb80f6e-37b2-4a69-9542-f21a70c211e5.pdf?_t=1421229744478', '8a8c80f44ed8a524014ed8a6c17c0004');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed8e5f7014ed8e661090002', '9b0d0536-fe06-44b3-989e-25ee56b65060', '大众品牌大使', '/tech/upload/dmppkg/9b0d0536-fe06-44b3-989e-25ee56b65060.pdf?_t=1421229933747', '8a8c80f44ed8e5f7014ed8e656260001');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed8f655014ed8fad3d10002', '4cb80f6e-37b2-4a69-9542-f21a70c211e5', '大众历史', '/tech/upload/dmppkg/4cb80f6e-37b2-4a69-9542-f21a70c211e5.pdf?_t=1421229744478', '8a8c80f44ed8f655014ed8fab8d60001');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed8fb5f014ed8ffdb820002', '4cb80f6e-37b2-4a69-9542-f21a70c211e5', '大众历史', '/tech/upload/dmppkg/4cb80f6e-37b2-4a69-9542-f21a70c211e5.pdf?_t=1421229744478', '8a8c80f44ed8fb5f014ed8ffcf030001');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed90d3a014ed90df3e60002', 'f877517b-8db5-4b8f-a93e-c0fe91c6e67a', '大众品牌', '/tech/upload/dmppkg/f877517b-8db5-4b8f-a93e-c0fe91c6e67a.pdf?_t=1421229859697', '8a8c80f44ed90d3a014ed90ddcee0001');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed910cd014ed9112a7a0002', '4cb80f6e-37b2-4a69-9542-f21a70c211e5', '大众历史', '/tech/upload/dmppkg/4cb80f6e-37b2-4a69-9542-f21a70c211e5.pdf?_t=1421229744478', '8a8c80f44ed910cd014ed91120b20001');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed91181014ed913ae3f0002', '9b0d0536-fe06-44b3-989e-25ee56b65060', '大众品牌大使', '/tech/upload/dmppkg/9b0d0536-fe06-44b3-989e-25ee56b65060.pdf?_t=1421229933747', '8a8c80f44ed8a524014ed8a6c17c0004');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed91181014ed913ae460003', 'vup', 'up! 产品培训-竞品对比', '/uploads/WBT/vup.zip', '8a8c80f44ed8a524014ed8a6c17c0004');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed91181014ed913ae4d0004', 'vacc', 'ACC与前方感应辅助系统', '/uploads/WBT/product/vacc.zip', '8a8c80f44ed8a524014ed8a6c17c0004');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed92050014ed920b0470002', '9b0d0536-fe06-44b3-989e-25ee56b65060', '大众品牌大使', '/tech/upload/dmppkg/9b0d0536-fe06-44b3-989e-25ee56b65060.pdf?_t=1421229933747', '8a8c80f44ed92050014ed920a0f20001');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed92050014ed920b04f0003', 'f877517b-8db5-4b8f-a93e-c0fe91c6e67a', '大众品牌', '/tech/upload/dmppkg/f877517b-8db5-4b8f-a93e-c0fe91c6e67a.pdf?_t=1421229859697', '8a8c80f44ed92050014ed920a0f20001');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed92050014ed920b0550004', '4cb80f6e-37b2-4a69-9542-f21a70c211e5', '大众历史', '/tech/upload/dmppkg/4cb80f6e-37b2-4a69-9542-f21a70c211e5.pdf?_t=1421229744478', '8a8c80f44ed92050014ed920a0f20001');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed92d68014ed9323dd60001', 'vup', 'up! 产品培训-竞品对比', '/uploads/WBT/vup.zip', '8a8c80f44ed92050014ed921d4170008');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed92050014ed921f49b000a', 'f877517b-8db5-4b8f-a93e-c0fe91c6e67a', '大众品牌', '/tech/upload/dmppkg/f877517b-8db5-4b8f-a93e-c0fe91c6e67a.pdf?_t=1421229859697', '8a8c80f44ed92050014ed921d4170008');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed92050014ed921f4a1000b', '4cb80f6e-37b2-4a69-9542-f21a70c211e5', '大众历史', '/tech/upload/dmppkg/4cb80f6e-37b2-4a69-9542-f21a70c211e5.pdf?_t=1421229744478', '8a8c80f44ed92050014ed921d4170008');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed94a4f014ed95482c60005', '4cb80f6e-37b2-4a69-9542-f21a70c211e5', '大众历史', '/tech/upload/dmppkg/4cb80f6e-37b2-4a69-9542-f21a70c211e5.pdf?_t=1421229744478', '8a8c80f44ed94a4f014ed95466ad0001');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed93530014ed93dad4a0002', '34578fa8-f1c9-48b6-8cf7-914d81386f28', 'SSP501_CN_vwac.pdf', '/tech/upload/dmppkg/34578fa8-f1c9-48b6-8cf7-914d81386f28.pdf?_t=1432871153305', '8a8c80f44ed93530014ed93d8a730001');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed94a4f014ed95482b10002', '34578fa8-f1c9-48b6-8cf7-914d81386f28', 'SSP501_CN_vwac.pdf', '/tech/upload/dmppkg/34578fa8-f1c9-48b6-8cf7-914d81386f28.pdf?_t=1432871153305', '8a8c80f44ed94a4f014ed95466ad0001');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed93530014ed93dad540004', 'f877517b-8db5-4b8f-a93e-c0fe91c6e67a', '大众品牌', '/tech/upload/dmppkg/f877517b-8db5-4b8f-a93e-c0fe91c6e67a.pdf?_t=1421229859697', '8a8c80f44ed93530014ed93d8a730001');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed93530014ed93dad590005', '4cb80f6e-37b2-4a69-9542-f21a70c211e5', '大众历史', '/tech/upload/dmppkg/4cb80f6e-37b2-4a69-9542-f21a70c211e5.pdf?_t=1421229744478', '8a8c80f44ed93530014ed93d8a730001');
INSERT INTO `techmdttln_courses` VALUES ('8a8c80f44ed94a4f014ed95482bf0004', 'f877517b-8db5-4b8f-a93e-c0fe91c6e67a', '大众品牌', '/tech/upload/dmppkg/f877517b-8db5-4b8f-a93e-c0fe91c6e67a.pdf?_t=1421229859697', '8a8c80f44ed94a4f014ed95466ad0001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee487f4014ee489979c0016', 'vup', 'up! 产品培训-竞品对比', '/uploads/WBT/vup.zip', '40288a654edf6ab8014edf6bb3ef0001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee487f4014ee489978f0014', '34578fa8-f1c9-48b6-8cf7-914d81386f28', 'SSP501_CN_vwac.pdf', '/tech/upload/dmppkg/34578fa8-f1c9-48b6-8cf7-914d81386f28.pdf?_t=1432871153305', '40288a654edf6ab8014edf6bb3ef0001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee487f4014ee48997960015', '9b0d0536-fe06-44b3-989e-25ee56b65060', '大众品牌大使', '/tech/upload/dmppkg/9b0d0536-fe06-44b3-989e-25ee56b65060.pdf?_t=1421229933747', '40288a654edf6ab8014edf6bb3ef0001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee487f4014ee4892a570010', 'f877517b-8db5-4b8f-a93e-c0fe91c6e67a', '大众品牌', '/tech/upload/dmppkg/f877517b-8db5-4b8f-a93e-c0fe91c6e67a.pdf?_t=1421229859697', '40288a654edf6ab8014edf6bb3ef0001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee487f4014ee4892a5e0011', '4cb80f6e-37b2-4a69-9542-f21a70c211e5', '大众历史', '/tech/upload/dmppkg/4cb80f6e-37b2-4a69-9542-f21a70c211e5.pdf?_t=1421229744478', '40288a654edf6ab8014edf6bb3ef0001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee49204014ee492d1950010', 'vjtbssbxt', '交通标志识别系统', '/uploads/WBT/product/vjtbssbxt.zip', '40288a654ee49204014ee49283540001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee49204014ee492f1760012', 'vpcxt', '泊车辅助系统', '/uploads/WBT/product/vpcxt.zip', '40288a654ee49204014ee49283540001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee49204014ee493136a0028', 'vjtbssbxt', '交通标志识别系统', '/uploads/WBT/product/vjtbssbxt.zip', '40288a654ee49204014ee49283540001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee49204014ee4931372002a', 'vpcxt', '泊车辅助系统', '/uploads/WBT/product/vpcxt.zip', '40288a654ee49204014ee49283540001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee49204014ee493138f0032', 'vshijia', '试驾辅助工具', '/uploads/WBT/vshijia.zip', '40288a654ee49204014ee49283540001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee49204014ee49313a50038', 'iTV05', '功能和结构-诊断接口VAS5054A和5055', '/uploads/iTV/iTV05.mp4', '40288a654ee49204014ee49283540001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee49204014ee49313f10039', 'iTV06', 'CSS报告', '/uploads/iTV/iTV06.mp4', '40288a654ee49204014ee49283540001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee49204014ee49313fe003a', 'iTV07', '大众汽车经济备件 1', '/uploads/iTV/iTV07.mp4', '40288a654ee49204014ee49283540001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee49204014ee49322a80073', 'vjtbssbxt', '交通标志识别系统', '/uploads/WBT/product/vjtbssbxt.zip', '40288a654ee49204014ee49283540001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee49204014ee49322b10075', 'vpcxt', '泊车辅助系统', '/uploads/WBT/product/vpcxt.zip', '40288a654ee49204014ee49283540001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee49204014ee49322ce007d', 'vshijia', '试驾辅助工具', '/uploads/WBT/vshijia.zip', '40288a654ee49204014ee49283540001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee49204014ee49322e40083', 'iTV05', '功能和结构-诊断接口VAS5054A和5055', '/uploads/iTV/iTV05.mp4', '40288a654ee49204014ee49283540001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee49204014ee49322e80084', 'iTV06', 'CSS报告', '/uploads/iTV/iTV06.mp4', '40288a654ee49204014ee49283540001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee49204014ee49322ec0085', 'iTV07', '大众汽车经济备件 1', '/uploads/iTV/iTV07.mp4', '40288a654ee49204014ee49283540001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee49204014ee495111500be', 'vjtbssbxt', '交通标志识别系统', '/uploads/WBT/product/vjtbssbxt.zip', '40288a654ee49204014ee49283540001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee49204014ee495111f00c0', 'vpcxt', '泊车辅助系统', '/uploads/WBT/product/vpcxt.zip', '40288a654ee49204014ee49283540001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee49204014ee495113800c8', 'vshijia', '试驾辅助工具', '/uploads/WBT/vshijia.zip', '40288a654ee49204014ee49283540001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee49204014ee495114a00ce', 'iTV05', '功能和结构-诊断接口VAS5054A和5055', '/uploads/iTV/iTV05.mp4', '40288a654ee49204014ee49283540001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee49204014ee495114e00cf', 'iTV06', 'CSS报告', '/uploads/iTV/iTV06.mp4', '40288a654ee49204014ee49283540001');
INSERT INTO `techmdttln_courses` VALUES ('40288a654ee49204014ee495115300d0', 'iTV07', '大众汽车经济备件 1', '/uploads/iTV/iTV07.mp4', '40288a654ee49204014ee49283540001');
