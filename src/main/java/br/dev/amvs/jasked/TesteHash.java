package br.dev.amvs.jasked;

import br.dev.amvs.jasked.exception.UnexpectedBehaivorException;
import br.dev.amvs.jasked.security.util.SecurityUtil;

public class TesteHash {

	public static void main(String[] args) {
		try {
			System.out.println(SecurityUtil.passwordHash("admin"));
		} catch (UnexpectedBehaivorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
