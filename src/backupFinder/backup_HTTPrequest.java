/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package backupFinder;

import static backupFinder.BackupFinder.getBaseReqResp;
import burp.BurpExtender;
import burp.IBurpExtenderCallbacks;
import burp.IHttpRequestResponse;
import burp.IHttpService;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class backup_HTTPrequest implements Runnable {
    public Thread t;
    private int num=1;
    public IHttpService service;
    String method;
    IHttpRequestResponse requestResponse;
    List<String> file_list=new ArrayList<>();
    IBurpExtenderCallbacks callbacks=BurpExtender.callbacks;
    int code;
    
    backup_HTTPrequest( IHttpRequestResponse rr,List<String> files,String meth, int type){
        service=rr.getHttpService();
        requestResponse=rr;
        file_list=files;
        method=meth;
        code=type;
//       callbacks.makeHttpRequest(service, request);
    }
    
    public void run() {
        switch (code){
            case 1:     //Invalid Request
                for (int i = 0; i < file_list.size(); i++) {
                    byte[] req=getRequest(requestResponse, file_list.get(i));
                    IHttpRequestResponse rr=callbacks.makeHttpRequest(service, req);
                    BackupFinder.setInvalidReqResp(rr);
                }
                break;
            case 2: //find index Page
                for (int i = 0; i < file_list.size(); i++) {
                    if(BackupFinder.getIndexFile().length()==0){    //index file is not found yet
                        byte[] req=getRequest(requestResponse, file_list.get(i));
                        IHttpRequestResponse rr=callbacks.makeHttpRequest(service, req);
                        if(rr.getResponse()!=null&&callbacks.getHelpers().analyzeResponse(rr.getResponse()).getStatusCode()!=BackupFinder.getInvalidStatus()){
                            BackupFinder.setIndexFile(file_list.get(i));
                        }
                    }
                    
                }
                break;
            case 3: //request various files to see if they are sensitive files
                for (int i = 0; i < file_list.size(); i++) {
                    byte[] req=getRequest(requestResponse, file_list.get(i));
                    if(req!=null){  //file is unique
                        if(new String(req).contains(BackupFinder.getInvalidFileName())){
                            IHttpRequestResponse rr=callbacks.makeHttpRequest(service, req);
                            BackupFinder.setInvalidReqResp(rr);
                        }
                        else{
                            IHttpRequestResponse rr=callbacks.makeHttpRequest(service, req);
                            if(BackupFinder.getInvalidFile_ErrorText().length()>0){ //when user has filled this in options
                                if(new String(req).contains(BackupFinder.getInvalidFile_ErrorText())){
                                    BackupFinder.addToTable(rr);
                                }
                            }
                            else{
                                if(rr.getResponse()!=null&&callbacks.getHelpers().analyzeResponse(rr.getResponse()).getStatusCode()!=BackupFinder.getInvalidStatus()){
                                    BackupFinder.addToTable(rr);
                                }
                            }
                            BackupFinder.increaseSentRequests();
                        }
                    }
                    try {
                        Thread.sleep(BackupFinder.getThrottle());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(backup_HTTPrequest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                break;
                case 4: //find index pages from all directories
                for (int i = 0; i < file_list.size(); i++) {
                    byte[] req=getRequest(requestResponse, file_list.get(i));
                    IHttpRequestResponse rr=callbacks.makeHttpRequest(service, req);
                    if(rr.getResponse()!=null&&callbacks.getHelpers().analyzeResponse(rr.getResponse()).getStatusCode()!=BackupFinder.getInvalidStatus()){
                        //BackupFinder.addIndexFilesToList(file_list.get(i));
                        List<String>indexes=new ArrayList<>();
                        indexes.add(file_list.get(i));
                        List<String> payloads=BackupFinder.generatePayloadForFiles(indexes);
                        BackupFinder.addListToList(BackupFinder.indexBackupFiles_list, payloads);
                        backup_HTTPrequest bhr=new backup_HTTPrequest(getBaseReqResp(), payloads, (String) BackupFinder.methodCombobox.getSelectedItem(),3);
                        bhr.start();
                    }
                }
                break;
                
        }
        
    }
    
    public void start ()
    {
        t = new Thread (this, (num)+"");
        t.start ();
    }
    
    public Thread getThread(){
        return t;
    }
    
    private byte[] getRequest(IHttpRequestResponse ReqResp, String backupFile) {
        if(BackupFinder.fileIsUnique(backupFile)){
            URL baseURL=BurpExtender.callbacks.getHelpers().analyzeRequest(ReqResp).getUrl();
            String base=baseURL.getProtocol()+"://"+baseURL.getHost()+":"+baseURL.getPort();
            URL testURL=null;
            try {
                testURL = new URL(base+backupFile);
            } catch (MalformedURLException ex) {
                Logger.getLogger(BackupFinder.class.getName()).log(Level.SEVERE, null, ex);
            }
            byte[] req = buildRequest(testURL);
            
            return req;
        }
        else{
            BackupFinder.increaseSentRequests();
            return null;
        }
        
    }
    
    private byte[] buildRequest(URL testURL) {
        if(method.equals("HEAD")){
                return new String(BurpExtender.callbacks.getHelpers().buildHttpRequest(testURL)).replace("GET", "HEAD").getBytes();
            
        }
        else if(method.equals("GET")){
            return BurpExtender.callbacks.getHelpers().buildHttpRequest(testURL);
        }
        return BurpExtender.callbacks.getHelpers().buildHttpRequest(testURL);
    }
    
}