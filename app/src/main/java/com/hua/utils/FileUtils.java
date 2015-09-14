/*
 * @(#)FileUtils.java		       Project:com.sinaapp.msdxblog.androidkit
 * Date:2012-5-6
 *
 * Copyright (c) 2011 CFuture09, Institute of Software, 
 * Guangdong Ocean University, Zhanjiang, GuangDong, China.
 * All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hua.utils;

import android.content.Context;
import android.os.Environment;

import com.hua.bean.CityBean;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;


/**
 * 此类的代码完全抽取自apache开源项目commons中的commons-io包，并作适当修改，使其更适合在手机上执行。
 * 
 * @author Geek_Soledad (66704238@51uc.com)
 */
public class FileUtils {
	/**
	 * The number of bytes in a kilobyte.
	 */
	public static final long ONE_KB = 1024;

	/**
	 * The number of bytes in a megabyte.
	 */
	public static final long ONE_MB = ONE_KB * ONE_KB;

	/**
	 * The file copy buffer size (10 MB) （原为30MB，为更适合在手机上使用，将其改为10MB，by
	 * Geek_Soledad)
	 */
	private static final long FILE_COPY_BUFFER_SIZE = ONE_MB * 10;

	/**
	 * <p>
	 * 将一个目录下的文件全部拷贝到另一个目录里面，并且保留文件日期。
	 * </p>
	 * <p>
	 * 如果目标目录不存在，则被创建。 如果目标目录已经存在，则将会合并两个文件夹的内容，若有冲突则替换掉目标目录中的文件。
	 * </p>
	 * 
	 * @param srcDir
	 *            源目录，不能为null且必须存在。
	 * @param destDir
	 *            目标目录，不能为null。
	 * @throws NullPointerException
	 *             如果源目录或目标目录为null。
	 * @throws IOException
	 *             如果源目录或目标目录无效。
	 * @throws IOException
	 *             如果拷贝中出现IO错误。
	 */
	public static void copyDirectoryToDirectory(File srcDir, File destDir)
			throws IOException {
		if (srcDir == null) {
			throw new NullPointerException("Source must not be null");
		}
		if (srcDir.exists() && srcDir.isDirectory() == false) {
			throw new IllegalArgumentException("Source '" + destDir
					+ "' is not a directory");
		}
		if (destDir == null) {
			throw new NullPointerException("Destination must not be null");
		}
		if (destDir.exists() && destDir.isDirectory() == false) {
			throw new IllegalArgumentException("Destination '" + destDir
					+ "' is not a directory");
		}
		copyDirectory(srcDir, new File(destDir, srcDir.getName()), true);
	}

	/**
	 * <p>
	 * 将目录及其以下子目录拷贝到一个新的位置，并且保留文件日期。
	 * <p>
	 * 如果目标目录不存在，则被创建。 如果目标目录已经存在，则将会合并两个文件夹的内容，若有冲突则替换掉目标目录中的文件。
	 * <p>
	 * 
	 * @param srcDir
	 *            一个存在的源目录，不能为null。
	 * @param destDir
	 *            新的目录，不能为null。
	 * 
	 * @throws NullPointerException
	 *             如果源目录或目标目录为null。
	 * @throws IOException
	 *             如果源目录或目标目录无效。
	 * @throws IOException
	 *             如果拷贝中出现IO错误。
	 */
	public static void copyDirectory(File srcDir, File destDir)
			throws IOException {
		copyDirectory(srcDir, destDir, true);
	}

	/**
	 * 拷贝目录到一个新的位置。
	 * <p>
	 * 该方法将拷贝指定的源目录的所有内容到一个新的目录中。
	 * </p>
	 * <p>
	 * 如果目标目录不存在，则被创建。 如果目标目录已经存在，则将会合并两个文件夹的内容，若有冲突则替换掉目标目录中的文件。
	 * </p>
	 * 
	 * @param srcDir
	 *            一个存在的源目录，不能为null。
	 * @param destDir
	 *            新的目录，不能为null。
	 * 
	 * @throws NullPointerException
	 *             如果源目录或目标目录为null。
	 * @throws IOException
	 *             如果源目录或目标目录无效。
	 * @throws IOException
	 *             如果拷贝中出现IO错误。
	 */
	public static void copyDirectory(File srcDir, File destDir,
			boolean preserveFileDate) throws IOException {
		if (srcDir == null) {
			throw new NullPointerException("Source must not be null");
		}
		if (srcDir.exists() && srcDir.isDirectory() == false) {
			throw new IllegalArgumentException("Source '" + destDir
					+ "' is not a directory");
		}
		if (destDir == null) {
			throw new NullPointerException("Destination must not be null");
		}
		if (destDir.exists() && destDir.isDirectory() == false) {
			throw new IllegalArgumentException("Destination '" + destDir
					+ "' is not a directory");
		}
		if (srcDir.getCanonicalPath().equals(destDir.getCanonicalPath())) {
			throw new IOException("Source '" + srcDir + "' and destination '"
					+ destDir + "' are the same");
		}

		// 为满足当目标目录在源目录里面的情况。
		List<String> exclusionList = null;
		if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath())) {
			File[] srcFiles = srcDir.listFiles();
			if (srcFiles != null && srcFiles.length > 0) {
				exclusionList = new ArrayList<String>(srcFiles.length);
				for (File srcFile : srcFiles) {
					File copiedFile = new File(destDir, srcFile.getName());
					exclusionList.add(copiedFile.getCanonicalPath());
				}
			}
		}

		doCopyDirectory(srcDir, destDir, preserveFileDate, exclusionList);
	}

	/**
	 * Internal copy directory method.
	 * 
	 * @param srcDir
	 *            the validated source directory, must not be <code>null</code>
	 * @param destDir
	 *            the validated destination directory, must not be
	 *            <code>null</code>
	 *            the filter to apply, null means copy all directories and files
	 * @param preserveFileDate
	 *            whether to preserve the file date
	 * @param exclusionList
	 *            List of files and directories to exclude from the copy, may be
	 *            null
	 * @throws IOException
	 *             if an error occurs
	 * @since Commons IO 1.1
	 */
	private static void doCopyDirectory(File srcDir, File destDir,
			boolean preserveFileDate, List<String> exclusionList)
			throws IOException {
		// recurse
		File[] srcFiles = srcDir.listFiles();
		if (srcFiles == null) { // null if abstract pathname does not denote a
								// directory, or if an I/O error occurs
			throw new IOException("Failed to list contents of " + srcDir);
		}
		if (destDir.exists()) {
			if (destDir.isDirectory() == false) {
				throw new IOException("Destination '" + destDir
						+ "' exists but is not a directory");
			}
		} else {
			if (!destDir.mkdirs() && !destDir.isDirectory()) {
				throw new IOException("Destination '" + destDir
						+ "' directory cannot be created");
			}
		}
		if (destDir.canWrite() == false) {
			throw new IOException("Destination '" + destDir
					+ "' cannot be written to");
		}
		for (File srcFile : srcFiles) {
			File dstFile = new File(destDir, srcFile.getName());
			if (exclusionList == null
					|| !exclusionList.contains(srcFile.getCanonicalPath())) {
				if (srcFile.isDirectory()) {
					doCopyDirectory(srcFile, dstFile, preserveFileDate,
							exclusionList);
				} else {
					doCopyFile(srcFile, dstFile, preserveFileDate);
				}
			}
		}

		// Do this last, as the above has probably affected directory metadata
		if (preserveFileDate) {
			destDir.setLastModified(srcDir.lastModified());
		}
	}

	/**
	 * Internal copy file method.
	 * 
	 * @param srcFile
	 *            the validated source file, must not be <code>null</code>
	 * @param destFile
	 *            the validated destination file, must not be <code>null</code>
	 * @param preserveFileDate
	 *            whether to preserve the file date
	 * @throws IOException
	 *             if an error occurs
	 */
	private static void doCopyFile(File srcFile, File destFile,
			boolean preserveFileDate) throws IOException {
		if (destFile.exists() && destFile.isDirectory()) {
			throw new IOException("Destination '" + destFile
					+ "' exists but is a directory");
		}

		FileInputStream fis = null;
		FileOutputStream fos = null;
		FileChannel input = null;
		FileChannel output = null;
		try {
			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(destFile);
			input = fis.getChannel();
			output = fos.getChannel();
			long size = input.size();
			long pos = 0;
			long count = 0;
			while (pos < size) {
				count = (size - pos) > FILE_COPY_BUFFER_SIZE ? FILE_COPY_BUFFER_SIZE
						: (size - pos);
				pos += output.transferFrom(input, pos, count);
			}
		} finally {
			IOUtils.closeQuietly(output);
			IOUtils.closeQuietly(fos);
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(fis);
		}

		if (srcFile.length() != destFile.length()) {
			throw new IOException("Failed to copy full contents from '"
					+ srcFile + "' to '" + destFile + "'");
		}
		if (preserveFileDate) {
			destFile.setLastModified(srcFile.lastModified());
		}
	}

	/**
	 * Opens a {@link FileInputStream} for the specified file, providing better
	 * error messages than simply calling <code>new FileInputStream(file)</code>
	 * .
	 * <p>
	 * At the end of the method either the stream will be successfully opened,
	 * or an exception will have been thrown.
	 * <p>
	 * An exception is thrown if the file does not exist. An exception is thrown
	 * if the file object exists but is a directory. An exception is thrown if
	 * the file exists but cannot be read.
	 * 
	 * @param file
	 *            the file to open for input, must not be <code>null</code>
	 * @return a new {@link FileInputStream} for the specified file
	 * @throws FileNotFoundException
	 *             if the file does not exist
	 * @throws IOException
	 *             if the file object is a directory
	 * @throws IOException
	 *             if the file cannot be read
	 * @since Commons IO 1.3
	 */
	public static FileInputStream openInputStream(File file) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file
						+ "' exists but is a directory");
			}
			if (file.canRead() == false) {
				throw new IOException("File '" + file + "' cannot be read");
			}
		} else {
			throw new FileNotFoundException("File '" + file
					+ "' does not exist");
		}
		return new FileInputStream(file);
	}

	/**
	 * Reads the contents of a file line by line to a List of Strings. The file
	 * is always closed.
	 * 
	 * @param file
	 *            the file to read, must not be <code>null</code>
	 * @param encoding
	 *            the encoding to use, <code>null</code> means platform default
	 * @return the list of Strings representing each line in the file, never
	 *         <code>null</code>
	 * @throws IOException
	 *             in case of an I/O error
	 * @throws java.io.UnsupportedEncodingException
	 *             if the encoding is not supported by the VM
	 * @since Commons IO 1.1
	 */
	public static List<String> readLines(File file, String encoding)
			throws IOException {
		InputStream in = null;
		try {
			in = openInputStream(file);
			return readLines(in, encoding);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	/**
	 * Reads the contents of a file line by line to a List of Strings using the
	 * default encoding for the VM. The file is always closed.
	 * 
	 * @param file
	 *            the file to read, must not be <code>null</code>
	 * @return the list of Strings representing each line in the file, never
	 *         <code>null</code>
	 * @throws IOException
	 *             in case of an I/O error
	 * @since Commons IO 1.3
	 */
	public static List<String> readLines(File file) throws IOException {
		return readLines(file, null);
	}

	/**
	 * Get the contents of an <code>InputStream</code> as a list of Strings, one
	 * entry per line, using the default character encoding of the platform.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 * 
	 * @param input
	 *            the <code>InputStream</code> to read from, not null
	 * @return the list of Strings, never null
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static List<String> readLines(InputStream input) throws IOException {
		InputStreamReader reader = new InputStreamReader(input);
		return readLines(reader);
	}

	/**
	 * Get the contents of an <code>InputStream</code> as a list of Strings, one
	 * entry per line, using the specified character encoding.
	 * <p>
	 * Character encoding names can be found at <a
	 * href="http://www.iana.org/assignments/character-sets">IANA</a>.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 * 
	 * @param input
	 *            the <code>InputStream</code> to read from, not null
	 * @param encoding
	 *            the encoding to use, null means platform default
	 * @return the list of Strings, never null
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static List<String> readLines(InputStream input, String encoding)
			throws IOException {
		if (encoding == null) {
			return readLines(input);
		} else {
			InputStreamReader reader = new InputStreamReader(input, encoding);
			return readLines(reader);
		}
	}

	/**
	 * Get the contents of a <code>Reader</code> as a list of Strings, one entry
	 * per line.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedReader</code>.
	 * 
	 * @param input
	 *            the <code>Reader</code> to read from, not null
	 * @return the list of Strings, never null
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.1
	 */
	public static List<String> readLines(Reader input) throws IOException {
		BufferedReader reader = new BufferedReader(input);
		List<String> list = new ArrayList<String>();
		String line = reader.readLine();
		while (line != null) {
			list.add(line);
			line = reader.readLine();
		}
		return list;
	}

	/**
	 * 快速拷贝文件。
	 * 
	 * @param is
	 *            数据来源
	 * @param os
	 *            数据目标
	 * @throws IOException
	 */
	public static void copyFileFast(FileInputStream is, FileOutputStream os)
			throws IOException {
		FileChannel in = is.getChannel();
		FileChannel out = os.getChannel();
		in.transferTo(0, in.size(), out);
	}

	/**
	 * 返回自定文件或文件夹的大小
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFileSizes(File f) throws Exception {// 取得文件大小
		long s = 0;
		if (f.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(f);
			s = fis.available();
		} else {
			f.createNewFile();
			System.out.println("文件不存在");
		}
		return s;
	}

	// 递归
	public static long getFileSize(File f) throws Exception// 取得文件夹大小
	{
		if(f==null){
			return 0;
		}
		long size = 0;
		File flist[] = f.listFiles();
		if (flist == null) {
			return 0;
		}
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSize(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}

	public static String FormetFileSize(long fileS) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#0.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 获取文件夹大小
	 * @param f
	 * @return
	 */
	public static long getlist(File f) {// 递归求取目录文件个数
		long size = 0;
		File flist[] = f.listFiles();
		size = flist.length;
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getlist(flist[i]);
				size--;
			}
		}
		return size;

	}

	/**
	 * 创建目录
	 * @param fileName
	 * @return
	 */
	public static File createFile(String fileName) {
		File dir = new File(PhotoUtil.DIR);
		if (!dir.exists()) {
			dir.mkdir();
		}
		dir = new File(fileName);
		if (!dir.exists()) {
			try {
				dir.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dir;
	}

	/**
	 * // 写入properties信息
	 * @param context
	 * @param list
	 */
	public static void writeProperties(Context context, List<CityBean> list) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Properties prop = new Properties();
			try {
				File file = createCity(context);
				if(file==null){
					return;
				}
				InputStream fis = new FileInputStream(file);
				// 从输入流中读取属性列表（键和元素对）
				prop.load(fis);
				// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
				// 强制要求为属性的键和值  使用字符串。返回值是 Hashtable 调用 put 的结果。
				OutputStream fos = new FileOutputStream(file);

				for (CityBean cityBean : list) {
					String P = "";
					if (StringUtil.isNull(cityBean.getEle())) {

						if (cityBean.getName().length() > 1) {
							P = cityBean.getName().substring(0, 1);
						}
						P = StringUtil.getPinYinHeadChar(P);
					} else {
						P = cityBean.getEle();
					}
					prop.setProperty(cityBean.getId(), P + cityBean.getName());
				}
				// 以适合使用 load 方法加载到 Properties 表中的格式，
				// 将此 Properties 表中的属性列表（键和元素对）写入输出流
				prop.store(fos, "Update");
			} catch (IOException e) {
				System.out.println(e);
			}
		}

	}

	/**
	 * 创建城市文件
	 * @param context
	 * @return
	 */
	private static File createCity(Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			try {
				File dir = new File(Environment.getExternalStorageDirectory()
						+ "/Android/data/" + context.getPackageName() + "/");
				if (dir!=null&&!dir.exists())
					dir.mkdir();
				File file = new File(dir, "city.properties");
				if (file!=null&&!file.exists()) {
					file.createNewFile();
				}
				return file;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 是否存在城市缓存文件
	 * @param context
	 * @return
	 */
	public static File isExistsCity(Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			try {
				File dir = new File(Environment.getExternalStorageDirectory()
						+ "/Android/data/" + context.getPackageName() + "/");
				if (!dir.exists())
					dir.mkdir();
				File file = new File(dir, "city.properties");
				return file;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	// 读取properties的全部信息
	public static List<CityBean> readProperties(Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			List<CityBean> list = new ArrayList<CityBean>();
			Properties props = new Properties();
			CityBean bean;
			try {
				InputStream in = new BufferedInputStream(new FileInputStream(
						createCity(context)));
				props.load(in);

				Enumeration en = props.propertyNames();
				while (en.hasMoreElements()) {
					String key = (String) en.nextElement();
					String Property = props.getProperty(key);
					bean = new CityBean();
					bean.setId(key);
					bean.setName(Property);
					list.add(bean);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		return null;
	}

	// 读取properties的全部信息
	public static CityBean readProperties(Context context, String value) {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Properties props = new Properties();
			CityBean bean = null;
			try {
				InputStream in = new BufferedInputStream(new FileInputStream(
						createCity(context)));
				props.load(in);

				Enumeration en = props.propertyNames();
				while (en.hasMoreElements()) {
					String key = (String) en.nextElement();
					String Property = props.getProperty(key);
					if (Property.contains(value)) {
						bean = new CityBean();
						bean.setId(key);
						bean.setName(Property);
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bean;
		}
		
		return null;
	}
}
