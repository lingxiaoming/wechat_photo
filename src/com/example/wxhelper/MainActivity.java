package com.example.wxhelper;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements MediaScannerConnectionClient, MyPictureAdapter.OnItemClickLitener {

	private final String ROOTPATH = Environment.getExternalStorageDirectory().toString()+"/Tencent/MicroMsg/";

	List<String> listpath;
	private static final String FILE_TYPE = "image/*";// ��������Ϊimage
	private MediaScannerConnection conn;//
	private MyPictureAdapter adapter;
	private RecyclerView mRecyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listpath = new ArrayList<String>();
		mRecyclerView = (RecyclerView) this.findViewById(R.id.recyclerview);
//		mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
		startScan();

		File folder = new File(ROOTPATH);//��

		getAllPicture(folder, listpath);
		adapter = new MyPictureAdapter(this, listpath);
		adapter.setOnItemClickLitener(this);
		mRecyclerView.setAdapter(adapter);
	}


	public void getAllPicture(File file, List<String> list){
		if(file == null) return;
		
		if(file.exists()){
			if(file.isFile()){
				if(file.getName().endsWith(".png") || file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg"))
//				if(MediaFile.isPictureFileType(file.getAbsolutePath()))
				if(file.getAbsolutePath().contains("image2")){
					FileInputStream fis = null;
					try {
						fis = new FileInputStream(file);
						long s = fis.available();
						fis.close();
						if(s > 1)
						list.add(file.getAbsolutePath());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}else if(file.isDirectory() && file.listFiles()!=null){
				for (File fileItem : file.listFiles()) {
					getAllPicture(fileItem, list);
				}
			}
		}
	}
	
	private void startScan() {
		Log.d("Connected", "----->>success" + conn);
		if (conn != null) {
			conn.disconnect();//
		}
		conn = new MediaScannerConnection(this, this);//
		conn.connect();// ����
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (conn != null) {
			conn.disconnect();//
		}
	}

	@Override
	public void onMediaScannerConnected() {
		conn.scanFile(ROOTPATH, FILE_TYPE);//
	}

	@Override
	public void onScanCompleted(String path, Uri uri) {

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

	@Override
	public void onItemClick(View view, int position) {
		Toast.makeText(this, position + " click "+adapter.getItem(position), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onItemLongClick(View view, int position) {
		Toast.makeText(this, position + " long click "+adapter.getItem(position), Toast.LENGTH_SHORT).show();
	}
}
