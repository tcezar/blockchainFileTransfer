package ru.tcezar.blockchain.forms;

import javax.swing.*;
import java.io.File;

public class SendFileForm {

    private File file;

    public void chooseFile() {
        JFileChooser fileopen = new JFileChooser();
        int ret = fileopen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            file = fileopen.getSelectedFile();
        }
    }

    public File getFile() {
        return file;
    }
}
