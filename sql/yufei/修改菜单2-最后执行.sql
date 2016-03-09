-- 菜单修改
-- select * from ecan_app where app_name='i18n.classdata.mana';

delete from ecan_app where  app_name in ('i18n.appname.mdttln','i18n.appname.exam','i18n.appname.tagmanage','i18n.appname.messtype','i18n.appname.message');

update ecan_app set level_code='1013' where app_name='i18n.classdata.mana';

delete from Ecan_App where id=8110;

update Ecan_App set app_code='',fun_code='' where app_name='i18n.appname.mw.b8';
update Ecan_App set level_code='101310041001' where app_name='i18n.appname.mw.funsystem';
update Ecan_App set level_code='101310041002' where app_name='i18n.appname.mw.competition';

delete from Ecan_Auth where  app_id=( select id from Ecan_app where  app_name='i18n.appname.mw.gg');

 delete from Ecan_Auth where  app_id in ( select id from Ecan_app where  app_name='i18n.appname.questionmanager');
 
 	insert into ecan_i18n_properties 
set id='ff8080812349014ea01tk943f03ff006',
	lang_type='zh_CN',
    TEXT_VALUE='调查名称',
    property_id='i18n.survey.name',
    app_ID='SYSTEM';
    
            insert into ecan_i18n_properties 
set id='3f80808223490g4ea0138943f03ffl06',
	lang_type='en',
    TEXT_VALUE='Survey Name',
    property_id='i18n.survey.name',
    app_ID='SYSTEM';
    
    delete from Ecan_Auth where app_id=2;
    
    update Ecan_App set level_code='101410001001' where app_name='i18n.appname.mainsellingPoint';
update Ecan_App set level_code='101410001002' where app_name='i18n.appname.conentsellingPoint';


update ecan_i18n_properties set TEXT_VALUE='卡片标题管理' where PROPERTY_ID='i18n.appname.mainsellingPoint' and lang_type='zh_CN';
update ecan_i18n_properties set TEXT_VALUE='selling Point Title manage' where PROPERTY_ID='i18n.appname.mainsellingPoint' and lang_type='en';

update ecan_i18n_properties set TEXT_VALUE='卡片内容管理' where PROPERTY_ID='i18n.appname.conentsellingPoint' and lang_type='zh_CN';
update ecan_i18n_properties set TEXT_VALUE='selling Point Content manage' where PROPERTY_ID='i18n.appname.conentsellingPoint' and lang_type='en';