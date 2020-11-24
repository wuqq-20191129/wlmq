/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.login.bo;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.login.result.AuthResult;
import com.goldsign.login.result.EditResult;
import com.goldsign.login.vo.AuthInParam;

/**
 *
 * @author oywl
 */
public interface ILoginBo {

    String SUCCESS_AUTH = "0";//�ɹ�
    String ERROR_PARAM_NULL = "300";//�������Ϊ��
    String ERROR_USER_NOT_EXIST = "301";//�û�������
    String ERROR_USER_LOCKED = "302";//�û�������
    String ERROR_USER_NOT_IN_VALID_DATE = "303";//�û��ѹ���
    String ERROR_PASSWORD_WRONG = "304";//�������
    String ERROR_PASSWORD_NOT_IN_VALID_DATE = "305";//�����ѹ���
    String ERROR_DENY_IPS = "306";//�û�IP���ܷ���Ӧ��
    String ERROR_DATABASE_CONNECT_EXCEPTION = "310";//���ݿ����Ӵ���

    /**
     *��¼��֤
     * @param account �˺�
     * @param password ����
     * @param sysFlag ϵͳ��ʶ
     * @param dbHelper
     * @return
     * @throws Exception
     */
    public AuthResult authorization(String account, String password, String sysFlag, DbHelper dbHelper);
    
    /**
     *��¼��֤2
     * @param account �˺�
     * @param password ����
     * @param sysFlag ϵͳ��ʶ
     * @param dbHelper
     * @return
     * @throws Exception
     */
    public AuthResult authorization(String account, String password, String sysFlag, AuthInParam authInParam, DbHelper dbHelper);

    /**
     *�޸�����
     * @param account �˺�
     * @param oldPassword ������
     * @param newPassword ������
     * @param dbHelper
     * @return
     */
    public EditResult editPassword(String account, String oldPassword, String newPassword, DbHelper dbHelper);
    
    /**
     * �ǳ��ռǼ�¼
     *
     * @param request
     * @param operatorID
     * @return
     * @throws Exception
     */
    public int logLogoutInfo(String flowID, String operatorID, DbHelper dbHelper );
}
