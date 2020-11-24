create table IC_ET_PUB_FLAG
(
  TYPE        VARCHAR2(50) not null,
  CODE        VARCHAR2(20) not null,
  CODE_TEXT   VARCHAR2(50),
  DESCRIPTION VARCHAR2(100)
);

insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('CERTIFICATE_ISMETRO', '0', '其他', '本单位职工');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('CERTIFICATE_TYPE', '5', '工卡', '证件类型');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('ISSUE_STATUS', '0', '未发行', '发行状态');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('ISSUE_STATUS', '1', '已发行', '发行状态');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('ISSUE_STATUS', '2', '注销', '发行状态');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('TICKET_STATUS', '200', '初始化', '票卡状态');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('TICKET_STATUS', '201', 'Init II (Pre–value loaded @E/S)ES预赋值', '票卡状态');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('TICKET_STATUS', '202', 'BOM/TVM发售', '票卡状态');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('TICKET_STATUS', '203', '出站(exit)', '票卡状态');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('TICKET_STATUS', '204', '列车故障模式出站(exit during Train–disruption)', '票卡状态');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('TICKET_STATUS', '205', '进站BOM更新(upgrade at BOM for Entry)', '票卡状态');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('TICKET_STATUS', '206', '非付费区免费更新（BOM/pca 非付费区）', '票卡状态');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('TICKET_STATUS', '207', '非付费区付费更新（BOM/pca 非付费区）', '票卡状态');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('TICKET_STATUS', '208', '进站(entry)', '票卡状态');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('TICKET_STATUS', '209', '出站BOM更新(upgrade at BOM for Exit)', '票卡状态');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('TICKET_STATUS', '210', '无进站码更新（BOM/pca 付费区）', '票卡状态');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('TICKET_STATUS', '211', '超时更新（BOM/pca 付费区）', '票卡状态');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('TICKET_STATUS', '212', '超乘更新（BOM/pca 付费区）', '票卡状态');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('TICKET_STATUS', '213', 'ET for Exit(出站票)', '票卡状态');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('TICKET_STATUS', '214', '退卡', '票卡状态');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('TICKET_STATUS', '215', '车票注销', '票卡状态');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('PHY_CHARACTER', '2', 'CPU', '票卡物理类型');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('CERTIFICATE_SEX', '0', '女', '性别');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('CERTIFICATE_SEX', '1', '男', '性别');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('CERTIFICATE_ISCOMPANY', '0', '个人卡', '持卡类型');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('CERTIFICATE_ISCOMPANY', '1', '单位卡', '持卡类型');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('CERTIFICATE_TYPE', '0', '身份证', '证件类型');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('CERTIFICATE_TYPE', '1', '军官证', '证件类型');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('CERTIFICATE_TYPE', '2', '护照', '证件类型');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('CERTIFICATE_TYPE', '3', '入境证（仅限香港/台湾居民使用）', '证件类型');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('CERTIFICATE_TYPE', '4', '临时身份证', '证件类型');
insert into IC_ET_PUB_FLAG (TYPE, CODE, CODE_TEXT, DESCRIPTION)
values ('CERTIFICATE_ISMETRO', '1', '通卡员工卡', '本单位职工');
commit;
