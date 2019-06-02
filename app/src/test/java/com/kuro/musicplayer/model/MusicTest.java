package com.kuro.musicplayer.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MusicTest {
    Music m1 = new Music();
    Music m2 = new Music();

    @Before
    public void setUp() throws Exception {
        m1.setId(1);
        m2.setId(2);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testEquals() {
        System.out.println(m1.equals(m2));
        System.out.println(m2.equals(m1));

        m2.setId(1);
        System.out.println(m1.equals(m2));
        System.out.println(m2.equals(m1));

        m1.setId(null);
        m1.setMusicName("a");
        m1.setMusician("ab");
        m2.setMusicName("a");
        m2.setMusician("ab");
        System.out.println(m1.equals(m2));
        System.out.println(m2.equals(m1));
    }
}