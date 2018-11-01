package ru.tcezar.blockchain.forms;

import javax.swing.*;

public class ApplicationForm extends JFrame {

    private JPanel mainPanel;
    private JButton button1;
    private JButton button2;
    private JButton button3;

    public ApplicationForm() {

        super("Task 12 / WorkOnDon");

        //По-умолчанию на весь экран
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        add(mainPanel);

    }

}
