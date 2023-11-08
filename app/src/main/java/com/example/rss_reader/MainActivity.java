package com.example.rss_reader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * @noinspection ALL
 */
public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> arrayList,arrayLink,arrayDate,arrayDis;
//    ArrayAdapter arrayAdapter;

    ArrayList<VNEXPRESS> list_item;
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listViewTieuDe);
//        arrayList = new ArrayList<>();
//        arrayLink = new ArrayList<>();
//        arrayDate = new ArrayList<>();
//        arrayDis = new ArrayList<>();
        list_item = new ArrayList<>();
        adapter = new  ListViewAdapter(MainActivity.this,R.layout.item,list_item);
        listView.setAdapter(adapter);
        listView.setDivider(null);
        new RSSReader().execute("https://vnexpress.net/rss/the-gioi.rss");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String link = list_item.get(position).getLink();
                String title = list_item.get(position).getTitle();
                Intent intent = new Intent(getApplicationContext(), DatailActivity.class);
                intent.putExtra("title",title);
                intent.putExtra("link",link);
                startActivity(intent);
                Toast.makeText(MainActivity.this, list_item.get(position).getLink(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class RSSReader extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLDOMParser xmldomParser = new XMLDOMParser();
            Document document = xmldomParser.getDocument(s);
            NodeList list = document.getElementsByTagName("item");
            for (int i = 0; i < list.getLength(); i++) {
                Element element = (Element) list.item(i);
                VNEXPRESS item = new VNEXPRESS(xmldomParser.getValue(element,"title"),xmldomParser.getValue(element,"description"),xmldomParser.getValue(element,"pubDate"),xmldomParser.getValue(element,"link"));
                list_item.add(item);
            }
            adapter.notifyDataSetChanged();
            //Toast.makeText(MainActivity.this, tieude, Toast.LENGTH_SHORT).show();
        }
    }
}