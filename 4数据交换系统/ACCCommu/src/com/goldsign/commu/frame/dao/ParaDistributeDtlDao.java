package com.goldsign.commu.frame.dao;

import com.goldsign.commu.frame.constant.FrameDBConstant;
import com.goldsign.commu.frame.exception.ParameterException;
import com.goldsign.commu.frame.util.PubUtil;
import com.goldsign.commu.frame.vo.ParaDistributeDtl;
import com.goldsign.commu.frame.vo.ParaGenDtl;
import com.goldsign.commu.frame.vo.ParaInformDtl;
import com.goldsign.lib.db.util.DbHelper;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 * 
 * @author hejj
 */
public class ParaDistributeDtlDao {

	private static Logger logger = Logger.getLogger(ParaDistributeDtlDao.class
			.getName());

	public ParaDistributeDtlDao() throws Exception {
		super();
	}

	public ParaDistributeDtl getOneParaDistribute() {
		boolean result;
		ParaDistributeDtl paraDistributeDtl = null;
		int waterNo = -1;
		Vector<ParaInformDtl> paraInfoDtlV = null;
		Vector<ParaGenDtl> paraGenDtlV = null;
		StringBuilder sql = new StringBuilder();
		DbHelper dbHelper = null;
		sql.append("select water_no,distribute_datetime,operator_id,distribute_result,distribute_memo from "+FrameDBConstant.COM_ST_P+"op_prm_para_distribute_dtl where distribute_result='0'");
		sql.append(" and water_no=(select min(water_no) from "+FrameDBConstant.COM_ST_P+"op_prm_para_distribute_dtl where distribute_result='0')");
		try {
			dbHelper = new DbHelper("ParaDistributeDtlDao",
					FrameDBConstant.OP_DBCPHELPER.getConnection());
			if (!dbHelper.isAvailableForConn()) {
				return null;
			}

			result = dbHelper.getFirstDocument(sql.toString());
			if (result) {
				// logger.info("Access op_prm_para_distribute_dtl OK! ");
				paraDistributeDtl = new ParaDistributeDtl();
				waterNo = dbHelper.getItemIntValue("water_no");
				Object[] values = { new Integer(waterNo) };
				paraDistributeDtl.setWaterNo(waterNo);
				paraDistributeDtl.setDistributeDatetime(dbHelper
						.getItemDateValue("distribute_datetime"));
				paraDistributeDtl.setSysOperatorId(dbHelper
						.getItemValue("operator_id"));
				paraDistributeDtl.setDistributeResult(dbHelper
						.getItemValue("distribute_result"));
				paraDistributeDtl.setDistributeMemo(dbHelper
						.getItemValue("distribute_memo"));
				// access para_info_dtl
				String sqlStr = "select water_no,line_id,station_id,inform_result from "+FrameDBConstant.COM_ST_P+"op_prm_para_inform_dtl where water_no=?";
				result = dbHelper.getFirstDocument(sqlStr, values);
				if (result) {
					// logger.info("Access "+FrameDBConstant.TABLE_PREFIX+"op_prm_para_inform_dtl OK! ");
					paraInfoDtlV = new Vector<ParaInformDtl>();
				} else {
					throw new ParameterException(
							FrameDBConstant.TABLE_PREFIX+"op_prm_para_inform_dtl has no record! water_no:"
									+ waterNo);
				}
				while (result) {
					ParaInformDtl paraInfoDtl = new ParaInformDtl();
					paraInfoDtl.setWaterNo(waterNo);
					paraInfoDtl.setLineId(dbHelper.getItemValue("line_id"));
					paraInfoDtl.setStationId(dbHelper
							.getItemValue("station_id"));
					paraInfoDtl.setInformResult(dbHelper
							.getItemValue("inform_result"));
					paraInfoDtlV.add(paraInfoDtl);
					result = dbHelper.getNextDocument();
				}

				// access op_prm_para_gen_dtl
				sqlStr = "select water_no,parm_type_id,version_no,version_type,gen_result from "+FrameDBConstant.COM_ST_P+"op_prm_para_gen_dtl where water_no=?";
				result = dbHelper.getFirstDocument(sqlStr, values);
				if (result) {
					// logger.info("Access op_prm_para_gen_dtl OK! ");
					paraGenDtlV = new Vector<ParaGenDtl>();
				} else {
					throw new ParameterException(
							"op_prm_para_gen_dtl has no record!water_no:"
									+ waterNo);
				}
				while (result) {
					ParaGenDtl paraGenDtl = new ParaGenDtl();
					paraGenDtl.setWaterNo(waterNo);
					paraGenDtl.setParmTypeId(dbHelper
							.getItemValue("parm_type_id"));
					paraGenDtl.setVerNum(dbHelper.getItemValue("version_no"));
					paraGenDtl
							.setVerType(dbHelper.getItemValue("version_type"));
					paraGenDtl
							.setGenResult(dbHelper.getItemValue("gen_result"));
					paraGenDtlV.add(paraGenDtl);
					result = dbHelper.getNextDocument();
				}
				paraDistributeDtl.setParaInformDtl(paraInfoDtlV);
				paraDistributeDtl.setParaGenDtl(paraGenDtlV);
			}
		} catch (SQLException e) {
			logger.error("getOneParaDistribute error! ", e);
			if (waterNo != -1) {
				updateOneParaDistribute(waterNo, "9", "");
			}
			return null;
		} catch (ParameterException e) {
			logger.error("getOneParaDistribute", e);
			updateOneParaDistribute(waterNo, "9", e.getMessage());
			return null;
		} finally {
			if (null != dbHelper) {
				try {
					dbHelper.closeConnection();
				} catch (Exception e) {
					logger.error("getOneParaDistribute", e);
				}
			}

		}
		return paraDistributeDtl;
	}

	public boolean updateOneParaDistribute(int waterNo,
			String distributeResult, String distributeMemo) {
		boolean result = false;
		DbHelper dbHelper = null;
		String sqlStr = "update "+FrameDBConstant.COM_ST_P+"op_prm_para_distribute_dtl set distribute_result=?,distribute_memo=? where water_no=?";
		Object[] values = { distributeResult, distributeMemo,
				new Integer(waterNo) };
		try {
			dbHelper = new DbHelper("ParaDistributeDtlDao",
					FrameDBConstant.OP_DBCPHELPER.getConnection());
			dbHelper.executeUpdate(sqlStr, values);
			result = true;
		} catch (Exception e) {
			PubUtil.handleExceptionNoThrow(e, logger);

		} finally {
			PubUtil.finalProcess(dbHelper);
		}
		return result;
	}
}
