package gr.hua.it21326;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editUserid, editLong , editLat, editDt;
    Button btnSaveData;
    Button btnupdateData;

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editUserid = (EditText)findViewById(R.id.user_id);
        editLong = (EditText)findViewById(R.id.long_id);
        editLat = (EditText)findViewById(R.id.lat_id);
        editDt = (EditText)findViewById(R.id.dt_id);

        btnSaveData =(Button)findViewById(R.id.btnsave_id);

        AddData();

        btnupdateData =(Button)findViewById(R.id.btnupdate);
        UpdateData();



        Button btnToSecond = (Button)findViewById(R.id.btncontinue_id);



        btnToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondScreen.class);

                startActivity(intent);
                /*editUserid.setText(null);
                editLong.setText(null);
                editLat.setText(null);
                editDt.setText(null);*/
            }
        });
    }

    public void AddData(){
        btnSaveData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(editUserid.getText().toString())) {           //this else_if helps so the user cannot put empty values
                            editUserid.setError("cannot be empty");                        //and after that we show him an error message so that he can understand his mistake
                            return;
                        } else if (TextUtils.isEmpty(editLong.getText().toString())) {
                            editLong.setError("cannot be empty");
                            return;
                        } else if (TextUtils.isEmpty(editLat.getText().toString())) {
                            editLat.setError("cannot be empty");
                            return;
                        } else if (TextUtils.isEmpty(editDt.getText().toString())) {
                            editDt.setError("cannot be empty");
                            return;
                        } else {
                            boolean isInserted = myDb.insertData(editUserid.getText().toString(),
                                    editLong.getText().toString(), editLat.getText().toString(), editDt.getText().toString());
                            if (isInserted = true) {
                                Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(MainActivity.this, "Problem. Data not Inserted", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );


    }
    public void UpdateData(){
        btnupdateData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(editUserid.getText().toString())) {
                            editUserid.setError("cannot be empty");
                            return;
                        } else if (TextUtils.isEmpty(editLong.getText().toString())) {
                            editLong.setError("cannot be empty");
                            return;
                        } else if (TextUtils.isEmpty(editLat.getText().toString())) {
                            editLat.setError("cannot be empty");
                            return;
                        } else if (TextUtils.isEmpty(editDt.getText().toString())) {
                            editDt.setError("cannot be empty");
                            return;
                        } else {
                            boolean isInserted = myDb.update(editUserid.getText().toString(),
                                    editLong.getText().toString(), editLat.getText().toString(), editDt.getText().toString());
                            if (isInserted = true) {
                                Toast.makeText(MainActivity.this, "Data updated", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(MainActivity.this, "Problem. Data not Inserted", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );


    }
}
