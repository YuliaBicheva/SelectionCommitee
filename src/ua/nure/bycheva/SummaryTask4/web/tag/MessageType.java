package ua.nure.bycheva.SummaryTask4.web.tag;

/**
 * Type for custom message tag.
 */
public enum MessageType {

    SUCCESS, ERROR, INFO;

    public String getName(){
        return name().toLowerCase();
    }
}
