package ru.tcezar.blockchain.transport.listener.multicast;

import ru.tcezar.blockchain.api.IBlockChain;
import ru.tcezar.blockchain.api.IMember;
import ru.tcezar.blockchain.api.IMessage;
import ru.tcezar.blockchain.api.UID;
import ru.tcezar.blockchain.transport.api.INewMembersListener;
import ru.tcezar.blockchain.transport.messages.Message;
import ru.tcezar.blockchain.transport.udp.multicast.AbstractMulticastReceiver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static ru.tcezar.blockchain.transport.protocols.NewMembersMessageCommands.HELLO;

/**
 * Created by Michael on 01.11.2018.
 */
public class NewMembersListener extends AbstractMulticastReceiver implements INewMembersListener {

    private Map<UID, Integer> addressBook = new ConcurrentHashMap<>();
    private IBlockChain blockChain = null;
    private IMember member;

    protected NewMembersListener(MulticastSocket socket, InetAddress group, IMember member) throws IOException {
        super(socket, group);
        this.member = member;
    }

    public NewMembersListener(String socketAddr, int socketPort, IMember member) throws IOException {
        super(socketAddr, socketPort);
        this.member = member;
    }

    @Override
    public void setBlockChain(IBlockChain blockChain) {
        this.blockChain = blockChain;
    }

    @Override
    public void setMembers(Map<UID, Integer> members) {
        this.addressBook = members;
    }

    private void addMember(UID uid, Integer chainSize) {
        if (!addressBook.containsKey(uid) && !member.getUID().equals(uid)) {
            this.addressBook.put(uid, chainSize);
        }
    }

    @Override
    protected boolean processMessage(IMessage message) {
//        System.out.println("получено сообщение:" + message);
        Message<Integer> newMemberMessage = (Message) message;
        Integer chainSize = newMemberMessage.getMessage();
        if (HELLO.equals(newMemberMessage.getTheme())) {
//            System.out.println(String.format("Получен HELLO! %s", newMemberMessage.getSender()));
            addMember(newMemberMessage.getSender(), chainSize);
            return true;
        }
        return false;
    }


    private boolean newChainIsMoreActual(Integer size) {
        return false;
    }

    @Override
    protected boolean isStoping(IMessage recivedMessage) {
        return false;
    }

    @Override
    protected void errors(IMessage recivedMessage) {
    }
}
