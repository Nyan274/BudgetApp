package com.nyan.budgetapp.views.transaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nyan.budgetapp.R;
import com.nyan.budgetapp.db.AppDatabase;
import com.nyan.budgetapp.utils.CurrencyUtils;
import com.nyan.budgetapp.utils.DateUtils;
import com.nyan.budgetapp.utils.ThreadExecutorImpl;
import com.nyan.budgetapp.vo.Category;
import com.nyan.budgetapp.vo.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

public class TransactionActivity extends AppCompatActivity implements TransactionContract.View {
    private static final String PARCELABLE_EXTRA = "TRANSACTION";
    Spinner spnCategory;
    Spinner spnCurrency;
    TextInputEditText etAmount;
    TextView tvTimeStamp;
    Button btnSave;
    TransactionContract.Presenter presenter;
    long timeStamp = System.currentTimeMillis();
    CategoryAdapter categoryAdapter;
    ArrayAdapter<String> currencyAdapter;
    List<Category> categories = new ArrayList<>();
    Transaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        setupViews();
        presenter = new TransactionPresenter(AppDatabase.getInstance(this), this, new ThreadExecutorImpl());
        transaction = getIntent().getParcelableExtra(PARCELABLE_EXTRA);
        presenter.loadCategories();
        btnSave.setOnClickListener(view -> {
            double amount = Double.parseDouble(Objects.requireNonNull(etAmount.getText()).toString());
            if (transaction == null) transaction = new Transaction();
            transaction.amount = amount;
            transaction.category = categoryAdapter.getItem(spnCategory.getSelectedItemPosition());
            transaction.timeStamp = timeStamp;
            transaction.currencyISO = spnCurrency.getSelectedItem().toString();
            presenter.save(transaction);
        });
        tvTimeStamp.setOnClickListener(view -> showDateTimePicker());
    }

    private void showExistingTransaction(Transaction transaction) {
        tvTimeStamp.setText(DateUtils.getDateTimeString(transaction.timeStamp));
        etAmount.setText(CurrencyUtils.parse(transaction.amount));
        int currencyAdapterPosition = currencyAdapter.getPosition(transaction.currencyISO);
        spnCurrency.setSelection(currencyAdapterPosition);
        int categoryAdapterPosition = categoryAdapter.getPosition(transaction.category);
        spnCategory.setSelection(categoryAdapterPosition);
    }

    private void showDateTimePicker() {
        final View dialogView = View.inflate(this, R.layout.date_time_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        dialogView.findViewById(R.id.date_time_set).setOnClickListener(view -> {

            DatePicker datePicker = dialogView.findViewById(R.id.date_picker);
            TimePicker timePicker = dialogView.findViewById(R.id.time_picker);

            Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                    datePicker.getMonth(),
                    datePicker.getDayOfMonth(),
                    timePicker.getCurrentHour(),
                    timePicker.getCurrentMinute());

            timeStamp = calendar.getTimeInMillis();
            tvTimeStamp.setText(DateUtils.getDateTimeString(timeStamp));
            alertDialog.dismiss();
        });
        alertDialog.setView(dialogView);
        alertDialog.show();
    }

    private void setupViews() {
        spnCategory = findViewById(R.id.spnCategory);
        spnCurrency = findViewById(R.id.spnCurrency);
        etAmount = findViewById(R.id.etAmount);
        tvTimeStamp = findViewById(R.id.tvTimeStamp);
        btnSave = findViewById(R.id.btnSave);
        currencyAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.supported_currencies));
        categoryAdapter = new CategoryAdapter(this, android.R.layout.simple_spinner_item, categories);
        spnCurrency.setAdapter(currencyAdapter);
        spnCategory.setAdapter(categoryAdapter);
        tvTimeStamp.setText(DateUtils.getDateTimeString(timeStamp));

    }

    @Override
    public void showCategories(List<Category> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
        categoryAdapter.notifyDataSetChanged();
        if (transaction != null) {
            showExistingTransaction(transaction);
        }
    }

    public static void start(Activity activity) {
        start(activity, null);
    }

    public static void start(Activity activity, Transaction transaction) {
        Intent intent = new Intent(activity, TransactionActivity.class);
        intent.putExtra(PARCELABLE_EXTRA, transaction);
        activity.startActivity(intent);
    }
}
