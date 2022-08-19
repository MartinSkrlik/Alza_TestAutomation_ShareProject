package utility;

public class Validation {

    private boolean condition;
    private String msg;
    private String check;
    private Object actual;
    private Object expected;
    
    public Validation(String check, Boolean condition) {
        this.check = check;
        this.condition = condition;
    }
    
    public Validation(String check, Object actual, Object expected) {
        this.check = check;
        this.actual = actual;
        this.expected = expected;
    }
    
    //Check if actual and expected strings are equal
    public void stringEquals() {
        String actualValue = (String) actual;
        String expectedValue = (String) expected;
        
        if (actualValue.equals(expectedValue)) {
            msg = "Expected value '" + expectedValue + "' equals to actual value '" + actualValue + "'. ";
            logPassMessage();
        }
        else {
            msg = "Expected value '" + expectedValue + "' does not equal to actual value '" + actualValue + "'. ";
            logFailMessage();
        }
    }
    
  //Check if actual and expected double/numbers are equal
    public void doubleEquals() {
        Double actualValue = (Double) actual;
        Double expectedValue = (Double) expected;
        
        if (actualValue.equals(expectedValue)) {
            msg = "Expected value '" + expectedValue + "' equals to actual value '" + actualValue + "'. ";
            logPassMessage();
        }
        else {
            msg = "Expected value '" + expectedValue + "' does not equal to actual value '" + actualValue + "'. ";
            logFailMessage();
        }
    }
    
  //Check if actual and expected double/numbers are equal
    public void integerEquals() {
        Integer actualValue   = (Integer) actual;
        Integer expectedValue = (Integer) expected;
        
        if (actualValue.equals(expectedValue)) {
             msg = "Actual value '" + actualValue + "' contains expected value '" + expectedValue + "'. ";
            logPassMessage();
        }
        else {
            msg = "Expected value '" + expectedValue + "' does not equal to actual value '" + actualValue + "'. ";
            logFailMessage();
        }
    }
    
    //Check if actual string contains expected substring
    public void contains() {
        String actualValue = (String) actual;
        String expectedValue = (String) expected;
        
        if (actualValue.contains(expectedValue)) {
            msg = "Actual value '" + actualValue + "' contains expected value '" + expectedValue + "'. ";
            logPassMessage();
        }
        else {
            msg = "Actual value '" + actualValue + "' does not contain expected value '" + expectedValue + "'.";
            logFailMessage();
        }
    }
    
    //Check if condition is false
    public void isTrue() {
        if (!condition) {
            msg = "Expected value is '" + true + "', but actual value is '" + condition + "'. ";
            logFailMessage();
        }
    }
    
    //Check if condition is true
    public void isFalse() {
        if (condition) {
            msg = "Expected value is '" + false + "', but actual value is '" + condition + "'. ";
            logFailMessage();
        }
    }
    
    private void logPassMessage() {
        Log.info(check + ": " + msg);
        ReportExtender.logPass("Validating " + check + ": </br>" + ReportExtender.addMarkup("PASS") + msg);
    }
    
    private void logFailMessage() {
        Log.warn(msg);
        ReportExtender.logWarning("Validating " + check + ": </br>" + ReportExtender.addMarkup("WARN") + msg);
    }
}