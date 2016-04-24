package com.hochhauser.mongodbcourse;

import freemarker.template.Configuration;

public class HelloFreemarker {

	public static void main(String[] args) {
		Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(HelloFreemarker.class, "/");
	}
}
