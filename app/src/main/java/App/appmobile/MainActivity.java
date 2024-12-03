package App.appmobile;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

//conexão db
FirebaseFirestore db = FirebaseFirestore.getInstance();

//index
int id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Acessar banco de dados
        DocumentReference docRef = db.collection("textos").document("frases");

        //Instanciar Botões e textos
        TextView view = findViewById(R.id.text);
        Button bt_prev = findViewById(R.id.buttonprevious);
        Button bt_next = findViewById(R.id.buttonnext);
        Button bt_share = findViewById(R.id.buttonshare);
        Button bt_login = findViewById(R.id.buttonlogin_activitymain);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                //Coletando Frases no DB
                DocumentSnapshot document = task.getResult();


                //Avançar Frases
                bt_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //simulando um index
                        if (id<7) {
                            id++;
                        }
                        //Mudando Frases no APP
                        String idString = Integer.toString(id);
                        view.setText(document.getString(idString));
                    }
                });

                //Retornar Frases
                bt_prev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Simulando um index e Mudando Frases no APP
                        if (id>1){
                            id--;
                            String idString = Integer.toString(id);
                            view.setText(document.getString(idString));
                        }
                    }
                });

                //Mudar para tela de compartilhar frases
                bt_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MainActivity.this, AdicionarFrases.class);
                        startActivity(intent);
                    }
                });

                bt_login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MainActivity.this, Login.class);
                        startActivity(intent);
                    }
                });
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });

    }
}