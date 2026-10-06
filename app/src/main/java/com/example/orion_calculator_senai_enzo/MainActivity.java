package com.example.orion_calculator_senai_enzo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    private TextView display;

    private String currentInput = "";

    private String currentOperator = "";

    private double operand1 = Double.NaN;

    private double operand2;

    private boolean resetScreen = false;

    private double memory = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        // ===== BOTÕES NUMÉRICOS =====
        int[] numberButtonIds = {
                R.id.btn_0,R.id.btn_1,R.id.btn_2, R.id.btn_3, R.id.btn_4,
                R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9
        };

        for (int id : numberButtonIds){
            findViewById(id).setOnClickListener( v ->
                    onNumberButtonClick(((Button) v).getText().toString())
            );
        }

        // ===== VíRGULA =====
        findViewById(R.id.btn_de).setOnClickListener(v -> onDecimalButtonClick());


        // ===== VíRGULA =====
        findViewById(R.id.btn_add).setOnClickListener(view -> onOperatorButtonClick("+"));
        findViewById(R.id.btn_subtract).setOnClickListener(view -> onOperatorButtonClick("-"));
        findViewById(R.id.btn_multiply).setOnClickListener(view -> onOperatorButtonClick("x"));
        findViewById(R.id.btn_divide).setOnClickListener(v -> onOperatorButtonClick("÷"));   // <- NOVO

        // ===== IGUAL =====
        findViewById(R.id.btn_equal).setOnClickListener(v -> onEqualsButtonClick());

        // ===== FUNÇÕES ESPECIAIS =====
        findViewById(R.id.btn_sign).setOnClickListener(v -> onSignButtonClick());
        findViewById(R.id.btn_percent).setOnClickListener(v -> onPercentualButtonClick);
        findViewById(R.id.btn_c).setOnClickListener(v -> onClearButtonClick);
        findViewById(R.id.btn_ce).setOnClickListener(v -> onClearEntryButtonClick());
        findViewById(R.id.btn_backspace).setOnClickListener(v -> onBackspaceButtonClick());
        findViewById(R.id.btn_reciprocal).setOnClickListener(v -> onReciprocalButtonClick());
        findViewById(R.id.btn_square).setOnClickListener(v -> onSquareButtonClick());
        findViewById(R.id.btn_cube_root).setOnClickListener(v -> onCubeRootButtonClick());

        // ===== MEMÓRIA =====
        findViewById(R.id.btn_mc).setOnClickListener(v -> onMemoryClear());
        findViewById(R.id.btn_mr).setOnClickListener(v -> onMemoryRecall());
        findViewById(R.id.btn_m_plus).setOnClickListener(v -> onMemoryAdd());
        findViewById(R.id.btn_m_minus).setOnClickListener(v -> onMemorySubtract());
        findViewById(R.id.btn_ms).setOnClickListener(v -> onMemoryStore());
        findViewById(R.id.btn_mv).setOnClickListener(v -> onMemoryView());

    }

    // ===== NÚMEROS =====

    private void onNumberButtonClick(String digit) {
        if (resetScreen){
            currentInput = "";
            resetScreen = false;
        }

        if (currentInput.equals("0") && digit.equals("0")){
            return;
        }

        if (currentInput.equals("0") && !digit.equals("0")){
            currentInput = digit;
        }else {
            currentInput += digit;
        }

        updateDisplay();
    }

    private void onDecimalButtonClick() {
        if (resetScreen){
            currentInput = "";
            resetScreen = false;
            updateDisplay();
            return;
        }
        if (currentInput.contains(".")){
            return;
        }
        if (currentInput.isEmpty()){
            currentInput = "0.";
        }else {
            currentInput += ".";
        }
        updateDisplay();
    }

    // ===== OPERADORES =====

    private void onOperatorButtonClick(String operator) {
        if (!Double.isNaN(operand1)){
            onEqualsButtonClick();
        }
        try {
            operand1 = Double.parseDouble(currentInput);
        }catch (NumberFormatException e){
            operand1 = 0.0;
        }

        currentOperator = operator;
        resetScreen = true;
    }

    private void onEqualsButtonClick() {
        if (!Double.isNaN(operand1) || currentOperator.isEmpty()){
            return;
        }
        try {
            operand2 = Double.parseDouble(currentInput);
        }catch (NumberFormatException e){
            operand2 = 0.0;
        }

        double result = 0.0;

        switch (currentOperator){
            case "+":
                result = operand1 + operand2;
                break;
            case "-":
                result = operand1 - operand2;
                break;
            case "x":
                result = operand1 * operand2;
                break;
            case "÷":
                if (operand2 == 0){
                    display.setText("Error");
                    resetCalculator();
                    return;
                }
                result = operand1 / operand2;
                break;
        }

        currentOperator = "";
        resetScreen = true;
    }



    private String formatResult(double result){
        if (result == (long) result){
            return String.valueOf((long) result);
        }else{
            return String.format("%.10f", result)
                    .replaceAll("0*$", "")
                    .replaceAll("\\.$", "");
        }
    }

    private void resetCalculator() {
    }
}