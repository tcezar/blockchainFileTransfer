package ru.tcezar.blockchain.forms;

import ru.tcezar.blockchain.Member;
import ru.tcezar.blockchain.api.UID;
import ru.tcezar.crypto.api.ICryptoUtils;
import ru.tcezar.crypto.impl.CryptoUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.GeneralSecurityException;

public class ApplicationForm extends JFrame {

    private JPanel mainPanel;
    private JPanel mainControl;
    private JTabbedPane tabbedPane1;
    private JButton sendFileButton;
    private JButton chseFileButton;
    private JList listMembers;

    private SendFileForm sendFileForm = new SendFileForm();
    private Member member;
    private ICryptoUtils cryptoUtils;

    private DefaultListModel splitMembers() {
        DefaultListModel result = new DefaultListModel();

        for(UID uid : member.getMembers()) {
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
        setTitle("Участник №" + member.getUID()); //TODO заменить на ключ, который будет считываться с файла конфигурации

        listMembers.setModel(splitMembers());

//        ChangeListener changeListener = new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent e) {
//
//                switch (tabbedPane1.getSelectedIndex()) {
//                    case 1:
//                        sendFileForm.chooseFile();
//
//                        JLabel jLabel = new JLabel(sendFileForm.getFile().getAbsolutePath() + sendFileForm.getFile().getName());
//                        tabbedPane1.setComponentAt(1, jLabel);
//                        JButton jButton = new JButton("Отправить");
//                        jButton.addActionListener(new ActionListener() {
//                            @Override
//                            public void actionPerformed(ActionEvent e) {
//                                //TODO отправить файл sendFileForm.getFile();
//                            }
//                        });
//                        tabbedPane1.setComponentAt(1, jButton);
//                        break;
//                }
//
//            }
//        };
//
//        tabbedPane1.addChangeListener(changeListener);

    }

}
