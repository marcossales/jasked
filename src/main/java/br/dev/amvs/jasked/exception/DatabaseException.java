package br.dev.amvs.jasked.exception;

public class DatabaseException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DatabaseException() {
	}
	
	public DatabaseException(String msg) {
		super(msg);
	}
	public DatabaseException(String msg, Throwable cause) {
		super(msg,cause);
	}
	public DatabaseException( Throwable cause) {
		super(cause);
	}

}
