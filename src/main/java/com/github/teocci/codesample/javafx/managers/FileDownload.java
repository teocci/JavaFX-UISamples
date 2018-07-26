package com.github.teocci.codesample.javafx.managers;

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by teocci.
 *
 * @author teocci@yandex.com on 2018-Jul-26
 */
public class FileDownload
{
    public static final String TRADEFEED_USER_AGENT_STRING = "Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405";

    public static final int CONST_MAX_REDIRECTS = 3;

    /**
     * Downloads a file from the specified URL to the specified local file path.
     * It returns the number of bytes in the downloaded file,
     * or throws an exception if there is an error.
     *
     * @param aURL
     * @param aLocalFilePath
     * @param aMilliSecondTimeout
     * @param aSkipSNI
     * @return
     */
    public static void downLoadFileFromURL(String aURL, String aLocalFilePath, int aMilliSecondTimeout, boolean aSkipSNI)
    {
        try {

            int responseCode;
            HttpURLConnection httpUrlConnection;
            String requestURL = aURL;
            URL url;
            int redirects = 0;

            // handle multiple redirects
            while (true) {
                // open a connection to the URL and check it
                url = new URL(requestURL);
                URLConnection urlConnection = url.openConnection();

                // override hostnmae verifier if skipSNI is set
                if (aSkipSNI && urlConnection instanceof HttpsURLConnection) {
                    HttpsURLConnection httpsUrlConnection = (HttpsURLConnection) urlConnection;
                    httpsUrlConnection.setHostnameVerifier(new SSLSkipSNIHostnameVerifier());
                }

                // (GWN: 2010-05-12): We tell the server that we can support gzip/deflate
                urlConnection.setRequestProperty("Accept-Encoding", "gzip,deflate");
                urlConnection.setRequestProperty("User-Agent", TRADEFEED_USER_AGENT_STRING);
                httpUrlConnection = (HttpURLConnection) urlConnection;
                httpUrlConnection.setInstanceFollowRedirects(true);
                httpUrlConnection.setReadTimeout(aMilliSecondTimeout);
                httpUrlConnection.setConnectTimeout(aMilliSecondTimeout);
                responseCode = httpUrlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_MOVED_PERM ||
                        responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
                    if (++redirects == CONST_MAX_REDIRECTS)
                        throw new Exception("Too many redirects");

                    String location = urlConnection.getHeaderField("Location");
                    URL base = new URL(requestURL);
                    URL next = new URL(base, location);  // Deal with relative URLs
                    requestURL = next.toExternalForm();
                    continue;
                }

                break;
            }

            // if OK
            if (responseCode == HttpsURLConnection.HTTP_OK) {

                httpUrlConnection.setReadTimeout(aMilliSecondTimeout);
                // (GWN: 2010-05-12): We get the encoding returned by the server and then flip the inputstream accordingly
                // If the server does not support gzip/deflate, encoding will be NULL
                String encoding = httpUrlConnection.getContentEncoding();
                InputStream receiving = null;

                // get the file....
            }

        } catch (Exception e) {
            System.err.println("Download of '" + aURL + "' failed with error=" + e.getMessage());
        }
    }
}
