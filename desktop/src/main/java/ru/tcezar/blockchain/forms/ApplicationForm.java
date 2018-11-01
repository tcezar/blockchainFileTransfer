package ru.tcezar.blockchain.forms;

import ru.tcezar.blockchain.Member;
import ru.tcezar.blockchain.api.IMember;
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

    private SendFileForm sendFileForm;
    private ListOfMembers listOfMembers;
    private Member member;

    public ApplicationForm(Member member) throws GeneralSecurityException {
        this.member = member;

        listOfMembers = new ListOfMembers(member);

        Thread threadTransport = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    JLabel jLabel = new JLabel(listOfMembers.getMembers());
                    tabbedPane1.setComponentAt(0, jLabel);
                    try {
                        Thread.sleep(30000l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        threadTransport.setDaemon(true);
        threadTransport.start();

        //Свойства формы по-умолчанию
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(mainPanel);

        JLabel jLabel = new JLabel(listOfMembers.getMembers());

        tabbedPane1.setComponentAt(0, jLabel);

        ICryptoUtils cryptoUtils = new CryptoUtils();

        setTitle("Участник №" + cryptoUtils.generateKeys().getPublicKey().hashCode()); //TODO заменить на ключ, который будет считываться с файла конфигурации

        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                switch (tabbedPane1.getSelectedIndex()) {
                    case 1:
                        sendFileForm = new SendFileForm();
                        JLabel jLabel = new JLabel(sendFileForm.getFile().getAbsolutePath() + sendFileForm.getFile().getName());
                        tabbedPane1.setComponentAt(1, jLabel);
                        JButton jButton = new JButton("Отправить");
                        jButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                //TODO отправить файл sendFileForm.getFile();
                            }
                        });
                        tabbedPane1.setComponentAt(1, jButton);
                        break;
                }

            }
        };

        tabbedPane1.addChangeListener(changeListener);

    }

}
