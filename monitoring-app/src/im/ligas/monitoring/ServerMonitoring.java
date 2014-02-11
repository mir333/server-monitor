package im.ligas.monitoring;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;

import java.lang.reflect.Type;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ServerMonitoring extends Activity {

    private static final Gson GSON = new Gson();
    private static final String TAG = ServerMonitoring.class.getSimpleName();
    public static final String NEW_LINE = "\n";
    public static final SimpleDateFormat SDF = new SimpleDateFormat("dd.MM. HH:mm:ss");

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        UIUpdater updater = new UIUpdater(new Runnable() {
            @Override
            public void run() {
                try {
                    TextView viewById = (TextView) findViewById(R.id.messageOutput);
                    LoadDataTask loadDataTask = new LoadDataTask();
                    loadDataTask.execute("http://192.168.0.114:8080/messages");

                    String dataToShow = getDataToShow(loadDataTask.get());
                    viewById.setText(dataToShow);
                } catch (InterruptedException e) {
                    Log.w(TAG, "Could not render data.", e);
                } catch (ExecutionException e) {
                    Log.w(TAG, "Could not render data.", e);
                }
            }
        });

        updater.startUpdates();
    }


    private String getDataToShow(List<ServerData> data) {
        StringBuilder sb = new StringBuilder();
        for (ServerData serverData : data) {
            sb.append(SDF.format(serverData.getNow()));
            sb.append("                           up time: ");
            sb.append(serverData.getSystemup());
            sb.append(NEW_LINE);
            for (GpuData gpuData : serverData.getGpu()) {
                sb.append(gpuData.getId()).append("::").append(gpuData.getTemp())
                        .append("::").append(gpuData.getHashrate()).append(NEW_LINE);
            }
            sb.append("-----------------------------------------------------------------------------------------\n");
        }
        return sb.toString();
    }

    private class LoadDataTask extends AsyncTask<String, Void, List<ServerData>> {

        private Exception exception;

        protected List<ServerData> doInBackground(String... urls) {
            List<ServerData> result = Collections.emptyList();
            try {
                URL url = new URL(urls[0]);
                String json = IOUtils.toString(url);

                Type listType = new TypeToken<ArrayList<ServerData>>() {
                }.getType();
                result = GSON.fromJson(json, listType);
            } catch (Exception e) {
                Log.w(TAG, "Could not load data.", e);
            }
            return result;
        }


    }


}
