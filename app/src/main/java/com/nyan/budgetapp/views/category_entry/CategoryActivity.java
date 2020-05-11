package com.nyan.budgetapp.views.category_entry;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nyan.budgetapp.R;
import com.nyan.budgetapp.db.AppDatabase;
import com.nyan.budgetapp.utils.CurrencyUtils;
import com.nyan.budgetapp.utils.ThreadExecutorImpl;
import com.nyan.budgetapp.vo.Category;

import top.defaults.colorpicker.ColorPickerPopup;

public class CategoryActivity extends AppCompatActivity implements CategoryContract.View {

    TextInputEditText etName, etMonthlyBudget;
    Button btnSave;
    Button btnColor;
    int selectedColor = Color.RED; //Default is red
    Category category;
    private static final String PARCELABLE_EXTRA = "CATEGORY";
    CategoryContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        setupViews();
        btnSave.setOnClickListener(v -> save());
        btnColor.setOnClickListener(v -> pickColor());
        presenter = new CategoryPresenter(this, new ThreadExecutorImpl(), AppDatabase.getInstance(this));
        category = getIntent().getParcelableExtra(PARCELABLE_EXTRA);
        if (category != null) {
            etName.setText(category.name);
            btnColor.setBackgroundColor(category.colorHex);
            etMonthlyBudget.setText(CurrencyUtils.parse(category.monthlyBudget));
        }
    }

    private void pickColor() {
        new ColorPickerPopup.Builder(this)
                .initialColor(selectedColor) // Set initial color
                .enableBrightness(false) // Enable brightness slider or not
                .enableAlpha(false) // Enable alpha slider or not
                .okTitle("Choose")
                .cancelTitle("Cancel")
                .showIndicator(false)
                .showValue(false)
                .build()
                .show(btnColor, new ColorPickerPopup.ColorPickerObserver() {
                    @Override
                    public void onColorPicked(int color) {
                        btnColor.setBackgroundColor(color);
                        selectedColor = color;
                    }
                });
    }

    private void save() {
        if (category == null)
            category = new Category();
        category.colorHex = selectedColor;
        category.name = etName.getText().toString();
        category.currencyISO = getResources().getStringArray(R.array.supported_currencies)[0];
        category.monthlyBudget = CurrencyUtils.parse(etMonthlyBudget.getText().toString());
        presenter.save(category);
    }

    private void setupViews() {
        etName = findViewById(R.id.etName);
        etMonthlyBudget = findViewById(R.id.etMonthlyBudget);
        btnSave = findViewById(R.id.btnSave);
        btnColor = findViewById(R.id.btnColor);
        btnColor.setBackgroundColor(selectedColor);
    }

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Category category) {
        Intent intent = new Intent(context, CategoryActivity.class);
        intent.putExtra(PARCELABLE_EXTRA, category);
        context.startActivity(intent);
    }
}
