package App.appmobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    //acessando db
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //instanciando botões e edittexts
    private Button bt_previous, bt_foward;
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //função para instanciar
        StartComponents();

        //listener do botão
        bt_foward.setOnClickListener(new View.OnClickListener()
        {
            DocumentReference reference = db.collection("Frases").document("frases");
            @Override
            public void onClick(View v)
            {
                //acessando dados dentro do firestore
                reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        text.setText((String) documentSnapshot.getData().get("olhe pra cima"));
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

    private void StartComponents(){
        bt_previous = findViewById(R.id.buttonprevious);
        bt_foward = findViewById(R.id.buttonnext);
        text = findViewById(R.id.text);
    }


}