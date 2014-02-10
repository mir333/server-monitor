package im.ligas.monitoring;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ServerMonitoring extends Activity {

    private static final Gson GSON = new Gson();
    private static final String TAG = ServerMonitoring.class.getSimpleName();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView viewById = (TextView) findViewById(R.id.messageOutput);
        BufferedReader in = null;
        try {
            URL url = new URL("http://192.168.0.114:8080/messages");
            String json = IOUtils.toString(url);
            Type listType = new TypeToken<ArrayList<ServerData>>() {
            }.getType();
            List<ServerData> data = GSON.fromJson(json, listType);
            for (ServerData serverData : data) {
                viewById.append(serverData.getSystemup());
            }

        } catch (Exception e) {
            Log.w(TAG, "Could not load data.", e);
        }

    }


}
