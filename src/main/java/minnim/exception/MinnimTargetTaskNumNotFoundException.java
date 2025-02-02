package minnim.exception;

public class MinnimTargetTaskNumNotFoundException extends MinnimException{
    @Override
    public String toString() {
        return String.format("You are missing your target task number. Please Provide one! %s", super.toString());
    }
}
