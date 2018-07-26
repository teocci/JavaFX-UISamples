package com.github.teocci.codesample.javafx.uisamples.features;

import com.github.teocci.codesample.javafx.managers.SSLSkipSNIHostnameVerifier;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;

/**
 * Reports load times for pages loaded in a WebView
 * <p>
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class LoadTimer extends Application
{
    // Create a trust manager that does not validate certificate chains
     private TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager()
            {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) throws CertificateException {}

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) throws CertificateException {}

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers()
                {
                    return null;
                }
            }
    };

    @Override
    public void start(final Stage stage)
    {
        final WebView webview = new WebView();

        VBox layout = new VBox();
        layout.getChildren().setAll(createProgressReport(webview.getEngine()), webview);

        stage.setScene(new Scene(layout, 1024, 600));
        stage.show();

        loadWeb(webview);
    }

    private void loadWeb(WebView webview)
    {
        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new SSLSkipSNIHostnameVerifier());
        } catch (GeneralSecurityException e) {
        }

        // Now you can access an https URL without having the certificate in the truststore
        webview.getEngine().load("http://www.fxexperience.com");
    }

    /**
     * @return a HBox containing a ProgressBar bound to engine load progress and a Label showing load times
     */
    private HBox createProgressReport(WebEngine engine)
    {
        final LongProperty startTime = new SimpleLongProperty();
        final LongProperty endTime = new SimpleLongProperty();
        final LongProperty elapsedTime = new SimpleLongProperty();

        final ProgressBar loadProgress = new ProgressBar();
        loadProgress.progressProperty().bind(engine.getLoadWorker().progressProperty());

        final Label loadTimeLabel = new Label();
        loadTimeLabel.textProperty().bind(Bindings
                .when(elapsedTime.greaterThan(0))
                .then(Bindings.concat("Loaded page in ", elapsedTime.divide(1_000_000), "ms"))
                .otherwise("Loading...")
        );

        elapsedTime.bind(Bindings.subtract(endTime, startTime));

        engine.getLoadWorker().stateProperty().addListener((observableValue, oldState, state) -> {
            switch (state) {
                case RUNNING:
                    startTime.set(System.nanoTime());
                    break;

                case SUCCEEDED:
                    endTime.set(System.nanoTime());
                    break;
            }
        });

        HBox progressReport = new HBox(10);
        progressReport.getChildren().setAll(
                loadProgress,
                loadTimeLabel
        );
        progressReport.setPadding(new Insets(5));
        progressReport.setAlignment(Pos.CENTER_LEFT);

        return progressReport;
    }

    public static void main(String[] args) { launch(args); }
}
