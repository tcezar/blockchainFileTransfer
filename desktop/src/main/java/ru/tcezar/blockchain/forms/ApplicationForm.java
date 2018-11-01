package ru.tcezar.blockchain.forms;

import ru.tcezar.crypto.api.ICryptoUtils;
import ru.tcezar.crypto.impl.CryptoUtils;

import javax.swing.*;
import java.security.GeneralSecurityException;

public class ApplicationForm extends JFrame {

    private JPanel mainPanel;
    private JButton sendButton;
    private JButton membersButton;
    private JButton historyButton;
    private JPanel mainControl;
    private JLabel idMember;

    public ApplicationForm() throws GeneralSecurityException {

        super("Task 12 / WorkOnDon");

        //По-умолчанию на весь экран
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(mainPanel);

        ICryptoUtils cryptoUtils = new CryptoUtils();

        idMember.setText("Участник №" + cryptoUtils.generateKeys().getPublicKey().hashCode());

    }

}
