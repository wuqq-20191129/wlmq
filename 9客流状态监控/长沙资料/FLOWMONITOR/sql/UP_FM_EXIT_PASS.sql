CREATE OR REPLACE PROCEDURE UP_FM_EXIT_PASS(V_PASS          IN VARCHAR2, --输入密码
                                            P_RESULT        OUT VARCHAR2) --返回结果代码

---------------------------------------------------------------------------------
--过程名:  UP_FM_EXIT_PASS
--系统：状态客流监视系统
--功能: 检验退出密码
--输出:P_RESULT 返回结果代码
-------------------------------------------------------------------------------

AS
  TMP_COUNT     INTEGER; --临时计数变量

BEGIN
    
    SELECT COUNT(1) INTO TMP_COUNT FROM fm_password;      
    --数据表为空时 
    IF TMP_COUNT=0 THEN
        P_RESULT := '1';
        RETURN;
    ELSE
       --数据表存在该密码
       SELECT COUNT(1) INTO TMP_COUNT FROM fm_password WHERE exit_pass = V_PASS;
       IF TMP_COUNT >=1 THEN
          P_RESULT := '0';
          RETURN;
       ELSE
          --密码不正确
          P_RESULT := '2';
          RETURN;
       END IF;
     END IF;

END UP_FM_EXIT_PASS;
