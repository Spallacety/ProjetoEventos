package br.edu.ifpi.projetoeventos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import br.edu.ifpi.projetoeventos.firebase.UserDAO;
import br.edu.ifpi.projetoeventos.models.event.Factory;
import br.edu.ifpi.projetoeventos.models.event.User;
import br.edu.ifpi.projetoeventos.models.others.MyActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MyActivity {

    private FirebaseAuth mAuth;
    private static User user = Factory.makeUser();

    @BindView(R.id.button_main) Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        UserDAO.getInstance().findById(mAuth.getCurrentUser().getUid(), new UserDAO.LoadOneCallback() {
            @Override
            public void onLoaded(User user) {
                MainActivity.user = user;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(user.getName() != null) Toast.makeText(this, user.getName(), Toast.LENGTH_LONG).show();
    }

    public void button(View v){
        Intent intent = new Intent(this, EditLocationActivity.class);
        startActivity(intent);
        finish();
    }

    public void button2(View v){
        Intent intent = new Intent(this, EditEventActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                AlertDialog logout;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(R.string.logout_confirm);
                builder.setNegativeButton(R.string.cancel, null);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                logout = builder.create();
                logout.show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
