package br.dev.amvs.jasked.jsf;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

@Named("languageController")
@SessionScoped
public class LanguageController implements Serializable {

	private static final long serialVersionUID = 1L;

	private String localeCode;

	private static Map<String, Object> languages;
	static {
		languages = new LinkedHashMap<String, Object>();
		languages.put("English", Locale.ENGLISH);
		languages.put("Português(BR)", new Locale("pt", "BR"));
	}

	public Map<String, Object> getLanguagesInMap() {
		return languages;
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public void languageLocaleCodeChanged(ValueChangeEvent e) {

		String newLocaleValue = e.getNewValue().toString();

		for (Map.Entry<String, Object> entry : languages.entrySet()) {

			if (entry.getValue().toString().equals(newLocaleValue)) {

				FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) entry.getValue());

			}
		}
	}

}
