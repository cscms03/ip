package minnim.exception;

public class MinnimMissingTaskDetailException extends MinnimException{
    @Override
    public String toString() {
        return String.format("You are missing your task name. Please Provide one! %s", super.toString());
    }
 }
