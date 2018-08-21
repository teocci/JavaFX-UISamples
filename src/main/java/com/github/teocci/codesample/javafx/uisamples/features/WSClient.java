package com.github.teocci.codesample.javafx.uisamples.features;

import com.github.teocci.codesample.javafx.utils.LogHelper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import static com.github.teocci.codesample.javafx.utils.Config.DEFAULT_CONTROLLER_PORT;


/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Aug-16
 */
public class WSClient extends Application
{
    private static final String TAG = LogHelper.makeLogTag(WSClient.class);

    private WebSocketClient cc;

    @Override
    public void start(Stage stage)
    {
        stage.setOnCloseRequest(e -> Platform.exit());
        stage.show();

        String location = "ws://192.168.1.16:" + DEFAULT_CONTROLLER_PORT;

        try {
            // cc = new ChatClient(new URI(uriField.getText()), area, ( Draft ) draft.getSelectedItem() );
            cc = new WebSocketClient(new URI(location))
            {

                @Override
                public void onMessage(String message)
                {
                    LogHelper.w(TAG, "got: " + message);
                }

                @Override
                public void onOpen(ServerHandshake handshake)
                {
                    LogHelper.w(TAG, "You are connected to ChatServer: " + getURI());
                }

                @Override
                public void onClose(int code, String reason, boolean remote)
                {
                    LogHelper.w(TAG, "You have been disconnected from: " + getURI() + "; Code: " + code + " " + reason);
                }

                @Override
                public void onError(Exception ex)
                {
                    LogHelper.w(TAG, "Exception occured ...\n" + ex);
                }
            };

            cc.connect();
        } catch (URISyntaxException ex) {
            LogHelper.w(TAG, location + " is not a valid WebSocket URI");
            ex.printStackTrace();
        }

        LogHelper.w(TAG, "Default server url not specified: defaulting to \'" + location + "\'");
    }


    @Override
    public void stop()
    {
        if (cc != null) {
            cc.close();
            LogHelper.w(TAG, "Websocket was closed");
        }

        System.exit(0);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
