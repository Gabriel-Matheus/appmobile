package App.appmobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.HashMap;
import java.util.Map;


public class AdicionarFrases extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adicionar_frases);



        //Instanciando Elementos
        Button btShare = findViewById(R.id.buttonshare);
        Button btReturn = findViewById(R.id.buttonreturn);
        EditText text = findViewById(R.id.text);

            btShare.setOnClickListener(new View.OnClickListener() {
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

                                // Conta os campos existentes no DB para inserir a próxima frase logo após
                                String fieldCount = Integer.toString(data.size() + 1);

                                // Insere a próxima frase
                                Map<String, Object> newData = new HashMap<>();
                                newData.put(fieldCount, String.valueOf(text.getText()));
                                DocumentReference documentReference = db.collection("textos").document("frases");
                                documentReference.update(newData);

                                //Zera caixa de texto e insere log de sucesso
                                text.setText("");
                                Toast.makeText(AdicionarFrases.this,"Envio bem sucedido", Toast.LENGTH_LONG).show();


                            } else {
                                // O documento não existe
                                Toast.makeText(AdicionarFrases.this,"Ocorreu algum erro, contate o administrador", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            // Trata o erro
                            Toast.makeText(AdicionarFrases.this,"Ocorreu algum erro, contate o administrador", Toast.LENGTH_LONG).show();

                        }
                    });

                }
            });

        //Mudar para tela de compartilhar frases
        btReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdicionarFrases.this, MainActivity.class);
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