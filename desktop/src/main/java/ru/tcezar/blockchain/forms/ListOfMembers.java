package ru.tcezar.blockchain.forms;

import javax.swing.*;

public class ListOfMembers {

    private JList<String> members;


    public ListOfMembers() {
        updateMembers();
    }

    public JList<String> getMembers() {
        return members;
    }

    public void updateMembers() {
        DefaultListModel<String> list = new DefaultListModel<>();
        String[] mems = TestUtils.getMembers().split(";");

        for (int i = 0; i < mems.length; ++i) {
            list.addElement(mems[i]);
        }

        members = new JList<>(mems);

    }

}
