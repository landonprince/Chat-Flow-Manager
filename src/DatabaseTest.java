// By: Landon Prince (5/9/2024)

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {
    @Test
    public void testDefaultConstructor() {
        Database db = new Database();
        assertEquals(0, db.getNumMessages());
        assertEquals("", db.toString());
    }

    @Test
    public void testIsEmpty() {
        Database db = new Database();
        assertTrue(db.isEmpty());
        db.insert(new Message("User1", "Message1", 1));
        assertFalse(db.isEmpty());
    }

    @Test
    public void testGetNumTweets() {
        Database db = new Database();
        assertEquals(0, db.getNumMessages());
        db.insert(new Message("User1", "Message1", 1));
        assertEquals(1, db.getNumMessages());
        db.insert(new Message("User1", "Message1", 1));
        assertEquals(2, db.getNumMessages());
    }

    @Test
    public void testRetrieve() {
        Database db = new Database();
        db.insert(new Message("User1", "Message1", 1));
        db.insert(new Message("User2", "Message2", 2));
        Message retrievedTweet1 = db.retrieve(0);
        assertEquals("User1", retrievedTweet1.getUser());
        assertEquals("Message1", retrievedTweet1.getText());
        assertEquals(1, retrievedTweet1.getTimestamp());
        db.insert(new Message("User3", "Message3", 2));
        Message retrievedTweet2 = db.retrieve(1);
        assertEquals("User2", retrievedTweet2.getUser());
        assertEquals("Message2", retrievedTweet2.getText());
        assertEquals(2, retrievedTweet2.getTimestamp());
        assertThrows(IllegalArgumentException.class, () -> db.retrieve(3));
    }

    @Test
    public void testContains() {
        Database db = new Database();
        db.insert(new Message("User1", "Message1", 1));
        db.insert(new Message("User2", "Message2", 2));
        assertTrue(db.contains(1));
        assertFalse(db.contains(3));
    }

    @Test
    public void testCount() {
        Database db = new Database();
        db.insert(new Message("User1", "Message1", 1));
        db.insert(new Message("User2", "Message2", 2));
        db.insert(new Message("User1", "Message3", 3));
        assertEquals(2, db.count("User1"));
        assertEquals(1, db.count("User2"));
        assertEquals(0, db.count("User3"));
    }

    @Test
    public void testToString() {
        Database db = new Database();
        db.insert(new Message("User2", "Message2", 2));
        db.insert(new Message("User1", "Message1", 1));
        String expected = "User1: Message1 (sent at 1)\nUser2: Message2 (sent at 2)\n";
        assertEquals(expected, db.toString());
    }

    @Test
    public void testToStringBySender() {
        Database db = new Database();
        db.insert(new Message("User2", "Message2", 2));
        db.insert(new Message("User1", "Message1", 1));
        db.insert(new Message("User1", "Message3", 3));
        String expected = "User1: Message1 (sent at 1)\nUser1: Message3 (sent at 3)\n";
        assertEquals(expected, db.toString("User1"));
    }

    @Test
    public void testToStringByTimestamp() {
        Database db = new Database();
        db.insert(new Message("User2", "Message2", 2));
        db.insert(new Message("User1", "Message1", 1));
        db.insert(new Message("User1", "Message3", 1));
        String expected = "User1: Message1 (sent at 1)\nUser1: Message3 (sent at 1)\n";
        assertEquals(expected, db.toString(1));
    }

    @Test
    public void testToStringByTimestampRange() {
        Database db = new Database();
        db.insert(new Message("User2", "Message2", 2));
        db.insert(new Message("User1", "Message1", 1));
        db.insert(new Message("User1", "Message3", 3));
        db.insert(new Message("User1", "Message4", 4));
        String expected = "User1: Message1 (sent at 1)\nUser2: Message2 (sent at 2)\nUser1: Message3 (sent at 3)\nUser1: Message4 (sent at 4)\n";
        assertEquals(expected, db.toString(1, 4));
        String expected2 = "User2: Message2 (sent at 2)\nUser1: Message3 (sent at 3)\n";
        assertEquals(expected2, db.toString(2, 3));
    }

    @Test
    public void testToString_EmptyString() {
        Database db = new Database();
        assertEquals("", db.toString());
        assertEquals("", db.toString("User1"));
        assertEquals("", db.toString(2));
        assertEquals("", db.toString(1,3));
    }

    @Test
    public void testEquals() {
        Database db = new Database();
        db.insert(new Message("User2", "Message2", 2));
        db.insert(new Message("User1", "Message1", 1));
        Database otherDb = new Database();
        otherDb.insert(new Message("User2", "Message2", 2));
        otherDb.insert(new Message("User1", "Message1", 1));
        assertEquals(db, otherDb);
        otherDb.insert(new Message("User3", "Message3", 3));
        assertNotEquals(db, otherDb);
    }

    @Test
    public void testInsert() {
        Database db = new Database();
        int index1 = db.insert(new Message("User2", "Message2", 2));
        assertEquals(0, index1);
        int index2 = db.insert(new Message("User1", "Message1", 2));
        assertEquals(1, index2);
        int index3 = db.insert(new Message("User3", "Message3", 1));
        assertEquals(0, index3);
        int index4 = db.insert(new Message("User4", "Message4", 3));
        assertEquals(3, index4);
        assertEquals("User3: Message3 (sent at 1)\nUser2: Message2 (sent at 2)\nUser1: Message1 (sent at 2)\nUser4: Message4 (sent at 3)\n", db.toString());
    }

    @Test
    public void testNoInsertException() {
        Database db = new Database();
        Message message1 = new Message("User1","Message1",0);
        for (int i = 0; i < 300; i++) {
            assertEquals(i, db.insert(message1));
        }
        assertEquals(300, db.getNumMessages());
    }

    @Test
    public void testDeleteMessages() {
        Database db = new Database();
        db.insert(new Message("user1", "Message1", 1));
        db.insert(new Message("user2", "Message2", 2));
        db.insert(new Message("user1", "Message3", 3));
        db.insert(new Message("user3", "Message4", 4));
        int deleted = db.deleteMessages();
        assertEquals(4, deleted);
        assertTrue(db.isEmpty());
    }

    @Test
    public void testDeleteMessagesBySender() {
        Database db = new Database();
        db.insert(new Message("user1", "Message1", 1));
        db.insert(new Message("user2", "Message2", 2));
        db.insert(new Message("user1", "Message3", 3));
        db.insert(new Message("user3", "Message4", 4));
        int deleted = db.deleteMessages("user1");
        assertEquals(2, deleted);
        assertEquals(2, db.getNumMessages());
        assertEquals("user2", db.retrieve(0).getUser());
        assertEquals("user3", db.retrieve(1).getUser());
    }

    @Test
    public void testDeleteMessagesByTimestamp() {
        Database db = new Database();
        db.insert(new Message("user1", "Message1", 1));
        db.insert(new Message("user2", "Message2", 2));
        db.insert(new Message("user1", "Message3", 3));
        db.insert(new Message("user3", "Message4", 4));
        int deleted = db.deleteMessages(3);
        assertEquals(2, deleted);
        assertEquals(2, db.getNumMessages());
        assertEquals(3, db.retrieve(0).getTimestamp());
        assertEquals(4, db.retrieve(1).getTimestamp());
    }

    @Test
    public void testDeleteMessagesByTimestampRange() {
        Database db = new Database();
        db.insert(new Message("user1", "Message1", 1));
        db.insert(new Message("user2", "Message2", 2));
        db.insert(new Message("user1", "Message3", 3));
        db.insert(new Message("user3", "Message4", 4));
        int deleted = db.deleteMessages(2, 3);
        assertEquals(2, deleted);
        assertEquals(2, db.getNumMessages());
        assertEquals(1, db.retrieve(0).getTimestamp());
        assertEquals(4, db.retrieve(1).getTimestamp());
    }

    @Test
    public void testClone() {
        Database db = new Database();
        Message message1 = new Message("user1", "Message1", 1001);
        Message message2 = new Message("user2", "Message2", 1002);
        db.insert(message1);
        db.insert(message2);
        Database clonedDb = db.clone();
        assertEquals(db.getNumMessages(), clonedDb.getNumMessages());
        for (int i = 0; i < db.getNumMessages(); i++) {
            assertEquals(db.retrieve(i), clonedDb.retrieve(i));
        }
        Message message3 = new Message("user3", "New message", 1003);
        db.insert(message3);
        assertEquals(3, db.getNumMessages());
        assertEquals(2, clonedDb.getNumMessages());
    }

    @Test
    public void testMerge() {
        Database db1 = new Database();
        Message message1 = new Message("user1", "Message1", 1001);
        Message message2 = new Message("user2", "Message2", 1002);
        db1.insert(message1);
        db1.insert(message2);
        Database db2 = new Database();
        Message message3 = new Message("user3", "Message3", 1003);
        Message message4 = new Message("user4", "Message4", 1001);
        Message message5 = new Message("user5", "Message5", 1004);
        db2.insert(message3);
        db2.insert(message4);
        db2.insert(message5);
        db1.merge(db2);
        assertEquals(5, db1.getNumMessages());
        assertEquals(message1, db1.retrieve(0));
        assertEquals(message4, db1.retrieve(1));
        assertEquals(message2, db1.retrieve(2));
        assertEquals(message3, db1.retrieve(3));
        assertEquals(message5, db1.retrieve(4));
        assertEquals(3, db2.getNumMessages());
    }
}
