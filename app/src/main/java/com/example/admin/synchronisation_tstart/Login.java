package com.example.admin.synchronisation_tstart;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText login, pass;

    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
    login = (EditText) findViewById(R.id.login);
    pass = (EditText) findViewById(R.id.pass);
    }


    public void connecter(View tr){
        String lo= login.getText().toString();
        String pa= pass.getText().toString();


        if(lo.equals("admin") && pa.equals("ums")){
            Intent in = new Intent(Login.this, MainActivity.class);
            startActivity(in);
            this.finish();
        }else{
            ShowSnackBar("Accés refusé");
        }

    }


    private void displayToast(String message) {
        // Inflate toast XML layout
        View layout = getLayoutInflater().inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.root_layout));
        // Fill in the message into the textview
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(message);
        // Construct the toast, set the view and display
        Toast toast = new Toast(getApplicationContext());
        toast.setView(layout);
        toast.show();
    }

    public void ShowSnackBar(String message){
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
