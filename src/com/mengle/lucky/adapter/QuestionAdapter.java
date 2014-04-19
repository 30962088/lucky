package com.mengle.lucky.adapter;

import java.util.List;

import com.mengle.lucky.R;
import com.mengle.lucky.wiget.QuestionLayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class QuestionAdapter extends BaseAdapter {

	public static class Question {
		private String no;
		private String name;
		private double percent;
		private int color;
		private int type;
		
		public Question(String no, String name, double percent, int color,int type) {
			super();
			this.no = no;
			this.name = name;
			this.percent = percent;
			this.color = color;
			this.type = type;
		}
		public String getNo() {
			return no;
		}
		public void setNo(String no) {
			this.no = no;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public double getPercent() {
			return percent;
		}
		public void setPercent(double percent) {
			this.percent = percent;
		}
		public int getColor() {
			return color;
		}
		public void setColor(int color) {
			this.color = color;
		}
		public void setType(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
		

	}

	private Context context;
	
	private List<Question> questions;

	public QuestionAdapter(Context context, List<Question> questions) {
		super();
		this.context = context;
		this.questions = questions;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return questions.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return questions.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Question question = questions.get(position);
		ViewHolder holder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.question_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.noView.setText(question.no);
		holder.noView.setTextColor(question.color);
		holder.nameView.setText(question.name);
		holder.nameView.setTextColor(question.color);
		holder.percentView.setText(""+(question.percent*100));
		holder.questionView.setColor(question.color);
		holder.questionView.setType(question.type);
		holder.questionView.invalidate();
		return convertView;
	}
	
	private static class ViewHolder{
		private TextView noView;
		private TextView nameView;
		private QuestionLayout questionView;
		private TextView percentView;
		public ViewHolder(View view) {
			noView = (TextView) view.findViewById(R.id.no);
			nameView = (TextView) view.findViewById(R.id.name);
			questionView = (QuestionLayout) view.findViewById(R.id.questionlayout);
			percentView = (TextView) view.findViewById(R.id.percent);
		}
	}

}
