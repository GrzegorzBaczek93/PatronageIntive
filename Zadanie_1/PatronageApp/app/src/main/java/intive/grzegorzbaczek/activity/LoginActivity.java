package intive.grzegorzbaczek.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import intive.grzegorzbaczek.R;

public class LoginActivity extends AppCompatActivity {

    String response = null;
    EditText nameInput = null;
    Button loginButton = null;
    AlertDialog.Builder dialogBuilder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameInput = (EditText) findViewById(R.id.input_name);
        loginButton = (Button) findViewById(R.id.button_login);
        dialogBuilder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Validate(nameInput)) {
                    dialogBuilder.setMessage("Hello " + nameInput.getText().toString())
                            .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                } else {
                    dialogBuilder.setMessage(response)
                            .setNegativeButton(R.string.ok, null);
                }
                dialogBuilder.create().show();
            }
        });
    }

    private boolean Validate(TextView textView) {
        if (textView.getText().toString().length() == 0) {
            response = "Name field is empty.\nPlease enter your name.";
            return false;
        } else {
            response = "OK";
            return true;
        }
    }
}
