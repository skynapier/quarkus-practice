package tian.websockets;

import io.agroal.api.AgroalDataSource;
import org.jboss.logging.Logger;

import tian.record.RecordService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;
import org.json.JSONObject;
import tian.timeconverter.TimeConverterService;

@ServerEndpoint("/chat/{username}")
@ApplicationScoped
public class ChatSocket {

    Map<String, Session> sessions = new ConcurrentHashMap<>();
    Map<String, Integer> appearance = new ConcurrentHashMap<>();

    @Inject
    RecordService service;

    @Inject
    AgroalDataSource dataSource;

    @Inject
    TimeConverterService timeConverterService;


    private static final Logger LOGGER = Logger.getLogger(ChatSocket.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username)  {

        LOGGER.debug("works on socket1");

        sessions.put(username, session);

        String parsed = timeConverterService.timeParser(1492301050L,  33.865143f, 151.2099f);

        LOGGER.info("works on socket time service" + parsed);
        LOGGER.info("works on socket service" + service.findAllRecords());
//        try{
//
//            service.findAllRecords();
//            LOGGER.debug("works on socket2 : " );
//            Connection connection = dataSource.getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement("select * from record;");
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()){
//                String id = resultSet.getString("id");
//                LOGGER.debug("works on socket3 : " + id);
//            }
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }



        broadcast("User " + username + " joined " );
    }

    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        sessions.remove(username);
        broadcast("User " + username + " left");
    }

    @OnError
    public void onError(Session session, @PathParam("username") String username, Throwable throwable) {
        sessions.remove(username);
        broadcast("User " + username + " left on error: " + throwable);
    }

    @OnMessage
    public void onMessage(String msg,  @PathParam("username") String username) {

        LOGGER.debug("debuge msg : " + msg);
        JSONObject test  = new JSONObject(msg);
        LOGGER.debug("debuge msg : " + test);
        LOGGER.debug("debuge msg : " + test.get("lat"));
//        service.findRecordById("0");
        broadcast(">> " + username + ": " + msg );


    }

    private void broadcast(String message) {
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result ->  {
                if (result.getException() != null) {
                    System.out.println("Unable to send message: " + result.getException());
                }
            });
        });
    }

}