package ru.tcezar.blockchain.transport.tcp;

import java.io.IOException;
import java.io.InputStream;

abstract class AbstractTCP {

    protected byte[] readAllBytesFromInputStream(InputStream inputStream) throws IOException {
        byte[] resultBuff = new byte[0];
        byte[] buff = new byte[1024*1024];
        int k = -1;
        while((k = inputStream.read(buff, 0, buff.length)) > -1) {
            byte[] tbuff = new byte[resultBuff.length + k]; // temp buffer size = bytes already read + bytes last read
            System.arraycopy(resultBuff, 0, tbuff, 0, resultBuff.length); // copy previous bytes
            System.arraycopy(buff, 0, tbuff, resultBuff.length, k);  // copy current lot
            resultBuff = tbuff; // call the temp buffer as your result buff
        }
        System.out.println(resultBuff.length + " bytes read.");
        return resultBuff;
    }

}
