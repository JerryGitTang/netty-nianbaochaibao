/**
 * @author rency
 * @data Jan 10, 2018 12:30:13 PM
 * @describe SmartCarProtocol.java <br>
 *      <p>TODO</p>
 * @version 0.0.1
 */
package com.netty.potocal;

/**
 * 自己定义的协议 
 * @author rency
 * @data Jan 10, 2018 12:30:13 PM
 * @describe SmartCarProtocol.java <br>
 *      <p>
 * 		 数据包格式 
 * 		+——----——+——-----——+——----——+ 
 * 		|协议开始标志|  长度             |   数据       | 
 * 		+——----——+——-----——+——----——+ 
 * 		1.协议开始标志head_data，为int类型的数据，16进制表示为0X76 
 * 		2.传输数据的长度contentLength，int类型 
 * 		3.要传输的数据 
 * </p>
 * @version 0.0.1
 */
public class SmartCarProtocol {
	/** 
     * 消息的开头的信息标志 
     */ 
	private int head_data = ConstantValue.HEAD_DATA;
	/** 
     * 消息的长度 
     */  
    private int contentLength;  
    /** 
     * 消息的内容 
     */  
    private byte[] content;
    
    public SmartCarProtocol(int contentLength,byte[] content) {
		this.content = content;
		this.contentLength = contentLength;
	}

	public int getHead_data() {
		return head_data;
	}

	public void setHead_data(int head_data) {
		this.head_data = head_data;
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "SmartCarProtocol [head_data=" + head_data + ", contentLength=" + contentLength + ", content="
				+ getContentStr() + "]";
	}
    
	private String getContentStr(){
		String str = new String(content);
		return str;
	}
    

}
