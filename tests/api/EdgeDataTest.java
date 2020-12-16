package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeDataTest {

    @Test
    void getSrc() {
        EdgeData e=new EdgeData(0,1,3);
        assertEquals(0,e.getSrc());
    }

    @Test
    void getDest() {
        EdgeData e=new EdgeData(0,1,3);
        assertEquals(1,e.getDest());
    }

    @Test
    void getWeight() {
        EdgeData e=new EdgeData(0,1,3);
        assertEquals(3,e.getWeight());
    }

    @Test
    void getInfo() {
        EdgeData e=new EdgeData(0,1,3);
        assertEquals("",e.getInfo());
    }

    @Test
    void setInfo() {
        EdgeData e=new EdgeData(0,1,3);
        assertEquals("",e.getInfo());
        e.setInfo("t");
        assertEquals("t",e.getInfo());
    }

    @Test
    void getTag() {
        EdgeData e=new EdgeData(0,1,3);
        assertEquals(0,e.getTag());
    }

    @Test
    void setTag() {
        EdgeData e=new EdgeData(0,1,3);
        assertEquals(0,e.getTag());
        e.setTag(1);
        assertEquals(1,e.getTag());
    }
}