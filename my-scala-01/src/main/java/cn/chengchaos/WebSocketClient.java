package cn.chengchaos;

import org.eclipse.jetty.websocket.api.UpgradeRequest;
import org.eclipse.jetty.websocket.api.UpgradeResponse;

import javax.websocket.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.List;

@ClientEndpoint
public class WebSocketClient {


    @OnOpen
    public void onOpen(Session session) {
        System.out.println("open");
    }

    @OnMessage
    public void onMessage(String message) {

        System.out.println("clicent .. on Message : "+ message);
    }

    @OnClose
    public void onClose() {
        System.out.println("close");
    }



    private void connect() {

        String userID = "18";
        String clentUrl = "ws://localhost:8080/myHandler/"+ userID;

        WebSocketContainer container = null;
        try {
            container = ContainerProvider.getWebSocketContainer();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            URI uri = URI.create(clentUrl);
            session = container.connectToServer(WebSocketClient.class, uri);
        } catch ( DeploymentException | IOException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        try {
            session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "bye .. "));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Session session;

    public Session getSession() {
        return session;
    }

    public static void main(String[] args) {

        WebSocketClient client = new WebSocketClient();

        client.connect();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String line = "";
            Session clientSession = client.getSession();

            org.eclipse.jetty.websocket.api.Session jettySession = (org.eclipse.jetty.websocket.api.Session)client.getSession();

            do {
                line = br.readLine();
                if (line.equals("exit")) {
                    break;
                }
                if (client.session.isOpen()) {
                    UpgradeRequest req = jettySession.getUpgradeRequest();
                    List<String> channelName = req.getParameterMap().get("channelName");

                    UpgradeResponse resp = jettySession.getUpgradeResponse();
                    System.out.println("channelName "+ channelName);

                    String subprotocol = resp.getAcceptedSubProtocol();
                    System.out.println("subprotocol "+ subprotocol);

                    InetSocketAddress remoteAddr = jettySession.getRemoteAddress();
                    System.out.println("remoteAddr "+ remoteAddr);


                    org.eclipse.jetty.websocket.api.RemoteEndpoint remoteEndpoint = jettySession.getRemote();
                    ByteBuffer buf = ByteBuffer.wrap(new byte[] { 0x11, 0x22, 0x33, 0x44 });
                    remoteEndpoint.sendBytes(buf);





                    clientSession.getBasicRemote().sendText("javaclient: "+ line);


                } else {
                    client.connect();
                }
            } while (true);


            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

