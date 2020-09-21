package gr.hua.it21326;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by xrhstos on 15/12/2017.
 */

public class ThirdScreen extends AppCompatActivity {

    DatabaseHelper myDb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        String selectedUserId = getIntent().getStringExtra("UserId"); //we use this strings to get our variables from the previus activity
        String selectedDt = getIntent().getStringExtra("dt");
        myDb = new DatabaseHelper(this);

        ListView finalListView = findViewById(R.id.myarray);        //here we create the list that we want to be printed at our 3rd screen
        List<String> myarray;
        myarray = myDb.findData(selectedUserId, selectedDt);    //calling the data from our base
        ArrayAdapter mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myarray);
        finalListView.setAdapter(mAdapter);
    }
}

