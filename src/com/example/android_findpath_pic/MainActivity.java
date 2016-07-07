package com.example.android_findpath_pic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements
		MediaScannerConnectionClient {
	List<String> listpath;
	public String[] allFiles;// �����ļ���·��
	// ///////////////////////////////////////////////////////////
	private String SCAN_PATH;//
	private static final String FILE_TYPE = "image/*";// ��������Ϊimage
	private MediaScannerConnection conn;// ���һ��MediaScannerConnection���� ���ڲ����ļ�
	private Button scanbutton;
	Myadapter adapter;
	private ListView listView;

	// ////////////////////////////////////////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listpath = new ArrayList<String>();
		listView = (ListView) this.findViewById(R.id.listView1);
		File folder = new File(Environment.getExternalStorageDirectory().toString()+"/Tencent/MicroMsg/");// ���һ���ļ���·��
		allFiles = folder.list();
		
		getAllPicture(folder, listpath);
		
		SCAN_PATH = Environment.getExternalStorageDirectory().toString()
				+ "/Tencent/MicroMsg/" + allFiles[0];// ������ͼƬ·��

		Log.d("SCAN PATH", "Scan Path " + SCAN_PATH);
		scanbutton = (Button) this.findViewById(R.id.button1);
		scanbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startScan();
				adapter = new Myadapter(getApplicationContext());
				adapter.setdate(listpath);
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();

			}

		});

	}
	public void getAllPicture(File file, List<String> list){
		if(file == null) return;
		
		if(file.exists()){
			if(file.isFile()){
				if(file.getName().endsWith(".png") || file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg"))
//				if(MediaFile.isPictureFileType(file.getAbsolutePath()))
				list.add(file.getAbsolutePath());
			}else if(file.isDirectory()){
				for (File fileItem : file.listFiles()) {
					getAllPicture(fileItem, list);
				}
			}
		}
	}
	
	// ////////////////////////////////////////
	// ����ɨ��Ķ���
	private void startScan() {
		Log.d("Connected", "----->>success" + conn);
		if (conn != null) {
			conn.disconnect();// �ȹ���ԭ����ɨ��Ķ���
		}
		conn = new MediaScannerConnection(this, this);// ��һ��������ɨ���context
														// ��2����interface
		conn.connect();// ����
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// //////////////////////////////////////////////////////////////
	// �����ʾ�����Ѿ�������
	@Override
	public void onMediaScannerConnected() {
		// TODO Auto-generated method stub
		conn.scanFile(SCAN_PATH, FILE_TYPE);// ��һ�������� ɨ��ĳ�ʼ·�� ��2���Ǳ���Ҫɨ����ļ�����

	}

	// ////////////////////////////////////////////////////
	// �����ɨ����ɵ�
	@Override
	public void onScanCompleted(String path, Uri uri) {
		// TODO Auto-generated method stub

		try {
			Log.d("onScanCompleted", uri + "----->>success" + conn);
			System.out.println("URI " + uri);
			// if (uri != null) {
			// Intent intent = new Intent(Intent.ACTION_VIEW);// ��ʾ�Ĳ鿴ͼƬ��intent
			// intent.setData(uri);// ��һ��ָ������uri��ʾͼƬ
			// startActivity(intent);//
			// }
		} finally {
			conn.disconnect();
			conn = null;
		}
	}

	public class Myadapter extends BaseAdapter {
		private LayoutInflater inflater;
		private List<String> listpath;

		private void setdate(List<String> path) {
			// TODO Auto-generated method stub
			this.listpath = path;
		}

		@SuppressWarnings("static-access")
		public Myadapter(Context context) {
			inflater = inflater.from(context);

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listpath.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return listpath.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = null;
			convertView = inflater.inflate(R.layout.item, null);
			TextView picname = (TextView) convertView.findViewById(R.id.imagename);
			ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1);
			
			String path = listpath.get(position);
			Bitmap bitmap = BitmapFactory.decodeFile(path, null);
			imageView.setImageBitmap(bitmap);
			picname.setText(path);
			
			return convertView;
		}

	}

}
