package ru.tcezar.blockchain.forms;

import ru.tcezar.blockchain.Member;
import ru.tcezar.blockchain.api.IBlock;
import ru.tcezar.blockchain.api.UID;
import ru.tcezar.blockchain.transport.MulticastPublisher;
import ru.tcezar.blockchain.transport.messages.Message;
import ru.tcezar.blockchain.transport.servers.ServerFileTransfer;
import ru.tcezar.blockchain.transport.utils.SerializationUtils;
import ru.tcezar.config.ConfigKeeper;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ApplicationForm extends JFrame {

    private JPanel mainPanel;
    private JPanel mainControl;
    private JTabbedPane tabbedPane1;
    private JList listMembers;
    private JList listChains;

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

    private DefaultListModel splitChains() {
        DefaultListModel result = new DefaultListModel();
        for (IBlock iBlock : member.getBlockChain().getBlockchain()) {
            result.addElement( iBlock.toString());
        }
        return result;
    }

    public ApplicationForm(Member member) throws GeneralSecurityException {
        super();
        this.member = member;

        //Свойства формы по-умолчанию
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(mainPanel);
        setTitle("Участник №" + member.getUID());

        Thread threadTransport = new Thread(() -> {
            while (true) {
                if(listMembers.getModel().getSize() != this.member.getMembers().size()){
                    listMembers.setModel(splitMembers());
                    listMembers.repaint();
                }
                try {
                    Thread.sleep(5000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadTransport.setDaemon(true);
        threadTransport.start();

        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                switch (tabbedPane1.getSelectedIndex()) {
                    case 0:
                        listMembers.setModel(splitMembers());
                        break;
                    case 1:
                        try {
                            sendFileForm.chooseFile();
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
                        listChains.setModel(splitChains());
                        break;
                }
            }
        };
        tabbedPane1.addChangeListener(changeListener);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                try {
                    OutputStream outputStream = new FileOutputStream(ConfigKeeper.blockchainFilepath);
                    outputStream.write(SerializationUtils.serializeObject(member.getBlockChain()));
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Завершаемся!");
                System.exit(0);
            }
        });
    }

    private Set<UID> getCheckedMemebers() {
        return member.getMembers().keySet();
    }

}
