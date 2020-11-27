package com.example.carteiramensal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import ferramentas.EventosDb;

public class MainActivity extends AppCompatActivity {

    private TextView titulo;
    private TextView entrada;
    private TextView saida;
    private TextView saldo;
    private ImageButton entraBtn;
    private ImageButton saidaBtn;
    private Button anteriorBtn;
    private Button proxBtn;
    private Button novoBtn;
    private Calendar hoje;
    private Calendar dataAPP;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //criando o link entre os componentes JAVA X XML
        titulo = (TextView) findViewById(R.id.tituloMain);
        entrada = (TextView) findViewById(R.id.entradaTxt);
        saida = (TextView) findViewById(R.id.saidaTxt);
        saldo = (TextView) findViewById(R.id.saltoTxt);

        entraBtn = (ImageButton) findViewById(R.id.entradaBtn);
        saidaBtn = (ImageButton) findViewById(R.id.saidaBtn);

        anteriorBtn = (Button) findViewById(R.id.anteriorBtn);
        proxBtn = (Button) findViewById(R.id.proximoBtn);
        novoBtn = (Button) findViewById(R.id.novoBtn);

        //Responsável por implementar todos os eventos de botões
        cadastroEventos();

        //recupero a data e hora atual
        hoje = Calendar.getInstance();
        dataAPP = Calendar.getInstance();

        mostraDataApp();



    }

    private void cadastroEventos(){
        anteriorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizaMes(-1);
            }
        });

        proxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizaMes(1);
            }
        });

        novoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventosDb db = new EventosDb(MainActivity.this);
                db.insereEvento();

                Toast.makeText(MainActivity.this, db.getDatabaseName(), Toast.LENGTH_LONG);


            }
        });



    }

    private void mostraDataApp(){
        //0-janeiro, 1-fevereiro, ...... 11-dezembro

        String nomeMes[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro",
                "Outubro", "Novembro", "Dezembro"};

        int mes = dataAPP.get(Calendar.MONTH);
        int ano = dataAPP.get(Calendar.YEAR);

        titulo.setText(nomeMes[mes] + "/" +ano);

        }


    private void atualizaMes(int ajuste){
        dataAPP.add(Calendar.MONTH, -1);
        //próximo mes (Não pode passar do mês atual)
        if(ajuste > 0){
            if(dataAPP.after(hoje)){
                dataAPP.add(Calendar.MONTH, -1);
            }
        }else{
            //aqui temos que reealizar uma busca no banco de dados (avaliar se existem meses anteriores cadastrados)
        }

        mostraDataApp();
    }
}