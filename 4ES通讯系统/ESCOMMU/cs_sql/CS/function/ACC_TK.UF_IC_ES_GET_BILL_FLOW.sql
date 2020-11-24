create or replace
FUNCTION ACC_TK.UF_IC_ES_GET_BILL_FLOW
( 
  msg out VARCHAR2
) RETURN VARCHAR2 AS

  --单号生成使用变量
  billType char(2);                 --生产单类型
  currentIntNo INTEGER;             --表记录的当前流水号
  initValue INTEGER;                --流水初始化值
  billYear INTEGER;                 --单据年
  curYear  INTEGER;                 --当前年
  waterNo INTEGER;                  --流水号
  strWaterNo VARCHAR2(16);          --流水号字符串值 
  billNo CHAR(12);                  --单号
  
BEGIN

  curYear := TO_NUMBER(to_char(SYSDATE,'yyyy'));
  initValue := 1;
  billType := 'SD';
 
  --生成生产单号------------------------------------
  --取出当前流水号
  begin
    select current_int_no into currentIntNo 
      from ACC_TK.IC_PRM_BILL_CURRENT_FLOW where bill_main_type_id=billType;
  EXCEPTION
    when OTHERS then
    begin
      null;
    end;
  end;
    
  if(currentIntNo is not null) THEN--存在当前流水号
  begin
    waterNo := currentIntNo;
    --取出当前年
    begin
      select bill_year INTO billYear from ACC_TK.IC_PRM_BILL_CURRENT_DATE;
    EXCEPTION
      when OTHERS then
      begin
        null;
      end;
    end;
    if(billYear is null) THEN--当前年不存在
    begin
      insert into ACC_TK.IC_PRM_BILL_CURRENT_DATE(bill_year) values(curYear);
    end;
    else
    begin
      if(billYear<>curYear) THEN--系统当前年与表当前年不一致 已过1年
      begin
          waterNo := initValue;
          update ACC_TK.IC_PRM_BILL_CURRENT_DATE set bill_year=curYear;
      end;
      END IF;
      update ACC_TK.IC_PRM_BILL_CURRENT_FLOW set current_int_no=waterNo+1 where bill_main_type_id=billType;  
    end;
    end IF;
  END;
  else --当前流水号不存在
  begin
    waterNo := initValue;
    --流水号+1
    insert into ACC_TK.IC_PRM_BILL_CURRENT_FLOW(bill_main_type_id,current_no,current_int_no) values(billType,'',initValue+1);
  end;
  end if;
  
  msg :=  waterNo;
  
  --格式化流水号
  strWaterNo := LPAD(waterNo,6,'0');
  --单号=单类型+当前年+6位流水号
  billNo := billType||curYear||strWaterNo;
    
  RETURN billNo;
  
END UF_IC_ES_GET_BILL_FLOW;
