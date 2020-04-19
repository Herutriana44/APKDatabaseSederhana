package com.dtb;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;
import java.lang.Override;
import java.lang.Exception;

public class DTBSEDERHANAActivity extends Activity
{
	private dtbs dm;
	private EditText kata;
	private Button add;
	private TableLayout tabelleot;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    
    	dm = new dtbs(this);
    	kata = (EditText)findViewById(R.id.edit);
    	tabelleot = (TableLayout)findViewById(R.id.tabel);
    	add = (Button)findViewById(R.id.add);
    	add.setOnClickListener(new View.OnClickListener()
    	{
    		@Override
    		public void onClick(View arg0)
    		{
    			simpKamuta();
    		}
    	});
    	updateTable();
    }
    protected void simpKamuta()
    {
    	try
    	{
    		dm.addRow(kata.getText().toString());
    		Toast.makeText(getBaseContext(),kata.getText()+", berhasil disimpan",Toast.LENGTH_SHORT).show();
    		updateTable();
    		KosongkanField();
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		Toast.makeText(getBaseContext(),"gagal simpan, "+ e.toString(),Toast.LENGTH_SHORT).show();
    	}
    }
    protected void KosongkanField()
    {
    	kata.setText("");
    }
    protected void updateTable()
    {
    	while(tabelleot.getChildCount() > 1)
    	{
    		tabelleot.removeViewAt(1);
    	}
    	ArrayList<ArrayList<Object>> data = dm.ambilSemuaBaris();
    	for (int posisi = 0;posisi < data.size();posisi++)
    	{
    		TableRow tabelrowe = new TableRow(this);
    		ArrayList<Object> baris = data.get(posisi);
    		
    		TextView txt = new TextView(this);
    		txt.setText(baris.get(0).toString());
    		tabelrowe.addView(txt);
    		
    		tabelleot.addView(tabelrowe);
    	}
    }
}
