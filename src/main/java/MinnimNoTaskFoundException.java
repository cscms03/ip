public class MinnimNoTaskFoundException extends MinnimException{
    private int taskNum;
    public MinnimNoTaskFoundException(int taskNum) {
        this.taskNum = taskNum;
    }

    @Override
    public String toString() {
        return String.format("I cannot find the task number %d. %s", this.taskNum, super.toString());
    }
}
