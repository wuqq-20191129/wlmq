package com.goldsign.frame.env;

/**
 * This class is used to store the constant values for the framework
 */
public interface FrameworkConstant {

    /**
     *  Message resouce used by the framework
     */
    public final static String FRAMEWORK_RESOURCE = "FRAMEWORKMESSAGE";


    /**
     *  The id for "login fail" message
     */
    public final static String MSG_LOGIN_FAIL = "FRAMEWORKMESSAGE_LOGIN_FAIL";


    /**
     *  The id for "password is expired" message
     */
    public final static String MSG_PASSWORD_EXPIRED = "FRAMEWORKMESSAGE_PASSWORD_EXPIRED";


    /**
     *  The id for "user account is disabled" message
     */
    public final static String MSG_ACCOUNT_DISABLED = "FRAMEWORKMESSAGE_ACCOUNT_DISABLED";


    /**
     *  The id for "not authorized to application" message
     */
    public final static String MSG_NOT_AUT_APPLICATION = "FRAMEWORKMESSAGE_NOT_AUT_APPLICATION";


    /**
     *  The id for "application is disabled" message
     */
    public final static String MSG_APPLICATION_DISABLED = "FRAMEWORKMESSAGE_APPLICATION_DISABLED";


    /**
     *  The id for "success to change password" message
     */
    public final static String MSG_CHANGE_PASSWORD_SUCCESS = "FRAMEWORKMESSAGE_CHANGE_PASSWORD_SUCCESS";


    /**
     *  The id for "fail to change password" message
     */
    public final static String MSG_CHANGE_PASSWORD_FAIL = "FRAMEWORKMESSAGE_CHANGE_PASSWORD_FAIL";


    /**
     *  The id for "Invalid password" message
     */
    public final static String MSG_INVALID_PASSWORD = "FRAMEWORKMESSAGE_INVALID_PASSWORD";


    /**
     *  The id for "The new password is too short" message
     */
    public final static String MSG_NEW_PASSWORD_TOO_SHORT = "FRAMEWORKMESSAGE_NEW_PASSWORD_TOO_SHORT";


    /**
     *  The id for "The new password is too long" message
     */
    public final static String MSG_NEW_PASSWORD_TOO_LONG = "FRAMEWORKMESSAGE_NEW_PASSWORD_TOO_LONG";


    /**
     *  The id for "The new password equals the user id" message
     */
    public final static String MSG_NEW_PASSWORD_EQUALS_USER_ID = "FRAMEWORKMESSAGE_NEW_PASSWORD_EQUALS_USER_ID";


    /**
     *  The id for "The new password contains white spaces" message
     */
    public final static String MSG_NEW_PASSWORD_CONTAINS_SPACE = "FRAMEWORKMESSAGE_NEW_PASSWORD_CONTAINS_SPACE";


    /**
     *  The id for "The new password contains more than 2 successive repeat of a character or digit." message
     */
    public final static String MSG_NEW_PASSWORD_SUCCESSIVE_REPEAT = "FRAMEWORKMESSAGE_NEW_PASSWORD_SUCCESSIVE_REPEAT";


    /**
     *  The id for "The new password is not alphanumeric." message
     */
    public final static String MSG_NEW_PASSWORD_ALPHANUMERIC = "FRAMEWORKMESSAGE_NEW_PASSWORD_ALPHANUMERIC";


    /**
     *  The id for "The new password do not contain enough digit." message
     */
    public final static String MSG_NEW_PASSWORD_NOT_ENOUGH_DIGIT = "FRAMEWORKMESSAGE_NEW_PASSWORD_NOT_ENOUGH_DIGIT";


    /**
     *  The id for "The new password has been used before." message
     */
    public final static String MSG_NEW_PASSWORD_USED = "FRAMEWORKMESSAGE_NEW_PASSWORD_USED";

    /**
     *  The id for "Invalid password. Fail to unlock." message
     */
    public final static String MSG_INVALID_PASSWORD_UNLOCKAPP = "FRAMEWORKMESSAGE_INVALID_PASSWORD_UNLOCKAPP";

    /**
     *  The id for "User account is disabled. Please contact system administrator for help." message
     */
    public final static String MSG_ACCOUNT_DISABLED_UNLOCKAPP = "FRAMEWORKMESSAGE_ACCOUNT_DISABLED_UNLOCKAPP";

    /**
     *  The id for "success to change default application" message
     */
    public final static String MSG_CHANGE_DEF_APP_SUCCESS = "FRAMEWORKMESSAGE_CHANGE_DEF_APP_SUCCESS";


    /**
     *  The id for "success to enable application" message
     */
    public final static String MSG_ENABLE_APP_SUCCESS = "FRAMEWORKMESSAGE_ENABLE_APP_SUCCESS";


    /**
     *  The id for "success to disable application" message
     */
    public final static String MSG_DISABLE_APP_SUCCESS = "FRAMEWORKMESSAGE_DISABLE_APP_SUCCESS";


    /**
     *  The id for "The new password can not be changed twice within 24 hours." message
     */
    public final static String MSG_NEW_PASSWORD_24_HOURS = "FRAMEWORKMESSAGE_NEW_PASSWORD_24_HOURS";

    public final static String FAIL_TO_CHECK_RECORD_EXISTING = "FRAMEWORKMESSAGE_FAILTOCHECKRECORDEXISTING";
    public final static String FAIL_TO_GET_RECORD = "FRAMEWORKMESSAGE_FAILTOGETRECORD";
    public final static String FAIL_TO_CREATE_RECORD = "FRAMEWORKMESSAGE_FAILTOCREATERECORD";
    public final static String FAIL_TO_UPDATE_RECORD = "FRAMEWORKMESSAGE_FAILTOUPDATERECORD";
    public final static String FAIL_TO_DELETE_RECORD = "FRAMEWORKMESSAGE_FAILTODELETERECORD";
    public final static String RUNTIME_EXCEPTION = "FRAMEWORKMESSAGE_RUNTIMEERROR";
    public final static String FRAMEWORKMESSAGE_SAVE_SUCCESS = "FRAMEWORKMESSAGE_SAVE_SUCCESS";


    /**
     * Rescource id and description "ViewCurrentUser"
     */
    public final static String FRAMEWORKMESSAGE_RES_ID_VIEW_USER = "FRAMEWORKMESSAGEViewCurrentUser";
    public final static String FRAMEWORKMESSAGE_RES_DESC_VIEW_USER = "View Current User";

    /**
     * Rescource id and description "LockApp"
     */
    public final static String FRAMEWORKMESSAGE_RES_ID_LOCK_APP = "FRAMEWORKMESSAGEEnableDisableApp";
    public final static String FRAMEWORKMESSAGE_RES_DESC_LOCK_APP = "Enable/Disable Application";

    /**
     * Rescource id and description "AllowMultiLogin"
     */
    public final static String FRAMEWORKMESSAGE_RES_ID_MUL_LOGIN   = "FRAMEWORKMESSAGEAllowMultiLogin";
    public final static String FRAMEWORKMESSAGE_RES_DESC_MUL_LOGIN = "Allow the same user to login simultaneously";

    /**
     * document flow exception
     */
    public final static String FRAMEWORKMESSAGE_DF_DUPLICATIONDOC = "FRAMEWORKMESSAGE_DF_DUPLICATIONDOC";
    public final static String FRAMEWORKMESSAGE_DF_ALREADYAPP = "FRAMEWORKMESSAGE_DF_ALREADYAPP";
    public final static String FRAMEWORKMESSAGE_DF_RECNOTFOUND = "FRAMEWORKMESSAGE_DF_RECNOTFOUND";
    public final static String FRAMEWORKMESSAGE_DF_PATHNOTFOUND = "FRAMEWORKMESSAGE_DF_PATHNOTFOUND";
    public final static String FRAMEWORKMESSAGE_DF_DOCKEYTOOLONG = "FRAMEWORKMESSAGE_DF_DOCKEYTOOLONG";

    //Message Key for Document Flow
    final static String DOC_FLOW_MSG_KEY    = "MSG_KEY";

    /**
     * Constant for record generated by system
     */
    public final static String SYSTEM_GEN = "*SYS";

 }