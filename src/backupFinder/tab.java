/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package backupFinder;

import burp.ITab;
import java.awt.Component;

/**
 *
 * @author fatehi
 */
public class tab implements ITab{
    public static mainPanel panel=new mainPanel();
    public static tab tb=new tab();
    @Override
    public String getTabCaption() {
        return burp.BurpExtender.getProjectName();
    }

    @Override
    public Component getUiComponent() {
        return panel;
    }
    
}
