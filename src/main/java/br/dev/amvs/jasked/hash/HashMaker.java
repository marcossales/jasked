package br.dev.amvs.jasked.hash;

import br.dev.amvs.jasked.exception.UnexpectedBehaivorException;

public interface HashMaker {
	
	public  String hashAsString(String input) throws UnexpectedBehaivorException;
	

}
