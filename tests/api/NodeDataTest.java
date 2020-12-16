package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeDataTest {

    @Test
    void getKey() {
        NodeData n=new NodeData(0);
        assertEquals(0,n.getKey());
    }

    @Test
    void getLocation() {
        NodeData n=new NodeData(0);
        assertEquals(null,n.getLocation());
    }

    @Test
    void setLocation() {
        NodeData n=new NodeData(0);
        geo_location g=new GeoLocation(1,2,3);
        n.setLocation(g);
        assertEquals(g,n.getLocation());
    }

    @Test
    void getWeight() {
        NodeData n=new NodeData(0);
        assertEquals(0,n.getWeight());
    }

    @Test
    void setWeight() {
        NodeData n=new NodeData(0);
        assertEquals(0,n.getWeight());
        n.setWeight(2.5);
        assertEquals(2.5,n.getWeight());
    }

    @Test
    void getInfo() {
        NodeData n=new NodeData(0);
        assertEquals(null,n.getInfo());
    }

    @Test
    void setInfo() {
        NodeData n=new NodeData(0);
        assertEquals(null,n.getInfo());
        n.setInfo("test");
        assertEquals("test",n.getInfo());
    }

    @Test
    void getTag() {
        NodeData n=new NodeData(0);
        assertEquals(0,n.getTag());
    }

    @Test
    void setTag() {
        NodeData n=new NodeData(0);
        assertEquals(0,n.getTag());
        n.setTag(1);
        assertEquals(1,n.getTag());
    }
}