package com.hochhauser.mongodbcourse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class HelloFreemarker {

	public static void main(String[] args) throws IOException, TemplateException {
		Spark.get(new Route("freemarker") {
			@Override
			public Object handle(Request rqst, Response rspns) {
				try {
					Configuration config = new Configuration();
					config.setClassForTemplateLoading(HelloFreemarker.class, "/");
					Template template = config.getTemplate("hello.ftl");
					Map<String, Object> map = new HashMap<>();
					map.put("name", "blah blah blah");

					StringWriter out = new StringWriter();
					template.process(map, out);
					return out.toString();
				} catch (IOException | TemplateException ex) {
					Logger.getLogger(HelloFreemarker.class.getName()).log(Level.SEVERE, null, ex);
					halt(500);
					return ex;
				}
			}
		});
	}
}
