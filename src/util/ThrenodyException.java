package util;

public class ThrenodyException extends Exception {
    public ThrenodyException(String message) { super("\n========= [THRENODY GAME EXCEPTION] =========\n" + message); }

    public ThrenodyException(String message, Throwable cause) { super("\n========= [THRENODY GAME EXCEPTION] =========\n" + message, cause); }

}
