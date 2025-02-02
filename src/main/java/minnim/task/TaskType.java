package minnim.task;

public enum TaskType {
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    LIST("list");

    private final String keyword;

    TaskType(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public static TaskType fromString(String keyword) {
        for (TaskType type : TaskType.values()) {
            if (type.getKeyword().equalsIgnoreCase(keyword)) {
                return type;
            }
        }
        return null; // If no match is found
    }
}
