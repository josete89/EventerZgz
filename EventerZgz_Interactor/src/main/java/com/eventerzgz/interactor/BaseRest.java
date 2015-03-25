package com.eventerzgz.interactor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.eventerzgz.model.exception.EventZgzException;
import org.apache.http.HttpEntity;


/**
 * Created by joseluis on 21/3/15.
 */
public class BaseRest extends BaseInteractor{

    private final int TIMEOUT = 20000;
    private final int DATARETRIEVAL_TIMEOUT = 5000;

    protected String doHTTPGet(String sUrl) throws EventZgzException
    {

        HttpURLConnection urlConnection = null;
        try {
            // create connection
            URL urlToRequest = new URL(sUrl);
            urlConnection = (HttpURLConnection)
                    urlToRequest.openConnection();
            urlConnection.setConnectTimeout(TIMEOUT);
            urlConnection.setReadTimeout(DATARETRIEVAL_TIMEOUT);

            // handle issues
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                // handle unauthorized (if service requires user login)
                throw new EventZgzException(" return HTTP_UNAUTHORIZED code");
            } else if (statusCode != HttpURLConnection.HTTP_OK) {
                // handle any other errors, like 404, 500,..
                if(urlConnection.getInputStream() != null){
                    String message  = convertInputStreamToString(new BufferedInputStream(urlConnection.getInputStream()));
                    throw new EventZgzException(message);
                }
            }

            return convertInputStreamToString(new BufferedInputStream(urlConnection.getInputStream()));

        } catch (IOException e) {

            throw new EventZgzException(e);

        } finally
        {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }


    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException
    {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder result = new StringBuilder();

        try
        {
            while ((line = bufferedReader.readLine()) != null)
                result.append(line);
            return result.toString();
        } finally
        {
            inputStream.close();
        }
    }
}


