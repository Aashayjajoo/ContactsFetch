package com.example.aashayj.contactsfetch;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by Aashay J on 21-03-2018.
 */
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CustomList extends BaseAdapter {

	Context mContext;
	List<Android_Contacts> mList_Android_Contacts;

	public CustomList(Context mContext, List<Android_Contacts> mContact) {
		this.mContext = mContext;
		this.mList_Android_Contacts = mContact;
	}

	@Override
	public int getCount() {
		return mList_Android_Contacts.size();
	}

	@Override
	public Object getItem(int position) {
		return mList_Android_Contacts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	//----< show items >----
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=View.inflate(mContext,R.layout.single_list,null);
//< get controls >
		TextView textview_contact_Name= (TextView) view.findViewById(R.id.textName);
		TextView textview_contact_TelefonNr= (TextView) view.findViewById(R.id.textNum);
//</ get controls >

//< show values >
		textview_contact_Name.setText(mList_Android_Contacts.get(position).contact_name);
		textview_contact_TelefonNr.setText(mList_Android_Contacts.get(position).contact_number);
//</ show values >


		view.setTag(mList_Android_Contacts.get(position).contact_name);
		return view;
	}
}


