package com.toedter.ms60min;

import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@BasePathAwareController
public class HalExplorer {
	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public View index(HttpServletRequest request) {
        final String url = "/webjars/hal-explorer/0.9.5/index.html?#url=/api";
        return new RedirectView(url);
	}
}