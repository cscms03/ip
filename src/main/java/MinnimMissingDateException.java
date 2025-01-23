public class MinnimMissingDateException extends MinnimException{
    @Override
    public String toString() {
        return String.format("You are missing date for deadline/event task. Please provide as necessary. %s",
                super.toString());
    }
}
