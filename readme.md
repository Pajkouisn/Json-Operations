# JSON Operations

## Some simple operations on java.util.Collections.Map

### Functions included:

* toJSONObject(String)

```
	Converts a String to a JSONObject.
```

* toJSONArray(String)

```
	Converts a String to a JSONObject.
```
* findValueWithKey(Strings...)
* findValueWithKey(JSONObject, Strings...)

```
	Finds the value for any key in a JSON. Returns the first match.
    EXAMPLE:
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
```
* copy(Strings...)
* copy(JSONObject, JSONObject, Strings...)

```
	Copies specified keys into the target JSON from the source JSON.
    EXAMPLE:
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
```