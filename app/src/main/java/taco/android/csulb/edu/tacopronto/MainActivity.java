package taco.android.csulb.edu.tacopronto;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import static taco.android.csulb.edu.tacopronto.R.id.large;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
   GridView grid2;
    GridView grid;
    int size=1;
    int base=2;
    int[] fill;
    int[] bev ;
    String size_desc ="Medium";
    String base_desc ="Corn";
    int price=0;
    String description = "";
    String[] fillings = new String[100];
    String[] bevrages = new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillings = getResources().getStringArray(R.array.fillings);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, fillings);
        grid = (GridView) findViewById(R.id.gridView);
        grid.setAdapter(adapter);
        fill = new int[grid.getCount()];
        for (int i=0; i<adapter.getCount();i++){
            fill[i]=0;
            grid.setItemChecked(i,false);
        }

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                if(fill[position]==0) {
                    fill[position] = 1;
                    Toast.makeText(MainActivity.this, fillings[position] + " Added to your order", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    fill[position]=0;
                    Toast.makeText(MainActivity.this, fillings[position]+" Deleted from your order" , Toast.LENGTH_SHORT).show();
                }

            }
        });

        bevrages = getResources().getStringArray(R.array.bevrages);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, bevrages);
        grid2 = (GridView) findViewById(R.id.gridView2);
        grid2.setAdapter(adapter2);
        bev = new int[grid2.getCount()];
        for(int i=0;i<grid2.getCount();i++){
            grid2.setItemChecked(i,false);
            bev[i]=0;
        }
        grid2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                if(bev[position]==0) {
                    bev[position] = 1;
                    Toast.makeText(MainActivity.this, bevrages[position]+" Added to your order" , Toast.LENGTH_SHORT).show();
                }
                else {
                    bev[position] = 0;
                    Toast.makeText(MainActivity.this, bevrages[position] + " Deleted from your order", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    public void radioClick(View v){
        switch (v.getId()){
            case R.id.large:
                size = 0;
                size_desc ="Large";
                break;
            case R.id.medium:
                size = 1;
                size_desc = "Medium";
                break;
        }
    }

    public  void baseSelection(View v){
        switch (v.getId()){
            case R.id.corn:
                base = 2;
                base_desc="Corn";
                break;
            case R.id.flour:
                base = 3;
                base_desc = "Flour";
                break;
        }
    }

    public void onClick(View v){
        description =" A "+ size_desc +"Taco with the base of" + base_desc + " and filled with following items :";
        int[] pricelist= getResources().getIntArray(R.array.prices);
        price = price + pricelist[size];
        price = price + pricelist[base];
        if(v.getId()==R.id.Button){
            for(int i=0;i<grid.getCount();i++){
                if(fill[i]==1){
                    price = price + pricelist[i+4];
                    description += "\n" + fillings[i];
                }
            }
            description += "\n Bevrages ordered are:";
            for(int i=0;i<grid2.getCount();i++){
                if(bev[i]==1){
                    price = price + pricelist[i+4+grid.getCount()];
                    description += "\n " + bevrages[i];
                }
            }
            description += "\n Total bill is "+ Integer.toString(price) +"$";
            Toast.makeText( getApplicationContext(),"Your total bill is " +Integer.toString(price)+"$", Toast.LENGTH_SHORT ).show();
            sendSms( price , description);

        }

    }


    public void sendSms( int price , String description)
    {
       if( ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED)
       {
           try {

               SmsManager smsManager = SmsManager.getDefault();
               smsManager.sendTextMessage("5623506072", null, description , null, null);
               Toast.makeText( getApplicationContext(),"Sending your order via sms ", Toast.LENGTH_SHORT ).show();
           }
           catch (Exception e)
            {
                Toast.makeText( getApplicationContext(),"Sending sms failed"  , Toast.LENGTH_SHORT ).show();
                e.printStackTrace();

            }
       }
        else
       {
            Toast.makeText( getApplicationContext(),"Permission for sending messages is not granted"  , Toast.LENGTH_SHORT ).show();
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.SEND_SMS} , 123);
       }
    }




    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSms(price,description);
            }

        }
    }
}
