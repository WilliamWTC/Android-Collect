package com.example.collect;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class NotesProgress extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_progress);

        BarChart barChart = findViewById(R.id.barchart);

        ArrayList<BarEntry> visitors = new ArrayList<>();
        visitors.add(new BarEntry(1, 73));
        visitors.add(new BarEntry(2, 77));
        visitors.add(new BarEntry(3, 73));
        visitors.add(new BarEntry(4, 68));
        visitors.add(new BarEntry(5, 58));

        BarDataSet barDataSet = new BarDataSet(visitors, "Progress %");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("");
        barChart.animateY(2000);
    }
}