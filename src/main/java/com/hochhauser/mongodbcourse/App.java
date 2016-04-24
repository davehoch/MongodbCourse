package com.hochhauser.mongodbcourse;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class App {

	public static void main(String[] args) {
		Spark.get(new Route("hello") {
			@Override
			public Object handle(Request rqst, Response rspns) {
				return "hello";
			}
		});
	}
}
