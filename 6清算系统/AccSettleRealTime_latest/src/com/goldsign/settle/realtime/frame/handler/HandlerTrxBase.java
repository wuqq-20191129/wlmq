/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.handler;

import com.goldsign.settle.realtime.frame.constant.FrameCheckConstant;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameFileHandledConstant;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.vo.CheckControlVo;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class HandlerTrxBase extends HandlerBase {

    @Override
    public void handleMessage(MessageBase msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected boolean isNeedCheckTac(String trdType) {
        // if(!this.isCardForMetro(cardMainId))
        //   return false;

        for (String trdTypeCheck : FrameCodeConstant.TRX_TYPES_TAC_CHECK) {
            if (trdType.equals(trdTypeCheck)) {
                return true;
            }
        }
        return false;

    }

    protected boolean isNeedCheckTac() {
        if (FrameCheckConstant.CHECK_CONTROLS == null || FrameCheckConstant.CHECK_CONTROLS.isEmpty()) {
            return false;
        }
        String keyTac = FrameFileHandledConstant.RECORD_ERR_TAC[0] + FrameCheckConstant.CHECK_FLAG_YES;
        String key = "";
        for (CheckControlVo vo : FrameCheckConstant.CHECK_CONTROLS) {
            key = vo.getChkId() + vo.getValidFlag();
            if (keyTac.equals(key)) {
                return true;
            }
        }
        return false;
    }

    protected Vector<FileRecordBase> getDatasForTac(Vector<FileRecordBase> datas) {
        Vector<FileRecordBase> datasTac = new Vector();
        for (FileRecordBase frb : datas) {
            if (!this.isCardForMetro(frb.getCardMainId())) {
                continue;
            }
            if (this.isNonReturn(frb)) {//非即时退款退款记录不参与TAC校验 20151223modified by hejj 
                continue;
            }
            datasTac.add(frb);
        }


        return datasTac;
    }

    private boolean isNonReturn(FileRecordBase frb) {
        return frb.isNonReturn();
    }

    private boolean isCardForMetro(String cardMainId) {
        for (String tmp : FrameCodeConstant.CARD_MAIN_TYPE_METRO) {
            if (tmp.equals(cardMainId)) {
                return true;
            }
        }
        return false;
    }
}
