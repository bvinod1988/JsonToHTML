package com.jsontable;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jsontable.JsonToHtml;

@Controller
public class WelcomeController {

	// inject via application.properties
	@Value("${welcome.message:test}")
	private String message = "Hello World";

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message);
		return "welcome";
	}
	
	@RequestMapping(value="/convertjson", method = RequestMethod.POST)
	public String convertJson(Map<String, Object> model, HttpServletRequest request) {
		String json = request.getParameter("jsontextarea");
		JsonToHtml jhtml = new JsonToHtml();
		String htmlString = jhtml.convert(json);
		model.put("body", htmlString);
		return "jsontable";
	}

}