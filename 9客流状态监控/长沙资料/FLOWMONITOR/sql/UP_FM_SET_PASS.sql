CREATE OR REPLACE PROCEDURE UP_FM_SET_PASS(V_OLD_PASS          IN VARCHAR2, --输入旧密码
                                           V_NEW_PASS          IN VARCHAR2, --输入新密码
                                           P_RESULT            OUT VARCHAR2) --返回结果代码

---------------------------------------------------------------------------------
--过程名:  UP_FM_SET_PASS
--系统：状态客流监视系统
--功能: 密码修改
--输出:P_RESULT 返回结果代码
-------------------------------------------------------------------------------

AS
  TMP_COUNT     INTEGER; --临时计数变量

BEGIN
    --新密码不能为空
    IF V_NEW_PASS IS NULL OR V_NEW_PASS = '' THEN
        P_RESULT := 1;
        RETURN;
    END IF;

    SELECT COUNT(1) INTO TMP_COUNT FROM fm_password;
    --数据表为空时
    IF TMP_COUNT=0 THEN
        --初始密码
        IF V_OLD_PASS IS NULL OR V_OLD_PASS = '' THEN
            INSERT INTO fm_password SELECT V_NEW_PASS FROM dual;
            P_RESULT := 0;
            RETURN;
        ELSE
            P_RESULT := 1;
            RETURN;
        END IF;
    ELSE
       --数据表存在该密码
       SELECT COUNT(1) INTO TMP_COUNT FROM fm_password WHERE exit_pass = V_OLD_PASS;
       IF TMP_COUNT >=1 THEN
          UPDATE fm_password SET exit_pass = V_NEW_PASS;
          P_RESULT := 0;
          RETURN;
       ELSE
          --密码不正确
          P_RESULT := 1;
          RETURN;
       END IF;
     END IF;

END UP_FM_SET_PASS;
