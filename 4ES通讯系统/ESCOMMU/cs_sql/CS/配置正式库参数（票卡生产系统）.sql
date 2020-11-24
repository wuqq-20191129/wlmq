--功能: 配置票卡生产系统表

--更新BCP配置表，数据库地址为正式库
UPDATE ACC_TK.IC_ES_BCP_CONFIG SET SERVER='10.99.1.15:1521',DB='acc';
--更新ES配置表，发卡商代码、行业代码、卡版本号、和密钥版本为正式密钥
UPDATE ACC_TK.IC_ES_CFG_SYS SET SENDER_CODE='0731',BUSI_CODE='0000',CARD_VERSION='0001',KEY_VERSION='01';

--提交
commit;