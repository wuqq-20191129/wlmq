/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.fm.listener;


import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;


import com.goldsign.fm.common.AppUtil;

import javax.swing.JLabel;


import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import org.apache.log4j.Logger;


/**
 *
 * @author Administrator
 */
public class TreeNetSelectListener implements TreeSelectionListener {

    private JTree tree = null;
    private JPanel jPanelGraph;
    private JScrollPane jScrollPane1Data;
    private JScrollPane jScrollPane1Dev;
    private JLabel jLabelDataCaption;
    private JLabel jLabelGraphCaption;
    private Logger logger = Logger.getLogger(TreeNetSelectListener.class.getName());

    public TreeNetSelectListener(JTree tree, JPanel jPanelGraph, JScrollPane jScrollPane1Data, JLabel jLabelDataCaption, 
            JLabel jLabelGraphCaption, JScrollPane jScrollPane1Dev) {
        this.tree = tree;
        this.jPanelGraph = jPanelGraph;
        this.jScrollPane1Data = jScrollPane1Data;
        this.jLabelDataCaption = jLabelDataCaption;
        this.jLabelGraphCaption = jLabelGraphCaption;
        this.jScrollPane1Dev = jScrollPane1Dev;

    }
  
    

    public void valueChanged(TreeSelectionEvent e) {
        //this.tree.setSelectionPath(e.getNewLeadSelectionPath());
        AppUtil util = new AppUtil();


        //PromptDialog dialog=new PromptDialog(null,true);
        //PromptThread t = new PromptThread(dialog);
        //t.start();
        /*
        PubUtil putil = new PubUtil();
        putil.makeContainerInScreenMiddle(dialog);
        dialog.setVisible(true);
       */
        try {
            //显示小时客流或分钟客流
            util.processFlow(tree, jPanelGraph, jScrollPane1Data, jLabelDataCaption, jLabelGraphCaption, jScrollPane1Dev);
           


        } catch (Exception ex) {
           logger.error(e);
        }
        /*
        finally{
            dialog.dispose();
        }
*/

    }
}
