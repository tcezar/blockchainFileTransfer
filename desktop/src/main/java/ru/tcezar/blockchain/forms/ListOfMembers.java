package ru.tcezar.blockchain.forms;

import ru.tcezar.blockchain.Member;
import ru.tcezar.blockchain.api.UID;

import javax.swing.*;
import java.util.Set;

public class ListOfMembers {

    private static Set<UID> members;


    public ListOfMembers(Member member) {
//        updateMembers();
        members = member.getMembers();
    }

    public String getMembers() {
        String result = "";

        for (UID uid : members) {
            result += "Участник №" + uid.toString();
        }
        return result;
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
