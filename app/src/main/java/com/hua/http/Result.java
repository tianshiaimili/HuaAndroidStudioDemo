package com.hua.http;

import java.io.Serializable;
import java.util.List;

/**
 * pt前缀接口返回结构
 * 
 *
 * @param <T>
 */
public class Result<T> {
    public static final int STATUS_UNKNOWN = -1;
    public static final int STATUS_ERR = 0;
	public static final int STATUS_OK = 1;

	private int status;

	private ErrorMsg errmsg;

    private UnknownBody unknownBody;

	private List<T> arrayData;

    private T singleData;

    private boolean isArrayResult;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ErrorMsg getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(ErrorMsg errmsg) {
		this.errmsg = errmsg;
	}

    public UnknownBody getUnknownBody() {
        return unknownBody;
    }

    public void setUnknownBody(UnknownBody unknownBody) {
        this.unknownBody = unknownBody;
    }

    public T getData() {
        return singleData;
    }

    public void setData(T singleData) {
        this.singleData = singleData;
    }

    public List<T> getArrayData() {
		return arrayData;
	}

	public void setArrayData(List<T> arrayData) {
		this.arrayData = arrayData;
	}

    public boolean isArrayResult() {
        return isArrayResult;
    }

    public void setArrayResult(boolean isArrayResult) {
        this.isArrayResult = isArrayResult;
    }

    public static class ErrorMsg implements Serializable {
		private static final long serialVersionUID = 7183771210791009353L;

		private int errno;

		private String msg;

        public ErrorMsg(String errMsg) {
            this.msg = errMsg;
        }

        public ErrorMsg(int errCode, String errMsg) {
            this.errno = errCode;
            this.msg = errMsg;
        }

		public int getErrno() {
			return errno;
		}

		public void setErrno(int errno) {
			this.errno = errno;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		@Override
		public String toString() {
			return "error no=" + getErrno() + ", msg=" + getMsg();
		}
	}


    public static class UnknownBody implements Serializable {

        private String mMsg;

        public UnknownBody(String msg) {
            this.mMsg = msg;
        }

        public String getmMsg() {
            return mMsg;
        }

        public void setmMsg(String mMsg) {
            this.mMsg = mMsg;
        }
    }
}
