package br.dev.amvs.jasked.exception;

import java.util.ResourceBundle;

public class UnexpectedBehaivorException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnexpectedBehaivorException() {
		super(ResourceBundle.getBundle("/Messages").getString("UnexpectedBehaivor"));
	}
    public UnexpectedBehaivorException(Throwable e) {
	   super(ResourceBundle.getBundle("/Messages").getString("UnexpectedBehaivor"),e);	
	}


}
