package com.example.simpledatabase4;

import java.util.ArrayList;

import com.example.simpledatabase4.R;

import android.R.integer;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {
	ListView mListView;
	SimpleCursorAdapter mAdapter2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// widgets
		mListView = (ListView)findViewById(R.id.listView1);
		mAdapter2 = new SimpleCursorAdapter(
			this,
			android.R.layout.simple_list_item_1,
			null, // cursor
			new String[]{ "body" },
			new int[]{ android.R.id.text1 },
			0
		);
		mListView.setAdapter(mAdapter2);
		
		// 初期表示
		clearResult();
		select();
	}
	
	// DBインターフェース取得
	SQLiteDatabase getDb(){
		MyDbHelper helper = new MyDbHelper(this);
		SQLiteDatabase db = helper.getWritableDatabase();
		return db;
	}
	
	// 結果表示
	public void clearResult(){
		TextView textView = (TextView)findViewById(R.id.textView1);
		textView.setText("");
	}
	public void addResult(String result){
		TextView textView = (TextView)findViewById(R.id.textView1);
		textView.setText(textView.getText().toString() + result + "\n");
	}

	// INSERT
	public void buttonMethodInsert(View v){
		clearResult();
		SQLiteDatabase db = getDb();
		// insertメソッド版
		try{
			ContentValues values = new ContentValues();
			values.put("body", "foo");
			db.insert(
				"messages", // テーブル名
				null,		// データを挿入する際にnull値が許可されていないカラムに代わりに利用される値
				values		// 値群
			);
			addResult("INSERT 成功");
		}
		catch(Exception ex){
			addResult("INSERT 失敗: " + ex.getMessage());
		}
		select();
	}
	
	// SELECT
	public void buttonMethodSelect(View v){
		clearResult();
		select();
	}
	public void select(){
		SQLiteDatabase db = getDb();
		try{
			Cursor cursor = db.query(
				"messages",						// テーブル名
				new String[]{"_id", "body"},	// 選択するカラム群
				null,							// selection
				null,							// selectionArgs
				null,							// group by
				null,							// having
				null							// order by
			);
			mAdapter2.swapCursor(cursor);
			addResult("SELECT 成功");
		}
		catch(Exception ex){
			addResult("SELECT 失敗: " + ex.getMessage());
		}
	}
	
	public void buttonMethodUpdate(View v){
		clearResult();
		SQLiteDatabase db = getDb();

		// updateメソッド版
		try{
			ContentValues values = new ContentValues();
			values.put("body", "XYZ");
			db.update(
				"messages",						// テーブル名
				values,							// 値群。
				null,							// 条件。
				null							// where args
			);
			addResult("UPDATE 成功");
		}
		catch(Exception ex){
			addResult("UPDATE 失敗: " + ex.getMessage());
		}
		select();
	}

	public void buttonMethodDelete(View v){
		clearResult();
		SQLiteDatabase db = getDb();
		
		// deleteメソッド版
		try{
			db.delete(
				"messages",							// テーブル名
				null,							// 条件。
				null							// where args
			);
			addResult("DELETE 成功");
		}
		catch(Exception ex){
			addResult("DELETE 失敗: " + ex.getMessage());
		}
		select();
	}
}
