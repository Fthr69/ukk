package com.example.membuataplikasikalkulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {
    private int[] tombolnumeric = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
    private int[] tomboloperator = {R.id.kali, R.id.bagi, R.id.tambah, R.id.kurang};
    private TextView layarTampil;
    private TextView layarHasil;
    private boolean angkaTerakhir;
    private boolean kaloError;
    private boolean setelahTitik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.layarTampil = (TextView) findViewById(R.id.tvSol);
        this.layarHasil = (TextView) findViewById(R.id.tvRes);
        NumericDiKlik();
        OperatorDiKlik();
    }

    private void NumericDiKlik() {
        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button tombol = (Button) v;
                if (kaloError) {
                    layarTampil.setText(tombol.getText());
                    kaloError = false;
                } else {
                    layarTampil.append(tombol.getText());
                }
                angkaTerakhir = true;
            }
        };
        for (int id : tombolnumeric) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void OperatorDiKlik() {
        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (angkaTerakhir && !kaloError) {
                    Button tombol = (Button) v;
                    layarTampil.append(tombol.getText());
                    angkaTerakhir = false;
                    setelahTitik = false;
                }
            }
        };
        for (int id : tomboloperator) {
            findViewById(id).setOnClickListener(listener);
        }
        findViewById(R.id.titik).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (angkaTerakhir && !kaloError && !setelahTitik) {
                    layarTampil.append(".");
                    angkaTerakhir = false;
                    setelahTitik = true;
                }
            }
        });
        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layarTampil.setText(" ");
                layarHasil.setText(" ");
                angkaTerakhir = false;
                kaloError = false;
                setelahTitik = false;

            }
        });
        findViewById(R.id.equal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEqual();
            }
        });
    }

    private void onEqual() {
        if (angkaTerakhir && !kaloError) {
            String txt = layarTampil.getText().toString();
            Expression expression = new ExpressionBuilder(txt).build();
            try {
                double result = expression.evaluate();
                layarHasil.setText(Double.toString(result));
                setelahTitik = true;
            } catch (ArithmeticException ex) {
                layarHasil.setText("ERROR");
                kaloError = true;
                angkaTerakhir = false;
            }
        }
    }
}

