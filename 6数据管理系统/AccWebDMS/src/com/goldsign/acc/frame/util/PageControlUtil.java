/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.util;

import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.ConfigConstant;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.acc.frame.vo.PageVo;
import com.goldsign.login.vo.User;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hejj
 */
public class PageControlUtil {

    public static String NAME_BUFFER = "resultBuffer";
    public static String NAME_RESULT = "result";
    public static String NAME_NEXT = "nextVo";

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

    public void getMessage(HttpServletRequest request, OperationResult opResult) {
        PageVo vo = (PageVo) request.getSession().getAttribute(PageControlUtil.NAME_NEXT);
//        String msg = "成功获取" + vo.getTotalRecords() + "条记录，共" + vo.getTotal() + "页，第" + vo.getCurrent() + "页" + ",记录数" + vo.getCurrentRecords() + "条";
        String msg = "成功获取" + vo.getTotalRecords() + "条记录，当前第" + vo.getCurrent() + "页" + "，记录数" + vo.getCurrentRecords() + "条，共" + vo.getTotal() + "页";

        opResult.setMessage(msg);

    }

    public void getMessageForAdd(HttpServletRequest request, OperationResult opResult) {
        PageVo vo = (PageVo) request.getSession().getAttribute(PageControlUtil.NAME_NEXT);
//        String msg = "当前共" + vo.getTotalRecords() + "条记录，共" + vo.getTotal() + "页，第" + vo.getCurrent() + "页" + ",记录数" + vo.getCurrentRecords() + "条";
        String msg = "共" + vo.getTotalRecords() + "条记录，当前第" + vo.getCurrent() + "页" + "，记录数" + vo.getCurrentRecords() + "条，共" + vo.getTotal() + "页";
        opResult.addMessage(msg);

    }

    public boolean isPageControl(HttpServletRequest request) {
        String command = request.getParameter("command");
        if (command == null || command.length() == 0) {
            return false;
        }
        if (command.equals(CommandConstant.COMMAND_QUERY) || command.equals(CommandConstant.COMMAND_BACK)
                || command.equals(CommandConstant.COMMAND_BACKEND) || command.equals(CommandConstant.COMMAND_NEXT)
                || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_CHECK1)
                || command.equals(CommandConstant.COMMAND_GOPAGE)) {
            return true;
        }
        return false;
    }

    public void seperateResults(HttpServletRequest request, ModelAndView mv, OperationResult opResult) {
        //先将结果拆分后放入会话
        String command = request.getParameter("command");
        List results = opResult.getReturnResultSet();
        if (command != null && (command.equals(CommandConstant.COMMAND_QUERY)
                || command.equals(CommandConstant.COMMAND_DELETE)
                || command.equals(CommandConstant.COMMAND_ADD)
                || command.equals(CommandConstant.COMMAND_MODIFY)
                || command.equals(CommandConstant.COMMAND_CHECK1)
                || command.equals(CommandConstant.COMMAND_CLONE)
                || command.equals(CommandConstant.COMMAND_SUBMIT))) {
            this.putBuffer(request, results);
        }
        int n = this.getBufferIndex(request);
        //取出拆分结果
        Vector v = this.getBufferElement(request, n);
        opResult.setReturnResultSet(v);
        //返回下一页面控制结果
        PageVo nextVo = this.getNextPageControl(request);
        this.setNextVoToSession(request, nextVo);
        this.saveResultForPage(request, mv, nextVo);

    }

    private void setNextVoToSession(HttpServletRequest request, PageVo nextVo) {
        request.getSession().setAttribute(PageControlUtil.NAME_NEXT, nextVo);

    }

    private int getBufferIndex(HttpServletRequest request) {

        String command = request.getParameter("command");
        if (command == null || command.length() == 0
                || command.equals(CommandConstant.COMMAND_QUERY)
                || command.equals(CommandConstant.COMMAND_DELETE)
                || command.equals(CommandConstant.COMMAND_CHECK1)) {
            return 1;
        }
        PageVo vo = this.getPageControl(request);
        if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_MODIFY)) {
            return 1;
        }

        if (command.equals(CommandConstant.COMMAND_BACK)) {
            return NumberUtil.getIntegerValue(vo.getBack(), 1).intValue();
        }
        if (command.equals(CommandConstant.COMMAND_BACKEND)) {
            return NumberUtil.getIntegerValue(vo.getBackEnd(), 1).intValue();
        };
        if (command.equals(CommandConstant.COMMAND_NEXT)) {
            return NumberUtil.getIntegerValue(vo.getNext(), 1).intValue();
        };
        if (command.equals(CommandConstant.COMMAND_NEXTEND)) {
            return NumberUtil.getIntegerValue(vo.getNextEnd(), 1).intValue();
        };
        if (command.equals(CommandConstant.COMMAND_GOPAGE)) {  //跳转             
            String goPageNos = request.getParameter("goPageNos");
            if (Integer.parseInt(goPageNos) > Integer.parseInt(vo.getNextEnd())) {
                return NumberUtil.getIntegerValue(vo.getNextEnd(), 1).intValue();
            } else {
                return NumberUtil.getIntegerValue(goPageNos, 1).intValue();
            }
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

    private PageVo getNextPageControl(HttpServletRequest request) {
        PageVo vo = this.getPageControl(request);
        String command = request.getParameter("command");

        HashMap buffer = (HashMap) request.getSession().getAttribute(PageControlUtil.NAME_BUFFER);
        Vector results = (Vector) request.getSession().getAttribute(PageControlUtil.NAME_RESULT);
        Vector curResults;
        vo.setTotal(new Integer(buffer.size()).toString());
        vo.setTotalRecords(new Integer(results.size()).toString());

        if (command.equals(CommandConstant.COMMAND_QUERY) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_CHECK1)) {
            vo.setBackEnd("1");
            vo.setNextEnd(new Integer(buffer.size()).toString());
            vo.setBack("0");
            vo.setNext("2");
            vo.setCurrent("1");

            curResults = (Vector) buffer.get(new Integer(1));
            vo.setCurrentRecords(new Integer(curResults.size()).toString());

            return vo;
        }
        if (command.equals(CommandConstant.COMMAND_BACK)) {
            vo.setCurrent(vo.getBack());
            curResults = (Vector) buffer.get(new Integer(vo.getCurrent()));
            vo.setCurrentRecords(new Integer(curResults.size()).toString());

            vo.setBack(NumberUtil.add(vo.getBack(), -1));
            vo.setNext(NumberUtil.add(vo.getNext(), -1));

            return vo;
        }
        if (command.equals(CommandConstant.COMMAND_NEXT)) {

            vo.setCurrent(vo.getNext());
            curResults = (Vector) buffer.get(new Integer(vo.getCurrent()));
            vo.setCurrentRecords(new Integer(curResults.size()).toString());

            vo.setBack(NumberUtil.add(vo.getBack(), 1));

            vo.setNext(NumberUtil.add(vo.getNext(), 1));

            return vo;
        }
        if (command.equals(CommandConstant.COMMAND_BACKEND)) {
            vo.setCurrent(vo.getBackEnd());
            curResults = (Vector) buffer.get(new Integer(vo.getCurrent()));
            vo.setCurrentRecords(new Integer(curResults.size()).toString());

            vo.setBack(NumberUtil.add(vo.getBackEnd(), -1));
            vo.setNext(NumberUtil.add(vo.getBackEnd(), 1));

            return vo;
        }
        if (command.equals(CommandConstant.COMMAND_NEXTEND)) {
            vo.setCurrent(vo.getNextEnd());
            curResults = (Vector) buffer.get(new Integer(vo.getCurrent()));
            vo.setCurrentRecords(new Integer(curResults.size()).toString());

            vo.setBack(NumberUtil.add(vo.getNextEnd(), -1));
            vo.setNext(NumberUtil.add(vo.getNextEnd(), 1));

            return vo;
        }
        //跳转               
        if (command.equals(CommandConstant.COMMAND_GOPAGE)) {
            String goPageNos = request.getParameter("goPageNos");
            //回显 
            request.setAttribute("goPageNos", goPageNos);
            String nextEnd = vo.getNextEnd();
            //如果跳转页码大于最后一页，就显示最后一页
            if (Integer.parseInt(goPageNos) > Integer.parseInt(nextEnd)) {
                vo.setCurrent(vo.getNextEnd());
            } else {
                vo.setCurrent(goPageNos);
            }

            curResults = (Vector) buffer.get(new Integer(vo.getCurrent()));
            vo.setCurrentRecords(new Integer(curResults.size()).toString());

            vo.setBack(NumberUtil.add(vo.getCurrent(), -1));
            vo.setNext(NumberUtil.add(vo.getCurrent(), 1));

            return vo;
        }
        if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_MODIFY)) {
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

    private void saveResultForPage(HttpServletRequest request, ModelAndView mv, PageVo vo) {

        if (vo != null) {
            mv.addObject("PageVo", vo);

        }

    }

    public void putBuffer(HttpServletRequest request, List results) {
        HashMap buffer = this.divideResults(results);
        Vector v = new Vector();
        v.addAll(results);
        request.getSession().setAttribute(NAME_BUFFER, buffer);
        request.getSession().setAttribute(NAME_RESULT, v);

    }

    public Vector getBufferElement(HttpServletRequest request, int n) {
        HashMap buffer = (HashMap) request.getSession().getAttribute(NAME_BUFFER);
        Integer key = new Integer(n);
        if (buffer != null && buffer.containsKey(key)) {
            return (Vector) buffer.get(key);
        }
        return new Vector();
    }

    private HashMap divideResults(List results) {
        HashMap buffer = new HashMap();
        Vector vResults = new Vector();
        if (results == null || results.isEmpty()) {
            buffer.put(new Integer(1), new Vector());
            return buffer;
        }
        int size = results.size();
        if (size <= ConfigConstant.MAX_PAGE_NUMBER) {
            vResults.addAll(results);
            buffer.put(new Integer(1), vResults);
            return buffer;

        }

        Vector v = new Vector();
        int k = 1;
        for (int i = 0; i < size; i++) {
            v.add(results.get(i));
            if (this.isFull(i, ConfigConstant.MAX_PAGE_NUMBER, size)) {
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

    public static String getOperatorFromSession(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("User");
        String operatorID = user.getAccount();
        return operatorID;
    }
}
