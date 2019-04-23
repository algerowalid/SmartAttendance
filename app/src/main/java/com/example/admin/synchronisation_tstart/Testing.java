package com.example.admin.synchronisation_tstart;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.synchronisation_tstart.affichage.Adapter_affichage;
import com.example.admin.synchronisation_tstart.affichage.list_contact;

import org.w3c.dom.Text;

public class Testing extends AppCompatActivity {

    Context context = Testing.this;
    EditText edit;
 TextView affiche ;
 ListView lista ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);


        edit = (EditText) findViewById(R.id.edit);
         affiche= (TextView) findViewById(R.id.affiche);
        lista = (ListView) findViewById(R.id.listview);

        edit.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {



                    if(edit.getText().toString().trim().length()>0){

                        String crt = edit.getText().toString();
                        String af= affiche.getText().toString();

                        affiche.setText(af+"\n"+crt);
                        edit.setText("");
                        edit.requestFocus();


                    }

                    return true;
                }
                return false;
            }
        });

    }
}
