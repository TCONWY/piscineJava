package edu.school21.chat.exceptions;

public class NotSavedException extends RuntimeException{
    public NotSavedException() {
        super("No such entry exception!");
    }
}