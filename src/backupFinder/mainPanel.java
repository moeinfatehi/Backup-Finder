/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backupFinder;

import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author "Moein Fatehi moein.fatehi@gmail.com"
 */
public class mainPanel extends JPanel
{
    
    static BackupFinder backupFinderPanel;
    
    static int backupFinder_index=0;
    static int help_index=1;
    
    public static JTabbedPane firstLevelTabs;
    private JPanel mainPanel;
    private JPanel helpPanel;
    
    
    

    public mainPanel()
    {
        mainPanel = new JPanel(); //Creating the mainPanel JPanel
        backupFinderPanel=new BackupFinder();
        helpPanel=new HelpPanel();
        
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); //Setting Box layout, and set the direction to Y axis.
        firstLevelTabs = new JTabbedPane(); //Creating the additionalPanel JPanel     
        firstLevelTabs.add(backupFinderPanel,"Backup finder");
        firstLevelTabs.add(helpPanel,"Help");
        firstLevelTabs.setSelectedIndex(0);
        mainPanel.add(firstLevelTabs); //Adding panel2 into mainPanel
        this.setLayout(new GridLayout(1,1));
        this.add(mainPanel); //Setting mainPanel into JFrame

        this.setVisible(true); //Making JFrame Visible
    }
    static void resetBackupFinderPanel() {
        backupFinderPanel=new BackupFinder();
        firstLevelTabs.remove(backupFinder_index);
        firstLevelTabs.add(backupFinderPanel,"Finder",backupFinder_index);
    }

}
