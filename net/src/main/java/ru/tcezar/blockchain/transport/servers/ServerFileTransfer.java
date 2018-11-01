package ru.tcezar.blockchain.transport.servers;

import ru.tcezar.blockchain.api.IMember;
import ru.tcezar.blockchain.api.IMessage;
import ru.tcezar.blockchain.api.UID;
import ru.tcezar.blockchain.transport.api.IServerFileTranfer;
import ru.tcezar.blockchain.transport.messages.Message;
import ru.tcezar.blockchain.transport.tcp.AbstractTCPServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Set;

public class ServerFileTransfer extends AbstractTCPServer implements IServerFileTranfer {
    private File file;
    private Set<UID> members;
    private IMember member;

    public ServerFileTransfer(String addr, int port, IMember member, File file, Set<UID> members) throws UnknownHostException {
        super(addr, port, member);
        this.file = file;
        this.members = members;
        this.member = member;
    }

    public ServerFileTransfer(int port, IMember member, File file, Set<UID> members) throws UnknownHostException {
        super(member.getUID().addr, port, member);
        this.file = file;
        this.members = members;
        this.member = member;
    }

    @Override
    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    protected IMessage processMessage(IMessage message) {
        if (members.contains(message.getSender())
                && member.equals(message.getRecipient())
                && "GET FILE".equals(message.getTheme())) {
            try {
                return new Message(
                        message.getSender(),
                        member.getUID(),
                        "SEND FILE",
                        readAllBytesFromInputStream(new FileInputStream(file))
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (members.contains(message.getSender())
                    && member.equals(message.getRecipient())
                    && "ACCEPT GET FILE".equals(message.getTheme())) {
                members.remove(message.getSender());
            }
        }
        return null;
    }

    @Override
    protected boolean isStopping(IMessage clientSentence) {
        if (members.isEmpty()) {
            return true;
        }
        return false;
    }
}
