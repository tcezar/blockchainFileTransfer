package ru.tcezar.blockchain.forms;

import ru.tcezar.blockchain.Member;
import ru.tcezar.blockchain.api.UID;

import javax.swing.*;

public class ListOfMembers {

    private static String members;


    public ListOfMembers(Member member) {
//        updateMembers();

        String result = "";

        for (UID uid : member.getMembers()) {
            result += "Участник №" + uid.toString();
        }

    }

    public String getMembers() {
        return members;
    }

    public void updateMembers() {
//        DefaultListModel<String> list = new DefaultListModel<>();
//        String[] mems = TestUtils.getMembers().split(";");
//
//        for (int i = 0; i < mems.length; ++i) {
//            list.addElement(mems[i]);
//        }
//
//        members = new JList<>(mems);



    }

}
