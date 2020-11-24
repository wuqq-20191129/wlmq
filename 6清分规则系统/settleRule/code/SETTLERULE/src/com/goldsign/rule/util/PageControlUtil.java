package com.goldsign.rule.util;


import com.goldsign.frame.constant.FrameCodeConstant;
import com.goldsign.frame.struts.BaseAction;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.rule.vo.PageVo;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMessage;

public class PageControlUtil {

    public static String NAME_BUFFER = "resultBuffer";
    public static String NAME_RESULT = "result";
    public static String NAME_NEXT = "nextVo";
    public static String COMMAND_NEXT = "next";
    public static String COMMAND_NEXTEND = "nextEnd";
    public static String COMMAND_BACK = "back";
    public static String COMMAND_BACKEND = "backEnd";
    public static String COMMAND_QUERY = "query";
    public static String COMMAND_DELETE = "delete";
    public static String COMMAND_ADD = "add";
    public static String COMMAND_MODIFY = "modify";
    public static String COMMAND_CHECK1 = "check1";

    public PageControlUtil() {
        super();
        // TODO Auto-generated constructor stub
    }

    public boolean isNeedDivide(HttpServletRequest request) {
        String flag = request.getParameter("_divideShow");
        if (flag == null || flag.length() == 0) {
            return false;
        }
        if (flag.equals("1")) {
            return true;
        }
        return false;
    }

    public Vector seperateResults(HttpServletRequest request, BaseAction action, Vector results) {
        //先将结果拆分后放入会话
        String command = request.getParameter("command");
        if (command != null && (command.equals(PageControlUtil.COMMAND_QUERY)
                || command.equals(PageControlUtil.COMMAND_DELETE)
                || command.equals(PageControlUtil.COMMAND_ADD)
                || command.equals(PageControlUtil.COMMAND_MODIFY)
                || command.equals(PageControlUtil.COMMAND_CHECK1))) {
            this.putBuffer(request, results);
        }
        int n = this.getBufferIndex(request);
        //取出拆分结果
        Vector v = this.getBufferElement(request, n);
        //返回下一页面控制结果
        PageVo nextVo = this.getNextPageControl(request);
        this.setNextVoToSession(request, nextVo);
        this.saveResultForPage(request, action, nextVo);
        return v;
    }

    public void putBuffer(HttpServletRequest request, Vector results) {
        HashMap buffer = this.divideResults(results);
        Vector v = new Vector();
        v.addAll(results);
        request.getSession().setAttribute(NAME_BUFFER, buffer);
        request.getSession().setAttribute(NAME_RESULT, v);
    }

    private HashMap divideResults(Vector results) {
        HashMap buffer = new HashMap();
        if (results == null || results.isEmpty()) {
            buffer.put(new Integer(1), results);
            return buffer;
        }
        int size = results.size();
        if (size <= FrameCodeConstant.MAX_PAGE_NUMBER) {
            buffer.put(new Integer(1), results);
            return buffer;

        }

        Vector v = new Vector();
        int k = 1;
        for (int i = 0; i < size; i++) {
            v.add(results.get(i));
            if (this.isFull(i, FrameCodeConstant.MAX_PAGE_NUMBER, size)) {
                buffer.put(new Integer(k), v);
                k++;
                v = new Vector();
            }
        }
        return buffer;
    }

    private boolean isFull(int i, int max, int size) {
        if ((i + 1) % max == 0 || i == size - 1) {
            return true;
        }
        return false;
    }

    private int getBufferIndex(HttpServletRequest request) {

        String command = request.getParameter("command");
        if (command == null || command.length() == 0
                || command.equals(PageControlUtil.COMMAND_QUERY)
                || command.equals(PageControlUtil.COMMAND_DELETE)
                || command.equals(PageControlUtil.COMMAND_CHECK1)) {
            return 1;
        }
        PageVo vo = this.getPageControl(request);
        if (command.equals(PageControlUtil.COMMAND_ADD) || command.equals(PageControlUtil.COMMAND_MODIFY)) {
            return 1;
        }

        if (command.equals(PageControlUtil.COMMAND_BACK)) {
            return NumberUtil.getIntegerValue(vo.getBack(), 1).intValue();
        }
        if (command.equals(PageControlUtil.COMMAND_BACKEND)) {
            return NumberUtil.getIntegerValue(vo.getBackEnd(), 1).intValue();
        };
        if (command.equals(PageControlUtil.COMMAND_NEXT)) {
            return NumberUtil.getIntegerValue(vo.getNext(), 1).intValue();
        };
        if (command.equals(PageControlUtil.COMMAND_NEXTEND)) {
            return NumberUtil.getIntegerValue(vo.getNextEnd(), 1).intValue();
        };
        return 1;

    }

    private PageVo getPageControl(HttpServletRequest request) {
        int back = NumberUtil.getIntegerValue(request.getParameter("_back"), 0).intValue();
        int backEnd = NumberUtil.getIntegerValue(request.getParameter("_backEnd"), 0).intValue();
        int next = NumberUtil.getIntegerValue(request.getParameter("_next"), 0).intValue();
        int nextEnd = NumberUtil.getIntegerValue(request.getParameter("_nextEnd"), 0).intValue();
        PageVo vo = new PageVo();
        vo.setBack(new Integer(back).toString());
        vo.setBackEnd(new Integer(backEnd).toString());
        vo.setNext(new Integer(next).toString());
        vo.setNextEnd(new Integer(nextEnd).toString());
        return vo;
    }

    public Vector getBufferElement(HttpServletRequest request, int n) {
        HashMap buffer = (HashMap) request.getSession().getAttribute(NAME_BUFFER);
        Integer key = new Integer(n);
        if (buffer != null && buffer.containsKey(key)) {
            return (Vector) buffer.get(key);
        }
        return new Vector();
    }

    private PageVo getNextPageControl(HttpServletRequest request) {
        PageVo vo = this.getPageControl(request);
        String command = request.getParameter("command");

        HashMap buffer = (HashMap) request.getSession().getAttribute(PageControlUtil.NAME_BUFFER);
        Vector results = (Vector) request.getSession().getAttribute(PageControlUtil.NAME_RESULT);
        Vector curResults;
        vo.setTotal(new Integer(buffer.size()).toString());
        vo.setTotalRecords(new Integer(results.size()).toString());


        if (command.equals(PageControlUtil.COMMAND_QUERY) || command.equals(PageControlUtil.COMMAND_DELETE) || command.equals(PageControlUtil.COMMAND_CHECK1)) {
            vo.setBackEnd("1");
            vo.setNextEnd(new Integer(buffer.size()).toString());
            vo.setBack("0");
            vo.setNext("2");
            vo.setCurrent("1");

            curResults = (Vector) buffer.get(new Integer(1));
            vo.setCurrentRecords(new Integer(curResults.size()).toString());

            return vo;
        }
        if (command.equals(PageControlUtil.COMMAND_BACK)) {
            vo.setCurrent(vo.getBack());
            curResults = (Vector) buffer.get(new Integer(vo.getCurrent()));
            vo.setCurrentRecords(new Integer(curResults.size()).toString());

            vo.setBack(NumberUtil.add(vo.getBack(), -1));
            vo.setNext(NumberUtil.add(vo.getNext(), -1));

            return vo;
        }
        if (command.equals(PageControlUtil.COMMAND_NEXT)) {

            vo.setCurrent(vo.getNext());
            curResults = (Vector) buffer.get(new Integer(vo.getCurrent()));
            vo.setCurrentRecords(new Integer(curResults.size()).toString());

            vo.setBack(NumberUtil.add(vo.getBack(), 1));

            vo.setNext(NumberUtil.add(vo.getNext(), 1));

            return vo;
        }
        if (command.equals(PageControlUtil.COMMAND_BACKEND)) {
            vo.setCurrent(vo.getBackEnd());
            curResults = (Vector) buffer.get(new Integer(vo.getCurrent()));
            vo.setCurrentRecords(new Integer(curResults.size()).toString());

            vo.setBack(NumberUtil.add(vo.getBackEnd(), -1));
            vo.setNext(NumberUtil.add(vo.getBackEnd(), 1));

            return vo;
        }
        if (command.equals(PageControlUtil.COMMAND_NEXTEND)) {
            vo.setCurrent(vo.getNextEnd());
            curResults = (Vector) buffer.get(new Integer(vo.getCurrent()));
            vo.setCurrentRecords(new Integer(curResults.size()).toString());

            vo.setBack(NumberUtil.add(vo.getNextEnd(), -1));
            vo.setNext(NumberUtil.add(vo.getNextEnd(), 1));

            return vo;
        }
        if (command.equals(PageControlUtil.COMMAND_ADD) || command.equals(PageControlUtil.COMMAND_MODIFY)) {
            vo.setBackEnd("1");
            vo.setNextEnd(new Integer(buffer.size()).toString());
            vo.setBack("0");
            vo.setNext("2");
            vo.setCurrent("1");

            curResults = (Vector) buffer.get(new Integer(1));
            vo.setCurrentRecords(new Integer(curResults.size()).toString());

            return vo;
        }
        return vo;
    }

    private void setNextVoToSession(HttpServletRequest request, PageVo nextVo) {
        request.getSession().setAttribute(PageControlUtil.NAME_NEXT, nextVo);
    }

    private void saveResultForPage(HttpServletRequest request, BaseAction action, PageVo vo) {
        if (vo != null) {
            action.saveActionResult(request, "PageVo", vo);
        }
    }

    public boolean isPageControl(HttpServletRequest request) {
        String command = request.getParameter("command");
        if (command == null || command.length() == 0) {
            return false;
        }
        if (command.equals(PageControlUtil.COMMAND_QUERY) || command.equals(PageControlUtil.COMMAND_BACK)
                || command.equals(PageControlUtil.COMMAND_BACKEND) || command.equals(PageControlUtil.COMMAND_NEXT)
                || command.equals(PageControlUtil.COMMAND_NEXTEND) || command.equals(PageControlUtil.COMMAND_CHECK1)) {
            return true;
        }
        return false;
    }

    public ActionMessage getMessage(HttpServletRequest request) {
        PageVo vo = (PageVo) request.getSession().getAttribute(PageControlUtil.NAME_NEXT);
        String msg = "成功获取" + vo.getTotalRecords() + "条记录，共" + vo.getTotal() + "页，第" + vo.getCurrent() + "页" + ",记录数" + vo.getCurrentRecords() + "条";
        ActionMessage am = new ActionMessage("queryMessage", FrameUtil.GbkToIso(msg));
        return am;
    }

    public ActionMessage getMessageForAdd(HttpServletRequest request) {
        PageVo vo = (PageVo) request.getSession().getAttribute(PageControlUtil.NAME_NEXT);
        String msg = "当前共" + vo.getTotalRecords() + "条记录，共" + vo.getTotal() + "页，第" + vo.getCurrent() + "页" + ",记录数" + vo.getCurrentRecords() + "条";
        ActionMessage am = new ActionMessage("queryMessage", FrameUtil.GbkToIso(msg));
        return am;


    }
}
