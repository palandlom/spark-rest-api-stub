package org.test.main;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.microserv.rest.SparkRestServerManager;
import org.microserv.rest.common.StandardResponse;

import com.google.gson.Gson;

import static org.microserv.rest.SparkRestServerManager.SERVER_HOST;
import static org.microserv.rest.SparkRestServerManager.SERVER_PORT;
import static org.junit.Assert.*;
import static org.microserv.rest.SparkRestServerManager.MAIN_PATH;

public class SimpleTest
{
	@BeforeClass
	public static void start()
	{
		SparkRestServerManager.startServer();
	}

	@AfterClass
	public static void shutdown() throws InterruptedException
	{
		SparkRestServerManager.stopServer();
	}

	@Test
	public void test()
	{

	}

	@Test
	public void givenRequestWithNoAcceptHeader_whenRequestIsExecuted_thenDefaultResponseContentTypeIsJson()
			throws ClientProtocolException, IOException
	{
		// Given
		String jsonMimeType = "application/json";
		HttpUriRequest request = new HttpGet(MAIN_PATH);

		// When
		CloseableHttpResponse response = HttpClientBuilder.create().build()
				.execute(request);

		// Then
		String mimeType = ContentType.getOrDefault(response.getEntity())
				.getMimeType();
		assertEquals(jsonMimeType, mimeType);
	}

	@Test
	public void givenUserDoesNotExists_whenUserInfoIsRetrieved_then404IsReceived()
			throws ClientProtocolException, IOException
	{
		// Given
		String name = RandomStringUtils.randomAlphabetic(8);
		HttpUriRequest request = new HttpGet(MAIN_PATH + name);

		// When
		HttpResponse httpResponse = HttpClientBuilder.create().build()
				.execute(request);

		// Then
		assertThat(httpResponse.getStatusLine().getStatusCode(),
				equalTo(HttpStatus.SC_NOT_FOUND));
	}
	
	@Test // payload test
	public void
	  givenUserExists_whenUserInformationIsRetrieved_thenRetrievedResourceIsCorrect()
	  throws ClientProtocolException, IOException {
	  
	    // Given
		String name = "Vasya";
	    HttpUriRequest request = new HttpGet( MAIN_PATH + "user/"+name);
	 
	    // When
	    HttpResponse response = HttpClientBuilder.create().build().execute( request );
	 
	    // Then
	    String json = EntityUtils.toString(response.getEntity());
	    Gson gson = new Gson();
	    StandardResponse responseInstance = gson.fromJson(json, StandardResponse.class);
	    assertEquals( name, responseInstance.getData()  );
	}

}
