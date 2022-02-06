package com.application.weatherappversion1;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// Implementation of App Widget functionality.
class NewAppWidget extends AppWidgetProvider {
    String temp;
    String API = "8118ed6ee68db2debfaaa5a44c832918";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {

        // There may be multiple
        // widgets active, so update
        // all of them
        for (int appWidgetId : appWidgetIds) {updateAppWidget(context, appWidgetManager, appWidgetId);

            for (int i = 0; i < appWidgetIds.length; i++){
                appWidgetId = appWidgetIds[i];

                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

                //Setup a static image, this works fine.
                views.setTextViewText(R.id.appwidget_text, "jdjj");

                new weatherTask(views, appWidgetId, appWidgetManager).execute("My String");

                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        }
    }

    // Enter relevant functionality for
    // when the first widget is created
    @Override
    public void onEnabled(Context context)
    {
        super.onEnabled(context);

    }

    // Enter relevant functionality for
    // when the last widget is disabled
    @Override
    public void onDisabled(Context context)
    {
        super.onDisabled(context);
    }

    private void
    updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId)
    {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        // Instruct the widget manager to update the widget
       // new weatherTask(views, appWidgetId, appWidgetManager).execute("My String");

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    class weatherTask extends AsyncTask<String, Void, String> {
        private RemoteViews views;

        private int WidgetID;
        private AppWidgetManager WidgetManager;

        public weatherTask(RemoteViews views, int appWidgetID, AppWidgetManager appWidgetManager){
            this.views = views;
            this.WidgetID = appWidgetID;
            this.WidgetManager = appWidgetManager;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected String doInBackground(String... args) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" +"Meerut" + "&units=metric&appid=" + API);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                Long updatedAt = jsonObj.getLong("dt");
                String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                 temp = main.getString("temp") + "°C";
                String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
                String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";


                views.setTextViewText(R.id.appwidget_text, temp);
                WidgetManager.updateAppWidget(WidgetID,views);
                /* Populating extracted data into our views */



            } catch (JSONException e) {

            }

        }
    }

}