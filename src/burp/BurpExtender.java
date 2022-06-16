package burp;

import java.io.PrintWriter;
import javax.swing.*;
import backupFinder.menuItem;
import backupFinder.tab;

public class BurpExtender extends JPanel implements IBurpExtender
{
    
    public static IBurpExtenderCallbacks callbacks;
    static JScrollPane frame;
    public static PrintWriter output;
    public static String project_Name="backupFinder";
    private static final String project_Version="0.2";
    private static String project_git="https://github.com/moeinfatehi/Backup-Finder";
    
    public BurpExtender() {
//        this.historyModel = (DefaultTableModel)mainPanel.historyTable.getModel();
    }
    
    @Override
    public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks)
    {
        // keep a reference to our callbacks object
        this.callbacks = callbacks;
        output = new PrintWriter(callbacks.getStdout(), true);
        // create our UI
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                initComponents();
                // customize our UI components
                callbacks.customizeUiComponent(tab.panel);
                
                // add the custom tab to Burp's UI
                callbacks.addSuiteTab(tab.tb);
                callbacks.registerContextMenuFactory(new menuItem());
                
            }
        });
    }
    
    private void initComponents() {
        output.println("Project Git URL: "+project_git);
    }// </editor-fold>
    
    public byte[] processProxyMessage(int messageReference, boolean messageIsRequest, String remoteHost, int remotePort, boolean serviceIsHttps, String httpMethod, String url,
        String resourceType, String statusCode, String responseContentType, byte message[], int action[])
    {
        return message;
    }
    
    public static String getProjectName(){
        return project_Name;
    }
    public static String getVersion(){
        return project_Version;
    }
    
}