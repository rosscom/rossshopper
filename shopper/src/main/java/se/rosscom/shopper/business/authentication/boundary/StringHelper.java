package se.rosscom.shopper.business.authentication.boundary;

public class StringHelper {

    public String getStringAfterSeparator(String authString, String separator) {
        return authString.substring(authString.lastIndexOf(separator) + 1);
    }

    public String getStringBeforeSeparator(String authString, String separator) {
        return authString.substring(0, authString.indexOf(separator));
    }
}
