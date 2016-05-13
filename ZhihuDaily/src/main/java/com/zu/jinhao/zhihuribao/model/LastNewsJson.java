package com.zu.jinhao.zhihuribao.model;

/**
 * 3.URL: http://news-at.zhihu.com/api/4/news/latest
 * */
/**
	 分析
	 date : 日期
	 stories : 当日新闻
	 title : 新闻标题
	 images : 图像地址（官方 API 使用数组形式。目前暂未有使用多张图片的情形出现，曾见无 images 属性的情况，请在使用中注意 ）
	 ga_prefix : 供 Google Analytics 使用
	 type : 作用未知
	 id : url 与 share_url 中最后的数字（应为内容的 id）
	 multipic : 消息是否包含多张图片（仅出现在包含多图的新闻中）
	 top_stories : 界面顶部 ViewPager 滚动显示的显示内容（子项格式同上）
 */
import java.io.Serializable;
import java.util.List;

public class LastNewsJson implements Serializable{
	String date;
	List<Story> stories;
	List<TopStory> top_stories;
	public static class TopStory implements Serializable{
		String image;
		int type;
		int id;
		String ga_prefix;
		String title;

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getGa_prefix() {
			return ga_prefix;
		}

		public void setGa_prefix(String ga_prefix) {
			this.ga_prefix = ga_prefix;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<Story> getStories() {
		return stories;
	}

	public void setStories(List<Story> stories) {
		this.stories = stories;
	}

	public List<TopStory> getTop_stories() {
		return top_stories;
	}

	public void setTop_stories(List<TopStory> top_stories) {
		this.top_stories = top_stories;
	}
}