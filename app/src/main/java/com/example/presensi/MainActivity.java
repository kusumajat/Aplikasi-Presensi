package com.example.presensi;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TimePicker timePicker;
    private Spinner spinnerStatus;
    private EditText editTextKeterangan;
    private Button btnSubmit;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi view
        calendarView = findViewById(R.id.calendarView);
        timePicker = findViewById(R.id.timePicker);
        spinnerStatus = findViewById(R.id.spinner_status);
        editTextKeterangan = findViewById(R.id.editText_keterangan);
        btnSubmit = findViewById(R.id.btn_submit);

        // Set default format untuk TimePicker (12 jam format)
        timePicker.setIs24HourView(false);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            }
        });

        // Set action ketika tombol Submit ditekan
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ambil nilai dari Spinner
                String selectedStatus = spinnerStatus.getSelectedItem().toString();

                // Ambil nilai keterangan
                String keterangan = editTextKeterangan.getText().toString();

                // Ambil waktu dari TimePicker
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                String amPm = (hour >= 12) ? "PM" : "AM";
                if (hour > 12) {
                    hour -= 12;
                } else if (hour == 0) {
                    hour = 12;
                }
                String selectedTime = String.format("%02d:%02d", hour, minute);

                // Cek jika belum memilih tanggal, default ke tanggal sekarang
                if (selectedDate == null) {
                    Calendar calendar = Calendar.getInstance();
                    selectedDate = calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                            (calendar.get(Calendar.MONTH) + 1) + "/" +
                            calendar.get(Calendar.YEAR);
                }

                // EditText tidak boleh kosong
                if (spinnerStatus.getSelectedItemPosition() == 1 || spinnerStatus.getSelectedItemPosition() == 2) {
                    if (TextUtils.isEmpty(keterangan)) {
                        editTextKeterangan.setError("Keterangan harus diisi!");
                        Toast.makeText(MainActivity.this, "Isi keterangan untuk pilihan ini!", Toast.LENGTH_SHORT).show();
                        return;  // Hentikan proses jika keterangan kosong
                    }
                }

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout, findViewById(R.id.custom_toast_container));

                // Inisialisasi komponen di custom toast
                TextView textStatus = layout.findViewById(R.id.textStatus);
                TextView textKeterangan = layout.findViewById(R.id.textKeterangan);
                TextView textTanggal = layout.findViewById(R.id.textTanggal);
                TextView textWaktu = layout.findViewById(R.id.textWaktu);

                // Mengisi nilai untuk status, keterangan, tanggal, dan waktu
                textStatus.setText("Status: " + selectedStatus);
                textKeterangan.setText("Keterangan: " + keterangan);
                textTanggal.setText("Tanggal: " + selectedDate);
                textWaktu.setText("Waktu: " + selectedTime);

                // Buat toast
                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        });
    }
}


