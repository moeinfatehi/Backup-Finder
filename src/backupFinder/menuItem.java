/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package backupFinder;

import burp.BurpExtender;
import burp.IContextMenuFactory;
import burp.IContextMenuInvocation;
import burp.IHttpRequestResponse;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JMenuItem;

/**
 *
 * @author fatehi
 */
public class menuItem implements IContextMenuFactory{
    @Override
    public List<JMenuItem> createMenuItems(IContextMenuInvocation invocation) {
        final IHttpRequestResponse responses[] = invocation.getSelectedMessages();
        
        if(responses.length > 0){
            List<JMenuItem> ret = new LinkedList<JMenuItem>();
            final String active_BackupFinder_item="Send to Backup Finder";
            JMenuItem menuItem = new JMenuItem(active_BackupFinder_item);
            
            menuItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0) {
                    //Backup Finder tab
                    if(arg0.getActionCommand().equals(active_BackupFinder_item)){
                        mainPanel.resetBackupFinderPanel();
                        BackupFinder.setBaseReqResp(responses[0]);
                        URL url=BurpExtender.callbacks.getHelpers().analyzeRequest(responses[0]).getUrl();
                        mainPanel.firstLevelTabs.setSelectedIndex(mainPanel.backupFinder_index);
                    }
                }
            });
            ret.add(menuItem);
            return(ret);
        }
        return null;
    }
    
}
