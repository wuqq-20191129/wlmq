/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.fm.common;

import com.goldsign.fm.dao.DevMonitorDao;
import com.goldsign.fm.dao.TrafficNetDao;
import com.goldsign.fm.vo.DevCurrentStateVo;
import com.goldsign.fm.vo.DevMonitorNodeVo;
import com.goldsign.fm.vo.DevMonitorVo;
import com.goldsign.fm.vo.DevStatusImageVo;
import com.goldsign.fm.vo.TreeNode;
import com.goldsign.fm.vo.ViewVo;
import java.awt.Color;
import java.awt.Font;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 * 设备状态
 * @author Administrator
 */
public class DevUtil {

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DevUtil.class.getName());

    /**
     * 设备状态显示
     * @param treeNode 网点
     * @param jScrollPane1Dev 显示区
     * @param jLabelDataCaption 顶title
     */
    void processDev(TreeNode treeNode, JScrollPane jScrollPane1Dev, JLabel jLabelDataCaption) {
        //修改title文字
        addCaptionForDev(treeNode, jLabelDataCaption);
        jScrollPane1Dev.removeAll();
        //显示设备实时状态图片到原表格区
        if (treeNode.getNodeType().equals(AppConstant.NODE_TYPE_STATION)) {
            showDeviceStatus(treeNode, jScrollPane1Dev);
        }
    }

    
    /**
     * 修改title文字
     * @param treeNode
     * @param jLabelDataCaption
     */
    private void addCaptionForDev(TreeNode treeNode, JLabel jLabelDataCaption) {
        String captionData = this.getCaptionDataForDev(treeNode);
        jLabelDataCaption.setText(captionData);
    }

    private String getCaptionDataForDev(TreeNode treeNode) {
        if (treeNode.getNodeType().equals(AppConstant.NODE_TYPE_STATION)) {
            return "\n" + treeNode.getName() + "当前设备状态";
        }
        return "\n请选择左边网点车站";
    }

    
    /**
     * 显示设备实时状态图片到原表格区
     * @param treeNode
     * @param jScrollPane1Dev 
     */
    private void showDeviceStatus(TreeNode treeNode, JScrollPane jScrollPane1Dev) {
        
        //设备状态查询DAO
        DevMonitorDao devMonitorDao = new DevMonitorDao();
        //查询进出站人数DAO
        TrafficNetDao trafficNetDao = new TrafficNetDao();
        
        //线路
        String lineId = treeNode.getNodeId().split("_")[0];
        //车站
        String stationId = treeNode.getNodeId().split("_")[1];
        
        //分拣后结果集，用作最终显示
        Vector nodes = null;
        Vector states = null;
        Vector imagesUrls = null;
        //查询进出站人数ViewVo vo
        Vector traffics = null;
        
        try {
            nodes = devMonitorDao.getDeviceByLineStation(lineId, stationId);//车站对应所有图片
            states = devMonitorDao.getDeviceCurrentStatus(lineId, stationId);//实时状态
            imagesUrls = devMonitorDao.getDeviceStatusImage();//图片与状态对照结果集
            traffics = trafficNetDao.getLineStationTraffic(treeNode.getNodeId(), DateHelper.currentTodToFormatString());//查询进出站人数
        } catch (Exception ex) {
            logger.error(ex);
        }
        
        //分拣车站对应所有图片，根据当时状态，仅取实时状态的图片
        for(int i=0;i<nodes.size();i++){
            DevMonitorNodeVo node = new DevMonitorNodeVo();
            DevMonitorVo vo = (DevMonitorVo)nodes.get(i);
            node.setPosX(vo.getPosX());
            node.setPosY(vo.getPosY());
            JLabel l_pic = null;
            
            //设备状态（动态）
            if(vo.getNodeType().equals(DevConstant.NODE_TYPE_DYN_IMAGE)){
                boolean isShow = true;
                getNodeValueForDynImg(node,vo,states,imagesUrls,isShow);
                if(isShow){
                    addNodeImage(node,jScrollPane1Dev,l_pic);
                }
            }
            
            //设备状态（静态）
            if(vo.getNodeType().equals(DevConstant.NODE_TYPE_STATIC_IMAGE)){
                getNodeValueForStaticImg(node,vo);
                addNodeImage(node,jScrollPane1Dev,l_pic);
            }
            
            //文字
            if(vo.getNodeType().equals(DevConstant.NODE_TYPE_TEXT)){
                getNodeValueForText(node,vo);
                setTrafficText(traffics,node);//设置进出站人数
                l_pic = new JLabel(node.getText());
                Font font = new Font("Serif",Font.HANGING_BASELINE,Integer.valueOf(node.getFontSize())*4+1);
                l_pic.setFont(font);
                l_pic.setForeground(new Color(Integer.decode(node.getFontColor())));
                //取当前屏幕显示大小
                currentNode(node);
                l_pic.setBounds(node.getPosX(), node.getPosY(), 120, 15);
                jScrollPane1Dev.add(l_pic);
            }
            
            //线条
            if(vo.getNodeType().equals(DevConstant.NODE_TYPE_LINE)){
                getNodeValueForLine(node,vo);
                //取当前屏幕显示大小
                currentNode(node);
                addLineImage(node,vo,jScrollPane1Dev);
//                logger.info(node.getNodeType() + ":" + node.getPosX()+","+node.getPosY() 
//                        + ":" + vo.getStartX()+","+ vo.getStartY()
//                        + ":" + vo.getEndX()+","+ vo.getEndY()
//                        + ":" + node.getWidth()+"," + node.getHeight() + ":" + node.getSrc());
            }
            
        }
        jScrollPane1Dev.updateUI();
        
    }

    //设备状态（动态）
    private void getNodeValueForDynImg(DevMonitorNodeVo node, DevMonitorVo vo, Vector states, Vector imagesUrls,boolean isShow) {
        //更新实时状态到Vo实例
        //状态值
        updateVoState(vo,states,node,isShow);
        //图片路径
        updateVoImageUrl(vo,imagesUrls);
        node.setSrc(vo.getImageURL());
        node.setNodeType(DevConstant.NODE_TYPE_DYN_IMAGE);
    }
    
    //更新实时状态到Vo实例（状态值）
    private void updateVoState(DevMonitorVo vo, Vector states,DevMonitorNodeVo node,boolean isShow) {
        for (Iterator it = states.iterator(); it.hasNext();) {
            DevCurrentStateVo stateVo = (DevCurrentStateVo) it.next();
            if(stateVo.getLineId().equals(vo.getLineID()) &&
                    stateVo.getStationID().equals(vo.getStationID()) && 
                    stateVo.getDeviceID().equals(vo.getDeviceID()) && 
                    stateVo.getDeviceTypeId().equals(vo.getDeviceType())){
                vo.setState(stateVo.getAccStatusValue());
                node.setAlt(vo.getDeviceID() + ":" + stateVo.getStatusName());
                isShow = true;
            }
        }
    }
    
    //更新实时状态到Vo实例（图片路径）
    private void updateVoImageUrl(DevMonitorVo vo, Vector imagesUrls) {
        for (Iterator it = imagesUrls.iterator(); it.hasNext();) {
            DevStatusImageVo imageVo = (DevStatusImageVo) it.next();
            if(imageVo.getImageDirection().trim().equals(vo.getImageDirection().trim()) && 
                    imageVo.getStatus().trim().equals(vo.getState().trim()) && 
                    imageVo.getDeviceTypeID().trim().equals(vo.getDeviceType().trim())){
                vo.setImageURL(imageVo.getImagURL().trim());
                continue;
            }
        }
    }
    
    /**
     * 添加图片到panel(静态，动态)
     * @param node
     * @param jScrollPane1Dev
     * @param l_pic 
     */
    private void addNodeImage(DevMonitorNodeVo node, JScrollPane jScrollPane1Dev, JLabel l_pic) {
        ImageIcon im = new ImageIcon(AppConstant.appWorkDir + "/" + node.getSrc());
        //默认图片大小
        if(!AppConstant.IMAGE_SIZES_BUFFER.containsKey(getImageName(node.getSrc()))){
            node.setWidth(im.getIconWidth());
            node.setHeight(im.getIconHeight());
        }else{
            //取Properties文件图片大小
            String sizeWH = (String) AppConstant.IMAGE_SIZES_BUFFER.get(getImageName(node.getSrc()));
            node.setWidth(Integer.valueOf(sizeWH.split("x")[0]));
            node.setHeight(Integer.valueOf(sizeWH.split("x")[1]));
        }
        l_pic = new JLabel(im);
        l_pic.setToolTipText(node.getAlt());
        //取当前屏幕显示大小
        currentNode(node);
        l_pic.setBounds(node.getPosX(), node.getPosY(), node.getWidth(), node.getHeight());
        jScrollPane1Dev.add(l_pic);
    }

    //设备状态（静态）
    private void getNodeValueForStaticImg(DevMonitorNodeVo node, DevMonitorVo vo) {

        String imageURL = DevConstant.IMAGE_BASE_URL + vo.getImageURL();
        node.setNodeType(DevConstant.NODE_TYPE_STATIC_IMAGE);
        node.setSrc(imageURL);
        node.setText(vo.getNodeDescription());
        
//        if(this.isSwfImage(vo)){
//            node.setSrc(getSwfImgUrl(imageURL));
//            node.setImagFlag("swf");
//        }

    }

    //文字
    private void getNodeValueForText(DevMonitorNodeVo node, DevMonitorVo vo) {
        
        String fontSize = vo.getFontSize();
        String description =vo.getNodeDescription();
        
        node.setNodeType(DevConstant.NODE_TYPE_TEXT);
        node.setText(description);

        if(fontSize.length() ==0){
            fontSize ="2";
        }
        node.setFontSize(fontSize);
        node.setFontColor(DevConstant.NODE_TEXT_FONT_COLOR_WHITE);
    }

    //线条
    private void getNodeValueForLine(DevMonitorNodeVo node, DevMonitorVo vo) {
        int len = 0;
        int width = 0;
        int height =0;
        boolean isHorizontal = isHorizontalLine(vo.getStartX(),vo.getEndX());

        if(!isHorizontal){//竖线
            len = vo.getEndY() - vo.getStartY();
            len = Math.abs(len);
            width = DevConstant.LINE_THICK;
            height = len;

        }else{//水平线
            len = vo.getEndX() - vo.getStartX();
            len = Math.abs(len);
            height = DevConstant.LINE_THICK;
            width = len;
        }
        node.setWidth(width);
        node.setHeight(height);
        node.setPosX(vo.getStartX());
        node.setPosY(vo.getStartY());
        node.setSrc(DevConstant.IMAGE_URL_LINE);
        node.setNodeType(DevConstant.NODE_TYPE_LINE);
    }
    
    //SWF图片
    public boolean isSwfImage(DevMonitorVo vo){
        String imgUrl = vo.getImageURL();
        String temp = null;
        int index = -1;
        for(int i=0;i<DevConstant.imgUrls.length;i++){
          temp = DevConstant.imgUrls[i];
          index = imgUrl.indexOf(temp);
          if(index !=-1)
            return true;
        }
        return false;
    }
    
    public String getSwfImgUrl(String imgUrl){
        return imgUrl.indexOf(".")==-1 ? imgUrl : imgUrl.substring(0,imgUrl.indexOf("."))+".swf";
    }
    
    //取路径中图片名称
    public String getImageName(String path){
        int index = -1;
        index = path.lastIndexOf("/");
        if(index == -1){
          return "";
        }
        return path.substring(index+1);
    }
    
    /**
     * 是否为水平线
     * @param startx
     * @param endx
     * @return 
     */
    public boolean isHorizontalLine(int startx ,int endx){
        if(endx != startx){
          return true;
        } else{
          return false;
        }
    }

    /**
     * 组装线条
     * @param node
     * @param vo
     * @param jScrollPane1Dev 
     */
    private void addLineImage(DevMonitorNodeVo node, DevMonitorVo vo, JScrollPane jScrollPane1Dev) {
        ImageIcon im = new ImageIcon(AppConstant.appWorkDir + "/" + node.getSrc());
        boolean isHorizontal = isHorizontalLine(vo.getStartX(),vo.getEndX());
        int ih = im.getIconHeight();
        int iw = im.getIconWidth();
        int len = 0;
        int postX = node.getPosX();
        int postY = node.getPosY();
        if(isHorizontal){//水平线
            len = node.getWidth();
            addJLabelToPanel(im, postX, postY, len%iw, DevConstant.LINE_THICK, jScrollPane1Dev);
            postX = postX+len%iw;
            len = len - len%iw;
            for(int i=0;len>0;i++){
                addJLabelToPanel(im, postX, postY, iw, DevConstant.LINE_THICK, jScrollPane1Dev);
                len = len - iw;
                postX = postX+iw;
            }
        }else{//竖直线
            len = node.getHeight();
            addJLabelToPanel(im, postX, postY, DevConstant.LINE_THICK, len%ih, jScrollPane1Dev);
            postY = postY+len%ih;
            len = len - len%ih;
            for(int i=0;len>0;i++){
                addJLabelToPanel(im, postX, postY, DevConstant.LINE_THICK, ih, jScrollPane1Dev);
                len = len - ih;
                postY = postY+ih;
            }
        }
    }

    /**
     * 添加Label到Panel
     * @param im 图片
     * @param postX x坐标
     * @param postY y坐标
     * @param width 宽
     * @param height 长
     * @param jScrollPane1Dev 
     */
    private void addJLabelToPanel(ImageIcon im, int postX, int postY, int width, int height, JScrollPane jScrollPane1Dev) {
        JLabel l_picw = new JLabel(im);
        l_picw.setBounds(postX, postY, width, height);
        jScrollPane1Dev.add(l_picw);
    }

    /**
     * 设置进出站人数 
     * @param traffics
     * @param node 
     */
    private void setTrafficText(Vector traffics, DevMonitorNodeVo node) {
        ViewVo vvo = (ViewVo) traffics.get(0);
        if(node.getText().trim().indexOf(DevConstant.TRAFFIC_IN)>-1 && traffics.size()>0){
            node.setText(node.getText() + vvo.getTraffic_in());
        }
        if(node.getText().trim().indexOf(DevConstant.TRAFFIC_OUT)>-1 && traffics.size()>0){
            node.setText(node.getText() + vvo.getTraffic_out());
        }
    }
    
     /**
     * 取当前屏幕大小 高
     * @param height
     * @return 
     */
    public int currentH(int height){
        return (int)(AppConstant.BATE_HEIGHT*height);
    }
    
    /**
     * 取当前屏幕大小 宽
     * @param width
     * @return 
     */
    public int currentW(int width){
        return (int)(AppConstant.BATE_WIDTH*width);
    }
    
    //取当前屏幕下图片显示大小
    public void currentNode(DevMonitorNodeVo node){
        node.setPosX(currentW(node.getPosX()));
        node.setPosY(currentH(node.getPosY()));
        node.setHeight(currentH(node.getHeight()));
        node.setWidth(currentW(node.getWidth()));
    }

}
