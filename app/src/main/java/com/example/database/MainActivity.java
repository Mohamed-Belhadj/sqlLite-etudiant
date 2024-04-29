package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public EditText nom, prenom, presence;
    public ListView listEtudiant;
    public Button addBtn, deleteBtn;
    public EtudiantDataSource etudiantDataSource;
    public int selectedStudentId = -1, getpresence = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        listEtudiant = findViewById(R.id.listetudiant);
        nom = findViewById(R.id.edittext_nom);
        prenom = findViewById(R.id.edittext_prenom);
        presence = findViewById(R.id.edittext_presence);
        addBtn = findViewById(R.id.button_add);
        deleteBtn = findViewById(R.id.button_delete);

        // Initialize data source
        etudiantDataSource = new EtudiantDataSource(this);

        // Display students on startup
        updateListEtudiants();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getnom = nom.getText().toString();
                String getprenom = prenom.getText().toString();
                getpresence = Integer.parseInt(presence.getText().toString());

                if (getnom.isEmpty() || getprenom.isEmpty() || getpresence == -1) {
                    Toast.makeText(MainActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    Etudiant etudiant = new Etudiant(getnom, getprenom, getpresence);
                    etudiantDataSource.addEtudiant(etudiant);
                    getpresence = -1;
                    updateListEtudiants();
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Selected idEtudiant to delete is: "+ selectedStudentId, Toast.LENGTH_SHORT).show();
                if (selectedStudentId != -1) {
                    // Delete the student with the correct ID
                    etudiantDataSource.deleteEtudiant(selectedStudentId);
                    selectedStudentId = -1;
                    updateListEtudiants();
                } else {
                    Toast.makeText(MainActivity.this, "No student selected for deletion", Toast.LENGTH_SHORT).show();
                }
            }
        });


        listEtudiant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedStudentId = etudiantDataSource.getListEtudiants().get(position).getId();
                Toast.makeText(MainActivity.this, "Etudiant selected at position: " + position, Toast.LENGTH_SHORT).show(); // For debugging
            }
        });
    }

    private void updateListEtudiants() {
        List<Etudiant> updatedList = etudiantDataSource.getListEtudiants();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                getNomEtudiants(updatedList));
        listEtudiant.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private List<String> getNomEtudiants(List<Etudiant> etudiants) {
        List<String> studentNames = new ArrayList<>();
        for (Etudiant etudiant : etudiants) {
            studentNames.add(etudiant.getNom() + " " + etudiant.getPrenom());
        }
        return studentNames;
    }
}
