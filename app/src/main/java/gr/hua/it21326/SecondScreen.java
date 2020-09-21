package gr.hua.it21326;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

/**
 * Created by xrhstos on 14/12/2017.
 */

public class SecondScreen extends AppCompatActivity {
    DatabaseHelper myDb;
    //Button btnShowData;
    //Button button;
    String spinValue;
    Spinner spin;
    EditText user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {      //in this on create first of all we build our button , and then our list that we want to fill our dropdown
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final Button btnShowData =findViewById(R.id.btnShow);
        btnShowData.setOnClickListener(ThirdScreenL);
        //button = (Button)findViewById(R.id.button);
        //ShowData();
        user = findViewById(R.id.user3);

        myDb = new DatabaseHelper(this);

        List<String> list = myDb.getAlldt();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin = findViewById(R.id.spinner_id);

        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {
                spinValue = parent.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

    }
    //now we set the tri    ger for the time our user presses our button so we can move at the next screen
    View.OnClickListener ThirdScreenL = new View.OnClickListener() {
        public void onClick(View view) {
            String userIdValue = user.getText().toString();
            Intent intent = new Intent( SecondScreen.this, ThirdScreen.class);
            intent.putExtra("UserId", userIdValue);
            intent.putExtra("dt", spinValue);
            startActivity(intent);

            //user.setText(null);

        }
    };

    /*
    public void ShowData(){           //temp void used to view all data from my database in the early stages

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res = myDb.getData();
                            if(res.getCount() == 0 ){
                                showMessage("Error","nothing found");
                                return;
                            }
                            StringBuffer buffer = new StringBuffer();
                            while (res.moveToNext()) {
                                buffer.append("id :" + res.getString(0) +"\n");
                                buffer.append("user id :" + res.getString(1) +"\n");
                                buffer.append("Longitude :" + res.getString(2) +"\n");
                                buffer.append("Latitude :" + res.getString(3) +"\n");
                                buffer.append("timestamp :" + res.getString(4) +"\n");


                            }
                            showMessage("Data",buffer.toString());
                    }
                }
        );

    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }*/
}
