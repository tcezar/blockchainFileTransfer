package ru.tcezar.blockchain.bitcoinj;

import org.bitcoinj.core.*;
import org.bitcoinj.net.ClientConnectionManager;
import org.bitcoinj.wallet.Protos;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.listeners.AbstractWalletEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;

public class Demo {
    Logger logger = LoggerFactory.getLogger(getClass());

//    public static void main(String[] args) {
//        NetworkParameters params =
//                NetworkParameters.testNet();
////        NetworkParameters params =
////                NetworkParameters.prodNet();
//        File walletFile = new File("coins.dat");
//        Wallet wallet=null;
//        try {
//            wallet = Wallet.loadFromFile(walletFile);
//        } catch (IOException e) {
//            wallet = new Protos.Wallet(params);
//            wallet.addKey(new ECKey());
//            wallet.saveToFile(walletFile);
//        }
//        System.out.println(wallet);
//        Address a1 =
//                new Address(params, "1HaSchNbFfLF8MJw41QNie7RPePPqdTozb");
//        ECKey key = new ECKey();
//        byte[] publicKey = key.getPubKey();
//        Address addr = key.toAddress(params);
//        System.out.println(addr.toString());
//        NetworkParameters blockStore = NetworkParameters.testNet();
//        ClientConnectionManager chain = ;
//        final PeerGroup peerGroup =
//                new PeerGroup(blockStore, params, chain);
//        peerGroup.setUserAgent("MyApp", "1.2");
//        peerGroup.addWallet(wallet);
//        peerGroup.addAddress(
//                new PeerAddress(InetAddress.getLocalHost()));
//        peerGroup.start();
//        wallet.addEventListener(
//                new AbstractWalletEventListener() {
//                    public void onCoinsReceived(
//                            Wallet w,
//                            Transaction tx,
//                            BigInteger prevBalance,
//                            BigInteger newBalance) {
//                        // Running on a peer thread.
//                    }
//                });
//        wallet.addEventListener(
//                new AbstractWalletEventListener() {
//                    public void onCoinsReceived(
//                            Wallet w,
//                            Transaction tx,
//                            BigInteger prevBalance,
//                            BigInteger newBalance) {
//                        // Running on a peer thread.
//                    }
//                });
//    }
}
