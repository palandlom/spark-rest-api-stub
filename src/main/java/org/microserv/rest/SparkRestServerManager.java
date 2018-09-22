package org.microserv.rest;

import static spark.Spark.delete;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.internalServerError;
import static spark.Spark.options;
import static spark.Spark.path;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.threadPool;
import static spark.Spark.init;
import static spark.Spark.stop;
import static spark.Spark.awaitInitialization;

import org.microserv.rest.common.ResponseStatus;
import org.microserv.rest.common.StandardResponse;
import org.microserv.rest.exception.DataIntegrityException;
import org.microserv.rest.transformer.JsonTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;



public class SparkRestServerManager
{
	public static final int SERVER_PORT = 4567;
	public static final String SERVER_HOST = "localhost";
	public static final String MAIN_PATH = "http://"+SERVER_HOST+":"+SERVER_PORT+"/api/";
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SparkRestServerManager.class);

	
	public static void main(String[] args)
	{
		startServer();
	}
	
	public static void startServer()
	{
		LOGGER.info("======== Starting REST server ============");
		initServerConfig();
		initExceptionHandling();
		initPaths();
		LOGGER.info("======== REST server has started =========");

	}
	
	public static void stopServer()
	{
		LOGGER.info("======== Stoping REST server =============");
		stop();
		LOGGER.info("======== REST server has stoped===========");
	}

	/**
	 * Path = {Routes}
	 */
	private static void initPaths()
	{
		// === root path for all routes
		path("/api", () -> {
			initRoutes();
		});
	}

	private static void initServerConfig()
	{
		port(SERVER_PORT);
		
		// === configure the minimum numbers of threads, and the idle timeout:
		int maxThreads = 8;
		int minThreads = 2;
		int timeOutMillis = 30000;
		threadPool(maxThreads, minThreads, timeOutMillis);
		
		init();
		awaitInitialization();

		
	}


	
	private static void initRoutes()
	{

		get("/", (request, response) -> {
			response.type("application/json");

//			Random rand = new Random();
//			if (rand.nextBoolean())
//				throw new DataIntegrityException("AAAAAAAA");
		
			// Show something
			return new StandardResponse(ResponseStatus.SUCCESS);
		}, new JsonTransformer());
		
		// method with variable in path
		get("/user/:name", (request, response) -> {
			response.type("application/json");

			String name = request.params(":name");		
			StandardResponse resp = new StandardResponse(ResponseStatus.SUCCESS);
			resp.setData(name);
			
			return resp;
		}, new JsonTransformer());


		post("/", (request, response) -> {
			response.type("application/json");

			// Create something
			return new Gson()
					.toJson(new StandardResponse(ResponseStatus.SUCCESS));
		});

		put("/", (request, response) -> {
			response.type("application/json");

			// Update something
			return new Gson()
					.toJson(new StandardResponse(ResponseStatus.SUCCESS));
		});

		delete("/", (request, response) -> {
			response.type("application/json");

			// Annihilate something
			return new Gson()
					.toJson(new StandardResponse(ResponseStatus.SUCCESS));
		});

		options("/", (request, response) -> {
			response.type("application/json");

			// Appease something
			return new Gson()
					.toJson(new StandardResponse(ResponseStatus.SUCCESS));
		});
	}

	private static void initExceptionHandling()
	{
		// handling of Custom exception
		exception(DataIntegrityException.class,
				(exception, request, response) -> {
					response.body(new Gson().toJson(new StandardResponse(
							ResponseStatus.ERROR, exception.getMessage())));
					response.status(500);
				});

		// handling of Default exception
		internalServerError((req, res) -> {
			res.type("application/json");
			return "{\"message\":\"Custom 500 handling\"}";
		});
	}

}
