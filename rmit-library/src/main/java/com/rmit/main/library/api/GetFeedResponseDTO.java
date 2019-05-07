package com.rmit.main.library.api;

import java.util.List;

public class GetFeedResponseDTO extends ServiceResponse {

	private static final long serialVersionUID = -7760142356011546133L;

	private List<FeedDTO> feed;

	private long size;

	public List<FeedDTO> getFeed() {
		return feed;
	}

	public void setFeed(List<FeedDTO> feed) {
		this.feed = feed;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "GetFeedResponseDTO [feed=" + feed + ", size=" + size + "]";
	}

}
