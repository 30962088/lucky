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

	public static class Status{
		private boolean gameover;
		private List<Integer> passIndex;
		public Status(boolean gameover, List<Integer> passIndex) {
			super();
			this.gameover = gameover;
			this.passIndex = passIndex;
		}
		public boolean isGameover() {
			return gameover;
		}
		public void setGameover(boolean gameover) {
			this.gameover = gameover;
		}
		public List<Integer> getPassIndex() {
			return passIndex;
		}
		public void setPassIndex(List<Integer> passIndex) {
			this.passIndex = passIndex;
		}
		
		
		
	}
	
	public static class Question{
		private String title;
		private Status status;
		private List<QuestionItem> list;
		
		
		
		public Question(String title, Status status, List<QuestionItem> list) {
			super();
			this.title = title;
			this.status = status;
			this.list = list;
		}
		
		public String getTitle() {
			return title;
		}
		public List<QuestionItem> getList() {
			return list;
		}
		public void setList(List<QuestionItem> list) {
			this.list = list;
		}
		
	}
	
	
	
	public static class QuestionItem {
		private String no;
		private String name;
		private double percent;
		private int color;
		private int type;
		private int id;
		
		public QuestionItem(int id,String no, String name, double percent, int color,int type) {
			this.id = id;
			this.no = no;
			this.name = name;
			this.percent = percent;
			this.color = color;
			this.type = type;
		}
		
		public int getId() {
			return id;
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
	
	private Question question;

	public QuestionAdapter(Context context, Question question) {
		super();
		this.context = context;
		this.question = question;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return question.list.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return question.list.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		QuestionItem questionItem = question.list.get(position);
		ViewHolder holder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.question_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.noView.setText(questionItem.no);
		holder.selectedNo.setText(questionItem.no);
		holder.noView.setTextColor(questionItem.color);
		holder.nameView.setText(questionItem.name);
		holder.nameView.setTextColor(questionItem.color);
		holder.percentView.setText(""+String.format("%.2f", (questionItem.percent*100)));
		holder.questionView.setColor(questionItem.color);
		holder.questionView.setType(questionItem.type);
		if(questionItem.type == QuestionLayout.TYPE_MOST){
			holder.selectedView.setVisibility(View.VISIBLE);
			holder.noView.setVisibility(View.GONE);
		}else{
			holder.selectedView.setVisibility(View.GONE);
			holder.noView.setVisibility(View.VISIBLE);
		}
		
		if(question.status.gameover){
			holder.gameoverView.setVisibility(View.VISIBLE);
			if(question.status.passIndex.indexOf(position) != -1){
				holder.questionView.setStatus(new QuestionLayout.Status(question.status.gameover, true));
				holder.passView.setVisibility(View.VISIBLE);
				holder.notpassView.setVisibility(View.GONE);
			}else{
				holder.questionView.setStatus(new QuestionLayout.Status(question.status.gameover, false));
				holder.passView.setVisibility(View.GONE);
				holder.notpassView.setVisibility(View.VISIBLE);
			}
		}else{
			holder.gameoverView.setVisibility(View.GONE);
		}
		holder.questionView.invalidate();
		return convertView;
	}
	
	private static class ViewHolder{
		private TextView noView;
		private TextView nameView;
		private QuestionLayout questionView;
		private TextView percentView;
		private View gameoverView;
		private View passView;
		private View notpassView;
		private View selectedView;
		private TextView selectedNo;
		public ViewHolder(View view) {
			noView = (TextView) view.findViewById(R.id.no);
			nameView = (TextView) view.findViewById(R.id.name);
			questionView = (QuestionLayout) view.findViewById(R.id.questionlayout);
			percentView = (TextView) view.findViewById(R.id.percent);
			gameoverView = view.findViewById(R.id.gameover);
			passView = view.findViewById(R.id.pass);
			selectedView = view.findViewById(R.id.selected);
			selectedNo = (TextView) view.findViewById(R.id.selected_no);
			notpassView = view.findViewById(R.id.notpass);
		}
	}

}
