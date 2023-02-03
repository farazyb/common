package hello.ssh;

import com.jcraft.jsch.*;

import java.io.ByteArrayOutputStream;


public class CommandCenter {


    private Session connect(String host, String user, String password) throws JSchException {
        Session session = null;

        session = new JSch().getSession(user, host);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();


        return session;
    }

    private String executeCommand(Session session, String command) {
        ChannelExec channel = null;
        try {
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.connect();
            while (channel.isConnected()) {
                Thread.sleep(100);
            }
            String responseString = new String(responseStream.toByteArray());
            return responseString;

        } catch (JSchException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
        return null;
    }

    public static String getHostDiskSpace(String host, String username, String password) {
        CommandCenter commandCenter = new CommandCenter();
        Session session = null;
        try {
            session = commandCenter.connect(host, username, password);
        } catch (JSchException e) {
            return e.getMessage();
        }
        String response = commandCenter.executeCommand(session, "df -k");
        if (response == null || response.length() == 0) {
            return "Command Not Found";
        }
        return response;
    }


    public static void main(String[] args) {

        System.out.println(CommandCenter.getHostDiskSpace("server.kafy-nextlevel.art", "root", "wQK28769#9"));
    }
}
