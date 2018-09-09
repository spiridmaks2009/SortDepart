package com.company;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.hamcrest.core.Is.is;

public class MainTest {

    @org.junit.Test
    public void findParent() {
        String input = "K1\\SK1\\SSK2";
        String expect = "K1\\SK1\\SSK2";

        Main.Elem result = Main.findParent(input);
       assertThat(expect,is(result.depName));
    }
}