package org.microserv.rest.transformer;

import com.google.gson.Gson;

import spark.ResponseTransformer;

/**
 * Transform response into JSON format.
 * @author Lomov PA.
 *
 */
public class JsonTransformer implements ResponseTransformer
{
	 private Gson gson = new Gson();

	    @Override
	    public String render(Object model) 
	    {
	        return gson.toJson(model);
	    }
}
