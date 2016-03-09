-- 修改菜单

-- 备份表
 create table ecan_app_bak28 as select  *  from ecan_app;
 create table Ecan_Auth_bak28 as select  *  from Ecan_Auth;
 create table ecan_i18n_properties_bak28 SELECT * FROM ecan_i18n_properties;

-- 系统管理
-- 去掉“批量生成”，即不挂在系统管理下
update Ecan_App set level_code='' where id=31;

-- 去掉“讲师课程”，即不挂在系统管理下
update Ecan_App set level_code='' where id=101;

-- 去掉“资源管理”，即不挂在系统管理下
update Ecan_App set level_code='' where id=34;

-- 去掉“假期管理”，即不挂在系统管理下
update Ecan_App set level_code='' where id=23;

-- 去掉“缓存清理”，即不挂在系统管理下
update Ecan_App set level_code='' where id=33;

-- 增加“问卷管理”二级菜单，并增加admin访问权限
update Ecan_App set level_code='100910091001',IDX=0 where id=121;
insert into Ecan_App set id=38,
			app_name='i18n.appname.questionmanager',
			level_code='10091009',
            is_outside=0,
            IDX=3;
insert into Ecan_Auth set id=8219, role_id='admin',app_id=38;

-- 增加“分类管理”二级菜单，并增加admin访问权限
update Ecan_App set level_code='100910011001',IDX=0 where id=22;
insert into Ecan_App set id=49,
			app_name='i18n.appname.domainmanage',
			level_code='10091001',
            is_outside=0,
            IDX=4;
insert into Ecan_Auth set id=8220, role_id='admin',app_id=49;

-- 增加“语言管理”二级菜单，并增加admin访问权限
update Ecan_App set level_code='100910031001',IDX=0 where id=24;
insert into Ecan_App set id=40,
			app_name='i18n.appname.i18nmanager',
			level_code='10091003',
            is_outside=0,
            IDX=5;
insert into Ecan_Auth set id=8221, role_id='admin',app_id=40;

-- 测评中心
-- 增加“测评活动管理”二级菜单，并增加admin访问权限
update Ecan_App set level_code='100410051001',IDX=0 where id=7;
update Ecan_App set level_code='100410051002',IDX=1 where id=8;
update Ecan_App set level_code='100410051003',IDX=2 where id=19;
update Ecan_App set level_code='100410051004',IDX=3 where id=27;

insert into Ecan_App set id=41,
			app_name='i18n.testactive.title',
			level_code='10041005',
            is_outside=0,
            IDX=0;
insert into Ecan_Auth set id=8222, role_id='admin',app_id=41;

-- 增加“培训评估管理”二级菜单，并增加admin访问权限
update Ecan_App set level_code='100410061001',IDX=0 where id=8002;
update Ecan_App set level_code='100410061002',IDX=1 where id=8003;
update Ecan_App set level_code='100410061003',IDX=2 where id=8004;

insert into Ecan_App set id=42,
			app_name='i18n.trainplan.evaluationmana',
			level_code='10041006',
            is_outside=0,
            IDX=1;
insert into Ecan_Auth set id=8223, role_id='admin',app_id=42;

-- 微信管理
update Ecan_App set level_code='' where id in (8102,8107,8106,8105,8108,8109);

update Ecan_App set app_code='info' where id=20;

update ecan_i18n_properties set TEXT_VALUE='微信管理' where id='1506';
update ecan_i18n_properties set TEXT_VALUE='WeiXin Management' where id='534';

-- 移动学院
update ecan_i18n_properties set TEXT_VALUE='移动学院' where PROPERTY_ID='i18n.appname.mdtt' and lang_type='zh_CN';

update Ecan_App set level_code='101110011001' where id=1001;

-- 内容管理
update ecan_i18n_properties set TEXT_VALUE='内容管理' where id='A1123123123';
    
insert into Ecan_App set id=8236,
			app_name='i18n.appname.mdttpkg',
			level_code='10111001',
            is_outside=0,
            IDX=0;
insert into Ecan_Auth set id=8236, role_id='admin',app_id=8236;

-- 任务管理
insert into Ecan_App set id=8238,
			app_name='i18n.study.task',
			level_code='10111002',
            is_outside=0,
            IDX=2;
insert into Ecan_Auth set id=8248, role_id='admin',app_id=8238;

insert into Ecan_App set id=8239,
			app_name='i18n.study.task',
            app_code='mdttln',
            fun_name='任务管理',
            fun_code='index',
			level_code='101110021001',
            is_outside=0,
            IDX=1;
  
  insert into Ecan_Auth set id=8239, role_id='admin',app_id=8239;

-- 考试管理
insert into Ecan_App set id=8240,
			app_name='i18n.exam.mana',
			level_code='10111004',
            is_outside=0,
            IDX=4;
insert into Ecan_Auth set id=8240, role_id='admin',app_id=8240;

 insert into Ecan_App set id=8249,
			app_name='i18n.exam.mana',
            app_code='exam',
            fun_name='考试管理',
            fun_code='index',
			level_code='101110041001',
            is_outside=0,
            IDX=0;
  
  insert into Ecan_Auth set id=8249, role_id='admin',app_id=8249;

-- 标签管理
insert into Ecan_App set id=8241,
			app_name='i18n.contenttag.mana',
			level_code='10111005',
            is_outside=0,
            IDX=5;
insert into Ecan_Auth set id=8241, role_id='admin',app_id=8241;

 insert into Ecan_App set id=8250,
			app_name='i18n.contenttag.mana',
            app_code='contenttag',
            fun_name='标签管理',
            fun_code='index',
			level_code='101110051001',
            is_outside=0,
            IDX=0;
  
  insert into Ecan_Auth set id=8250, role_id='admin',app_id=8250;

-- 通知管理
insert into Ecan_App set id=8242,
			app_name='i18n.Note.mana',
			level_code='10111006',
            is_outside=0,
            IDX=6;
insert into Ecan_Auth set id=8242, role_id='admin',app_id=8242;

 insert into Ecan_App set id=8251,
			app_name='i18n.Note.mana',
            app_code='message',
            fun_name='通知管理',
            fun_code='index',
			level_code='101110061001',
            is_outside=0,
            IDX=0;
  
  insert into Ecan_Auth set id=8251, role_id='admin',app_id=8251;
  
   insert into Ecan_App set id=8252,
			app_name='i18n.NoteType.mana',
            app_code='messtype',
            fun_name='通知管理',
            fun_code='index',
			level_code='101110061002',
            is_outside=0,
            IDX=1;
  
  insert into Ecan_Auth set id=8252, role_id='admin',app_id=8252;

-- 首页幻灯管理
update ecan_i18n_properties set TEXT_VALUE='首页幻灯管理' where PROPERTY_ID='i18n.appname.mw.homenew' and lang_type='zh_CN';

insert into Ecan_App set id=8243,
			app_name='i18n.appname.mw.homenew',
			level_code='10111007',
            is_outside=0,
            IDX=7;
insert into Ecan_Auth set id=8243, role_id='admin',app_id=8243;

 insert into Ecan_App set id=8253,
			app_name='i18n.appname.mw.homenew',
            app_code='homenew',
            fun_name='首页幻灯管理',
            fun_code='index',
			level_code='101110071001',
            is_outside=0,
            IDX=0;
  
  insert into Ecan_Auth set id=8253, role_id='admin',app_id=8253;

-- 知识库管理
update Ecan_App set level_code='101110031001',IDX=0 where id=1003;
    
insert into Ecan_App set id=8237,
			app_name='i18n.appname.mdttqa',
			level_code='10111003',
            is_outside=0,
            IDX=100;
insert into Ecan_Auth set id=8238, role_id='admin',app_id=8237;

update ecan_i18n_properties set TEXT_VALUE='知识库管理' where PROPERTY_ID='i18n.appname.mdttqa' and lang_type='zh_CN';

-- e-Selling Point
insert into Ecan_App set id=8228,
			app_name='i18n.e-Selling Point',
            app_code='sellingPoint',
            fun_name='e-Selling Point',
            fun_code='index',
			level_code='1014',
            is_outside=0,
            IDX=199;
  
insert into Ecan_Auth set id=8228, role_id='admin',app_id=8228;

insert into Ecan_App set id=8234,
			app_name='i18n.picture.mana',
			level_code='10141000',
            is_outside=0,
            IDX=0;
insert into Ecan_Auth set id=8234, role_id='admin',app_id=8234;

update Ecan_App set level_code='101410001001',IDX=1 where id=8224;
update Ecan_App set level_code='101410001002',IDX=2 where id=8226;


-- 课堂课件管理
insert into Ecan_App set id=8230,
	app_name='i18n.classdata.mana',
	app_code='retailknowhow',
    fun_name='课堂课件管理',
    fun_code='index',
	level_code='1014',
    is_outside=0,
	IDX=201;
  
insert into Ecan_Auth set id=8230, role_id='admin',app_id=8230;
  
update Ecan_App set level_code='10131001',IDX=0 where id=8005;
update Ecan_App set level_code='10131002',IDX=1 where id=8006;
update Ecan_App set level_code='10131003',IDX=2 where id=8007;
update Ecan_App set level_code='10131004',IDX=2 where APP_NAME='i18n.appname.mw.b8';


-- app发布包管理
insert into Ecan_App set id=8235,
			app_name='i18n.apppack.mana',
            app_code='app',
            fun_name='app发布包管理',
            fun_code='index',
			level_code='1015',
            is_outside=0,
            IDX=201;
  
insert into Ecan_Auth set id=8235, role_id='admin',app_id=8235;

update Ecan_App set level_code='10151000',IDX=0 where id=8008;

-- 不显示菜单
-- Ecan_Auth去掉或者level_code变为5位
-- delete from Ecan_Auth where app_id=2; -- 屏蔽“个人中心”

delete from Ecan_Auth where app_id=3;-- 屏蔽“学习中心”
update Ecan_app set fun_code='index' where app_name='i18n.appname.studycenter';

update Ecan_app set IDX=1 where app_name='i18n.appname.system'; -- 改变顺序

delete from Ecan_App where id=8110;