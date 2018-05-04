package ru.ifmo.se;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Set;

import static org.junit.Assert.*;


public class Tests {
    private App app;

    @Before
    public void createApp(){
        app = new App();
    }

    @Test
    public void testFileGetting() {
        App.filemaker();
        File file = new File(System.getProperty("user.dir") +"\\COLLECTION");
        assertEquals(file, App.getFile());
    }

    @Test
    public void testLoading(){
        App.setFile(System.getProperty("user.dir") + "\\files\\testLoadingFile");
        app.load();
        Set set = app.getCollection();
        Person person = new Known("Andy","Humburg");
        assertEquals(person, set.toArray()[0]);
    }

    @Test
    public void testLoadingEmpty(){
        App.setFile(System.getProperty("user.dir") + "\\files\\testLoadingEmptyFile");
        app.load();
        Set set = app.getCollection();
        assertTrue(set.isEmpty());
    }

    @Test
    public void testLoadingFromNotExisingFile(){
        App.setFile(System.getProperty("user.dir") + "\\files\\NotExistingFile");
        app.load();
        Set set = app.getCollection();
        assertTrue(set.isEmpty());
    }

    @Test
    public void testSaving(){
        app.addObject("{\"name\":\"Andy\",\"last_name\":\"Humburg\"}");
        App.setFile(System.getProperty("user.dir") + "\\files\\testSavingFile");
        app.save();
        app.clear();
        app.load();
        Set set = app.getCollection();
        Person person = new Known("Andy","Humburg");
        assertEquals(person, set.toArray()[0]);
        File file = App.getFile();
        file.delete();
    }

    @Test
    public void testSavingEmpty(){
        App.setFile(System.getProperty("user.dir") + "\\files\\testSavingFile");
        app.save();
        app.clear();
        app.load();
        Set set = app.getCollection();
        assertTrue(set.isEmpty());
        File file = App.getFile();
        file.delete();
    }

    @Test
    public void testAdding(){
        App.setFile(System.getProperty("user.dir") + "\\files\\testLoadingFile");
        app.load();
        Person person = new Known("Ben","Black");
        app.addObject("{\"name\":\"Ben\",\"last_name\":\"Black\"}");
        Set set = app.getCollection();
        assertTrue(set.contains(person));
    }

    @Test
    public void testAddingNull(){
        app.addObject("{}");
        Set set = app.getCollection();
        assertTrue(set.isEmpty());
    }

    @Test
    public void testRemovingDiffBigger(){
        app.addObject("{\"name\":\"Andy\",\"last_name\":\"Humburg\"}");
        Person person = new Known("Andy","Humburg");
        app.removeGreater("{\"name\":\"Ben\",\"last_name\":\"Creed\"}");
        Set set = app.getCollection();
        assertFalse(set.contains(person));
    }

    @Test
    public void testRemovingDiffSmaller(){
        app.addObject("{\"name\":\"Ben\",\"last_name\":\"Dlack\"}");
        Person person = new Known("Ben","Dlack");
        app.removeGreater("{\"name\":\"Ben\",\"last_name\":\"Creed\"}");
        Set set = app.getCollection();
        assertTrue(set.contains(person));
    }

    @Test
    public void testRemovingNoLastNameSet(){
        app.addObject("{\"name\":\"Zack\",\"last_name\":\"Fillips\"}");
        Person person = new Known("Zack","Fillips");
        app.removeGreater("{\"name\":\"Ben\"}");
        Set set = app.getCollection();
        assertTrue(set.contains(person));
    }

    @Test
    public void testRemovingSame(){
        app.addObject("{\"name\":\"Zack\",\"last_name\":\"Fillips\"}");
        Person person = new Known("Zack","Fillips");
        app.removeGreater("{\"name\":\"Zack\",\"last_name\":\"Fillips\"}");
        Set set = app.getCollection();
        assertTrue(set.contains(person));
    }
}
