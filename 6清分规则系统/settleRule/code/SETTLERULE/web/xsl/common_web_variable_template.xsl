<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xml"/>

    <!-- ҳ���õ��ı��� ͨ��ģ�� -->
    <xsl:template name="common_web_variable">
        <xsl:param name="divideShow" select="0"/>

        <!--һֱ����DISABLE ״̬���� -->
        <DIV id="disableAlwaysObs" value=""/>
        <!--һֱ����ENABLED ״̬���� -->
        <DIV id="enableAlwaysObs" value=""/>

        <!-- ������ѡ����б��е�����Ϣ(��ǰ������) ��ʼֵ��"init" -->
        <DIV id="rowSelected" value="init" />

        <!-- ������ѡ����б��е�����Ŀ(��ǰ��ѡ��) ��ʼֵ��"0" -->
        <DIV id="rowTickedCount" value="0" />

        <!-- ���������ֶοؼ����ƣ��ԣ��ָ��� ��ʼֵ��"" -->
        <DIV id="primaryKeys" value="" />

        <!-- ����ģ��Ȩ�� ��ʼֵ��"" -->

        <!-- ��ѯȨ�޶�Ӧ��ť-->
        <DIV id="priviQuery" name="priviQuery" value="btQuery" />
        <!-- ����Ȩ�޶�Ӧ��ť-->
        <DIV id="priviAdd" name="priviAdd" value="btAdd,btSave,btSave1,btCancle" />
        <!-- ɾ��Ȩ�޶�Ӧ��ť-->
        <DIV id="priviDelete" name="priviDelete" value="btDelete" />
        <!-- ���Ȩ�޶�Ӧ��ť-->
        <DIV id="priviAudit" name="priviAudit" value="btAudit" />
        <!-- �޸�Ȩ�޶�Ӧ��ť-->
        <DIV id="priviModify" name="priviModify" value="btModify,btModify1,btSave,btSave1,btCancle,updateId" />

        <!-- ��¡Ȩ�޶�Ӧ��ť-->
        <DIV id="priviClone" name="priviClone" value="btClone" />
        <!-- �ύȨ�޶�Ӧ��ť-->
        <DIV id="priviSubmit" name="priviSubmit" value="btSubmit,btDetailSubmit,btSubmitSam" />
        <!-- ����Ȩ�޶�Ӧ��ť-->
        <DIV id="priviExport" name="priviExport" value="btExport,btExport1,btExportBill,btBack,btBackEnd,btNext,btNextEnd" />
        <!-- ����Ȩ�޶�Ӧ��ť-->
        <DIV id="priviImport" name="priviImport" value="btImport,btImport1" />
        <!-- ��ӡȨ�޶�Ӧ��ť-->
        <DIV id="priviPrint" name="priviPrint" value="btPrint,btPrint1,btPrint2,btPrint3" />

        <!-- �·�Ȩ�޶�Ӧ��ť-->
        <DIV id="priviDownload" name="priviDownload" value="btDownload,btDownload1" />
        <!-- �����ѯȨ�޶�Ӧ��ť-->
        <DIV id="priviRptQuery" name="priviRptQuery" value="btReportQuery" />
        <!-- ����ͳ�ƶ�Ӧ��ť-->
        <DIV id="priviStatistic" name="priviStatistic" value="btStatistic" />
        <!-- �̵�Ȩ�޶�Ӧ��ť-->
        <DIV id="priviCheck" name="priviCheck" value="btCheck,btCheck1" />
        <!-- ����Ȩ�޶�Ӧ��ť-->
        <DIV id="priviDistribute" name="priviDistribute" value="btDistribute" />
        <!-- ȷ���˿�Ȩ�޶�Ӧ��ť-->
        <DIV id="priviRefundOk" name="priviRefundOk" value="btRefundOk" />
        <!-- �ܾ��˿�Ȩ�޶�Ӧ��ť-->
        <DIV id="priviRefundNo" name="priviRefundNo" value="btRefundNo" />
        <!-- ȷ���޸Ķ�Ӧ��ť-->
        <DIV id="priviRefundMd" name="priviRefundMd" value="btRefundMd" />
        <!-- ȷ����˶�Ӧ��ť-->
        <DIV id="priviRefundCk" name="priviRefundCk" value="btRefundCk" />

        <!-- �������й��ܰ�ť -->
        <DIV id="functions" value="btClone,btAdd,btDelete,btModify,btModify1,btCheck,btSave,btSave1,btSubmit,btDetailSubmit,btSubmitSam,btUpdateId,btCancle,btPrint,btPrint1,btPrint2,btPrint3,btImport,btImport1,btExport,btExportBill,btExport1,btQuery,btDownload,btStatistic,btReportQuery,btCheck1,btDownload1,btDistribute,btRefundOk,btRefundNo,btRefundMd,btRefundCk,btAudit,btBack,btBackEnd,btNext,btNextEnd" />

        <!-- ϵͳ�ɲ������ܣ�browse/select/clone/select/add/del/modify/check/save/submit/cancle/print/import/export 12�� -->
        <!-- ���浱ǰ�Ĳ���������Ϣ Ĭ�ϳ�ʼֵΪ��� browse �����嵱ǰ����ɲ����Ĺ���-->
        <!-- <DIV id="operation" value="op_browse" /> -->

        <input type="hidden" name="operation" id="operation" value="op_browse" />
        <input type="hidden" name="command" id="command" value="list" />
        <input type="hidden" name="allSelectedIDs" id="allSelectedIDs" value="" />
        <input type="hidden" name="allAddSelectedIDs" id="allAddSelectedIDs" value="" />
        <input type="hidden" name="ver_date_begin"  id="ver_date_begin" value="" />
        <input type="hidden" name="ver_date_end" id="ver_date_end" value="" />
        <input type="hidden" name="ver_generate" id="ver_generate" value="" />
        <input type="hidden" name="selectedRadios" id="selectedRadios" value="" />
        <DIV  name="curent_version_start_date_value" id="curent_version_start_date_value" value="{/Service/Result/CurrentVersionInformation/b_ava_time}" />


        <!-- �����ʱ����ʼ��ʱ״̬ -->
        <DIV id="op_browse" avriable="btClone,btAdd,btModify1,btCheck,btSubmit,btDetailSubmit,btSubmitSam,btPrint,btExport,btExportBill,btBack,btBackEnd,btNext,btNextEnd,btQuery,btStatistic,btReportQuery,btImport,btImport1,btCheck1,btExport1,btPrint1,btPrint2,btPrint3,btDistribute,btRefundOk,btRefundNo,btRefundMd,btRefundCk" />
        <!-- ����CHECKBOX ����UNCHECKED״̬ -->
        <DIV id="op_select_browse" avriable="btClone,btAdd,btCheck,btSubmit,btDetailSubmit,btSubmitSam,btPrint,btExport,btExportBill,btBack,btBackEnd,btNext,btNextEnd,btQuery,btStatistic,btReportQuery,btImport,btImport1,btCheck1,btExport1,btPrint1,btPrint2,btPrint3,btDistribute,btRefundOk,btRefundNo,btRefundMd,btRefundCk" />
        <!-- ѡ��CHECKBOX -->
        <DIV id="op_select" avriable="btClone,btAdd,btDelete,btAudit,btModify,btCheck,btSubmit,btDetailSubmit,btSubmitSam,btPrint,btExport,btExportBill,btBack,btBackEnd,btNext,btNextEnd,btDistribute,btDownload,btQuery,btStatistic,btReportQuery,btImport,btImport1,btCheck1,btExport1,btPrint1,btDownload1,btPrint2,btPrint3,btDistribute,btRefundOk,btRefundNo,btRefundMd,btRefundCk" />
        <!-- ѡ������CHECKBOX -->
        <DIV id="op_select_all" avriable="btClone,btAdd,btDelete,btAudit,btCheck,btSubmit,btDetailSubmit,btSubmitSam,btPrint,btExport,btExportBill,btBack,btBackEnd,btNext,btNextEnd,btDownload,btQuery,btStatistic,btReportQuery,btImport,btImport1,btCheck1,btExport1,btPrint1,btDownload1,btPrint2,btPrint3,btDistribute,btRefundOk,btRefundNo,btRefundMd,btRefundCk" />


        <!-- �����ťʱ,���õ�������ť -->
        <!-- �����¡��ťʱ,���õ�������ť -->
        <DIV id="op_clone" avriable="btSave,btCancle,btPrint,btExport,btExportBill,btBack,btBackEnd,btNext,btNextEnd" />
        <!-- ����·���ťʱ,���õ�������ť -->
        <DIV id="op_download" avriable="btDownload,btPrint,btExport,btExportBill,btBack,btBackEnd,btNext,btNextEnd" />
        <DIV id="op_download1" avriable="btDownload1,btPrint,btExport,btExportBill,btBack,btBackEnd,btNext,btNextEnd" />
        <!-- ���ͳ�ư�ťʱ,���õ�������ť -->
        <DIV id="op_statistic" avriable="btStatistic,btPrint,btExport,btExportBill,btBack,btBackEnd,btNext,btNextEnd" />
        <!-- ��������ѯ��ťʱ,���õ�������ť -->
        <DIV id="op_reportQuery" avriable="btReportQuery,btPrint,btExport,btExportBill,btBack,btBackEnd,btNext,btNextEnd" />
        <!-- ������Ӱ�ťʱ,���õ�������ť -->
        <DIV id="op_add" avriable="btSave,btCancle,btSave1" />
        <!-- ���ɾ����ťʱ,���õ�������ť -->
        <DIV id="op_del" avriable="btClone,btAdd,btDelete,btAudit,btCheck,btSubmit,btDetailSubmit,btSubmitSam,btPrint,btExport,btExportBill,btBack,btBackEnd,btNext,btNextEnd" />
        <!-- �����˰�ťʱ,���õ�������ť -->
        <DIV id="op_audit" avriable="btClone,btAdd,btDelete,btAudit,btCheck,btSubmit,btDetailSubmit,btSubmitSam,btPrint,btExport,btExportBill,btBack,btBackEnd,btNext,btNextEnd" />
        <!-- ������밴ťʱ,���õ�������ť -->
        <DIV id="op_import" avriable="btClone,btAdd,btDelete,btCheck,btSubmit,btDetailSubmit,btSubmitSam,btPrint,btImport,btExport,btExportBill,btBack,btBackEnd,btNext,btNextEnd" />
        <DIV id="op_import1" avriable="btClone,btAdd,btDelete,btCheck,btSubmit,btDetailSubmit,btSubmitSam,btPrint,btImport1,btExport,btExportBill,btBack,btBackEnd,btNext,btNextEnd" />
        <!-- ����޸İ�ťʱ,���õ�������ť -->
        <DIV id="op_modify" avriable="btSave,btCancle,btSave1,updateId" />
        <DIV id="op_modify1" avriable="btSave,btCancle" />
        <!-- ���������ťʱ,���õ�������ť -->
        <DIV id="op_export1" avriable="btExport1,btPrint1,btExportBill,btBack,btBackEnd,btNext,btNextEnd" />
        <DIV id="op_print1" avriable="btExport1,btPrint1,btExportBill,btBack,btBackEnd,btNext,btNextEnd" />
        <DIV id="op_print2" avriable="btExport1,btExport,btPrint2,btExportBill,btBack,btBackEnd,btNext,btNextEnd" />
        <DIV id="op_print3" avriable="btExport1,btExport,btPrint3,btExportBill,btBack,btBackEnd,btNext,btNextEnd" />
        <!-- ������水ťʱ,���õ�������ť -->
        <DIV id="op_save" avriable="btClone,btAdd,btDelete,btAudit,btModify,btPrint,btExport,btExportBill,btBack,btBackEnd,btNext,btNextEnd" />
        <DIV id="op_save1" avriable="btClone,btAdd,btDelete,btAudit,btModify,btPrint,btExport,btExportBill,btBack,btBackEnd,btNext,btNextEnd" />

        <DIV id="op_check1" avriable="btCheck1" />

        <DIV id="op_version_draft" avriable="btAdd,btModify1,btCheck,btSubmit,btPrint,btExport,btExportBill,btQuery,btBack,btBackEnd,btNext,btNextEnd" />
        <DIV id="op_version_other" avriable="btClone,btQuery,btPrint,btExport,btExportBill,btBack,btBackEnd,btNext,btNextEnd" />
        <!-- ���ȷ���˿ťʱ,���õ�������ť -->
        <DIV id="op_refundOk" avriable="btRefundOk,btRefundNo,btRefundMd,btRefundCk" />
        <!-- ����ܾ��˿ťʱ,���õ�������ť -->
        <DIV id="op_refundNo" avriable="btRefundOk,btRefundNo,btRefundMd,btRefundCk" />
        <!-- ���ȷ���޸İ�ťʱ,���õ�������ť -->
        <DIV id="op_refundMd" avriable="btRefundOk,btRefundNo,btRefundMd,btRefundCk" />
        <!-- ���ȷ����˰�ťʱ,���õ�������ť -->
        <DIV id="op_refundCk" avriable="btRefundOk,btRefundNo,btRefundMd,btRefundCk" />

        <DIV id="op_null" avriable="" />

        <xsl:for-each select="/Service/Result/ModulePrilivedge">
                <DIV name="modulePriviledges" id="modulePriviledges" value="{concat(queryRight,'#',addRight,'#',deleteRight,'#',modifyRight,'#',cloneRight,'#',submitRight,'#',exportRight,'#',importRight,'#',printRight,'#',downloadRight,'#',rptQueryRight,'#',statisticRight,'#',checkRight,'#',distributeRight,'#',refundOkRight,'#',refundNoRight,'#',refundModifyRight,'#',refundCheckRight,'#',auditRight)}" />
        </xsl:for-each>
        <input type="hidden" name="ModuleID" id="ModuleID" value="{/Service/Result/ModuleID}" />

        <DIV id="controlDefaultValues" name="controlDefaultValues" value="{/Service/Result/ControlDefaultValues}" />
        <input type="hidden" name="queryControlDefaultValues" id="queryControlDefaultValues" value="" />
        <input type="hidden" name="precommand" id="precommand" value="{/Service/Result/Precommand}" />
        <input type="hidden" name="preTwoCommand" id="preTwoCommand" value="{/Service/Result/PreTwoCommand}" />
        <DIV  name="updatePKControlValues" id="updatePKControlValues" value="{/Service/Result/UpdatePKControlNames}" />
        <input type="hidden" name="ReportNamePrefix" id="ReportNamePrefix" value="" />
        <input type="hidden" name="ControlNames" id="ControlNames" value="" />
        <input type="hidden" name="_mainCardName" id="_mainCardID" value="{/Service/Result/QueryMainCardName}" />
        <input type="hidden" name="_subCardName" id="_subCardID" value="{/Service/Result/QuerySubCardName}" />
        <input type="hidden" name="_lineName" id="_lineID" value="{/Service/Result/QueryLineName}" />
        <input type="hidden" name="_stationName" id="_stationID" value="{/Service/Result/QueryStationName}" />
        <input type="hidden" name="_stationCommonVariable" id="_stationCommonVariable" value="{/Service/Result/StationCommonVariable}" />
        <input type="hidden" name="_subCardCommonVariable" id="_subCardCommonVariable" value="{/Service/Result/SubCardCommonVariable}" />
        <input type="hidden" name="_updatePKControlNames" id="_updatePKControlNames" value="" />

        <input type="hidden" name="reportParamValues" id="reportParamValues" value="" />

        <input type="hidden" name="_backEnd" id="_backEnd" value="{/Service/Result/PageVo/backEnd}" />
        <input type="hidden" name="_back" id="_back" value="{/Service/Result/PageVo/back}" />
        <input type="hidden" name="_nextEnd" id="_nextEnd" value="{/Service/Result/PageVo/nextEnd}" />
        <input type="hidden" name="_next" id="_next" value="{/Service/Result/PageVo/next}" />
        <input type="hidden" name="_current" id="_current" value="{/Service/Result/PageVo/current}" />
        <input type="hidden" name="_total" id="_total" value="{/Service/Result/PageVo/total}" />
        <input type="hidden" name="_divideShow" id="_divideShow" value="{$divideShow}" />

    </xsl:template>

</xsl:stylesheet>