package br.dev.amvs.jasked.security.util;

import br.dev.amvs.jasked.exception.UnexpectedBehaivorException;
import br.dev.amvs.jasked.hash.HashMaker;
import br.dev.amvs.jasked.hash.Sha512HashMaker;

public class SecurityUtil {
	

    public static HashMaker getDefaultPasswordHashMaker() {
		return new Sha512HashMaker();
	}
    
    public static String passwordHash(String pass) throws UnexpectedBehaivorException {
    	if(pass==null || pass.length()==0) {
    		return null;
    	}
    	HashMaker maker = getDefaultPasswordHashMaker();
    	return maker.hashAsString(pass);
    	
    }
    public static String passwordHash(char[] pass) throws UnexpectedBehaivorException {
    	if(pass==null || pass.length==0) {
    		return null;
    	}
    	HashMaker maker = getDefaultPasswordHashMaker();
    	return maker.hashAsString(new String(pass));
    	
    }
  
    

}
