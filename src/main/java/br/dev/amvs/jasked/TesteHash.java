package br.dev.amvs.jasked;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.dev.amvs.jasked.exception.UnexpectedBehaivorException;
import br.dev.amvs.jasked.jsf.util.JsfUtil;
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
