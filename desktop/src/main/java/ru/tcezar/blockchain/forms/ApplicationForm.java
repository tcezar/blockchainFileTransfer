package ru.tcezar.blockchain.forms;

import ru.tcezar.blockchain.Member;
import ru.tcezar.blockchain.api.UID;
import ru.tcezar.blockchain.transport.MulticastPublisher;
import ru.tcezar.blockchain.transport.messages.Message;
import ru.tcezar.blockchain.transport.servers.ServerFileTransfer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ApplicationForm extends JFrame {

    private JPanel mainPanel;
    private JPanel mainControl;
    private JTabbedPane tabbedPane1;
    private JList listMembers;

    private SendFileForm sendFileForm = new SendFileForm();
    private Member member;

    private Map<String, UID> dataStorage = new HashMap<>();

    private DefaultListModel splitMembers() {
        dataStorage.clear();

        DefaultListModel result = new DefaultListModel();

        for (UID uid : member.getMembers().keySet()) {
            result.addElement("Участник №" + uid.toString());
        }

        return result;
    }

    public ApplicationForm(Member member) throws GeneralSecurityException {
        this.member = member;

        //Свойства формы по-умолчанию
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(mainPanel);
        setTitle("Участник №" + member.getUID());
        listMembers.setModel(splitMembers());

        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                switch (tabbedPane1.getSelectedIndex()) {
                    case 0:
                        listMembers.setModel(splitMembers());
                    case 1:
                        try {
                            member.startFileTransfer(new ServerFileTransfer(
                                    4455,
                                    member,
                                    sendFileForm.getFile(),
                                    getCheckedMemebers()));
                            for (UID uid : getCheckedMemebers()) {
                                Message sendFile = new Message(
                                        uid,
                                        member.getUID(),
                                        "SEND FILE",
                                        sendFileForm.getFile().getName()
                                );
                                if (member.getBlockChain().generateNextBlock(sendFile)) {
                                    new MulticastPublisher("230.0.0.0", 2002).multicast(
                                            new Message(
                                                    uid,
                                                    member.getUID(),
                                                    "NEXT BLOCK",
                                                    member.getBlockChain().getLatestBlock()
                                            )
                                    );
                                }

                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        break;
                    case 2:
                        //TODO добавить историю
                        break;
                }
            }
        };
        tabbedPane1.addChangeListener(changeListener);
    }

    private Set<UID> getCheckedMemebers() {
        return member.getMembers().keySet();
    }

}
