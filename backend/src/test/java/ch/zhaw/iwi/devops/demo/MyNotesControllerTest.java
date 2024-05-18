package ch.zhaw.iwi.devops.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MyNotesControllerTest {
    
    @Test
    void testCreate() {
        MyNotesController controller = new MyNotesController();
        var mynote = new MyNotes(1, "t", "d");
        controller.createMyNote(1, mynote);
        assertEquals(1, controller.count());
        assertEquals(1, controller.mynote().size());
    }
    @Test
    void testDelete() {
        MyNotesController controller = new MyNotesController();
        var mynote = new MyNotes(1, "title", "description");
        controller.createMyNote(1, mynote);

        controller.deleteMyNote(1);
        assertEquals(0, controller.count()); 
        assertTrue(controller.mynote().isEmpty());
    }

    @Test
    void testInitMethod() {
        MyNotesController controller = new MyNotesController();
        controller.init();
        assertEquals(3, controller.count(), "Should have initialized 3 notes");
    }

    @Test
    void testGetMyNoteExists() {
        MyNotesController controller = new MyNotesController();
        controller.init();
        MyNotes note = controller.getMyNote(1);
        assertNotNull(note, "Retrieved note should not be null");
        assertEquals("Ein wichtiger Gedanke", note.getTitle(), "Title should match the initialized data");
    }

    @Test
    void testGetMyNoteDoesNotExist() {
        MyNotesController controller = new MyNotesController();
        controller.init();
        MyNotes note = controller.getMyNote(99);
        assertNull(note, "Should return null for non-existent note");
    }

    @Test
    void testUpdateMyNote() {
        MyNotesController controller = new MyNotesController();
        var mynote = new MyNotes(1, "Updated Title", "Updated Description");
        controller.createMyNote(1, mynote);
        MyNotes updatedNote = controller.getMyNote(1);
        assertEquals("Updated Title", updatedNote.getTitle(), "Title should be updated");
        assertEquals("Updated Description", updatedNote.getDescription(), "Description should be updated");
    }

    @Test
    void testPingEndpoint() {
        MyNotesController controller = new MyNotesController();
        String response = controller.ping();
        assertTrue(response.contains("\"status\": \"ok\""), "Ping response should contain status ok");
        assertTrue(response.contains("\"userId\": \"admin\""), "Ping response should contain userId");
    }

    @Test
    void testTestEndpoint() {
        MyNotesController controller = new MyNotesController();
        String response = controller.test();
        assertEquals("MyNotes app is up and running!", response, "The response should match the expected message");
    }
}