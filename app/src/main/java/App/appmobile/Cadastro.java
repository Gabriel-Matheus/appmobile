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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;


public class Cadastro extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);

        //Instanciando elementos
        Button btCadastrar = findViewById(R.id.buttonsignup);
        Button btBack = findViewById(R.id.buttonback);
        EditText textName = findViewById(R.id.editTextNome);
        EditText textLogin = findViewById(R.id.editTextLoginCadastro);
        EditText textPassword = findViewById(R.id.editTextPasswordCadastro);


        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Recebendo dados do usuário
                String name = textName.getText().toString();
                String login = textLogin.getText().toString();
                String password = textPassword.getText().toString();

                 if(name.isEmpty() || login.isEmpty() || password.isEmpty()){
                     Toast.makeText(Cadastro.this,"Preencha os campos em branco", Toast.LENGTH_LONG).show();
                 }
                 else {
                     //Criando Login
                     FirebaseAuth.getInstance().createUserWithEmailAndPassword(login,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if(task.isSuccessful()){
                                 Toast.makeText(Cadastro.this,"Cadastro efetuado com sucesso", Toast.LENGTH_LONG).show();
                                 Intent intent = new Intent(Cadastro.this, MainActivity.class);
                                 startActivity(intent);
                             } else {
                                 String erro;
                                 try {
                                    throw task.getException();

                                 } catch (FirebaseAuthWeakPasswordException e) {
                                     erro = "Digite uma senha com no mínimo 6 caracteres";
                                     Toast.makeText(Cadastro.this,erro, Toast.LENGTH_LONG).show();

                                 } catch (FirebaseAuthUserCollisionException e) {
                                     erro = "Conta já cadastrada";
                                     Toast.makeText(Cadastro.this,erro, Toast.LENGTH_LONG).show();

                                 } catch (FirebaseAuthInvalidCredentialsException e) {
                                     erro = "E-mail inválido";
                                     Toast.makeText(Cadastro.this,erro, Toast.LENGTH_LONG).show();

                                 } catch (Exception e) {
                                     erro = "Erro ao cadastrar o usuário";
                                     Toast.makeText(Cadastro.this,erro, Toast.LENGTH_LONG).show();
                                 }

                             }
                         }
                     });
                 }
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cadastro.this, Login.class);
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
