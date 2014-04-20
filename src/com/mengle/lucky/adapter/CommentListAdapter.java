package com.mengle.lucky.adapter;

import java.util.List;

import com.mengle.lucky.R;
import com.mengle.lucky.adapter.CommentModel.Reply;
import com.mengle.lucky.utils.BitmapLoader;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CommentListAdapter extends BaseAdapter {


	
	private Context context;

	private List<CommentModel> commentList;

	

	public CommentListAdapter(Context context, List<CommentModel> commentList) {
		super();
		this.context = context;
		this.commentList = commentList;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return commentList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return commentList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		CommentModel commentModel = commentList.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.comment_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		BitmapLoader.displayImage(context, commentModel.getPhoto(), holder.photoView);
		
		holder.nameView.setText(commentModel.getName());
		holder.timeView.setText(commentModel.getTime());
		initReply(holder,commentModel.getReply());
		
		return convertView;
	}
	
	private void initReply(ViewHolder holder,List<Reply> list){
		holder.listView.removeAllViews();
		for(Reply reply : list){
			TextView textView = (TextView) LayoutInflater.from(context).inflate(
					R.layout.comment_list_item, null);
			
			String replyContent = ""; 
			if(reply.getName() != null){
				replyContent = "回复"+reply.getName()+":";
			}
			SpannableString sp = new SpannableString(replyContent+reply.getContent());
			if(reply.getName() != null){
				sp.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.reply_highlight)),2,reply.getName().length()+2,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			}
			
			textView.setText(sp);
			holder.listView.addView(textView);
		}
		
		
	}

	private static class ViewHolder {
		private ImageView photoView;
		private ViewGroup listView;
		private TextView timeView;
		private TextView nameView;
		public ViewHolder(View view) {
			photoView = (ImageView) view.findViewById(R.id.photo);
			listView = (ViewGroup) view.findViewById(R.id.listview);
			timeView = (TextView) view.findViewById(R.id.time);
			nameView = (TextView) view.findViewById(R.id.name);
		}
	}
	

}
