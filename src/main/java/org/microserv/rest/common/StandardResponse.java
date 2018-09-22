package org.microserv.rest.common;


import com.google.gson.JsonElement;

public class StandardResponse
{

	private ResponseStatus status;
	private String message;
	private Object data;

	public StandardResponse(ResponseStatus status)
	{
		this.status = status;
		this.message = "";

	}

	public StandardResponse(ResponseStatus status, String message)
	{
		this.status = status;
		this.message = message;
	}

	public StandardResponse(ResponseStatus status, JsonElement data)
	{
		this.status = status;
		this.message = "";
		this.data = data;
	}

	public ResponseStatus getStatus()
	{
		return status;
	}

	public void setStatus(ResponseStatus status)
	{
		this.status = status;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}


}
