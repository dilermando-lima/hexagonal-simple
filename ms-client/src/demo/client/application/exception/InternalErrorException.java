package demo.client.application.exception;


public class InternalErrorException extends RuntimeException {

    public InternalErrorException(String message){
        super(message);
    }

    public InternalErrorException(String message, Throwable throwable){
        super(message, throwable);
    }
    
}
