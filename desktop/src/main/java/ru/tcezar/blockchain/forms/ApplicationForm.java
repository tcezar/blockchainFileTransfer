package ru.tcezar.blockchain.forms;

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

    public ApplicationForm() throws GeneralSecurityException {

        listOfMembers = new ListOfMembers();

        //Свойства формы по-умолчанию
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(mainPanel);

        tabbedPane1.setComponentAt(0, listOfMembers.getMembers());

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
                    case 0:
                        tabbedPane1.setComponentAt(0, listOfMembers.getMembers());
                        break;
                }

            }
        };

        tabbedPane1.addChangeListener(changeListener);

    }

}
