package com.jyh.app.plat.attach;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jyh.app.plat.common.SysConst;

@WebFilter("/*")
public class RestFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// ==========编码==========
		request.setCharacterEncoding(SysConst.ENCODING);
		response.setCharacterEncoding(SysConst.ENCODING);

		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;

//		SysConst.setCrosResponseHeader(httpResponse);

		if (httpRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
			httpResponse.setStatus(200);
			return;
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
