package ru.tcezar.blockchain.forms;

import ru.tcezar.blockchain.BlockChain;
import ru.tcezar.blockchain.Transport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.InetAddress;
import java.util.Map;
import java.util.Set;

public class FileTransferForm extends JFrame {
    private static Transport transport;
    private static BlockChain blockChain;

    public FileTransferForm() throws HeadlessException {
        super("BlockChainFileTransfer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalGlue());

//        final JLabel labelServer = new JLabel(getTransport().toString());
//        labelServer.setAlignmentX(CENTER_ALIGNMENT);
//        panel.add(labelServer);

        final JLabel labelFile = new JLabel("Выбранный файл");
        labelFile.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(labelFile);

        final JLabel labelRecipient = new JLabel("Выбранный получатель");
        labelRecipient.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(labelRecipient);

        final JLabel labelStatus = new JLabel("Статус передачи");
        labelStatus.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(labelStatus);

        panel.add(Box.createRigidArea(new Dimension(10, 10)));

        JButton buttonChoseFile = new JButton("Выбрать файл");
        buttonChoseFile.setAlignmentX(CENTER_ALIGNMENT);

        buttonChoseFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    labelFile.setText(file.getName());
                }
            }
        });

        panel.add(buttonChoseFile);

        JButton buttonChoiseRecipient = new JButton("Выбрать получателя");
        buttonChoiseRecipient.setAlignmentX(CENTER_ALIGNMENT);

        buttonChoiseRecipient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<InetAddress, Set<Integer>> recipientList = getTransport().getRecipientList();
                if (recipientList.isEmpty()) {
                    getTransport().scanRecipient();
                }
                if (recipientList.isEmpty()) {
                    labelRecipient.setText("Получатель плохой");
                } else {
                    // TODO вывести список получателей с возможностью выбрать одного
                }
            }
        });

        panel.add(buttonChoiseRecipient);

        JButton buttonSendFile = new JButton("Отправить файл получателю");
        buttonSendFile.setAlignmentX(CENTER_ALIGNMENT);

        buttonSendFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        panel.add(buttonSendFile);

        panel.add(Box.createVerticalGlue());
        getContentPane().add(panel);

        setPreferredSize(new Dimension(260, 220));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void run() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);
                new FileTransferForm();
            }
        });
    }

    public static Transport getTransport() {
        return transport;
    }

    public static void setTransport(Transport transport) {
        if (FileTransferForm.transport == null) {
            FileTransferForm.transport = transport;
        } else {
            throw new RuntimeException("Transport has inited");
        }
    }

    public static void setBlockChain(BlockChain blockChain) {
        FileTransferForm.blockChain = blockChain;
    }
}
