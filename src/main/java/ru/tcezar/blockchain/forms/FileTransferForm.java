package ru.tcezar.blockchain.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileTransferForm extends JFrame {
    public FileTransferForm() throws HeadlessException {
        super("BlockChainFileTransfer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalGlue());

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

        JButton buttonChoiseRecipient=new JButton("Выбрать получателя");
        buttonChoiseRecipient.setAlignmentX(CENTER_ALIGNMENT);

        buttonChoiseRecipient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelRecipient.setText("Получатель плохой");
            }
        });

        panel.add(buttonChoiseRecipient);

        JButton buttonSendFile=new JButton("Отправить файл получателю");
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
}
