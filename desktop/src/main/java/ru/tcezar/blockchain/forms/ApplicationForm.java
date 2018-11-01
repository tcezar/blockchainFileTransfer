package ru.tcezar.blockchain.forms;

import ru.tcezar.crypto.api.ICryptoUtils;
import ru.tcezar.crypto.impl.CryptoUtils;

import javax.swing.*;
import java.security.GeneralSecurityException;

public class ApplicationForm extends JFrame {

    private JPanel mainPanel;
    private JPanel mainControl;
    private JTabbedPane tabbedPane1;

    public ApplicationForm() throws GeneralSecurityException {

        //По-умолчанию на весь экран
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        add(mainPanel);

        ICryptoUtils cryptoUtils = new CryptoUtils();

        setTitle("Участник №" + cryptoUtils.generateKeys().getPublicKey().hashCode());

    }

}
