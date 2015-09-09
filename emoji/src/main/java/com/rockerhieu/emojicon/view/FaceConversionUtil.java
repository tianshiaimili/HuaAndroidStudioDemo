package com.rockerhieu.emojicon.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rockerhieu.emojicon.emoji.Emojicon;
import com.rockerhieu.emojicon.emoji.Mama;
import com.rockerhieu.emojicon.emoji.People;

/**
 * 
 ****************************************** 
 * @文件名称 : FaceConversionUtil.java
 * @创建时间 : 2013-1-27 下午02:34:09
 * @文件描述 : 表情轉換工具
 ****************************************** 
 */
public class FaceConversionUtil {

	private boolean usingEmoji;

	private static FaceConversionUtil mEmojiFaceConversionUtil;
	private static FaceConversionUtil mMamaFaceConversionUtil;

	/** 每一页表情的个数 */
	public int pageSize = 20;

	/** 保存于内存中的表情集合 */
	private List<Emojicon> emojis = new ArrayList<>();

	/** 表情分页的结果集合 */
	public List<List<Emojicon>> emojiLists = new ArrayList<>();

	private FaceConversionUtil(boolean usingEmoji) {
		this.usingEmoji = usingEmoji;
		pageSize = usingEmoji ? 20 : 17;
	}

	public static FaceConversionUtil getInstace(boolean usingEmoji) {
		if (usingEmoji) {
			if (mEmojiFaceConversionUtil == null)
				mEmojiFaceConversionUtil = new FaceConversionUtil(true);
			return mEmojiFaceConversionUtil;
		}
		else {
			if (mMamaFaceConversionUtil == null)
				mMamaFaceConversionUtil = new FaceConversionUtil(false);
			return mMamaFaceConversionUtil;
		}
	}

	/**
	 * 解析字符
	 */
	public void ParseData() {
		if (emojiLists.size() > 0)
			return;
		try {
			emojiLists.clear();
			emojis.clear();
			if (usingEmoji)
				emojis.addAll(Arrays.asList(People.DATA));
			else
				emojis.addAll(Arrays.asList(Mama.DATA));
			int pageCount = (int) Math.ceil(emojis.size() / ((double) pageSize));

			for (int i = 0; i < pageCount; i++) {
				emojiLists.add(getData(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取分页数据
	 * 
	 * @param page
	 * @return
	 */
	private List<Emojicon> getData(int page) {
		int startIndex = page * pageSize;
		int endIndex = startIndex + pageSize;

		if (endIndex > emojis.size()) {
			endIndex = emojis.size();
		}
		List<Emojicon> list = new ArrayList<>();
		list.addAll(emojis.subList(startIndex, endIndex));
		if (list.size() < pageSize) {
			for (int i = list.size(); i < pageSize; i++) {
				Emojicon object = new Emojicon();
				list.add(object);
			}
		}
		if (list.size() == pageSize) {
			list.add(Emojicon.fromCodePoint(0x1f888));
		}
		return list;
	}
}