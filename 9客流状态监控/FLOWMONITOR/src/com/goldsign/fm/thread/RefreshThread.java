/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.fm.thread;

import com.goldsign.fm.common.AppConstant;
import com.goldsign.fm.common.AppUtil;
import com.goldsign.fm.vo.TreeNode;
import java.util.logging.Level;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class RefreshThread extends Thread {
    private static Logger logger =Logger.getLogger(RefreshThread.class.getName());

    private JTree tree;
    private JPanel jPanelGraph;
    private JScrollPane jScrollPane1Data;
    private JScrollPane jScrollPane1Dev;
    private JLabel jLabelDataCaption;
    private JLabel jLabelGraphCaption;

    public RefreshThread(JTree tree, JPanel jPanelGraph, JScrollPane jScrollPane1Data, JLabel jLabelDataCaption, JLabel jLabelGraphCaption,
            JScrollPane jScrollPane1Dev) {
        this.tree = tree;
         this.jPanelGraph = jPanelGraph;
        this.jScrollPane1Data = jScrollPane1Data;
        this.jLabelDataCaption = jLabelDataCaption;
        this.jLabelGraphCaption = jLabelGraphCaption;
        this.jScrollPane1Dev = jScrollPane1Dev;
    }

    public void run() {
        TreePath path;
        DefaultMutableTreeNode node;
        AppUtil util = new AppUtil();
        MoveDataThread moveDataThread = new MoveDataThread();
        long freshTime = 0;//线程刷新频率(毫秒)
        while (true) {
            try {
                try {
                    freshTime = moveDataThread.getThreadIntervalRefresh();
                } catch (Exception ex) {
                    java.util.logging.Logger.getLogger(RefreshThread.class.getName()).log(Level.SEVERE, null, ex);
                }
                RefreshThread.sleep(freshTime);//(AppConstant.TIME_INTERVAL_REFRESH);
                node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (node == null) {
                    continue;
                }
                path = tree.getSelectionPath();

                DefaultMutableTreeNode n =(DefaultMutableTreeNode)path.getLastPathComponent();
                TreeNode tn =(TreeNode) n.getUserObject();
                logger.info("刷新节点："+tn.getName());


                util.processFlow(tree, jPanelGraph, jScrollPane1Data, jLabelDataCaption, jLabelGraphCaption, jScrollPane1Dev);

            } catch (InterruptedException ex) {
            }
        }
    }
}
