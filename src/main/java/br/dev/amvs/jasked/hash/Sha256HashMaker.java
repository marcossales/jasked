package br.dev.amvs.jasked.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import br.dev.amvs.jasked.exception.UnexpectedBehaivorException;

public class Sha256HashMaker implements HashMaker {

	@Override
	public String hashAsString(String input) throws UnexpectedBehaivorException {
		String text = input;
		String salt = "jasked";
		text = salt+text;
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new UnexpectedBehaivorException(e);
			
		}
		byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
		String encoded = Base64.getEncoder().encodeToString(hash);
		return encoded;
	}

}
