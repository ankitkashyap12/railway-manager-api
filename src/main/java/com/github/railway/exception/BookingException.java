package com.github.railway.exception;

/**
 * @author Ankit Kashyap on 09-03-2024
 */
public class BookingException extends Exception {

    private String message;
    private String erorrCode;
    public  BookingException(String msg)  {
        super(msg);
    }

    public BookingException(String message, String errorCode) {
        this.erorrCode=errorCode;
        this.message =message;
    }
}
