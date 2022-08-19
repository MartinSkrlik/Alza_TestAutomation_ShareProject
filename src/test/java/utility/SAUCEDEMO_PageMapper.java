package utility;

//Constants of page saucedemo.com
public class SAUCEDEMO_PageMapper {
	
	public static final String ERROR_MESSAGE    = "Epic sadface: Sorry, this user has been locked out.";
	public static final String SUCCESS_MESSAGE  = "Page title is not visible";
	public static final String FAIL_MESSAGE_ONE = "Page title is visible";
	public static final String FAIL_MESSAGE_TWO = "Page title is visible";
	
	public static String getFailMessage(String keyIHave) {
        String valueOfString = null;
        String defaultKey    = "FAIL_MESSAGE_"; 
		try {
			valueOfString = SAUCEDEMO_PageMapper.class.getDeclaredField(defaultKey + keyIHave).get(null).toString();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			Log.info("String by key " + "\"" + defaultKey + keyIHave + "\" not found!");
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			Log.info("String by key " + "\"" + defaultKey + keyIHave + "\" not found!");		
	    }
	    return valueOfString;
	}
}