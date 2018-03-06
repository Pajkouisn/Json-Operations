package pajkouisn.unit;

import static org.junit.Assert.assertTrue;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import pajkouisn.utilities.json.JSONOperations;

import static pajkouisn.utilities.json.JSONOperations.toJSONObject;
import static pajkouisn.utilities.json.JSONOperations.toJSONArray;

/**
 * 
 * 	JUnit tests for JSONOperations.
 * 	
 * 	@author kartiklaw@gmail.com (www.kartik-reddy.com)
 *
 */
@SuppressWarnings("unchecked")
public class TestJSONOperations
{
	
	static JSONObject json = new JSONObject();
	
	static
	{
		json.put("B", 2);
		json.put("A", 1);
		json.put("C", 3);
		json.put("E", 5);
		json.put("D", 4);
	}
	
	/*
	 * 	Convert String to JSONObject
	 */
	@Test
	public void testToJSONObject()
	{
		String input = "{\"key\":\"value\"}";
		JSONObject json = toJSONObject(input);
		
		if (json.containsKey("key") && json.get("key").toString().equals("value"))
			assertTrue(true);
		else 
			assertTrue(false);
	}
	
	/*
	 * 	Convert String to JSONArray
	 */
	@Test
	public void testToJSONArray()
	{
		String input = "[{\"key\":\"value\"},{\"key\":\"value\"}]";
		JSONArray jsonArr = toJSONArray(input);
		
		boolean check = true;
		
		for(int i=0; i < jsonArr.size(); i++)
		{
			JSONObject json = toJSONObject(jsonArr.get(i).toString());
			if (json.containsKey("key") && json.get("key").toString().equals("value"))
				check &= true;
			else 
				check &= false;
		}
		
		assertTrue(check);
	}
	
	/*
	 * 	Find Value with Key with String Input.
	 */
	@Test
	public void testFindValueWithKeyStringInput() throws IllegalArgumentException, ParseException
	{
		String input = "{\"PAGE_1\":{\"WORD_1\":{\"BODY\":\"CONTENT\"}},\"PAGE_2\":{\"WORD_1\":{\"BODY\":\"CONTENT2\"}}}";
		boolean check = true;
		
		if (JSONOperations.findValueWithKey(input, "BODY").equals("CONTENT"))
		{
			if (JSONOperations.findValueWithKey(input, "BODY", "PAGE_2").equals("CONTENT2"));
			else check = false;
		}
		
		else check = false;
		
		assertTrue(check);
	}
	
	/*
	 * 	Find Value with Key with JSON Input.
	 */
	@Test
	public void testFindValueWithKeyJSONObjectInput() throws IllegalArgumentException, ParseException
	{
		String input = "{\"PAGE_1\":{\"WORD_1\":{\"BODY\":\"CONTENT\"}},\"PAGE_2\":{\"WORD_1\":{\"BODY\":\"CONTENT2\"}}}";
		boolean check = true;
		
		if (JSONOperations.findValueWithKey(toJSONObject(input), "BODY").equals("CONTENT"))
		{
			if (JSONOperations.findValueWithKey(toJSONObject(input), "BODY", "PAGE_2").equals("CONTENT2"));
			else check = false;
		}
		
		else check = false;
		
		assertTrue(check);
	}
	

	/*
	 * 	Copy with JSONObject input.
	 */
	@Test
	public void testCopyJSONObjectInput() throws IllegalArgumentException, ParseException
	{
		JSONObject copy = new JSONObject();
		JSONOperations.copy(json, copy, "A", "B", "C", "d");

		if (
				(copy.containsKey("A")) && 
				(copy.containsKey("B")) && 
				(copy.containsKey("B")) &&
				!(copy.containsKey("D")))
			assertTrue(true);
		else
			assertTrue(false);
	}
	
	/*
	 * 	Copy with String input.
	 */
	@Test
	public void testCopyStringInput() throws IllegalArgumentException, ParseException
	{
		JSONObject copy = new JSONObject();
		copy = JSONOperations.copy(json.toJSONString(), copy.toJSONString(), "A", "B", "C", "d");

		if (
				(copy.containsKey("A")) && 
				(copy.containsKey("B")) && 
				(copy.containsKey("B")) &&
				!(copy.containsKey("D")))
			assertTrue(true);
		else
			assertTrue(false);
	}
}
