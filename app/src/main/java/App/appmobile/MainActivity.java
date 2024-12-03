package App.appmobile;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

//index
int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Instanciar Botões e textos
        TextView view = findViewById(R.id.text);
        Button bt_prev = findViewById(R.id.buttonprevious);
        Button bt_next = findViewById(R.id.buttonnext);
        Button bt_share = findViewById(R.id.buttonshare);
        Button bt_login = findViewById(R.id.buttonlogin1);


                //Avançar Frases
                bt_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Indexador


                        //Conectando ao DB
                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        //Coletar Informações do Banco de Dados
                        Task<DocumentSnapshot> documentTask = db.collection("textos").document("frases").get();

                        documentTask.addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();

                                if (document.exists()) {
                                    // Obtém os dados do documento
                                    Map<String, Object> data = document.getData();

                                    //Passando pelas frases no botão próxima

                                    if (id < data.size()) {
                                        id++;
                                        //Mudando frases na EditText
                                        String idString = Integer.toString(id);
                                        view.setText(document.getString(idString));
                                    }
                                    else {
                                        view.setText("Você já leu todas as frases");
                                        if(id== data.size()){
                                        id++;
                                        }
                                    };




                                } else {
                                    // O documento não existe
                                    Toast.makeText(MainActivity.this,"Ocorreu algum erro, contate o administrador", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                // Trata o erro
                                Toast.makeText(MainActivity.this,"Ocorreu algum erro, contate o administrador", Toast.LENGTH_LONG).show();

                            }
                        });

                    }
                });

                bt_prev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Conectando ao DB
                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        //Coletar Informações do Banco de Dados
                        Task<DocumentSnapshot> documentTask = db.collection("textos").document("frases").get();

                        documentTask.addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();

                                if (document.exists()) {
                                    // Obtém os dados do documento
                                    Map<String, Object> data = document.getData();

                                    //Passando pelas frases no botão próxima

                                    if (id >1) {
                                        id--;
                                        //Mudando frases na EditText
                                        String idString = Integer.toString(id);
                                        view.setText(document.getString(idString));

                                    }

                                } else {
                                    // O documento não existe
                                    Toast.makeText(MainActivity.this,"Ocorreu algum erro, contate o administrador", Toast.LENGTH_LONG).show();
                                }

                        }
                    });
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

                //Mudar para tela de Login
                bt_login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MainActivity.this, Login.class);
                        startActivity(intent);
                    }
                });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });

    }
}