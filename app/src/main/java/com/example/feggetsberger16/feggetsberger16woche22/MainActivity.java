package com.example.feggetsberger16.feggetsberger16woche22;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Spinner sArt;
    Spinner sKat;
    TextView np;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sArt = findViewById(R.id.spinner);
        sKat = findViewById(R.id.spinner2);
        List<String> listArt = new ArrayList<>();
        listArt.add("Einnahmen");
        listArt.add("Ausgaben");
        ArrayAdapter<String> artAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listArt);
        artAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sArt.setAdapter(artAdapter);
        List<String> listKat = createKategorien();
        ArrayAdapter<String> katAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listKat);
        sKat.setAdapter(katAdapter);
        np = findViewById(R.id.numberPicker);
    }

    public void onClickButton(View view)
    {
        TextView tDate = findViewById(R.id.date);
        String s = tDate.getText().toString();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(s,dtf);
        System.out.println(sArt.getSelectedItem().toString());
        String kat = sKat.getSelectedItem().toString();
        String art = sArt.getSelectedItem().toString();

        double price = Double.parseDouble(np.getText().toString());
        String sDate = date.format(dtf);
        //printToString(sDate,art,price,kat);
        Context c = new MainActivity();
        if(date == null || art.equals("") || art==null || price == 0 || kat.equals("") || kat.equals(null))
        {
            Toast.makeText(MainActivity.this,"invalid data",Toast.LENGTH_LONG).show();
        }
        Eintrag e = new Eintrag(date,art,price,kat);
        writeToCsv(c,e);
    }

    private void writeToCsv(Context context,Eintrag e)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            OutputStreamWriter writer = new OutputStreamWriter(context.openFileOutput("values.csv",Context.MODE_PRIVATE));
            writer.write(e.getDate().format(dtf));
            writer.write(e.getArt());
            writer.write(String.valueOf(e.getPrice()));
            writer.write(e.getKat());
        }catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private void printToString(String date,String art,Double price,String kat)
    {
        String s = "Am " + date + " " + art + " von " + price + " Euro für " +kat;
        System.out.println(s);
    }

    private boolean dateIsValid(String s)
    {
        if(s.contains("."))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private List<String> createKategorien()
    {
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("Bank");
        list.add("Lebensmittel");
        list.add("Frisör");
        //Hier fehlt noch das einlesen von gridview
        return list;
    }
}