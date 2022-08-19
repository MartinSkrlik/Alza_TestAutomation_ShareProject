package utility;

public class Queries {
    
    public enum Test_run {
        INSERT_NEW_TESTRUN 
        ("INSERT INTO TEST_RUN (GLOBAL_RUN_ID, TEST_SET_DESCRIPTION, EXECUTED_BY, EXECUTION_START, STATUS, TESTS_TOTAL) "
        + "VALUES (%s, '%s', '%s', SYSDATE, 'STARTED', %s)"),
        
        //TODO - number of tests warning to be handled
        UPDATE_RUN_STATUS
        ("UPDATE TEST_RUN SET EXECUTION_END = SYSDATE, STATUS = 'DONE', TESTS_TOTAL = %s, TESTS_PASSED = %s,  TESTS_FAILED = %s, TESTS_WARNING = '0' WHERE GLOBAL_RUN_ID = %s"),
        
        ADD_HTML_REPORT
        ("UPDATE TEST_RUN SET HTML_REPORT = ? WHERE GLOBAL_RUN_ID = ?"),
        
        UPDATE_TOTAL
        ("UPDATE TEST_RUN SET TESTS_TOTAL = %s WHERE GLOBAL_RUN_ID = %s"),
        ;
        
        private final String value;

        private Test_run(String value) {
            this.value = value;
        }

        public String get() {
            return value;
        }
    }
}