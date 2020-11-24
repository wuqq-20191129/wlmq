package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.OpLogImport;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import java.util.List;
import java.util.Map;

/**
 * 导入日志表
 *
 * @author xiaowu
 * @version 20170621
 */
public interface OpLogImportMapper {

    public List<OpLogImport> getOpLogImports(OpLogImport opLogImport);

    public List<OpLogImport> getOpLogImportsLikeFileName(OpLogImport opLogImport);

    public int addOpLogImport(OpLogImport opLogImport);

    public int addOpLogImportForDealAssign(OpLogImport opLogImport);

    public int modifyOpLogImport(OpLogImport opLogImport);

    public List<OpLogImport> getOpLogImportById(OpLogImport opLogImport);

    public int deleteOpLogImport(OpLogImport opLogImport);

    public int submitToOldFlag(OpLogImport opLogImport);

    public int submitFromDraftToCurOrFur(OpLogImport opLogImport);

    public int deleteOpLogImportForClone(OpLogImport opLogImport);

    public List<Map> queryToMap(OpLogImport queryCondition);

    public int deleteOpLog(OpLogImport opLogImport);
}
