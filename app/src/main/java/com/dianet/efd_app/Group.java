package com.dianet.efd_app;

/**
 * Created by BELLO on 16/02/2017.
 */

import java.util.ArrayList;
import java.util.List;

public class Group {

    public String string;
    public final List<String> children = new ArrayList<String>();

    public Group(String string) {
        this.string = string;
    }
    public Group() {}

}
