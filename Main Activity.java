package com.example.kalkulmod;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    private int[] tombolNumerik = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};

    private int[] tombolOperator = {R.id.btnKali, R.id.btnBagi, R.id.btnKurang, R.id.btnTambah};

    private TextView layarTampil;
    private TextView layarHasil;

    private boolean angkaTerakhir;
    private boolean kaloEror;
    private boolean setelahTitik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layarTampil = findViewById(R.id.tvSol);
        layarHasil = findViewById(R.id.tvResult);

        setNumerikDiKlik();
        setOperatorDiklik();
    }

    private void setNumerikDiKlik() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button tombol = (Button) v;
                if (kaloEror) {
                    layarTampil.setText(tombol.getText());
                    kaloEror = false;
                } else {
                    layarTampil.append(tombol.getText());
                }
                angkaTerakhir = true;
            }
        };

        for (int id : tombolNumerik) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOperatorDiklik() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (angkaTerakhir && !kaloEror) {
                    Button tombol = (Button) v;
                    layarTampil.append(tombol.getText());
                    angkaTerakhir = false;
                    setelahTitik = false;
                }
            }
        };

        for (int id : tombolOperator) {
            findViewById(id).setOnClickListener(listener);
        }

        findViewById(R.id.btnTitik).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (angkaTerakhir && !kaloEror && !setelahTitik) {
                    layarTampil.append(".");
                    angkaTerakhir = false;
                    setelahTitik = true;
                }
            }
        });

        findViewById(R.id.btnC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layarTampil.setText("");
                layarHasil.setText("");
                angkaTerakhir = false;
                kaloEror = false;
                setelahTitik = false;
            }
        });

        findViewById(R.id.btnSmdg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEqual();
            }
        });
    }

    private void onEqual() {
        if (angkaTerakhir && !kaloEror) {
            String txt = layarTampil.getText().toString();
            try {
                Expression expression = new ExpressionBuilder(txt).build();
                double result = expression.evaluate();
                layarHasil.setText(String.valueOf(result));
                setelahTitik = true;
            } catch (ArithmeticException ex) {
                layarHasil.setText("ERROR");
                kaloEror = true;
                angkaTerakhir = false;
            }
        }
    }
}
