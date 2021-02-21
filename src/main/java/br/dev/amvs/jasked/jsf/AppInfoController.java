package br.dev.amvs.jasked.jsf;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named("appInfoController")
@ApplicationScoped
public class AppInfoController implements Serializable {

    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    
    private  Map<String,SessionData> openSessions ;
    
  
	
    
    public void addSessionData(SessionData data) {
    	openSessions.put(data.getSessionId(),data);
    }
    public void removeSessionData(String sessionId) {
    	openSessions.remove(sessionId);
    }
    
    
    
    @PostConstruct
    public void init() {
    	openSessions = new HashMap<String,SessionData>();
    }
	public  Map<String, SessionData> getOpenSessions() {
		return openSessions;
	}
	
    
    
	

	  
	    
	   
    
}
