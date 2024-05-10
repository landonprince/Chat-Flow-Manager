// By: Landon Prince (5/9/2024)

/**
 * Message object that has a sender (user), a text message, and a timestamp
 * of when the message was sent
 */
public class Message {
    private final String user;
    private final String text;
    private final int timestamp;

    /**
     * Default constructor. Sets sender & message to "" and timestamp to zero
     */
    public Message() {
        this("","",0);
    }

    /**
     * Alternate constructor. Sets the fields to the given parameters.
     * @param sender -- given user
     * @param msg -- given text of the message
     * @param ts -- given timestamp
     * Note: throws IllegalArgumentException if ts is negative
     */
    public Message(String sender, String msg, int ts) {
        user = sender;
        text = msg;
        timestamp = ts;
        if (ts < 0) {
            throw new IllegalArgumentException("Timestamp must not be negative.");
        }
    }

    /**
     * getUser -- accessor to get user
     * @return user of message
     */
    public String getUser() {
        return user;
    }

    /**
     * getText -- accessor to get text
     * @return text of message
     */
    public String getText() {
        return text;
    }

    /**
     * getTimestamp -- accessor to get time stamp
     * @return timestamp of message
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * toString -- return string representation of the message
     * @return String
     */
    public String toString(){
        return user + ": " + text + " (sent at " + timestamp + ")";
    }

    /**
     * equals -- compares two objects for equality
     * @param other -- the object to be compared
     * @return boolean -- true if they are identical message objects, false otherwise
     */
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Message otherMessage) {
            return user.equals(otherMessage.user) &&
                    text.equals(otherMessage.text) &&
                    timestamp == otherMessage.timestamp;
        }
        return false;
    }

    /**
     * sameTime -- compares two messages for timestamp equality
     * @param other -- the object to be compared
     * @return boolean -- true if they are from the same time, false otherwise
     */
    public boolean sameTime(Message other) {
        return timestamp == other.timestamp;
    }

    /**
     * before -- compares two messages for timestamp inequality
     * @param other -- the object to be compared
     * @return boolean -- true this message came before other, false otherwise
     */
    public boolean before(Message other) {
        return timestamp < other.timestamp;
    }

    /**
     * before -- compares two messages for timestamp inequality
     * @param other -- the object to be compared
     * @return boolean -- true this message came after other, false otherwise
     */
    public boolean after(Message other) {
        return timestamp > other.timestamp;
    }

    /**
     * sameTime -- compares two timestamps for timestamp equality
     * @param otherTimeStamp -- the object to be compared
     * @return boolean -- true if they are from the same time, false otherwise
     */
    public boolean sameTime(int otherTimeStamp) {
        return timestamp == otherTimeStamp;
    }

    /**
     * before -- compares two timestamps for timestamp inequality
     * @param otherTimeStamp -- the object to be compared
     * @return boolean -- true this message came before other, false otherwise
     */
    public boolean before(int otherTimeStamp) {
        return timestamp < otherTimeStamp;
    }

    /**
     * before -- compares two timestamps for timestamp inequality
     * @param otherTimeStamp -- the object to be compared
     * @return boolean -- true this message came after other, false otherwise
     */
    public boolean after(int otherTimeStamp) {
        return timestamp > otherTimeStamp;
    }

    /**
     * replaceUser -- replace the user on a message; returns a new message
     * @param newUser -- the new user id
     */
    public Message replaceUser(String newUser) {
        return new Message(newUser, this.text, this.timestamp);
    }

    /**
     * replaceText -- replace the text of a message; returns a new message
     * @param newText -- the new text msg
     */
    public Message replaceText(String newText) {
        return new Message(this.user, newText, this.timestamp);
    }

    /**
     * replaceTimestamp -- replace the time stamp on a message; returns a new message
     * @param newTime -- the new time stamp
     * Note: throws IllegalArgumentException if newTime is negative
     */
    public Message replaceTimestamp(int newTime) {
        return new Message(this.user, this.text, newTime);
    }
}
