package config;

import utility.ConfigFileReader;

public class Config extends BaseConfig { 
    
    //to be able call decryption method anywhere in the project 
    public static String getDecryptedValue(String key) {
        return decryptValue(key);	
    }
    
    public enum Database {

        ROBO (getDecryptedValue("ROBODB_Link"),
              getDecryptedValue("ROBODB_Username"),
              getDecryptedValue("ROBODB_Pwd"),
              ConfigFileReader.getValueFromProperties("ROBODB_Driver")
             ),
        ;
        
        private String url;
        private String user;
        private String pwd;
        private String driver;

        private Database(String url, String user, String pwd, String driver) {           
            this.url = url;
            this.user = user;
            this.pwd = pwd;
            this.driver = driver;
        }

        public String getConnectionString() {
            return url;
        }
    
        public String getUser() {
            return user;
        }
    
        public String getPWD() {
            return pwd;
        }
        
        public String getDriver() {
            return driver;
        }
    }
}