package br.dev.amvs.jasked.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.dev.amvs.jasked.jsf.util.JsfUtil;

@WebFilter("*.xhtml")
public class AuthorizationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		if (    !isUsuarioLogado(request.getSession()) 
				&& request.getRequestURI().contains(request.getContextPath()+"/admin/")
				/*&& !request.getRequestURI().contains("/javax.faces.resource/")*/) {
			
			//TODO: remember  filter pages only for superuser (if people try access it directly from URL)
			
			//TODO: remember  filter pages only for some permission (if people try access it directly from URL)
			
			response.sendRedirect(request.getContextPath() + "/Login.xhtml");
		} else {
			chain.doFilter(req, res);
		}

	}

	private boolean isUsuarioLogado(HttpSession session) {
		return session.getAttribute(JsfUtil.LOGGEDIN_USERNAME_SESSION_ATTRIBUTE)!=null;
	}

}
