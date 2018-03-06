package pajkouisn.utilities.json;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * 	This class extends functionalities for org.json.simple
 * 	Accessible functions:
 * 	1)	toJSONObject(String);	-->	Converts a String to a JSONObject.
 * 	2)	toJSONArray(String);	-->	Converts a String to a JSONArray.
 * 	3)	findValueWithKey(String...);	-->	Finds keys and returns their respective objects.	
 * 	4)	copy(String...);	-->	Copy key and their values into a desftination object.	
 * 
 * 	@author kartiklaw@gmail.com (www.kartik-reddy.com)
 *
 */
public class JSONOperations 
{
	private String string;
	
	//	Default Constructor
	JSONOperations()
	{
		this.string = "";
	}
	
	//	Parameterized Constructor.
	JSONOperations(String string)
	{
		this.string = string;
	}
	
	/**
	 * 	Set the value of JSON.
	 * 
	 * 	@param string
	 */
	public void set(String string)
	{
		this.string = string;
	}
	
	/**
	 * 	Get the string value of this JSONObject.
	 * 
	 * 	@return String 
	 */
	public String get()
	{
		return this.string;
	}	
	
	
	//	Functions for simple operations. 	
	/**
	 * 	A converter of Strings to JSONObjects.
	 * 
	 * 	@param string
	 * 	@return
	 */
	public static JSONObject toJSONObject (String string)
	{
		// 	Parser to parse the string.
		JSONParser parser = new JSONParser();
		
		// Object where string is stored in JSONObject format.
		JSONObject object = new JSONObject();
		
		try 
		{
			// 	If the string can be parsed successfully, store it in object.s
			object = (JSONObject) parser.parse(string);
		} 	
		catch (Exception e) 
		{
			// 	Else return null because JSON is not well formed.
			return null;
		}
		return object;
	}
	
	/**
	 *  A converter of String to JSONArray.
	 *  
	 * 	@param string
	 * 	@return
	 */
	public static JSONArray toJSONArray (String string)
	{
		// 	Parser to parse the string.
		JSONParser parser = new JSONParser();
		
		// Object where string is stored in JSONObject format.
		JSONArray object = new JSONArray();
		
		try 
		{
			// 	If the string can be parsed successfully, store it in object.s
			object = (JSONArray) parser.parse(string);
		} 	
		catch (Exception e) 
		{
			// 	Else return null because JSON is not well formed.
			return null;
		}
		return object;
	}
	
	
	/**
	 * 	Finds the first object with any key.
	 * 
	 * 	USAGE:
	 * 		findValueWithKey(String json, String key);
	 * 
	 * 	EXAMPLE:
	 * 		
	 * 		Consider the following json = 
	 * 	
	 * 		{
	 * 			"PAGE_1":
	 * 			{
	 * 				"WORD_1":
	 * 				{
	 * 					"BODY" : "CONTENT";
	 *				}
	 *			}
	 *		} 			
	 * 
	 * 	findValueWithKey(String json, String "BODY") will return "CONTENT".
	 * 		
	 * 
	 * 	The first parameter should be the JSON object that we wish to search.
	 * 	The second parameter should be the key to be found.
	 * 
	 * 	ALTERNATE USAGE:
	 * 		findValueWithKey(String json, String key, String parent1, String parent2);
	 * 
	 * 	EXAMPLE:
	 * 		
	 * 		Consider the following json = 
	 * 	
	 * 		{
	 * 			"PAGE_1":
	 * 			{
	 * 				"WORD_1":
	 * 				{
	 * 					"BODY" : "CONTENT"
	 *				}
	 *			},
	 *
	 *			"PAGE_2":
	 * 			{
	 * 				"WORD_1":
	 * 				{
	 * 					"BODY" : "CONTENT2"
	 *				}
	 *			}
	 *		} 	
	 *
	 *	findValueWithKey(String json, String "BODY", String "PAGE_2") will return "CONTENT2".
	 * 	@param strings
	 * 	@return
	 * 	@throws ParseException 
	 * 	@throws Exception
	 */
	public static String findValueWithKey(String... strings) throws IllegalArgumentException, ParseException
	{
		/*
		 * 	Get the length of parameters.
		 */
		int length = strings.length;
		if(length < 2)
			throw new IllegalArgumentException("Invalid input, expects at least 2 parameters. Usage: (JSONObject, keyName)");
		
		/*
		 * 	Get the json and the key to search for.
		 */
		String jsonContent = strings[0];
		String key = strings[1];
		
		/*
		 * 	If the parents are mentioned, then traverse to the last given parent.
		 */
		if(length > 2)
		{
			jsonContent = prepareJson(jsonContent, strings);
		}
		
		/*
		 * 	Cast the string to a JSONObject and validate it.
		 */
		JSONObject json = toJSONObject(jsonContent);
		if(json == null) return null;
		
		/*
		 * 	Check if the content has the key at top level.
		 * 	If yes, return the body. 
		 * 	Else, Recursively check.
		 */
		if(json.containsKey(key))
			return json.get(key).toString();
		else
		{
			/*
			 * 	Get all keys.
			 * 	Search each key for the value.
			 */
			for(@SuppressWarnings("unchecked")
			Iterator<String> iterator = json.keySet().iterator(); iterator.hasNext();)
			{
				String parent = iterator.next();
				String body = findValueWithKey(json.get(parent).toString(), key);
				if(body == null) continue;
				return body;
			}
		}
		return null;
	}
	

	/**
	 * 	Finds the first object with any key.
	 * 
	 * 	USAGE:
	 * 		findValueWithKey(JSONObject json, String key);
	 * 
	 * 	EXAMPLE:
	 * 		
	 * 		Consider the following json = 
	 * 	
	 * 		{
	 * 			"PAGE_1":
	 * 			{
	 * 				"WORD_1":
	 * 				{
	 * 					"BODY" : "CONTENT"
	 *				}
	 *			}
	 *		} 			
	 * 
	 * 	findValueWithKey(JSONObject json, String "BODY") will return "CONTENT".
	 * 		
	 * 
	 * 	The first parameter should be the JSON object that we wish to search.
	 * 	The second parameter should be the key to be found.
	 * 
	 * 	ALTERNATE USAGE:
	 * 		findValueWithKey(JSONObject json, String key, String parent1, String parent2);
	 * 
	 * 	EXAMPLE:
	 * 		
	 * 		Consider the following json = 
	 * 	
	 * 		{
	 * 			"PAGE_1":
	 * 			{
	 * 				"WORD_1":
	 * 				{
	 * 					"BODY" : "CONTENT"
	 *				}
	 *			},
	 *
	 *			"PAGE_2":
	 * 			{
	 * 				"WORD_1":
	 * 				{
	 * 					"BODY" : "CONTENT2"
	 *				}
	 *			}
	 *		} 	
	 *
	 *	findValueWithKey(JSONObject json, String "BODY", String "PAGE_2") will return "CONTENT2".
	 * 
	 * 	@param json
	 * 	@param strings
	 * 	@return
	 * 	@throws ParseException 
	 * 	@throws Exception
	 *
	 */
	public static String findValueWithKey(JSONObject json, String... strings) throws IllegalArgumentException, ParseException 
	{
		int length = strings.length;
		if(length < 1)
			throw new IllegalArgumentException("Invalid input, expects at least 2 parameters. Usage: (JSONObject, keyName)");
		
		/*
		 * 	Get the key to search for.
		 */
		String key = strings[0];
		String jsonContent = "";
		
		/*
		 * 	If the parents are mentioned, then traverse to the last given parent.
		 */
		if(length > 1)
		{
			jsonContent = prepareJson(json, strings);
			
			/*
			 * 	Cast the string to a JSONObject and validate it.
			 */
			json = toJSONObject(jsonContent);
			if(json == null) return null;
		}
			
		/*
		 * 	Check if the content has the key at top level.
		 * 	If yes, return the body. 
		 * 	Else, Recursively check.
		 */
		if(json.containsKey(key))
			return json.get(key).toString();
		else
		{
			/*
			 * 	Get all keys.
			 * 	Search each key for the value.
			 */
			for(@SuppressWarnings("unchecked")
			Iterator<String> iterator = json.keySet().iterator(); iterator.hasNext();)
			{
				String parent = iterator.next();
				String body = findValueWithKey(json.get(parent).toString(), key);
				if(body == null) continue;
				return body;
			}
		}
		return null;
	}

	
	/**
	 * 	@param jsonContent
	 * 	@param strings
	 * 	@return
	 * 	@throws ParseException
	 *
	 * 	Given the parents, this function prepares the final JSONObject to be parsed.
	 */
	private static String prepareJson(String jsonContent, String[] strings) throws ParseException 
	{
		/*
		 * 	Cast the string to an object.
		 */
		JSONObject json = toJSONObject(jsonContent);
		if(json == null) throw new ParseException(0, "JSON is not well formed");
		
		return prepareJson(json, strings);
	}
	
	/**
	 * 	Given the parents, this function prepares the final JSONObject to be parsed.
	 * 	
	 * 	@param json
	 * 	@param strings
	 * 	@return
	 * 	@throws ParseException
	 */
	private static String prepareJson(JSONObject json, String[] strings) throws ParseException 
	{
		for(int i=1; i < strings.length; i++ )
		{
			String key = strings[i];
			if(json.containsKey(key))
			{
				json = toJSONObject(json.get(key).toString());
				if(json == null) throw new ParseException(0, "JSON is not well formed");
			}
		}
		return json.toJSONString();
	}
	
	//	TODO allow parsing paths.
	/**
	 * 	Copy the keys and their values from source to destination object (Root level only).
	 * 
	 * 	EXAMPLE:
	 * 	
	 * 		Consider the following JSON(A) =
	 * 		
	 * 		{
	 * 			"key1" : "value1",
	 * 			"key2" : "value2",
	 * 			"key3" : 
	 * 			{
	 * 				"chileKey1" : "childValue1"
	 *			}
	 *		} 		
	 *
	 *		and JSON (B) =
	 *		{
	 * 			"key4" : "value4",
	 *		} 
	 * 
	 * 	copy(A.toString, B.toString, "key1", "key3") will return
	 * 
	 * 		{
	 * 			"key1" : "value1",
	 * 			"key4" : "value4",
	 * 			"key3" : 
	 * 			{
	 * 				"chileKey1" : "childValue1"
	 *			}
	 *		} 	
	 * 
	 * 	@param source
	 * 	@param destination
	 * 	@param strings
	 * 	@return
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject copy(JSONObject source, JSONObject destination, String...strings)
	{
		/*
		 * 	Iterate through all the keys.
		 */
		for(int i=0; i<strings.length; i++)
		{
			String key = strings[i]; 
			/*
			 * 	If the source contains the key, copy it.
			 */
			if(source.containsKey(key))
				destination.put(key, source.get(key));
		}
		
		return destination;
	}
	
	/**
	 * 	Copy the keys and their values from source to destination object (Root level only).
	 * 	EXAMPLE:
	 * 	
	 * 		Consider the following JSON(A) =
	 * 		
	 * 		{
	 * 			"key1" : "value1",
	 * 			"key2" : "value2",
	 * 			"key3" : 
	 * 			{
	 * 				"chileKey1" : "childValue1"
	 *			}
	 *		} 		
	 *
	 *		and JSON (B) =
	 *		{
	 * 			"key4" : "value4",
	 *		} 
	 * 
	 * 	copy(A.toString, B.toString, "key1", "key3") will return
	 * 
	 * 		{
	 * 			"key1" : "value1",
	 * 			"key4" : "value4",
	 * 			"key3" : 
	 * 			{
	 * 				"chileKey1" : "childValue1"
	 *			}
	 *		} 	
	 * 
	 * 
	 * 	@param strings
	 * 	@return
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject copy(String...strings)
	{
		/*
		 * 	If source and destination not present, then no point.
		 */
		if(strings.length < 2)
			throw new IllegalArgumentException ("Expects a source and destinatin object. Found lesser arguments.");
		
		/*
		 * 	Cast source and destination to JSONObjects.
		 */
		JSONObject source = toJSONObject(strings[0]);
		if (source == null) throw new IllegalArgumentException ("Source cannot be cast to a JSONObject.");
		
		
		JSONObject destination = toJSONObject(strings[1]);
		if (destination == null) throw new IllegalArgumentException ("Destination cannot be cast to a JSONObject.");
		
		/*
		 * 	Iterate through all the keys.
		 */
		for(int i=2; i<strings.length; i++)
		{
			String key = strings[i]; 
			/*
			 * 	If hte source contains the key, copy it.
			 */
			if(source.containsKey(key))
				destination.put(key, source.get(key));
		}
		
		return destination;
	}
	
	//	TODO JSON Path.
	//	TODO JSON Flatten. 
}
