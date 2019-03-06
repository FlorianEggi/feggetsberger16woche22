package com.example.feggetsberger16.feggetsberger16woche22;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Spinner sArt;
    Spinner sKat;
    TextView np;
    ListView lv;
    TextView lvc;
    List<String> eintraege;
    ArrayAdapter<String> eintraegeAdapter;
    double cash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sArt = findViewById(R.id.spinner);
        sKat = findViewById(R.id.spinner2);
        lv = findViewById(R.id.listView);
        lvc = findViewById(R.id.textViewMoney);
        eintraege = new ArrayList<>();
        List<String> listArt = new ArrayList<>();
        listArt.add("Einnahmen");
        listArt.add("Ausgaben");
        cash = 4711;
        eintraegeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, eintraege);
        ArrayAdapter<String> artAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listArt);
        artAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sArt.setAdapter(artAdapter);
        lv.setAdapter(eintraegeAdapter);
        List<String> listKat = readKat();
        ArrayAdapter<String> katAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listKat);
        katAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sKat.setAdapter(katAdapter);
        np = findViewById(R.id.numberPicker);
        String s = cash + " €";
        lvc.setText(s);
    }

    public void onClickButton(View view) {
        TextView tDate = findViewById(R.id.date);
        String s = tDate.getText().toString();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(s, dtf);
        System.out.println(sArt.getSelectedItem().toString());
        String kat = sKat.getSelectedItem().toString();
        String art = sArt.getSelectedItem().toString();
        double price = Double.parseDouble(np.getText().toString());
        String sDate = date.format(dtf);
        //printToString(sDate,art,price,kat);
        Context c = new MainActivity();
        if (date == null || art.equals("") || art == null || price == 0 || kat.equals("") || kat.equals(null)) {
            Toast.makeText(MainActivity.this, "invalid data", Toast.LENGTH_LONG).show();
        }
        String zeichen;
        zeichen = "";
        if ("Einnahmen".equals(art)) {
            zeichen = "+";

        }
        if ("Ausgaben".equals(art)) {
            zeichen = "-";
        }
        Eintrag e = new Eintrag(date, art, price, kat, zeichen);
        if ("+".equals(zeichen)) {
            cash = cash + price;
        }
        if ("-".equals(zeichen)) {
            cash = cash - price;
        }
        String s2 = cash + " €";
        lvc.setText(s2);
        eintraegeAdapter.add(e.toString());
        lv.setAdapter(eintraegeAdapter);
        writeToCsv(e);
    }

    private void writeToCsv(Eintrag e) {
        try{
            FileOutputStream fos = openFileOutput("values.txt",MODE_APPEND);
            PrintWriter wr = new PrintWriter((new OutputStreamWriter(fos)));
            wr.write(e.toString());
            System.out.println("written");
            wr.flush();
            System.out.println("flushed");
            wr.close();
            System.out.println("closed");
        }
        catch(Exception ex)
        {

        }
    }

    private void printToString(String date, String art, Double price, String kat) {
        String s = "Am " + date + " " + art + " von " + price + " Euro für " + kat;
        System.out.println(s);
    }

    private boolean dateIsValid(String s) {
        if (s.contains(".")) {
            return true;
        } else {
            return false;
        }
    }

    private List<String> readKat() {

        List<String> list = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("kategorien.txt")));
            String s;
            while ((s = br.readLine())!=null){
                list.add(s);
            }
        } catch (Exception ex) {
        }
        return list;
    }
}