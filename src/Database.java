// By: Landon Prince (5/9/2024)

/**
 * Database class contains linked list of message objects, and includes
 * many methods to store, retrieve, and manipulate messages
 */
public class Database {
    /**
     * represents a single node in the linked list, containing a
     * reference to a message object and a reference to the next node
     */
    public static class MessageNode {
        public Message message;
        public MessageNode next;

        public MessageNode() {
            this(new Message(), null);
        }

        public MessageNode(Message message) {
            this(message, null);
        }

        public MessageNode(Message message, MessageNode next) {
            this.message = message;
            this.next = next;
        }
    }

    private int numMessages;
    private MessageNode messageList;

    /**
     * Default Constructor -- Create an empty collection (one with zero messages).
     */
    public Database() {
        numMessages = 0;
        messageList = null;
    }

    /**
     * isEmpty -- Returns true if the database is empty (contains no messages)
     */
    public boolean isEmpty() {
        return numMessages == 0;
    }


    /**
     * getNumMessages -- Return the total number of Messages in the collection.
     */
    public int getNumMessages() {
        return numMessages;
    }

    /**
     * retrieve(int index)
     * Purpose:  returns the message at the specified index in the collection,
     *           throw exception if index is bad.
     * @param  index - the index of the desired message; using zero-based indexing
     * @return message - the message at the specified index
     */
    public Message retrieve(int index) {
        if (index < 0 || index >= numMessages) {
            throw new IllegalArgumentException("Invalid index");
        }
        MessageNode current = messageList;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.message;
    }

    /**
     * contains(int ts)
     * Purpose: Returns true if the database contains a message with the given timestamp
     * @param ts - the timestamp we are supposed to check
     * @return bool - true if such a message exists, otherwise false
     */
    public boolean contains(int ts) {
        MessageNode current = messageList;
        for (int i = 0; current != null; current = current.next) {
            if (current.message.sameTime(ts)) {
                return true;
            }
        }
        return false;
    }

    /**
     * count(String sender)
     * Purpose:    Returns the number of messages from a specified sender
     * @param sender - the author of the messages we are to count
     * @return int - the number of messages from the specified sender
     */
    public int count(String sender) {
        int count = 0;
        MessageNode current = messageList;
        for (int i = 0; current != null; current = current.next) {
            if (current.message.getUser().equals(sender)) {
                count++;
            }
        }
        return count;
    }

    /**
     * toString()
     * Purpose:    Return a string of all messages
     * @return     String containing all the messages in sorted order with each & every
     *             message followed immediately by a newline character.
     *             Returns an empty string if the collection is empty
     */
    public String toString() {
        StringBuilder messageString = new StringBuilder();
        MessageNode current = messageList;
        for (int i = 0; current!= null; current = current.next) {
            messageString.append(current.message.toString()).append("\n");
        }
        return messageString.toString();
    }

    /**
     * toString(String sender)
     * Purpose:    Return a string of all messages sent by the specified sender
     * @param sender - the author of the messages we are supposed to return
     * @return string - containing all the messages from the specified author, in sorted order,
     *    with each message followed immediately by a newline character.
     */
    public String toString(String sender) {
        StringBuilder messageString = new StringBuilder();
        MessageNode current = messageList;
        for (int i = 0; current!= null; current = current.next) {
            if (current.message.getUser().equals(sender)) {
                messageString.append(current.message.toString()).append("\n");
            }
        }
        return messageString.toString();
    }

    /**
     * toString(int ts)
     * Purpose:  Return a string of all messages for a given timestamp
     * @param ts - the timestamp we are supposed to check for match
     * @return string - containing all the matching messages, in sorted order,
     *        each message followed immediately by a newline character.
     */
    public String toString(int ts) {
        StringBuilder messageString = new StringBuilder();
        MessageNode current = messageList;
        for (int i = 0; current!= null; current = current.next) {
            if (current.message.sameTime(ts)) {
                messageString.append(current.message.toString()).append("\n");
            }
        }
        return messageString.toString();
    }

    /**
     * toString(int ts1, int ts2)
     * Purpose:    Return a string of all messages in a range of two given timestamps
     * @param ts1 -- a timestamp
     * @param ts2 -- a timestamp
     * @return  string containing all the matching messages, in sorted order,
     *      each message followed immediately by a newline character.
     */
    public String toString(int ts1, int ts2) {
        StringBuilder messageString = new StringBuilder();
        int minTimeStamp = Math.min(ts1,ts2);
        int maxTimeStamp = Math.max(ts1,ts2);
        MessageNode current = messageList;
        for (int i = 0; current!= null; current = current.next) {
            int timeStamp = current.message.getTimestamp();
            if (timeStamp >= minTimeStamp && timeStamp <= maxTimeStamp) {
                messageString.append(current.message.toString()).append("\n");
            }
        }
        return messageString.toString();
    }

    /**
     * equals(Object other)
     * Purpose:  compare two objects for equality
     * @param other -- an object
     * @return true if the two Database objects are equal, otherwise false
     */
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Database otherDatabase) {
            if (this.numMessages != otherDatabase.numMessages) {
                return false;
            }
            MessageNode current = this.messageList;
            MessageNode otherCurrent = otherDatabase.messageList;
            for (int i = 0; current != null; current = current.next,
                    otherCurrent = otherCurrent.next) {
                if (!current.message.equals(otherCurrent.message)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * insert(Message)
     * Purpose: add/insert a message to the collection of message objects
     * @param  message - the message to be added
     * @return int - the index position of the inserted message
     */
    public int insert(Message message) {
        MessageNode newNode = new MessageNode(message);
        if (isEmpty()) {
            messageList = newNode;
            numMessages++;
            return 0;
        }
        MessageNode current = messageList;
        MessageNode prev = null;
        int index = 0;
        while (current != null && message.getTimestamp() >=
                current.message.getTimestamp()) {
            prev = current;
            current = current.next;
            index++;
        }
        if (prev != null) {
            newNode.next = current;
            prev.next = newNode;
        } else {
            newNode.next = messageList;
            messageList = newNode;
        }
        numMessages++;
        return index;
    }

    /**
     * deleteMessages()
     * Purpose: Deletes all messages from the collection
     * @return  int - the number of messages deleted
     */
    public int deleteMessages() {
        int count = numMessages;
        numMessages = 0;
        messageList = null;
        return count;
    }

    /**
     * deleteMessages(String sender)
     * Purpose:    Delete all messages from the specified sender
     * @param  sender - author of messages to be deleted, comparison is case-sensitive
     * @return number of messages deleted (int value)
     */
    public int deleteMessages(String sender) {
        MessageNode current = messageList;
        MessageNode prev = null;
        int count = 0;
        while (current != null) {
            if (current.message.getUser().equals(sender)) {
                if (prev != null) {
                    prev.next = current.next;
                } else {
                    messageList = current.next;
                }
                current = current.next;
                count++;
                numMessages--;
            } else {
                prev = current;
                current = current.next;
            }
        }
        return count;
    }

    /**
     * deleteMessages(int ts)
     * Purpose:    Deletes all messages whose timestamps are earlier than the parameter ts
     * @param  ts - the specified timestamp
     * @return the number of messages deleted (int value)
     */
    public int deleteMessages(int ts) {
        MessageNode current = messageList;
        MessageNode prev = null;
        int count = 0;
        while (current != null) {
            if (current.message.getTimestamp() < ts) {
                if (prev != null) {
                    prev.next = current.next;
                } else {
                    messageList = current.next;
                }
                current = current.next;
                count++;
                numMessages--;
            } else {
                prev = current;
                current = current.next;
            }
        }
        return count;
    }

    /**
     * deleteMessages(int ts1, int ts2)
     * Purpose:    Deletes all messages between a range of two given timestamps
     * @param ts1 - one timestamp
     * @param ts2 - the other timestamp
     * @return  number of messages deleted (int value)
     */
    public int deleteMessages(int ts1, int ts2) {
        MessageNode current = messageList;
        MessageNode prev = null;
        int count = 0;
        while (current != null) {
            int timestamp = current.message.getTimestamp();
            if ((timestamp >= Math.min(ts1, ts2)) && (timestamp <= Math.max(ts1, ts2))) {
                if (prev != null) {
                    prev.next = current.next;
                } else {
                    messageList = current.next;
                }
                current = current.next;
                count++;
                numMessages--;
            } else {
                prev = current;
                current = current.next;
            }
        }
        return count;
    }

    /**
     * clone() -- Return a new database object that is a clone of the 'this' object.
     * The clone should have its own list (of the same size) and contain all the messages
     * of 'this' object.
     */
    public Database clone() {
        Database clonedDatabase = new Database();
        MessageNode current = messageList;
        while (current != null) {
            clonedDatabase.insert(new Message(current.message.getUser(),
                    current.message.getText(), current.message.getTimestamp()));
            current = current.next;
        }
        return clonedDatabase;
    }

    /**
     * merge(Database) - Merge a received message collection into this collection
     * @param otherDatabase - the message collection to be merged
     */
    public void merge(Database otherDatabase) {
        MessageNode current = otherDatabase.messageList;
        while (current != null) {
            this.insert(new Message(current.message.getUser(), current.message.getText(),
                    current.message.getTimestamp()));
            current = current.next;
        }
    }
}


