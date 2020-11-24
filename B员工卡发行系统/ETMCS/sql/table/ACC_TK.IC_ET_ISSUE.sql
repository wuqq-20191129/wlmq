DROP TABLE "ACC_TK"."IC_ET_ISSUE";
CREATE TABLE "ACC_TK"."IC_ET_ISSUE"(
    employee_id       varchar(10)       NOT NULL,
    employee_name     varchar(10)       NULL,
    gender            char(1)           default 0,
    logical_id        varchar(20)       NOT NULL,
    phy_id            varchar(20)       NULL,
    print_id          varchar(16)       NULL,
    make_oper         varchar(10)       NULL,
    make_time         date              NULL,
    return_oper       varchar(10)       NULL,
    return_time       date              NULL,
    use_state         char(1)           NULL, --0：发卡，1：退卡
    remark            varchar(256)      NULL,
    EMPLOYEE_DEPARTMENT VARCHAR2(2),
    EMPLOYEE_POSITION   VARCHAR2(2)
);
ALTER TABLE "ACC_TK"."IC_ET_ISSUE" ADD PRIMARY KEY (employee_id, logical_id);