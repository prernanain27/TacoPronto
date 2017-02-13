package taco.android.csulb.edu.tacopronto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends  AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] fillings = getResources().getStringArray(R.array.fillings);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,fillings);
        GridView grid = (GridView)findViewById(R.id.gridView);

        grid.setAdapter(adapter);
        for (int i=0; i<adapter.getCount();i++){
            grid.setItemChecked(i,true);
        }

        String[] bevrages = getResources().getStringArray(R.array.bevrages);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,bevrages);
        GridView grid2 = (GridView)findViewById(R.id.gridView2);

        grid2.setAdapter(adapter2);
        for (int i=0; i<adapter2.getCount();i++){
            grid2.setItemChecked(i,true);
        }
    }
}
